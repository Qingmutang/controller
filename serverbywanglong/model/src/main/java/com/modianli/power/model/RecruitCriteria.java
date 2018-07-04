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
public class RecruitCriteria {

  private String cityCode;

  private Integer lowSalary;

  private Integer highSalary;

  private Long experienceId;

  private Boolean active;

  private String enterpriseUuid;

  private Long categoryId;

  private String positionName;
}
