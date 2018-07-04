package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import com.modianli.power.DTOUtils;
import com.modianli.power.MessagingConstants;
import com.modianli.power.RedisKeyConstants;
import com.modianli.power.common.exception.CertificateTypeExistedException;
import com.modianli.power.common.exception.EnterpriseExistedException;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.domain.jpa.CertificateType;
import com.modianli.power.domain.jpa.Comments;
import com.modianli.power.domain.jpa.DictionaryItem;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.EnterpriseCategory;
import com.modianli.power.domain.jpa.EnterpriseLicensePictures;
import com.modianli.power.domain.jpa.EnterpriseProduceCategory;
import com.modianli.power.domain.jpa.EnterpriseQualification;
import com.modianli.power.domain.jpa.IndustryCategory;
import com.modianli.power.domain.jpa.QualificationLastCategory;
import com.modianli.power.domain.jpa.QualificationMiddleCategory;
import com.modianli.power.domain.jpa.QualificationTopCategory;
import com.modianli.power.domain.jpa.RecommendEnterprise;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.domain.jpa.UserProfile;
import com.modianli.power.model.CertificateTypeDetails;
import com.modianli.power.model.CertificateTypeForm;
import com.modianli.power.model.CommentDetails;
import com.modianli.power.model.CommentForm;
import com.modianli.power.model.EnterpriseCount;
import com.modianli.power.model.EnterpriseCriteria;
import com.modianli.power.model.EnterpriseCriteriaMultiCategories;
import com.modianli.power.model.EnterpriseDetails;
import com.modianli.power.model.EnterpriseForm;
import com.modianli.power.model.EnterpriseLicensePicturesDetails;
import com.modianli.power.model.EnterpriseListDetails;
import com.modianli.power.model.EnterpriseQualificationDetails;
import com.modianli.power.model.EnterpriseQualificationForm;
import com.modianli.power.model.IdValue;
import com.modianli.power.model.RecommendEnterpriseDetails;
import com.modianli.power.model.RecommendEnterpriseForm;
import com.modianli.power.model.RecommendEnterpriseMgtForm;
import com.modianli.power.model.RecommendEnterprisePubDetails;
import com.modianli.power.model.RecommendEnterpriseSortForm;
import com.modianli.power.model.enums.EnterpriseEvent;
import com.modianli.power.persistence.repository.jpa.CertificateTypeRepository;
import com.modianli.power.persistence.repository.jpa.CommentsRepository;
import com.modianli.power.persistence.repository.jpa.DictionaryItemRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseCategoryRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseLicensePicturesRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseProduceCategoryRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseQualificationRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseSpecifications;
import com.modianli.power.persistence.repository.jpa.IndustryCategoryRepository;
import com.modianli.power.persistence.repository.jpa.QualificationLastCategoryRepository;
import com.modianli.power.persistence.repository.jpa.RecommendEnterpriseRepository;
import com.modianli.power.persistence.repository.jpa.RecommendEnterpriseSpecifications;
import com.modianli.power.persistence.repository.jpa.UserProfileRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/2/23.
 */
@Service
@Slf4j
public class EnterpriseService {

  @Inject
  private EnterpriseRepository enterpriseRepository;
  @Inject
  private CommentsRepository commentsRepository;
  @Inject
  private QualificationLastCategoryRepository qualificationLastCategoryRepository;
  @Inject
  private EnterpriseCategoryRepository enterpriseCategoryRepository;
  @Inject
  private IndustryCategoryRepository industryCategoryRepository;

  @Inject
  private EnterpriseLicensePicturesRepository enterpriseLicensePicturesRepository;

  @Inject
  private EnterpriseQualificationRepository enterpriseQualificationRepository;

  @Inject
  private EnterpriseProductPriceService enterpriseProductPriceService;

  @Inject
  private CertificateTypeRepository certificateTypeRepository;

  @Inject
  private UserProfileRepository userProfileRepository;

  @Inject
  private RedisService redisService;

  @Inject
  private RecommendEnterpriseRepository recommendEnterpriseRepository;

  @Inject
  private EnterpriseProduceCategoryRepository enterpriseProduceCategoryRepository;

  @Inject
  private DictionaryItemRepository dictionaryItemRepository;

  @Inject
  private RabbitTemplate rabbitTemplate;

  @Inject
  private RedisTemplate<Object, Object> redisTemplate;

  public Page<EnterpriseListDetails> searchEnterprise(EnterpriseCriteria criteria, Pageable pageable) {
	log.debug(" criteria {} ", criteria);
	criteria.setActive(true);
	Page<Enterprise>
		enterprises =
		enterpriseRepository.findAll(EnterpriseSpecifications.searchEnterprises(criteria), pageable);
	return DTOUtils.mapPage(enterprises, EnterpriseListDetails.class);
  }

  public Page<EnterpriseListDetails> searchEnterprise(EnterpriseCriteriaMultiCategories criteria, Pageable pageable) {
	log.debug(" criteria {} ", criteria);
	Page<Enterprise>
		enterprises =
		enterpriseRepository.findAll(EnterpriseSpecifications.searchEnterprises(criteria), pageable);
	return DTOUtils.mapPage(enterprises, EnterpriseListDetails.class);
  }

  public Page<EnterpriseListDetails> searchEnterprise(EnterpriseCriteriaMultiCategories criteria, Pageable pageable,
													  String flag) {
	log.debug(" criteria {}:-> {} ", criteria, flag);
	Page<Enterprise>
		enterprises =
		enterpriseRepository.findAll(EnterpriseSpecifications.searchEnterprises(criteria, flag), pageable);
	return DTOUtils.mapPage(enterprises, EnterpriseListDetails.class);
  }

  public Page<EnterpriseListDetails> searchBackEndEnterprise(EnterpriseCriteria criteria, Pageable pageable) {
	log.debug(" criteria {} ", criteria);
	Page<Enterprise>
		enterprises =
		enterpriseRepository.findAll(EnterpriseSpecifications.searchEnterprises(criteria), pageable);
	return DTOUtils.mapPage(enterprises, EnterpriseListDetails.class);
  }

  public EnterpriseDetails findEnterpriseByUuid(String uuid) {
	Assert.hasText(uuid, "uuid cannot be null");

	Enterprise enterprise = enterpriseRepository.findByUuid(uuid);
	if (enterprise == null) {
	  throw new ResourceNotFoundException(" enterpriseBaseInfo can't find by uuid :" + uuid);
	}

	EnterpriseDetails enterpriseInfoDetails = DTOUtils.map(enterprise, EnterpriseDetails.class);

	List<Comments> comments = commentsRepository.findTop10ByEnterpriseAndActiveOrderByIdDesc(enterprise, true);

	List<EnterpriseLicensePictures>
		licensePictures =
		enterpriseLicensePicturesRepository.findByEnterprise(enterprise);

//	List<QualificationLastCategory>
//		qualificationLastCategories =
//		qualificationCategoryRepository.findByEnterpriseBaseInfo(enterpriseBaseInfo);
	List<EnterpriseQualificationDetails> enterpriseQualificationDetailsList = Lists.newArrayList();
	List<EnterpriseQualification> enterpriseQualifications = enterpriseQualificationRepository
		.findByEnterprise(enterprise);
	enterpriseQualifications.stream().forEach(q -> {
	  QualificationLastCategory lastCategory = q.getQualificationLastCategory();
	  QualificationMiddleCategory middleCategory = lastCategory.getQualificationMiddleCategory();
	  QualificationTopCategory topCategory = middleCategory.getQualificationTopCategory();
	  EnterpriseQualificationDetails details =
		  EnterpriseQualificationDetails
			  .builder()
			  .lastCateGoryId(lastCategory.getId())
			  .lastCateGoryName(lastCategory.getName())
			  .middleCateGoryId(middleCategory.getId())
			  .middleCateGoryName(middleCategory.getName())
			  .topCateGoryId(topCategory.getId())
			  .topCateGoryName(topCategory.getName()).build();
	  enterpriseQualificationDetailsList.add(details);
	});
	enterpriseInfoDetails.setEnterpriseQualificationDetails(enterpriseQualificationDetailsList);

	List<CommentDetails> commentDetailsList = new ArrayList<>();
	for (Comments comment : comments) {
	  UserAccount userAccount = comment.getUser();
	  CommentDetails details = new CommentDetails();
	  details.setDescription(comment.getDescription());
	  details.setCreatedDate(comment.getCreatedDate());

	  if (null != userAccount) {
		String username = userAccount.getUsername();
		if (org.springframework.util.StringUtils.hasText(username) && username.length() > 4) {
		  username = username.substring(0, username.length() - 4) + "****";
		}
		details.setUserUsername(username);

		UserProfile userProfile = userProfileRepository.findByAccount(userAccount);
		if (null != userProfile) {
		  details.setUrl(userProfile.getHeadImage());
		}
	  }

	  commentDetailsList.add(details);
	}
	enterpriseInfoDetails.setCommentDetails(commentDetailsList);

	enterpriseInfoDetails.setEnterpriseLicensePictures(DTOUtils.mapList(licensePictures, EnterpriseLicensePicturesDetails.class));

//	enterpriseInfoDetails.setQualificationCategoryDetails(DTOUtils.mapList(qualificationLastCategories,
//																		   QualificationCategoryDetails.class));
	CertificateType certificateType = certificateTypeRepository.findByCodeAndActive(enterprise.getCertificateType(), true);
	if (null != certificateType) {
	  enterpriseInfoDetails.setCertificateTypeUrl(certificateType.getUrl());
	}
	return enterpriseInfoDetails;
  }

  public EnterpriseDetails findEnterpriseById(Long id) {
	Assert.notNull(id, "enterprise id can not be null");
	if (log.isDebugEnabled()) {
	  log.debug("find enterprise by id @" + id);
	}

	Enterprise enterprise = enterpriseRepository.findById(id);

	if (enterprise == null) {
	  throw new ResourceNotFoundException(id);
	}

	EnterpriseDetails details = DTOUtils.map(enterprise, EnterpriseDetails.class);

	List<EnterpriseCategory> enterpriseCategories = enterpriseCategoryRepository.findByEnterprise(enterprise);
	List<Long> ids = Lists.newArrayList();
	enterpriseCategories.stream().forEach(p -> {
	  ids.add(p.getIndustryCategory().getId());
	});

	List<EnterpriseLicensePictures>
		licensePictures =
		enterpriseLicensePicturesRepository.findByEnterprise(enterprise);

	List<EnterpriseQualification> qualifications = enterpriseQualificationRepository.findByEnterprise(enterprise);
	List<EnterpriseQualificationDetails> enterpriseQualificationDetailsList = Lists.newArrayList();
	qualifications.stream().forEach(q -> {
	  QualificationLastCategory lastCategory = q.getQualificationLastCategory();
	  QualificationMiddleCategory middleCategory = lastCategory.getQualificationMiddleCategory();
	  QualificationTopCategory topCategory = middleCategory.getQualificationTopCategory();

	  EnterpriseQualificationDetails detail = EnterpriseQualificationDetails.builder()
																			.lastCateGoryId(lastCategory.getId())
																			.lastCateGoryName(lastCategory.getName())
																			.middleCateGoryId(middleCategory.getId())
																			.middleCateGoryName(middleCategory.getName())
																			.topCateGoryId(topCategory.getId())
																			.topCateGoryName(topCategory.getName()).build();

	  enterpriseQualificationDetailsList.add(detail);
	});

	List<Long> produceCategoryList = enterpriseProduceCategoryRepository.findActiveItemIdByEnterprise(enterprise);

	details.setEnterpriseQualificationDetails(enterpriseQualificationDetailsList);
	details.setEnterpriseLicensePictures(DTOUtils.mapList(licensePictures, EnterpriseLicensePicturesDetails.class));
	details.setCategoryIds(ids);
	details.setProduceCategories(produceCategoryList);

	return details;
  }

  @Transactional
  public Enterprise saveEnterprise(EnterpriseForm form) {

	Assert.notNull(form, "form cannot be null");
//	Assert.hasText(form.getEmail(), "email cannot be null");
	Assert.hasText(form.getCreditCode(), "creditCode cannot be null");
	Assert.hasText(form.getCertificateType(), "certificateType cannot be null");
//	Assert.hasText(form.getBusinessPhone(), "businessPhone cannot be null");
	Assert.hasText(form.getEnterpriseAddress(), "enterpriseAddress cannot be null");

	log.debug("save EnterpriseForm form {}", form);

	if (enterpriseRepository.findByName(form.getName()) != null) {
	  throw new EnterpriseExistedException(form.getName());
	}
	Enterprise enterprise = DTOUtils.strictMap(form, Enterprise.class);
	enterprise.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));

	CertificateType certificateType = certificateTypeRepository.findByCodeAndActive(form.getCertificateType(), true);
	if (null == certificateType) {
	  log.debug("can not find certificateType @{}", form.getCertificateType());
	  throw new ResourceNotFoundException("can not find certificateType " + form.getCertificateType());
	}

	Enterprise saved = enterpriseRepository.save(enterprise);

	if (form.getCategoryIds() != null) {
	  for (IdValue idValue : form.getCategoryIds()) {
		if (idValue != null) {
		  IndustryCategory industryCategory = industryCategoryRepository.findOne(idValue.getId());
		  if (industryCategory == null) {
			throw new ResourceNotFoundException(" industryCategory can't find by id :" + idValue.getId());
		  }
		  EnterpriseCategory enterpriseCategory = new EnterpriseCategory();
		  enterpriseCategory.setIndustryCategory(industryCategory);
		  enterpriseCategory.setEnterprise(saved);
		  enterpriseCategoryRepository.save(enterpriseCategory);
		}
	  }
	}
	if (form.getQualificationForms() != null && !form.getQualificationForms().isEmpty()) {
	  for (EnterpriseQualificationForm qualificationForm : form.getQualificationForms()) {
		if (qualificationForm != null) {
		  QualificationLastCategory
			  qualificationLastCategory =
			  qualificationLastCategoryRepository.findOne(qualificationForm.getId());
		  if (qualificationLastCategory == null) {
			throw new ResourceNotFoundException(" qualificationLastCategory can't find by id :" + qualificationForm.getId());
		  }
		  EnterpriseQualification enterpriseQualification = new EnterpriseQualification();
		  enterpriseQualification.setEnterprise(saved);
		  enterpriseQualification.setQualificationLastCategory(qualificationLastCategory);
		  enterpriseQualificationRepository.save(enterpriseQualification);
		}
	  }
	}

	if (form.getLicensePictures() != null && !form.getLicensePictures().isEmpty()) {
	  for (String url : form.getLicensePictures()) {
		EnterpriseLicensePictures enterpriseLicense = EnterpriseLicensePictures
			.builder()
			.url(url)
			.enterprise(saved)
			.build();
		enterpriseLicensePicturesRepository.save(enterpriseLicense);
	  }
	}

	List<EnterpriseProduceCategory> enterpriseProduceCategoryList = Lists.newArrayList();

	if (form.getProduceCategories() != null && !form.getProduceCategories().isEmpty()) {
	  form.getProduceCategories().forEach(p -> {
		DictionaryItem item = dictionaryItemRepository.findOne(p.getId());
		if (item == null) {
		  throw new ResourceNotFoundException(" DictionaryItem can't find by id :" + p.getId());
		}
		EnterpriseProduceCategory enterpriseProduceCategory =
			EnterpriseProduceCategory
				.builder()
				.enterprise(saved)
				.dictionaryItem(item)
				.active(true)
				.build();
		enterpriseProduceCategoryList.add(enterpriseProduceCategory);
	  });
	  enterpriseProduceCategoryRepository.save(enterpriseProduceCategoryList);
	}

	rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_ENTERPRISE, MessagingConstants.ROUTING_ENTERPRISE,
								  EnterpriseEvent.CREATE);

	return saved;
  }

  @Transactional
  public void updateEnterprise(Long id, EnterpriseForm form) {
	Assert.notNull(id, "enterprise id can not be null");
	Assert.notNull(form, "form cannot be null");
//	Assert.hasText(form.getEmail(), "email cannot be null");
	Assert.hasText(form.getCreditCode(), "creditCode cannot be null");
	Assert.hasText(form.getCertificateType(), "certificateType cannot be null");
//	Assert.hasText(form.getBusinessPhone(), "businessPhone cannot be null");
	Assert.hasText(form.getEnterpriseAddress(), "enterpriseAddress cannot be null");
	if (log.isDebugEnabled()) {
	  log.debug("find enterprise by id @" + id);
	}

	Enterprise enterprise = enterpriseRepository.findOne(id);
	if (enterprise == null) {
	  throw new ResourceNotFoundException(id);
	}

	Enterprise compareEnterprise = enterpriseRepository.findByName(form.getName());
	if (compareEnterprise != null && !compareEnterprise.getId().equals(id)) {
	  throw new EnterpriseExistedException(form.getName());
	}

	//更新属性
	updateCategoryIds(form.getCategoryIds(), enterprise);
	//更新分类
	updateQualifications(form.getQualificationForms(), enterprise);
	//更新资质证书
	updateLicensePictures(form.getLicensePictures(), enterprise);

	//更新企业生产产品类型
	updateProduceCategories(form.getProduceCategories(), enterprise);

	DTOUtils.strictMapTo(form, enterprise);

	Enterprise saved = enterpriseRepository.save(enterprise);

	enterpriseProductPriceService.updatePriceCount(saved);
	if (log.isDebugEnabled()) {
	  log.debug("updated Enterprise @" + saved);
	}

	redisService.deleteEnterprise(enterprise.getUuid());
  }

  @Transactional
  public void deactivateEnterprise(Long id) {
	Assert.notNull(id, "enterprise id can not be null");
	Enterprise enterprise = enterpriseRepository.findOne(id);
	if (null != enterprise) {
	  enterpriseProductPriceService.updatePriceCount(enterprise);
	}

	enterpriseRepository.deactivate(id, false);

	redisService.deleteEnterprise(enterprise.getUuid());

	rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_ENTERPRISE, MessagingConstants.ROUTING_ENTERPRISE,
								  EnterpriseEvent.DEACTIVATE);
  }

  @Transactional
  public void restoreEnterprise(Long id) {
	Assert.notNull(id, "enterprise id can not be null");

	Enterprise enterprise = enterpriseRepository.findOne(id);
	if (null != enterprise) {
	  enterpriseProductPriceService.updatePriceCount(enterprise);
	}
	enterpriseRepository.deactivate(id, true);

	redisService.deleteEnterprise(enterprise.getUuid());

	rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_ENTERPRISE, MessagingConstants.ROUTING_ENTERPRISE,
								  EnterpriseEvent.ACTIVE);
  }

//  public Page<EnterpriseDetails> findEnterpriseDetailsByKeyword(String q, boolean status, Pageable page) {
//	if (log.isDebugEnabled()) {
//	  log.debug("search enterprise by name@" + q);
//	}
//
//	EnterpriseCriteria criteria = new EnterpriseCriteria();
//	criteria.setName(q);
//	criteria.setActive(status);
//
//	Page<Enterprise> enterprises = enterpriseRepository.findAll(
//		EnterpriseSpecifications.searchEnterprises(criteria), page);
//
//	if (log.isDebugEnabled()) {
//	  log.debug("get enterprise size @" + enterprises.getTotalElements());
//	}
//
//	return DTOUtils.mapPage(enterprises, EnterpriseDetails.class);
//  }
//
//  public Page<EnterpriseDetails> findEnterpriseDetailsByKeyword(Pageable page) {
//	if (log.isDebugEnabled()) {
//	  log.debug("search enterprise by name@");
//	}
//
//	Page<Enterprise> enterprises = enterpriseRepository.findAll(
//		EnterpriseSpecifications.findEnterprisesByAccountingInfo(), page);
//
//	if (log.isDebugEnabled()) {
//	  log.debug("get enterprise size @" + enterprises.getTotalElements());
//	}
//
//	return DTOUtils.mapPage(enterprises, EnterpriseDetails.class);
//  }
//
//  public Page<EnterpriseDetails> findEnterpriseByName(String name, Pageable page) {
//	if (log.isDebugEnabled()) {
//	  log.debug("findEnterprises by @start @page@" + page);
//	}
//
//	EnterpriseCriteria criteria = new EnterpriseCriteria();
//	criteria.setName(name);
//	criteria.setActive(true);
//	Page<Enterprise> enterprises = enterpriseRepository.findAll(
//		EnterpriseSpecifications.searchEnterprises(criteria), page);
//
//	if (log.isDebugEnabled()) {
//	  log.debug("all enterprises @" + enterprises.getTotalElements());
//	}
//
//	return DTOUtils.mapPage(enterprises, EnterpriseDetails.class);
//  }

  @Transactional
  @Deprecated
  public void enableEnterprise(Long id) {
	Assert.notNull(id, "enterprise id can not be null");

	Enterprise enterprise = enterpriseRepository.findOne(id);
	if (null != enterprise) {
	  enterpriseProductPriceService.updatePriceCount(enterprise);
	}

	redisService.deleteEnterprise(enterprise.getUuid());
	enterpriseRepository.deactivate(id, true);
  }

  /**
   * 获取认证类型列表
   */
  public List<CertificateTypeDetails> getCertificateTypes(Boolean active) {

	log.debug("get certificateTypes active@ {}", active);
	List<CertificateType> types = certificateTypeRepository.findByActive(active);
	return DTOUtils.mapList(types, CertificateTypeDetails.class);
  }

  /**
   * 添加认证类型
   */
  public void saveCertificateType(CertificateTypeForm form) {
	Assert.notNull(form, "form can not be null");
	Assert.hasText(form.getName(), "name can not be null");
	Assert.hasText(form.getCode(), "code can not be null");
	Assert.hasText(form.getUrl(), "url can not be null");

	log.debug("saveCertificateType form@ {}", form);

	CertificateType certificateType = certificateTypeRepository.findByCode(form.getCode());
	if (null != certificateType) {
	  throw new CertificateTypeExistedException("certificateType has existed");
	}
	CertificateType type = DTOUtils.strictMap(form, CertificateType.class);
	type.setActive(true);
	certificateTypeRepository.save(type);

  }

  public void updateCertificateType(Long id, CertificateTypeForm form) {
	Assert.notNull(id, "id can not be null");
	Assert.notNull(form, "form can not be null");
	Assert.hasText(form.getName(), "name can not be null");
	Assert.hasText(form.getCode(), "code can not be null");
	Assert.hasText(form.getUrl(), "url can not be null");

	log.debug("updateCertificateType id@ {} form@ {}", id, form);

	CertificateType certificateType = certificateTypeRepository.findByCode(form.getCode());
	if (null != certificateType && !id.equals(certificateType.getId())) {
	  throw new CertificateTypeExistedException("certificateType has existed");
	}

	CertificateType type = certificateTypeRepository.findOne(id);
	type.setName(form.getName());
	type.setCode(form.getCode());
	type.setUrl(form.getUrl());

	certificateTypeRepository.save(type);

  }

  /**
   * 添加推荐企业
   */
  @Transactional
  public void saveRecommendEnterprise(RecommendEnterpriseMgtForm form) {

	log.debug("recommendEnterpriseForm  @{}", form);
	Enterprise enterprise = enterpriseRepository.findOne(form.getEnterpriseId());
	if (null == enterprise) {
	  throw new ResourceNotFoundException("can not find enterprise by id" + form.getEnterpriseId());
	}
	RecommendEnterprise recommendEnterprise = new RecommendEnterprise();
	if (StringUtils.isNoneBlank(form.getType())) {
	  if (RecommendEnterprise.Type.DEFAULT_HOME_PAGE.name().equals(form.getType())) {
		recommendEnterprise.setType(RecommendEnterprise.Type.DEFAULT_HOME_PAGE);
	  } else {
		recommendEnterprise.setType(RecommendEnterprise.Type.ENTERPRISE_HOME_PAGE);
	  }
	}
	Integer sort = recommendEnterpriseRepository.getMaxSortByType(recommendEnterprise.getType());
	recommendEnterprise.setSort(sort == null ? 1 : sort + 1);
	recommendEnterprise.setEnterprise(enterprise);
	recommendEnterpriseRepository.save(recommendEnterprise);

	//删除首页缓存
	redisService.deleteHomePage();
  }

  @Transactional
  public void deleteRecommendEnterprise(Long id) {

	log.debug("recommend enterprise id @{}", id);

	RecommendEnterprise recommendEnterprise = recommendEnterpriseRepository.findOne(id);
	if (null == recommendEnterprise) {
	  throw new ResourceNotFoundException("can not find recommend enterprise by id " + id);
	}

	recommendEnterprise.setActive(false);
	recommendEnterpriseRepository.save(recommendEnterprise);

	//删除首页缓存
	redisService.deleteHomePage();
  }

  @Transactional
  public void restoreRecommendEnterprise(Long id) {

	log.debug("recommend enterprise id @{}", id);

	RecommendEnterprise recommendEnterprise = recommendEnterpriseRepository.findOne(id);
	if (null == recommendEnterprise) {
	  throw new ResourceNotFoundException("can not find recommend enterprise by id " + id);
	}

	recommendEnterprise.setActive(true);
	recommendEnterpriseRepository.save(recommendEnterprise);

	//删除首页缓存
	redisService.deleteHomePage();
  }

  /**
   * 后台查询推荐企业
   */
  public List<RecommendEnterpriseDetails> getRecommendEnterprise(RecommendEnterpriseForm form) {
	Assert.notNull(form, "form can not be null");
//	Assert.notNull(form.getType(),"type can not be null");
	List<RecommendEnterprise> enterprises;
	enterprises =
		recommendEnterpriseRepository.findAll(RecommendEnterpriseSpecifications.searchRecommendEnterprise(form), new Sort(
			Sort.Direction.DESC, "sort"));

	return enterprises.stream().map(enterprise -> {
	  RecommendEnterpriseDetails details = DTOUtils.strictMap(enterprise, RecommendEnterpriseDetails.class);
	  details.setName(enterprise.getEnterprise().getName());
	  details.setUrl(enterprise.getEnterprise().getImageUrl());
	  return details;
	}).collect(Collectors.toList());

  }

  /**
   * 前端推荐企业接口
   */
  public List<RecommendEnterprisePubDetails> getPubRecommendEnterprise(String type) {
	List<RecommendEnterprise> enterprises;
	if (StringUtils.isNoneBlank(type) && RecommendEnterprise.Type.DEFAULT_HOME_PAGE.name().equals(type)) {
	  enterprises =
		  recommendEnterpriseRepository.findTop12ByActiveAndTypeOrderBySortDesc(true, RecommendEnterprise.Type.DEFAULT_HOME_PAGE);
	} else {
	  enterprises =
		  recommendEnterpriseRepository.findTop18ByActiveAndTypeOrderBySortDesc(true,
																				RecommendEnterprise.Type.ENTERPRISE_HOME_PAGE);
	}
	return enterprises.stream().map(enterprise -> {
	  RecommendEnterprisePubDetails details = new RecommendEnterprisePubDetails();
	  details.setName(enterprise.getEnterprise().getName());
	  details.setUrl(enterprise.getEnterprise().getImageUrl());
	  details.setUuid(enterprise.getEnterprise().getUuid());
	  return details;
	}).collect(Collectors.toList());

  }

  private void updateCategoryIds(List<IdValue> categoryIds, Enterprise enterprise) {
	List<EnterpriseCategory> added = Lists.newArrayList();
	List<EnterpriseCategory> removed = Lists.newArrayList();

	List<EnterpriseCategory> enterpriseCategories = enterpriseCategoryRepository.findByEnterprise(enterprise);

	for (IdValue idValue : categoryIds) {
	  boolean found = false;

	  for (EnterpriseCategory category : enterpriseCategories) {
		if (category.getIndustryCategory().getId().equals(idValue.getId())) {
		  found = true;
		  break;
		}
	  }

	  if (found == false) {
		EnterpriseCategory enterpriseCategory
			= new EnterpriseCategory(industryCategoryRepository.findOne(idValue.getId()), enterprise);
		added.add(enterpriseCategory);
	  }
	}

	//found removed
	for (EnterpriseCategory category : enterpriseCategories) {
	  boolean found = false;

	  for (IdValue categroyId : categoryIds) {
		if (category.getIndustryCategory().getId().equals(categroyId.getId())) {
		  found = true;
		  break;
		}
	  }

	  if (found == false) {
		removed.add(category);
	  }
	}

	if (!added.isEmpty()) {
	  enterpriseCategoryRepository.batchSaveEnterpriseCategory(added);
	}
	if (!removed.isEmpty()) {
	  enterpriseCategoryRepository.batchDeleteEnterpriseCategory(removed);
	}
  }

  private void updateQualifications(List<EnterpriseQualificationForm> qualificationForms, Enterprise enterprise) {
	List<EnterpriseQualification> added = Lists.newArrayList();
	List<EnterpriseQualification> removed = Lists.newArrayList();

	List<EnterpriseQualification> enterpriseQualifications = enterpriseQualificationRepository.findByEnterprise(enterprise);

	for (EnterpriseQualificationForm form : qualificationForms) {
	  boolean found = false;
	  Long addId = form.getId();

	  for (EnterpriseQualification qualification : enterpriseQualifications) {
		if (addId.equals(qualification.getQualificationLastCategory().getId())) {
		  found = true;
		  break;
		}
	  }

	  if (found == false) {
		QualificationLastCategory lastCategory = qualificationLastCategoryRepository.getOne(addId);
		if (lastCategory != null) {
		  EnterpriseQualification enterpriseQualification = new EnterpriseQualification(lastCategory, enterprise);
		  added.add(enterpriseQualification);
		}

	  }
	}

	//found removed
	for (EnterpriseQualification qualification : enterpriseQualifications) {
	  boolean found = false;

	  for (EnterpriseQualificationForm form : qualificationForms) {
		if (form.getId().equals(qualification.getQualificationLastCategory().getId())) {
		  found = true;
		  break;
		}
	  }

	  if (found == false) {
		removed.add(qualification);
	  }
	}

	if (!added.isEmpty()) {
	  enterpriseQualificationRepository.batchSaveEnterpriseQualification(added);
	}
	if (!removed.isEmpty()) {
	  enterpriseQualificationRepository.batchDeleteEnterpriseQualification(removed);
	}
  }

  private void updateLicensePictures(List<String> licensePictures, Enterprise enterprise) {
	List<EnterpriseLicensePictures> added = Lists.newArrayList();
	List<EnterpriseLicensePictures> removed = Lists.newArrayList();

	List<EnterpriseLicensePictures> licensePicturesList = enterpriseLicensePicturesRepository.findByEnterprise(enterprise);

	for (String url : licensePictures) {
	  boolean found = false;

	  for (EnterpriseLicensePictures licensePicture : licensePicturesList) {
		if (licensePicture.getUrl().equals(url)) {
		  found = true;
		  break;
		}
	  }

	  if (found == false) {
		EnterpriseLicensePictures enterpriseLicensePictures = new EnterpriseLicensePictures(url, enterprise);
		added.add(enterpriseLicensePictures);
	  }
	}

	//found removed
	for (EnterpriseLicensePictures licensePicture : licensePicturesList) {
	  boolean found = false;

	  for (String url : licensePictures) {
		if (licensePicture.getUrl().equals(url)) {
		  found = true;
		  break;
		}
	  }

	  if (found == false) {
		removed.add(licensePicture);
	  }
	}

	if (!added.isEmpty()) {
	  enterpriseLicensePicturesRepository.batchSaveEnterpriseLicensePictures(added);
	}
	if (!removed.isEmpty()) {
	  enterpriseLicensePicturesRepository.batchDeleteEnterpriseLicensePictures(removed);
	}
  }

  private void updateProduceCategories(List<IdValue> items, Enterprise enterprise) {
	List<EnterpriseProduceCategory> added = Lists.newArrayList();
	List<Long> removed = Lists.newArrayList();

	List<Long> produceCategoryList = enterpriseProduceCategoryRepository.findActiveItemIdByEnterprise(enterprise);

	items.forEach(p -> {
	  boolean found = false;
	  for (Long p1 : produceCategoryList) {
		if (p.getId().longValue() == p1.longValue()) {
		  found = true;
		  break;
		}
	  }
	  if (found == false) {
		DictionaryItem dictionaryItem = new DictionaryItem();
		dictionaryItem.setId(p.getId());
		EnterpriseProduceCategory enterpriseProduceCategory = new EnterpriseProduceCategory();
		enterpriseProduceCategory.setDictionaryItem(dictionaryItem);
		enterpriseProduceCategory.setEnterprise(enterprise);
		added.add(enterpriseProduceCategory);
	  }
	});

	produceCategoryList.forEach(p -> {
	  boolean found = false;
	  for (IdValue p1 : items) {
		if (p.longValue() == p1.getId().longValue()) {
		  found = true;
		  break;
		}
	  }
	  if (found == false) {
		removed.add(p);
	  }
	});

	if (!added.isEmpty()) {
	  enterpriseProduceCategoryRepository.save(added);
	}
	if (!removed.isEmpty()) {
	  removed.forEach(r -> {
		enterpriseProduceCategoryRepository.deleteEnterpriseProduceCategories(r, enterprise);
	  });

	}
  }

  public void saveComment(Long id, CommentForm form, UserAccount userAccount) {
	log.debug("save comment @{}", form);
	Assert.notNull(form, "form can not be null");
	Assert.notNull(id, "enterprise id can not be null");

	Enterprise enterprise = enterpriseRepository.findOne(id);
	if (null == enterprise) {
	  throw new ResourceNotFoundException("can not found enterprise by id" + id);
	}

	Comments comments = new Comments();
	comments.setActive(false);
	comments.setDescription(form.getDescription());
	comments.setEnterprise(enterprise);
	comments.setUser(userAccount);

	commentsRepository.save(comments);
  }

  @Cacheable(value = RedisKeyConstants.CACHE_KEY + "enterprise.num", key = "#root.method.name")
  public List<EnterpriseCount> searchEnterpriseNumber() {
	return enterpriseRepository.searchEnterpriseNumber();
  }

  @Transactional
  public void recommendEnterpriseSort(RecommendEnterpriseSortForm form) {
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getType(), "form.getType() cannot be null");
	Assert.notNull(form.getEnterpriseSorts(), "form.getEnterpriseSorts() cannot be null");

	log.debug("recommendEnterpriseSort form  {} ", form);

	if (!form.getEnterpriseSorts().isEmpty()) {
	  form.getEnterpriseSorts().forEach(r -> {
		recommendEnterpriseRepository.updateRecommendEnterpriseSort(r.getSort(), r.getId(),
																	RecommendEnterprise.Type.valueOf(form.getType()));
	  });
	}

	if (form.getType().equals("DEFAULT_HOME_PAGE")) {
	  redisService.deleteHomePage();
	} else {
	  redisService.deleteEnterpriseListPage();
	}
  }

//  public List<Long> findActiveItemIdByEnterprise(Long id) {
//	Assert.notNull(id, "enterprise id cannot be null");
//	log.debug("findActiveItemIdByEnterprise by id {} ", id);
//	Enterprise enterprise = enterpriseRepository.findOne(id);
//	if (enterprise == null) {
//	  throw new ResourceNotFoundException("can not found enterprise by id" + id);
//	}
//	List<Long> produceCategoryList = enterpriseProduceCategoryRepository.findActiveItemIdByEnterprise(enterprise);
//	return produceCategoryList;
//  }

  public Long findActiveCount() {
	Long count = (Long) (redisTemplate.opsForValue().get("enterprise.num"));
	return count;
  }
}


