package com.modianli.power;

import com.modianli.power.config.BeanConfig;
import com.modianli.power.config.ElasticsearchConfig;
import com.modianli.power.config.JpaConfig;
import com.modianli.power.config.MessageSourceConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gao on 17-3-28.
 */
@Configuration
@EnableAutoConfiguration(exclude = {
	JpaRepositoriesAutoConfiguration.class,
	MongoRepositoriesAutoConfiguration.class,
	RedisRepositoriesAutoConfiguration.class,
	ElasticsearchRepositoriesAutoConfiguration.class,
	MongoAutoConfiguration.class,
	MongoDataAutoConfiguration.class

})
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
@Import({BeanConfig.class,
		 MessageSourceConfig.class,
		 ElasticsearchConfig.class,
		 JpaConfig.class,
})
@EnableScheduling
public class JobApplication {

  public static void main(String[] args) {
	SpringApplication.run(JobApplication.class, args);
  }

}
