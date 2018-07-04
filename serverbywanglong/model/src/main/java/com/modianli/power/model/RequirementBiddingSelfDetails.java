package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by haijun on 2017/4/18.
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementBiddingSelfDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String biddingStatus;

  private BigDecimal price;//供应商报价

  private String mark;//报价备注

  private String attachUrl;//附件地址

  private boolean self = true;//当前用户是发布需求用户

  private EnterpriseDetails enterprise;//配单供应商

  private String payStatus;

  private boolean active;
}
