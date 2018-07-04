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
public class ProductPropertyOptionsForm implements Serializable {

  private static final long serialVersionUID = 1724036718925019886L;

  private IdValue equipmentPropertyOptionsFK;

  private String equipmentPropertyName;

  private String equipmentPropertyOptionsName;

}
