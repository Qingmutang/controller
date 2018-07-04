package com.modianli.power.common.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modianli.power.DTOUtils;
import com.modianli.power.common.cache.CacheService;
import com.modianli.power.common.exception.RequirementException;
import com.modianli.power.common.utils.RequirementUtils;
import com.modianli.power.domain.jpa.DictionaryItem;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.IndustryCategory;
import com.modianli.power.domain.jpa.OrderItem;
import com.modianli.power.domain.jpa.PurchaseOrder;
import com.modianli.power.domain.jpa.RequirementBidding;
import com.modianli.power.domain.jpa.RequirementCategory;
import com.modianli.power.domain.jpa.Requirements;
import com.modianli.power.domain.jpa.ServiceCategory;
import com.modianli.power.domain.jpa.TransactionLog;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.domain.jpa.UserCertification;
import com.modianli.power.domain.jpa.UserProfile;
import com.modianli.power.model.EnterpriseCriteriaMultiCategories;
import com.modianli.power.model.EnterpriseListDetails;
import com.modianli.power.model.OrderForm;
import com.modianli.power.model.OrderSN;
import com.modianli.power.model.RequirementBiddingDetails;
import com.modianli.power.model.RequirementBiddingForm;
import com.modianli.power.model.RequirementBiddingOnlyDetails;
import com.modianli.power.model.RequirementBiddingPubDetails;
import com.modianli.power.model.RequirementBiddingSelfDetails;
import com.modianli.power.model.RequirementDetails;
import com.modianli.power.model.RequirementForm;
import com.modianli.power.model.RequirementQueryForm;
import com.modianli.power.model.RequirementValidatedForm;
import com.modianli.power.model.ServiceCategoryDetails;
import com.modianli.power.model.UserProfileDetail;
import com.modianli.power.payment.alipay.config.AlipayConfig;
import com.modianli.power.payment.alipay.config.PaymentBeanConfig;
import com.modianli.power.payment.alipay.util.AlipaySubmit;
import com.modianli.power.payment.model.PaymentForm;
import com.modianli.power.persistence.repository.jpa.DictionaryItemRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;
import com.modianli.power.persistence.repository.jpa.IndustryCategoryRepository;
import com.modianli.power.persistence.repository.jpa.OrderItemRepository;
import com.modianli.power.persistence.repository.jpa.OrderRepository;
import com.modianli.power.persistence.repository.jpa.RequirementBiddingRepository;
import com.modianli.power.persistence.repository.jpa.RequirementCategoryRepository;
import com.modianli.power.persistence.repository.jpa.RequirementRepository;
import com.modianli.power.persistence.repository.jpa.RequirementSpecifications;
import com.modianli.power.persistence.repository.jpa.ServiceCategoryRepository;
import com.modianli.power.persistence.repository.jpa.TransactionLogRepository;
import com.modianli.power.persistence.repository.jpa.UserProfileRepository;
import com.modianli.power.sms.core.SmsMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

/**
 * Created by haijun on 2017/3/2.
 */
@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class RequirementService {

  @Inject
  private PaymentBeanConfig.AlipayConfigDetails alipayConfigDetails;

  @Inject
  private OrderRepository orderRepository;

  @Inject
  private OrderItemRepository orderItemRepository;

  @Inject
  private OrderService orderService;

  @Inject
  private EnterpriseService enterpriseService;

  @Inject
  private CacheService cacheService;

  @Inject
  private RequirementRepository requirementRepository;

  @Inject
  private RequirementBiddingRepository requirementBiddingRepository;

  @Inject
  private IndustryCategoryRepository industryCategoryRepository;

  @Inject
  private UserProfileRepository userProfileRepository;

  @Inject
  private EnterpriseRepository enterpriseRepository;

  @Inject
  private TransactionLogRepository transactionLogRepository;

  @Inject
  private ServiceCategoryRepository serviceCategoryRepository;

  @Inject
  private RequirementCategoryRepository requirementCategoryRepository;

  @Inject
  private DictionaryItemRepository dictionaryItemRepository;

  @Inject
  private RedisTemplate<String, String> redisTemplate;

  @Inject
  private ObjectMapper objectMapper;

  @Inject
  private MessageSource messageSource;

  @Inject
  private RedisService redisService;

  @Inject
  private EmailService emailService;

  private final static String PREFIX_REDIS = "POWER-REQUIREMENT-EP_";

  private final static String PREFIX_REDIS_PV = "POWER-REQUIREMENT-PV_";

  @Inject
  private RequirementService requirementServiceProxy;

  @Value("${power.static.img}")
  private String staticImg;


  private static Map<String, List<Long>> globalCategoryRelated = null;

  /**
   * 企业用户是认证状态
   */
  @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
  public String auth(UserAccount userAccount) {
	UserProfile userProfile = userProfileRepository.findByAccountId(userAccount.getId());
	if (userProfile != null) {
	  return userProfile.getCertificateStatus().name();
	}
	return null;
  }

  /**
   * 新增需求对象
   * 企业方发布需求
   */
  public RequirementDetails saveRequirement(RequirementForm form, UserAccount userAccount) {
	Assert.notNull(form, "form of requirement cannot be null");
	log.debug("saveRequirement form {}， user account {}", form, userAccount);

	//数据校验
	String errMsg = RequirementUtils.isLegalForm(form, false);
	if (errMsg != null) {
	  throw new InvalidParameterException(errMsg);
	}

	//关联的企业用户
	UserProfile userProfile = userProfileRepository.findByAccountId(userAccount.getId());
	if (userProfile == null) {
	  throw new RequirementException("企业用户对象不存在");
	}

	//需求实体对象
	form.setPrice(null);
	Requirements requirement = DTOUtils.strictMap(form, Requirements.class);
	requirement.setUserProfile(userProfile);
	requirement.setStatus(Requirements.Status.CREATED);
	requirement.setPushDate(LocalDate.now());//发标日期

	//需求编号
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	requirement.setRequirementUUID(LocalDateTime.now().format(formatter) + UUID.randomUUID().toString().substring(0, 8));
	requirement.setCreatedBy(userAccount);

	//保存需求对象
	requirement = requirementRepository.save(requirement);

	//服务类型
	this.saveRequirementCategory(requirement, form.getCategoryIds());

	try{
	  Boolean flag = emailService.sendMail("1983245871@qq.com", "用户发布需求", "有用户发布了新需求！", "2530654946@qq.com","402244278@qq.com");
	  if (!flag) {
		log.warn("邮件发送失败！");
	  }
	}catch (Exception ex){
	  log.info(">>> send mail is error.", ex.getMessage());
	}

	return DTOUtils.map(requirement, RequirementDetails.class);
  }

  /**
   * 魔电后台审核时根据ID获得需求信息
   */
  public RequirementDetails getRequirement(Long id) {
	Assert.notNull(id, "id of requirement cannot be null");

	Requirements requirement = requirementRepository.findOne(id);
	if (requirement == null || !requirement.isActive() || !isActiveRequirement(requirement)) {
	  throw new RequirementException(id + "的需求已进行逻辑删除或者已处在下架结标状态");
	}

	RequirementDetails requirementDetails = DTOUtils.map(requirement, RequirementDetails.class);

	//服务类型列表
	List<RequirementCategory> categories = requirementCategoryRepository.findByRequirementId(requirement.getId());
	List<ServiceCategory> serviceCategories = categories.stream().map(RequirementCategory::getServiceCategory).collect(toList());
	List<ServiceCategoryDetails> serviceCategoryDetails = DTOUtils.mapList(serviceCategories, ServiceCategoryDetails.class);
	requirementDetails.setServiceCategoryDetailsList(serviceCategoryDetails);

	return requirementDetails;
  }

  /**
   * 魔电后台根据需求ID获得所有需求的配单信息
   */
  public Page<RequirementBiddingSelfDetails> getRequirementBidding(Long id, Pageable page) {
	Assert.notNull(id, "id of requirement cannot be null");

	Page<RequirementBidding>
		biddings =
		requirementBiddingRepository.findAll(RequirementSpecifications.getRequirementBiddings(id, true), page);
	return DTOUtils.mapPage(biddings, RequirementBiddingSelfDetails.class);
  }

  /**
   * 根据ID获得对应的需求(需求详情页)
   */
  @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
  public Map<String, Object> getRequirement(String uuid, UserAccount userAccount) {
	Assert.notNull(uuid, "id of requirement cannot be null");
	log.debug("getRequirement uuid {}", uuid);

	Map<String, Object> requirementAndProfileDetails = Maps.newHashMap();
	//1. get requirement
	Requirements requirement = requirementRepository.findByRequirementUUIDAndActive(uuid, true);
	if (requirement == null || Requirements.Status.FINISHED.equals(requirement.getStatus())) {
	  throw new RequirementException(uuid + "的需求不存在");
	}

	String pageViewStr = redisTemplate.opsForValue().get(PREFIX_REDIS_PV + uuid);
	Integer pageView = 1;
	if (StringUtils.isBlank(pageViewStr)) {
	  redisTemplate.opsForValue().set(PREFIX_REDIS_PV + uuid, pageView.toString());
	} else {
	  pageView = Integer.parseInt(pageViewStr) + 1;
	  redisTemplate.opsForValue().set(PREFIX_REDIS_PV + uuid, pageView.toString());
	}

	requirement.setPageView(pageView);

	//服务类型列表
	List<RequirementCategory> categories = requirementCategoryRepository.findByRequirementId(requirement.getId());
	List<ServiceCategory> serviceCategories = categories.stream().map(RequirementCategory::getServiceCategory).collect(toList());
	List<ServiceCategoryDetails> serviceCategoryDetails = DTOUtils.mapList(serviceCategories, ServiceCategoryDetails.class);
	if(requirement.getCategoryType() != null){
	  serviceCategoryDetails.add(getOldDetail(requirement.getCategoryType().name()));
	}

	if (userAccount != null && requirement.getUserProfile() != null && userAccount.getId()
																				  .equals(requirement.getUserProfile()
																									 .getAccount()
																									 .getId())) {
	  RequirementDetails requirementDetails = DTOUtils.map(requirement, RequirementDetails.class);
	  requirementDetails.setServiceCategoryDetailsList(serviceCategoryDetails);
	  requirementAndProfileDetails.put("requirement", requirementDetails);
	} else {//非发标人不可见
	  RequirementDetails requirementDetails = DTOUtils.map(requirement, RequirementDetails.class);
	  UserProfileDetail userProfileDetailNew = new UserProfileDetail();
	  userProfileDetailNew.setAddress("暂不可见");
	  userProfileDetailNew.setContacts("隐藏");
	  userProfileDetailNew.setPhone("隐藏");
	  userProfileDetailNew.setName("企业名称暂不可见");
	  userProfileDetailNew.setLogo(this.staticImg);
	  requirementDetails.setUserProfile(userProfileDetailNew);
	  requirementDetails.setAttachFile(null);//附件不可见
	  requirementDetails.setServiceCategoryDetailsList(serviceCategoryDetails);

	  requirementAndProfileDetails.put("requirement", requirementDetails);
	}

	//当前发表用户和支付之后的供应商可以查看公司信息
	//2. get enterprises
	/*List<RequirementBidding> requirementBiddings = requirementBiddingRepository.findAll(
		RequirementSpecifications.getReqirementBiddings( requirement.getId() ) );
	if(userAccount != null && requirement.getUserProfile() != null && userAccount.getId().equals(requirement.getUserProfile().getAccount().getId())){
	  requirementAndProfileDetails.put("requirementBiddings", DTOUtils.map(requirementBiddings, RequirementBiddingSelfDetails.class));
	}else{
	  requirementAndProfileDetails.put("requirementBiddings", DTOUtils.mapList(requirementBiddings, RequirementBiddingPubDetails.class));
	}*/
	return requirementAndProfileDetails;
  }

  /**
   * 根据UUID获得需求的配单信息
   *
   * 如果当前存在登录用户，如果是发标用户，可以查看接标供应商的报价、说明等信息。 其他用户已经非登录用户则差看不到。
   */
  public Object getRequirementBidding(String uuid, UserAccount userAccount, Pageable page) {
	Assert.notNull(uuid, "id of requirement cannot be null");
	//1. get requirement
	Requirements requirement = requirementRepository.findByRequirementUUIDAndActive(uuid, true);
	if (requirement == null) {
	  throw new RequirementException(uuid + "的需求不存在");
	}

	Page<RequirementBidding> requirementBiddings = requirementBiddingRepository.findAll(
		RequirementSpecifications.getRequirementBiddings(requirement.getId()), page);
	if (userAccount != null && requirement.getUserProfile() != null && userAccount.getId()
																				  .equals(requirement.getUserProfile()
																									 .getAccount()
																									 .getId())) {
	  return DTOUtils.mapPage(requirementBiddings, RequirementBiddingSelfDetails.class);
	} else {
	  return DTOUtils.mapPage(requirementBiddings, RequirementBiddingPubDetails.class);
	}
  }

  /**
   * 根据用户信息获得企业信息
   */
  @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
  public UserProfileDetail getUserProfileDetail(UserAccount userAccount) {
	Assert.notNull(userAccount, "user cannot be null");
	UserProfile userProfile = userProfileRepository.findByAccountId(userAccount.getId());
	return DTOUtils.map(userProfile, UserProfileDetail.class);
  }

  /**
   * 获得需求列表(分页)
   * 根据名称， 状态， 省市区， 企业认证状态过滤条件查看信息
   */
  @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
  public Page<RequirementDetails> getRequirements(RequirementQueryForm form, Pageable page, UserAccount userAccount) {
	log.debug("get requirements. user:{}", userAccount);

	//设备和物料的子属性分类
	List<Long> ids = Lists.newArrayList();
	if(form != null && form.getCategoryType() != null && "MATERIAL".equals(form.getCategoryType())){
	  ServiceCategory serviceCategory = serviceCategoryRepository.findOne(3L);
	  List<ServiceCategory> serviceCategories = serviceCategoryRepository.findByParentCategory(serviceCategory);
	  serviceCategories.forEach(item -> ids.add(item.getId()));
	}
	log.debug("ids when category type is material: {}", ids);
	Page<Requirements> requirementsPage =
		requirementRepository.findAll(RequirementSpecifications.getActiveRequirement(form, ids), page);

	return DTOUtils.mapPage(requirementsPage, RequirementDetails.class);
  }

  /**
   * 获得需求列表(分页)
   * 企业方获得自己发布的需求列表
   */
  @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
  public Page<RequirementDetails> getRequirements(Pageable page, UserAccount userAccount) {
	log.debug("get requirements. user:{}", userAccount);

	//UserProfile userProfile = userProfileRepository.findByAccountId(userAccount.getId());
	Page<Requirements> requirementsPage =
		requirementRepository.findAll(RequirementSpecifications.getActiveRequirement(userAccount.getId()), page);

	return DTOUtils.mapPage(requirementsPage, RequirementDetails.class);
  }

  /**
   * 需求方选中供应商
   */
  public boolean chooseEnterprise(String requirementUUID, String requirementBiddingUUID, UserAccount userAccount) {
	Assert.notNull(requirementUUID, "id of requirement cannot be null");
	Assert.notNull(requirementBiddingUUID, "id of requirement bidding cannot be null");

	if (!isActiveRequirement(requirementRepository.findByRequirementUUIDAndActive(requirementUUID, true))) {
	  throw new RequirementException("标的已经截标或已经下架, 无法进行其他操作");
	}

	//TODO 逻辑校验
	//更改需求状态:中标
	requirementRepository.updateRequirementByIdAndStatus(requirementUUID, Requirements.Status.BID,
														 userAccount != null ? userAccount.getId() : null);
	requirementBiddingRepository.updateRequirementBiddingByUuidsAndStatus(requirementBiddingUUID,
																		  RequirementBidding.BiddingStatus.BID);

	redisService.deleteRequirement(requirementUUID);

	return true;
  }

  /**
   * 魔电后台查看需求
   * 所有企业方发布的需求,
   * 查询条件： 部分uuid, 状态
   */
  @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
  public Page<RequirementDetails> getRequirementsViaMDLCondition(String requirementUUID, String status, Pageable pageable) {
	Page<Requirements> requirementPages = requirementRepository.findAll(
		RequirementSpecifications.searchRequirement(requirementUUID, status), pageable);

	return DTOUtils.mapPage(requirementPages, RequirementDetails.class);
  }

  /**
   * 魔电后台审核需求
   * 修改需求
   */
  public RequirementDetails validateRequirement(Long id, RequirementValidatedForm form, UserAccount userAccount) {
	Assert.notNull(id, "id of requirement cannot be null");
	Assert.notNull(form, "form of requirement cannot be null");
	Assert.notNull(form.getPrice(), "price of requirement cannot be null");
	log.debug("validateRequirement form {}", form);

	//校验
	String errMsg = RequirementUtils.isLegalForm(form);
	if (errMsg != null) {
	  throw new InvalidParameterException(errMsg);
	}

	Requirements requirement = requirementRepository.findOne(id);
	if (requirement == null || !requirement.isActive()) {
	  throw new RequirementException(id + "的需求不存在");
	}
	if (!isActiveRequirement(requirement)) {
	  throw new RequirementException("需求已经下架或者截标，无法审核");
	}

	DTOUtils.strictMapTo(form, requirement);
	requirement.setStatus(Requirements.Status.VALIDATED);//需求审核通过
	requirement.setValidateDate(LocalDate.now());//需求审核时间
	requirementRepository.save(requirement);

	//服务类型
	requirementCategoryRepository.deleteByRequirementId(requirement.getId());
	this.saveRequirementCategory(requirement, form.getCategoryIds());

	redisService.deleteRequirement(requirement.getRequirementUUID());
	return DTOUtils.map(requirement, RequirementDetails.class);
  }

  public void saveRequirementCategory(Requirements requirement, List<Long> cids) {
	List<RequirementCategory> categories = Lists.newArrayList();
	for (Long cid : cids) {
	  RequirementCategory category = new RequirementCategory();
	  category.setRequirementId(requirement.getId());
	  category.setServiceCategory(serviceCategoryRepository.findOne(cid));

	  categories.add(category);
	}
	requirementCategoryRepository.save(categories);
  }

  /**
   * 魔电后台上下架
   * ps：下架后无法再次上架， 截标后无法上下架
   * 上架时， 根据需求的类型，地区进行供应商匹配，匹配到供应商后进行配单和发送消息， 然后把当前的上架信息存至redis中，供job进行继续配单。
   * 下架时， 配单信息逻辑删除， 并清除redis的缓存信息，防止job继续执行配单功能。
   */
  public boolean shelfRequirement(Long id, int isShelf, UserAccount userAccount) {
	Assert.notNull(id, "id of requirement cannot be null");
	Assert.notNull(isShelf, "operation of requirement cannot be null");

	Requirements requirement = requirementRepository.findOne(id);
	if (requirement == null) {
	  throw new RequirementException("需求不存在");
	}

	Requirements.Status status = requirement.getStatus();
	if (Requirements.Status.FINISHED.equals(requirement.getStatus())) {
	  throw new RequirementException("标的已经下架, 无法进行其他操作");
	}

	//上架时
	if (isShelf == 1) {
	  if (Requirements.Status.BE_FINISHED.equals(status)) {
		throw new RequirementException("标的已经结标, 无法进行其他操作");
	  }
	  if (Requirements.Status.BIDDING.equals(status) || Requirements.Status.MATCHED.equals(status)) {
		throw new RequirementException("标的已经上架, 无法再次上架");
	  }

	  requirement.setShelfDate(LocalDate.now());
	  requirement.setStatus(Requirements.Status.BIDDING);
	  requirement.setLastModifiedBy(userAccount);
	  requirement.setLastModifiedDate(LocalDateTime.now());
	  requirementRepository.save(requirement);

	  matchEnterprise(id, new PageRequest(0, 3));
	} else {
	  //下架
	  requirement.setShelfDate(null);
	  requirement.setStatus(Requirements.Status.FINISHED);
	  requirement.setLastModifiedBy(userAccount);
	  requirement.setLastModifiedDate(LocalDateTime.now());
	  requirementRepository.save(requirement);

	  //清除派单job中的key
	  redisTemplate.delete(PREFIX_REDIS + id);

	  //下架时, 更新配单状态
	  requirementBiddingRepository.updateRequirementBiddingByRequirementAndActive(false, id);
	}

	if (null != requirement) {
	  redisService.deleteRequirement(requirement.getRequirementUUID());
	}

	return true;
  }

  /**
   * 魔电后台手动配单供应商
   * 页面暂时没有实现该功能
   */
  public Map<String, String> matchEnterpriseManual(Long id, Pageable page) {
	Map<String, String> result = Maps.newHashMap();
	try {
	  if (redisTemplate.opsForValue().get(PREFIX_REDIS + id) != null) {
		result.put("result", "error");
		result.put("message", "当前需求已经在自动配单队列中。");
	  } else {
		boolean autoMatch = requirementServiceProxy.matchEnterprise(id, page);
		//是否定时配单
		if (autoMatch) {
		  //定时任务 配单
		  Map<String, String> params = Maps.newHashMap();
		  params.put("requirementId", id.toString());
		  params.put("page", "0");
		  params.put("size", "3");
		  params.put("currentDate", LocalDateTime.now().toString());
		  redisTemplate.opsForValue().set(PREFIX_REDIS + id, objectMapper.writeValueAsString(params));
		}

		result.put("result", "success");
		result.put("message", "当前需求正在配单。");
	  }
	} catch (Exception ex) {
	  log.error("manual to match enterprise is error.", ex);
	  result.put("result", "error");
	  result.put("message", ex.getMessage());
	}

	return result;
  }

  /**
   * 魔电后台自动给供应商配单
   * 1、竞标中进行配单；
   * 2、匹配到供应商进行配单。
   *
   * 配单过程中，如果已有配单，则跳过；
   * 当匹配的供应商有电话是进行短信通知(TODO 短信通知未开通)
   */
  public boolean matchEnterprise(Long id, Pageable pageable) {
	//获得当前需求
	Requirements requirement = requirementRepository.findOne(id);
	if (requirement == null) {
	  return false;//
	}

	//当前需求的状态不在竞标或者匹配供应商状态，不进行配单
	Requirements.Status status = requirement.getStatus();
	if (!(Requirements.Status.BIDDING.equals(status) || Requirements.Status.MATCHED.equals(status))) {
	  return false;
	}

	//获得匹配的供应商
	Page<EnterpriseListDetails> enterpriseListDetails = requirementServiceProxy.getMatchEnterprise(pageable, requirement);
	if (enterpriseListDetails == null || enterpriseListDetails.getContent().size() == 0) {
	  return false;//没有匹配到数据
	}

	//新增竞标供应商
	List<RequirementBidding> requirementBiddings = Lists.newArrayList();
	Map<String, String> mobiles = Maps.newHashMap();
	for (EnterpriseListDetails ebid : enterpriseListDetails) {
	  //关联供应商
	  Long enterpriseId = ebid.getId();
	  if (enterpriseId == null) {
		continue;
	  }
	  //清除redis之后的重复配单
	  RequirementBidding bidding = requirementBiddingRepository.findByRequirementAndEnterprise(requirement.getId(), enterpriseId);
	  if (bidding != null) {
		continue;
	  }
	  Enterprise enterprise = enterpriseRepository.findOne(enterpriseId);

	  RequirementBidding requirementBidding = new RequirementBidding();
	  requirementBidding.setRequirement(requirement);
	  requirementBidding.setEnterprise(enterprise);
	  requirementBidding.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));

	  String mobile = enterprise.getBusinessPhone();
	  if (StringUtils.isNotBlank(mobile)) {
		mobiles.put(requirementBidding.getUuid(), mobile);
	  }

	  requirementBiddings.add(requirementBidding);
	}
	if (requirementBiddings.size() > 0) {
	  requirementBiddings = requirementBiddingRepository.save(requirementBiddings);
	}

	//需求更新状态：匹配状态（MATCHED）和匹配时间
	if (requirement.getMatchDate() == null) {
	  requirement.setStatus(Requirements.Status.MATCHED);
	  requirement.setMatchDate(LocalDate.now());
	  requirementRepository.save(requirement);
	}

	try {
	  //向供应商发送消息
	  requirementBiddings.stream().forEach(requirementBidding -> {
		String uuid = requirementBidding.getUuid();
		if (!mobiles.containsKey(uuid)) {
		  return;
		}

		final SmsMessage
			smsMessage =
			new SmsMessage(mobiles.get(uuid), messageSource.getMessage("reqcode", new String[]{requirement.getName()}, null));
		log.info("@send message of {} to {}", smsMessage.getContent(), smsMessage.getMobileNumber());
		//rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_SMS, MessagingConstants.ROUTING_SMS, smsMessage);//发送手机信息到mq
	  });
	} catch (Exception ex) {
	  log.error("send message is error.", ex);
	}

	if (null != requirement) {
	  redisService.deleteRequirement(requirement.getRequirementUUID());
	}
	return true;
  }

  /**
   * 获得3条匹配的供应商信息
   */
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Page<EnterpriseListDetails> getMatchEnterprise(Pageable page, Requirements requirement) {
	//设置匹配的供应商地域，认证，企业类型
	EnterpriseCriteriaMultiCategories criteria = new EnterpriseCriteriaMultiCategories();
	//rule 1: 认证企业
	criteria.setVerifyStatus("APPROVED");

	//rule 2: 市区
	if (StringUtils.isNotBlank(requirement.getCityCode())) {
	  criteria.setCityCode(requirement.getCityCode());
	}

	List<RequirementCategory> categories = requirementCategoryRepository.findByRequirementId(requirement.getId());
	List<Long> industryCategories = Lists.newArrayList();
	if(categories != null && categories.size() > 0){
	  //2017.06.15 采购配单通道
	  log.debug(">>>> the purchase category for match enterprise(s).");
	  ServiceCategory parent = categories.get(0).getServiceCategory().getParentCategory();
	  //TODO 采购配单
	  if(parent != null && "PURCHASE".equals(parent.getCode())){
		List<String> codes = categories.stream().map(item -> item.getServiceCategory().getCode()).collect(Collectors.toList());
		//根据codes映射到字典里的id, 获得ids, 然后根据ids进行供应商匹配
		List<DictionaryItem> dictionaryItems = dictionaryItemRepository.findByCodes(codes);
		if(dictionaryItems != null && dictionaryItems.size() > 0){
		  dictionaryItems.forEach(di ->{
		    industryCategories.add(di.getId());
		  });
		}
		if(industryCategories.size() < 1){
		  return null;
		}
		criteria.setCategory(industryCategories);
		return enterpriseService.searchEnterprise(criteria, page, "PURCHASE");
	  }
	}

	//rule 3: 企业类型
	if (this.globalCategoryRelated == null) {
	  this.globalCategoryRelated = categoryRelated(industryCategoryRepository.findAll());
	}

	for (RequirementCategory category : categories) {
	  ServiceCategory serviceCategory = category.getServiceCategory();
	  industryCategories.addAll(this.globalCategoryRelated.get(serviceCategory.getCode()));
	}
	log.debug(">>>>the category for match enterprise is(are):{}", industryCategories);
	criteria.setCategory(industryCategories);

	return enterpriseService.searchEnterprise(criteria, page);
  }

  private Map<String, List<Long>> categoryRelated(List<IndustryCategory> categories) {
	/**
	 ALL,//总包 -> 所有
	 PART,//分包 如下
	   POWER_APPROVAL,//用电规划报批 -> 2：设计 3：施工
	   CONSTRUCT,//施工 -> 3:施工
	   DESIGN,//设计 -> 2:设计
	   CONSTRUCT_DESIGN,//设计施工一体化 -> 2：设计 3：施工
	   SUPERVISE,//监理 -> 6:监理
	   HR,//人力资源 -> 7: 人力资源
	   MATERIAL,//物资和设备采购 -> 3:施工
	   CONSULT,//电力建设咨询 -> 2：设计 3：施工
	 */

	Map<String, List<Long>> categoryRelated = Maps.newHashMap();
	categoryRelated.put("ALL", Lists.newArrayList());
	categoryRelated.put("POWER_APPROVAL", Lists.newArrayList());
	categoryRelated.put("CONSTRUCT", Lists.newArrayList());
	categoryRelated.put("DESIGN", Lists.newArrayList());
	categoryRelated.put("CONSTRUCT_DESIGN", Lists.newArrayList());
	categoryRelated.put("SUPERVISE", Lists.newArrayList());
	categoryRelated.put("HR", Lists.newArrayList());
	categoryRelated.put("MATERIAL", Lists.newArrayList());
	categoryRelated.put("CONSULT", Lists.newArrayList());
	categoryRelated.put("OTHER", Lists.newArrayList());

	for (IndustryCategory category : categories) {
	  categoryRelated.get("ALL").add(category.getId());
	  if ("设计".equals(category.getName())) {
		categoryRelated.get("DESIGN").add(category.getId());
		categoryRelated.get("CONSTRUCT_DESIGN").add(category.getId());
		categoryRelated.get("POWER_APPROVAL").add(category.getId());
	  } else if ("施工".equals(category.getName())) {
		categoryRelated.get("CONSTRUCT").add(category.getId());
		categoryRelated.get("MATERIAL").add(category.getId());
		categoryRelated.get("CONSULT").add(category.getId());
		categoryRelated.get("POWER_APPROVAL").add(category.getId());
	  } else if ("设备".equals(category.getName()) || "物料".equals(category.getName())) {
		//categoryRelated.get("MATERIAL").add(category.getId());
	  } else if ("监理".equals(category.getName())) {
		categoryRelated.get("SUPERVISE").add(category.getId());
	  } else if ("人力资源".equals(category.getName())) {
		categoryRelated.get("HR").add(category.getId());
	  }
	}

	log.debug(">>> category related: {}", categoryRelated);
	//cacheService.set("ENTERPRISE_CATEGORY_01", categoryRelated);
	return categoryRelated;
  }

  /**
   * 供应商查看配单信息
   * 根据UUID, 或者状态查询配单信息
   */
  @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
  public Page<RequirementBiddingDetails> getRequirementsViaEPCondition(RequirementQueryForm form,
																	   Pageable pageable, final UserAccount userAccount) {
	//根据登录用户获得供应商信息
	String requirementUUID = null, status = null;
	if (form != null) {
	  requirementUUID = form.getRequirementUUID();
	  status = form.getStatus();
	}
	Enterprise enterprise = enterpriseRepository.findByUserAccountId(userAccount.getId());
	Page<RequirementBidding> requirementBiddingPage = requirementBiddingRepository.findAll(
		RequirementSpecifications.searchRequirementBidding(requirementUUID, status, enterprise), pageable);

	return DTOUtils.mapPage(requirementBiddingPage, RequirementBiddingDetails.class);
  }

  /**
   * 供应商接单
   */
  @Deprecated
  public Map<String, String> receiveRequirement(String requirementUUID, String requirementBiddingUUID) {
	//Assert.notNull(requirementUUID, "uuid for requirement cannot null");
	Assert.notNull(requirementBiddingUUID, "requirementBiddingId for requirement cannot null");

	Map<String, String> result = Maps.newHashMap();
	//校验：当前用户是否可以对当前需求进行接单
	//根据当前用户获得当前用户对应的企业信息，然后根据企业id，如
	//requirementBiddingRepository.updateRequirementBiddingByIdsAndStatus(id, requirementBiddingId, enterpriseId,
	//RequirementBidding.BiddingStatus.RECEIVED.name());
	requirementBiddingRepository.updateRequirementBiddingByUuidsAndStatus(requirementBiddingUUID,
																		  RequirementBidding.BiddingStatus.RECEIVED);
	result.put("result", "success");
	result.put("message", "供应商完成接单。");
	return result;
  }

  /**
   * 供应商报价并接单
   * 当需求不在下架或者截标状态时，以及配单是当前供应商所有时， 方可进行报价并接单功能
   */
  public Map<String, String> price4Requirement(String requirementUUID, String requirementBiddingUUID, RequirementBiddingForm form,
											   UserAccount userAccount) {
	//Assert.notNull(requirementUUID, "id for requirement cannot null");
	Assert.notNull(requirementBiddingUUID, "requirementBiddingId for requirement cannot null");
	Assert.notNull(form, "form of receiving cannot null");

	String errMsg = RequirementUtils.isLegalForm(form);
	if (errMsg != null) {
	  throw new InvalidParameterException(errMsg);
	}
	//校验：当前用户是否可以对当前需求进行报价
	RequirementBidding bidding = requirementBiddingRepository.findByUuidAndActive(requirementBiddingUUID, true);
	if (bidding == null) {
	  throw new RequirementException("配单不存在");
	}
	if (!userAccount.getId().equals(bidding.getEnterprise().getUserAccount().getId())) {
	  throw new RequirementException("访问被拒绝，配单用户信息跟当前用户不一致");
	}
	if (!isActiveRequirement(bidding.getRequirement())) {
	  throw new RequirementException("标的已经截标或已经下架, 无法进行其他操作");
	}
	if (RequirementBidding.PayStatus.NO_PAID.equals(bidding.getPayStatus())) {
	  throw new RequirementException("标的未支付,无法进行报价");
	}

	Map<String, String> result = Maps.newHashMap();
	BigDecimal price = form.getPrice();
	String mark = form.getMark();
	String attachUrl = form.getAttachUrl();
	requirementBiddingRepository.updateRequirementBiddingPriceByIds(requirementBiddingUUID, price, mark, attachUrl,
																	RequirementBidding.BiddingStatus.RECEIVED);
	result.put("result", "success");
	result.put("message", String.format("供应商报价：%s，备注：%s, 附件地址：%s", price, mark, attachUrl));

	return result;
  }

  /**
   * 根据UUID查看投标信息
   */
  public RequirementBiddingOnlyDetails view4RequirementBidding(String requirementBiddingUUID) {
	Assert.notNull(requirementBiddingUUID, "bidding uuid can not be null");

	RequirementBidding requirementBidding = requirementBiddingRepository.findByUuidAndActive(requirementBiddingUUID, true);
	if (requirementBidding == null) {
	  throw new RequirementException("配单不存在");
	}
	if (!isActiveRequirement(requirementBidding.getRequirement())) {
	  throw new RequirementException("标的已经截标或已经下架, 无法进行其他操作");
	}
	return DTOUtils.map(requirementBidding, RequirementBiddingOnlyDetails.class);
  }

  /**
   * Common
   */
  /**
   * 获得需求分类
   */
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public List<Object> getRequirementCategories() {
	List<Object> difCates = Lists.newArrayList();

	ServiceCategory category = serviceCategoryRepository.findByCode("PART");
	List<ServiceCategory> partCategories = serviceCategoryRepository.findByParentCategory(category);
	List<ServiceCategoryDetails> detailsList = DTOUtils.mapList(partCategories, ServiceCategoryDetails.class);
	Map<String, Object> categories2 = Maps.newHashMap();
	categories2.put("title", "标的类型");
	categories2.put("categories", detailsList);
	difCates.add(categories2);

	return difCates;
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public List<Object> getRequirementServer(){
	List<Object> difCates = Lists.newArrayList();

	List<ServiceCategory> serviceCategories = serviceCategoryRepository.findAll();
	List<Map<String, Object>> categories = Lists.newArrayList();
	final Map<String, List<ServiceCategory>>  parentMap = serviceCategories.stream().filter(item -> item.getParentCategory() != null).collect(
		Collectors.groupingBy(item -> item.getParentCategory().getCode()));
	Map<String, ServiceCategory> serviceCategoryMap = serviceCategories.stream().filter(item -> item.getParentCategory() == null).collect(
		Collectors.toMap(item -> item.getCode(), item -> item));

	serviceCategoryMap.forEach((String key, ServiceCategory value) ->{
	  Map<String, Object> categoryMap = Maps.newHashMap();
	  categoryMap.put("title", value.getName());
	  categoryMap.put("code", value.getCode());
	  categoryMap.put("id", value.getId());

	  List<ServiceCategory> childList = parentMap.get(key);
	  List<ServiceCategory> newChildList = Lists.newArrayList();
	  if(childList != null && !childList.isEmpty()){
		childList.forEach( (child) ->{
		  if("MATERIAL".equals(child.getCode())){
		    return;
		  }
		  child.setParentCategory(null);
		  newChildList.add(child);
		} );
	  }
	  categoryMap.put("child", newChildList);


	  categories.add(categoryMap);
	} );

	Map<String, Object> categories2 = Maps.newHashMap();
	categories2.put("title", "标的类型");
	categories2.put("categories", categories);
	difCates.add(categories2);
	return difCates;
  }

  /**
   * 获得当前最新发布的size条需求
   */
  @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
  public List<Object> topN(int size) {
	try {
	  Object topObj = cacheService.get("top");
	  if (topObj != null) {
		List<Object> top = objectMapper.readValue(topObj.toString(), List.class);
		return top;
	  }

	  size = size <= 0 ? 10 : size;
	  PageRequest page = new PageRequest(0, size, Sort.Direction.DESC, "createdDate");
	  Page<Requirements> requirementPages = requirementRepository.findAll(
		  RequirementSpecifications.searchRequirement(null, null), page);

	  List<Object> top = Lists.newArrayList();
	  Map<String, String> contacts = Maps.newHashMap();
	  requirementPages.getContent().forEach(requirements -> {
		UserProfile userProfile = requirements.getUserProfile();
		if (userProfile != null && userProfile.getUserCertification() != null
			&& !contacts.containsKey(userProfile.getUserCertification().getContacts())) {
		  UserCertification userCertification = userProfile.getUserCertification();
		  String contact = userCertification.getContacts();
		  Map<String, String> ele = getEle(requirements.getCreatedDate(), userCertification);
		  if (ele == null) {
			return;
		  }
		  top.add(ele);
		  contacts.put(contact, contact);
		}
	  });

	  cacheService.set("top", objectMapper.writeValueAsString(top), 2 * 3600);
	  return top;
	} catch (Exception ex) {
	  log.error("list to string is error", ex);
	  return null;
	}
  }

  private Map<String, String> getEle(LocalDateTime createdDate, UserCertification userCertification) {
	String contact = userCertification.getContacts();
	String phone = userCertification.getPhone();
	if (StringUtils.isBlank(contact) || StringUtils.isBlank(phone)) {
	  return null;
	}

	if (contact.length() == 1) {
	  contact += "*";
	} else if (contact.length() == 2) {
	  contact = contact.charAt(0) + "*";
	} else {
	  contact = contact.substring(0, 2) + "*";
	}

	phone = String.format("%s****%s", StringUtils.substring(phone, 0, 3), StringUtils.substring(phone, -4));
	Map<String, String> ele = Maps.newHashMap();
	ele.put("contact", contact);
	ele.put("phone", phone);
	ele.put("createdDate", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDate));
	return ele;
  }

  /**
   * 根据配单uuid生成订单
   * 1.供应商信息不一致，不能接单；
   * 2.需求下架或者截标，不能接单。
   *
   * 当前配单未进行支付时，查看是否有订单存在，订单不存在时，生成新的订单； 订单存在时，判断订单是否超时， 超时时关闭现有订单，生成新的订单；未超时，使用现有订单。
   * 如果当前标书价格为0时，配单、流水号、订单标记为成功状态；如果当前标书价格大于0时，生成支付宝所需的参数并返回
   */
  public Map<String, String> generateOrderForBid(String requirementBiddingUUID, UserAccount userAccount) {
	Assert.notNull(requirementBiddingUUID, "bidding uuid can not be null");

	RequirementBidding requirementBidding = requirementBiddingRepository.findByUuidAndActive(requirementBiddingUUID, true);
	if (requirementBidding == null) {
	  throw new RequirementException(requirementBiddingUUID + "的需求不存在");
	}
	if (!userAccount.getId().equals(requirementBidding.getEnterprise().getUserAccount().getId())) {
	  throw new RequirementException("用户信息不一致");
	}
	if (!isActiveRequirement(requirementBidding.getRequirement())) {
	  throw new RequirementException("标的已经截标或已经下架, 无法进行其他操作");
	}

	Requirements requirements = requirementBidding.getRequirement();
	if (!RequirementBidding.PayStatus.PAID.equals(requirementBidding.getPayStatus())) {
	  PaymentForm paymentForm = new PaymentForm();
	  //如果未支付，查看是否有订单存在
	  List<OrderItem> orderItems = orderItemRepository.findByProductIdAndActive(requirementBiddingUUID, true);
	  // 1.订单不存在时, 生成新的订单
	  if (orderItems == null || orderItems.size() < 1) {
		OrderSN orderSN = orderService.placeOrder(userAccount,
												  new OrderForm(requirements.getPrice(), PurchaseOrder.Type.REQUIREMENT.name(),
																requirementBiddingUUID));
		paymentForm.setSerialNumber(orderSN.getSerialNumber());
	  } else {//订单存在时,
		OrderItem orderItem = orderItems.get(0);
		PurchaseOrder order = orderItem.getPurchaseOrder();

		//1. 判断订单是否超时;
		// a. 超时时, 关闭现有订单, 重新生成新订单
		// b. 未超时，使用现有订单
		if (LocalDateTime.now().isAfter(order.getPlacedDate().plusMinutes(28L))) {
		  //关闭现有订单
		  orderRepository.updateStatus(order.getId(), PurchaseOrder.Status.CANCELED);
		  orderItemRepository.updateActiveByUUID(requirementBiddingUUID);

		  //重新生成新的订单
		  OrderSN orderSN = orderService.placeOrder(userAccount,
													new OrderForm(requirements.getPrice(), PurchaseOrder.Type.REQUIREMENT.name(),
																  requirementBiddingUUID));
		  paymentForm.setSerialNumber(orderSN.getSerialNumber());
		} else {
		  paymentForm.setSerialNumber(order.getSerialNumber());
		}
	  }

	  if (new BigDecimal(0).compareTo(requirements.getPrice()) >= 0) {
		requirementBiddingRepository.updateRequirementBiddingByPaystatus(RequirementBidding.PayStatus.PAID,
																		 requirementBiddingUUID);

		//订单
		PurchaseOrder purchaseOrder = orderRepository.findBySerialNumber(paymentForm.getSerialNumber());
		purchaseOrder.setPaidDate(LocalDateTime.now());
		purchaseOrder.setStatus(PurchaseOrder.Status.PAID);
		orderRepository.save(purchaseOrder);

		//流水
		TransactionLog transactionLog = transactionLogRepository.findBySerialNumber(paymentForm.getSerialNumber());
		transactionLog.setStatus(TransactionLog.Status.SUCCESS);
		transactionLog.setTransactedDate(LocalDate.now());
		transactionLogRepository.save(transactionLog);
	  } else {
		//标书价格大于0, 走支付宝
		paymentForm.setSubject(String.format("标书：%s", requirements.getName()));
		paymentForm.setTotalAmount(requirements.getPrice());
		paymentForm.setSubjectDescription("标书价格");

		//Map<String, String> paymentParams = AlipayConfig.buildRequestParams(paymentForm);
		Map<String, String> paymentParams = AlipayConfig.buildRequestParams(alipayConfigDetails, paymentForm);
		log.debug(">>>> demo html: {}", AlipaySubmit.buildRequest(paymentParams, "post", ""));
		return paymentParams;
	  }
	}

	Map<String, String> result = Maps.newHashMap();
	result.put("msg", "已支付标书");
	result.put("result", "success");

	return result;
  }

  /**
   * 供应商查看标书
   * 1.不存在不可见；
   * 2.用户不一致不可见；
   * 3.未支付不可见。
   */
  public Map<String, Object> view4Requirement(String uuid, UserAccount userAccount) {
	Assert.notNull(uuid, "uuid for requirement bidding can not be null");
	RequirementBidding bidding = requirementBiddingRepository.findByUuidAndActive(uuid, true);
	if (bidding == null) {
	  throw new RequirementException("配单不存在");
	}

	if (!userAccount.getId().equals(bidding.getEnterprise().getUserAccount().getId())) {
	  throw new RequirementException("访问被拒绝，配单用户信息跟当前用户不一致");
	}

	Map<String, Object> result = Maps.newHashMap();

	Requirements requirement = bidding.getRequirement();
	//服务类型列表
	List<RequirementCategory> categories = requirementCategoryRepository.findByRequirementId(requirement.getId());
	List<ServiceCategory> serviceCategories = categories.stream().map(RequirementCategory::getServiceCategory).collect(toList());
	List<ServiceCategoryDetails> serviceCategoryDetails = DTOUtils.mapList(serviceCategories, ServiceCategoryDetails.class);
	if(requirement.getCategoryType() != null){
	  serviceCategoryDetails.add(getOldDetail(requirement.getCategoryType().name()));
	}

	//供应商支付此配单
	if (RequirementBidding.PayStatus.PAID.equals(bidding.getPayStatus())) {
	  RequirementDetails requirementDetails = DTOUtils.map(requirement, RequirementDetails.class);
	  requirementDetails.setServiceCategoryDetailsList(serviceCategoryDetails);

	  result.put("requirementDetails", requirementDetails);
	  result.put("payStatus", bidding.getPayStatus());
	} else {
	  RequirementDetails requirementDetails = DTOUtils.map(requirement, RequirementDetails.class);
	  UserProfileDetail userProfileDetailNew = new UserProfileDetail();
	  userProfileDetailNew.setAddress("暂不可见");
	  userProfileDetailNew.setContacts("隐藏");
	  userProfileDetailNew.setPhone("隐藏");
	  userProfileDetailNew.setName("企业名称暂不可见");
	  userProfileDetailNew.setLogo(this.staticImg);
	  requirementDetails.setUserProfile(userProfileDetailNew);
	  requirementDetails.setAttachFile(null);//附件不可见
	  requirementDetails.setServiceCategoryDetailsList(serviceCategoryDetails);

	  result.put("requirementDetails", requirementDetails);
	  result.put("payStatus", RequirementBidding.PayStatus.NO_PAID);
	}

	return result;
  }

  private boolean isActiveRequirement(Requirements requirement) {
	if (Requirements.Status.BE_FINISHED.equals(requirement.getStatus())
		|| Requirements.Status.FINISHED.equals(requirement.getStatus())) {
	  return false;
	}
	return true;
  }

  public ServiceCategoryDetails getOldDetail(String code){
	ServiceCategoryDetails detail = new ServiceCategoryDetails();
	detail.setCode(code);
	detail.setName(RequirementUtils.categoryNameMap.get(detail.getCode()));

	return detail;
  }

  /**
   * test
   */
  public <T> T test() {
	//设置匹配的供应商地域，认证，企业类型
	EnterpriseCriteriaMultiCategories criteria = new EnterpriseCriteriaMultiCategories();
	//rule 1: 认证企业
	criteria.setVerifyStatus("APPROVED");

	//rule 2: 市区
	criteria.setCityCode("330200");

	//rule 3: 企业类型
	Map<String, List<Long>> categoryRelated = (Map<String, List<Long>>) cacheService.get("ENTERPRISE_CATEGORY_01");
	if (categoryRelated == null) {
	  categoryRelated = categoryRelated(industryCategoryRepository.findAll());
	}
	criteria.setCategory(categoryRelated.get("MATERIAL"));

	return (T) enterpriseService.searchEnterprise(criteria, new PageRequest(1, 50));
  }
}
