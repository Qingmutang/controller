/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.exception;

/**
 * @author
 */
public class UsernameExistedException extends RuntimeException {

  private String username;

  public UsernameExistedException(String username) {
	this.username = username;
  }

  public String getUsername() {
	return username;
  }

  public void setUsername(String username) {
	this.username = username;
  }

}
