package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.DictionaryItem;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.Enterprise_;
import com.modianli.power.domain.jpa.Recruit;
import com.modianli.power.domain.jpa.Recruit_;
import com.modianli.power.model.RecruitCriteria;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

public class RecruitSpecifications {

  public static Specification<Recruit> searchRecruits(RecruitCriteria criteria) {

	return (root, query, cb) -> {
	  List<Predicate> predicates = new ArrayList<>();

	  if (StringUtils.hasText(criteria.getCityCode())) {
		predicates.add(cb.equal(root.get(Recruit_.cityCode), criteria.getCityCode()));
	  }
	  if (StringUtils.hasText(criteria.getPositionName())) {

		predicates.add(cb.like(root.get(Recruit_.positionName), "%" + criteria.getPositionName() + "%"));
	  }

	  if (criteria.getActive() != null) {
		predicates.add(cb.equal(root.get(Recruit_.active), criteria.getActive()));
	  }

	  if (criteria.getLowSalary() != null && criteria.getHighSalary() != null) {

		predicates.add(

			cb.or(
				cb.and(cb.greaterThanOrEqualTo(root.get(Recruit_.lowSalary), criteria.getLowSalary()),
					   cb.lessThanOrEqualTo(root.get(Recruit_.lowSalary), criteria.getHighSalary()))
				,

				cb.and(
					cb.greaterThanOrEqualTo(root.get(Recruit_.highSalary), criteria.getLowSalary()),
					cb.lessThanOrEqualTo(root.get(Recruit_.highSalary), criteria.getHighSalary())
				))
		);

	  }

	  if (criteria.getExperienceId() != null) {
		DictionaryItem item = new DictionaryItem();
		item.setId(criteria.getExperienceId());
		predicates.add(cb.equal(root.get(Recruit_.experience), item));
	  }

	  if (criteria.getCategoryId() != null) {
		DictionaryItem item = new DictionaryItem();
		item.setId(criteria.getCategoryId());
		predicates.add(cb.equal(root.get(Recruit_.category), item));
	  }

	  if (StringUtils.hasText(criteria.getEnterpriseUuid())) {

		List<Predicate> subQueryPredicates = new ArrayList<Predicate>();

		Subquery<Enterprise> subquery = query.subquery(Enterprise.class);

		Root<Enterprise> enterpriseRoot = subquery.from(Enterprise.class);

		subquery.select(enterpriseRoot);

		subQueryPredicates.add(cb.equal(enterpriseRoot.get(Enterprise_.uuid), criteria.getEnterpriseUuid()));

		subQueryPredicates.add(cb.equal(root.get(Recruit_.userAccount), enterpriseRoot.get(Enterprise_.userAccount)));

		subquery.where(subQueryPredicates.toArray(new Predicate[]{}));

		predicates.add(cb.exists(subquery));
	  }

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

}
