package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-2-23.
 */
@Entity
@Table(name = "hot_products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotProduct extends PersistableEntity<Long> {

  @OneToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private HotProductCategory category;

  @Column(name = "sort",length = 20)
  private Integer sort;

  @Column(name = "is_active",length = 20)
  private Boolean active=true;
}
