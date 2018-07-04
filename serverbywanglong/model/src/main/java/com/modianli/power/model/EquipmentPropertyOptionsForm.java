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
public class EquipmentPropertyOptionsForm implements Serializable {

  private static final long serialVersionUID = 4105594910918039809L;

  private String value;

  private IdValue equipmentPropertyFK;
}
