package com.modianli.power.common.service;

import com.modianli.power.DTOUtils;
import com.modianli.power.domain.jpa.Area;
import com.modianli.power.domain.jpa.City;
import com.modianli.power.domain.jpa.Province;
import com.modianli.power.model.AreaDetails;
import com.modianli.power.model.CityDetails;
import com.modianli.power.model.ProvinceDetails;
import com.modianli.power.persistence.repository.jpa.AreaRepository;
import com.modianli.power.persistence.repository.jpa.CityRepository;
import com.modianli.power.persistence.repository.jpa.ProvinceRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/2/28.
 */
@Service
@Slf4j
public class EnterpriseAddressService {

  @Inject
  private ProvinceRepository provinceRepository;
  @Inject
  private AreaRepository areaRepository;
  @Inject
  private CityRepository cityRepository;



  public List<ProvinceDetails> getProvince(){

    List<Province> provinces=provinceRepository.findAllByOrderByAreaCodeAsc();
    List<ProvinceDetails> provinceDetails=DTOUtils.mapList(provinces,ProvinceDetails.class);
    return provinceDetails;
  }

  public List<CityDetails> getCity(Long parentId){


    log.debug("input parentId {}", parentId);
    Province province=new Province();
    province.setId(parentId);

    List<City> cities=cityRepository.findByProvinceOrderByAreaCodeAsc(province);
    List<CityDetails> cityDetails=DTOUtils.mapList(cities,CityDetails.class);

    return cityDetails;

  }


  public List<AreaDetails> getArea(Long parentId) {

    log.debug("input parentId {}", parentId);
    City city=new City();
    city.setId(parentId);
    List<Area> areas=areaRepository.findByCityOrderByAreaCodeAsc(city);

    List<AreaDetails> areaDetails=DTOUtils.mapList(areas,AreaDetails.class);
    return areaDetails;

  }

}
