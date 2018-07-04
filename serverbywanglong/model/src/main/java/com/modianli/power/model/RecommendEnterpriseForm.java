package com.modianli.power.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/5/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendEnterpriseForm {

  private String name;
  private String type;
  private Boolean active;
}
