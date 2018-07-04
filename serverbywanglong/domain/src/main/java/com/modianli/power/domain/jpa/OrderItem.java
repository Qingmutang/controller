package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends PersistableEntity<Long> {

  @ManyToOne
  @JoinColumn(name = "purchase_order_id")
  private PurchaseOrder purchaseOrder;

  @Column(name = "product_id")
  private String productId;

  @Column(name = "is_active")
  private boolean active = true;

  public OrderItem(PurchaseOrder purchaseOrder, String productId){
    this.purchaseOrder = purchaseOrder;
    this.productId = productId;
    this.active = true;
  }
}
