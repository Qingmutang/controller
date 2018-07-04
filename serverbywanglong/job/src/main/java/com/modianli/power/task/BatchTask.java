package com.modianli.power.task;

import com.modianli.power.common.integration.QiniuCloudService;
import com.modianli.power.domain.jpa.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/4/6.
 */
@Component
@Slf4j
public class BatchTask {

  @Inject
  private QiniuCloudService qiniuCloudService;

  @Inject
  private RabbitTemplate rabbitTemplate;

  @Scheduled(cron = "0 0 23 * * ?") // 每天23点执行
  public void scheduler() {
	List<Resource> resources = qiniuCloudService.findCanDeleteResources();

	if (!resources.isEmpty()){
	  qiniuCloudService.batchDeleteFile(resources);
	}
  }
}
