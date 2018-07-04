package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Cases;
import com.modianli.power.domain.jpa.Enterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dell on 2017/2/28.
 */
public interface CasesRepository extends JpaBaseRepository<Cases,Long>, JpaSpecificationExecutor<Cases>{

  @Query(value = "select c from Cases c "
                 + "WHERE c.active = TRUE "
                 + "AND c.enterprise = :enterprise "
                 + "ORDER BY c.projectTime asc")
  List<Cases> getCasesByEnterprise(@Param("enterprise")Enterprise enterprise);

//  @Query(value = "select distinct(DATE_FORMAT(c.projectTime, '%Y')) as ye from Cases c "
//                 + "WHERE c.active = TRUE "
//                 + "AND c.projectTime IS NOT NULL "
//                 + "AND c.enterprise = :enterprise "
//                 + "ORDER BY ye desc")
//  List<String> getEnterpriseCasesTime(@Param("enterprise") Enterprise enterprise);


}
