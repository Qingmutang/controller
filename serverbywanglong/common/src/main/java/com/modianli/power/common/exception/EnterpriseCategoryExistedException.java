package com.modianli.power.common.exception;

/**
 * Created by dell on 2017/2/27.
 */
public class EnterpriseCategoryExistedException extends RuntimeException {
  private String name;

  public EnterpriseCategoryExistedException(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
