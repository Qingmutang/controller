package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.EnterpriseQualification;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dell on 2017/3/9.
 */
public class EnterpriseQualificationRepositoryImpl implements EnterpriseQualificationRepositoryCustom{

  @PersistenceContext
  private EntityManager em;

  @Override
  public void batchSaveEnterpriseQualification(List<EnterpriseQualification> enterpriseQualifications) {
	for (int i=0;i<enterpriseQualifications.size();i++){
	  EnterpriseQualification enterpriseQualification = enterpriseQualifications.get(i);
	  enterpriseQualification.setId(null);
	  em.persist(enterpriseQualification);
	  if (i % 30 == 0){
		em.flush();
		em.clear();
	  }
	}
  }

  @Override
  public void batchDeleteEnterpriseQualification(List<EnterpriseQualification> enterpriseQualifications) {
	for (int i=0;i<enterpriseQualifications.size();i++){
	  EnterpriseQualification enterpriseQualification = enterpriseQualifications.get(i);
	  em.remove(em.contains(enterpriseQualification) ? enterpriseQualification : em.merge(enterpriseQualification));
	  if (i % 30 == 0){
		em.flush();
		em.clear();
	  }
	}
  }
}
