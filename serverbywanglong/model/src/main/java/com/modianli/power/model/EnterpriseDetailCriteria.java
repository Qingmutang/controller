package com.modianli.power.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnterpriseDetailCriteria {
  private Long id;
  private String uid;

}
