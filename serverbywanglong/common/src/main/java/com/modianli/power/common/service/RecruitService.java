package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import com.modianli.power.DTOUtils;
import com.modianli.power.common.exception.ResourceForbiddenException;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.common.utils.UUIDUtils;
import com.modianli.power.domain.jpa.DictionaryItem;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.Recruit;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.RecruitCriteria;
import com.modianli.power.model.RecruitDetails;
import com.modianli.power.model.RecruitForm;
import com.modianli.power.model.RecruitListDetails;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;
import com.modianli.power.persistence.repository.jpa.RecruitRepository;
import com.modianli.power.persistence.repository.jpa.RecruitSpecifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-6-28.
 */
@Slf4j
@Service
@Transactional
public class RecruitService {

  @Inject
  private RecruitRepository recruitRepository;

  @Inject
  private EnterpriseRepository enterpriseRepository;

  public RecruitDetails saveRecruit(RecruitForm form, UserAccount userAccount) {
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getCategoryFK(), "categoryFK cannot be null");
	Assert.notNull(form.getCategoryFK().getId(), "categoryFK.getId() cannot be null");
	Assert.notNull(form.getExperienceFK(), "experienceFK cannot be null");
	Assert.notNull(form.getExperienceFK().getId(), "experienceFK.getId() cannot be null");

	Assert.notNull(userAccount, "userAccount cannot be null");

	log.info("saveRecruit in => ");
	log.debug(" form {} , userAccount {}", form, userAccount);
	Recruit recruit = DTOUtils.strictNotNullMap(form, Recruit.class);
	recruit.setUserAccount(userAccount);
	recruit.setUuid(UUIDUtils.getShortUUID());

	DictionaryItem categoryItem = new DictionaryItem();
	categoryItem.setId(form.getCategoryFK().getId());

	DictionaryItem experienceItem = new DictionaryItem();
	experienceItem.setId(form.getExperienceFK().getId());

	recruit.setCategory(categoryItem);
	recruit.setExperience(experienceItem);

	Recruit saved = recruitRepository.save(recruit);

	log.debug("saved {} ", saved);

	log.info("saveRecruit out =>");

	return DTOUtils.map(saved, RecruitDetails.class);
  }

  @Transactional(readOnly = true)
  public Page<RecruitListDetails> findRecruits(RecruitCriteria criteria, Pageable page) {

	Assert.notNull(criteria, "criteria cannot be null");

	log.info(" findFrontRecruits  in  => ");

	log.debug("findFrontRecruits criteria {} , page {} ", criteria, page);

	Page<Recruit> recruits
		= recruitRepository.findAll(RecruitSpecifications.searchRecruits(criteria), page);

	List<RecruitListDetails> recruitListDetailsList = Lists.newArrayList();

	if (!recruits.getContent().isEmpty()) {
	  List<Recruit> recruitList = recruits.getContent();
	  recruitListDetailsList = recruitList.stream().map(r -> {
		RecruitListDetails details = DTOUtils.map(r, RecruitListDetails.class);
		if (r.getUserAccount() != null) {
		  Optional<Enterprise> enterprise = enterpriseRepository.findByUserAccount(r.getUserAccount());
		  enterprise.ifPresent(e -> {
			details.setEnterpriseName(e.getName());
			details.setEnterpriseUuid(e.getUuid());
		  });
		}
		return details;
	  }).collect(Collectors.toList());
	}
	log.info(" findFrontRecruits  out  <= ");
	return new PageImpl<RecruitListDetails>(recruitListDetailsList, page, recruits.getTotalElements());
  }

  @Transactional(readOnly = true)
  public RecruitDetails findOneRecruit(String uuid) {
	Assert.hasText(uuid, "uuid cannot be null");
	log.info("findOneRecruit in =>");
	log.debug("uuid {} ", uuid);

	Recruit recruit = recruitRepository.findByUuidAndActive(uuid, true);

	if (null == recruit) {
	  throw new ResourceNotFoundException(" recruit can't find by uuid :" + uuid);
	}

	RecruitDetails details = getRecruitDetails(recruit);

	log.info("findOneRecruit out =>");

	return details;
  }

  @Transactional(readOnly = true)
  public RecruitDetails findOneRecruit(Long id) {
	Assert.notNull(id, "uuid cannot be null");
	log.info("findOneRecruit in =>");
	log.debug("uuid {} ", id);

	Recruit recruit = recruitRepository.findOne(id);

	if (null == recruit) {
	  throw new ResourceNotFoundException(" recruit can't find by uuid :" + id);
	}
	RecruitDetails details = getRecruitDetails(recruit);

	log.info("findOneRecruit out =>");

	return details;
  }

  private RecruitDetails getRecruitDetails(Recruit recruit) {
	RecruitDetails details = DTOUtils.map(recruit, RecruitDetails.class);

	if (recruit.getUserAccount() != null) {
	  Optional<Enterprise> enterprise = enterpriseRepository.findByUserAccount(recruit.getUserAccount());
	  enterprise.ifPresent(e -> {
		details.setEnterpriseName(e.getName());
		details.setEnterpriseImageUrl(e.getImageUrl());
		details.setEnterpriseAddress(e.getEnterpriseAddress());
		details.setEnterpriseUuid(e.getUuid());
		details.setEnterprisePhone(e.getPhone());
	  });
	}
	return details;
  }

  public RecruitDetails updateRecruit(RecruitForm form, UserAccount userAccount, Long id) {
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getCategoryFK(), "categoryFK cannot be null");
	Assert.notNull(form.getCategoryFK().getId(), "categoryFK.getId() cannot be null");
	Assert.notNull(form.getExperienceFK(), "experienceFK cannot be null");
	Assert.notNull(form.getExperienceFK().getId(), "experienceFK.getId() cannot be null");
	Assert.notNull(userAccount, "userAccount cannot be null");

	log.info("updateRecruit in => ");
	log.debug(" form {} , userAccount {}", form, userAccount);

	Recruit recruit = recruitRepository.findOne(id);

	if (null == recruit) {
	  throw new ResourceNotFoundException(" recruit can't find by id :" + id);
	}

	if (recruit.getUserAccount() != null && !userAccount.getId().equals(recruit.getUserAccount().getId())) {
	  throw new ResourceForbiddenException("not current user recruit");
	}

	DTOUtils.strictMapTo(form, recruit);

	DictionaryItem categoryItem = new DictionaryItem();
	categoryItem.setId(form.getCategoryFK().getId());

	DictionaryItem experienceItem = new DictionaryItem();
	experienceItem.setId(form.getExperienceFK().getId());

	recruit.setCategory(categoryItem);
	recruit.setExperience(experienceItem);


	Recruit saved = recruitRepository.save(recruit);

	log.debug("saved {} ", saved);

	log.info("updateRecruit out =>");

	return DTOUtils.map(saved, RecruitDetails.class);
  }

  public void deactivateRecruit(Long id) {
	Assert.notNull(id, "Recruit id can not be null");
	recruitRepository.updateActiveStatus(id, false);
  }

  public void activateRecruit(Long id) {
	Assert.notNull(id, "Recruit id can not be null");
	recruitRepository.updateActiveStatus(id, true);
  }

}
