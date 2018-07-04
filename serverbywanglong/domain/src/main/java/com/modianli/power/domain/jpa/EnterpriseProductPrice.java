package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-2-23.
 */
@Entity
@Table(name = "product_prices")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EnterpriseProductPrice extends PersistableEntity<Long> {

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "price_date")
  private LocalDateTime priceDate;

  @Column(name = "is_active")
  private Boolean active=true;
}
