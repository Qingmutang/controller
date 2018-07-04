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
public class ProvinceDetails implements Serializable{

  private static final long serialVersionUID = -829534455575289819L;
  private Long id;
  private String name;
  private String areaCode;

}
