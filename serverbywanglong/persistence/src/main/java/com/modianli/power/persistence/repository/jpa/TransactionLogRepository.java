package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.TransactionLog;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionLogRepository extends JpaBaseRepository<TransactionLog, Long>,
												  JpaSpecificationExecutor<TransactionLog> {

  @Modifying
  @Query("update TransactionLog tl set tl.resultJson = :resultJson where tl.tradeNo = :tradeNo")
  void updateJsonBySN(@Param("tradeNo")String tradeNo, @Param("resultJson")String json);

  TransactionLog findBySerialNumber(String serialNumber);
}
