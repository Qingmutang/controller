package com.modianli.power.api.equipment;

import com.modianli.power.common.service.EquipmentService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.EquipmentCategoryDetails;
import com.modianli.power.model.EquipmentHomeFirstCategoryDetails;
import com.modianli.power.model.EquipmentPropertyEsDetails;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-2-23.
 */
@RestController
@RequestMapping(value = ApiConstants.URI_API_PUBLIC + ApiConstants.URI_EQUIPMENTS)
@Slf4j
@Api(description = "企业分类前台管理")
public class EquipmentPubController {

  @Inject
  private EquipmentService equipmentService;

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
  @ApiOperation(value = "获取全部分类")
  public ResponseEntity<List<EquipmentCategoryDetails>> getCategory() {

	List<EquipmentCategoryDetails> equipmentCategoryDetails = equipmentService.searchCategories(null);

	return new ResponseEntity<>(equipmentCategoryDetails, HttpStatus.OK);
  }

  @RequestMapping(value = {ApiConstants.URI_PROPERTY +"/{id}"},method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取分类属性")
  public ResponseEntity<List<EquipmentPropertyEsDetails>> getCategoryProperty(@PathVariable("id") Long id) {

	log.debug("get propertys id {} ", id);

	List<EquipmentPropertyEsDetails> equipmentPropertyDetails = equipmentService.searchCategoryProperty(id);

	return new ResponseEntity<>(equipmentPropertyDetails, HttpStatus.OK);
  }

  @GetMapping("homeCategoriesEs")
  @ApiOperation(value = "获取首页分类")
  public ResponseEntity<List<EquipmentHomeFirstCategoryDetails>> getHomeCategoriesEs() {
	return new ResponseEntity<>(equipmentService.searchEsCategories(), HttpStatus.OK);
  }

//  @GetMapping("es-p")
//  public String importProductToEs() {
//	equipmentService.saveProduct();
//	return "OK";
//  }

//  @GetMapping("es-q")
//  public String searchProductToEs() {
//	equipmentService.searchProduct();
//	return "OK";
//  }


}
