package com.modianli.power.api.user;

import com.modianli.power.api.security.CurrentUser;
import com.modianli.power.common.exception.InvalidRequestException;
import com.modianli.power.common.service.UserService;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.ApiErrors;
import com.modianli.power.model.LongValue;
import com.modianli.power.model.PasswordForm;
import com.modianli.power.model.PrivateMessageDetails;
import com.modianli.power.model.ProfileForm;
import com.modianli.power.model.ResponseMessage;
import com.modianli.power.model.UpdateMobileNumberForm;
import com.modianli.power.model.UserAccountDetails;
import com.modianli.power.model.UserCertificationForm;

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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = ApiConstants.URI_API + ApiConstants.URI_CURRENT_USER)
@Slf4j
public class CurrentUserController {

  @Inject
  private UserService userService;

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public UserAccountDetails currentUser(@CurrentUser UserAccount user) {
	log.debug("get current user info@" + user);

	UserAccountDetails info = userService.findUserByUsername(user.getUsername());

	return info;
  }

  @RequestMapping(method = RequestMethod.PUT, params = "action=CHANGE_PWD")
  @ResponseBody
  public ResponseEntity<ResponseMessage> changePassword(@RequestBody @Valid PasswordForm fm, BindingResult errors,
														@CurrentUser UserAccount u) {
	if (log.isDebugEnabled()) {
	  log.debug("change password of user@" + fm);
	}

	if (errors.hasErrors()) {
	  throw new InvalidRequestException(ApiErrors.INVALID_PASSWORD, errors);
	}

	userService.updatePassword(u.getId(), fm);

	return new ResponseEntity<>(new ResponseMessage(ResponseMessage.Type.success, "passwordUpdated"),
								HttpStatus.NO_CONTENT);
  }

  @RequestMapping(method = RequestMethod.PUT, params = "action=UPDATE_PROFILE")
  @ResponseBody
  public ResponseEntity<ResponseMessage> updateProfile(@RequestBody ProfileForm u, @CurrentUser UserAccount su) {
	if (log.isDebugEnabled()) {
	  log.debug("update user profile data @" + u);
	}

	userService.updateUserProfile(u, su);

	return new ResponseEntity<>(new ResponseMessage(ResponseMessage.Type.success, "profileUpdated"),
								HttpStatus.NO_CONTENT);
  }

  @RequestMapping(method = RequestMethod.PUT, params = "action=UPDATE_MOBILE_NUMBER")
  @ResponseBody
  public ResponseEntity<Void> updateMobileNumber(@RequestBody @Valid UpdateMobileNumberForm u, BindingResult errors,
												 @CurrentUser UserAccount su) {
	if (log.isDebugEnabled()) {
	  log.debug("update user mobile number data @" + u);
	}

	if (errors.hasErrors()) {
	  throw new InvalidRequestException(ApiErrors.INVALID_REQUEST, errors);
	}

	userService.updateMobileNumber(u, su);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/userCertificate"}, method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Void> completeUserInformation(@CurrentUser UserAccount userAccount,
													  @RequestBody @Valid UserCertificationForm form, BindingResult errors) {

	if (log.isDebugEnabled()) {
	  log.debug("completeUserInformation data @ {}", form);
	}

	if (errors.hasErrors()) {
	  throw new InvalidRequestException(ApiErrors.INVALID_REQUEST, errors);
	}

	userService.saveUserCertification(userAccount, form);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "messages", method = RequestMethod.GET, params = "f=RECEIVED")
  @ResponseBody
  public ResponseEntity<Page<PrivateMessageDetails>> getReceivedMessages(//
																		 @CurrentUser UserAccount user, //
																		 @PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable page) {
	if (log.isDebugEnabled()) {
	  log.debug("get received messages of @" + user);
	}

	Page<PrivateMessageDetails> messages = userService.getReceivedMessages(user.getId(), null, page);

	return new ResponseEntity<>(messages, HttpStatus.OK);
  }

  @RequestMapping(value = "messages", method = RequestMethod.GET, params = "f=SENT")
  @ResponseBody
  public ResponseEntity<Page<PrivateMessageDetails>> getSentMessages(//
																	 @CurrentUser UserAccount user, //
																	 @PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable page) {
	if (log.isDebugEnabled()) {
	  log.debug("get received messages of @" + user);
	}

	Page<PrivateMessageDetails> messages = userService.getSentMessages(user.getId(), page);

	return new ResponseEntity<>(messages, HttpStatus.OK);
  }

  @RequestMapping(value = "messages/{id}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<PrivateMessageDetails> getMessage(
	  @PathVariable("id") Long id) {
	if (log.isDebugEnabled()) {
	  log.debug("getMessage of @" + id);
	}

	PrivateMessageDetails msg = userService.getMessage(id);

	return new ResponseEntity<>(msg, HttpStatus.OK);
  }

  @RequestMapping(value = "messages/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseEntity<Void> deleteMessage(
	  @PathVariable("id") Long id) {
	if (log.isDebugEnabled()) {
	  log.debug("deleteMessage @" + id);
	}

	userService.deleteMessage(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "messages", method = RequestMethod.PUT, params = "MARK_READ")
  @ResponseBody
  public ResponseEntity<Void> markAllMessagesRead(//
												  @CurrentUser UserAccount user) {
	if (log.isDebugEnabled()) {
	  log.debug("mark unread messages as read of @" + user);
	}

	userService.markAllMessagesAsRead(user.getId());

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "messages/count", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<LongValue> getUnreadReceivedMessages(//
															 @CurrentUser UserAccount user) {
	if (log.isDebugEnabled()) {
	  log.debug("get received messages of @" + user);
	}

	long cnt = userService.countUnreadMessages(user.getId());

	return new ResponseEntity<>(new LongValue(cnt), HttpStatus.OK);
  }

}
