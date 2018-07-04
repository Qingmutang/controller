package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.EnterpriseCategory;

import java.util.List;

/**
 * Created by dell on 2017/3/9.
 */
public interface EnterpriseCategoryRepositoryCustom {
  void batchSaveEnterpriseCategory(List<EnterpriseCategory> enterpriseCategories);

  void batchDeleteEnterpriseCategory(List<EnterpriseCategory> enterpriseCategories);


}
