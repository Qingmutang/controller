package com.modianli.power.common.exception;

public class ProductExistedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String productName;

  public ProductExistedException(String productName) {
    super(productName);
	this.productName = productName;
  }

  public String getProductName() {
	return productName;
  }

  public void setProductName(String productName) {
	this.productName = productName;
  }
}
