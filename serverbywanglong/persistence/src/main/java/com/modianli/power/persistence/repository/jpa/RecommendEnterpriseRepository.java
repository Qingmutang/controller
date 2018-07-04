package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.RecommendEnterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dell on 2017/5/17.
 */
public interface RecommendEnterpriseRepository extends JpaBaseRepository<RecommendEnterprise,Long>, JpaSpecificationExecutor<RecommendEnterprise>{

  @Query("select max(e.sort) from RecommendEnterprise e where e.type=:type")
  Integer getMaxSortByType(@Param("type") RecommendEnterprise.Type type);



  RecommendEnterprise findByEnterprise(Enterprise enterprise);

  @Query(value = "select e from RecommendEnterprise e where e.enterprise.name like %:name% and e.active =:active")
  List<RecommendEnterprise> getByEnterpriseName(@Param("name")String name,@Param("active")Boolean active);

  @Query(value = "select e from RecommendEnterprise e where e.enterprise.name like %:name% and e.active =:active and e.type=:type")
  List<RecommendEnterprise> getByEnterpriseNameAndTypeAndActive(@Param("name")String name,@Param("active")Boolean active,@Param("type")RecommendEnterprise.Type type);

  @Query(value = "select e from RecommendEnterprise e where e.enterprise.name like %:name%  and e.type=:type")
  List<RecommendEnterprise> getByEnterpriseNameAndType(@Param("name")String name,@Param("type")RecommendEnterprise.Type type);

  List<RecommendEnterprise> findByActiveAndType(Boolean active,RecommendEnterprise.Type type);

  List<RecommendEnterprise> findByType(RecommendEnterprise.Type type);

  List<RecommendEnterprise> findByActive(Boolean active);

  List<RecommendEnterprise> findTop12ByActiveAndTypeOrderBySortDesc(Boolean active, RecommendEnterprise.Type type);
  List<RecommendEnterprise> findTop18ByActiveAndTypeOrderBySortDesc(Boolean active, RecommendEnterprise.Type type);

  @Query("update RecommendEnterprise as re set re.sort=:sort where re.id=:id and re.type=:type")
  @Modifying
  void updateRecommendEnterpriseSort(@Param("sort") Integer sort, @Param("id") Long id,@Param("type")RecommendEnterprise.Type type);
}
