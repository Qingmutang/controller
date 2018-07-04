package com.modianli.power.api.consumer;

import com.modianli.power.MessagingConstants;
import com.modianli.power.RedisKeyConstants;
import com.modianli.power.model.enums.EnterpriseEvent;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;

import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-3-28.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

  @Inject
  private RabbitTemplate rabbitTemplate;

  @Inject
  @Qualifier("redisTemplate")
  private RedisTemplate<Object, Object> redisTemplate;

  @Inject
  private CacheManager cacheManager;

  @Inject
  private EnterpriseRepository enterpriseRepository;

  @RabbitListener(
	  bindings = @QueueBinding(
		  value = @Queue(value = MessagingConstants.QUEUE_ENTERPRISE,
			  arguments = {
				  @Argument(name = MessagingConstants.X_DEAD_LETTER_EXCHANGE, value = MessagingConstants.DEAD_LETTER_EXCHANGE),
				  @Argument(name = MessagingConstants.X_DEAD_LETTER_ROUTING_KEY, value = MessagingConstants.DEAD_LETTER_ROUTING_KEY)
			  }
		  ),
		  exchange = @Exchange(value = MessagingConstants.EXCHANGE_ENTERPRISE),
		  key = MessagingConstants.ROUTING_ENTERPRISE
	  )
  )
  public void handleEnterpriseEvent(EnterpriseEvent event) {
	log.debug("handleEnterpriseEvent event {} ", event);

	cacheManager.getCache(RedisKeyConstants.CACHE_KEY + "enterprise.num").clear();

	Long count = enterpriseRepository.countByActive(true);

	redisTemplate.opsForValue().set("enterprise.num", count);

  }


}
