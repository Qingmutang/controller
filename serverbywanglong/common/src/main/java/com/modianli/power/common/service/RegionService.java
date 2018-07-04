package com.modianli.power.common.service;

import com.google.common.collect.Maps;

import com.modianli.power.domain.jpa.City;
import com.modianli.power.domain.jpa.Province;
import com.modianli.power.persistence.repository.jpa.AreaRepository;
import com.modianli.power.persistence.repository.jpa.CityRepository;
import com.modianli.power.persistence.repository.jpa.ProvinceRepository;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by dell on 2017/5/15.
 */
@Service
public class RegionService {

  @Inject
  private ProvinceRepository provinceRepository;

  @Inject
  private CityRepository cityRepository;

  @Inject
  private AreaRepository areaRepository;

  @Inject
  private Configuration configuration;

  public static final String path = "D://";

  public void buildRegionJson(){
	buildProvinceJson();
	buildProvinceCityJson();
	buildCityAreaJson();
  }

  /**
   * 生成省json
   */
  private void buildProvinceJson(){
	Map<String,Object> map = Maps.newHashMap();
	List<Province> provinces = provinceRepository.findAll();
	map.put("provinces", provinces);
	createJson("province.ftl","province.json",map);
  }

  /**
   * 生成省市的json
   */
  private void buildProvinceCityJson(){
	final Map<String,Object> map = Maps.newHashMap();
	List<Province> provinces = provinceRepository.findAll();
	provinces.stream().forEach(province -> {
	  map.put(province.getAreaCode(),cityRepository.findByProvinceOrderByAreaCodeAsc(province));
	});
	Map<String,Object> m = Maps.newHashMap();
	m.put("provinceCities",map);
	createJson("province_city.ftl","province_city.json",m);
  }

  /**
   * 生成市区的json
   */
  private void buildCityAreaJson(){
	final Map<String,Object> map = Maps.newHashMap();
	List<City> cities = cityRepository.findAll();
	cities.stream().forEach(city -> {
	  map.put(city.getAreaCode(),areaRepository.findByCityOrderByAreaCodeAsc(city));
	});
	Map<String,Object> m = Maps.newHashMap();
	m.put("cityAreas",map);
	createJson("city_area.ftl","city_area.json",m);
  }

  /**
   * 创建json文件
   */
  private void createJson(String templateName, String fileName, Object model) {
	try {
	  Template template = configuration.getTemplate(templateName);
	  //生成省属性
	  FileWriter result = new FileWriter(new File(path + fileName));
	  template.process(model, result);
	} catch (IOException e) {
	  e.printStackTrace();
	} catch (TemplateException e) {
	  e.printStackTrace();
	}

  }

}
