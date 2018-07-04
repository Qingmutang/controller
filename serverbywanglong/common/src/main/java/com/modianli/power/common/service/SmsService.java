/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.service;

import com.modianli.power.MessagingConstants;
import com.modianli.power.common.cache.CacheService;
import com.modianli.power.common.captcha.CaptchaService;
import com.modianli.power.common.exception.CaptchaMismatchedException;
import com.modianli.power.common.exception.MobileNumberNotBelongToUserException;
import com.modianli.power.common.exception.RepeatableTransmissionException;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.ImgCaptchaRequest;
import com.modianli.power.model.SmsCodeResult;
import com.modianli.power.persistence.repository.jpa.UserRepository;
import com.modianli.power.sms.core.SmsMessage;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsService {

  private static final String PREFIX = "SMS_SERVICE:";

  private static final int EXPIRES_IN_SECONDS = 90;

  @Inject
  private CacheService cacheService;

  @Inject
  private RabbitTemplate rabbitTemplate;

  @Inject
  private MessageSource messageSource;

  @Inject
  private UserRepository userRepository;

  @Inject
  private CaptchaService captchaService;

  @Inject
  private RedisTemplate redisTemplate;

  public boolean validate(String mobileNumber, String smsCode) {
	Assert.notNull(smsCode, "sms response code can not not null");

	log.debug("verifySmsCode @ mobileNumber @ {}  smsCode@ {} ", mobileNumber, smsCode);

	String key = PREFIX + mobileNumber;

	String existing = (String) cacheService.get(key);

	log.debug("fetching generated code from cache @ {} ", existing);

	boolean flag = smsCode.equals(existing);

	if (flag) {
	  cacheService.delete(key);
	}

	return flag;
  }

  public SmsCodeResult generateSmsCode(String mobileNumber, Long userId, ImgCaptchaRequest form) {
	Assert.hasText(mobileNumber, "mobileNumber can not be null");
	Assert.notNull(form, "ImgCaptchaRequest can not be null");

	log.debug("generateSmsCode @ mobileNumber @ {} userId {}  form {}  ", mobileNumber, userId, form);

	if (!captchaService.verifyImgCaptcha(form)) {
	  throw new CaptchaMismatchedException();
	}

	if (redisTemplate.hasKey(PREFIX + mobileNumber)) {
	  throw new RepeatableTransmissionException();
	}

	if (userId != null) {

	  UserAccount user = userRepository.findOne(userId);

	  if (!mobileNumber.equals(user.getMobileNumber())) {
		throw new MobileNumberNotBelongToUserException();
	  }
	}

	String result = RandomStringUtils.randomNumeric(6);

	log.debug("generated random number @ {} ", result);

	cacheService.set(PREFIX + mobileNumber, result, EXPIRES_IN_SECONDS);

	final SmsMessage smsMessage = new SmsMessage(mobileNumber, messageSource.getMessage("smscode", new String[]{result}, null));

	rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_SMS, MessagingConstants.ROUTING_SMS, smsMessage);

	return new SmsCodeResult(mobileNumber, result, new Date().getTime());
  }

}
