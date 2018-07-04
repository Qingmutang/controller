package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Recruit;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecruitRepository extends JpaBaseRepository<Recruit, Long>,//
										   JpaSpecificationExecutor<Recruit> {

  Recruit findByUuidAndActive(@Param("uuid") String uuid, @Param("active") Boolean active);

  @Query("update Recruit as c set c.active=:active where c.id=:id ")
  @Modifying
  void updateActiveStatus(@Param("id") Long id, @Param("active") Boolean active);

}
