package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Province;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProvinceRepository extends JpaBaseRepository<Province, Long>, JpaSpecificationExecutor<Province> {

  Province findByName(String name);

  Province findByAreaCode(String areaCode);

  List<Province> findAllByOrderByAreaCodeAsc();


}

