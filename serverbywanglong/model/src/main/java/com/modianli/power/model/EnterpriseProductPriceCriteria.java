package com.modianli.power.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/23.
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EnterpriseProductPriceCriteria {
  Long productId;

  Long enterpriseId;

  BigDecimal price;
}
