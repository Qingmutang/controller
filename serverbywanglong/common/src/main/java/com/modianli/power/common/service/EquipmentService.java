package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modianli.power.DTOUtils;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.common.utils.ClassUtils;
import com.modianli.power.domain.es.EquipmentCategoryEs;
import com.modianli.power.domain.es.EquipmentFirstCategoryEs;
import com.modianli.power.domain.es.EquipmentHotFirstCategoryEs;
import com.modianli.power.domain.es.EquipmentPropertyEs;
import com.modianli.power.domain.es.EquipmentPropertyOptionsEs;
import com.modianli.power.domain.es.EquipmentSecondCategoryEs;
import com.modianli.power.domain.es.EquipmentThirdCategoryEs;
import com.modianli.power.domain.jpa.EquipmentCategory;
import com.modianli.power.domain.jpa.EquipmentCategoryDetails;
import com.modianli.power.domain.jpa.EquipmentProperty;
import com.modianli.power.domain.jpa.EquipmentPropertyOptions;
import com.modianli.power.domain.jpa.Product;
import com.modianli.power.model.EquipmentCategoryForm;
import com.modianli.power.model.EquipmentFirstCategoryDetails;
import com.modianli.power.model.EquipmentHomeFirstCategoryDetails;
import com.modianli.power.model.EquipmentHomeSecondCategoryDetails;
import com.modianli.power.model.EquipmentHomeThirdCategoryDetails;
import com.modianli.power.model.EquipmentPropertyDetails;
import com.modianli.power.model.EquipmentPropertyEsDetails;
import com.modianli.power.model.EquipmentPropertyForm;
import com.modianli.power.model.EquipmentPropertyOptionsDetails;
import com.modianli.power.model.EquipmentPropertyOptionsEsDetails;
import com.modianli.power.model.EquipmentPropertyOptionsForm;
import com.modianli.power.model.ProductDetails;
import com.modianli.power.model.ProductForm;
import com.modianli.power.persistence.repository.es.EquipmentAllCategoryEsRepository;
import com.modianli.power.persistence.repository.es.EquipmentCategoryEsRepository;
import com.modianli.power.persistence.repository.es.EquipmentHotCategoryEsRepository;
import com.modianli.power.persistence.repository.jpa.EquipmentCategoryDetailsRepository;
import com.modianli.power.persistence.repository.jpa.EquipmentCategoryRepository;
import com.modianli.power.persistence.repository.jpa.EquipmentPropertyOptionsRepository;
import com.modianli.power.persistence.repository.jpa.EquipmentPropertyRepository;
import com.modianli.power.persistence.repository.jpa.ProductPropertyOptionsRepository;
import com.modianli.power.persistence.repository.jpa.ProductRepository;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by gao on 17-2-23.
 */
@Service
@Slf4j
public class EquipmentService {

  public static final String path = "resource.xls";
  public static final String sheetName = "物资分类主数据树形结构";

  @Inject
  private EquipmentPropertyRepository equipmentPropertyRepository;
  @Inject
  private EquipmentPropertyOptionsRepository equipmentPropertyOptionsRepository;
  @Inject
  private ProductPropertyOptionsRepository productPropertyOptionsRepository;
  @Inject
  private ProductRepository productRepository;
  @Inject
  private ResourceLoader resourceLoader;
  @Inject
  private EquipmentCategoryRepository equipmentCategoryRepository;
  @Inject
  private EquipmentCategoryEsRepository equipmentCategoryEsRepository;

  @Inject
  private EquipmentAllCategoryEsRepository equipmentAllCategoryEsRepository;

  @Inject
  private EquipmentHotCategoryEsRepository equipmentHotCategoryEsRepository;

  @Inject
  private ObjectMapper objectMapper;

  @Inject
  private ElasticsearchTemplate elasticsearchTemplate;

  @Inject
  private ElasticAliasesService elasticAliasesService;

  @Inject
  private EquipmentCategoryDetailsRepository equipmentCategoryDetailsRepository;

  @Inject
  private RedisService redisService;

  @Transactional
  public com.modianli.power.model.EquipmentCategoryDetails saveEquipmentCategory(EquipmentCategoryForm form) {
	Assert.notNull(form, "form cannot be null");
//	Assert.notNull(form.getEquipmentCategoryFK(), "equipmentCategoryFK cannot be null");
	log.debug("saveEquipmentCategory form {}", form);
	EquipmentCategory category = DTOUtils.strictMap(form, EquipmentCategory.class);

	EquipmentCategory parentCategory = null;
	if (null != form.getEquipmentCategoryFK()) {

	  parentCategory = equipmentCategoryRepository.findOne(form.getEquipmentCategoryFK().getId());
	}

//	if (parentCategory == null) {
//	  throw new ResourceNotFoundException(" parentCategory can't find by id :" + form.getEquipmentCategoryFK().getId());
//	}
	category.setParent(parentCategory);

	EquipmentCategory saved = equipmentCategoryRepository.save(category);

	log.debug("save category {} ", saved);

	return DTOUtils.map(saved, com.modianli.power.model.EquipmentCategoryDetails.class);
  }

  @Transactional
  public EquipmentPropertyDetails saveEquipmentProperty(EquipmentPropertyForm form) {
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getEquipmentCategoryFK(), "equipmentCategoryFK cannot be null");
	Assert.notNull(form.getEquipmentCategoryFK().getId(), "equipmentCategoryFK.id cannot be null");
	log.debug("saveEquipmentProperty form {}", form);

	EquipmentProperty equipmentProperty = DTOUtils.strictMap(form, EquipmentProperty.class);

	EquipmentCategory equipmentCategory = equipmentCategoryRepository.findOne(form.getEquipmentCategoryFK().getId());
	if (null == equipmentCategory) {
	  throw new ResourceNotFoundException(" equipmentCategory can't find by id :" + form.getEquipmentCategoryFK().getId());
	}

	equipmentProperty.setEquipmentCategory(equipmentCategory);

	EquipmentProperty saved = equipmentPropertyRepository.save(equipmentProperty);

	log.debug("save equipmentProperty {} ", saved);

	return DTOUtils.map(saved, EquipmentPropertyDetails.class);
  }

  @Transactional
  public EquipmentPropertyOptionsDetails saveEquipmentPropertyOptions(EquipmentPropertyOptionsForm form) {
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getEquipmentPropertyFK(), "equipmentPropertyFK cannot be null");
	Assert.notNull(form.getEquipmentPropertyFK().getId(), "equipmentPropertyFK.id cannot be null");
	log.debug("saveEquipmentProperty form {}", form);

	EquipmentPropertyOptions equipmentPropertyOptions = DTOUtils.strictMap(form, EquipmentPropertyOptions.class);

	EquipmentProperty equipmentProperty = equipmentPropertyRepository.findOne(form.getEquipmentPropertyFK().getId());
	if (null == equipmentProperty) {
	  throw new ResourceNotFoundException(" equipmentProperty can't find by id :" + form.getEquipmentPropertyFK().getId());
	}

	equipmentPropertyOptions.setEquipmentProperty(equipmentProperty);

	EquipmentPropertyOptions saved = equipmentPropertyOptionsRepository.save(equipmentPropertyOptions);

	log.debug("save equipmentPropertyOptions {} ", saved);

	return DTOUtils.map(saved, EquipmentPropertyOptionsDetails.class);
  }

  @Transactional
  public ProductDetails saveProduct(ProductForm form) {
	Assert.notNull(form, "form cannot be null");
	log.debug("saveProduct form {}", form);

	Product product = DTOUtils.strictMap(form, Product.class);

	Product saved = productRepository.save(product);

	log.debug("save saveProduct {} ", saved);

	return DTOUtils.map(saved, ProductDetails.class);
  }

  @Transactional
  public void importData() {
	try {
	  Resource resource = resourceLoader.getResource("classpath:product.txt");
	  List<String> lines = IOUtils.readLines(resource.getInputStream(), "UTF-8");
	  lines.stream().forEach(l -> {
		String[] str = l.split("\t");
		String code = str[0];
		String name = str[1];
		EquipmentCategory equipmentCategory = equipmentCategoryRepository.findByNameAndCodeAndLevel(name, code, 1);
		if (equipmentCategory == null) {
		  equipmentCategory = new EquipmentCategory(name, code, null, null, 1,false);
		  equipmentCategoryRepository.save(equipmentCategory);
		}

		String code1 = str[2];
		String name1 = str[3];
		EquipmentCategory
			equipmentCategory1 =
			equipmentCategoryRepository.findByNameAndCodeAndLevelAndParent(name1, code1, 2, equipmentCategory);
		if (equipmentCategory1 == null) {
		  equipmentCategory1 = new EquipmentCategory(name1, code1, null, equipmentCategory, 2,false);
		  equipmentCategoryRepository.save(equipmentCategory1);
		}

		String code2 = str[4];
		String name2 = str[5];
		EquipmentCategory
			equipmentCategory2 =
			equipmentCategoryRepository.findByNameAndCodeAndLevelAndParent(name2, code2, 3, equipmentCategory1);
		if (equipmentCategory2 == null) {
		  equipmentCategory2 = new EquipmentCategory(name2, code2, null, equipmentCategory1, 3,false);
		  equipmentCategoryRepository.save(equipmentCategory2);
		}

		String unit = str[6];
		if (null != equipmentCategory2){
		  EquipmentCategoryDetails details = EquipmentCategoryDetails.builder()
																	 .category(equipmentCategory2)
																	 .unit(unit)
																	 .build();
		  equipmentCategoryDetailsRepository.save(details);
		}


	  });
	} catch (Exception e) {
	  e.printStackTrace();

	}
  }

  @Transactional
  public void importData1() {
	try {
	  Resource resource = resourceLoader.getResource("classpath:" + path);
	  InputStream is = resource.getInputStream();
	  HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
	  HSSFSheet sheet = hssfWorkbook.getSheet(sheetName);
	  if (null == sheet) {
		throw new NoSuchElementException("不存在该sheet:" + sheetName);
	  }
	  for (int rowNum = 5; rowNum < sheet.getLastRowNum(); rowNum++) {
		HSSFRow row = sheet.getRow(rowNum);
		if (row == null) {
		  continue;
		}
		HSSFCell c1 = row.getCell(4);
		if (c1 == null) {
		  continue;
		}

		HSSFCell c2 = row.getCell(5);
		if (c2 == null) {
		  continue;
		}

		String name = c2.toString();
		String code = c1.toString();
		EquipmentCategory equipmentCategory = equipmentCategoryRepository.findByNameAndCodeAndLevel(name, code, 3);
		if (equipmentCategory == null) {
		  continue;
		}

		System.out.println("三级分类:" + name);
		int rowNum1 = rowNum;
		int col = 7;
		HSSFRow r2 = sheet.getRow(rowNum1);
		EquipmentProperty equipmentProperty = null;
		while (true) {
		  //属性那行查询为空则下一个分类
		  if (r2.getCell(col) == null || StringUtils.isBlank(r2.getCell(col).toString())) {
			break;
		  }

		  HSSFRow r3 = sheet.getRow(rowNum1);
		  if (r3 == null || r3.getCell(col) == null || StringUtils.isBlank(r3.getCell(col).toString())) {
			col += 2;
			rowNum1 = rowNum;
			continue;
		  }

		  String code1 = r3.getCell(col).toString();
		  String name1 = r3.getCell(col + 1).toString();
		  if (r2.getRowNum() == r3.getRowNum()) {
			equipmentProperty=equipmentPropertyRepository.findByCodeAndNameAndEquipmentCategory(code1, name1, equipmentCategory);
			if (equipmentProperty == null) {
			  equipmentProperty = new EquipmentProperty(name1, code1, equipmentCategory);
			  equipmentPropertyRepository.save(equipmentProperty);
			}
		  } else {
			EquipmentPropertyOptions option = new EquipmentPropertyOptions(name1, code1, equipmentProperty);
			if (equipmentPropertyOptionsRepository.findByCodeAndValueAndEquipmentProperty(code1, name1, equipmentProperty)
				== null) {
			  equipmentPropertyOptionsRepository.save(option);
			}
		  }
		  rowNum1 += 1;
		}
	  }

	} catch (Exception e) {
	  e.printStackTrace();
	}
  }

  public void saveEquipmentCategoryToES() {
	elasticsearchTemplate.deleteIndex(EquipmentCategoryEs.class);
	elasticsearchTemplate.createIndex(EquipmentCategoryEs.class);
	elasticsearchTemplate.putMapping(EquipmentCategoryEs.class);
	elasticsearchTemplate.refresh(EquipmentCategoryEs.class);

	String indexName = ClassUtils.getIndexName(EquipmentCategoryEs.class);

	elasticAliasesService.saveAlias(indexName, "category_property");

	int page = 0;

	boolean flag = true;

	while (flag) {
	  Pageable pageable = new PageRequest(page, 100, new Sort(Sort.Direction.ASC, "id"));
	  Page<EquipmentCategory> equipmentCategories = equipmentCategoryRepository.findByLevel(3, pageable);
	  if (equipmentCategories.getTotalPages() == page) {
		flag = false;
	  }

	  List<EquipmentCategoryEs> bucket = Lists.newArrayList();

	  List<EquipmentCategory> equipmentCategoryList = equipmentCategories.getContent();
	  equipmentCategoryList.stream().forEach(e -> {
		EquipmentCategoryEs equipmentCategoryEs = new EquipmentCategoryEs();
		equipmentCategoryEs.setId(e.getId() + "");

		List<EquipmentProperty> equipmentPropertyList = equipmentPropertyRepository.findByEquipmentCategory(e);

		List<EquipmentPropertyEs> equipmentPropertyEsList = Lists.newArrayList();

		equipmentPropertyList.stream().forEach(p -> {

		  List<EquipmentPropertyOptions>
			  equipmentPropertyOptionsList =
			  equipmentPropertyOptionsRepository.findByEquipmentProperty(p);

		  List<EquipmentPropertyOptionsEs>
			  equipmentPropertyOptionsEsList =
			  DTOUtils.mapList(equipmentPropertyOptionsList, EquipmentPropertyOptionsEs.class);

		  EquipmentPropertyEs equipmentPropertyEs = new EquipmentPropertyEs();
		  equipmentPropertyEs.setId(p.getId());
		  equipmentPropertyEs.setName(p.getName());
		  equipmentPropertyEs.setCode(p.getCode());
		  equipmentPropertyEs.setPropertyOptionsEs(equipmentPropertyOptionsEsList);

		  equipmentPropertyEsList.add(equipmentPropertyEs);

		});
		equipmentCategoryEs.setCategoryDetails(equipmentPropertyEsList);

//		try {
//		  log.debug("equipmentCategoryEs json {}", objectMapper.writeValueAsString(equipmentCategoryEs));
//		} catch (Exception e1) {
//		  e1.printStackTrace();
//		}

		bucket.add(equipmentCategoryEs);
	  });

	  log.debug("bucket {} ", bucket);

	  if (!bucket.isEmpty()){
		equipmentCategoryEsRepository.save(bucket);
	  }

	  page++;
	}

  }

  public void saveAllCategoryToES() {
	elasticsearchTemplate.deleteIndex(EquipmentFirstCategoryEs.class);
	elasticsearchTemplate.createIndex(EquipmentFirstCategoryEs.class);

	elasticsearchTemplate.putMapping(EquipmentFirstCategoryEs.class);

	elasticsearchTemplate.putMapping(EquipmentHotFirstCategoryEs.class);

	elasticsearchTemplate.refresh(EquipmentFirstCategoryEs.class);
	elasticsearchTemplate.refresh(EquipmentHotFirstCategoryEs.class);

	String indexName = ClassUtils.getIndexName(EquipmentFirstCategoryEs.class);

	elasticAliasesService.saveAlias(indexName, "equipment");

	List<EquipmentCategory> firstCategory = equipmentCategoryRepository.findByLevel(1);
	if (!firstCategory.isEmpty()){
	  firstCategory.stream().forEach(f -> {
		EquipmentFirstCategoryEs firstCategoryEs = DTOUtils.map(f,EquipmentFirstCategoryEs.class);
		List<EquipmentCategory> secondCategory = equipmentCategoryRepository.findByParent(f);
		List<EquipmentSecondCategoryEs> secondCategories = Lists.newArrayList();
		secondCategory.stream().forEach(s->{
		  EquipmentSecondCategoryEs secondCategoryEs = DTOUtils.map(s,EquipmentSecondCategoryEs.class);
		  List<EquipmentCategory> thirdCategory = equipmentCategoryRepository.findByParent(s);
		  List<EquipmentThirdCategoryEs> thirdCategories = Lists.newArrayList();
		  thirdCategory.stream().forEach(t->{
			thirdCategories.add(DTOUtils.map(t,EquipmentThirdCategoryEs.class));
		  });
		  secondCategoryEs.setThirdCategoryEs(thirdCategories);
		  secondCategories.add(secondCategoryEs);
		});
		firstCategoryEs.setSecondCategoryEs(secondCategories);
		equipmentAllCategoryEsRepository.save(firstCategoryEs);
		log.debug("添加成功 {}",firstCategoryEs.getId());

	  });
	}
	saveHotCategoryToES();
  }

  private void saveHotCategoryToES() {

	List<EquipmentCategory> firstCategory = equipmentCategoryRepository.findByLevel(1);
	if (!firstCategory.isEmpty()){
	  for (EquipmentCategory f:firstCategory){
		EquipmentHotFirstCategoryEs firstCategoryEs = DTOUtils.map(f, EquipmentHotFirstCategoryEs.class);
		List<EquipmentCategory> secondCategory = equipmentCategoryRepository.findByParent(f);
		List<EquipmentSecondCategoryEs> secondCategories = Lists.newArrayList();
		boolean flag = false;
		for (EquipmentCategory s :secondCategory){
		  if (s.getHot()){
			flag = true;
			EquipmentSecondCategoryEs secondCategoryEs = DTOUtils.map(s,EquipmentSecondCategoryEs.class);
			List<EquipmentCategory> thirdCategory = equipmentCategoryRepository.findByParent(s);
			List<EquipmentThirdCategoryEs> thirdCategories = Lists.newArrayList();
			thirdCategory.stream().forEach(t->{
			  if (t.getHot()){
				thirdCategories.add(DTOUtils.map(t,EquipmentThirdCategoryEs.class));
			  }
			});
			secondCategoryEs.setThirdCategoryEs(thirdCategories);
			secondCategories.add(secondCategoryEs);
		  }
		}
		if (!flag) continue;

		firstCategoryEs.setSecondCategoryEs(secondCategories);

		firstCategoryEs.setHotThirdCategoryEs(
			DTOUtils.mapList(equipmentCategoryRepository.getHotThirdCategoryByFirst(f),EquipmentThirdCategoryEs.class));
		equipmentHotCategoryEsRepository.save(firstCategoryEs);
	  }
	}
  }

  public List<com.modianli.power.model.EquipmentCategoryDetails> searchCategories(Long id) {
    if (null == id){
      return DTOUtils.mapList(equipmentCategoryRepository.findByLevel(1), com.modianli.power.model.EquipmentCategoryDetails.class);
	}
	EquipmentCategory equipmentCategory = equipmentCategoryRepository.findOne(id);
    if (equipmentCategory != null){
	  return DTOUtils.mapList(equipmentCategoryRepository.findByParent(equipmentCategory),
							  com.modianli.power.model.EquipmentCategoryDetails.class);
	}
    return null;
  }

  /**
   * 查询全部的分类列表
   * @return
   */
  public List<EquipmentFirstCategoryDetails> searchAllCategories() {
	Iterable<EquipmentFirstCategoryEs>  categoryEs = equipmentAllCategoryEsRepository.findAll();
	List<EquipmentFirstCategoryEs> firstCategoryEs = Lists.newArrayList();
	categoryEs.forEach(p->{
	  firstCategoryEs.add(p);
	});

	  return convertToAllCategoryDetails(firstCategoryEs);
  }

  /**
   * 转换成全部分类数据
   * @param firstCategoryEs
   * @return
   */
  private List<EquipmentFirstCategoryDetails> convertToAllCategoryDetails(List<EquipmentFirstCategoryEs> firstCategoryEs){
	if (!firstCategoryEs.isEmpty()){
	  List<EquipmentFirstCategoryDetails> firstCategories = Lists.newArrayList();
	  firstCategoryEs.stream().forEach(p->{
		EquipmentFirstCategoryDetails firstCategory = DTOUtils.map(p,EquipmentFirstCategoryDetails.class);
		List<EquipmentHomeSecondCategoryDetails> secondCategories = Lists.newArrayList();
		if (null != p.getSecondCategoryEs()){
		  p.getSecondCategoryEs().stream().forEach(s->{
			EquipmentHomeSecondCategoryDetails secondCategory = DTOUtils.map(s,EquipmentHomeSecondCategoryDetails.class);
			secondCategory.setThirdCategoryDetails(
				DTOUtils.mapList(s.getThirdCategoryEs(), EquipmentHomeThirdCategoryDetails.class));
			secondCategories.add(secondCategory);
		  });
		  firstCategory.setSecondCategoryDetails(secondCategories);
		}
		firstCategories.add(firstCategory);
	  });
	  return firstCategories;
	}
	return null;
  }

  /**
   * 查询首页的分类列表
   * @return
   */
  public List<EquipmentHomeFirstCategoryDetails> searchEsCategories() {
	final SearchQuery searchQuery = new NativeSearchQueryBuilder()
		.withQuery(matchAllQuery())
		.withPageable(new PageRequest(0,6))
		.withIndices("equipment")
		.withTypes("hot_category")
		.build();

	if(elasticsearchTemplate.count(searchQuery)==0){
	  throw  new ResourceNotFoundException("product cannot find");
	}

	Page<EquipmentHotFirstCategoryEs> firstCategoryEs = elasticsearchTemplate.queryForPage(searchQuery, EquipmentHotFirstCategoryEs.class);

	return convertToHomeCategoryDetails(firstCategoryEs.getContent());
  }

  public List<EquipmentHomeFirstCategoryDetails> searchAllEsCategories() {
	Iterable list = equipmentHotCategoryEsRepository.findAll();
	List<EquipmentHotFirstCategoryEs> firstCategoryEs = Lists.newArrayList();
	list.forEach(p->{
	  firstCategoryEs.add(DTOUtils.map(p,EquipmentHotFirstCategoryEs.class));
	});

	return convertToHomeCategoryDetails(firstCategoryEs);
  }

  /**
   * 转换成首页数据
   * @param firstCategoryEs
   * @return
   */
  private List<EquipmentHomeFirstCategoryDetails> convertToHomeCategoryDetails(List<EquipmentHotFirstCategoryEs> firstCategoryEs){
	if (!firstCategoryEs.isEmpty()){
	  List<EquipmentHomeFirstCategoryDetails> firstCategories = Lists.newArrayList();
	  firstCategoryEs.stream().forEach(p->{
		EquipmentHomeFirstCategoryDetails firstCategory = DTOUtils.map(p,EquipmentHomeFirstCategoryDetails.class);
		List<EquipmentHomeSecondCategoryDetails> secondCategories = Lists.newArrayList();
		if (null != p.getSecondCategoryEs()){
		  p.getSecondCategoryEs().stream().forEach(s->{
			EquipmentHomeSecondCategoryDetails secondCategory = DTOUtils.map(s,EquipmentHomeSecondCategoryDetails.class);
			secondCategory.setThirdCategoryDetails(
				DTOUtils.mapList(s.getThirdCategoryEs(), EquipmentHomeThirdCategoryDetails.class));
			secondCategories.add(secondCategory);
		  });
		  firstCategory.setSecondCategoryDetails(secondCategories);
		}
		List list = p.getHotThirdCategoryEs();
		if (!list.isEmpty() && list.size() > 2) {
		  list = Arrays.asList(Arrays.copyOf(list.toArray(),2));
		}

		firstCategory.setHotCategoryDetails(DTOUtils.mapList(list,EquipmentHomeThirdCategoryDetails.class));
		firstCategories.add(firstCategory);
	  });
	  return firstCategories;
	}
    return null;
  }



//  public List<EquipmentPropertyDetails> searchCategoryProperty(Long id) {
//	if (null == id){
//	  return null;
//	}
//	EquipmentCategory equipmentCategory = equipmentCategoryRepository.findOne(id);
//
//	if (equipmentCategory != null){
//	  List<EquipmentProperty> propertyDetails = equipmentPropertyRepository.findByEquipmentCategory(equipmentCategory);
//	  List<EquipmentPropertyDetails> details = DTOUtils.mapList(propertyDetails,EquipmentPropertyDetails.class);
//	  details.stream().forEach(p ->{
//		List<EquipmentPropertyOptionsDetails> optionsDetails = DTOUtils.mapList(equipmentPropertyOptionsRepository.findByEquipmentProperty(
//			equipmentPropertyRepository.findOne(p.getId())),
//					 EquipmentPropertyOptionsDetails.class);
//		p.setOptionsDetails(optionsDetails);
//	  });
//	  return details;
//	}
//	return null;
//  }

  /**
   * 查询分类属性
   * @param id
   * @return
   */
  public List<EquipmentPropertyEsDetails> searchCategoryProperty(Long id) {
	if (null == id){
	  return null;
	}
	EquipmentCategoryEs es = equipmentCategoryEsRepository.findOne(id + "");

	if (null != es){
	  List<EquipmentPropertyEsDetails> list = Lists.newArrayList();
	  es.getCategoryDetails().stream().forEach(p -> {

		EquipmentPropertyEsDetails propertyEsDetails = EquipmentPropertyEsDetails.builder()
																				 .id(p.getId())
																				 .name(p.getName())
																				 .code(p.getCode())
																				 .propertyOptionsEs(DTOUtils.mapList(p.getPropertyOptionsEs(), EquipmentPropertyOptionsEsDetails.class))
																				 .build();
		list.add(propertyEsDetails);
	  });
	  return list;
	}
	return null;
  }

  /**
   * 添加推荐分类
   * @param id
   */
  @Transactional
  public void saveRecommendCategory(Long id){
    Assert.notNull(id,"id cannot be null");
	EquipmentCategory equipmentCategory = equipmentCategoryRepository.findByIdAndLevel(id,3);
    if (null == equipmentCategory){
      throw  new ResourceNotFoundException("equipmentCategory cannot be found"+id);
	}
	equipmentCategory.setHot(true);
	equipmentCategory.getParent().setHot(true);
	equipmentCategoryRepository.save(equipmentCategory);

	EquipmentCategory firstCategory = equipmentCategory.getParent().getParent();

	EquipmentHotFirstCategoryEs firstCategoryEs = DTOUtils.map(firstCategory, EquipmentHotFirstCategoryEs.class);
	List<EquipmentCategory> secondCategory = equipmentCategoryRepository.findByParent(firstCategory);
	List<EquipmentSecondCategoryEs> secondCategories = Lists.newArrayList();
	boolean flag = false;
	for (EquipmentCategory s :secondCategory){
	  if (s.getHot()){
		flag = true;
		EquipmentSecondCategoryEs secondCategoryEs = DTOUtils.map(s,EquipmentSecondCategoryEs.class);
		List<EquipmentCategory> thirdCategory = equipmentCategoryRepository.findByParent(s);
		List<EquipmentThirdCategoryEs> thirdCategories = Lists.newArrayList();
		thirdCategory.stream().forEach(t->{
		  if (t.getHot()){
			thirdCategories.add(DTOUtils.map(t,EquipmentThirdCategoryEs.class));
		  }
		});
		secondCategoryEs.setThirdCategoryEs(thirdCategories);
		secondCategories.add(secondCategoryEs);
	  }
	}

	if (flag){
	  firstCategoryEs.setSecondCategoryEs(secondCategories);
	  firstCategoryEs.setHotThirdCategoryEs(
		  DTOUtils.mapList(equipmentCategoryRepository.getHotThirdCategoryByFirst(firstCategory),EquipmentThirdCategoryEs.class));
	  equipmentHotCategoryEsRepository.save(firstCategoryEs);
	}
  }

  /**
   * 获取分类单位
   * @param id
   * @return
   */
  public String getCategoryUnit(Long id){
    EquipmentCategory category = equipmentCategoryRepository.findOne(id);
    if (null == category) {
      return null;
	}

	EquipmentCategoryDetails details = equipmentCategoryDetailsRepository.findByCategory(category);
    if (null != details){
      return details.getUnit();
	}
    return null;
  }

  /**
   * 批量添加推荐分类
   * @param ids
   */
  @Transactional
  public void saveRecommendCategory(List<Long> ids){
	Assert.notNull(ids,"id cannot be null");

	//二、三级热门分类置为false
	List<EquipmentCategory> equipmentCategories =
		equipmentCategoryRepository.findByLevelAndHot(3,true);
	equipmentCategories.stream().forEach(p -> {
	  p.setHot(false);
	  p.getParent().setHot(false);
	  equipmentCategoryRepository.save(p);
	});


	for (Long id:ids){
	  EquipmentCategory equipmentCategory = equipmentCategoryRepository.findByIdAndLevel(id,3);
	  if (null == equipmentCategory || equipmentCategory.getHot()) continue;

	  equipmentCategory.setHot(true);
	  equipmentCategory.getParent().setHot(true);
	  equipmentCategoryRepository.save(equipmentCategory);
	}

	equipmentHotCategoryEsRepository.deleteAll();
	saveHotCategoryToES();

	redisService.deleteHomePage();
  }

}
