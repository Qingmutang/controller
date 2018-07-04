package com.modianli.power.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by gao on 17-2-20.
 */
@Configuration
public class BeanConfig {

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
	return new Jackson2JsonMessageConverter();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	return passwordEncoder;
  }

}
