package com.modianli.power.api.sitemap;

import com.modianli.power.common.service.SiteMapService;
import com.modianli.power.model.ApiConstants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/4/20.
 */
@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.SITE_MAP)
@Slf4j
@Api(description = "siteMap后端接口")
public class SiteMapController {

  @Inject
  private SiteMapService siteMapService;

  @GetMapping
  @ApiOperation(value = "生成siteMap文件")
  public ResponseEntity<Void> siteMaps(){
    log.debug("start site maps");
    siteMapService.siteMap();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
