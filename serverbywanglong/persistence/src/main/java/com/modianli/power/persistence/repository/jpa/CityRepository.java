package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.City;
import com.modianli.power.domain.jpa.Province;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaBaseRepository<City, Long>, CityRepositoryCustom,JpaSpecificationExecutor<City>{

    public City findByName(String cityName);
    List<City> findByProvinceOrderByAreaCodeAsc(@Param("province")Province province);

    City findByAreaCode(String areaCode);

    
}
