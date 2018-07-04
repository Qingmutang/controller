package com.modianli.power;

import com.modianli.power.sms.api.SmsConfiguration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Created by gao on 17-3-28.
 */
@SpringBootApplication(scanBasePackageClasses = RedisKeyConstants.class)
@EnableConfigurationProperties(value = {SmsConfiguration.class})
public class NotifierApplication {

  public static void main(String[] args) {
	SpringApplication.run(NotifierApplication.class, args);
  }

  @Bean
  @Primary
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
	return new Jackson2JsonMessageConverter();
  }


}
