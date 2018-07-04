package com.modianli.power.api.enterprise;

import com.google.common.collect.Maps;

import com.modianli.power.RedisKeyConstants;
import com.modianli.power.api.security.CurrentUser;
import com.modianli.power.common.exception.InvalidRequestException;
import com.modianli.power.common.service.CaseService;
import com.modianli.power.common.service.EnterpriseQualificationService;
import com.modianli.power.common.service.EnterpriseService;
import com.modianli.power.common.service.MessageService;
import com.modianli.power.common.service.RegionService;
import com.modianli.power.common.service.UserService;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.ApiErrors;
import com.modianli.power.model.CaseDetails;
import com.modianli.power.model.CaseForm;
import com.modianli.power.model.CaseSearchForm;
import com.modianli.power.model.CertificateTypeDetails;
import com.modianli.power.model.CertificateTypeForm;
import com.modianli.power.model.CommentForm;
import com.modianli.power.model.EnterpriseCriteria;
import com.modianli.power.model.EnterpriseDetails;
import com.modianli.power.model.EnterpriseForm;
import com.modianli.power.model.EnterpriseListDetails;
import com.modianli.power.model.MessageDetails;
import com.modianli.power.model.MessageSearchForm;
import com.modianli.power.model.RecommendEnterpriseDetails;
import com.modianli.power.model.RecommendEnterpriseForm;
import com.modianli.power.model.RecommendEnterpriseMgtForm;
import com.modianli.power.model.RecommendEnterpriseSortForm;
import com.modianli.power.model.SupplierForm;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_ENTERPRISE)
@Slf4j
@Api(description = "企业后台管理")
public class EnterpriseMgtController {

  @Inject
  private EnterpriseService enterpriseService;

  @Inject
  private UserService userService;

  @Inject
  private EnterpriseQualificationService qualificationService;

  @Inject
  private RegionService regionService;

  @Inject
  private CaseService caseService;

  //  @RequestMapping(method = RequestMethod.GET)
//  @ResponseBody
//  @ApiOperation(value = "获取所有的企业列表")
//  @Deprecated
//  public ResponseEntity<Page<EnterpriseDetails>> getAllEnterprises(@RequestParam("q") String q,
//																   @RequestParam("active") boolean status,
//																   @PageableDefault(page = 0, size = 10, sort = {
//																	   "createdDate"}, direction = Direction.DESC) Pageable page) {
//	log.debug("call getAllEnterprises");
//
//	Page<EnterpriseDetails> result = enterpriseService.findEnterpriseDetailsByKeyword(q, status, page);
//
//	return new ResponseEntity<>(result, HttpStatus.OK);
//  }
//
//  @GetMapping(value = {"/menu"})
//  @ResponseBody
//  @ApiOperation(value = "获取企业菜单")
//  public ResponseEntity<Page<EnterpriseDetails>> getEnterprisesMenu(
//	  @PageableDefault(page = 0, size = 10, sort = {"createdDate"}, direction = Direction.DESC) Pageable page) {
//
//	log.debug("call getEnterprisesMenu");
//
//	Page<EnterpriseDetails> result = enterpriseService.findEnterpriseDetailsByKeyword(page);
//
//	return new ResponseEntity<>(result, HttpStatus.OK);
//  }
//
//  @RequestMapping(value = {"/selectmenu"}, method = RequestMethod.GET)
//  @ResponseBody
//  @ApiOperation(value = "根据名称获取企业菜单")
//  public ResponseEntity<Page<EnterpriseDetails>> getEnterpriseMenuByName(@RequestParam("name") String name,
//																		 @PageableDefault(page = 0, size = 100, sort = {
//																			 "createdDate"}, direction = Direction.DESC) Pageable page) {
//	log.debug("get enterprise data by name @ {}", name);
//	Page<EnterpriseDetails> enterprises = enterpriseService.findEnterpriseByName(name, page);
//
//	return new ResponseEntity<>(enterprises, HttpStatus.OK);
//  }
  @Inject
  private MessageService messageService;

  @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "根据条件查询企业")
  public ResponseEntity<Page<EnterpriseListDetails>> searchEnterprise(@RequestBody EnterpriseCriteria criteria,
																	  @PageableDefault(page = 0, size = 20, sort = {
																		  "id"}, direction = Direction.DESC) Pageable page) {
	log.debug("get all enterprise by  search@ {}", criteria);
	log.debug("criteria {} pageable {} ", criteria, page);

	Page<EnterpriseListDetails> enterpriseList = enterpriseService.searchBackEndEnterprise(criteria, page);
	return new ResponseEntity<>(enterpriseList, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "创建企业")
  public ResponseEntity<Void> createEnterprise(@RequestBody @Valid EnterpriseForm form, BindingResult errors
	  , UriComponentsBuilder uriComponentsBuilder) {
	log.debug("save enterpriseBaseInfoForm data {}", form);

	if (errors.hasErrors()) {
	  throw new InvalidRequestException(ApiErrors.INVALID_REQUEST, errors);
	}

	Enterprise enterprise = enterpriseService.saveEnterprise(form);
	HttpHeaders headers = new HttpHeaders();
	headers.setLocation(uriComponentsBuilder
							.path(ApiConstants.URI_API_PUBLIC + ApiConstants.URI_ENTERPRISE + "/{id}")
							.buildAndExpand(enterprise.getId()).toUri());
	return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "根据id修改企业信息")
  public ResponseEntity<Void> updateEnterprise(@PathVariable("id") Long id, @RequestBody @Valid EnterpriseForm form,
											   BindingResult errors) {

	if (log.isDebugEnabled()) {
	  log.debug("EnterpriseForm @ {}", form);
	}

	if (errors.hasErrors()) {
	  throw new InvalidRequestException(ApiErrors.INVALID_REQUEST, errors);
	}

	enterpriseService.updateEnterprise(id, form);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
  @ResponseBody
  @ApiOperation(value = "删除企业")
  public ResponseEntity<Void> deleteEnterprise(@PathVariable("id") Long id) {

	if (log.isDebugEnabled()) {
	  log.debug("soft delete enterprise @{}, deactivate enterprise not delete it", id);
	}

	enterpriseService.deactivateEnterprise(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, params = {"action=RESTORE"})
  @ResponseBody
  @ApiOperation(value = "恢复企业")
  public ResponseEntity<Void> restoreEnterprise(@PathVariable("id") Long id) {

	log.debug("id @ {} ", id);

	enterpriseService.restoreEnterprise(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/normal"}, method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "软删除企业")
  @Deprecated
  public ResponseEntity<Void> enableEnterprise(@RequestParam("id") Long id) {

	if (log.isDebugEnabled()) {
	  log.debug("soft enable enterprise @ {}, enterprise not enable it", id);
	}

	enterpriseService.enableEnterprise(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

//  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, params = {"action=APPROVE"})
//  @ResponseBody
//  public ResponseEntity<Void> updatePassEnterprise(@PathVariable("id") Long id) {
//
//	if (log.isDebugEnabled()) {
//	  log.debug("id @" + id);
//	}
//
//	enterpriseService.updatePassEnterprise(id);
//
//	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }
//
//  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, params = {"action=REJECT"})
//  @ResponseBody
//  public ResponseEntity<Void> updateRejectEnterprise(@PathVariable("id") Long id, @RequestBody EnterpriseForm form) {
//
//	if (log.isDebugEnabled()) {
//	  log.debug("id @" + id + ",verifyCause @" + form.getVerifyCause());
//	}
//
//	enterpriseService.updateRejectEnterprise(id, form.getVerifyCause());
//
//	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }
//
//  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, params = {"action=DISCARD"})
//  @ResponseBody
//  public ResponseEntity<Void> updateDiscardEnterprise(@PathVariable("id") Long id, @RequestBody EnterpriseForm form) {
//
//	if (log.isDebugEnabled()) {
//	  log.debug("id @" + id + ",verifyCause @" + form.getVerifyCause());
//	}
//
//	enterpriseService.updateDiscardEnterprise(id, form.getVerifyCause());
//
//	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }
//
//  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, params = {"action=ACCOUNT"})
//  @ResponseBody
//  public ResponseEntity<Void> createAccount(@PathVariable("id") Long id) {
//
//	if (log.isDebugEnabled()) {
//	  log.debug("id @" + id);
//	}
//
//	//        enterpriseService.updateDiscardEnterprise(id, form.getVerifyCause());
//
//	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "查询企业详情")
  public ResponseEntity<EnterpriseDetails> getEnterprise(@PathVariable("id") Long id) {

	if (log.isDebugEnabled()) {
	  log.debug("get enterprise data by id @ {}", id);
	}

	EnterpriseDetails enterprise = enterpriseService.findEnterpriseById(id);

	return new ResponseEntity<>(enterprise, HttpStatus.OK);
  }

  @RequestMapping(value = {"/registerSupplier"}, method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "注册供应商账户")
  public ResponseEntity<Void> registerSupplier(SupplierForm supplierForm) {

	log.debug("registerSupplier form @ {}", supplierForm);

	userService.registerSupplier(supplierForm);

	return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = {"/certificateTypes"}, method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "根据状态获取认证类型")
  public ResponseEntity<List<CertificateTypeDetails>> certificateTypes(Boolean active) {

	log.debug("get certificateTypes active@{}", active);
	return ResponseEntity.ok(enterpriseService.getCertificateTypes(active));
  }

  @RequestMapping(value = {"/certificateType"}, method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "创建认证类型")
  public ResponseEntity<Void> createCertificateType(@RequestBody CertificateTypeForm form) {

	log.debug("createCertificateType form@{}", form);

	enterpriseService.saveCertificateType(form);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/certificateType/{id}"}, method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "认证类型详情")
  public ResponseEntity<Void> certificateType(@PathVariable("id") Long id, @RequestBody CertificateTypeForm form) {

	log.debug("createCertificateType form@{}", form);

	enterpriseService.updateCertificateType(id, form);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = "/password/{id}")
  @ResponseBody
  @ApiOperation(value = "重置供应商密码")
  public ResponseEntity<Void> resetPassword(@PathVariable Long id) {
	log.debug("enterprise id@{}", id);

	userService.updatePassword(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping(value = "{id}/comment")
  @ResponseBody
  @ApiOperation(value = "提交企业评论")
  public ResponseEntity<Void> commitComment(@PathVariable Long id, @RequestBody CommentForm form,
											@CurrentUser UserAccount userAccount) {
	enterpriseService.saveComment(id, form, userAccount);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = "recommend")
  @ApiOperation(value = "推荐企业添加")
  public ResponseEntity<Void> createRecommendEnterprise(@RequestBody RecommendEnterpriseMgtForm form) {
	log.debug("create recommendEnterprise @ {}", form);

	enterpriseService.saveRecommendEnterprise(form);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(value = "/recommend/{id}")
  @ApiOperation(value = "删除推荐企业")
  public ResponseEntity<Void> deleteRecommendEnterprise(@PathVariable("id") Long id) {

	log.debug("delete recommend enterprise id @{}", id);
	enterpriseService.deleteRecommendEnterprise(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = "/recommend/{id}", params = "action=RESTORE")
  @ApiOperation(value = "恢复推荐企业")
  public ResponseEntity<Void> restoreRecommendEnterprise(@PathVariable("id") Long id) {

	log.debug("restore recommend enterprise id @{}", id);
	enterpriseService.restoreRecommendEnterprise(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping(value = "/recommend")
  @ApiOperation(value = "查询推荐企业")
  public ResponseEntity<List<RecommendEnterpriseDetails>> recommendEnterprise(@RequestBody RecommendEnterpriseForm form) {

	log.debug("recommend enterprise form @{}", form);

	return new ResponseEntity<>(enterpriseService.getRecommendEnterprise(form), HttpStatus.OK);
  }

  @PostMapping(value = "/case/search")
  @ApiOperation(value = "查询企业案例")
  public ResponseEntity<Page<CaseDetails>> searchCase(@RequestBody CaseSearchForm form,
													  @PageableDefault(page = 0, size = 10, sort = {
														  "projectTime"}, direction = Direction.DESC) Pageable page) {
	log.debug("search case form @{}", form);

	return new ResponseEntity<>(caseService.searchCase(form, page), HttpStatus.OK);
  }

  @PostMapping(value = "{id}/case")
  @ApiOperation(value = "提交企业案例")
  public ResponseEntity<Void> commitCase(@PathVariable Long id, @RequestBody CaseForm form) {
	log.debug("save case form @{} enterprise id @{}", form, id);

	caseService.saveCase(form, id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(value = "/case/{id}")
  @ApiOperation(value = "删除企业案例")
  public ResponseEntity<Void> deleteCase(@PathVariable Long id) {
	log.debug("delete case  id @{}", id);

	caseService.deleteCase(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = "/case/{id}")
  @ApiOperation(value = "修改企业案例")
  public ResponseEntity<Void> updateCase(@PathVariable Long id, @RequestBody CaseForm form) {
	log.debug("save case form @{} case id @{}", form, id);

	caseService.updateCase(id, form);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = "/case/{id}", params = "action=RESTORE")
  @ApiOperation(value = "恢复企业案例")
  public ResponseEntity<Void> restoreCase(@PathVariable Long id) {
	log.debug("restore case id @{}", id);

	caseService.restoreCase(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = "/case/{id}")
  @ApiOperation(value = "查询企业案例")
  public CaseDetails findCase(@PathVariable Long id) {
	log.debug("findCase id @{} ", id);

	CaseDetails caseDetails = caseService.findOne(id);
	return caseDetails;
  }

//  @GetMapping("/importCreditType")
//  @ApiOperation(value = "导入认证类型")
//  public Map<String,String> importCreditType() {
//	qualificationService.importCreditType();
//	Map<String, String> map = Maps.newHashMap();
//	map.put("status", "OK");
//	return map;
//  }

//  @GetMapping("/importArea")
//  @ApiOperation(value = "导入地区列表")
//  public Map<String,String> importArea() {
//	qualificationService.importArea();
//	Map<String, String> map = Maps.newHashMap();
//	map.put("status", "OK");
//	return map;
//  }

  @GetMapping("/buildProvince")
  @ApiOperation(value = "生成地区的json")
  public Map<String, String> buildProvince() {
	regionService.buildRegionJson();
	Map<String, String> map = Maps.newHashMap();
	map.put("status", "OK");
	return map;
  }

  @RequestMapping(value = {"clearIndustryCategory"}, method = RequestMethod.GET)
  @ApiOperation(value = "清除企业分类缓存")
  @CacheEvict(value = RedisKeyConstants.CACHE_KEY
					  + "findCategory", key = "#findCategory", allEntries = true, beforeInvocation = true)
  public Map<String, String> clearIndustryCategory() {
	log.debug("test");
	Map<String, String> map = Maps.newHashMap();
	map.put("status", "OK");
	return map;
  }

  @RequestMapping(value = "/messages", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "后台查看预算或者咨询")
  public ResponseEntity<Page<MessageDetails>> messages(@RequestBody MessageSearchForm form,
													   @PageableDefault(page = 0, size = 20, sort = {
														   "id"}, direction = Sort.Direction.DESC) Pageable page) {

	log.debug("messages data {}", form);

	return new ResponseEntity<>(messageService.getMessages(form, page), HttpStatus.OK);

  }

  @RequestMapping(value = "recommend", method = RequestMethod.PUT, params = "action=SORT")
  @ResponseBody
  @ApiOperation(value = "热企业排序品排序")
  public ResponseEntity<Void> recommendEnterpriseSort(@RequestBody RecommendEnterpriseSortForm form) {
	if (log.isDebugEnabled()) {
	  log.debug("recommendEnterpriseSort data @ form {}", form);
	}
	enterpriseService.recommendEnterpriseSort(form);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

//  @ApiOperation(value = "企业设备物料类型")
//  @GetMapping(value = "/{id}", params = "action=PRODUCE_CATEGORY")
//  public List<Long> findActiveItemIdByEnterprise(@Param("id") Long id) {
//	return enterpriseService.findActiveItemIdByEnterprise(id);
//  }

}
