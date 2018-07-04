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
public class HotProductCategoryDetails implements Serializable{
  private Long id;

  private String name;

  private String url;

  private Boolean active;

  private Integer sort;

  private List<HotProductDetails> productDetails;
}
