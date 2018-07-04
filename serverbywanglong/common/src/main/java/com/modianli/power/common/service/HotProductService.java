package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import com.modianli.power.DTOUtils;
import com.modianli.power.common.exception.ProductExistedException;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.domain.es.EnterpriseProductPriceEs;
import com.modianli.power.domain.jpa.HotProduct;
import com.modianli.power.domain.jpa.HotProductCategory;
import com.modianli.power.domain.jpa.Product;
import com.modianli.power.model.HotProductCategoryDetails;
import com.modianli.power.model.HotProductCategoryTypeDetails;
import com.modianli.power.model.HotProductDetails;
import com.modianli.power.model.HotProductForm;
import com.modianli.power.model.IdAndSortValue;
import com.modianli.power.model.HotProductSortForm;
import com.modianli.power.model.HotProductTypeForm;
import com.modianli.power.persistence.repository.es.EnterpriseProductPriceEsRepository;
import com.modianli.power.persistence.repository.jpa.HotProductCategoryRepository;
import com.modianli.power.persistence.repository.jpa.HotProductRepository;
import com.modianli.power.persistence.repository.jpa.ProductRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/4/18.
 */
@Service
@Slf4j
public class HotProductService {

  @Inject
  private HotProductRepository hotProductRepository;

  @Inject
  private ProductRepository productRepository;

  @Inject
  private HotProductCategoryRepository hotProductCategoryRepository;

  @Inject
  private RedisService redisService;

  @Inject
  private EnterpriseProductPriceEsRepository productPriceEsRepository;

  public void saveHotProductType(HotProductTypeForm form) {
	Assert.notNull(form, "id cannot be null");
	Assert.notNull(form.getName(), "name cannot be null");
	Assert.notNull(form.getUrl(), "url cannot be null");

	log.debug("save hotProductType form @ {}", form);

	HotProductCategory hotProductCategory = DTOUtils.map(form, HotProductCategory.class);
	if (StringUtils.isBlank(form.getType()) || !HotProductCategory.Type.MODIAN_HOME_PAGE.name().equals(form.getType())) {
	  hotProductCategory.setType(HotProductCategory.Type.DEFAULT_HOME_PAGE);
	}

	Integer sort = hotProductCategoryRepository.getMaxSortByType(hotProductCategory.getType());
	hotProductCategory.setSort(sort == null ? 1 : sort + 1);
	hotProductCategoryRepository.save(hotProductCategory);

	if (null != hotProductCategory){
	  if(HotProductCategory.Type.MODIAN_HOME_PAGE == hotProductCategory.getType()){
		redisService.deleteHomePage();
	  }else {
		redisService.deleteShoppingPage();
	  }
	}
  }

  public List<HotProductCategoryTypeDetails> getHotTypes(Boolean active) {
	Assert.notNull(active, "active cannot be null");

	log.debug("save get hotTypes @ {}", active);

	List<HotProductCategoryTypeDetails> list = Lists.newArrayList();

	//默认大分类列表
	HotProductCategoryTypeDetails defaultDetails = new HotProductCategoryTypeDetails();
	defaultDetails.setType(HotProductCategory.Type.DEFAULT_HOME_PAGE.name());
	List<HotProductCategoryDetails> defaultCategories = DTOUtils.mapList(
		hotProductCategoryRepository.findByActiveAndTypeOrderBySortDesc(active, HotProductCategory.Type.DEFAULT_HOME_PAGE),
		HotProductCategoryDetails.class);
	defaultDetails.setHotProductCategoryDetails(defaultCategories);
	list.add(defaultDetails);

	//魔电首页
	HotProductCategoryTypeDetails shoppingDetails = new HotProductCategoryTypeDetails();
	shoppingDetails.setType(HotProductCategory.Type.MODIAN_HOME_PAGE.name());
	List<HotProductCategoryDetails> shoppingCategories = DTOUtils.mapList(
		hotProductCategoryRepository.findByActiveAndTypeOrderBySortDesc(active, HotProductCategory.Type.MODIAN_HOME_PAGE),
		HotProductCategoryDetails.class);
	shoppingDetails.setHotProductCategoryDetails(shoppingCategories);
	list.add(shoppingDetails);

	return list;
  }

  public void restoreHotProductType(Long id) {
	Assert.notNull(id, "id cannot be null");

	log.debug("save restore hotProductType id @ {}", id);

	HotProductCategory hotProductCategory = hotProductCategoryRepository.findOne(id);
	if (null == hotProductCategory) {
	  throw new ResourceNotFoundException("hotProductCategory can not be found by id " + id);
	}

	if (hotProductCategory.getActive()) {
	  return;
	}

	hotProductCategory.setActive(true);
	hotProductCategoryRepository.save(hotProductCategory);

	if(HotProductCategory.Type.MODIAN_HOME_PAGE == hotProductCategory.getType()){
	  redisService.deleteHomePage();
	}else {
	  redisService.deleteShoppingPage();
	}
  }

  public void deactivateHotProductType(Long id) {
	Assert.notNull(id, "id cannot be null");

	log.debug("deactivate hotProductType id @{}", id);

	HotProductCategory hotProductCategory = hotProductCategoryRepository.findOne(id);
	if (null == hotProductCategory) {
	  throw new ResourceNotFoundException("hotProductCategory can not be found by id " + id);
	}

	if (!hotProductCategory.getActive()) {
	  return;
	}

	hotProductCategory.setActive(false);
	hotProductCategoryRepository.save(hotProductCategory);

	if(HotProductCategory.Type.MODIAN_HOME_PAGE == hotProductCategory.getType()){
	  redisService.deleteHomePage();
	}else {
	  redisService.deleteShoppingPage();
	}
  }

  public void sortHotProductType(List<IdAndSortValue> sortDetails) {
	log.debug("sort hotProductType sortDetails @{}", sortDetails);
	HotProductCategory.Type type = null;

	for (IdAndSortValue p:sortDetails){
	  HotProductCategory category = hotProductCategoryRepository.findOne(p.getId());
	  if (null != category) {
		category.setSort(p.getSort());
		hotProductCategoryRepository.save(category);
		if (null == type) type = category.getType();
	  }
	}

	if (null != type){
	  if(HotProductCategory.Type.MODIAN_HOME_PAGE == type){
		redisService.deleteHomePage();
	  }else {
		redisService.deleteShoppingPage();
	  }
	}
  }

  public void updateHotProductType(Long id, HotProductTypeForm form) {
	Assert.notNull(id, "id cannot be null");
	Assert.notNull(form, "form cannot be null");

	log.debug("update hotProductType id @{}", id);

	HotProductCategory category = hotProductCategoryRepository.findOne(id);
	if (null == category) {
	  throw new ResourceNotFoundException("hotProductCategory can not found by id " + id);
	}

	if (StringUtils.isNotBlank(form.getName())) {
	  category.setName(form.getName());
	}

	if (StringUtils.isNotBlank(form.getUrl())) {
	  category.setUrl(form.getUrl());
	}

	hotProductCategoryRepository.save(category);

	if(HotProductCategory.Type.MODIAN_HOME_PAGE == category.getType()){
	  redisService.deleteHomePage();
	}else {
	  redisService.deleteShoppingPage();
	}
  }

  public void saveHotProduct(HotProductForm hotProductForm) {
	Assert.notNull(hotProductForm, "hotProductForm cannot be null");
	Assert.notNull(hotProductForm.getProductId(), "hotProductForm.getProductId() cannot be null");
	Assert.notNull(hotProductForm.getCategoryId(), "hotProductForm.getType() cannot be null");

	log.debug("save hotProduct hotProductForm @{}", hotProductForm);

	Product product = productRepository.findOne(hotProductForm.getProductId());
	if (null == product) {
	  throw new ResourceNotFoundException("product can not found by id " + hotProductForm.getProductId());
	}

	HotProductCategory category = hotProductCategoryRepository.findOne(hotProductForm.getCategoryId());
	if (null == category) {
	  throw new ResourceNotFoundException("hotProductCategory can not found by id " + hotProductForm.getCategoryId());
	}

	HotProduct hotProduct = hotProductRepository.findByProductAndCategory(product, category);
	if (null != hotProduct) {
	  throw new ProductExistedException("product is existed " + hotProductForm.getProductId());
	}

	Integer sort = hotProductRepository.getMaxSort(category);
	HotProduct hProduct = HotProduct
		.builder()
		.product(product)
		.category(category)
		.sort(sort == null ? 1 : sort + 1)
		.active(true)
		.build();
	hotProductRepository.save(hProduct);

	if(HotProductCategory.Type.MODIAN_HOME_PAGE == category.getType()){
	  redisService.deleteHomePage();
	}else {
	  redisService.deleteShoppingPage();
	}
  }

  public void restoreHotProduct(Long id) {

	log.debug("restore hotProduct id @{}", id);

	HotProduct hotProduct = hotProductRepository.findOne(id);
	if (null == hotProduct) {
	  throw new ResourceNotFoundException("hotProduct can not found");
	}

	if (!hotProduct.getActive()) {
	  hotProduct.setActive(true);
	  hotProductRepository.save(hotProduct);
	}

	HotProductCategory category = hotProduct.getCategory();
	if (null != category){
	  if(HotProductCategory.Type.MODIAN_HOME_PAGE == category.getType()){
		redisService.deleteHomePage();
	  }else {
		redisService.deleteShoppingPage();
	  }
	}

  }

  public void deactivateHotProduct(Long id) {

	log.debug("deactivate hotProduct id @{}", id);

	HotProduct hotProduct = hotProductRepository.findOne(id);
	if (null == hotProduct) {
	  throw new ResourceNotFoundException("hotProduct can not found");
	}

	if (hotProduct.getActive()) {
	  hotProduct.setActive(false);
	  hotProductRepository.save(hotProduct);
	}

	HotProductCategory category = hotProduct.getCategory();
	if (null != category){
	  if(HotProductCategory.Type.MODIAN_HOME_PAGE == category.getType()){
		redisService.deleteHomePage();
	  }else {
		redisService.deleteShoppingPage();
	  }
	}
  }

  public List<HotProductCategoryDetails> getHotProductList(Boolean active, String type) {

	log.debug("get hotProductList active @{} type @{}", active, type);

	HotProductCategory.Type categoryType = HotProductCategory.Type.MODIAN_HOME_PAGE;
	if (StringUtils.isBlank(type) || !HotProductCategory.Type.MODIAN_HOME_PAGE.name().equals(type)) {
	  categoryType = HotProductCategory.Type.DEFAULT_HOME_PAGE;
	}
	List<HotProductCategory> categories = hotProductCategoryRepository.findByActiveAndTypeOrderBySortDesc(active, categoryType);

	List<HotProductCategoryDetails> list = Lists.newArrayList();

	categories.stream().forEach(category -> {
	  HotProductCategoryDetails details = DTOUtils.map(category, HotProductCategoryDetails.class);
	  List<HotProduct> products = hotProductRepository.findByCategoryOrderBySortDesc(category);
	  List<Product> productList = Lists.newArrayList();
	  products.stream().forEach(p -> {
		Product product = p.getProduct();
		product.setId(p.getId());
		product.setActive(p.getActive());
		productList.add(product);
	  });
	  details.setProductDetails(DTOUtils.mapList(productList, HotProductDetails.class));
	  list.add(details);
	});
	return list;
  }

  public List<HotProductCategoryDetails> getPubHotProductList(String type) {
	log.debug("getPubHotProductList type @{}", type);

	if (StringUtils.isBlank(type) || HotProductCategory.Type.DEFAULT_HOME_PAGE.name().equals(type)) {
	  return getDefaultProductList();
	}
	return getHomeProductList();
  }

  /**
   * 魔电首页热门产品
   */
  private List<HotProductCategoryDetails> getHomeProductList() {
	List<HotProductCategoryDetails> list = Lists.newArrayList();

	List<HotProductCategory>
		categories =
		hotProductCategoryRepository.findByActiveAndTypeOrderBySortDesc(true, HotProductCategory.Type.MODIAN_HOME_PAGE);

	//每个分类显示3个产品
	categories.stream().forEach(category -> {
	  List<HotProduct> products = hotProductRepository.findTop3ByCategoryAndActiveOrderBySortDesc(category, true);
	  List<HotProductDetails> productList = Lists.newArrayList();
	  products.stream().forEach(p -> {
		if (null != p.getProduct()) {
		  HotProductDetails hotProductDetails = DTOUtils.map(p.getProduct(), HotProductDetails.class);
		  EnterpriseProductPriceEs productPriceEs = productPriceEsRepository.findOne(p.getProduct().getId());
		  if (null != productPriceEs) {
			hotProductDetails.setPriceCount(productPriceEs.getPriceCount());
		  }
		  productList.add(hotProductDetails);
		}

	  });
	  HotProductCategoryDetails details = HotProductCategoryDetails.builder()
																   .url(category.getUrl())
																   .name(category.getName())
																   .productDetails(productList)
																   .build();
	  list.add(details);
	});
	return list;
  }

  /**
   * 默认产品列表
   */
  private List<HotProductCategoryDetails> getDefaultProductList() {
	List<HotProductCategoryDetails> list = Lists.newArrayList();

	List<HotProductCategory>
		categories =
		hotProductCategoryRepository.findByActiveAndTypeOrderBySortDesc(true, HotProductCategory.Type.DEFAULT_HOME_PAGE);

	//每个分类显示6个产品
	categories.stream().forEach(category -> {
	  List<HotProduct> products = hotProductRepository.findTop6ByCategoryAndActiveOrderBySortDesc(category, true);
	  List<Product> productList = Lists.newArrayList();
	  products.stream().forEach(p -> {
		productList.add(p.getProduct());
	  });
	  HotProductCategoryDetails details = HotProductCategoryDetails.builder()
																   .url(category.getUrl())
																   .name(category.getName())
																   .productDetails(
																	   DTOUtils.mapList(productList, HotProductDetails.class))
																   .build();
	  list.add(details);
	});
	return list;
  }

  public void sortHotProduct(HotProductSortForm hotProductForm) {
	Assert.notNull(hotProductForm, "hotProductForm cannot be null");
	Assert.notNull(hotProductForm.getCategoryId(), "hotProductForm.getCategoryId() cannot be null");
	Assert.notNull(hotProductForm.getProductSorts(), "hotProductForm.getProductSorts() cannot be null");

	log.debug("sortHotProduct hotProductForm @{}", hotProductForm);

	HotProductCategory category = hotProductCategoryRepository.findOne(hotProductForm.getCategoryId());
	if (null == category) {
	  throw new ResourceNotFoundException("category can not found by id " + hotProductForm.getCategoryId());
	}

	hotProductForm.getProductSorts().stream().forEach(p -> {
	  if (null != p.getId() && null != p.getSort()) {
		HotProduct hotProduct = hotProductRepository.findOne(p.getId());
		if (null != hotProduct) {
		  hotProduct.setSort(p.getSort());
		}
		hotProductRepository.save(hotProduct);
	  }
	});

	if(HotProductCategory.Type.MODIAN_HOME_PAGE == category.getType()){
	  redisService.deleteHomePage();
	}else {
	  redisService.deleteShoppingPage();
	}
  }


}
