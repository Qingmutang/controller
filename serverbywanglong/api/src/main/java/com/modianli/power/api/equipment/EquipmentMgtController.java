package com.modianli.power.api.equipment;

import com.google.common.collect.Maps;

import com.modianli.power.common.service.EquipmentService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.EquipmentCategoryDetails;
import com.modianli.power.model.EquipmentCategoryForm;
import com.modianli.power.model.EquipmentFirstCategoryDetails;
import com.modianli.power.model.EquipmentHomeFirstCategoryDetails;
import com.modianli.power.model.EquipmentPropertyEsDetails;

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

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-2-23.
 */
@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_EQUIPMENTS)
@Slf4j
@Api(description = "企业分类后台管理")
public class EquipmentMgtController {

  @Inject
  private EquipmentService equipmentService;

  @PostMapping(value = ApiConstants.URI_CATEGORIES)
  @ResponseBody
  @ApiOperation(value = "创建分类")
  public ResponseEntity<Void> createEquipmentCategory(@RequestBody EquipmentCategoryForm form,
													  UriComponentsBuilder uriComponentsBuilder) {

	log.debug("save EquipmentCategory data {} ", form);

	EquipmentCategoryDetails enterprise = equipmentService.saveEquipmentCategory(form);

	HttpHeaders headers = new HttpHeaders();
	headers.setLocation(uriComponentsBuilder
							.path(
								ApiConstants.URI_API_PUBLIC + ApiConstants.URI_EQUIPMENTS + ApiConstants.URI_CATEGORIES + "/{id}")
							.buildAndExpand(enterprise.getId()).toUri());

	return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = {ApiConstants.URI_CATEGORIES + "/{id}"}, method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取分类详情")
  public ResponseEntity<List<EquipmentCategoryDetails>> getCategory(@PathVariable("id") Long id) {

	log.debug("save getCategory id {} ", id);

	List<EquipmentCategoryDetails> equipmentCategoryDetails = equipmentService.searchCategories(id);

	return new ResponseEntity<>(equipmentCategoryDetails, HttpStatus.OK);
  }

  @RequestMapping(value = {ApiConstants.URI_CATEGORIES}, method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取分类列表")
  public ResponseEntity<List<EquipmentCategoryDetails>> getCategory() {

	List<EquipmentCategoryDetails> equipmentCategoryDetails = equipmentService.searchCategories(null);

	return new ResponseEntity<>(equipmentCategoryDetails, HttpStatus.OK);
  }

  @RequestMapping(value = {ApiConstants.URI_PROPERTY +"/{id}"},method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取分类属性")
  public ResponseEntity<List<EquipmentPropertyEsDetails>> getCategoryProperty(@PathVariable("id") Long id) {

	log.debug("save getCategory id {} ", id);

	List<EquipmentPropertyEsDetails> equipmentPropertyDetails = equipmentService.searchCategoryProperty(id);

	return new ResponseEntity<>(equipmentPropertyDetails, HttpStatus.OK);
  }

  @RequestMapping(value = {ApiConstants.URI_UNIT +"/{id}"},method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取分类单位")
  public ResponseEntity<Map<String,String>> getCategoryUnit(@PathVariable("id") Long id) {

	log.debug("getCategoryUnit id {} ", id);

	String unit = equipmentService.getCategoryUnit(id);

	Map<String,String> map = Maps.newHashMap();
	map.put("unit",unit);

	return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @RequestMapping(value = "hotCategory",method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "添加热门分类")
  public ResponseEntity<Void> addHotCategory(@RequestBody List<Long> ids) {
	log.debug("save getCategory id {} ", ids);

	equipmentService.saveRecommendCategory(ids);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

  }

  @RequestMapping(value = {"allCategories" },method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取全部分类")
  public ResponseEntity<List<EquipmentFirstCategoryDetails>> getAllCategories() {

	List<EquipmentFirstCategoryDetails> equipmentCategoryDetails = equipmentService.searchAllCategories();

	return new ResponseEntity<>(equipmentCategoryDetails, HttpStatus.OK);
  }

  @GetMapping("hotCategoriesEs")
  @ApiOperation(value = "获取首页热门分类")
  public ResponseEntity<List<EquipmentHomeFirstCategoryDetails>> getHomeCategoriesEs() {
	return new ResponseEntity<>(equipmentService.searchAllEsCategories(), HttpStatus.OK);
  }

  @GetMapping("/importCategories")
  @ApiOperation(value = "导入分类")
  public Map<String,String> importData() {
	equipmentService.importData();
	Map<String, String> map = Maps.newHashMap();
	map.put("status", "OK");
	return map;
  }

  @GetMapping("/importProducts")
  @ApiOperation(value = "导入三级分类属性")
  public Map<String,String> importData1() {
	equipmentService.importData1();
	Map<String, String> map = Maps.newHashMap();
	map.put("status", "OK");
	return map;
  }

  @PutMapping("categoryProperties")
  @ApiOperation(value = "导入分类和属性")
  public Map<String,String> importToEs() {
	equipmentService.saveEquipmentCategoryToES();
	Map<String, String> map = Maps.newHashMap();
	map.put("status", "OK");
	return map;
  }

  @PutMapping("allCategory")
  @ApiOperation(value = "重建分类索引")
  public Map<String,String> importCategoriesToEs() {
	equipmentService.saveAllCategoryToES();
	Map<String, String> map = Maps.newHashMap();
	map.put("status", "OK");
	return map;
  }


}
