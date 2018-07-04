package com.modianli.power.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/22.
 */

public class EnterpriseListForm {

   //企业名字
  private String name;
  //企业类型
    private Integer category;
  //企业等级认证
  private String verifyStatus;
  //企业所在地区
  private String province;
  private String city;
  private String area;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCategory() {
    return category;
  }

  public void setCategory(Integer category) {
    this.category = category;
  }

  public String getVerifyStatus() {
    return verifyStatus;
  }

  public void setVerifyStatus(String verifyStatus) {
    this.verifyStatus = verifyStatus;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }




}
