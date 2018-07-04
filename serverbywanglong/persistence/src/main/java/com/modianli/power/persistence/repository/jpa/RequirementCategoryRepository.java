package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.RequirementCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by haijun on 2017/6/5.
 */
public interface RequirementCategoryRepository extends JpaRepository<RequirementCategory, Long>,//
												   JpaSpecificationExecutor<RequirementCategory> {

  @Query("select e from RequirementCategory e where e.requirementId = :requirementId")
  List<RequirementCategory> findByRequirementId(@Param("requirementId") Long requirementId);

  @Modifying
  @Query("delete from RequirementCategory e where e.requirementId = :requirementId")
  void deleteByRequirementId(@Param("requirementId") Long requirementId);
}
