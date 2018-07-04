package com.modianli.power.api.user;

import com.modianli.power.common.service.UserService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.SystemUserDetails;
import com.modianli.power.model.SystemUserForm;
import com.modianli.power.model.UserAccountDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_ACCOUNTS)
@Slf4j
public class AccountMgtController {

  @Inject
  private PasswordEncoder passwordEncoder;

  @Inject
  private UserService userService;

  @RequestMapping(value = {""}, method = RequestMethod.GET)
  @ResponseBody
  public Page<SystemUserDetails> allUsers(
	  @RequestParam("q") String q,
	  @RequestParam("role") String role,
	  @RequestParam("active") String active,
	  @PageableDefault(value = 10) Pageable page) {
	if (log.isDebugEnabled()) {
	  log.debug("fetch all users...@ q:" + q + ", role:" + role + ", page:" + page);
	}

	Page<SystemUserDetails> users = userService.findUsers(q, role, active, null, page);

	if (log.isDebugEnabled()) {
	  log.debug("count of users @" + users.getTotalElements());
	  log.debug(" users @" + users.getContent());
	}

	return users;
  }

  @RequestMapping(value = {""}, method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Void> createUser(@RequestBody SystemUserForm form, UriComponentsBuilder uriComponentsBuilder) {
	if (log.isDebugEnabled()) {
	  log.debug("save user data @" + form);
	}

	UserAccountDetails saved = userService.saveUser(form);

	HttpHeaders headers = new HttpHeaders();
	headers.setLocation(uriComponentsBuilder.path(ApiConstants.URI_API_PUBLIC + ApiConstants.URI_USERS + "/{id}")
											.buildAndExpand(saved.getId()).toUri());

	return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
  @ResponseBody
  public ResponseEntity<Void> updateUser(@PathVariable("id") Long id, @RequestBody SystemUserForm form) {
	if (log.isDebugEnabled()) {
	  log.debug("udpate user @" + id + " data @" + form);
	}

	userService.updateUser(id, form);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<SystemUserDetails> getUser(@PathVariable("id") Long id) {

	if (log.isDebugEnabled()) {
	  log.debug("get user data by id @" + id);
	}

	SystemUserDetails user = userService.findUserById(id);

	return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {

	if (log.isDebugEnabled()) {
	  log.debug("soft delete user @" + id + ", deactivate user not delete it");
	}

	userService.deactivateUser(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, params = {"action=UNLOCK"})
  @ResponseBody
  public ResponseEntity<Void> activateUser(@PathVariable("id") Long id) {

	if (log.isDebugEnabled()) {
	  log.debug("soft delete user @" + id + ", deactivate user, not really delete it from database");
	}
	userService.activateUser(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
