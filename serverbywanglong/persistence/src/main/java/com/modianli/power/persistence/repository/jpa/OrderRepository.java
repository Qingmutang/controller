/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.PurchaseOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/*
* @author hansy
*/
public interface OrderRepository extends
								 JpaRepository<PurchaseOrder, Long>, //
								 JpaSpecificationExecutor<PurchaseOrder>,//
								 PagingAndSortingRepository<PurchaseOrder, Long> {

  @Query("update PurchaseOrder set status=?2 where id=?1")
  @Modifying
  public void updateStatus(Long id, PurchaseOrder.Status status);

  public PurchaseOrder findBySerialNumber(String posn);

  @Query("update PurchaseOrder set active=?2 where id=?1")
  @Modifying
  public void updateActiveStatus(Long id, boolean b);

  public long countByUserIdAndStatus(Long userId, PurchaseOrder.Status status);

  public Page<PurchaseOrder> findByUserId(Long userId, Pageable page);


//
//  public Page<PurchaseOrder> findByActiveIsTrueAndProductId(Long productId,
//															Pageable page);

}
