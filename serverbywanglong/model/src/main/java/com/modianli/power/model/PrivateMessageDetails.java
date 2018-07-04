package com.modianli.power.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author
 */
public class PrivateMessageDetails implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private Long id;

  private String title;

  private String content;

  private String type;

  private NameValue to;

  private NameValue from;

  private LocalDateTime createdDate;

  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public String getTitle() {
	return title;
  }

  public void setTitle(String title) {
	this.title = title;
  }

  public String getContent() {
	return content;
  }

  public void setContent(String content) {
	this.content = content;
  }

  public String getType() {
	return type;
  }

  public void setType(String type) {
	this.type = type;
  }

  public NameValue getFrom() {
	return from;
  }

  public void setFrom(NameValue from) {
	this.from = from;
  }

  public NameValue getTo() {
	return to;
  }

  public void setTo(NameValue to) {
	this.to = to;
  }

  public LocalDateTime getCreatedDate() {
	return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
	this.createdDate = createdDate;
  }

  @Override
  public String toString() {
	return "MessageDetails{" + "id=" + id + ", title=" + title + ", content=" + content + ", type=" + type + ", to=" + to
		   + ", from=" + from + ", createdDate=" + createdDate + '}';
  }

}
