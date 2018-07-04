package com.modianli.power.common.service;

import com.modianli.power.DTOUtils;
import com.modianli.power.common.exception.QualificationMiddleCategoryExistedException;
import com.modianli.power.common.exception.QualificationTopCategoryExistedException;
import com.modianli.power.domain.jpa.Area;
import com.modianli.power.domain.jpa.City;
import com.modianli.power.domain.jpa.Province;
import com.modianli.power.domain.jpa.QualificationLastCategory;
import com.modianli.power.domain.jpa.QualificationMiddleCategory;
import com.modianli.power.domain.jpa.QualificationTopCategory;
import com.modianli.power.model.EnterpriseQualificationMiddleDetails;
import com.modianli.power.model.QualificationLastDetails;
import com.modianli.power.model.QualificationMiddleDetails;
import com.modianli.power.model.QualificationMiddleForm;
import com.modianli.power.model.QualificationTopDetails;
import com.modianli.power.model.QualificationTopForm;
import com.modianli.power.persistence.repository.jpa.AreaRepository;
import com.modianli.power.persistence.repository.jpa.CityRepository;
import com.modianli.power.persistence.repository.jpa.ProvinceRepository;
import com.modianli.power.persistence.repository.jpa.QualificationLastCategoryRepository;
import com.modianli.power.persistence.repository.jpa.QualificationMiddleCategoryRepository;
import com.modianli.power.persistence.repository.jpa.QualificationTopCategoryRepository;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/2/25.
 */
@Service
@Slf4j
public class EnterpriseQualificationService {

  @Inject
  private QualificationTopCategoryRepository qualificationTopCategoryRepository;
  @Inject
  private QualificationMiddleCategoryRepository qualificationMiddleCategoryRepository;

  @Inject
  private QualificationLastCategoryRepository qualificationCategoryRepository;

  @Inject
  private ResourceLoader resourceLoader;

  @Inject
  private ProvinceRepository provinceRepository;

  @Inject
  private CityRepository cityRepository;

  @Inject
  private AreaRepository areaRepository;

  public List<QualificationTopDetails> getQualificationTopCategory() {

	List<QualificationTopCategory> qualificationTopCategories = qualificationTopCategoryRepository.findAll();
	return DTOUtils.mapList(qualificationTopCategories, QualificationTopDetails.class);
  }

  public List<EnterpriseQualificationMiddleDetails> getQualificationMiddleCategory(Long parentId) {

	log.debug("input QualificationTopForm {}", parentId);

	QualificationTopCategory qualificationTopCategory = new QualificationTopCategory();
	qualificationTopCategory.setId(parentId);

	List<QualificationMiddleCategory> qualificationMiddleCategories = qualificationMiddleCategoryRepository
		.findByQualificationTopCategory(qualificationTopCategory);
	return DTOUtils.mapList(qualificationMiddleCategories, EnterpriseQualificationMiddleDetails.class);
  }

  public List<QualificationLastDetails> getQualificationLastCategory(Long parentId) {
	log.debug("input QualificationMiddleForm {}", parentId);
	QualificationMiddleCategory qualificationMiddleCategory = new QualificationMiddleCategory();
	qualificationMiddleCategory.setId(parentId);
	List<QualificationLastCategory> qualificationLastCategories = qualificationCategoryRepository
		.findByQualificationMiddleCategory(qualificationMiddleCategory);
	return DTOUtils.mapList(qualificationLastCategories, QualificationLastDetails.class);
  }

  @Transactional
  public QualificationTopDetails saveTopCategory(QualificationTopForm form) {

	log.debug("saving enterprise@ {} ", form);

	if (qualificationTopCategoryRepository.findByName(form.getName()) != null) {
	  throw new QualificationTopCategoryExistedException(form.getName());
	}

	QualificationTopCategory qualificationTopCategory = DTOUtils.map(form, QualificationTopCategory.class);
	QualificationTopCategory saved = qualificationTopCategoryRepository.save(qualificationTopCategory);

	if (log.isDebugEnabled()) {
	  log.debug("saved qualificationTopCategory {}", saved);
	}

	return DTOUtils.map(saved, QualificationTopDetails.class);

  }

  @Transactional
  public QualificationMiddleDetails saveMiddleCategory(QualificationMiddleForm form) {

	if (log.isDebugEnabled()) {
	  log.debug("saving qualificationMiddleCategory@ {} ", form);
	}
	if (qualificationMiddleCategoryRepository.findByName(form.getName()) != null) {
	  throw new QualificationMiddleCategoryExistedException(form.getName());
	}

	QualificationMiddleCategory qualificationMiddleCategory = new QualificationMiddleCategory();
//    qualificationMiddleCategoryRepository.save(qualificationMiddleCategory);

	return null;
  }

  @Transactional
  @Deprecated
  public void importCreditType() {
	try {
	  Resource resource = resourceLoader.getResource("classpath:credit-type-dic.txt");
	  List<String> lines = IOUtils.readLines(resource.getInputStream(), "UTF-8");
	  lines.stream().forEach(l -> {
		String[] str = l.split("\t");
		String top = str[0];
		String middle = str[1];
		String last = str[2];
		QualificationTopCategory topCategory = qualificationTopCategoryRepository.findByName(top);
		if (topCategory == null) {
		  topCategory = new QualificationTopCategory(top);
		  topCategory = qualificationTopCategoryRepository.save(topCategory);
		}
		QualificationMiddleCategory
			middleCategory =
			qualificationMiddleCategoryRepository.findByNameAndQualificationTopCategory(middle, topCategory);
		if (middleCategory == null) {
		  middleCategory = new QualificationMiddleCategory(middle, topCategory);
		  middleCategory = qualificationMiddleCategoryRepository.save(middleCategory);
		}
		QualificationLastCategory
			lastCategory =
			qualificationCategoryRepository.findByNameAndQualificationMiddleCategory(last, middleCategory);
		if (lastCategory == null) {
		  lastCategory = new QualificationLastCategory(last, middleCategory);
		  lastCategory = qualificationCategoryRepository.save(lastCategory);
		}

	  });
	} catch (Exception e) {
	  e.printStackTrace();

	}
  }

  @Transactional
  @Deprecated
  public void importArea() {
	try {
	  Resource resource = resourceLoader.getResource("classpath:data.txt");
	  List<String> lines = IOUtils.readLines(resource.getInputStream(), "UTF-8");
	  lines.stream().forEach(l -> {
		String[] str = l.split(";");
		String pCode = str[0];
		String pName = str[1];
		String cCode = str[2];
		String cName = str[3];
		String aCode = str[4];
		String aName = str[5];

		Province province = provinceRepository.findByAreaCode(pCode);
		if(province==null){
		  province = new Province(pName,pCode);
		  province = provinceRepository.save(province);
		}
		City city = cityRepository.findByName(cName);
		if(city==null){
		  city = new City(cName, cCode, province);
		  city = cityRepository.save(city);
		}
		Area area = areaRepository.findByAreaCode(aCode);
		if(area==null){
		  area = new Area(aName, aCode, city);
		  area = areaRepository.save(area);
		}

	  });
	} catch (Exception e) {
	  e.printStackTrace();

	}
  }

}
