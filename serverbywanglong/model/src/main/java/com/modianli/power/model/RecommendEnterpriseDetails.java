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
public class RecommendEnterpriseDetails {

  private Long id;
  private String name;
  private Boolean active;
  private String url;
}
