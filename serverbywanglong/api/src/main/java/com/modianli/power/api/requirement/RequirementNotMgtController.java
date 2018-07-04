package com.modianli.power.api.requirement;

import com.modianli.power.api.security.CurrentUser;
import com.modianli.power.common.service.RequirementService;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.RequirementQueryForm;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by haijun on 2017/3/27.
 */
@RestController
@Slf4j
@Api(description = "需求API(非必须登录)")
@RequestMapping(ApiConstants.URI_API_PUBLIC)
public class RequirementNotMgtController {

  @Inject
  private RequirementService requirementService;

  /**
   * 根据ID获得当前对应的需求
   *
   * @param uuid
   * @return
   */
  @ApiOperation(value="获得需求", notes = "根据ID获得当前对应的需求")
  @RequestMapping(value =  ApiConstants.URI_REQUIREMENT + "/{uuid}", method = RequestMethod.GET)
  public ResponseEntity<?> getRequirement(@ApiParam(value = "需求UUID", required = true) @PathVariable("uuid") String uuid,
										  @CurrentUser UserAccount userAccount){
	log.debug("get requirement data @{}， current user @{}", uuid, userAccount);
	return ResponseEntity.ok(requirementService.getRequirement(uuid, userAccount));
  }

  @ApiOperation(value="分页获得需求配单信息", notes = "分页获得")
  @RequestMapping(value =  ApiConstants.URI_REQUIREMENT + "/bid/{uuid}", method = RequestMethod.GET)
  public ResponseEntity<?> getRequirementBidding(@ApiParam(value = "需求UUID", required = true) @PathVariable("uuid") String uuid,
												 @PageableDefault(page = 0, size = 20) Pageable page,
												 @CurrentUser UserAccount userAccount){
	log.debug("get requirement data @{}， current user @{}", uuid, userAccount);
	return ResponseEntity.ok(requirementService.getRequirementBidding(uuid, userAccount, page));
  }

  @ApiOperation(value="分页获得需求", notes = "根据页码需求")
  @RequestMapping(value = ApiConstants.URI_REQUIREMENT + "s", method = RequestMethod.POST)
  public ResponseEntity<?> getRequirements(@RequestBody(required = false)RequirementQueryForm form,
										   @PageableDefault(page = 0, size = 20, direction = Sort.Direction.DESC, sort = {"id"}) Pageable page,
										   @CurrentUser UserAccount userAccount){
	log.debug("get all requirement data.");

	return ResponseEntity.ok(requirementService.getRequirements(form, page, userAccount));
  }

  @ApiOperation(value="获得需求分类")
  @RequestMapping(value = ApiConstants.URI_REQUIREMENT + "s/categories", method = RequestMethod.GET)
  public ResponseEntity<?> getRequirementCategories(){
	log.debug("get all requirement data.");

	return ResponseEntity.ok(requirementService.getRequirementCategories());
  }

  @ApiOperation(value="获得服务分类")
  @RequestMapping(value = ApiConstants.URI_REQUIREMENT + "s/server", method = RequestMethod.GET)
  public ResponseEntity<?> getRequirementServer(){
	log.debug("get all requirement data.");

	return ResponseEntity.ok(requirementService.getRequirementServer());
  }

  @ApiOperation(value="获得最新发布的10条记录")
  @RequestMapping(value = ApiConstants.URI_REQUIREMENT + "s/top/{size}", method = RequestMethod.GET)
  public ResponseEntity<?> topN(@PathVariable(value = "size") int size){
    log.debug("get new top {} records.", size);

    return ResponseEntity.ok(requirementService.topN(size));
  }

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public ResponseEntity<?> test(){
    return ResponseEntity.ok(requirementService.test());
  }
}
