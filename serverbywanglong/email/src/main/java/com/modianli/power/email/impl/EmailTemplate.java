package com.modianli.power.email.impl;

import com.modianli.power.email.core.EmailMessage;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailTemplate {

  @Autowired
  private JavaMailSenderImpl mailSender;

  @Autowired
  private MailProperties mailProperties;

  public void send(String to, String subject, String message) {
	if (log.isDebugEnabled()) {
	  log.debug("to @" + to);
	  log.debug("subject @" + subject);
	  log.debug("message @" + message);
	}

	try {

	MimeMessagePreparator preparator = new MimeMessagePreparator() {

	  @Override
	  public void prepare(MimeMessage mimeMessage) throws Exception {

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setFrom(new InternetAddress(mailProperties.getProperties().get("mail.from")));
		helper.setTo(new InternetAddress(to));
		helper.setSubject(subject);
		helper.setSentDate(new Date());
		helper.setText(message, true);
	  }
	};

	this.mailSender.send(preparator);
	} catch (MailSendException e) {
	  log.error("mail send fail mail to {} subject {} message {} error msg {}  ", to, subject, message, e.getMessage());
	  throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
	}
  }

  public void send(EmailMessage msg) {
	if (log.isDebugEnabled()) {
	  log.debug("send EmailMessage@" + msg);
	}

	try {

	MimeMessagePreparator preparator = new MimeMessagePreparator() {

	  @Override
	  public void prepare(MimeMessage mimeMessage) throws Exception {

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setFrom(new InternetAddress(mailProperties.getProperties().get("mail.from")));
		helper.setTo(new InternetAddress(msg.getTo()));
		helper.setSubject(msg.getSubject());
		helper.setSentDate(new Date());
		helper.setText(msg.getContent(), true);
		if (null != msg.getCc()) {
		  helper.setCc(msg.getCc());
		}

		if (StringUtils.hasText(msg.getReplyTo())) {
		  helper.setReplyTo(new InternetAddress(msg.getReplyTo()));
		}
	  }
	};

	this.mailSender.send(preparator);
	} catch (Exception e) {
	  log.error("mail send fail mail {} error msg {}  ", msg, e.getMessage());
	  throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
	}
  }

}
