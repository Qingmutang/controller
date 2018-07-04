package com.modianli.power.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by haijun on 2017/3/9.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementBiddingForm implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  private BigDecimal price;

  private String mark;

  private String attachUrl;

  public String getAttachUrl() {
    return attachUrl;
  }

  public void setAttachUrl(String attachUrl) {
    this.attachUrl = attachUrl;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getMark() {
    return mark;
  }

  public void setMark(String mark) {
    this.mark = mark;
  }
}
