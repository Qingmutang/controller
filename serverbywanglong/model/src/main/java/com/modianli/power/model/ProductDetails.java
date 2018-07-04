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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails implements Serializable {

  private static final long serialVersionUID = -4546782267321119090L;

  private Long id;

  private String name;

  private String pic;

  private String code;

  private Boolean active;

  private BigDecimal defaultPrice;

  private String firstCategoryName;

  private String secondCategoryName;

  private String thirdCategoryName;

  private String unit;


}
