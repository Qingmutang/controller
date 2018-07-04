package com.modianli.power.common.service;

import com.modianli.power.MessagingConstants;
import com.modianli.power.model.EmailMessage;
import com.modianli.power.model.EmailModel;

import freemarker.template.Configuration;
import freemarker.template.Template;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-4-20.
 */
@Service
@Slf4j
public class EmailService {

  @Inject
  private Configuration configuration;

  @Inject
  private RabbitTemplate rabbitTemplate;

  public Boolean sendResetPasswordEmail(String to, String subject, EmailModel model) {

	try {
	  Template template = configuration.getTemplate("mail/mail.ftl");
	  String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
	  EmailMessage email = new EmailMessage();
	  email.setContent(content);
	  email.setTo(to);
	  email.setSubject(subject);
	  rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_EMAIL, MessagingConstants.ROUTING_EMAIL, email);
	  log.debug("content {} ", content);
	  return true;

	} catch (Exception e) {
	  log.error("发送邮件失败 {}", e.getMessage());
	}
	return false;
  }

  public Boolean sendMail(String to, String subject, String content, String... cc) {
	try {
	  EmailMessage email = new EmailMessage();
	  email.setTo(to);
	  email.setCc(cc);
	  email.setSubject(subject);
	  email.setContent(content);
	  rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_EMAIL, MessagingConstants.ROUTING_EMAIL, email);
	  return true;
	} catch (Exception e) {
	  log.error("发送邮件失败 {}", e.getMessage());
	}
	return false;
  }

}
