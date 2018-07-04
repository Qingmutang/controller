package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.CertificateType;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by dell on 2017/2/28.
 */
public interface CertificateTypeRepository extends JpaBaseRepository<CertificateType,Long>, JpaSpecificationExecutor<CertificateType>{

  List<CertificateType> findByActive(Boolean active);

  CertificateType findByCodeAndActive(String code,Boolean active);

  CertificateType findByCode(String code);

}
