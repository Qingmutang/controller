package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.QualificationLastCategory;
import com.modianli.power.domain.jpa.QualificationMiddleCategory;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dell on 2017/2/24.
 */
public interface QualificationLastCategoryRepository extends JpaBaseRepository<QualificationLastCategory, Long>,//
														 JpaSpecificationExecutor<QualificationLastCategory> {

  //  List<QualificationLastCategory> findByEnterpriseBaseInfo(@Param("enterpriseBaseInfo") EnterpriseBaseInfo enterpriseBaseInfo);
  QualificationLastCategory findByName(String name);

  QualificationLastCategory findByNameAndQualificationMiddleCategory(@Param("name") String name,
																	 @Param("qualificationMiddleCategory") QualificationMiddleCategory qualificationMiddleCategory);
  List<QualificationLastCategory> findByQualificationMiddleCategory(@Param("qualificationMiddleCategory") QualificationMiddleCategory qualificationMiddleCategory);

}
