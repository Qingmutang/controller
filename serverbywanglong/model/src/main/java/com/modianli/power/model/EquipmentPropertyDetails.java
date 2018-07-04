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
public class EquipmentPropertyDetails implements Serializable {

  private static final long serialVersionUID = 5412829721075285447L;

  private Long id;

  private String name;

  private List<EquipmentPropertyOptionsDetails> optionsDetails;


}
