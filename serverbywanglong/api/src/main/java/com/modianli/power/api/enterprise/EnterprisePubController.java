package com.modianli.power.api.enterprise;

import com.google.common.collect.Maps;

import com.modianli.power.RedisKeyConstants;
import com.modianli.power.common.exception.InvalidRequestException;
import com.modianli.power.common.service.CaseService;
import com.modianli.power.common.service.EnterpriseAddressService;
import com.modianli.power.common.service.EnterpriseQualificationService;
import com.modianli.power.common.service.EnterpriseService;
import com.modianli.power.common.service.MessageService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.ApiErrors;
import com.modianli.power.model.AreaDetails;
import com.modianli.power.model.CasePubDetails;
import com.modianli.power.model.CityDetails;
import com.modianli.power.model.EnterpriseCount;
import com.modianli.power.model.EnterpriseCriteria;
import com.modianli.power.model.EnterpriseDetails;
import com.modianli.power.model.EnterpriseListDetails;
import com.modianli.power.model.EnterpriseQualificationMiddleDetails;
import com.modianli.power.model.MessageDetails;
import com.modianli.power.model.MessageForm;
import com.modianli.power.model.ProvinceDetails;
import com.modianli.power.model.QualificationLastDetails;
import com.modianli.power.model.QualificationTopDetails;
import com.modianli.power.model.RecommendEnterprisePubDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

@RestController
@RequestMapping(value = ApiConstants.URI_API_PUBLIC + ApiConstants.URI_ENTERPRISE)
@Api(description = "企业前台管理")
public class EnterprisePubController {

  private static final Logger log = LoggerFactory.getLogger(EnterprisePubController.class);

  @Inject
  private EnterpriseService enterpriseService;

  @Inject
  private EnterpriseQualificationService qualificationService;
  @Inject
  private MessageService messageService;
  @Inject
  private EnterpriseAddressService addressService;
  @Inject
  private CaseService caseService;

  @PostMapping(value = "/search")
  @ResponseBody
  @ApiOperation(value = "获取所有的企业列表")
  public ResponseEntity<Page<EnterpriseListDetails>> getEnterpriseList(@RequestBody EnterpriseCriteria criteria,
																	   @PageableDefault(page = 0, size = 10, sort = {
																		   "id"}, direction = Sort.Direction.DESC) Pageable page) {
	log.debug("criteria {} pageable {} ", criteria, page);
	Page<EnterpriseListDetails> enterpriseList = enterpriseService.searchEnterprise(criteria, page);
	return new ResponseEntity<>(enterpriseList, HttpStatus.OK);
  }

  @GetMapping(value = "/{uuid}")
  @ApiOperation(value = "查询企业详情")
  public ResponseEntity<EnterpriseDetails> getEnterpriseDetail(@PathVariable("uuid") String uuid) {
	log.debug("uuid {} ", uuid);
	EnterpriseDetails enterpriseInfoDetail = enterpriseService.findEnterpriseByUuid(uuid);
	return new ResponseEntity<>(enterpriseInfoDetail, HttpStatus.OK);
  }

  @GetMapping("/top_qualification")
  @ApiOperation(value = "查询一级认证分类")
  public List<QualificationTopDetails> getQualificationTopDetails() {

	return qualificationService.getQualificationTopCategory();
  }

  @GetMapping(value = "/middle_qualification/{parentId}")
  @ResponseBody
  @ApiOperation(value = "查询二级认证分类")
  public List<EnterpriseQualificationMiddleDetails> getQualificationMiddleDetails(@PathVariable("parentId") Long parentId) {

	log.debug("input parentId {}", parentId);

	return qualificationService.getQualificationMiddleCategory(parentId);
  }

  @GetMapping("/last_qualification/{parentId}")
  @ResponseBody
  @ApiOperation(value = "查询三级认证分类")
  public List<QualificationLastDetails> getQualificationLastDetails(@PathVariable("parentId") Long parentId) {

	log.debug("input parentId {}", parentId);

	return qualificationService.getQualificationLastCategory(parentId);
  }

  @RequestMapping(value = "/message", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "创建留言")
  public ResponseEntity<Void> createMessage(@RequestBody @Valid MessageForm form, BindingResult errors,
											UriComponentsBuilder uriComponentsBuilder) {

	log.debug("save MessageForm data {}", form);

	if (errors.hasErrors()) {
	  throw new InvalidRequestException(ApiErrors.INVALID_PASSWORD, errors);
	}

	MessageDetails messageDetails = messageService.saveMessages(form);
	HttpHeaders headers = new HttpHeaders();
	headers.setLocation(uriComponentsBuilder
							.path(ApiConstants.URI_API_PUBLIC + ApiConstants.URL_MESSAGE + "/{id}")
							.buildAndExpand(messageDetails.getId()).toUri());
	return new ResponseEntity<>(headers, HttpStatus.CREATED);

  }

  @GetMapping("/province")
  @ApiOperation(value = "获取省份")
  @ResponseBody
  @Cacheable(value = RedisKeyConstants.CACHE_KEY + "getProvince", key = "#root.method.name")
  public List<ProvinceDetails> getProvince() {

	List<ProvinceDetails> provinces = addressService.getProvince();

	return provinces;
  }

  @GetMapping(value = "/city/{parentId}")
  @ResponseBody
  @ApiOperation(value = "查询省份下的城市")
  @Cacheable(value = RedisKeyConstants.CACHE_KEY + "getCity", key = "#parentId")
  public List<CityDetails> getCity(@PathVariable("parentId") Long parentId) {
	log.debug("input parentId {}", parentId);
	return addressService.getCity(parentId);

  }

  @GetMapping(value = "/area/{parentId}")
  @ResponseBody
  @ApiOperation(value = "查询城市下的地区")
  @Cacheable(value = RedisKeyConstants.CACHE_KEY + "getArea", key = "#parentId")
  public List<AreaDetails> getArea(@PathVariable("parentId") Long parentId) {
	log.debug("input parentId {}", parentId);
	return addressService.getArea(parentId);

  }

  @GetMapping(value = "/recommend")
  @ResponseBody
  @ApiOperation(value = "查询推荐企业")
  public List<RecommendEnterprisePubDetails> recommendEnterprise(@RequestParam(required = false) String type) {
	log.debug("find recommend Enterprise ");
	return enterpriseService.getPubRecommendEnterprise(type);
  }

  @GetMapping(value = "{uuid}/cases")
  @ApiOperation(value = "查询企业案例")
  public ResponseEntity<List<CasePubDetails>> searchCase(@PathVariable("uuid") String uuid) {
	log.debug("enterprise uuid @{}", uuid);

	return new ResponseEntity<>(caseService.searchPubCase(uuid), HttpStatus.OK);
  }

  @GetMapping(value = "/enterprise_number")
  @ApiOperation(value = "按地区查看企业数量")
  public ResponseEntity<List<EnterpriseCount>> searchEnterpriseNumber() {

	return new ResponseEntity<>(enterpriseService.searchEnterpriseNumber(), HttpStatus.OK);
  }

  @GetMapping(value = "counts")
  @ApiOperation(value = "有效的企业数量")
  public Map<String, Long> activeCounts() {
	Map<String, Long> map = Maps.newHashMap();
	Long count = enterpriseService.findActiveCount();
	map.put("count", count);
	return map;
  }


}
