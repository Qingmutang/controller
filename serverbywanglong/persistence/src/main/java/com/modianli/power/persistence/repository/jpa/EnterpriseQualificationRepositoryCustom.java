package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.EnterpriseQualification;

import java.util.List;

/**
 * Created by dell on 2017/3/9.
 */
public interface EnterpriseQualificationRepositoryCustom {
  void batchSaveEnterpriseQualification(List<EnterpriseQualification> enterpriseQualifications);

  void batchDeleteEnterpriseQualification(List<EnterpriseQualification> enterpriseQualifications);

}
