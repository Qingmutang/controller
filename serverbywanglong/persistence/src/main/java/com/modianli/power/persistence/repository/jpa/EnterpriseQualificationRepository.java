package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.EnterpriseQualification;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dell on 2017/2/25.
 */
public interface EnterpriseQualificationRepository  extends JpaBaseRepository<EnterpriseQualification,Long>,//
                                                            JpaSpecificationExecutor<EnterpriseQualification>,//
                                                            EnterpriseQualificationRepositoryCustom{


    List<EnterpriseQualification> findByEnterprise(@Param("enterprise") Enterprise enterprise);

}
