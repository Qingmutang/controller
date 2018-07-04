package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.RequirementBidding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by haijun on 2017/3/3.
 */
public interface RequirementBiddingRepository extends JpaRepository<RequirementBidding, Long>,//
												  JpaSpecificationExecutor<RequirementBidding> {

  @Query("update RequirementBidding req set req.biddingStatus=?2 where req.uuid=?1")
  @Modifying
  public void updateRequirementBiddingByUuidsAndStatus(String biddingUuid, RequirementBidding.BiddingStatus biddingStatus);

  @Query("update RequirementBidding req set req.price=?2, req.mark = ?3, req.attachUrl = ?4, req.biddingStatus=?5 where req.uuid=?1")
  @Modifying
  public void updateRequirementBiddingPriceByIds(String biddingUuid, BigDecimal price, String mark, String attachUrl, RequirementBidding.BiddingStatus biddingStatus );

  public RequirementBidding findByUuidAndActive(String uuid, boolean active);

  @Modifying
  @Query("update RequirementBidding req set req.payStatus = :payStatus where req.uuid = :uuid")
  void updateRequirementBiddingByPaystatus(@Param("payStatus") RequirementBidding.PayStatus payStatus, @Param("uuid")String uuid);

  @Query("select rb from RequirementBidding rb where rb.requirement.id = :requirementId and rb.enterprise.id = :enterpriseId")
  RequirementBidding findByRequirementAndEnterprise(@Param("requirementId")Long requirementId, @Param("enterpriseId")Long enterpriseId);

  @Modifying
  @Query("update RequirementBidding req set req.active = :active where req.requirement.id = :id")
  void updateRequirementBiddingByRequirementAndActive(@Param("active") boolean active, @Param("id") Long id);

  @Modifying
  @Query("update RequirementBidding req set req.active = :active where req.requirement.id in :ids")
  void updateRequirementBiddingByRequirementAndActive(@Param("active") boolean active, @Param("ids") List<Long> id);
}
