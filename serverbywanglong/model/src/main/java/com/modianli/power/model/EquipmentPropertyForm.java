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
public class EquipmentPropertyForm implements Serializable {

  private static final long serialVersionUID = -2529797157385872827L;

  private String name;

  private IdValue equipmentCategoryFK;
}
