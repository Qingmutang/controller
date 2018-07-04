package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.EnterpriseCategory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dell on 2017/3/9.
 */
public class EnterpriseCategoryRepositoryImpl implements EnterpriseCategoryRepositoryCustom{
  @PersistenceContext
  private EntityManager em;

  @Override
  public void batchSaveEnterpriseCategory(List<EnterpriseCategory> enterpriseCategories) {
	for (int i=0;i<enterpriseCategories.size();i++){
	  EnterpriseCategory enterpriseCategory = enterpriseCategories.get(i);
	  enterpriseCategory.setId(null);
	  em.persist(enterpriseCategory);
	  if (i % 30 == 0){
		em.flush();
		em.clear();
	  }
	}
  }

  @Override
  public void batchDeleteEnterpriseCategory(List<EnterpriseCategory> enterpriseCategories) {
	for (int i=0;i<enterpriseCategories.size();i++){
	  EnterpriseCategory enterpriseCategory = enterpriseCategories.get(i);
	  em.remove(em.contains(enterpriseCategory) ? enterpriseCategory : em.merge(enterpriseCategory));
	  if (i % 30 == 0){
		em.flush();
		em.clear();
	  }
	}
  }
}
