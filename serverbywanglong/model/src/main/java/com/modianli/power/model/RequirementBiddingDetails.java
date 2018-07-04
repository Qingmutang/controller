package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by haijun on 2017/3/6.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementBiddingDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private String biddingStatus;

  private BigDecimal price;//供应商报价

  private String mark;//报价备注

  private String attachUrl;//附件地址

  private RequirementPartDetails requirement;//被竞标的需求信息

  private String uuid;

  private String payStatus;
}
