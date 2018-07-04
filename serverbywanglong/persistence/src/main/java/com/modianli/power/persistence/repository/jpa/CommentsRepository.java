package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Comments;
import com.modianli.power.domain.jpa.Enterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dell on 2017/2/24.
 */
public interface CommentsRepository extends JpaBaseRepository<Comments, Long>, JpaSpecificationExecutor<Comments> {

  List<Comments> findTop10ByEnterpriseAndActiveOrderByIdDesc(@Param("enterprise") Enterprise enterprise,
															 @Param("active") Boolean active);

  @Query("update Comments as c set c.active=:active where c.id=:id ")
  @Modifying
  void updateActiveStatus(@Param("id") Long id, @Param("active") Boolean active);

}
