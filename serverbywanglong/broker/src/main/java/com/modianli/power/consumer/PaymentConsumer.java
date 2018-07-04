package com.modianli.power.consumer;

//import com.modianli.power.MessagingConstants;
//import com.modianli.power.payment.alipay.util.AlipayNotify;
//
//import org.springframework.amqp.AmqpRejectAndDontRequeueException;
//import org.springframework.amqp.rabbit.annotation.Argument;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Created by gao on 17-5-17.
// */
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class PaymentConsumer {
//
//  @RabbitListener(
//	  bindings = @QueueBinding(
//		  value = @Queue(value = MessagingConstants.QUEUE_PAYMENT,
//			  arguments = {
//				  @Argument(name = MessagingConstants.X_DEAD_LETTER_EXCHANGE, value = MessagingConstants.DEAD_LETTER_EXCHANGE),
//				  @Argument(name = MessagingConstants.X_DEAD_LETTER_ROUTING_KEY, value = MessagingConstants.DEAD_LETTER_ROUTING_KEY)
//			  }
//		  ),
//		  exchange = @Exchange(value = MessagingConstants.EXCHANGE_DIRECT_PAYMENT),
//		  key = MessagingConstants.ROUTING_PAYMENT
//	  )
//  )
//  public void handlePayment(Map<String, String> map) {
//	try {
//	  boolean verify = AlipayNotify.verify(map);
//	  if (verify) {
//		log.debug("true");
//	  } else {
//		log.debug("false");
//	  }
//	} catch (Exception e) {
//	  String msg = e.getMessage();
//	  log.error("handle payment fail msg {}", msg);
//	  throw new AmqpRejectAndDontRequeueException(msg);
//	}
//  }
//}
