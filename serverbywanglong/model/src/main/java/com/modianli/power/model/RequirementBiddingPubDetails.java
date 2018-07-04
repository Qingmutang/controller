package com.modianli.power.model;

import java.io.Serializable;

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
public class RequirementBiddingPubDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String biddingStatus;

  private boolean self = false;//当前用户非发布需求用户

  private EnterpriseDetails enterprise;//配单供应商

  private String payStatus;
}
