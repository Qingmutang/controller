package com.modianli.power.api.media;

import com.google.common.collect.Maps;

import com.modianli.power.common.integration.QiniuCloudService;
import com.modianli.power.model.ApiConstants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-2-22.
 */
@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_QINIU)
@Slf4j
@Api(value = "七牛后端接口",description = "七牛后端接口")
public class QiniuCloudMgtContorller {

  @Value("${spring.qiniu.callbackUrl}")
  private String callbackUrl = "";

  @Value("${spring.qiniu.resourceUrl}")
  private String resourceUrl = "";

  @Inject
  private QiniuCloudService qiniuCloudService;

  @GetMapping(value = "/upload/token")
  @ResponseBody
  @ApiOperation(value = "获取七牛上传token",notes = "返回token")
  public ResponseEntity<Map<String, String>> getToken() {

	String token = qiniuCloudService.getCallbackUploadToken();

	log.debug("upload token {} ", token);

	Map<String, String> map = Maps.newHashMap();
	map.put("uptoken", token);

	return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @RequestMapping(value = "resource",method = RequestMethod.POST,params = {"action=DEACTIVATED"})
  @ApiOperation(value = "删除七牛的资源",notes = "无返回值")
  public ResponseEntity<Void> invalidPicture(@RequestParam(value = "urls")String[] urls) {
	qiniuCloudService.deleteInvalidPicture(urls);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
