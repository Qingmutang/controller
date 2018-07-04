package com.modianli.power.model;

import java.io.Serializable;
import java.util.List;

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
public class EquipmentFirstCategoryDetails implements Serializable {

  private Long id;

  private String name;

  private String introduction;

  private String code;

  List<EquipmentHomeSecondCategoryDetails> secondCategoryDetails;

}
