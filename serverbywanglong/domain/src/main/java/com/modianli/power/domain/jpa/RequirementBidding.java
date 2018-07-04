package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by haijun on 2017/3/3.
 */
@Entity
@Table(name = "requirement_bidding")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequirementBidding extends PersistableEntity<Long> {

  public enum BiddingStatus{
    NO_RECEIVE,//未接单
    RECEIVED,//接单
    BID//中标
  }

  public enum PayStatus{
    PAID,
    NO_PAID
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "bidding_status")
  private BiddingStatus biddingStatus = BiddingStatus.NO_RECEIVE;//是否中标 0:未接单； 1：接单； 2：中标

  @Column(name = "price")
  private BigDecimal price;//供应商报价

  @Column(name = "mark", length = 500)
  private String mark;//报价备注

  @Column(name = "attach_url")
  private String attachUrl;//报价附件

  @ManyToOne
  @JoinColumn(name = "bidding_enterprise_id")
  private Enterprise enterprise;//配单供应商

  @ManyToOne
  @JoinColumn(name = "requirement_id")
  private Requirements requirement;//被竞标的需求信息

  @Column(name = "is_active")
  private boolean active = true;

  @Column(name = "uuid")
  private String uuid;//配单号

  @Enumerated(EnumType.STRING)
  @Column(name = "pay_status")
  private PayStatus payStatus = PayStatus.NO_PAID;
}
