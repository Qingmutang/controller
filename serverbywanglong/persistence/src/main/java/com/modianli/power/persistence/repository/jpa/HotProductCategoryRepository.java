package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.HotProductCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotProductCategoryRepository extends JpaRepository<HotProductCategory, Long> {
  @Query("select max(e.sort) from  HotProductCategory e where e.type = :type")
  Integer getMaxSortByType(@Param("type") HotProductCategory.Type type);

  List<HotProductCategory> findByActiveAndTypeOrderBySortDesc(boolean active,HotProductCategory.Type type);

  HotProductCategory findByName(String name);
}
