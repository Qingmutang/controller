package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Area;
import com.modianli.power.domain.jpa.City;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dell on 2017/2/28.
 */
public interface AreaRepository extends JpaBaseRepository<Area,Long>,JpaSpecificationExecutor<Area>{

  List<Area> findByCityOrderByAreaCodeAsc(@Param("city")City city);

  Area findByAreaCode(String areaCode);


}
