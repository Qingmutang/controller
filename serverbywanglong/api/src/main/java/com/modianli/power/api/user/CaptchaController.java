/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.api.user;

import com.modianli.power.common.captcha.CaptchaService;
import com.modianli.power.common.service.SmsService;
import com.modianli.power.common.service.UserService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.ImgCaptchaRequest;
import com.modianli.power.model.ImgCaptchaResult;
import com.modianli.power.model.SmsCaptchaRequest;
import com.modianli.power.model.SmsCaptchaResult;
import com.modianli.power.model.SmsCodeResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

@RequestMapping(value = ApiConstants.URI_API_PUBLIC)
@RestController
public class CaptchaController {

  private static final Logger log = LoggerFactory.getLogger(CaptchaController.class);

  @Inject
  private UserService userService;

  @Inject
  private CaptchaService captchaService;

  @Inject
  private SmsService smsService;

  @RequestMapping(value = {"/captcha/img"}, method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Map<String, Boolean>> verifyImgCaptcha(@RequestBody ImgCaptchaRequest form) {
	if (log.isDebugEnabled()) {
	  log.debug("img captcha data@" + form);
	}

	boolean result = captchaService.verifyImgCaptcha(form);

	Map<String, Boolean> res = new HashMap<>();
	res.put("result", result);

	return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @RequestMapping(value = {"/captcha/img"}, method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<ImgCaptchaResult> getImgCaptcha() {
	if (log.isDebugEnabled()) {
	  log.debug("retrieving img captcha code @");
	}

	ImgCaptchaResult img = captchaService.generateCaptcha();

	return new ResponseEntity<>(img, HttpStatus.OK);
  }

  @RequestMapping(value = {"/captcha/sms"}, method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<SmsCodeResult> getSmsCaptcha(@RequestParam("mobile") String mobile,
													 @RequestParam("key") String key,
													 @RequestParam("responseValue") String responseValue,
													 @RequestParam(value = "userId", required = false) Long userId) {
	if (log.isDebugEnabled()) {
	  log.debug("signup: retrieving sms code @" + mobile + ", userId @" + userId);
	}
	ImgCaptchaRequest form = new ImgCaptchaRequest();
	form.setKey(key);
	form.setResponseValue(responseValue);

	SmsCodeResult sms = smsService.generateSmsCode(mobile, userId, form);
	sms.setResult(null);

	if (log.isDebugEnabled()) {
	  log.debug("sms code result @" + sms);
	}

	return new ResponseEntity<>(sms, HttpStatus.OK);
  }

  @RequestMapping(value = {"/captcha/sms"}, method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<SmsCaptchaResult> validateSmsCode(@RequestBody SmsCaptchaRequest form) {

	log.debug("signup: retrieving sms code @ {} ", form);

	boolean result = smsService.validate(form.getMobileNumber(), form.getCaptchaValue());

	log.debug("sms validation result @ {} ", result);

	return new ResponseEntity<>(new SmsCaptchaResult(result), HttpStatus.OK);
  }


}
