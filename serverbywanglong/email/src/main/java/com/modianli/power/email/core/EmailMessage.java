/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.email.core;

import java.io.Serializable;

public class EmailMessage implements Serializable {

  private String from;
  private String to;
  private String subject;
  private String content;
  private String replyTo;
  private String[] cc;

  public String[] getCc() {
	return cc;
  }

  public void setCc(String[] cc) {
	this.cc = cc;
  }

  public String getFrom() {
	return from;
  }

  public void setFrom(String from) {
	this.from = from;
  }

  public String getTo() {
	return to;
  }

  public void setTo(String to) {
	this.to = to;
  }

  public String getSubject() {
	return subject;
  }

  public void setSubject(String subject) {
	this.subject = subject;
  }

  public String getContent() {
	return content;
  }

  public void setContent(String content) {
	this.content = content;
  }

  public String getReplyTo() {
	return replyTo;
  }

  public void setReplyTo(String replyTo) {
	this.replyTo = replyTo;
  }

  @Override
  public String toString() {
	return "EmailMessage{" +
		   "from='" + from + '\'' +
		   ", to='" + to + '\'' +
		   ", subject='" + subject + '\'' +
		   ", content='" + content + '\'' +
		   ", replyTo='" + replyTo + '\'' +
		   '}';
  }
}
