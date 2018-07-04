package com.modianli.power.api.products;

import com.modianli.power.common.service.EnterpriseProductPriceService;
import com.modianli.power.common.service.HotProductService;
import com.modianli.power.common.service.ProductService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.EnterpriseProductPriceDetails;
import com.modianli.power.model.HotProductCategoryDetails;
import com.modianli.power.model.ProductCategoryPropertyDetails;
import com.modianli.power.model.ProductPubCriteria;
import com.modianli.power.model.ProductPubDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = ApiConstants.URI_API_PUBLIC + ApiConstants.URI_PRODUCT)
@Slf4j
@Api(description = "产品前端接口")
public class ProductsPubController {

  @Inject
  private ProductService productService;

  @Inject
  private EnterpriseProductPriceService enterpriseProductService;

  @Inject
  private HotProductService hotProductService;

  @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "查询产品")
  public ResponseEntity<ProductCategoryPropertyDetails> searchProduct(@RequestBody ProductPubCriteria productCriteria,
																	  @PageableDefault(page = 0, size = 20) Pageable page) {

	if (log.isDebugEnabled()) {
	  log.debug("save product data @" + productCriteria);
	}

	ProductCategoryPropertyDetails productEs = productService.searchPubProduct(productCriteria, page);

	return new ResponseEntity<>(productEs, HttpStatus.OK);
  }

  @RequestMapping(value = {"/{uuid}"}, method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "根据uuid获取产品")
  public ResponseEntity<ProductPubDetails> getProduct(@PathVariable("uuid") String uuid) {

	if (log.isDebugEnabled()) {
	  log.debug("get product data @" + uuid);
	}

	ProductPubDetails productEs = productService.getProduct(uuid);

	return new ResponseEntity<>(productEs, HttpStatus.OK);
  }

  @RequestMapping(value = "validEnterpriseProductPrice/{uuid}", method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取产品报价")
  public ResponseEntity<Page<EnterpriseProductPriceDetails>> enterpriseProductPrice(
	  @PathVariable("uuid") String uuid,
	  @PageableDefault(page = 0, size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
	log.debug("uuid {}", uuid);
	Page<EnterpriseProductPriceDetails> pageList = enterpriseProductService.getValidEnterpriseProductPrice(uuid, page);
	return new ResponseEntity<>(pageList, HttpStatus.OK);
  }

  @RequestMapping(value = "hotProduct", method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "获取热门产品")
  public ResponseEntity<List<HotProductCategoryDetails>> getHotProduct(@RequestParam(required = false) String type) {
	log.debug("hotProduct  type {} ", type);
	List<HotProductCategoryDetails> hotProductCategoryDetailsList = hotProductService.getPubHotProductList(type);
	return new ResponseEntity<>(hotProductCategoryDetailsList, HttpStatus.OK);
  }

}
