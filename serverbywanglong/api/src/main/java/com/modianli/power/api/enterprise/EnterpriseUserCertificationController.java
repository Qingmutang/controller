package com.modianli.power.api.enterprise;

import com.modianli.power.common.exception.InvalidRequestException;
import com.modianli.power.common.service.UserService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.ApiErrors;
import com.modianli.power.model.UserCertificationForm;
import com.modianli.power.model.UserEnterpriseCertificateForm;
import com.modianli.power.model.UserProfileDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/2/25.
 */
@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT+ApiConstants.URL_USER_CERTIFICATION)
@Slf4j
@Api(description = "企业用户认证管理")
public class EnterpriseUserCertificationController {

  @Inject
  private UserService userService;

  @RequestMapping(value = "confirmUserCertification/{id}",method = RequestMethod.PUT,params = {"action=CERTIFICATE"})
  @ResponseBody
  @ApiOperation(value = "确认用户认证信息")
  public ResponseEntity<Void> confirmUserCertification(@PathVariable("id")Long id) {
    if (log.isDebugEnabled()) {
      log.debug("confirmUserCertification data @ id:{}" ,id);
    }
    userService.confirmUserCertification(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "confirmUserCertification/{id}",method = RequestMethod.PUT,params = {"action=REJECTED"})
  @ResponseBody
  @ApiOperation(value = "拒绝用户认证信息")
  public ResponseEntity<Void> rejectedUserCertification(@PathVariable("id")Long id,String reason) {
    if (log.isDebugEnabled()) {
      log.debug("rejectedUserCertification data @ id:{} @reason:{}" ,reason);
    }
    userService.rejectedUserCertification(id,reason);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "userCertification",method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "查询用户认证信息列表")
  public ResponseEntity<Page<UserProfileDetail>> userCertifications(@PageableDefault(page = 0,
      size = 20, sort = {"id"}
      , direction = Sort.Direction.DESC) Pageable page
      , @RequestBody UserEnterpriseCertificateForm form) {
    if (log.isDebugEnabled()) {
      log.debug("userCertifications data @ form:{}" ,form);
    }
    return new ResponseEntity<>(userService.searchUserProfiles(form,page),HttpStatus.OK);
  }

  @RequestMapping(value = "userCertification/{id}",method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "确认用户认证信息详情")
  public ResponseEntity<UserProfileDetail> userCertification(@PathVariable("id")Long id) {

    if (log.isDebugEnabled()) {
      log.debug("userCertification data @ id:{}" ,id);
    }
    return new ResponseEntity<>(userService.getUserCertification(id),HttpStatus.OK);
  }

  @RequestMapping(value = {"userCertification/{id}"}, method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "修改用户认证信息")
  public ResponseEntity<Void> userInformation(@PathVariable("id")Long id, @RequestBody @Valid UserCertificationForm form, BindingResult errors) {

    if (log.isDebugEnabled()) {
      log.debug("userInformation data @ id:{} form:{}" ,id,form);
    }

    if (errors.hasErrors()) {
      throw new InvalidRequestException(ApiErrors.INVALID_REQUEST, errors);
    }

    userService.saveUserCertification(id,form);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}


