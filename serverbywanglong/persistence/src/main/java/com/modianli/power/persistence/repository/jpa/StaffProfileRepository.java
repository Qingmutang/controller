package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.StaffProfile;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StaffProfileRepository extends JpaBaseRepository<StaffProfile, Long>,
	//
												JpaSpecificationExecutor<StaffProfile> {

  public StaffProfile findByUserAccountId(Long id);

}
