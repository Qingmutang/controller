package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-2-23.
 */
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product extends AuditableEntity<UserAccount, Long> {

  @Column(name = "name")
  private String name;

  @Column(name = "pic")
  private String pic;

  @Column(name = "code")
  private String code;

  @Column(name = "is_active")
  private Boolean active = true;

  @Column(name = "first_category_id")
  private Long firstCategoryId;

  @Column(name = "second_category_id")
  private Long secondCategoryId;

  @Column(name = "third_category_id")
  private Long thirdCategoryId;

  @Column(name = "default_price")
  private BigDecimal defaultPrice;

  @Column(name = "unit")
  private String unit;

  @Column(name = "uuid")
  private String uuid;


}
