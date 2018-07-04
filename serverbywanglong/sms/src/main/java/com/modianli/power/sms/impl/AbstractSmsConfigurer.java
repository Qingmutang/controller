package com.modianli.power.sms.impl;

import com.modianli.power.sms.api.SmsConfigurer;
import com.modianli.power.sms.api.SmsOperations;

public abstract class AbstractSmsConfigurer implements SmsConfigurer {

  @Override
  public SmsOperations smsTemplate() {
	return new SmsTemplate(this.smsConfiguration());
  }

  /**
   * Override this method and add a @Bean annotation to expose it as a spring
   * managed bean.
   */
  public SmsOperations smsTemplateBean() {
	return this.smsTemplate();
  }
}
