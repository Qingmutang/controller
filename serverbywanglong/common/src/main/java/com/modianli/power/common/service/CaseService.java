package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import com.modianli.power.DTOUtils;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.domain.jpa.CasePictures;
import com.modianli.power.domain.jpa.Cases;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.model.CaseDetails;
import com.modianli.power.model.CaseForm;
import com.modianli.power.model.CasePubDetails;
import com.modianli.power.model.CaseSearchForm;
import com.modianli.power.persistence.repository.jpa.CasePicturesRepository;
import com.modianli.power.persistence.repository.jpa.CaseSpecifications;
import com.modianli.power.persistence.repository.jpa.CasesRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-4-20.
 */
@Service
@Slf4j
public class CaseService {

  @Inject
  private CasesRepository casesRepository;

  @Inject
  private CasePicturesRepository casePicturesRepository;

  @Inject
  private EnterpriseRepository enterpriseRepository;

  @Inject
  private RedisService redisService;

  /**
   * 添加企业案例
   */
  @Transactional
  public void saveCase(CaseForm caseForm, Long id) {
	Assert.notNull(id, "enterprise id can not be null");
	Assert.notNull(caseForm, "case form id can not be null");

	log.debug("save cases @{}", caseForm);

	Enterprise enterprise = enterpriseRepository.findOne(id);
	if (null == enterprise) {
	  throw new ResourceNotFoundException("enterprise can not be found by id" + id);
	}

	Cases cases = DTOUtils.map(caseForm, Cases.class);
	cases.setEnterprise(enterprise);
	Cases saved = casesRepository.save(cases);

	//添加图片
	addCaseUrls(caseForm.getUrls(), saved);

	redisService.deleteEnterpriseCase(enterprise.getUuid());
  }

  /**
   * 删除案例
   */
  @Transactional
  public void deleteCase(Long id) {
	Assert.notNull(id, "case id can not be null");

	log.debug("delete cases id @{}", id);

	Cases cases = casesRepository.findOne(id);
	if (null == cases) {
	  throw new ResourceNotFoundException("case can not be found by id" + id);
	}

	cases.setActive(false);
	casesRepository.save(cases);

	if (cases.getEnterprise() != null) {
	  redisService.deleteEnterpriseCase(cases.getEnterprise().getUuid());
	}
  }

  /**
   * 恢复案例
   */
  @Transactional
  public void restoreCase(Long id) {
	Assert.notNull(id, "case id can not be null");

	log.debug("restore cases id @{}", id);

	Cases cases = casesRepository.findOne(id);
	if (null == cases) {
	  throw new ResourceNotFoundException("case can not be found by id" + id);
	}
	cases.setActive(true);
	casesRepository.save(cases);

	if (cases.getEnterprise() != null) {
	  redisService.deleteEnterpriseCase(cases.getEnterprise().getUuid());
	}
  }

  /**
   * 修改案例
   */
  @Transactional
  public void updateCase(Long id, CaseForm caseForm) {
	Assert.notNull(id, "case id can not be null");
	Assert.notNull(caseForm, "case form id can not be null");

	log.debug("update cases @{} id @{}", caseForm, id);
	Cases cases = casesRepository.findOne(id);

	if (null == cases) {
	  throw new ResourceNotFoundException("case can not be found by id" + id);
	}

	//清除这个案例下所有图片
	List<CasePictures> pictures = casePicturesRepository.findByCasesAndActive(cases, true);
	pictures.stream().forEach(picture -> {
	  picture.setActive(false);
	  casePicturesRepository.save(picture);
	});

	//添加图片
	addCaseUrls(caseForm.getUrls(), cases);

	cases.setProjectName(caseForm.getProjectName());
	cases.setProjectDescription(caseForm.getProjectDescription());
	cases.setProjectTime(caseForm.getProjectTime());

	casesRepository.save(cases);

	if (cases.getEnterprise() != null) {
	  redisService.deleteEnterpriseCase(cases.getEnterprise().getUuid());
	}
  }

  /**
   * 查询案例
   */
  @Transactional(readOnly = true)
  public Page<CaseDetails> searchCase(CaseSearchForm form, Pageable page) {
	Page<Cases> cases = casesRepository.findAll(CaseSpecifications.searchCase(form), page);

	List<CaseDetails> caseDetailsList = Lists.newArrayList();

	if (cases.getTotalElements() > 0 && !cases.getContent().isEmpty()) {
	  caseDetailsList = cases.getContent().stream().map(this::addCaseUrls).collect(Collectors.toList());
	}
	return new PageImpl<>(caseDetailsList, new PageRequest(cases.getNumber(), cases.getSize(), cases.getSort()),
						  cases.getTotalElements());
  }

  @Transactional(readOnly = true)
  public CaseDetails findOne(Long id) {
	Assert.notNull(id, "id  cannot  be null");
	log.debug(" find case by id {}  ", id);
	Cases cases = casesRepository.findOne(id);
	if (cases == null) {
	  throw new ResourceNotFoundException("case cannot be found by id " + id);
	}
	return this.addCaseUrls(cases);
  }

  /**
   * 查询案例
   */
  @Transactional(readOnly = true)
  public List<CasePubDetails> searchPubCase(String uuid) {
	Assert.hasText(uuid, "enterprise id can not be null");

	Enterprise enterprise = enterpriseRepository.findByUuid(uuid);
	if (null == enterprise) {
	  throw new ResourceNotFoundException("enterprise can not be found by uuid" + uuid);
	}
	List<Cases> cases = casesRepository.getCasesByEnterprise(enterprise);

	List<String> yearList = cases.stream().map((Cases c) -> {
	  return c.getProjectTime().format(DateTimeFormatter.ofPattern("yyyy"));
	}).sorted(Comparator.reverseOrder()).distinct().collect(Collectors.toList());

	List<CasePubDetails> caseDetailsList = yearList.stream().map(y -> {

	  LocalDate startDate = LocalDate.of(Integer.valueOf(y), 1, 1);
	  LocalDate endDate = LocalDate.of(Integer.valueOf(y), 12, 31);
	  List<CaseDetails> filterCases = cases.stream().filter(
		  c ->
			  c.getProjectTime().isAfter(startDate) && c.getProjectTime().isBefore(endDate)
	  ).map(this::addCaseUrls).sorted(Comparator.comparing(CaseDetails::getProjectTime).reversed()).collect(Collectors.toList());

	  CasePubDetails details = new CasePubDetails();
	  details.setYear(y);
	  details.setCases(filterCases);
	  return details;
	}).collect(Collectors.toList());

	return caseDetailsList;
  }

  /**
   * 添加案例图片
   */
  private void addCaseUrls(List<String> urls, Cases cases) {
	//添加案例图片
	if (urls.isEmpty()) {
	  return;
	}

	urls.stream().forEach(url -> {
	  CasePictures casePictures = new CasePictures();
	  casePictures.setUrl(url);
	  casePictures.setCases(cases);
	  casePicturesRepository.save(casePictures);
	});
  }

  private CaseDetails addCaseUrls(Cases c) {
	List<String> urls = Lists.newArrayList();
	List<CasePictures> pictures = casePicturesRepository.findByCasesAndActive(c, true);
	if (pictures != null && !pictures.isEmpty()) {
	  urls = pictures.stream().map(cp -> cp.getUrl()).collect(Collectors.toList());
	}
	CaseDetails caseDetails = DTOUtils.map(c, CaseDetails.class);
	caseDetails.setUrls(urls);
	return caseDetails;
  }
}
