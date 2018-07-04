package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.EnterpriseCategory;
import com.modianli.power.domain.jpa.IndustryCategory;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dell on 2017/2/27.
 */
public interface EnterpriseCategoryRepository extends JpaBaseRepository<EnterpriseCategory,Long>,JpaSpecificationExecutor<EnterpriseCategory>,EnterpriseCategoryRepositoryCustom {

  EnterpriseCategory findByIndustryCategory(@Param("industryCategory")IndustryCategory industryCategory);

  List<EnterpriseCategory> findByEnterprise(@Param("enterprise")Enterprise enterprise);




}
