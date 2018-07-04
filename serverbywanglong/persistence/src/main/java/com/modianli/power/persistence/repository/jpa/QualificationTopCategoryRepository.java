package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.QualificationTopCategory;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dell on 2017/2/25.
 */
public interface QualificationTopCategoryRepository extends JpaBaseRepository<QualificationTopCategory,Long>,JpaSpecificationExecutor<QualificationTopCategory> {

  public QualificationTopCategory findByName(String name);


}
