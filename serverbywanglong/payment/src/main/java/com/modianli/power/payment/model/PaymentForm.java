package com.modianli.power.payment.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-5-16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentForm {

  private String serialNumber;

  private String subject;

  private BigDecimal totalAmount;

  private String subjectDescription;

  private String type;
}
