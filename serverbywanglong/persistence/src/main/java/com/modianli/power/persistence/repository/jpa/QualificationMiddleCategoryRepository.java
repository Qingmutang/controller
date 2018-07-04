package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.QualificationMiddleCategory;
import com.modianli.power.domain.jpa.QualificationTopCategory;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dell on 2017/2/25.
 */
public interface QualificationMiddleCategoryRepository
	extends JpaBaseRepository<QualificationMiddleCategory, Long>, JpaSpecificationExecutor<QualificationMiddleCategory> {

  QualificationMiddleCategory findByName(String name);

  QualificationMiddleCategory findByNameAndQualificationTopCategory(@Param("name") String name,

      @Param("qualificationTopCategory") QualificationTopCategory qualificationTopCategory);

  List<QualificationMiddleCategory> findByQualificationTopCategory(
      @Param("qualificationTopCategory") QualificationTopCategory qualificationTopCategory);

}
