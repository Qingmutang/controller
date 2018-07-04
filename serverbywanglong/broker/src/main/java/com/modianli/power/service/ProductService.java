package com.modianli.power.service;

import com.google.common.collect.Lists;

import com.modianli.power.DTOUtils;
import com.modianli.power.domain.es.EquipmentProductEs;
import com.modianli.power.domain.es.EquipmentProductPropertyEs;
import com.modianli.power.domain.es.EquipmentProductPropertyOptionsEs;
import com.modianli.power.domain.jpa.EquipmentCategory;
import com.modianli.power.domain.jpa.EquipmentCategoryDetails;
import com.modianli.power.domain.jpa.EquipmentPropertyOptions;
import com.modianli.power.domain.jpa.Product;
import com.modianli.power.domain.jpa.ProductPropertyOptions;
import com.modianli.power.persistence.repository.es.EquipmentProductEsRepository;
import com.modianli.power.persistence.repository.jpa.EquipmentCategoryDetailsRepository;
import com.modianli.power.persistence.repository.jpa.EquipmentCategoryRepository;
import com.modianli.power.persistence.repository.jpa.ProductPropertyOptionsRepository;
import com.modianli.power.persistence.repository.jpa.ProductRepository;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/5/9.
 */
@Service
@Slf4j
public class ProductService {

  @Inject
  private ProductRepository productRepository;

  @Inject
  private ProductPropertyOptionsRepository productPropertyOptionsRepository;

  @Inject
  private EquipmentCategoryRepository categoryRepository;

  @Inject
  private EquipmentCategoryDetailsRepository equipmentCategoryDetailsRepository;

  @Inject
  private ElasticsearchTemplate elasticsearchTemplate;

  @Inject
  private EquipmentProductEsRepository equipmentProductEsRepository;

  public void saveProduct(EquipmentProductEs productEs){
	equipmentProductEsRepository.save(productEs);
  }

  public void batchSaveProducts() {
	elasticsearchTemplate.deleteIndex(EquipmentProductEs.class);
	elasticsearchTemplate.createIndex(EquipmentProductEs.class);
	elasticsearchTemplate.putMapping(EquipmentProductEs.class);
	elasticsearchTemplate.refresh(EquipmentProductEs.class);

	String indexName = getIndexName(EquipmentProductEs.class);

	if (elasticsearchTemplate.indexExists(indexName)){
	  AliasQuery query = new AliasQuery();
	  query.setIndexName(indexName);
	  query.setAliasName("product");

	  elasticsearchTemplate.addAlias(query);
	  elasticsearchTemplate.refresh(indexName);
	}

	List<Product> products = productRepository.findAll();
	log.debug("start time {}", System.currentTimeMillis());
	List<EquipmentProductEs> list = Lists.newArrayList();
	products.stream().forEach(p -> {
	  EquipmentProductEs equipmentProductEs = DTOUtils.map(p, EquipmentProductEs.class);
	  equipmentProductEs.setActive(p.getActive() == null ? true : p.getActive());

	  List<ProductPropertyOptions> productPropertyOptions = productPropertyOptionsRepository.findByProduct(p);

	  List<EquipmentProductPropertyEs> info = Lists.newArrayList();
	  productPropertyOptions.stream().forEach(e -> {
		EquipmentPropertyOptions propertyOption = e.getEquipmentPropertyOptions();
		EquipmentProductPropertyEs equipmentProductPropertyEs =
			getEquipmentProductPropertyEs(propertyOption.getCode(),
										  propertyOption.getValue(),
										  propertyOption.getEquipmentProperty().getCode(),
										  propertyOption.getEquipmentProperty().getName());
		info.add(equipmentProductPropertyEs);
	  });
	  equipmentProductEs.setInfo(info);
	  EquipmentCategory firstCategory = categoryRepository.findOne(p.getFirstCategoryId());
	  if (null != firstCategory){
		equipmentProductEs.setFirstCategoryName(firstCategory.getName());
	  }

	  EquipmentCategory secondCategory = categoryRepository.findOne(p.getSecondCategoryId());
	  if (null != secondCategory){
		equipmentProductEs.setSecondCategoryName(secondCategory.getName());
	  }

	  EquipmentCategory thirdCategory = categoryRepository.findOne(p.getThirdCategoryId());
	  if (null != thirdCategory){
		equipmentProductEs.setThirdCategoryName(thirdCategory.getName());

		EquipmentCategoryDetails details = equipmentCategoryDetailsRepository.findByCategory(thirdCategory);
		if (null != details){
		  equipmentProductEs.setUnit(details.getUnit());
		}
	  }

	  list.add(equipmentProductEs);
	});

	if (!list.isEmpty()) {
	  equipmentProductEsRepository.save(list);
	}
	log.debug("end time {}", System.currentTimeMillis());
  }

  private static <T> String getIndexName(Class<T> tClass) {
	boolean flag = tClass.isAnnotationPresent(Document.class);
	if (flag) {
	  Document document = tClass.getAnnotation(Document.class);
	  return document.indexName();
	}
	return null;
  }

  private EquipmentProductPropertyEs getEquipmentProductPropertyEs(String oCode, String oName, String pCode, String pName) {
	EquipmentProductPropertyOptionsEs optionsEs = new EquipmentProductPropertyOptionsEs();
	optionsEs.setCode(oCode);
	optionsEs.setName(oName);

	EquipmentProductPropertyEs equipmentProductPropertyEs = new EquipmentProductPropertyEs();
	equipmentProductPropertyEs.setName(pName);
	equipmentProductPropertyEs.setCode(pCode);
	equipmentProductPropertyEs.setOptions(optionsEs);
	return equipmentProductPropertyEs;
  }
}
