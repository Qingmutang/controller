package com.modianli.power.api.task;

import com.modianli.power.common.service.SiteMapService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/4/21.
 */
@Component
@Slf4j
public class SiteMapTask {

  @Inject
  private SiteMapService siteMapService;

  @Scheduled(cron = "0 0 0/2 * * ?") // 每2小时执行
  public void scheduler() {
    siteMapService.siteMap();
  }
}
