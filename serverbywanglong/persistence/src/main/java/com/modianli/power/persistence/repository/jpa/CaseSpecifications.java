package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Cases;
import com.modianli.power.domain.jpa.Cases_;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.Enterprise_;
import com.modianli.power.model.CaseSearchForm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

/**
 * Created by dell on 2017/5/16.
 */
public class CaseSpecifications {

  public static Specification<Cases> searchCase(final CaseSearchForm searchForm){
    return  (root, query, cb) -> {
	  List<Predicate> predicates = new ArrayList<>();

	  Join<Cases, Enterprise> enterpriseJoin = root.join(Cases_.enterprise);

	  if(searchForm.getEnterpriseId()!=null){
		predicates.add(cb.equal(enterpriseJoin.get(Enterprise_.id), searchForm.getEnterpriseId()));
	  }

	  if (StringUtils.isNotBlank(searchForm.getProjectName())){
		predicates.add(cb.like(root.get(Cases_.projectName) ,"%" +searchForm.getProjectName()+ "%" ));
	  }

	  if (null != searchForm.getActive()){
		predicates.add(cb.equal(root.get(Cases_.active) ,searchForm.getActive()));
	  }else {
		predicates.add(cb.equal(root.get(Cases_.active) ,true));
	  }

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

}
