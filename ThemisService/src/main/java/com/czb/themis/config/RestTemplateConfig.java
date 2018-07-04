package com.czb.themis.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.czb.themis.config.properties.HttpConfigProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置restTemplate
 *
 * @author japper
 * @since 2018年6月15日
 */
@Configuration
@EnableConfigurationProperties(HttpConfigProperties.class)
@Slf4j
public class RestTemplateConfig {

    @Autowired
    private HttpConfigProperties httpConfigProperties;

    /**
     * 自定义配置restTemplate
     *
     * @param clientHttpRequestFactory http请求工厂
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(@Qualifier("clientHttpRequestFactory") ClientHttpRequestFactory
                                                 clientHttpRequestFactory) {
        // 添加内容转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(new FastJsonHttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        if(log.isDebugEnabled()){
            log.debug("--初始化restTemplate完成");
        }
        return restTemplate;
    }

    /**
     * 配置http请求工厂
     *
     * @return http请求工厂
     */
    @Bean(name = "clientHttpRequestFactory")
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        // 长连接保持20秒,如有其它请求可以复用该链接
        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager
                (httpConfigProperties.getTimeToLive(), TimeUnit.SECONDS);
        // 总连接数
        pollingConnectionManager.setMaxTotal(httpConfigProperties.getMaxTotal());
        // 同路由的并发数
        pollingConnectionManager.setDefaultMaxPerRoute(httpConfigProperties.getMaxPerRoute());
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        // 重试次数，默认是3次，没有开启
        //httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true))
        // 保持长连接配置，需要在头添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Connection", "Keep-Alive"));
        httpClientBuilder.setDefaultHeaders(headers);
        HttpClient httpClient = httpClientBuilder.build();
        // httpClient连接配置，底层是配置RequestConfig
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory
                (httpClient);
        // 连接超时时间
        clientHttpRequestFactory.setConnectTimeout(httpConfigProperties.getConnectTimeout());
        // 数据读取超时时间，即SocketTimeout
        clientHttpRequestFactory.setReadTimeout(httpConfigProperties.getReadTimeout());
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(httpConfigProperties.getConnectionRequestTimeout());
        clientHttpRequestFactory.setBufferRequestBody(httpConfigProperties.isBufferRequestBody());
        return clientHttpRequestFactory;
    }
}
