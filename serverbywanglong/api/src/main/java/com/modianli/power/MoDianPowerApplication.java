package com.modianli.power;

import com.modianli.power.api.config.BeanConfig;
import com.modianli.power.api.config.ElasticsearchConfig;
import com.modianli.power.api.config.JpaConfig;
import com.modianli.power.api.config.MessageSourceConfig;
import com.modianli.power.api.config.ObjectMapperConfig;
import com.modianli.power.api.config.RedisCacheConfig;
import com.modianli.power.api.config.RedisConfig;
import com.modianli.power.api.config.RedisHttpSessionConfig;
import com.modianli.power.api.config.SecurityConfig;
import com.modianli.power.api.config.Swagger2Config;
import com.modianli.power.payment.alipay.config.PaymentBeanConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-2-20.
 */
@Slf4j
@Configuration
@EnableAutoConfiguration(exclude = {
	SecurityAutoConfiguration.class,
	JpaRepositoriesAutoConfiguration.class,
	MongoRepositoriesAutoConfiguration.class,
	RedisRepositoriesAutoConfiguration.class,
	ElasticsearchRepositoriesAutoConfiguration.class,
	MongoAutoConfiguration.class,
	MongoDataAutoConfiguration.class,

})
@EnableRedisHttpSession
@ComponentScans({
	@ComponentScan(basePackageClasses = Constants.class,
		excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ANNOTATION,
				value = {RestController.class,
						 ControllerAdvice.class,
						 Configuration.class
				})
		}),
	@ComponentScan(//
		useDefaultFilters = false,
		basePackageClasses = {Constants.class}, //
		includeFilters = { //

						   @ComponentScan.Filter(
							   type = FilterType.ANNOTATION, //
							   value = {//
										Controller.class,//
										RestController.class,//
										ControllerAdvice.class//
							   }//
						   ) //
		})}
)
@Import({
	BeanConfig.class,
	PaymentBeanConfig.class,
	ObjectMapperConfig.class,
	MessageSourceConfig.class,
	RedisConfig.class,
	RedisCacheConfig.class,
	RedisHttpSessionConfig.class,
	ElasticsearchConfig.class,
	JpaConfig.class,
	SecurityConfig.class,
	Swagger2Config.class
})
@SpringBootConfiguration
@EnableCaching
public class MoDianPowerApplication {

  //todo  deploy to server
//  public static class ApplicationServletInitializer extends SpringBootServletInitializer {
//
//
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//	  return application.sources(MoDianPowerApplication.class);
//	}
//  }

  public static void main(String[] args) throws Exception {
	SpringApplication.run(MoDianPowerApplication.class, args);
  }


}
