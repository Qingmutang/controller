package com.modianli.power.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EnterpriseCriteriaMultiCategories {
  //姓名
  private String name;
  //企业分类
  private List<Long> category;
  //是否认证
  private String verifyStatus;
  //地址
  private String province;
  private String provinceCode;
  private String city;
  private String cityCode;

  private Boolean active;


}
