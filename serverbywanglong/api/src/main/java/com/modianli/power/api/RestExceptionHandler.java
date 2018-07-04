package com.modianli.power.api;

import com.modianli.power.common.exception.CaptchaMismatchedException;
import com.modianli.power.common.exception.CertificateTypeExistedException;
import com.modianli.power.common.exception.EnterpriseExistedException;
import com.modianli.power.common.exception.EnterpriseUserExistedException;
import com.modianli.power.common.exception.IncorrectMailboxFormatException;
import com.modianli.power.common.exception.InvalidRequestException;
import com.modianli.power.common.exception.MobileNumberNotBelongToUserException;
import com.modianli.power.common.exception.OptionsNotInCategoryException;
import com.modianli.power.common.exception.PasswordMismatchedException;
import com.modianli.power.common.exception.ProductExistedException;
import com.modianli.power.common.exception.ProductHasSamePropertyException;
import com.modianli.power.common.exception.RepaymentNotAllowedException;
import com.modianli.power.common.exception.RepeatableTransmissionException;
import com.modianli.power.common.exception.RequirementException;
import com.modianli.power.common.exception.ResourceForbiddenException;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.common.exception.UsernameExistedException;
import com.modianli.power.locks.model.LockExistsException;
import com.modianli.power.locks.model.LockNotHeldException;
import com.modianli.power.locks.model.NoSuchLockException;
import com.modianli.power.model.ApiErrors;
import com.modianli.power.model.ResponseMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import javax.inject.Inject;

/**
 * Called when an exception occurs during request processing. Transforms the exception message into JSON format.
 */
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

  @Inject
  private MessageSource messageSource;

  //    @ExceptionHandler(value = { AuthenticationException.class })
  //    @ResponseBody
  //    public ResponseEntity<AlertMessage> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
  //        if (log.isDebugEnabled()) {
  //            log.debug("handling authentication exception...");
  //        }
  //        return new ResponseEntity<>(new AlertMessage(AlertMessage.Type.danger, ex.getMessage()),
  //                HttpStatus.UNAUTHORIZED);
  //    }
  @ExceptionHandler(value = {ResourceNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest req) {

  }

  @ExceptionHandler(value = {InvalidRequestException.class})
  public ResponseEntity<ResponseMessage> handleInvalidRequestException(InvalidRequestException ex, WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling InvalidRequestException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.INVALID_REQUEST,
		messageSource.getMessage(ApiErrors.INVALID_REQUEST, new String[]{}, null));

	BindingResult result = ex.getErrors();

	List<FieldError> fieldErrors = result.getFieldErrors();

	if (!fieldErrors.isEmpty()) {
	  for (FieldError e : fieldErrors) {
		alert.addError(e.getField(), e.getCode(), e.getDefaultMessage());
	  }
	}

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {UsernameExistedException.class})
  public ResponseEntity<ResponseMessage> handleUsernameExistedException(UsernameExistedException ex, WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling UsernameExistedException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.USERNAME_EXISTED,
		messageSource.getMessage(ApiErrors.USERNAME_EXISTED, new String[]{}, null));

	alert.addError("username", "exist", "username is exist");

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {EnterpriseUserExistedException.class})
  public ResponseEntity<ResponseMessage> handleEnterpriseUserExistedException(EnterpriseUserExistedException ex, WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling EnterpriseUserExistedException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.ENTERPRISE_USER_EXISTED,
		messageSource.getMessage(ApiErrors.ENTERPRISE_USER_EXISTED, new String[]{}, null));

	alert.addError("user", "exist", "enterprise user is exist");

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {CertificateTypeExistedException.class})
  public ResponseEntity<ResponseMessage> handleCertificateTypeExistedException(CertificateTypeExistedException ex, WebRequest req) {
	log.debug("handling {}......","CertificateTypeExistedException");

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.CERTIFICATE_TYPE_EXISTED,
		messageSource.getMessage(ApiErrors.CERTIFICATE_TYPE_EXISTED, new String[]{}, null));

	alert.addError("certificateType", "exist", "certificate type is exist");

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {MobileNumberNotBelongToUserException.class})
  public ResponseEntity<ResponseMessage> handleMobileNumberNotBelongToUserException(MobileNumberNotBelongToUserException ex,
																					WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling MobileNumberNotBelongToUserException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.MOBILE_NUMBER_NOT_BELONG_TO_USER,
		messageSource.getMessage(ApiErrors.MOBILE_NUMBER_NOT_BELONG_TO_USER, new String[]{}, null));

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {EnterpriseExistedException.class})
  public ResponseEntity<ResponseMessage> handleEnterpriseExistedException(EnterpriseExistedException ex,
																		  WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling EnterpriseExistedException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.ENTERPRISENAME_EXISTED,
		messageSource.getMessage(ApiErrors.ENTERPRISENAME_EXISTED, new String[]{}, null));

	alert.addError("enterprisename", "exist", "enterprisename is exist");

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {ProductExistedException.class})
  public ResponseEntity<ResponseMessage> handleProductExistedException(ProductExistedException ex,
																	   WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling EnterpriseExistedException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.PRODUCTNAME_EXISTED,
		messageSource.getMessage(ApiErrors.PRODUCTNAME_EXISTED, new String[]{}, null));

	alert.addError("productname", "exist", "productname is exist");

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {ProductHasSamePropertyException.class})
  public ResponseEntity<ResponseMessage> handleProductHasSamePropertyException(ProductHasSamePropertyException ex,
																			   WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling EnterpriseExistedException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.PRODUCTPROPERTY_EXISTED,
		messageSource.getMessage(ApiErrors.PRODUCTPROPERTY_EXISTED, new String[]{}, null));

	alert.addError("productproperty", "exist", "productproperty is exist");

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {OptionsNotInCategoryException.class})
  public ResponseEntity<ResponseMessage> handleOptionsNotInCategoryException(OptionsNotInCategoryException ex,
																			 WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling EnterpriseExistedException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.OPTIONS_NOT_IN_CATEGORY,
		messageSource.getMessage(ApiErrors.OPTIONS_NOT_IN_CATEGORY, new String[]{}, null));

	alert.addError("category", "exist", "exist option not in category");

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {IncorrectMailboxFormatException.class})
  public ResponseEntity<ResponseMessage> handleIncorrectMailboxFormatException(IncorrectMailboxFormatException ex,
																			 WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling IncorrectMailboxFormatException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.INCORRECT_MAILBOX_FORMAT,
		messageSource.getMessage(ApiErrors.INCORRECT_MAILBOX_FORMAT, new String[]{}, null));

	alert.addError("email", "format", "incorrect email format");

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {IllegalArgumentException.class})
  public ResponseEntity<ResponseMessage> handleSysIllegalArgumentException(IllegalArgumentException ex,
																		   WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling SysIllegalArgumentException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.ILLEGALARGUMENT,
		messageSource.getMessage(ApiErrors.ILLEGALARGUMENT, new String[]{}, null));

	alert.addError("illegalArgument", "illegalArgument", ex.getMessage());

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {CaptchaMismatchedException.class})
  public ResponseEntity<ResponseMessage> handleCaptchaMismatchedException(CaptchaMismatchedException ex,
																		  WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling CaptchaMismatchedException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.CAPTCHA_MISMATCHED,
		messageSource.getMessage(ApiErrors.CAPTCHA_MISMATCHED, new String[]{}, null));

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {RepeatableTransmissionException.class})
  public ResponseEntity<ResponseMessage> handleNoRepeatableTransmissionException(RepeatableTransmissionException ex,
																		  WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling CaptchaMismatchedException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.REPEATABLE_TRANSMISSION,
		messageSource.getMessage(ApiErrors.REPEATABLE_TRANSMISSION, new String[]{}, null));

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {RepaymentNotAllowedException.class})
  public ResponseEntity<ResponseMessage> handleRepaymentNotAllowedException(RepaymentNotAllowedException ex,
																			WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling RepaymentNotAllowedException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.REPAYMENT_NOT_ALLOWED,
		messageSource.getMessage(ApiErrors.REPAYMENT_NOT_ALLOWED, new String[]{}, null));

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {PasswordMismatchedException.class})
  public ResponseEntity<ResponseMessage> handlePasswordMismatchedException(PasswordMismatchedException ex,
																			WebRequest req) {
	if (log.isDebugEnabled()) {
	  log.debug("handling RepaymentNotAllowedException...");
	}

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.INVALID_PASSWORD,
		messageSource.getMessage(ApiErrors.INVALID_PASSWORD, new String[]{}, null));

	return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(LockExistsException.class)
  public ResponseEntity<ResponseMessage> lockExists() {

	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.LOCK_EXISTS_EXCEPTION,
		messageSource.getMessage(ApiErrors.LOCK_EXISTS_EXCEPTION, new String[]{}, null));

	return new ResponseEntity<>(alert, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoSuchLockException.class)
  public ResponseEntity<ResponseMessage> noSuchLock() {
	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.NO_SUCH_LOCK_EXCEPTION,
		messageSource.getMessage(ApiErrors.NO_SUCH_LOCK_EXCEPTION, new String[]{}, null));
	return new ResponseEntity<>(alert, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(LockNotHeldException.class)
  public ResponseEntity<ResponseMessage> lockNotHeld() {
	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.LOCK_NOT_HELD_EXCEPTION,
		messageSource.getMessage(ApiErrors.LOCK_NOT_HELD_EXCEPTION, new String[]{}, null));
	return new ResponseEntity<>(alert, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ResponseMessage> dataIntegrityViolationException() {
	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.DATA_INTEGRITY_VIOLATION_EXCEPTION,
		messageSource.getMessage(ApiErrors.DATA_INTEGRITY_VIOLATION_EXCEPTION, new String[]{}, null));
	return new ResponseEntity<>(alert, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ResourceForbiddenException.class)
  public ResponseEntity<ResponseMessage> resourceForbiddenException() {
	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.RESOURCE_FORBIDDEN_EXCEPTION,
		messageSource.getMessage(ApiErrors.RESOURCE_FORBIDDEN_EXCEPTION, new String[]{}, null));
	return new ResponseEntity<>(alert, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(RequirementException.class)
  public ResponseEntity<ResponseMessage> handleRequirementException(RequirementException re, WebRequest req){
    log.debug("handle requirement exception.");
	ResponseMessage alert = new ResponseMessage(
		ResponseMessage.Type.danger,
		ApiErrors.REQUIREMENT_EXCEPTION,
		messageSource.getMessage(ApiErrors.REQUIREMENT_EXCEPTION, new String[]{re.getMessage()}, null));

	return new ResponseEntity<>(alert, HttpStatus.NOT_FOUND);
  }
}
