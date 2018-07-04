package com.modianli.power.api.redis;

import com.modianli.power.common.service.RedisService;
import com.modianli.power.model.ApiConstants;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/5/2.
 */
@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_REDIS)
@Slf4j
@Api(description = "redis后端接口")
public class RedisController {

  @Inject
  private RedisService redisService;

  @DeleteMapping("prerenders")
  @ApiOperation(value = "删除所有预渲染")
  public void deletePrerenders(){
    log.debug("start delete prerenders");
    redisService.deleteAll();
  }

  @DeleteMapping("list_page")
  @ApiOperation(value = "删除列表页预渲染")
  public void deleteList(){
    log.debug("start delete list page");
	redisService.deleteList();
  }

}
