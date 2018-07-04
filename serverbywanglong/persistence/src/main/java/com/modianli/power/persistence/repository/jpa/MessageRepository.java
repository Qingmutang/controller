package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Messages;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dell on 2017/2/28.
 */

public interface MessageRepository extends JpaBaseRepository<Messages,Long>,JpaSpecificationExecutor<Messages> {


}
