package com.modianli.power.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to transport messages back to the client.
 *
 * @author
 */
public class ResponseMessage {

  private Type type;
  private String code;
  private String text;
  /**
   * For input validation, there could be some validation errors for the form
   * fields.
   */
  private List<Error> errors = new ArrayList<>();

  public ResponseMessage() {
  }

  public ResponseMessage(Type type, String code, String text) {
	this.type = type;
	this.code = code;
	this.text = text;
  }

  public ResponseMessage(Type type, String text) {
	this.type = type;
	this.text = text;
  }

  public static ResponseMessage success(String text) {
	return new ResponseMessage(Type.success, text);
  }

  public static ResponseMessage warning(String text) {
	return new ResponseMessage(Type.warning, text);
  }

  public static ResponseMessage danger(String text) {
	return new ResponseMessage(Type.danger, text);
  }

  public static ResponseMessage info(String text) {
	return new ResponseMessage(Type.info, text);
  }

  public String getText() {
	return text;
  }

  public Type getType() {
	return type;
  }

  public List<Error> getErrors() {
	return errors;
  }

  public void setErrors(List<Error> errors) {
	this.errors = errors;
  }

  public void addError(String field, String code, String message) {
	this.errors.add(new Error(field, code, message));
  }

  public enum Type {

	success, warning, danger, info;
  }

  public static class Error {

	private String code;

	private String field;

	private String message;

	public Error(String field, String code, String message) {
	  super();
	  this.field = field;
	  this.code = code;
	  this.message = message;
	}

	public String getCode() {
	  return code;
	}

	public void setCode(String code) {
	  this.code = code;
	}

	public String getField() {
	  return field;
	}

	public void setField(String field) {
	  this.field = field;
	}

	public String getMessage() {
	  return message;
	}

	public void setMessage(String message) {
	  this.message = message;
	}
  }
}
