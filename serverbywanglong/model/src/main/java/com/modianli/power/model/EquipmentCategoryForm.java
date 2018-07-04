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
public class EquipmentCategoryForm implements Serializable {

  private static final long serialVersionUID = -8951090724964695132L;

  private String name;

  private String introduction;

  private IdValue equipmentCategoryFK;
}
