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
public class RecruitDetails implements Serializable {

  private static final long serialVersionUID = -1758022918223133090L;

  private Long id;

  private String positionName;

  private String province;

  private String provinceCode;

  private String city;

  private String cityCode;

  private String area;

  private String areaCode;

  private String address;

  private Long categoryId;

  private String categoryText;

  private String description;

  private Integer recruitNum;

  private Integer lowSalary;

  private Integer highSalary;

  private Long experienceId;

  private String experienceText;

  private Boolean active;

  private String uuid;

  private String mobileNumber;

  private String enterpriseImageUrl;

  private String enterpriseName;

  private String enterpriseAddress;

  private String enterpriseUuid;

  private String enterprisePhone;

  private String tags;

}
