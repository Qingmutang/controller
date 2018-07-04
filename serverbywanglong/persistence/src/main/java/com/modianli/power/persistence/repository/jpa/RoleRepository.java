package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Role;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaBaseRepository<Role, Long>, JpaSpecificationExecutor<Role> {

  public Role findByName(String code);

  public List<Role> findByActiveIsTrue();

  @Query("update Role set active=?2 where id=?1")
  @Modifying
  public void updateActiveStatus(Long id, boolean b);

}
