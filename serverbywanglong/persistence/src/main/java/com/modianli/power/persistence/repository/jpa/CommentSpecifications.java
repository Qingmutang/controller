package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Comments;
import com.modianli.power.domain.jpa.Comments_;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.Enterprise_;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

/**
 * @author hantsy
 */
public class CommentSpecifications {

  public static Specification<Comments> searchComments(String enterpriseName, String active) {

	return (root, query, cb) -> {
	  List<Predicate> predicates = new ArrayList<>();

	  if (StringUtils.hasText(enterpriseName)) {

		Join<Comments, Enterprise> commentsEnterpriseJoin = root.join(Comments_.enterprise, JoinType.INNER);

		predicates.add(cb.like(commentsEnterpriseJoin.get(Enterprise_.name), "%" + enterpriseName + "%"));
	  }

	  if (StringUtils.hasText(active)) {
		predicates.add(cb.equal(root.get(Comments_.active), Boolean.valueOf(active)));
	  }

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

}
