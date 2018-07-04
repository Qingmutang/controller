package com.modianli.power.api.dictionary;

import com.google.common.collect.Lists;

import com.modianli.power.common.exception.ResourceForbiddenException;
import com.modianli.power.common.service.DictionaryService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.DictionaryItemDetails;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = ApiConstants.URI_API_PUBLIC + ApiConstants.URI_DICTIONARY)
@Slf4j
@Api(description = "字典表")
public class DictionaryPubController {

  private final List<String> codes = Lists.newArrayList("recruit_experience", "recruit_salary", "recruit_category");

  @Inject
  private DictionaryService dictionaryService;

  @GetMapping("/{code}")
  public List<DictionaryItemDetails> findDictionaryItemByCode(@PathVariable("code") String code) {
	log.debug("findDictionaryItemByCodeAndActive code {} ", code);
	if (!codes.contains(code)) {
	  throw new ResourceForbiddenException("no resource permission");
	}
	return dictionaryService.findDictionaryItemByCodeAndActive(code);
  }

}
