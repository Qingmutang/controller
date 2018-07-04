package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modianli.power.DTOUtils;
import com.modianli.power.MessagingConstants;
import com.modianli.power.common.exception.OptionsNotInCategoryException;
import com.modianli.power.common.exception.ProductExistedException;
import com.modianli.power.common.exception.ProductHasSamePropertyException;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.domain.es.EnterpriseProductPriceEs;
import com.modianli.power.domain.es.EquipmentCategoryEs;
import com.modianli.power.domain.es.EquipmentProductEs;
import com.modianli.power.domain.es.EquipmentProductPropertyEs;
import com.modianli.power.domain.es.EquipmentProductPropertyOptionsEs;
import com.modianli.power.domain.es.EquipmentPropertyOptionsEs;
import com.modianli.power.domain.jpa.EquipmentCategory;
import com.modianli.power.domain.jpa.EquipmentCategoryDetails;
import com.modianli.power.domain.jpa.EquipmentPropertyOptions;
import com.modianli.power.domain.jpa.Product;
import com.modianli.power.domain.jpa.ProductPropertyOptions;
import com.modianli.power.model.EquipmentPropertyEsDetails;
import com.modianli.power.model.EquipmentPropertyOptionsEsDetails;
import com.modianli.power.model.EquipmentThirdCategoryDetails;
import com.modianli.power.model.ProductCategoryPropertyDetails;
import com.modianli.power.model.ProductCriteria;
import com.modianli.power.model.ProductDetails;
import com.modianli.power.model.ProductForm;
import com.modianli.power.model.ProductFormDetail;
import com.modianli.power.model.ProductPropertyCriteria;
import com.modianli.power.model.ProductPropertyOptionsDetails;
import com.modianli.power.model.ProductPropertyOptionsForm;
import com.modianli.power.model.ProductPubCriteria;
import com.modianli.power.model.ProductPubDetails;
import com.modianli.power.persistence.repository.es.EnterpriseProductPriceEsRepository;
import com.modianli.power.persistence.repository.es.EquipmentCategoryEsRepository;
import com.modianli.power.persistence.repository.es.EquipmentProductEsRepository;
import com.modianli.power.persistence.repository.jpa.EquipmentCategoryDetailsRepository;
import com.modianli.power.persistence.repository.jpa.EquipmentCategoryRepository;
import com.modianli.power.persistence.repository.jpa.EquipmentPropertyOptionsRepository;
import com.modianli.power.persistence.repository.jpa.ProductPropertyOptionsRepository;
import com.modianli.power.persistence.repository.jpa.ProductRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Created by dell on 2017/3/8.
 */
@Service
@Slf4j
public class ProductService {

  @Inject
  private ProductRepository productRepository;

  @Inject
  private ProductPropertyOptionsRepository productPropertyOptionsRepository;

  @Inject
  private EquipmentPropertyOptionsRepository propertyOptionsRepository;

  @Inject
  private EquipmentCategoryRepository categoryRepository;

  @Inject
  private EquipmentProductEsRepository equipmentProductEsRepository;

  @Inject
  private ElasticsearchTemplate elasticsearchTemplate;

  @Inject
  private EquipmentCategoryRepository equipmentCategoryRepository;

  @Inject
  private EquipmentCategoryEsRepository equipmentCategoryEsRepository;

  @Inject
  private EnterpriseProductPriceEsRepository enterpriseProductPriceEsRepository;

  @Inject
  private ElasticAliasesService elasticAliasesService;

  @Inject
  private ObjectMapper objectMapper;

  @Inject
  private EquipmentCategoryDetailsRepository equipmentCategoryDetailsRepository;

  @Inject
  private RabbitTemplate rabbitTemplate;

  @Inject
  private RedisService redisService;

  @Transactional
  public ProductDetails saveProductAndPropertyOptions(ProductForm form) {
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getOptionsForms(), "optionsForms cannot be null");
	Assert.notNull(form.getCategoryId(), "categoryId cannot be null");
	log.debug("saveProduct form {}", form);

	EquipmentCategory equipmentCategory = categoryRepository.findByIdAndLevel(form.getCategoryId(), 3);
	if (null == equipmentCategory) {
	  throw new ResourceNotFoundException(
		  " equipmentCategory can't find by id :" + form.getCategoryId());
	}

	if (!checkSameProperty(form.getOptionsForms())) {
	  throw new ProductHasSamePropertyException(
		  " can't add same property");
	}

	if (!checkCategoryPropertyOptions(form.getCategoryId(), form.getOptionsForms())) {
	  throw new OptionsNotInCategoryException(
		  "options not in category");
	}

	Product product = DTOUtils.strictMap(form, Product.class);
	//生成code和name
	updateProductCodeAndName(product, equipmentCategory, form.getOptionsForms());

	String name = product.getName();
	String code = product.getCode();

	if (productRepository.findByName(name) != null) {
	  throw new ProductExistedException("product can't find by name :" + name);
	}

	if (productRepository.findByCode(code) != null) {
	  throw new ProductExistedException("product can't find by code :" + code);
	}

	product.setActive(true);
	product.setFirstCategoryId(equipmentCategory.getParent().getParent().getId());
	product.setSecondCategoryId(equipmentCategory.getParent().getId());
	product.setThirdCategoryId(equipmentCategory.getId());
	product.setUuid(UUID.randomUUID().toString().replace("-",""));

	EquipmentCategoryDetails details = equipmentCategoryDetailsRepository.findByCategory(equipmentCategory);
	if (null != details) {
	  product.setUnit(details.getUnit());
	}

	Product saved = productRepository.save(product);

	List<ProductPropertyOptions> options = Lists.newArrayList();
	if (form.getOptionsForms().size() > 0 && saved != null) {
	  options = formConvert(form.getOptionsForms(), saved);
	  productPropertyOptionsRepository.batchSaveProductPropertyOptions(options);
	}
	//保存ElasticSearch
	saveProductEs(saved, options, equipmentCategory);

	log.debug("save saveProduct {} ", saved);

	return DTOUtils.map(saved, ProductDetails.class);
  }

  @Transactional
  public ProductDetails updateProductAndPropertyOptions(ProductForm form, Long id) {
	Assert.notNull(id, "id cannot be null");
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getOptionsForms(), "optionsForms cannot be null");
	Assert.notNull(form.getCategoryId(), "categoryId cannot be null");
	log.debug("saveProduct form {}", form);

	Product product = productRepository.findOne(id);
	if (null == product) {
	  throw new ResourceNotFoundException(
		  " product can't find by id :" + id);
	}

	EquipmentCategory equipmentCategory = categoryRepository.findByIdAndLevel(form.getCategoryId(), 3);
	if (null == equipmentCategory) {
	  throw new ResourceNotFoundException(
		  " equipmentCategory can't find by id :" + form.getCategoryId());
	}

	if (!checkSameProperty(form.getOptionsForms())) {
	  throw new ProductHasSamePropertyException(
		  " can't add same property");
	}

	if (!checkCategoryPropertyOptions(form.getCategoryId(), form.getOptionsForms())) {
	  throw new OptionsNotInCategoryException(
		  "options not in category");
	}
	//生成code和name
	updateProductCodeAndName(product, equipmentCategory, form.getOptionsForms());

	String name = product.getName();
	String code = product.getCode();

	Product p1 = productRepository.findByName(name);
	if (p1 != null && !id.equals(p1.getId())) {
	  throw new ProductExistedException("product can't find by name :" + name);
	}

	Product p2 = productRepository.findByCode(code);
	if (p2 != null && !id.equals(p2.getId())) {
	  throw new ProductExistedException("product can't find by code :" + code);
	}

	if (product.getActive() == null) {
	  product.setActive(true);
	}

	Product saved = productRepository.save(product);

	if (form.getOptionsForms().size() > 0) {
	  updateProductPropertyOptions(form.getOptionsForms(), saved);
	  //保存ElasticSearch
	  saveProductEs(product, formConvert(form.getOptionsForms(), saved), equipmentCategory);
	}

	log.debug("save saveProduct {} ", saved);

	redisService.deleteProduct(product.getUuid());

	return DTOUtils.map(saved, ProductDetails.class);
  }

  @Transactional
  public ProductDetails updateProductForm(ProductFormDetail productForm, Long id) {
	Assert.notNull(id, "id cannot be null");
	Assert.notNull(productForm.getDefaultPrice(), "defaultPrice cannot be null");

	Product product = productRepository.findOne(id);
	if (null == product) {
	  throw new ResourceNotFoundException(
		  " product can't find by id :" + id);
	}

	if (StringUtils.isNotBlank(productForm.getPic())){
	  product.setPic(productForm.getPic());
	}
	product.setDefaultPrice(productForm.getDefaultPrice());
	Product saved = productRepository.save(product);

	//elasticsearch 更新
	EquipmentProductEs es = equipmentProductEsRepository.findOne(id);
	if (null != es){
	  if (StringUtils.isNotBlank(productForm.getPic())) {
		es.setPic(productForm.getPic());
	  }

	  es.setDefaultPrice(productForm.getDefaultPrice());
	  rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_INDEX, MessagingConstants.ROUTING_INDEX, es);
	}

	redisService.deleteProduct(product.getUuid());

	return DTOUtils.map(saved, ProductDetails.class);
  }

  @Transactional
  public void restoreProduct(Long id) {
	productRepository.updateActiveById(id, true);
	EquipmentProductEs es = equipmentProductEsRepository.findOne(id);
	if (null != es) {
	  es.setActive(true);
	  equipmentProductEsRepository.save(es);
	  redisService.deleteProduct(es.getUuid());
	}
  }

  @Transactional
  public void deactivateProduct(Long id) {
	productRepository.updateActiveById(id, false);
	EquipmentProductEs es = equipmentProductEsRepository.findOne(id);
	if (null != es) {
	  es.setActive(false);
	  equipmentProductEsRepository.save(es);
	  redisService.deleteProduct(es.getUuid());
	}
  }

  public void deleteAll() {
	equipmentProductEsRepository.deleteAll();
  }

  @Transactional
  public ProductPropertyOptionsDetails saveProductPropertyOptions(ProductPropertyOptionsForm form, Long productId) {
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getEquipmentPropertyOptionsFK(), "equipmentPropertyOptionsFK cannot be null");
	Assert.notNull(form.getEquipmentPropertyOptionsFK().getId(), "equipmentPropertyOptionsFK.id cannot be null");

	log.debug("saveProductPropertyOptions form {}", form);

	ProductPropertyOptions productPropertyOptions = DTOUtils.strictMap(form, ProductPropertyOptions.class);

	Product product = productRepository.findOne(productId);
	if (null == product) {
	  throw new ResourceNotFoundException(" product can't find by id :" + productId);
	}

	EquipmentPropertyOptions
		equipmentPropertyOptions =
		propertyOptionsRepository.findOne(form.getEquipmentPropertyOptionsFK().getId());
	if (null == equipmentPropertyOptions) {
	  throw new ResourceNotFoundException(
		  " equipmentPropertyOptions can't find by id :" + form.getEquipmentPropertyOptionsFK().getId());
	}

	productPropertyOptions.setProduct(product);

	productPropertyOptions.setEquipmentPropertyOptions(equipmentPropertyOptions);

	productPropertyOptions.setEquipmentPropertyName(equipmentPropertyOptions.getEquipmentProperty().getName());

	productPropertyOptions.setEquipmentPropertyOptionsName(equipmentPropertyOptions.getValue());

	ProductPropertyOptions saved = productPropertyOptionsRepository.save(productPropertyOptions);

	log.debug("save productPropertyOptions {} ", saved);

	return DTOUtils.map(saved, ProductPropertyOptionsDetails.class);
  }

  public ProductPubDetails getProduct(String uuid) {
	Assert.hasText(uuid, "id cannot be null");

	EquipmentProductEs es = equipmentProductEsRepository.findByUuid(uuid);
	if (null == es) {
	  return null;
	}

	ProductPubDetails details = DTOUtils.map(es, ProductPubDetails.class);
	EquipmentProductEs productEs = equipmentProductEsRepository.findByUuid(uuid);
	if (null != productEs) {
	  EquipmentCategory category = categoryRepository.findOne(productEs.getThirdCategoryId());
	  if (null != category) {
		details.setThirdCategoryName(category.getName());
		details.setSecondCategoryName(category.getParent().getName());
		details.setFirstCategoryName(category.getParent().getParent().getName());
	  }
	}

	List<ProductPropertyOptionsDetails> propertyOptionsDetails = Lists.newArrayList();
	es.getInfo().stream().forEach(p -> {
	  ProductPropertyOptionsDetails optionsDetails = ProductPropertyOptionsDetails
		  .builder()
		  .equipmentPropertyName(p.getName())
		  .equipmentPropertyOptionsName(p.getOptions().getName()).build();
	  propertyOptionsDetails.add(optionsDetails);
	});

	details.setPropertyOptionsDetails(propertyOptionsDetails);

	return details;
  }

  public Page<ProductDetails> searchProduct(ProductCriteria productCriteria, Pageable page) {

//	builder.must(
//		nestedQuery("info", boolQuery().must(matchQuery("info.code", "0005"))
//									   .must(matchQuery("info.options.code", "002"))))
//									   .must(nestedQuery("info.options", matchQuery("info.options.code", "001")))));

//	builder.must(
//		nestedQuery("info", boolQuery().must(matchQuery("info.code", "0006"))
//									   .must(matchQuery("info.options.code", "002"))))
//									   .must(nestedQuery("info.options", matchQuery("info.options.code", "002")))))
//		   .must(nestedQuery("info.options", matchQuery("info.options.code", "002")))
	;

	final BoolQueryBuilder builder = boolQuery();
	if (null != productCriteria) {
	  if (null != productCriteria.getActive()) {
		builder.filter(termQuery("active", productCriteria.getActive()));
	  }
	  if (StringUtils.isNotBlank(productCriteria.getName())) {
		String name = QueryParser.escape(productCriteria.getName());
		builder.should(matchQuery("name", name));
		builder.should(queryStringQuery("*" + name + "*").field("name").field("code").analyzeWildcard(true))
			   .minimumNumberShouldMatch(1);
	  }
	}

	final SearchQuery searchQuery = new NativeSearchQueryBuilder()
		.withQuery(builder)
		.withPageable(page)
		.withSort(SortBuilders.scoreSort())
		.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
		.withIndices("product")
		.withTypes("product")
		.build();

	log.debug("query \r\n {}", builder);

	if (elasticsearchTemplate.count(searchQuery) == 0) {
	  throw new ResourceNotFoundException("product cannot find");
	}

	final Page<EquipmentProductEs> equipmentProductEs = elasticsearchTemplate.queryForPage(searchQuery, EquipmentProductEs.class);
	log.debug("page {} ", equipmentProductEs.getContent());
	return DTOUtils.mapPage(equipmentProductEs, ProductDetails.class);

  }

  public void rebuildIndex() {
	rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_INDEX, MessagingConstants.ROUTING_INDEX_BATCH, "rebuild");
  }

  /**
   * 前端查询产品列表
   */
  public ProductCategoryPropertyDetails searchPubProduct(ProductPubCriteria productCriteria, Pageable page) {

	//查询所有的产品
	final BoolQueryBuilder builder = boolQuery();
	builder.filter(termQuery("active", true));
	if (null != productCriteria) {
	  if (StringUtils.isNotBlank(productCriteria.getName())) {
		String name = QueryParser.escape(productCriteria.getName());
//		builder.must(queryStringQuery(productCriteria.getName()).field("name"));
		builder.should(matchQuery("name", name));
		builder.should(queryStringQuery("*" + name + "*").field("name").analyzeWildcard(true))
			   .minimumNumberShouldMatch(1);
	  }
	  List<ProductPropertyCriteria> productProperties = productCriteria.getProductProperties();
	  if (null != productProperties && !productProperties.isEmpty()) {
		productProperties.stream().forEach(p -> {
		  String propertyCode = p.getPropertyCode();
		  String optionCode = p.getOptionCode();
		  if (StringUtils.isNotBlank(propertyCode) && StringUtils.isNotBlank(optionCode)) {
			builder.must(
				nestedQuery("info", boolQuery().must(matchQuery("info.code", propertyCode))
											   .must(nestedQuery("info.options", boolQuery()
												   .must(matchQuery("info.options.code", optionCode))))));
		  }

		});
	  }

	  if (null != productCriteria.getThirdCategoryId() && productCriteria.getThirdCategoryId() > 0) {
		builder.filter(termQuery("thirdCategoryId", productCriteria.getThirdCategoryId()));
	  }
	}
	final SearchQuery searchQuery = new NativeSearchQueryBuilder()
		.withQuery(builder)
		.withPageable(page)
		.withSort(SortBuilders.scoreSort())
		.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
		.withIndices("product")
		.withTypes("product")
		.withHighlightFields(new HighlightBuilder.Field("name")
			, new HighlightBuilder.Field("code"))
		.build();

	log.debug("query \r\n {}", builder.toString());

	if (elasticsearchTemplate.count(searchQuery) == 0) {
	  throw new ResourceNotFoundException("product cannot find");
	}

	final Page<EquipmentProductEs> equipmentProductEs =
		elasticsearchTemplate.queryForPage(searchQuery, EquipmentProductEs.class
			, new SearchResultMapper() {
			  @Override
			  public <T> AggregatedPage<T> mapResults(SearchResponse response,
													  Class<T> aClass,
													  Pageable pageable) {
				List<EquipmentProductEs> productEs = new ArrayList<>();
				for (SearchHit searchHit : response.getHits()) {
				  if (searchHit != null) {

					EquipmentProductEs es = setMapToEquipmentProductEs(searchHit.getSourceAsString());
					if (null != searchHit.getSource().get("defaultPrice")) {
					  es.setDefaultPrice(new BigDecimal(Double.valueOf(searchHit.getSource().get("defaultPrice").toString())));
					}

					HighlightField name = searchHit.getHighlightFields().get("name");
					if (null != name) {
					  es.setName(name.fragments()[0].toString());
					}

					HighlightField code = searchHit.getHighlightFields().get("code");
					if (null != code) {
					  es.setCode(code.fragments()[0].toString());
					}
					productEs.add(es);
				  }
				}
				return new AggregatedPageImpl<T>((List<T>) productEs, pageable, response.getHits().getTotalHits());
			  }
			});

	List<ProductPubDetails> productPubDetailList = Lists.newArrayList();
	equipmentProductEs.getContent().forEach(p -> {
	  ProductPubDetails detail = DTOUtils.map(p, ProductPubDetails.class);
	  List<ProductPropertyOptionsDetails> propertyOptionsDetails = Lists.newArrayList();
	  p.getInfo().stream().forEach(property -> {
		propertyOptionsDetails.add(ProductPropertyOptionsDetails.builder()
																.equipmentPropertyName(property.getName())
																.equipmentPropertyOptionsName(property.getOptions().getName())
																.build());
	  });
	  detail.setPropertyOptionsDetails(propertyOptionsDetails);

	  EnterpriseProductPriceEs enterpriseProductPriceEs = enterpriseProductPriceEsRepository.findOne(p.getId());
	  if (null != enterpriseProductPriceEs) {
		detail.setLastedPriceDate(
			LocalDateTime.ofInstant(enterpriseProductPriceEs.getLastedTime().toInstant(), ZoneId.systemDefault()).toLocalDate());
		detail.setPriceCount(enterpriseProductPriceEs.getPriceCount());
	  }

	  productPubDetailList.add(detail);
	});
	Page<ProductPubDetails> productPubDetails = DTOUtils.mapPage(equipmentProductEs, ProductPubDetails.class);
	Page<ProductPubDetails>
		productPubDetailsList =
		new PageImpl<>(productPubDetailList,
					   new PageRequest(productPubDetails.getNumber(), productPubDetails.getSize(), productPubDetails.getSort()),
					   productPubDetails.getTotalElements());

	if (null != productCriteria.getProductProperties()) {
	  return ProductCategoryPropertyDetails.builder()
										   .productDetails(productPubDetailsList).build();
	}

	//查询三级分类列表以及属性列表
	List<EquipmentProductEs> productEs = equipmentProductEs.getContent();
	List<EquipmentThirdCategoryDetails> thirdCategoryDetailss = Lists.newArrayList();
	List<EquipmentPropertyEsDetails> list = Lists.newArrayList();
	String secondCategoryName = "";
	if (!productEs.isEmpty()) {
	  EquipmentProductEs productE = productEs.get(0);
	  if (null != productE) {
		List<EquipmentCategory> categories =
			equipmentCategoryRepository.findByParent(
				equipmentCategoryRepository.findOne(productE.getSecondCategoryId()));

		EquipmentCategoryEs thirdCategroies = equipmentCategoryEsRepository.findOne(productE.getThirdCategoryId() + "");
		EquipmentCategory secondCategroies = equipmentCategoryRepository.findOne(productE.getSecondCategoryId());
		secondCategoryName = secondCategroies.getName();
		for (EquipmentCategory equipmentCategory : categories) {
		  EquipmentThirdCategoryDetails details = DTOUtils.map(equipmentCategory, EquipmentThirdCategoryDetails.class);
		  if (thirdCategroies.getId().equals(equipmentCategory.getId() + "")) {
			details.setChoosed(true);
		  } else {
			details.setChoosed(false);
		  }
		  thirdCategoryDetailss.add(details);
		}

		Map<String, String> Properties = new HashMap<>();
		productE.getInfo().stream().forEach(p -> {
		  Properties.put(p.getCode(), p.getOptions().getCode());
		});

		if (null != thirdCategroies) {
		  thirdCategroies.getCategoryDetails().stream().forEach(p -> {

			List<EquipmentPropertyOptionsEs> propertyOptionsEs = p.getPropertyOptionsEs();
			List<EquipmentPropertyOptionsEsDetails> propertyOptionsEsDetailss = Lists.newArrayList();
			String code = p.getCode();
			propertyOptionsEs.stream().forEach(o -> {
			  EquipmentPropertyOptionsEsDetails
				  propertyOptionsEsDetails =
				  DTOUtils.map(o, EquipmentPropertyOptionsEsDetails.class);
			  propertyOptionsEsDetailss.add(propertyOptionsEsDetails);
			});
			EquipmentPropertyEsDetails propertyEsDetails = EquipmentPropertyEsDetails.builder()
																					 .id(p.getId())
																					 .name(p.getName())
																					 .code(code)
																					 .propertyOptionsEs(propertyOptionsEsDetailss)
																					 .build();
			list.add(propertyEsDetails);
		  });
		}
	  }
	}
	return ProductCategoryPropertyDetails.builder()
										 .productDetails(productPubDetailsList)
										 .thirdCategoryDetails(thirdCategoryDetailss)
										 .secondCategoryName(secondCategoryName)
										 .propertyDetails(list).build();

  }

  private EquipmentProductEs setMapToEquipmentProductEs(String jsonStr) {
	try {
	  return objectMapper.readValue(jsonStr, EquipmentProductEs.class);
	} catch (IOException e) {
	  e.printStackTrace();
	}
	return null;
  }

  private List<ProductPropertyOptions> formConvert(List<ProductPropertyOptionsForm> forms, Product product) {
	List<ProductPropertyOptions> options = Lists.newArrayList();

	for (ProductPropertyOptionsForm form : forms) {
	  Assert.notNull(form, "form cannot be null");
	  Assert.notNull(form.getEquipmentPropertyOptionsFK(), "equipmentPropertyOptionsFK cannot be null");
	  Assert.notNull(form.getEquipmentPropertyOptionsFK().getId(), "equipmentPropertyOptionsFK.id cannot be null");

	  log.debug("saveProductPropertyOptions form {}", form);

	  ProductPropertyOptions productPropertyOptions = DTOUtils.strictMap(form, ProductPropertyOptions.class);

	  EquipmentPropertyOptions
		  equipmentPropertyOptions =
		  propertyOptionsRepository.findOne(form.getEquipmentPropertyOptionsFK().getId());
	  if (null == product) {
		throw new ResourceNotFoundException(
			" equipmentPropertyOptions can't find by id :" + form.getEquipmentPropertyOptionsFK().getId());
	  }

	  productPropertyOptions.setProduct(product);
	  productPropertyOptions.setEquipmentPropertyOptions(equipmentPropertyOptions);
	  productPropertyOptions.setEquipmentPropertyName(equipmentPropertyOptions.getEquipmentProperty().getName());
	  productPropertyOptions.setEquipmentPropertyOptionsName(equipmentPropertyOptions.getValue());
	  productPropertyOptions.setName(product.getName());
	  options.add(productPropertyOptions);
	}
	return options;
  }

  /**
   * 生成code和name
   */
  private Product updateProductCodeAndName(Product product, EquipmentCategory category,
										   List<ProductPropertyOptionsForm> optionsForms) {
	String categoriesCode = "";
	StringBuffer name = new StringBuffer();
	if (null != category) {
	  categoriesCode = category.getParent().getParent().getCode() +
					   category.getParent().getCode() + category.getCode();
	  name.append(category.getName());
	  for (ProductPropertyOptionsForm form : optionsForms) {
		EquipmentPropertyOptions
			equipmentPropertyOptions =
			propertyOptionsRepository.findOne(form.getEquipmentPropertyOptionsFK().getId());
		name.append("/");
		name.append(equipmentPropertyOptions.getValue());
	  }
	}

	String code = "";
	while (true) {
	  code = categoriesCode + RandomStringUtils.randomNumeric(6);
	  Product p = productRepository.findByCode(code);
	  if (null == p) {
		break;
	  }
	}

	product.setCode(code);
	product.setName(name.toString());
	return product;
  }

  private boolean checkSameProperty(List<ProductPropertyOptionsForm> optionsForms) {
	Map<Long, ProductPropertyOptionsForm> map = new HashMap<>();
	for (ProductPropertyOptionsForm form : optionsForms) {
	  if (form == null || form.getEquipmentPropertyOptionsFK() == null) {
		throw new ResourceNotFoundException(
			" equipmentPropertyOptions can't find by id :null");
	  }
	  EquipmentPropertyOptions options = propertyOptionsRepository.findOne(form.getEquipmentPropertyOptionsFK().getId());
	  if (null != options) {
		Long id = options.getId();
		if (map.get(id) == null) {
		  map.put(id, form);
		} else {
		  return false;
		}
	  }
	}
	return true;
  }

  private boolean checkCategoryPropertyOptions(Long id, List<ProductPropertyOptionsForm> forms) {
	List<Long> options = productRepository.getIdsByCategoryId(id);
	for (ProductPropertyOptionsForm form : forms) {
	  if (!options.contains(form.getEquipmentPropertyOptionsFK().getId())) {
		return false;
	  }
	}
	return true;
  }

  private void saveProductEs(Product product, List<ProductPropertyOptions> productPropertyOptions, EquipmentCategory category) {
	EquipmentProductEs equipmentProductEs = DTOUtils.map(product, EquipmentProductEs.class);
	equipmentProductEs.setActive(product.getActive() == null ? Boolean.TRUE : product.getActive());

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
	equipmentProductEs.setFirstCategoryId(category.getParent().getParent().getId());
	equipmentProductEs.setSecondCategoryId(category.getParent().getId());
	equipmentProductEs.setThirdCategoryId(category.getId());
	equipmentProductEs.setThirdCategoryName(category.getName());
	equipmentProductEs.setSecondCategoryName(category.getParent().getName());
	equipmentProductEs.setFirstCategoryName(category.getParent().getParent().getName());
	equipmentProductEs.setInfo(info);

	EquipmentCategoryDetails details = equipmentCategoryDetailsRepository.findByCategory(category);
	if (null != details) {
	  equipmentProductEs.setUnit(details.getUnit());
	}

	rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_INDEX, MessagingConstants.ROUTING_INDEX, equipmentProductEs);
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

  private void updateProductPropertyOptions(List<ProductPropertyOptionsForm> optionsFo, Product product) {
	List<ProductPropertyOptions> productPropertyOptions = productPropertyOptionsRepository.findByProduct(product);

	for (ProductPropertyOptionsForm optionsForm : optionsFo) {
	  boolean found = false;

	  for (ProductPropertyOptions propertyOption : productPropertyOptions) {
		if (propertyOption.getId().equals(optionsForm.getEquipmentPropertyOptionsFK().getId())) {
		  found = true;
		  break;
		}
	  }

	  if (found == false) {
		EquipmentPropertyOptions
			equipmentPropertyOptions =
			propertyOptionsRepository.findOne(optionsForm.getEquipmentPropertyOptionsFK().getId());
		ProductPropertyOptions propertyOptions = ProductPropertyOptions
			.builder()
			.name(product.getName())
			.equipmentPropertyOptions(equipmentPropertyOptions)
			.equipmentPropertyOptionsName(equipmentPropertyOptions.getValue())
			.equipmentPropertyName(equipmentPropertyOptions.getEquipmentProperty().getName())
			.product(product)
			.build();
		productPropertyOptionsRepository.save(propertyOptions);
	  }
	}

	//found removed
	for (ProductPropertyOptions propertyOption : productPropertyOptions) {
	  boolean found = false;

	  for (ProductPropertyOptionsForm optionsForm : optionsFo) {
		if (propertyOption.getId().equals(optionsForm.getEquipmentPropertyOptionsFK().getId())) {
		  found = true;
		  break;
		}
	  }

	  if (found == false) {
		productPropertyOptionsRepository.delete(propertyOption);
	  }
	}
  }

  @Transactional
  @Deprecated
  public void resetProductUuid(){
    List<Product> products = productRepository.findByUuidIsNull();
    products.stream().forEach(p->{
	  p.setUuid(UUID.randomUUID().toString().replace("-",""));
	  productRepository.save(p);
	});

	rebuildIndex();
  }

}
