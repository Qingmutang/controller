package com.modianli.power.api.dictionary;

import com.modianli.power.common.service.DictionaryService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.DictionaryItemDetails;
import com.modianli.power.model.DictionaryItemForm;
import com.modianli.power.model.DictionaryTypeDetails;
import com.modianli.power.model.DictionaryTypeForm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_DICTIONARY)
@Slf4j
@Api(description = "字典表")
public class DictionaryMgtController {

  @Inject
  private DictionaryService dictionaryService;

  @GetMapping(value = "/{code}")
  public List<DictionaryItemDetails> findDictionaryItemByCodeAndActive(@PathVariable("code") String code) {
	log.debug("findDictionaryItemByCodeAndActive code {} ", code);
	return dictionaryService.findDictionaryItemByCodeAndActive(code);
  }

  @GetMapping(value = "/{code}", params = "action=ALL")
  public List<DictionaryItemDetails> findDictionaryItemByCode(@PathVariable("code") String code) {
	log.debug("findDictionaryItemByCodeAndActive code {} ", code);
	return dictionaryService.findDictionaryItemByCode(code);
  }

  @GetMapping(value = "/type/{text}", params = "action=ACTIVE")
  public List<DictionaryTypeDetails> findDictionaryTypeByText(@PathVariable("text") String text) {
	log.debug("findDictionaryItemByCodeAndActive text {} ", text);
	return dictionaryService.findDictionaryTypeByText(text);
  }

  @GetMapping(value = "/type")
  public List<DictionaryTypeDetails> findDictionaryType() {
	log.debug("findDictionaryType  ");
	return dictionaryService.findDictionaryType();
  }

  @PostMapping(value = "/type")
  public ResponseEntity<Void> createDictionaryType(@RequestBody DictionaryTypeForm form) {
	log.debug("save createDictionaryType form @ {} ", form);

	DictionaryTypeDetails saved = dictionaryService.saveDictionaryType(form);

	return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(value = "/type/{id}")
  public DictionaryTypeDetails findDictionaryType(@PathVariable("id") Long id) {
	log.debug("findDictionaryType id  {} ", id);
	DictionaryTypeDetails details = dictionaryService.findDictionaryType(id);
	return details;
  }

  @PutMapping(value = "/type/{id}")
  public ResponseEntity<Void> updateDictionaryType(@PathVariable("id") Long id, DictionaryTypeForm form) {
	log.debug("updateDictionaryType id {} form {} ", id, form);
	dictionaryService.updateDictionaryType(id, form);
	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = {"/type/{id}"}, params = {"action=ACTIVATE"})
  @ResponseBody
  public ResponseEntity<Void> activateDictionaryType(@PathVariable("id") Long id) {

	log.debug(" activateDictionaryType @ {} ", id);

	dictionaryService.activateDictionaryType(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = {"/type/{id}"}, params = {"action=DEACTIVATE"})
  @ResponseBody
  public ResponseEntity<Void> deactivateDictionaryType(@PathVariable("id") Long id) {

	log.debug(" deactivateDictionaryType @ {} ", id);

	dictionaryService.deactivateDictionaryType(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping(value = "/item")
  public ResponseEntity<Void> createDictionaryItem(@RequestBody DictionaryItemForm form) {
	log.debug("save createDictionaryItem form @ {} ", form);

	DictionaryItemDetails saved = dictionaryService.saveDictionaryItem(form);

	return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(value = "/item/{id}")
  public DictionaryItemDetails findDictionaryItem(@PathVariable("id") Long id) {
	log.debug("findDictionaryItem id  {} ", id);
	DictionaryItemDetails details = dictionaryService.findDictionaryItem(id);
	return details;
  }

  @PutMapping(value = "/item/{id}")
  public ResponseEntity<Void> updateDictionaryItem(@PathVariable("id") Long id, DictionaryItemForm form) {
	log.debug("updateDictionaryItem id {} form {} ", id, form);
	dictionaryService.updateDictionaryItem(id, form);
	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = {"/item/{id}"}, params = {"action=ACTIVATE"})
  @ResponseBody
  public ResponseEntity<Void> activateDictionaryItem(@PathVariable("id") Long id) {

	log.debug(" activateDictionaryItem @ {} ", id);

	dictionaryService.activateDictionaryItem(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = {"/item/{id}"}, params = {"action=DEACTIVATE"})
  @ResponseBody
  public ResponseEntity<Void> deactivateDictionaryItem(@PathVariable("id") Long id) {

	log.debug(" deactivateDictionaryItem @ {} ", id);

	dictionaryService.deactivateDictionaryItem(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
