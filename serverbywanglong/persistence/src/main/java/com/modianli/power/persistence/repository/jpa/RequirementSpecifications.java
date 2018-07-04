package com.modianli.power.persistence.repository.jpa;

import com.google.common.collect.Lists;

import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.Enterprise_;
import com.modianli.power.domain.jpa.RequirementBidding;
import com.modianli.power.domain.jpa.RequirementBidding_;
import com.modianli.power.domain.jpa.RequirementCategory;
import com.modianli.power.domain.jpa.RequirementCategory_;
import com.modianli.power.domain.jpa.Requirements;
import com.modianli.power.domain.jpa.Requirements_;
import com.modianli.power.domain.jpa.ServiceCategory_;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.domain.jpa.UserAccount_;
import com.modianli.power.domain.jpa.UserProfile;
import com.modianli.power.domain.jpa.UserProfile_;
import com.modianli.power.model.RequirementQueryForm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

/**
 * Created by haijun on 2017/3/9.
 */
public class RequirementSpecifications {

  /**
   * 需求搜索
   *
   * @param requirementUUID
   * @param status
   * @return
   */
  public static Specification<Requirements> searchRequirement(final String requirementUUID, final String status){
	return (root, criteriaQuery, criteriaBuilder) -> {
	  List<Predicate> predicates = Lists.newArrayList();
	  predicates.add( criteriaBuilder.equal( root.get(Requirements_.active), true ) );
	  //predicates.add( criteriaBuilder.notEqual( root.get(Requirements_.status).as(String.class), Requirements.Status.BIDDING.name()) );

	   if(!StringUtils.isBlank(requirementUUID)){
		predicates.add( criteriaBuilder.like(root.get(Requirements_.requirementUUID), "%" + StringUtils.trim(requirementUUID) + "%") );
	   }
	  if(!StringUtils.isBlank(status)){
		predicates.add( criteriaBuilder.equal( root.get(Requirements_.status).as(String.class), status ) );
	  }

	  return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

  /**
   * 竞标搜索
   *
   * @param requirementUUID
   * @param status
   * @param enterprise
   * @return
   */
  public static Specification<RequirementBidding> searchRequirementBidding(final String requirementUUID, final String status, Enterprise enterprise){
	return (root, query, cb) -> {
	  List<Predicate> predicates = Lists.newArrayList();
	  Predicate p1 = cb.equal(root.get(RequirementBidding_.enterprise).get(Enterprise_.id), enterprise.getId());
	  Predicate p2 = cb.equal(root.get(RequirementBidding_.active), true);
	  predicates.add(p1);
	  predicates.add(p2);

	  /*if( !StringUtils.isBlank(requirementUUID) ){
		//设置连接SQL语句
		Join<RequirementBidding, Requirements> requirementsJoin = root.join(RequirementBidding_.requirement, JoinType.INNER);
		Predicate p2 = cb.like(requirementsJoin.get(Requirements_.requirementUUID).as(String.class), "%" + requirementUUID + "%" );

		predicates.add(p2);
	  }

	  if( !StringUtils.isBlank(requirementUUID) ){
		Predicate p3 = null;
		if(status.equals(Requirements.Status.FINISHED)){//需求下架
		  Join<RequirementBidding, Requirements> requirementsJoin = root.join(RequirementBidding_.requirement, JoinType.INNER);
		  p3 = cb.equal( requirementsJoin.get(Requirements_.status).as(String.class), status );//下架
		}else{
		  p3 = cb.equal( root.get(RequirementBidding_.biddingStatus).as(String.class), status );
		}

		predicates.add(p3);
	  }*/

	  Predicate[] p = new Predicate[predicates.size()];
	  return cb.and(predicates.toArray(p));
	};
  }

  /**
   */
  public static Specification<Requirements> getActiveRequirement(final RequirementQueryForm form, List<Long> ids){
	return (root, criteriaQuery, criteriaBuilder) -> {
	  List<Predicate> predicates = Lists.newArrayList();
	  predicates.add( criteriaBuilder.equal( root.get(Requirements_.active), true ) );
	  predicates.add( criteriaBuilder.isNotNull( root.get(Requirements_.shelfDate) ) );//已上架
	  predicates.add( criteriaBuilder.notEqual( root.get(Requirements_.status).as(String.class), "FINISHED" ) );//未下架

	  if(form != null){
	    String categoryType = form.getCategoryType();
		if(StringUtils.isNotBlank(categoryType)){
		  List<Predicate> subQueryPredicates = Lists.newArrayList();
		  List<Predicate> orIds = Lists.newArrayList();
		  //predicates.add( criteriaBuilder.equal( root.get(Requirements_.categoryType).as(String.class), categoryType ) );

		  Subquery<RequirementCategory> subQuery = criteriaQuery.subquery(RequirementCategory.class);
		  Root<RequirementCategory> requirementCategoryRoot = subQuery.from(RequirementCategory.class);
		  subQuery.select(requirementCategoryRoot);

		  subQueryPredicates.add(criteriaBuilder.equal(requirementCategoryRoot.get(RequirementCategory_.requirementId), root.get(Requirements_.id)));
		  if("MATERIAL".equals(form.getCategoryType())){//设备和物料
		    ids.forEach(id -> orIds.add(
				criteriaBuilder.equal(requirementCategoryRoot.get(RequirementCategory_.serviceCategory).get(
					ServiceCategory_.id), id)
			));

			subQuery.where(
				criteriaBuilder.and(subQueryPredicates.toArray(new Predicate[]{})),
				criteriaBuilder.and(criteriaBuilder.or(orIds.toArray(new Predicate[]{})))
			);
		  }else{
			subQueryPredicates.add(criteriaBuilder.equal(requirementCategoryRoot.get(RequirementCategory_.serviceCategory).get(
				ServiceCategory_.id), form.getCategoryId()));
subQuery.where(subQueryPredicates.toArray(new Predicate[]{}));
		  }

		  predicates.add(
		  	criteriaBuilder.or(
		  		criteriaBuilder.equal(root.get(Requirements_.categoryType).as(String.class), categoryType),
				criteriaBuilder.exists(subQuery)
			)
		  );
		}

		String authType = form.getAuthType();
		if(StringUtils.isNotBlank(authType)){
		  Join<Requirements, UserProfile> requirementsJoin = root.join(Requirements_.userProfile, JoinType.INNER);//关联查询,(或者子查询)
		  predicates.add( criteriaBuilder.equal(requirementsJoin.get(UserProfile_.certificateStatus).as(String.class), authType ) );
		}
		String status = form.getStatus();
		if(StringUtils.isNotBlank(status)){
		  //竞标中的状态为：BIDDING, MATCHED
		  if(Requirements.Status.BIDDING.name().equals(status)){
			predicates.add(
				criteriaBuilder.or(
					criteriaBuilder.equal(root.get(Requirements_.status).as(String.class), Requirements.Status.BIDDING.name()),
					criteriaBuilder.equal(root.get(Requirements_.status).as(String.class), Requirements.Status.MATCHED.name())
				)
			);
		  }else{
			predicates.add(criteriaBuilder.equal(root.get(Requirements_.status).as(String.class), status));
		  }
		}
		String name = form.getName();
		if(StringUtils.isNotBlank(name)){
		  predicates.add( criteriaBuilder.like( root.get(Requirements_.name), "%" + StringUtils.trim(name) + "%") );
		}

		String provinceCode = form.getProvinceCode();
		if(StringUtils.isNotBlank(provinceCode)){
		  predicates.add( criteriaBuilder.equal( root.get(Requirements_.provinceCode), provinceCode) );
		}
		String areaCode = form.getAreaCode();
		if(StringUtils.isNotBlank(areaCode)){
		  predicates.add( criteriaBuilder.equal( root.get(Requirements_.areaCode), areaCode) );
		}
		String cityCode = form.getCityCode();
		if(StringUtils.isNotBlank(cityCode)){
		  predicates.add( criteriaBuilder.equal( root.get(Requirements_.cityCode), cityCode) );
		}
	  }

	  return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

  public static Specification<Requirements> getActiveRequirement(Long userId){
	return (root, criteriaQuery, criteriaBuilder) -> {
	  List<Predicate> predicates = Lists.newArrayList();
	  predicates.add( criteriaBuilder.equal( root.get(Requirements_.active), true ) );

	  Join<Requirements, UserProfile> requirementsJoin = root.join(Requirements_.userProfile, JoinType.INNER);//关联查询,(或者子查询)
	  Join<UserProfile, UserAccount> userJoin = requirementsJoin.join(UserProfile_.account, JoinType.INNER);
	  predicates.add( criteriaBuilder.equal(userJoin.get(UserAccount_.id), userId ) );

	  return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

  public static Specification<RequirementBidding> getRequirementBiddings(Long requirementId){
	return (root, criteriaQuery, criteriaBuilder) -> {
	  List<Predicate> predicates = Lists.newArrayList();
	  predicates.add( criteriaBuilder.equal( root.get(RequirementBidding_.active), true ) );
	  predicates.add( criteriaBuilder.equal( root.get(RequirementBidding_.requirement).get(Requirements_.id), requirementId ) );
	  predicates.add( criteriaBuilder.equal( root.get(RequirementBidding_.biddingStatus), RequirementBidding.BiddingStatus.RECEIVED ) );

	  return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

  public static Specification<RequirementBidding> getRequirementBiddings(Long requirementId, boolean notNeedBiddingStatus){
	return (root, criteriaQuery, criteriaBuilder) -> {
	  List<Predicate> predicates = Lists.newArrayList();
	  predicates.add( criteriaBuilder.equal( root.get(RequirementBidding_.requirement).get(Requirements_.id), requirementId ) );

	  return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }
}
