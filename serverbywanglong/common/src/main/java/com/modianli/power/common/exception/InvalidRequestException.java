package com.modianli.power.common.exception;

import org.springframework.validation.BindingResult;

public class InvalidRequestException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  BindingResult errors;

  public InvalidRequestException(String message, BindingResult errors) {
	super(message);
	this.errors = errors;
  }

  public BindingResult getErrors() {
	return errors;
  }

  public void setErrors(BindingResult errors) {
	this.errors = errors;
  }

}
