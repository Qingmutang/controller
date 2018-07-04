package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Requirements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by haijun on 2017/3/2.
 */
public interface RequirementRepository extends JpaRepository<Requirements, Long>,//
										   JpaSpecificationExecutor<Requirements> {


  @Query("update Requirements req set req.status=?2, req.lastModifiedBy.id=?3 where req.requirementUUID=?1")
  @Modifying
  public void updateRequirementByIdAndStatus(String uuid, Requirements.Status status, Long userId);

  @Query("update Requirements req set req.status=?2, req.lastModifiedBy.id=?3, req.shelfDate=?4 where req.id=?1")
  @Modifying
  public void updateRequirementByIdAndStatus(Long id, Requirements.Status status, Long userId, LocalDate shelfDate);

  public Requirements findByRequirementUUIDAndActive(String uuid, boolean active);

  @Transactional
  @Query("update Requirements req set req.pageView=?2 where req.requirementUUID=?1")
  @Modifying
  public void updateRequirementByUUIDIdAndPageView(String uuid, int pageView);

  Page<Requirements> findByActiveOrderByCreatedDateDesc(Boolean active, Pageable pageable);

  @Modifying
  @Query("update Requirements req set req.status = :status where req.biddingEndDate < :now and req.status in :statues")
  void updateRequirementBatch(@Param("now")LocalDate now, @Param("status") Requirements.Status status, @Param("statues")List<Requirements.Status> statuses);

  List<Requirements> findByBiddingEndDateLessThan(LocalDate now);

  @Query("select req from Requirements req where req.biddingEndDate < :now and req.status in :statues")
  List<Requirements> findByBiddingEndDateAndStatus(@Param("now") LocalDate now, @Param("statues") List<Requirements.Status> statuses);
}
