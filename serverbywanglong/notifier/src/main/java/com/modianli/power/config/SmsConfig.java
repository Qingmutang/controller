package com.modianli.power.config;

import com.modianli.power.sms.api.SmsConfiguration;
import com.modianli.power.sms.api.SmsOperations;
import com.modianli.power.sms.impl.AbstractSmsConfigurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SmsConfig extends AbstractSmsConfigurer {

  private final SmsConfiguration smsConfiguration;

  @Bean
  @Override
  public SmsConfiguration smsConfiguration() {
	log.debug("smsConfiguration {} ", smsConfiguration);
	return smsConfiguration;
  }

  @Bean
  @Override
  public SmsOperations smsTemplateBean() {
	return super.smsTemplateBean();
  }

}
