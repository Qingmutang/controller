package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.Enterprise_;
import com.modianli.power.domain.jpa.RecommendEnterprise;
import com.modianli.power.domain.jpa.RecommendEnterprise_;
import com.modianli.power.model.RecommendEnterpriseForm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

/**
 * Created by dell on 2017/6/12.
 */
public class RecommendEnterpriseSpecifications {
  public static Specification<RecommendEnterprise> searchRecommendEnterprise(final RecommendEnterpriseForm form){
    return (root, criteriaQuery, cb) -> {
	  List<Predicate> predicates = new ArrayList<>();
      if (StringUtils.isNoneBlank(form.getType())){
		  predicates.add(cb.equal(root.get(RecommendEnterprise_.type.getName()),RecommendEnterprise.Type.valueOf(form.getType())));
	  }
	  if (form.getActive()!=null){
		predicates.add(cb.equal(root.get(RecommendEnterprise_.active),form.getActive()));
	  }
	  if (StringUtils.isNoneBlank(form.getName())){
		Join<RecommendEnterprise, Enterprise> join = root.join(RecommendEnterprise_.enterprise);
		predicates.add(cb.like(join.get(Enterprise_.name),"%"+ form.getName()+"%"));
	  }
      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }
}
