package com.modianli.power.api.enterprise;

import com.modianli.power.common.service.EnterpriseQualificationService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.QualificationTopDetails;
import com.modianli.power.model.QualificationTopForm;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/2/25.
 */
@RestController
@RequestMapping(value = ApiConstants.URI_API_PUBLIC+ApiConstants.URL_QUALIFICATION)
@Slf4j
@Api(description = "企业认证管理")
public class EnterpriseQualificationController {
  @Inject
  private EnterpriseQualificationService enterpriseQualificationService;

  @RequestMapping(method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "创建一级认证类型")
  public ResponseEntity<Void> createTopQualification(@RequestBody QualificationTopForm form,
      UriComponentsBuilder uriComponentsBuilder) {

    if (log.isDebugEnabled()) {
      log.debug("save qualificationTop data {]" , form);
    }

    QualificationTopDetails qualificationTopDetail=enterpriseQualificationService.saveTopCategory(form);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(uriComponentsBuilder
        .path(ApiConstants.URI_ENTERPRISE + "/{id}")
        .buildAndExpand(qualificationTopDetail.getId()).toUri());

    return new ResponseEntity<>(headers,HttpStatus.CREATED);
  }


  }


