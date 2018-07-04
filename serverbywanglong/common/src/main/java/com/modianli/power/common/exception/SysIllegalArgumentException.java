package com.modianli.power.common.exception;

public class SysIllegalArgumentException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String illegalArgument;

  public SysIllegalArgumentException(String illegalArgument) {
	this.illegalArgument = illegalArgument;
  }

  public String getIllegalArgument() {
	return illegalArgument;
  }

  public void setIllegalArgument(String illegalArgument) {
	this.illegalArgument = illegalArgument;
  }

}
