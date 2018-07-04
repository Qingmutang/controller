package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.domain.jpa.UserProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserProfileRepository extends JpaBaseRepository<UserProfile, Long>,//
											   JpaSpecificationExecutor<UserProfile> {

  UserProfile findByAccountId(Long id);

  UserProfile findByAccount(UserAccount userAccount);

  Page<UserProfile> findByCertificateStatus(UserProfile.CertificateType type, Pageable page);


}
