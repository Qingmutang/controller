package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.HotProduct;
import com.modianli.power.domain.jpa.HotProductCategory;
import com.modianli.power.domain.jpa.Product;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotProductRepository extends JpaRepository<HotProduct, Long> {

  HotProduct findByProductAndCategory(Product product, HotProductCategory category);

  @Query("select max(e.sort) from  HotProduct e where e.category =:category")
  Integer getMaxSort(@Param("category") HotProductCategory category);

  List<HotProduct> findByActive(Boolean active, Sort sort);

  List<HotProduct> findByCategoryOrderBySortDesc(HotProductCategory category);

  List<HotProduct> findTop6ByCategoryAndActiveOrderBySortDesc(HotProductCategory category,Boolean active);

  List<HotProduct> findTop3ByCategoryAndActiveOrderBySortDesc(HotProductCategory category,Boolean active);
}
