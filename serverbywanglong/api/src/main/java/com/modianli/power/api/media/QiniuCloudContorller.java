package com.modianli.power.api.media;

import com.google.common.collect.Maps;

import com.modianli.power.common.integration.QiniuCloudService;
import com.modianli.power.domain.jpa.Resource;
import com.modianli.power.model.ApiConstants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Map;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-2-22.
 */
@RestController
@RequestMapping(value = ApiConstants.URI_API_PUBLIC + ApiConstants.URI_QINIU)
@Slf4j
@Api(description = "七牛云")
public class QiniuCloudContorller {

  @Value("${spring.qiniu.callbackUrl}")
  private String callbackUrl = "";

  @Value("${spring.qiniu.resourceUrl}")
  private String resourceUrl = "";

  private final String callbackBodyType = "application/x-www-form-urlencoded";

  @Inject
  private QiniuCloudService qiniuCloudService;

  @PostMapping(value = "/upload/callback")
  @ApiOperation(value = "七牛回调")
  public ResponseEntity<Map> callback(@RequestBody String body, @RequestParam("key") String key,
									  @RequestHeader("authorization") String authorization) {
    log.info("body {} key {} authorization {} ",body,key,authorization);
	boolean
		validCallback =
		qiniuCloudService.isValidCallback(authorization, callbackUrl, body.getBytes(Charset.forName("UTF-8"))
			, callbackBodyType);
	log.info("validCallback {}", validCallback);


	Resource resource = new Resource(key, false, true, LocalDateTime.now());
	qiniuCloudService.saveResource(resource);

	Map<String, String> result = Maps.newHashMap();
	if (validCallback) {
	  result.put("key", resourceUrl + key);
	} else {
	  log.warn("upload callback fail");
	}
	return new ResponseEntity<Map>(result, HttpStatus.OK);
  }

}
