package com.modianli.power.common.exception;

/**
 * Created by dell on 2017/2/27.
 */
public class EnterpriseBaseInfoExistedException extends RuntimeException{
  private static final long serialVersionUID = 1L;
  private String enterpriseBaseInfoName;

  public EnterpriseBaseInfoExistedException(String enterpriseBaseInfoName) {
    super(enterpriseBaseInfoName);
    this.enterpriseBaseInfoName = enterpriseBaseInfoName;
  }

  public String getEnterpriseBaseInfoName() {
    return enterpriseBaseInfoName;
  }

  public void setEnterpriseBaseInfoName(String enterpriseBaseInfoName) {
    this.enterpriseBaseInfoName = enterpriseBaseInfoName;
  }
}
