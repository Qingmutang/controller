package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-6-28.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecruitForm implements Serializable {

  private static final long serialVersionUID = 6988609574088812089L;

  private String positionName;

  private String province;

  private String provinceCode;

  private String city;

  private String cityCode;

  private String area;

  private String areaCode;

  private String address;

  private IdValue categoryFK;

  private String description;

  private Integer recruitNum;

  private Integer lowSalary;

  private Integer highSalary;

  private IdValue experienceFK;

  private String tags;

  private String mobileNumber;


}
