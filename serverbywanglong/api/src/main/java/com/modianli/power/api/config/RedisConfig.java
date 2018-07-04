package com.modianli.power.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableRedisRepositories(basePackages ={"com.modianli.power.persistence.repository.redis"})
public class RedisConfig {

//  @Inject
//  private RedisProperties redisProperties;

//  @Bean
//  @Primary
//  public JedisConnectionFactory redisConnectionFactory() {
//	log.info("connecting reids: {} ", redisProperties.getHost());
//	JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
//
//	connectionFactory.setHostName(redisProperties.getHost());
//	connectionFactory.setUsePool(true);
//	connectionFactory.setPort(redisProperties.getPort());
//
//	if (StringUtils.hasText(redisProperties.getPassword())) {
//	  connectionFactory.setPassword(redisProperties.getPassword());
//	}
//
//	return connectionFactory;
//  }

//  @Bean
//  public StringRedisTemplate stringRedisTemplate() {
//	StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory());
//	return stringRedisTemplate;
//  }
//
//  @Bean(name = "redisTemplate")
//  public RedisTemplate<Object, Object> redisTemplate() {
//	RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//	redisTemplate.setConnectionFactory(redisConnectionFactory());
//	return redisTemplate;
//  }

}
