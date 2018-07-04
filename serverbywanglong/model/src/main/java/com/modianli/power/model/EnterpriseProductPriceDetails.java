package com.modianli.power.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/24.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EnterpriseProductPriceDetails {
  private Long id;

  private String name;

  private BigDecimal price;

  private Boolean active;

  private String enterpriseUUID;

  private Long enterpriseId;

  private String contracts;

  private String pic;

  private String enterpriseAddress;

  private String businessPhone;


}
