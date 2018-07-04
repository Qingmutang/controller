package com.modianli.power.api.user;

import com.modianli.power.common.captcha.CaptchaService;
import com.modianli.power.common.exception.CaptchaMismatchedException;
import com.modianli.power.common.exception.InvalidRequestException;
import com.modianli.power.common.service.UserService;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.ApiErrors;
import com.modianli.power.model.AuthenticationToken;
import com.modianli.power.model.ResponseMessage;
import com.modianli.power.model.SigninForm;
import com.modianli.power.model.UserPasswordForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = ApiConstants.URI_API)
public class SigninController {

  private static final Logger log = LoggerFactory.getLogger(SigninController.class);

  @Inject
  private AuthenticationManager authenticationManager;

  @Inject
  private UserDetailsService userDetailsService;

  @Inject
  private CaptchaService captchaService;

  @Inject
  private UserService userService;

  public SigninController() {
  }

  @RequestMapping(value = "/password",method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "根据手机号找回密码")
  public ResponseEntity<ResponseMessage> findPassword(@RequestBody @Valid UserPasswordForm fm) {
	if (log.isDebugEnabled()) {
	  log.debug("change password of user@" + fm);
	}

	userService.findPassword(fm);

	return new ResponseEntity<>(new ResponseMessage(ResponseMessage.Type.success, "passwordFound"),
								HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/signin", method = {RequestMethod.POST})
  public AuthenticationToken signin(
	  @RequestBody @Valid SigninForm authenticationRequest,
	  BindingResult errors,
	  HttpServletRequest request) {
	if (log.isDebugEnabled()) {
	  log.debug("signin form  data@" + authenticationRequest);
	}

	if (errors.hasErrors()) {
	  throw new InvalidRequestException(ApiErrors.INVALID_REQUEST, errors);
	}

	if (authenticationRequest.getCaptchaResponse() == null || !captchaService.verifyImgCaptcha(
		authenticationRequest.getCaptchaResponse())) {
	  throw new CaptchaMismatchedException();
	}

	final UserAccount details = (UserAccount) this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

	if (!details.isUser()) {
	  throw new AccessDeniedException("");
	}

	final UsernamePasswordAuthenticationToken token
		= new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
												  authenticationRequest.getPassword());

	final Authentication authentication = this.authenticationManager.authenticate(token);

	SecurityContextHolder.getContext().setAuthentication(authentication);

	final HttpSession session = request.getSession(true);

	session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
						 SecurityContextHolder.getContext());

	final Map<String, Boolean> roles = new HashMap<>();

	details.getAuthorities().stream().forEach((authority) -> {
	  roles.put(authority.toString(), Boolean.TRUE);
	});

	return new AuthenticationToken(details.getUsername(), roles, session.getId());
  }

  @ApiOperation("企业登录api")
  @RequestMapping(value = "/enterprise/signin", method = {RequestMethod.POST})
  public AuthenticationToken enterpriseSignin(
	  @RequestBody @Valid SigninForm authenticationRequest, BindingResult errors, HttpServletRequest request) {

	log.debug("signin form  data@ {} ", authenticationRequest);

	if (errors.hasErrors()) {
	  throw new InvalidRequestException(ApiErrors.INVALID_REQUEST, errors);
	}

	if (authenticationRequest.getCaptchaResponse() == null || !captchaService.verifyImgCaptcha(
		authenticationRequest.getCaptchaResponse())) {
	  throw new CaptchaMismatchedException();
	}

	final UserAccount details = (UserAccount) this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

	if (!details.isEnterprise()) {
	  throw new AccessDeniedException("");
	}

	final UsernamePasswordAuthenticationToken token
		= new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
												  authenticationRequest.getPassword());

	final Authentication authentication = this.authenticationManager.authenticate(token);

	SecurityContextHolder.getContext().setAuthentication(authentication);

	final HttpSession session = request.getSession(true);

	session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

	final Map<String, Boolean> roles = new HashMap<>();

	details.getAuthorities().stream().forEach((authority) -> {
	  roles.put(authority.toString(), Boolean.TRUE);
	});

	return new AuthenticationToken(details.getUsername(), roles, session.getId());
  }
}
