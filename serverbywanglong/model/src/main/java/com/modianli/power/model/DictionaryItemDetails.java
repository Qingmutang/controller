package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DictionaryItemDetails implements Serializable {

  private static final long serialVersionUID = -9158097032593117160L;

  private Long id;

  private String text;

  private String value;

  private Long dictionaryTypeId;

  private Boolean active;

  private Integer sort;


}
