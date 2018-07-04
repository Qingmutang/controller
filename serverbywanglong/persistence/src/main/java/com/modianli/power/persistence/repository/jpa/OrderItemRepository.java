package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends
									 JpaRepository<OrderItem, Long>, //
									 JpaSpecificationExecutor<OrderItem>,//
									 PagingAndSortingRepository<OrderItem, Long> {

  @Query("select oi from OrderItem oi where oi.purchaseOrder.id = :id")
  public List<OrderItem> findByPurchaseOrder(@Param("id")Long id);

  List<OrderItem> findByProductIdAndActive(String requirementBiddingUUID, boolean active);

  @Modifying
  @Query("update OrderItem oi set oi.active = false where oi.productId =?1")
  void updateActiveByUUID(String requirementBiddingUUID);
}
