package com.modianli.power.persistence.repository.jpa;

import com.google.common.collect.Lists;

import com.modianli.power.domain.jpa.DictionaryItem_;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.EnterpriseCategory;
import com.modianli.power.domain.jpa.EnterpriseCategory_;
import com.modianli.power.domain.jpa.EnterpriseProduceCategory;
import com.modianli.power.domain.jpa.EnterpriseProduceCategory_;
import com.modianli.power.domain.jpa.Enterprise_;
import com.modianli.power.domain.jpa.IndustryCategory_;
import com.modianli.power.model.EnterpriseCriteria;
import com.modianli.power.model.EnterpriseCriteriaMultiCategories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

/**
 * Created by dell on 2017/2/23.
 */
public class EnterpriseSpecifications {

  public static Specification<Enterprise> searchEnterprises(final EnterpriseCriteria criteria) {

	return (root, query, cb) -> {

	  List<Predicate> predicates = new ArrayList<>();

	  if (criteria.getActive() != null) {
		predicates.add(cb.equal(root.get(Enterprise_.active), criteria.getActive()));
	  }

	  if (StringUtils.hasText(criteria.getName())) {
		predicates.add(cb.like(root.get(Enterprise_.name), "%" + criteria.getName() + "%"));
	  }
	  if (StringUtils.hasText(criteria.getProvince())) {
		predicates.add(cb.equal(root.get(Enterprise_.province), criteria.getProvince()));
	  }
	  if (StringUtils.hasText(criteria.getProvinceCode())) {
		predicates.add(cb.equal(root.get(Enterprise_.provinceCode), criteria.getProvinceCode()));
	  }
	  if (StringUtils.hasText(criteria.getCity())) {
		predicates.add(cb.equal(root.get(Enterprise_.city), criteria.getCity()));
	  }
	  if (StringUtils.hasText(criteria.getCityCode())) {
		predicates.add(cb.equal(root.get(Enterprise_.cityCode), criteria.getCityCode()));
	  }
	  if (StringUtils.hasText(criteria.getVerifyStatus())) {

		if ("APPROVED".equals(criteria.getVerifyStatus())) {
		  predicates.add(cb.notEqual(root.get(Enterprise_.certificateType), "NONE"));
		}

		if ("UN_APPROVED".equals(criteria.getVerifyStatus())) {
		  predicates.add(cb.equal(root.get(Enterprise_.certificateType), "NONE"));
		}

	  }
	  if (criteria.getCategory() != null) {
		List<Predicate> subQueryPredicates = new ArrayList<Predicate>();
		Subquery<EnterpriseCategory> subquery = query.subquery(EnterpriseCategory.class);
		Root<EnterpriseCategory> enterpriseCategoryRoot = subquery.from(EnterpriseCategory.class);
		subquery.select(enterpriseCategoryRoot);
		subQueryPredicates.add(cb.equal(root.get(Enterprise_.id),
										enterpriseCategoryRoot.get(EnterpriseCategory_.enterprise)));
		subQueryPredicates.add(
			cb.equal(enterpriseCategoryRoot.get(EnterpriseCategory_.industryCategory), criteria.getCategory()));
		subquery.where(subQueryPredicates.toArray(new Predicate[]{}));
		predicates.add(cb.exists(subquery));
	  }

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

  public static Specification<Enterprise> searchEnterprises(final EnterpriseCriteriaMultiCategories criteria) {

	return (root, query, cb) -> {

	  List<Predicate> predicates = new ArrayList<>();
	  predicates.add(cb.equal(root.get(Enterprise_.active), Boolean.TRUE));

	  if (StringUtils.hasText(criteria.getName())) {
		predicates.add(cb.like(root.get(Enterprise_.name), "%" + criteria.getName() + "%"));
	  }
	  if (StringUtils.hasText(criteria.getProvince())) {
		predicates.add(cb.equal(root.get(Enterprise_.province), criteria.getProvince()));
	  }
	  if (StringUtils.hasText(criteria.getProvinceCode())) {
		predicates.add(cb.equal(root.get(Enterprise_.provinceCode), criteria.getProvinceCode()));
	  }
	  if (StringUtils.hasText(criteria.getCity())) {
		predicates.add(cb.equal(root.get(Enterprise_.city), criteria.getCity()));
	  }
	  if (StringUtils.hasText(criteria.getCityCode())) {
		predicates.add(cb.equal(root.get(Enterprise_.cityCode), criteria.getCityCode()));
	  }
	  if (StringUtils.hasText(criteria.getVerifyStatus())) {

		if ("APPROVED".equals(criteria.getVerifyStatus())) {

		  predicates.add(cb.notEqual(root.get(Enterprise_.certificateType), "NONE"));
		}

		if ("UN_APPROVED".equals(criteria.getVerifyStatus())) {
		  predicates.add(cb.equal(root.get(Enterprise_.certificateType), "NONE"));
		}

	  }
	  if (criteria.getCategory() != null && criteria.getCategory().size() > 0) {
		List<Predicate> subQueryPredicates = new ArrayList<Predicate>();
		Subquery<EnterpriseCategory> subquery = query.subquery(EnterpriseCategory.class);
		Root<EnterpriseCategory> enterpriseCategoryRoot = subquery.from(EnterpriseCategory.class);
		subquery.select(enterpriseCategoryRoot);
		subQueryPredicates.add(cb.equal(root.get(Enterprise_.id),
										enterpriseCategoryRoot.get(EnterpriseCategory_.enterprise)));
		/*subQueryPredicates.add(
			cb.equal(enterpriseCategoryRoot.get(EnterpriseCategory_.industryCategory), criteria.getCategory()));*/

		List<Predicate> orPredicts = Lists.newArrayList();
		for (Long industryCategoryId : criteria.getCategory()) {
		  orPredicts.add(cb.equal(enterpriseCategoryRoot.get(EnterpriseCategory_.industryCategory).get(IndustryCategory_.id),
								  industryCategoryId));
		}

		subQueryPredicates.toArray(new Predicate[]{});
		subquery.where(
			cb.and(
				subQueryPredicates.toArray(new Predicate[]{})
			),
			cb.and(
				cb.or(orPredicts.toArray(new Predicate[]{}))
			)
		);
		predicates.add(cb.exists(subquery));
	  }

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

  /**
   * 采购匹配供应商
   * flag: purchase
   */
  public static Specification<Enterprise> searchEnterprises(final EnterpriseCriteriaMultiCategories criteria, String flag) {

	return (root, query, cb) -> {
	  List<Predicate> predicates = new ArrayList<>();
	  predicates.add(cb.equal(root.get(Enterprise_.active), Boolean.TRUE));

	  if (criteria.getCategory() != null && criteria.getCategory().size() > 0) {
		List<Predicate> subQueryPredicates = new ArrayList<>();

		Subquery<EnterpriseProduceCategory> subquery = query.subquery(EnterpriseProduceCategory.class);//
		Root<EnterpriseProduceCategory> enterpriseCategoryRoot = subquery.from(EnterpriseProduceCategory.class);//
		subquery.select(enterpriseCategoryRoot);

		subQueryPredicates.add(cb.equal(root.get(Enterprise_.id),
										enterpriseCategoryRoot.get(EnterpriseProduceCategory_.enterprise).get(Enterprise_.id)));//

		List<Predicate> orPredicts = Lists.newArrayList();
		criteria.getCategory().forEach(industryCategoryId ->{
		  orPredicts.add(cb.equal(enterpriseCategoryRoot.get(EnterpriseProduceCategory_.dictionaryItem).get(DictionaryItem_.id),//
								  industryCategoryId));
		});

		subQueryPredicates.toArray(new Predicate[]{});
		subquery.where(
			cb.and(
				subQueryPredicates.toArray(new Predicate[]{})
			),
			cb.and(
				cb.or(orPredicts.toArray(new Predicate[]{}))
			)
		);
		predicates.add(cb.exists(subquery));
	  }

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

//  public static Specification<Enterprise> searchBackEndEnterprises(final EnterpriseCriteria criteria) {
//
//	return (root, query, cb) -> {
//
//	  List<Predicate> predicates = new ArrayList<>();
//
//	  if (!StringUtils.isEmpty(criteria.getActive())) {
//		predicates.add(cb.equal(root.get(Enterprise_.active), criteria.getActive()));
//	  }
//
//	  if (StringUtils.hasText(criteria.getName())) {
//		predicates.add(cb.like(root.get(Enterprise_.name), "%" + criteria.getName() + "%"));
//	  }
//
//	  if (StringUtils.hasText(criteria.getProvince())) {
//		predicates.add(cb.equal(root.get(Enterprise_.province), criteria.getProvince()));
//	  }
//	  if (StringUtils.hasText(criteria.getProvinceCode())) {
//		predicates.add(cb.equal(root.get(Enterprise_.provinceCode), criteria.getProvinceCode()));
//	  }
//	  if (StringUtils.hasText(criteria.getCity())) {
//		predicates.add(cb.equal(root.get(Enterprise_.city), criteria.getCity()));
//	  }
//	  if (StringUtils.hasText(criteria.getCityCode())) {
//		predicates.add(cb.equal(root.get(Enterprise_.cityCode), criteria.getCityCode()));
//	  }
//	  if (StringUtils.hasText(criteria.getVerifyStatus())) {
//
//		if ("APPROVED".equals(criteria.getVerifyStatus())) {
//
//		  predicates.add(cb.isNotNull(root.get(Enterprise_.certificateType)));
//		}
//
//		if ("UN_APPROVED".equals(criteria.getVerifyStatus())) {
//		  predicates.add(cb.isNull(root.get(Enterprise_.certificateType)));
//		}
//
//
//	  }
//	  if (criteria.getCategory() != null) {
//		List<Predicate> subQueryPredicates = new ArrayList<Predicate>();
//		Subquery<EnterpriseCategory> subquery = query.subquery(EnterpriseCategory.class);
//		Root<EnterpriseCategory> enterpriseCategoryRoot = subquery.from(EnterpriseCategory.class);
//		subquery.select(enterpriseCategoryRoot);
//		subQueryPredicates.add(cb.equal(root.get(Enterprise_.id),
//										enterpriseCategoryRoot.get(EnterpriseCategory_.enterprise)));
//		subQueryPredicates.add(
//			cb.equal(enterpriseCategoryRoot.get(EnterpriseCategory_.industryCategory), criteria.getCategory()));
//		subquery.where(subQueryPredicates.toArray(new Predicate[]{}));
//		predicates.add(cb.exists(subquery));
//	  }
//
//	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
//	};
//  }


}
