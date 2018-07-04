package com.modianli.power.api.recruit;

import com.modianli.power.api.security.CurrentUser;
import com.modianli.power.common.service.RecruitService;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.RecruitCriteria;
import com.modianli.power.model.RecruitDetails;
import com.modianli.power.model.RecruitForm;
import com.modianli.power.model.RecruitListDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-6-27.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_RECRUIT)
public class RecruitMgtController {

  @Inject
  private RecruitService recruitService;

  @RequestMapping(value = {""}, method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Void> createRecruit(@RequestBody RecruitForm form, @CurrentUser UserAccount userAccount,
											UriComponentsBuilder uriComponentsBuilder) {
	log.debug("save recruit data @ {} ", form);

	RecruitDetails saved = recruitService.saveRecruit(form, userAccount);

	HttpHeaders headers = new HttpHeaders();
	headers.setLocation(uriComponentsBuilder.path(ApiConstants.URI_API_PUBLIC + ApiConstants.URI_RECRUIT + "/{id}")
											.buildAndExpand(saved.getId()).toUri());

	return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @PostMapping(value = "/search")
  @ApiOperation(value = "查询招聘信息")
  public ResponseEntity<Page<RecruitListDetails>> searchRecruit(
	  @RequestBody RecruitCriteria criteria,
	  @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
	log.debug("criteria {} pageable {} ", criteria, page);
	Page<RecruitListDetails> recruits = recruitService.findRecruits(criteria, page);
	return new ResponseEntity<>(recruits, HttpStatus.OK);
  }

  @PutMapping(value = {"/{id}"}, params = {"action=ACTIVATE"})
  @ResponseBody
  public ResponseEntity<Void> activateRecruit(@PathVariable("id") Long id) {

	log.debug("activateRecruit @ {} ", id);

	recruitService.activateRecruit(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = {"/{id}"}, params = {"action=DEACTIVATE"})
  @ResponseBody
  public ResponseEntity<Void> deactivateRecruit(@PathVariable("id") Long id) {

	log.debug("deactivateRecruit @ {} ", id);

	recruitService.deactivateRecruit(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = "/{id}")
  @ApiOperation(value = "查询招聘信息")
  public ResponseEntity<RecruitDetails> searchEnterpriseBackendRecruit(@PathVariable(value = "id") Long uuid) {
	log.debug(" uuid {} ", uuid);
	RecruitDetails recruits = recruitService.findOneRecruit(uuid);
	return new ResponseEntity<>(recruits, HttpStatus.OK);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
  @ResponseBody
  public ResponseEntity<Void> updateRecruit(@PathVariable("id") Long id, @RequestBody RecruitForm form,
											@CurrentUser UserAccount userAccount) {

	log.debug("updateRecruit id {} form {}", id, form);
	recruitService.updateRecruit(form, userAccount, id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
