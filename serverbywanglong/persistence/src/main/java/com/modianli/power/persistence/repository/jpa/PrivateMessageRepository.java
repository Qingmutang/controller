package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.PrivateMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long>, //
												  JpaSpecificationExecutor<PrivateMessage> {

  @Modifying
  @Query("update PrivateMessage set read=?2 where to.id =?1 and read=false")
  void batchUpdateReadStatus(Long userId);

}
