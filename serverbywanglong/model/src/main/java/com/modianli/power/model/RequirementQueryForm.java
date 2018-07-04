package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by haijun on 2017/3/3.
 * 需求查询表单数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementQueryForm implements Serializable{

  private String requirementUUID;//需求编号

  private String status;//需求状态


  private String categoryType;

  private Long categoryId;

  private String authType;

  private String name;

  private String provinceCode;

  private String areaCode;

  private String cityCode;

}
