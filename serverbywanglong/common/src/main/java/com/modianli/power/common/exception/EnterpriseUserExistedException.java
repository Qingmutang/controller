/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.exception;

/**
 * @author
 */
public class EnterpriseUserExistedException extends RuntimeException {

  private String message;

  public EnterpriseUserExistedException(String message) {
	this.message = message;
  }

  public String getMessage() {
	return message;
  }

  public void setMessage(String message) {
	this.message = message;
  }

}
