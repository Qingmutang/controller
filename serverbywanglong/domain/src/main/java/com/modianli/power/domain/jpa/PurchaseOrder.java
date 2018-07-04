/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "purchase_orders")
@Data
@AllArgsConstructor
@Builder
public class PurchaseOrder extends PersistableEntity<Long> {

  public enum Status {
	//NA,// default status, when is created
	PENDING_PAYMENT,// The order is placed, and notify user to pay.
	PAID,//payment is successful
	PAYMENT_FAILED,//payment is failed
	REPAYMENT_COMPLETED, // copy the completed status.
	CANCELED// canceled by user
  }

  public enum Type{
	REQUIREMENT
  }

  @Column(name = "serial_number")
  private String serialNumber;

//    @JoinColumn(name = "product_id")
//    @ManyToOne
//    private Product product;

  @Enumerated(EnumType.STRING)
  @Column(name = "order_type")
  public Type orderType;

  private BigDecimal amount = BigDecimal.ZERO;

  @Enumerated(EnumType.STRING)
  private Status status;

  private boolean active = true;

  @Column(name = "paid_date")
  private LocalDateTime paidDate;

  @JoinColumn(name = "user_id")
  @ManyToOne
  private UserAccount user;

  @Column(name = "placed_date")
  private LocalDateTime placedDate;

  public PurchaseOrder() {
	this.placedDate = LocalDateTime.now();
  }


}
