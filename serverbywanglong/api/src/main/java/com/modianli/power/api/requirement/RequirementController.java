package com.modianli.power.api.requirement;

import com.google.common.collect.Maps;

import com.modianli.power.api.security.CurrentUser;
import com.modianli.power.common.exception.InvalidRequestException;
import com.modianli.power.common.service.RequirementService;
import com.modianli.power.common.utils.MsgUtils;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.ApiErrors;
import com.modianli.power.model.RequirementBiddingForm;
import com.modianli.power.model.RequirementForm;
import com.modianli.power.model.RequirementQueryForm;
import com.modianli.power.model.RequirementValidatedForm;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by haijun on 2017/3/2.
 */
@RestController
@Slf4j
@Api(description = "需求API(登录)")
@RequestMapping(ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT)
public class RequirementController {

  @Inject
  private RequirementService requirementService;

  /**
   * 企业方
   * 1.发布需求； 2.选择供应商； 3.服务完成以及评价
   * 4.企业是否认证...
   * 5.查看我的需求(s)
   */

  /**
   * 新增发布需求
   */
  @ApiOperation(value="企业方:发布需求")
  @RequestMapping(value = ApiConstants.URI_PROFILE, method = RequestMethod.POST)
  public ResponseEntity<?> createRequirement(@RequestBody @Valid RequirementForm form, BindingResult errors,
											 @CurrentUser UserAccount userAccount){
	log.debug("save requirement data @{}", form);

	//当前用户是否有企业role
	/*if( !( ApiConstants.TYPE_USER.equals(userAccount.getType().name())) ){
	  throw new AccessDeniedException("");
	}*/

	//字段校验是否有错
	if (errors.hasErrors()) {
	  throw new InvalidRequestException(ApiErrors.INVALID_REQUEST, errors);
	}

	//企业是否认证
	/*if( !"AUTHORIZED".equals(requirementService.auth(userAccount)) ){
	  throw new AccessDeniedException("企业未认证成功");
	}*/

	//返回结果处理
	return ResponseEntity.ok(requirementService.saveRequirement(form, userAccount));
  }

  /**
   * 企业方选择供应商
   */
  @ApiOperation(value="企业方:选择供应商")
  @RequestMapping(value = ApiConstants.URI_PROFILE + "/{requirementUUID}/{requirementBiddingUUID}", method = RequestMethod.PUT)
  public ResponseEntity<?> chooseEnterprise(@ApiParam(value = "需求ID", required = true) @PathVariable("requirementUUID") String requirementUUID,
											@ApiParam(value = "竞标信息ID", required = true) @PathVariable("requirementBiddingUUID") String requirementBiddingUUID,
											@CurrentUser UserAccount userAccount){
	log.debug("choose requirement data @{}， @{}", requirementUUID, requirementBiddingUUID);

	//当前用户是否有企业role
	/*if( !ApiConstants.TYPE_USER.equals(userAccount.getType().name()) ){
	  throw new AccessDeniedException("");
	}*/

	return ResponseEntity.ok(requirementService.chooseEnterprise(requirementUUID, requirementBiddingUUID, userAccount));
  }

  /**
   * 认证状态
   */
  @ApiOperation(value="企业方:认证状态")
  @RequestMapping(value = ApiConstants.URI_PROFILE + "/certificate", method = RequestMethod.GET)
  public ResponseEntity<?> auth(@CurrentUser UserAccount userAccount){

    //当前用户是否有企业role
	/*if( !ApiConstants.TYPE_USER.equals(userAccount.getType().name()) ){
	  throw new AccessDeniedException("");
	}*/
	Map<String, String> status = Maps.newHashMap();
	status.put("status", requirementService.auth(userAccount));

	return ResponseEntity.ok( status );
  }

  /**
   * 查看我的需求(s)
   */
  @ApiOperation(value="企业方:分页获得需求", notes = "根据页码需求")
  @RequestMapping(value = ApiConstants.URI_PROFILE + "/page", method = RequestMethod.GET)
  public ResponseEntity<?> getRequirements(@PageableDefault(page = 0, size = 20, direction = Sort.Direction.DESC, sort = {"id"}) Pageable page,
										   @CurrentUser UserAccount userAccount){
	log.debug("get all requirement data.");
	//当前用户是否有企业role
	/*if( !ApiConstants.TYPE_USER.equals(userAccount.getType().name()) ){
	  throw new AccessDeniedException("");
	}*/
	return ResponseEntity.ok(requirementService.getRequirements(page, userAccount));
  }

  /**
   * 魔电后台需求相关
   * 1.查看&审核；2.上架；3.配单
   */

  /**
   *魔电后台查看需求
   */
  @ApiOperation(value="魔电后台:查看需求")
  @RequestMapping(value = ApiConstants.URI_SELF, method = RequestMethod.GET)
  public ResponseEntity<?> getRequirementsViaCondition(@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
													   @RequestParam(value = "status", required = false) String status, @CurrentUser UserAccount userAccount,
													   @PageableDefault(page = 0, size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
	log.debug("get requirement data @uuid:{} status:{}, current user {}", requirementUUID, status , userAccount);

	//是否有魔电后台操作权限
	/*if( !ApiConstants.TYPE_STAFF.equals(userAccount.getType().name()) ){
	  throw new AccessDeniedException("");
	}*/

	return ResponseEntity.ok(requirementService.getRequirementsViaMDLCondition(requirementUUID, status, pageable));
  }

  @ApiOperation(value="魔电后台:查看某一需求")
  @RequestMapping(value = ApiConstants.URI_SELF + "/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getRequirement(@PathVariable(value = "id") Long id, @CurrentUser UserAccount userAccount){
    return ResponseEntity.ok(requirementService.getRequirement(id));
  }

  @ApiOperation(value="魔电后台:查看某一需求的配单")
  @RequestMapping(value = ApiConstants.URI_SELF + "/bidding/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getRequirementBidding(@PathVariable(value = "id") Long id,
												 @PageableDefault(page = 0, size = 20, direction = Sort.Direction.DESC, sort = {"id"}) Pageable page,
												 @CurrentUser UserAccount userAccount){
    return ResponseEntity.ok(requirementService.getRequirementBidding(id, page));
  }

  /**
   * 审核需求
   */
  @ApiOperation(value="魔电后台:审核需求")
  @RequestMapping(value = ApiConstants.URI_SELF + "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<?> validateRequirement(@ApiParam(value = "需求ID", required = true) @PathVariable("id") Long id,
											   @CurrentUser UserAccount userAccount, @RequestBody @Valid RequirementValidatedForm form, BindingResult errors){
	log.debug("validate requirement data @{}", form);

	//字段校验是否有错
	if (errors.hasErrors()) {
	  throw new InvalidRequestException(ApiErrors.INVALID_REQUEST, errors);
	}

	//是否有魔电后台操作权限
	/*if( !ApiConstants.TYPE_STAFF.equals(userAccount.getType().name()) ){
	  throw new AccessDeniedException("无操作后台权限");
	}*/
    return ResponseEntity.ok(requirementService.validateRequirement(id, form, userAccount));
  }

  /**
   * 上下架
   */
  @ApiOperation(value="魔电后台:上(下)架")
  @RequestMapping(value = ApiConstants.URI_SELF + "/shelf/{id}/{isShelf}", method = RequestMethod.PUT)
  public ResponseEntity<?> shelfRequirement(@ApiParam(value = "需求ID", required = true) @PathVariable("id") Long id,
											@ApiParam(value = "是否上架", required = true) @PathVariable("isShelf") int isShelf,
											@CurrentUser UserAccount userAccount){
	log.debug("shelf for requirement id @{}， is cancel @{}", id, isShelf);

	//是否有魔电后台操作权限
	/*if( !ApiConstants.TYPE_STAFF.equals(userAccount.getType().name()) ){
	  throw new AccessDeniedException("");
	}*/
	Map<String, Boolean> result = Maps.newHashMap();
	result.put("result", requirementService.shelfRequirement(id, isShelf, userAccount));
	return ResponseEntity.ok( result );
  }

  /**
   * 配单供应商
   */
  @ApiOperation(value="魔电后台:配单功能")
  @RequestMapping(value = ApiConstants.URI_SELF + "/match/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> matchEnterprise(@ApiParam(value = "需求ID", required = true) @PathVariable("id") Long id,
										   @PageableDefault(page = 0, size = 3) Pageable pageable, @CurrentUser UserAccount userAccount){
	log.debug("match requirement id @{}", id);

	//是否有魔电后台操作权限
	/*if( !ApiConstants.TYPE_STAFF.equals(userAccount.getType().name()) ){
	  throw new AccessDeniedException("");
	}*/
	return ResponseEntity.ok(requirementService.matchEnterpriseManual(id, pageable));
  }

  /**
   * 供应商后台相关
   * 1.查看配单； 2.接单；3.报价
   * 4.附件
   * 5.生成支付标书订单
   * 6.看看标书信息
   */
  /**
   * 查看配单
   */
  @ApiOperation(value="供应商:查看配单")
  @RequestMapping(value = ApiConstants.URI_ENTERPRISE, method = RequestMethod.GET)
  public ResponseEntity<?> getMatchRequirements(@RequestBody(required = false) RequirementQueryForm form,
												@PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
												@CurrentUser UserAccount userAccount){
	log.debug("get requirement data @{}", form);

	//当前登录用户是否有供应商的role
	/*if( !ApiConstants.TYPE_ENTERPRISE.equals(userAccount.getType().name()) ){
	  throw new AccessDeniedException("");
	}*/
	return ResponseEntity.ok(requirementService.getRequirementsViaEPCondition(form, pageable, userAccount));
  }

  /**
   * 供应商报价并接单
   */
  @ApiOperation(value="供应商:投标")
  @RequestMapping(value = ApiConstants.URI_ENTERPRISE + "/price/{requirementUUID}/{requirementBiddingUUID}", method = RequestMethod.PUT)
  public ResponseEntity<?> price4Requirement(@ApiParam(value = "需求UUID", required = true) @PathVariable("requirementUUID") String requirementUUID,
											 @ApiParam(value = "竞标信息ID", required = true) @PathVariable("requirementBiddingUUID") String requirementBiddingUUID,
											 @RequestBody(required = false) RequirementBiddingForm form, @CurrentUser UserAccount userAccount){
	log.debug("price for requirement bidding data @{}, @{}, @{}", requirementUUID, requirementBiddingUUID, form);

	//当前登录用户是否有供应商的role
	/*if( !ApiConstants.TYPE_ENTERPRISE.equals(userAccount.getType().name()) ){
	  throw new AccessDeniedException("");
	}*/
	return ResponseEntity.ok( requirementService.price4Requirement(requirementUUID, requirementBiddingUUID, form, userAccount) );
  }

  @ApiOperation(value="供应商:查看投标信息")
  @RequestMapping(value = ApiConstants.URI_ENTERPRISE + "/{requirementBiddingUUID}", method = RequestMethod.GET)
  public ResponseEntity<?> view4RequirementBidding(@ApiParam(value = "竞标信息ID", required = true) @PathVariable("requirementBiddingUUID") String requirementBiddingUUID){
	log.debug("price for requirement bidding data @{}",requirementBiddingUUID);

	return ResponseEntity.ok( requirementService.view4RequirementBidding(requirementBiddingUUID) );
  }


  @ApiOperation(value="供应商:生成标书订单以及返回支付宝所需信息")
  @RequestMapping(value = ApiConstants.URI_ENTERPRISE + "/order/{requirementBiddingUUID}", method = RequestMethod.POST)
  public ResponseEntity<?> generateOrderForBid(@ApiParam(value = "竞标信息UUID", required = true) @PathVariable("requirementBiddingUUID") String requirementBiddingUUID,
											   @CurrentUser UserAccount userAccount){
	log.debug("order for requirement bidding data @{}",requirementBiddingUUID);
    return ResponseEntity.ok(
    	MsgUtils.getResult(requirementService.generateOrderForBid(requirementBiddingUUID, userAccount))
	);
  }

  @ApiOperation(value="供应商:查看标书信息")
  @RequestMapping(value = ApiConstants.URI_ENTERPRISE + "/requirement/{requirementBiddingUUID}", method = RequestMethod.GET)
  public ResponseEntity<?> view4Requirement(@ApiParam(value = "竞标信息UUID", required = true) @PathVariable("requirementBiddingUUID") String requirementBiddingUUID,
											@CurrentUser UserAccount userAccount){
    log.debug("requirement for enterprise @{}", requirementBiddingUUID);
    return ResponseEntity.ok(requirementService.view4Requirement(requirementBiddingUUID, userAccount));
  }
}
