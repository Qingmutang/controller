package com.modianli.power.common.aop;

import com.modianli.power.locks.annotation.RedisLockable;
import com.modianli.power.locks.model.Lock;
import com.modianli.power.locks.service.LockService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-4-1.
 */
@Component
@Aspect
@Slf4j
public class RedisLockAop {

  @Inject
  private LockService lockService;

  @Around("execution(* *.*(..)) && @annotation(redisLockable)")
  public Object logAround(ProceedingJoinPoint joinPoint, RedisLockable redisLockable) throws Throwable {
	if (StringUtils.hasText(redisLockable.key())) {
	  Lock lock = lockService.create(redisLockable.key(), redisLockable.expiration());
	  try {
		return joinPoint.proceed();
	  } finally {
		lockService.release(redisLockable.key(), lock.getValue());
	  }
	} else {
	  return joinPoint.proceed();
	}
  }
}
