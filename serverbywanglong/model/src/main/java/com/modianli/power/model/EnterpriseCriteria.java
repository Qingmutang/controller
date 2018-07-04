package com.modianli.power.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/23.
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EnterpriseCriteria {
  //姓名
  private String name;
  //企业分类
  private Integer category;
  //是否认证
  private String verifyStatus;
  //地址
  private String province;
  private String provinceCode;
  private String city;
  private String cityCode;

  private Boolean active;


}
