/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.exception;

/**
 * @author
 */
public class PasswordMismatchedException extends RuntimeException {

  public PasswordMismatchedException(String msg) {
    super(msg);
  }

}
