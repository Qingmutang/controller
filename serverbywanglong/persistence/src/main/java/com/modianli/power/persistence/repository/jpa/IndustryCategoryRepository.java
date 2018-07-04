package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.IndustryCategory;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by dell on 2017/2/27.
 */
public interface IndustryCategoryRepository extends JpaBaseRepository<IndustryCategory,Long>,JpaSpecificationExecutor<IndustryCategory>{

  List<IndustryCategory> findAll();
  IndustryCategory findByName(String name);

}
