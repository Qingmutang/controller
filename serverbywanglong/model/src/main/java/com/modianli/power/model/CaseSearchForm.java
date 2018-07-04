package com.modianli.power.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/5/15.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseSearchForm {

  private String projectName;

  private Boolean active;

  private Long enterpriseId;

}
