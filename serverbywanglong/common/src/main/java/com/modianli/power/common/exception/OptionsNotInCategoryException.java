package com.modianli.power.common.exception;

public class OptionsNotInCategoryException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String message;

  public OptionsNotInCategoryException(String message) {
    super(message);
	this.message = message;
  }

  @Override
  public String getMessage() {
	return message;
  }

  public void setMessage(String message) {
	this.message = message;
  }
}
