package com.modianli.power.api.recruit;

import com.modianli.power.common.service.RecruitService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.RecruitCriteria;
import com.modianli.power.model.RecruitDetails;
import com.modianli.power.model.RecruitListDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-6-27.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiConstants.URI_API_PUBLIC + ApiConstants.URI_RECRUIT)
public class RecruitPubController {

  @Inject
  private RecruitService recruitService;

  @PostMapping(value = "/search")
  @ApiOperation(value = "查询前台招聘信息")
  public ResponseEntity<Page<RecruitListDetails>> searchFrontRecruit(
	  @RequestBody RecruitCriteria criteria,
	  @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
	log.debug("criteria {} pageable {} ", criteria, page);
	criteria.setActive(true);
	Page<RecruitListDetails> recruits = recruitService.findRecruits(criteria, page);
	return new ResponseEntity<>(recruits, HttpStatus.OK);
  }

  @PostMapping(value = "/{uuid}/search")
  @ApiOperation(value = "查询前台招聘信息")
  public ResponseEntity<Page<RecruitListDetails>> searchEnterpriseFrontRecruit(
	  @RequestBody RecruitCriteria criteria,
	  @PathVariable(value = "uuid") String uuid,
	  @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
	log.debug("criteria {} pageable {} uuid {} ", criteria, page, uuid);
	criteria.setActive(true);
	criteria.setEnterpriseUuid(uuid);
	Page<RecruitListDetails> recruits = recruitService.findRecruits(criteria, page);
	return new ResponseEntity<>(recruits, HttpStatus.OK);
  }

  @GetMapping(value = "/{uuid}")
  @ApiOperation(value = "查询前台招聘信息")
  public ResponseEntity<RecruitDetails> searchEnterpriseFrontRecruit(@PathVariable(value = "uuid") String uuid) {
	log.debug(" uuid {} ", uuid);
	RecruitDetails recruits = recruitService.findOneRecruit(uuid);
	return new ResponseEntity<>(recruits, HttpStatus.OK);
  }

}
