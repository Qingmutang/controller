/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author hansy
 */
public class OrderDetails implements Serializable {

  private Long id;

  private BigDecimal amount;

  private String status;

  private boolean active;

  private String productType;

  private String serialNumber;

  private BigDecimal accruedInterestAmount;

  private LocalDateTime accruedStartDate;

  private LocalDateTime accruedEndDate;

  private LocalDateTime completedDate;

  private LocalDateTime paidDate;

  private LocalDateTime placedDate;

  private NameValue user;

  private String borrowerCustId;

  public BigDecimal getAmount() {
	return amount;
  }

  public void setAmount(BigDecimal amount) {
	this.amount = amount;
  }

  public String getStatus() {
	return status;
  }

  public void setStatus(String status) {
	this.status = status;
  }

  public LocalDateTime getPlacedDate() {
	return placedDate;
  }

  public void setPlacedDate(LocalDateTime placedDate) {
	this.placedDate = placedDate;
  }

  public NameValue getUser() {
	return user;
  }

  public void setUser(NameValue user) {
	this.user = user;
  }

  public String getSerialNumber() {
	return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
	this.serialNumber = serialNumber;
  }

  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public String getProductType() {
	return productType;
  }

  public void setProductType(String productType) {
	this.productType = productType;
  }

  public boolean isActive() {
	return active;
  }

  public void setActive(boolean active) {
	this.active = active;
  }

  public BigDecimal getAccruedInterestAmount() {
	return accruedInterestAmount;
  }

  public void setAccruedInterestAmount(BigDecimal accruedInterestAmount) {
	this.accruedInterestAmount = accruedInterestAmount;
  }

  public LocalDateTime getAccruedStartDate() {
	return accruedStartDate;
  }

  public void setAccruedStartDate(LocalDateTime accruedStartDate) {
	this.accruedStartDate = accruedStartDate;
  }

  public LocalDateTime getAccruedEndDate() {
	return accruedEndDate;
  }

  public void setAccruedEndDate(LocalDateTime accruedEndDate) {
	this.accruedEndDate = accruedEndDate;
  }

  public LocalDateTime getCompletedDate() {
	return completedDate;
  }

  public void setCompletedDate(LocalDateTime completedDate) {
	this.completedDate = completedDate;
  }

  public LocalDateTime getPaidDate() {
	return paidDate;
  }

  public void setPaidDate(LocalDateTime paidDate) {
	this.paidDate = paidDate;
  }

  public String getBorrowerCustId() {
	return borrowerCustId;
  }

  public void setBorrowerCustId(String borrowerCustId) {
	this.borrowerCustId = borrowerCustId;
  }



}
