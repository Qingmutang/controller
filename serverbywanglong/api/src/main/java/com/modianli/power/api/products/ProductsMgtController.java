package com.modianli.power.api.products;

import com.modianli.power.common.service.EnterpriseProductPriceService;
import com.modianli.power.common.service.HotProductService;
import com.modianli.power.common.service.ProductService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.EnterpriseDetail;
import com.modianli.power.model.EnterpriseProductPriceCriteria;
import com.modianli.power.model.EnterpriseProductPriceDetails;
import com.modianli.power.model.EnterpriseProductPriceForm;
import com.modianli.power.model.HotProductCategoryDetails;
import com.modianli.power.model.HotProductCategoryTypeDetails;
import com.modianli.power.model.HotProductForm;
import com.modianli.power.model.IdAndSortValue;
import com.modianli.power.model.HotProductSortForm;
import com.modianli.power.model.HotProductTypeForm;
import com.modianli.power.model.ProductCriteria;
import com.modianli.power.model.ProductDetails;
import com.modianli.power.model.ProductForm;
import com.modianli.power.model.ProductFormDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-2-23.
 */
@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_PRODUCT)
@Slf4j
@Api(description = "产品后端接口")
public class ProductsMgtController {

  @Inject
  private ProductService productService;

  @Inject
  private HotProductService hotProductService;

  @Inject
  private EnterpriseProductPriceService enterpriseProductService;

  @RequestMapping(method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "创建产品")
  public ResponseEntity<Void> createProduct(@RequestBody ProductForm form,
											   UriComponentsBuilder uriComponentsBuilder) {

	if (log.isDebugEnabled()) {
	  log.debug("save product data @" + form);
	}

	ProductDetails product = productService.saveProductAndPropertyOptions(form);

	HttpHeaders headers = new HttpHeaders();
	headers.setLocation(uriComponentsBuilder
							.path(ApiConstants.URI_API_PUBLIC + ApiConstants.URI_PRODUCT + "/{id}")
							.buildAndExpand(product.getId()).toUri());

	return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "更新产品")
  public ResponseEntity<Void> updateProduct(@RequestBody ProductForm form,@PathVariable("id") Long id) {

	if (log.isDebugEnabled()) {
	  log.debug("save product data @" + form);
	}

	productService.updateProductAndPropertyOptions(form,id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "查询产品列表")
  public ResponseEntity<Page<ProductDetails>> searchProduct(@RequestBody ProductCriteria productCriteria,@PageableDefault(page = 0, size = 20) Pageable page) {

	if (log.isDebugEnabled()) {
	  log.debug("save product data @" + productCriteria);
	}

	Page<ProductDetails> productEs = productService.searchProduct(productCriteria, page);

	return new ResponseEntity<>(productEs, HttpStatus.OK);
  }

  @RequestMapping(value = {"updateProduct/{id}"}, method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "修改产品")
  public ResponseEntity<Void> updateProductForm(@RequestBody ProductFormDetail productForm, @PathVariable("id") Long id) {

	if (log.isDebugEnabled()) {
	  log.debug("product data @" + productForm);
	}

	productService.updateProductForm(productForm,id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, params = {"action=RESTORE"})
  @ResponseBody
  @ApiOperation(value = "恢复产品")
  public ResponseEntity<Void> restoreProduct(@PathVariable("id") Long id) {

	log.debug("id @ {} " ,id);

	productService.restoreProduct(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, params = {"action=DEACTIVATED"})
  @ResponseBody
  @ApiOperation(value = "删除产品")
  public ResponseEntity<Void> deactivateProduct(@PathVariable("id") Long id) {

	log.debug("id @ {} " ,id);

	productService.deactivateProduct(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(method = RequestMethod.DELETE)
  @ResponseBody
  @ApiOperation(value = "清空产品")
  public ResponseEntity<Void> deleteAll() {

	productService.deleteAll();

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "enterpriseProductPrice",method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "提交产品报价")
  public ResponseEntity<Void> enterpriseProductPrice(@RequestBody EnterpriseProductPriceCriteria enterpriseProductCriteria) {

	log.debug("save product data @" + enterpriseProductCriteria);
	enterpriseProductService.saveEnterpriseProductPrice(enterpriseProductCriteria);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

//  @RequestMapping(value = "enterpriseProductPrice",method = RequestMethod.DELETE)
//  @ResponseBody
//  public ResponseEntity<Void> deleteEnterpriseProductPrice() {
//	enterpriseProductService.deleteEnterpriseProductPriceEs();
//	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }

  @RequestMapping(value = "validEnterpriseProductPrice/{id}",method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取报价信息列表")
  public ResponseEntity<Page<EnterpriseProductPriceDetails>> enterpriseProductPrice(@PathVariable("id")Long id,
																					@PageableDefault(page = 0, size = 20, sort = {"id"},
														 direction = Sort.Direction.DESC) Pageable page) {
    log.debug("id {}",id);
	Page<EnterpriseProductPriceDetails> pageList = enterpriseProductService.getValidEnterpriseProductPrice(id,page);
	return new ResponseEntity<>(pageList,HttpStatus.OK);
  }

  @RequestMapping(value = "enterpriseProductPrice/{id}",method = RequestMethod.DELETE)
  @ResponseBody
  @ApiOperation(value = "删除产品")
  public ResponseEntity<Void> deactivateEnterpriseProductPrice(@PathVariable("id")Long id) {
	if (log.isDebugEnabled()) {
	  log.debug("enterpriseProductPrice data @ id:{}" ,id);
	}
	enterpriseProductService.deactivateEnterpriseProductPrice(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "noProductPriceEnterprise",method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "获取未报价企业列表")
  public ResponseEntity<List<EnterpriseDetail>> noProductPriceEnterprise(@RequestBody EnterpriseProductPriceForm form) {
	if (log.isDebugEnabled()) {
	  log.debug("enterpriseProductPrice data @ form:{}" ,form);
	}
	List<EnterpriseDetail> details = enterpriseProductService.getEnterpriseDetail(form);
	return new ResponseEntity<List<EnterpriseDetail>>(details,HttpStatus.OK);
  }

  @RequestMapping(value = "productEs",method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "重建产品索引")
  public ResponseEntity<Void> rebuildProduct() {
	productService.rebuildIndex();
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "productPriceEs",method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "重建产品报价")
  public ResponseEntity<Void> rebuildProductPrice() {
	enterpriseProductService.rebuildIndex();
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "hotProductTypes",method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "提交热门产品类型")
  public ResponseEntity<Void> hotProductTypes(@RequestBody HotProductTypeForm form) {
    log.debug("create hotProductTypes @ {}",form);
	hotProductService.saveHotProductType(form);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "hotProductTypes",method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取热门产品类型")
  public ResponseEntity<List<HotProductCategoryTypeDetails>> hotProductTypes(Boolean active) {
    log.debug("search hotProductTypes");

	return new ResponseEntity<>(hotProductService.getHotTypes(active),HttpStatus.OK);
  }

  @RequestMapping(value = "hotProductTypes/{id}",method = RequestMethod.PUT,params = {"action=RESTORE"})
  @ResponseBody
  @ApiOperation(value = "恢复热门产品类型")
  public ResponseEntity<Void> restoreHotProductTypes(@PathVariable Long id) {
	log.debug("restore hotProductTypes id@ {}",id);
	hotProductService.restoreHotProductType(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "hotProductTypes/{id}",method = RequestMethod.DELETE,params = {"action=DEACTIVATED"})
  @ResponseBody
  @ApiOperation(value = "删除热门产品类型")
  public ResponseEntity<Void> deactivatedHotProductTypes(@PathVariable Long id) {
	log.debug("delete hotProductTypes id@ {}",id);
	hotProductService.deactivateHotProductType(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "hotProductTypes/{id}",method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "修改热门产品类型")
  public ResponseEntity<Void> updateHotProductTypes(@PathVariable Long id,@RequestBody HotProductTypeForm form) {
	log.debug("update hotProductTypes id@ {}",id);
	hotProductService.updateHotProductType(id,form);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "sortHotProductTypes",method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "热门产品类型排序")
  public ResponseEntity<Void> sortHotProductTypes(@RequestBody List<IdAndSortValue> sortDetails) {
	log.debug("sort hotProductTypes");
	hotProductService.sortHotProductType(sortDetails);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "hotProduct",method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "创建热门产品")
  public ResponseEntity<Void> createHotProduct(@RequestBody HotProductForm hotProductForm) {
	if (log.isDebugEnabled()) {
	  log.debug("createHotProduct data @ hotProductForm:{}" ,hotProductForm);
	}
	hotProductService.saveHotProduct(hotProductForm);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "hotProduct/{id}",method = RequestMethod.PUT,params = {"action=RESTORE"})
  @ResponseBody
  @ApiOperation(value = "恢复热门产品")
  public ResponseEntity<Void> restoreHotProduct(@PathVariable Long id) {
	if (log.isDebugEnabled()) {
	  log.debug("restoreHotProduct data @ id:{}" ,id);
	}
	hotProductService.restoreHotProduct(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "hotProduct/{id}",method = RequestMethod.DELETE,params = {"action=DEACTIVATED"})
  @ResponseBody
  @ApiOperation(value = "删除热门产品")
  public ResponseEntity<Void> deleteHotProduct(@PathVariable Long id) {
	if (log.isDebugEnabled()) {
	  log.debug("deleteHotProduct data @ id:{}" ,id);
	}
	hotProductService.deactivateHotProduct(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "hotProduct",method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取热门产品")
  public ResponseEntity<List<HotProductCategoryDetails>> getHotProduct(Boolean active,@RequestParam(required = false) String type) {
    log.debug("getHotProduct @");

	return new ResponseEntity<>(hotProductService.getHotProductList(active,type), HttpStatus.OK);
  }

  @RequestMapping(value = "hotProductSort",method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "热门产品排序")
  public ResponseEntity<Void> hotProductSort(@RequestBody HotProductSortForm hotProductForm) {
	if (log.isDebugEnabled()) {
	  log.debug("hotProductSort data @ hotProductForm:{}" ,hotProductForm);
	}
	hotProductService.sortHotProduct(hotProductForm);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

//  @RequestMapping(value = "productUuid",method = RequestMethod.GET)
//  @ResponseBody
//  @ApiOperation(value = "重置产品uuid")
//  @Deprecated
//  public ResponseEntity<Void> productUuid() {
//    log.debug("start create uuid");
//	productService.resetProductUuid();
//	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }
}
