package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.EnterpriseProduceCategory;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnterpriseProduceCategoryRepository
	extends JpaBaseRepository<EnterpriseProduceCategory, Long>, JpaSpecificationExecutor<EnterpriseProduceCategory> {

  @Query("select epc.dictionaryItem.id from EnterpriseProduceCategory as epc where epc.active=true and epc.enterprise=:enterprise")
  List<Long> findActiveItemIdByEnterprise(@Param("enterprise") Enterprise enterprise);

  @Query("delete from EnterpriseProduceCategory as epc where epc.dictionaryItem.id=:itemId and epc.enterprise=:enterprise")
  @Modifying
  void deleteEnterpriseProduceCategories(@Param("itemId") Long id,@Param("enterprise")Enterprise enterprise);
}
