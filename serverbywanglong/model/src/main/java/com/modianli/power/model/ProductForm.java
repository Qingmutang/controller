package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
public class ProductForm implements Serializable {

  private static final long serialVersionUID = -7208238715071582838L;

  private Long categoryId;

  private String name;

  private String code;

  private Boolean active=true;

  private String pic;

  private List<ProductPropertyOptionsForm> optionsForms;

  private BigDecimal defaultPrice;

}
