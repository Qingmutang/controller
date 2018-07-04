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
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotProductCategoryTypeDetails implements Serializable{
  private String type;

  private List<HotProductCategoryDetails> hotProductCategoryDetails;
}
