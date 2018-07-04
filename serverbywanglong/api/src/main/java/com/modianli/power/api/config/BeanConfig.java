package com.modianli.power.api.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lombok.Data;

/**
 * Created by gao on 17-2-20.
 */
@Configuration
public class BeanConfig {

  @Data
  static class DataSourceConfig {

	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private String jndi;
  }

  @Component
  @ConfigurationProperties(prefix = "spring.datasource.config")
  public static class MdDataSourceConfig extends DataSourceConfig {

  }

  @Component
  @Data
  @ConfigurationProperties(prefix = "spring.redis")
  public static class MdRedisConfig {

	private String host;
	private int port;
	private String password;
  }

  @Component
  @Data
  @ConfigurationProperties(prefix = "spring.qiniu")
  public static class MdQiniuConfig {

	private String callbackUrl;
	private String resourceUrl;
  }

  @Bean
  @Primary
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
	return new Jackson2JsonMessageConverter();
  }

//  @Inject
//  private DataSourceProperties dataSourceProperties;
//
//  @Bean(name = "tomcatEmbeddedServletContainerFactory")
//  public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
//	TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory() {
//	  @Override
//	  protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
//		tomcat.enableNaming();
//		TomcatEmbeddedServletContainer container = super.getTomcatEmbeddedServletContainer(tomcat);
//
//		// JNDI lookup with embedded tomcat:  https://github.com/spring-projects/spring-boot/issues/2308
//		Context context = (Context) container.getTomcat().getHost().findChild("");
//		Thread.currentThread().setContextClassLoader(context.getLoader().getClassLoader());
//		return container;
//	  }
//	};
//
//	// add JNDI datasource resources
//	factory.addContextCustomizers((context) -> {
//
//	  // actual datasource
//	  ContextResource resource = new ContextResource();
//	  resource.setName("jdbc/ds");
//	  resource.setType(DataSource.class.getName());
//	  resource.setProperty("driverClassName", dataSourceProperties.getDriverClassName());
//	  resource.setProperty("url", dataSourceProperties.getUrl());
//	  resource.setProperty("username", dataSourceProperties.getUsername());
//	  resource.setProperty("password", dataSourceProperties.getPassword());
//	  context.getNamingResources().addResource(resource);
//
//	});
//
//	return factory;
//  }
//
//  @Bean
//  public DataSource dataSource() throws NamingException {
//	// look up JNDI for this application
//	JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
//	factory.setJndiName("java:comp/env/jdbc/ds");  // lookup ProxyDataSource
//	factory.setExpectedType(DataSource.class);  // as javax.sql.DataSource
//	factory.setLookupOnStartup(false);  // deffer the lookup because resources get registered at app startup
//	factory.afterPropertiesSet();
//	return (DataSource) factory.getObject();
//  }



  @Bean
  WebMvcConfigurer configurer () {
	return new WebMvcConfigurerAdapter() {
	  @Override
	  public void addResourceHandlers (ResourceHandlerRegistry registry) {
		String tmp = System.getProperty("java.io.tmpdir");
		registry.addResourceHandler("/sitemap/**").
			addResourceLocations("file:" + tmp + "/sitemap/");
	  }
	};
  }



}
