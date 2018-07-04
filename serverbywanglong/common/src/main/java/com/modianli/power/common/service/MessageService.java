package com.modianli.power.common.service;

import com.modianli.power.DTOUtils;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.Messages;
import com.modianli.power.model.MessageDetails;
import com.modianli.power.model.MessageForm;
import com.modianli.power.model.MessageSearchForm;
import com.modianli.power.model.enums.MessageType;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;
import com.modianli.power.persistence.repository.jpa.MessageRepository;
import com.modianli.power.persistence.repository.jpa.MessageSpecifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/2/28.
 */
@Service
@Slf4j
public class MessageService {

  @Inject
  private MessageRepository messageRepository;

  @Inject
  private EnterpriseRepository enterpriseRepository;

  @Inject
  private EmailService emailService;

  public MessageDetails saveMessages(MessageForm form) {
	Assert.notNull(form, "form cannot be null");

	log.debug("save MessageForm form {}", form);

	Messages messages = DTOUtils.strictMap(form, Messages.class);
	//如果咨询合作
	if (!StringUtils.hasText(form.getType()) || MessageType.CONSULTING_COOPERATION.name().equals(form.getType())) {
	  Assert.notNull(form.getEnterpriseId(), "enterprise id cannot be null");
	  Enterprise enterprise = enterpriseRepository.findOne(form.getEnterpriseId());
	  if (null == enterprise) {
		throw new ResourceNotFoundException("enterprise can not be found by id " + form.getEnterpriseId());
	  }

	  messages.setEnterprise(enterprise);
	}

	Messages saved = messageRepository.save(messages);

	Boolean flag = emailService.sendMail("1983245871@qq.com", "新咨询", form.toString()+"\r\n"+"请及时跟进！", "2530654946@qq.com","402244278@qq.com");

	if (!flag) {
	  log.warn("邮件发送失败！");
	}

	return DTOUtils.map(saved, MessageDetails.class);
  }

  public Page<MessageDetails> getMessages(MessageSearchForm form, Pageable page) {

	Assert.notNull(form, "form cannot be null");

	log.debug("getMessages form {}", form);

	return DTOUtils.mapPage(messageRepository.findAll(MessageSpecifications.findByParams(form), page), MessageDetails.class);
  }

}
