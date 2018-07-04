package com.modianli.power.consumer;

import com.modianli.power.MessagingConstants;
import com.modianli.power.email.core.EmailMessage;
import com.modianli.power.email.impl.EmailTemplate;
import com.modianli.power.sms.api.SmsOperations;
import com.modianli.power.sms.core.SmsMessage;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-3-28.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

  private final SmsOperations smsOperations;

  private final EmailTemplate emailTemplate;

  @RabbitListener(
	  bindings = @QueueBinding(
		  value = @Queue(value = MessagingConstants.QUEUE_SMS,
				arguments = {
			  @Argument(name = MessagingConstants.X_DEAD_LETTER_EXCHANGE,value = MessagingConstants.DEAD_LETTER_EXCHANGE),
			  @Argument(name = MessagingConstants.X_DEAD_LETTER_ROUTING_KEY,value = MessagingConstants.DEAD_LETTER_ROUTING_KEY)
		  }
		),
		  exchange = @Exchange(value = MessagingConstants.EXCHANGE_SMS),
		  key = MessagingConstants.ROUTING_SMS
	  )
  )
  public void handleMessage(SmsMessage sms) {
	log.debug("handle sms message @ {} ", sms);
	try {
	  smsOperations.send(sms);
	}catch (Exception e){
	  log.error(e.getMessage());
	  throw new AmqpRejectAndDontRequeueException(e.getMessage());
	}
  }

  @RabbitListener(
	  bindings = @QueueBinding(
		  value = @Queue(value = MessagingConstants.QUEUE_EMAIL,
			  arguments = {
				  @Argument(name = MessagingConstants.X_DEAD_LETTER_EXCHANGE,value = MessagingConstants.DEAD_LETTER_EXCHANGE),
				  @Argument(name = MessagingConstants.X_DEAD_LETTER_ROUTING_KEY,value = MessagingConstants.DEAD_LETTER_ROUTING_KEY)
			  }
			),
		  exchange = @Exchange(value = MessagingConstants.EXCHANGE_EMAIL),
		  key = MessagingConstants.ROUTING_EMAIL
	  )
  )
  public void handleMessage(EmailMessage email) {
	log.debug("handle email message @ {} ", email);
	try{
	  emailTemplate.send(email);
	}catch (Exception e){
	  log.error(e.getMessage());
	  throw new AmqpRejectAndDontRequeueException(e.getMessage());
	}
  }
}
