package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import com.modianli.power.DTOUtils;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.common.utils.ClassUtils;
import com.modianli.power.domain.es.EnterpriseProductPriceEs;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.EnterpriseProductPrice;
import com.modianli.power.domain.jpa.Product;
import com.modianli.power.model.EnterpriseDetail;
import com.modianli.power.model.EnterpriseProductPriceCriteria;
import com.modianli.power.model.EnterpriseProductPriceDetails;
import com.modianli.power.model.EnterpriseProductPriceForm;
import com.modianli.power.persistence.repository.es.EnterpriseProductPriceEsRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseProductPriceRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;
import com.modianli.power.persistence.repository.jpa.ProductRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/3/16.
 */
@Service
@Slf4j
public class EnterpriseProductPriceService {

  @Inject
  private EnterpriseProductPriceRepository enterpriseProductPriceRepository;

  @Inject
  private EnterpriseRepository enterpriseRepository;

  @Inject
  private ProductRepository productRepository;

  @Inject
  private EnterpriseProductPriceEsRepository enterpriseProductPriceEsRepository;

  @Inject
  private ElasticsearchTemplate elasticsearchTemplate;

  @Inject
  private RedisService redisService;

  @Transactional
  public void saveEnterpriseProductPrice(EnterpriseProductPriceCriteria priceCriteria) {
	Assert.notNull(priceCriteria, "priceCriteria cannot be null");
	Assert.notNull(priceCriteria.getEnterpriseId(), "enterpriseId cannot be null");
	Assert.notNull(priceCriteria.getProductId(), "productId cannot be null");

	Long enterpriseId = priceCriteria.getEnterpriseId();
	Enterprise enterprise = enterpriseRepository.findOne(enterpriseId);
	if (null == enterprise) {
	  throw new ResourceNotFoundException(
		  " enterprise can't find by id :" + enterpriseId);
	}

	Long productId = priceCriteria.getProductId();
	Product product = productRepository.findOne(productId);
	if (null == product) {
	  throw new ResourceNotFoundException(
		  " product can't find by id :" + productId);
	}

	EnterpriseProductPrice enterpriseProductPrice = EnterpriseProductPrice.builder()
																		  .active(true)
																		  .priceDate(LocalDateTime.now())
																		  .enterprise(enterprise)
																		  .product(product)
																		  .price(priceCriteria.getPrice()).build();
	EnterpriseProductPrice productPrice = enterpriseProductPriceRepository.findByProductAndEnterprise(product, enterprise);
	if (null != productPrice) {
	  enterpriseProductPrice.setId(productPrice.getId());
	}

	enterpriseProductPriceRepository.save(enterpriseProductPrice);

	//保存es
	saveEnterpriseProductPriceEs(productId);

	redisService.deleteEnterprise(enterprise.getUuid());
  }

  private void saveEnterpriseProductPriceEs(Long productId) {
	if (null == productId) {
	  return;
	}

	Product product = productRepository.findOne(productId);
	if (null != product) {
	  Long productPriceCount = enterpriseProductPriceRepository.getEnterpriseProductPriceCount(productId);
	  EnterpriseProductPrice enterpriseProductPrice = enterpriseProductPriceRepository.findTop1ByProductOrderByPriceDate(product);

	  EnterpriseProductPriceEs productPriceEs = EnterpriseProductPriceEs
		  .builder()
		  .id(productId)
		  .lastedTime(Date.from(enterpriseProductPrice.getPriceDate().atZone(ZoneId.systemDefault()).toInstant()))
		  .priceCount(productPriceCount)
		  .build();

	  enterpriseProductPriceEsRepository.save(productPriceEs);
	}

  }

  public void updatePriceCount(Enterprise enterprise) {

    List<EnterpriseProductPrice> list = enterpriseProductPriceRepository.findByEnterprise(enterprise);
	list.stream().forEach(p -> {
	  Product product = p.getProduct();
	  EnterpriseProductPriceEs enterpriseProductPrice = enterpriseProductPriceEsRepository.findOne(product.getId());

	  if (null != enterpriseProductPrice){
		Long productPriceCount = enterpriseProductPriceRepository.getEnterpriseProductPriceCount(product.getId());
		enterpriseProductPrice.setPriceCount(productPriceCount);
		enterpriseProductPriceEsRepository.save(enterpriseProductPrice);
	  }
	});
  }

  @Transactional
  public void restoreEnterpriseProductPrice(Long id){
	EnterpriseProductPrice productPrice = enterpriseProductPriceRepository.findOne(id);
	productPrice.setActive(true);
	enterpriseProductPriceRepository.save(productPrice);
  }

  @Transactional
  public void deactivateEnterpriseProductPrice(Long id){
	EnterpriseProductPrice productPrice = enterpriseProductPriceRepository.findOne(id);
	if (null != productPrice){
	  productPrice.setActive(false);
	  enterpriseProductPriceRepository.save(productPrice);

     if(productPrice.getProduct()!=null){

	   EnterpriseProductPriceEs enterpriseProductPriceEs = enterpriseProductPriceEsRepository.findOne(productPrice.getProduct().getId());
	   if (enterpriseProductPriceEs != null) {
		 Long priceCount = enterpriseProductPriceRepository.getEnterpriseProductPriceCount(productPrice.getProduct().getId());
		 if(priceCount>0){
		 enterpriseProductPriceEs.setPriceCount(priceCount);
		 enterpriseProductPriceEsRepository.save(enterpriseProductPriceEs);
		 }else {
		   enterpriseProductPriceEsRepository.delete(productPrice.getProduct().getId());
		 }
	   }
	 }

	}

  }

  public Page<EnterpriseProductPriceDetails> getValidEnterpriseProductPrice(String uuid, Pageable pageable){
	Assert.notNull(uuid, "form cannot be null");


    Page<EnterpriseProductPrice> page = enterpriseProductPriceRepository.getActiveEnterpriseProductPrice(uuid,pageable);

	List<EnterpriseProductPrice> list = page.getContent();
	if (null == list || list.isEmpty()) return null;
	List<EnterpriseProductPriceDetails> detailsList = Lists.newArrayList();
	list.stream().forEach(p->{
	  EnterpriseProductPriceDetails productPriceDetails = DTOUtils.map(p,EnterpriseProductPriceDetails.class);
	  productPriceDetails.setName(p.getEnterprise().getName());
	  productPriceDetails.setEnterpriseUUID(p.getEnterprise().getUuid());
	  productPriceDetails.setBusinessPhone(p.getEnterprise().getBusinessPhone());
	  productPriceDetails.setEnterpriseAddress(p.getEnterprise().getEnterpriseAddress());
	  productPriceDetails.setContracts(p.getEnterprise().getContacts());
	  productPriceDetails.setPic(p.getEnterprise().getImageUrl());
	  productPriceDetails.setEnterpriseId(p.getEnterprise().getId());
	  detailsList.add(productPriceDetails);
	});
	return new PageImpl<EnterpriseProductPriceDetails>(detailsList, new PageRequest(page.getNumber(), page.getSize(), page.getSort()),
													   page.getTotalElements());
  }

  public Page<EnterpriseProductPriceDetails> getValidEnterpriseProductPrice(Long id, Pageable pageable){
	Assert.notNull(id, "form cannot be null");


	Page<EnterpriseProductPrice> page = enterpriseProductPriceRepository.getActiveEnterpriseProductPrice(id,pageable);

	List<EnterpriseProductPrice> list = page.getContent();
	if (null == list || list.isEmpty()) return null;
	List<EnterpriseProductPriceDetails> detailsList = Lists.newArrayList();
	list.stream().forEach(p->{
	  EnterpriseProductPriceDetails productPriceDetails = DTOUtils.map(p,EnterpriseProductPriceDetails.class);
	  productPriceDetails.setName(p.getEnterprise().getName());
	  productPriceDetails.setEnterpriseUUID(p.getEnterprise().getUuid());
	  productPriceDetails.setBusinessPhone(p.getEnterprise().getBusinessPhone());
	  productPriceDetails.setEnterpriseAddress(p.getEnterprise().getEnterpriseAddress());
	  productPriceDetails.setContracts(p.getEnterprise().getContacts());
	  productPriceDetails.setPic(p.getEnterprise().getImageUrl());
	  productPriceDetails.setEnterpriseId(p.getEnterprise().getId());
	  detailsList.add(productPriceDetails);
	});
	return new PageImpl<EnterpriseProductPriceDetails>(detailsList, new PageRequest(page.getNumber(), page.getSize(), page.getSort()),
													   page.getTotalElements());
  }

  public void deleteEnterpriseProductPriceEs() {
	enterpriseProductPriceEsRepository.deleteAll();
  }

  public List<EnterpriseDetail> getEnterpriseDetail(EnterpriseProductPriceForm form){
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getId(), "id cannot be null");


	String name = form.getName();
	Long id = form.getId();
	List<Enterprise> list;
	if (StringUtils.isNoneBlank(name)){
	  list = enterpriseRepository.getNoProductPriceEnterpriseByProductIdAndName(id, "%" + name + "%", new PageRequest(0, 10));
	}else {
	  list = enterpriseRepository.getNoProductPriceEnterpriseByProductId(id,new PageRequest(0,10));
	}

	return DTOUtils.mapList(list,EnterpriseDetail.class);
  }

  public void rebuildIndex() {
	elasticsearchTemplate.deleteIndex(EnterpriseProductPriceEs.class);
	elasticsearchTemplate.createIndex(EnterpriseProductPriceEs.class);
	elasticsearchTemplate.putMapping(EnterpriseProductPriceEs.class);
	elasticsearchTemplate.refresh(EnterpriseProductPriceEs.class);

	String indexName = ClassUtils.getIndexName(EnterpriseProductPriceEs.class);

	if (elasticsearchTemplate.indexExists(indexName)){
	  AliasQuery query = new AliasQuery();
	  query.setIndexName(indexName);
	  query.setAliasName("product_price");

	  elasticsearchTemplate.addAlias(query);
	  elasticsearchTemplate.refresh(indexName);
	}

	List<EnterpriseProductPriceEs> bucket = Lists.newArrayList();

	List<EnterpriseProductPrice> productPrices = enterpriseProductPriceRepository.getAllProductPriceGroupByProduct();
	productPrices.stream().forEach(p->{
	  Long productId = p.getProduct().getId();
	  Long productPriceCount = enterpriseProductPriceRepository.getEnterpriseProductPriceCount(productId);
	  EnterpriseProductPrice enterpriseProductPrice = enterpriseProductPriceRepository.findTop1ByProductOrderByPriceDate(p.getProduct());

	  EnterpriseProductPriceEs productPriceEs = EnterpriseProductPriceEs
		  .builder()
		  .id(productId)
		  .lastedTime(Date.from(enterpriseProductPrice.getPriceDate().atZone(ZoneId.systemDefault()).toInstant()))
		  .priceCount(productPriceCount)
		  .build();

	  bucket.add(productPriceEs);
	});

	if (!bucket.isEmpty()){
	  enterpriseProductPriceEsRepository.save(bucket);
	}

  }

}


