package com.modianli.power.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/5/24.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendEnterpriseMgtForm {
  private String type;
  private Long enterpriseId;

}
