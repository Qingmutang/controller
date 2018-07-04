package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.UserCertification;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dell on 2017/2/27.
 */
public interface UserCertificationRepository extends JpaBaseRepository<UserCertification,Long>, JpaSpecificationExecutor<UserCertification> {

}
