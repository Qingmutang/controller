/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

@Service
public class CacheService {

  private static final Logger log = LoggerFactory.getLogger(CacheService.class);

  private static final int DEFAULT_TIME_TO_LIVE = 24 * 3600;
  private final int timeToLive = DEFAULT_TIME_TO_LIVE;

  @Inject
  private RedisTemplate<Object, Object> redisTemplate;

  public CacheService() {
  }

  public void set(Object key, final Object value, final int expire) {

	if (log.isDebugEnabled()) {
	  log.debug("storing value in redis @" + key + ":" + value + ":" + expire);
	}

	redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);

  }

  public void set(Object key, Object value) {
	set(key, value, timeToLive);
  }

  public Object get(String key) {
	if (log.isDebugEnabled()) {
	  log.debug("retrieving value from key @" + key);
	}

	Object val = redisTemplate.opsForValue().get(key);
	if (log.isDebugEnabled()) {
	  log.debug("retrieving value @" + val);
	}

	return val;
  }

  public void delete(Object key) {
	redisTemplate.delete(key);
  }

}
