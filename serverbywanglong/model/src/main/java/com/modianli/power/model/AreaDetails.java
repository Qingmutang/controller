package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/28.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaDetails implements Serializable {

  private static final long serialVersionUID = -8078682342083708768L;
  private Long id;
  private String name;
  private String areaCode;

}
