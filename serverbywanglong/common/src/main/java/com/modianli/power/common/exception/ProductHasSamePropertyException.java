package com.modianli.power.common.exception;

public class ProductHasSamePropertyException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String propertyName;

  public ProductHasSamePropertyException(String propertyName) {
    super(propertyName);
	this.propertyName = propertyName;
  }

  public String getPropertyName() {
	return propertyName;
  }

  public void setPropertyName(String propertyName) {
	this.propertyName = propertyName;
  }
}
