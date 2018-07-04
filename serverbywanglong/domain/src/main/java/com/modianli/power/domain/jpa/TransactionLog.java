/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionLog extends PersistableEntity<Long> {

  public enum Type {
	PAYMENT,//
	REPAYMENT,//
  }

  public enum Status {

	SUCCESS,//
	FAILED, //
	IN_PROGRESS//
  }

  @Column(name = "serial_number")
  private String serialNumber;

  @Column(name = "trade_no")
  private String tradeNo;

  @Enumerated(EnumType.STRING)
  @Column(name = "t_type")
  private Type type = Type.PAYMENT;

  @Enumerated(EnumType.STRING)
  @Column(name = "t_status")
  private Status status = Status.SUCCESS;

  @JoinColumn(name = "order_id")
  @ManyToOne()
  private PurchaseOrder order;

  @Column(name = "fee")
  private BigDecimal fee;

  @JoinColumn(name = "from_user_id")
  @ManyToOne()
  private UserAccount from;

  @JoinColumn(name = "to_user_id")
  @ManyToOne()
  private UserAccount to;

  @Column(name = "transacted_date")
  private LocalDate transactedDate;

  @CreatedDate
  @Column(name = "created_date")
  private LocalDateTime createdDate = LocalDateTime.now();

  @Column(name = "result_json", columnDefinition = "text")
  private String resultJson;
}
