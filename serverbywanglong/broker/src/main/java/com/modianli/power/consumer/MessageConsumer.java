package com.modianli.power.consumer;

import com.modianli.power.MessagingConstants;
import com.modianli.power.domain.es.EquipmentProductEs;
import com.modianli.power.model.EmailMessage;
import com.modianli.power.service.ProductService;
import com.modianli.power.service.QiniuCloudService;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
  private QiniuCloudService qiniuCloudService;

  @Inject
  private ProductService productService;

  @Inject
  private RabbitTemplate rabbitTemplate;

  @RabbitListener(
	  bindings = @QueueBinding(
		  value = @Queue(value = MessagingConstants.QUEUE_INDEX,
			  arguments = {
				  @Argument(name = MessagingConstants.X_DEAD_LETTER_EXCHANGE,value = MessagingConstants.DEAD_LETTER_EXCHANGE),
				  @Argument(name = MessagingConstants.X_DEAD_LETTER_ROUTING_KEY,value = MessagingConstants.DEAD_LETTER_ROUTING_KEY)
		  		}
		  ),
		  exchange = @Exchange(value = MessagingConstants.EXCHANGE_INDEX),
		  key = MessagingConstants.ROUTING_INDEX
	  )
  )
  public void handleIndex(EquipmentProductEs productEs) {
    try {
	  productService.saveProduct(productEs);
	}catch (Exception e){
      log.error(e.getMessage());
      throw new AmqpRejectAndDontRequeueException(e.getMessage());
	}
  }

  @RabbitListener(
	  bindings = @QueueBinding(
		  value = @Queue(value = MessagingConstants.DEAD_LETTER_QUEUE),
		  exchange = @Exchange(value = MessagingConstants.DEAD_LETTER_EXCHANGE),
		  key = MessagingConstants.DEAD_LETTER_ROUTING_KEY
	  )
  )
  public void handleDeadLetter(Message msg) {
	log.debug("msg", msg.toString());
	byte[] bytes = msg.getBody();
	EmailMessage email = new EmailMessage();
	email.setContent(new String(bytes));
	email.setTo("910522660@qq.com");
	email.setSubject("error");
	String[] cc = {"739663407@qq.com"};
	email.setCc(cc);
	rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_EMAIL, MessagingConstants.ROUTING_EMAIL, email);
  }

  @RabbitListener(
	  bindings = @QueueBinding(
		  value = @Queue(
		  	value = MessagingConstants.QUEUE_INDEX_BATCH,
			arguments = {
				@Argument(name = MessagingConstants.X_DEAD_LETTER_EXCHANGE,value = MessagingConstants.DEAD_LETTER_EXCHANGE),
				@Argument(name = MessagingConstants.X_DEAD_LETTER_ROUTING_KEY,value = MessagingConstants.DEAD_LETTER_ROUTING_KEY)
			}
		  ),
		  exchange = @Exchange(value = MessagingConstants.EXCHANGE_INDEX),
		  key = MessagingConstants.ROUTING_INDEX_BATCH
	  )
  )
  public void handleBatchIndex(String msg) {
	try {
	  productService.batchSaveProducts();
	}catch (Exception e){
	  log.error(e.getMessage());
	  throw new AmqpRejectAndDontRequeueException(e.getMessage());
	}
  }

  @RabbitListener(
	  bindings = @QueueBinding(
		  value = @Queue(
		  	value = MessagingConstants.QUEUE_PICTURE_BATCH,
			arguments = {
				@Argument(name = MessagingConstants.X_DEAD_LETTER_EXCHANGE,value = MessagingConstants.DEAD_LETTER_EXCHANGE),
				@Argument(name = MessagingConstants.X_DEAD_LETTER_ROUTING_KEY,value = MessagingConstants.DEAD_LETTER_ROUTING_KEY)
		  	}
		  ),
		  exchange = @Exchange(value = MessagingConstants.EXCHANGE_DIRECT_PICTURE),
		  key = MessagingConstants.ROUTING_PICTURE_BATCH
	  )
  )
  public void handleBatchDeleteInvalidPicture(String[] urls){
    if (urls == null || urls.length < 1){
      return;
	}

	try {
	  qiniuCloudService.batchDeleteResource(urls);
	}catch (Exception e){
	  log.error(e.getMessage());
	  throw new AmqpRejectAndDontRequeueException(e.getMessage());
	}
  }

}
