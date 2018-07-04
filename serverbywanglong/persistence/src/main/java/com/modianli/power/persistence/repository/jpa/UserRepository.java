package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.UserAccount;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaBaseRepository<UserAccount, Long>,//
										JpaSpecificationExecutor<UserAccount> {

  public UserAccount findByUsername(String username);

  public UserAccount findByMobileNumber(String mobileNumber);

  public UserAccount findByEmail(String email);

  @Modifying
  @Query("update UserAccount user set user.active=:active where user.id=:id")
  public void updateActiveStatus(@Param("id") Long id, @Param("active") boolean active);

  @Modifying
  @Query("update UserAccount user set user.locked=:locked where user.id=:id")
  public void updateLockedStatus(@Param("id") Long id, @Param("locked") boolean locked);

}
