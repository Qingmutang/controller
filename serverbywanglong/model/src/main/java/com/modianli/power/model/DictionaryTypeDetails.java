package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-7-7.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DictionaryTypeDetails implements Serializable {

  private static final long serialVersionUID = 9178505502488033828L;

  private String code;

  private Boolean active = true;

  private String text;

  private Long id;


}
