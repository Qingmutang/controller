package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaBaseRepository<Permission, Long>, PermissionRepositoryCustom {

  public List<Permission> findByActiveIsTrue(Sort sort);

  public List<Permission> findByActiveIsTrue();

  @Query("select p.name from Permission p where p.active=true")
  public List<String> findAllActivePermissionNames();

  public Permission findByName(String name);

  public Page<Permission> findByNameLike(String q, Pageable page);

}
