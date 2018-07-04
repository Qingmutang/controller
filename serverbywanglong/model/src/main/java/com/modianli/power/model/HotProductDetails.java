package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class HotProductDetails implements Serializable{
  private Long id;

  private String name;

  private String pic;

  private String code;

  private Long firstCategoryId;

  private Long secondCategoryId;

  private Long thirdCategoryId;

  private BigDecimal defaultPrice;

  private String unit;

  private Boolean active;

  private String uuid;

  private Long priceCount;


}
