/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hantsy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderForm implements Serializable {

  private String orderType;

  private BigDecimal amount;

  private String productId;

  public OrderForm(BigDecimal amount, String orderType, String productId) {
	this.amount = amount;
	this.orderType = orderType;
	this.productId = productId;
  }
}
