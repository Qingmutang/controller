/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.common.exception;

/**
 * @author
 */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
	super(message);
  }

  public ResourceNotFoundException(Long id) {
	super("hotel not found by id:" + id);
  }

}
