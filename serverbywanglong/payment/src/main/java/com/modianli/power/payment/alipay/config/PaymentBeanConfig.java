package com.modianli.power.payment.alipay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Created by gao on 17-5-18.
 */
@Configuration
public class PaymentBeanConfig {

  @Data
  @Component
  @ConfigurationProperties(prefix = "power.payment.alipay")
  public static class AlipayConfigDetails{
    private String partner;
    private String privateKey;
    private String notifyUrl;
    private String returnUrl;
  }

}
