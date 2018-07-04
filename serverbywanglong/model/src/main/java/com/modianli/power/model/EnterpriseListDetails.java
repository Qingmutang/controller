package com.modianli.power.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/22.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseListDetails {

  private Long id;
  private String uuid;
  //企业名称
  private String name;
  //公司logo地址
  private String imageUrl;
  //企业法人
  private String legalRepresentative;
  //成立日期
  private LocalDate foundDate;
  //注册资本
  private BigDecimal registeredCapital;
  //认证类型
  private String certificateType;
  //公司地址
  private String province;
  private String provinceCode;
  private String city;
  private String cityCode;
  private String area;
  private String areaCode;
  //公司电话
  private String phone;
  private String businessPhone;
  private String enterpriseAddress;
  private Boolean active;
  private String username;
}
