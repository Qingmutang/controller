package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-2-23.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentThirdCategoryDetails implements Serializable {

  private static final long serialVersionUID = -4948013221415936239L;

  private Long id;

  private String name;

  private String introduction;

  private String code;

  private Boolean choosed=false;

}
