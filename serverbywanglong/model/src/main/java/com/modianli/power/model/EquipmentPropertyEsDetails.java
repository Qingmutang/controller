package com.modianli.power.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/3/13.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentPropertyEsDetails implements Serializable{

  private Long id;

  private String name;

  private String code;

  List<EquipmentPropertyOptionsEsDetails> propertyOptionsEs;
}
