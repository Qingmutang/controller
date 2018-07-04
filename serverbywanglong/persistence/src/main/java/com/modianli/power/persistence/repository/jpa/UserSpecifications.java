package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Role;
import com.modianli.power.domain.jpa.Role_;
import com.modianli.power.domain.jpa.StaffProfile;
import com.modianli.power.domain.jpa.StaffProfile_;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.domain.jpa.UserAccount_;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

public class UserSpecifications {

  public static Specification<StaffProfile> filterUserAccountsByKeywordAndRole(
	  final String keyword,//
	  final String role, //
	  final String activeStatus,//
	  final String locked//
  ) {

	return (Root<StaffProfile> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

	  Join<StaffProfile, UserAccount> userJoin = root.join(StaffProfile_.userAccount);
	  List<Predicate> predicates = new ArrayList<>();

	  if (StringUtils.hasText(keyword)) {
		predicates.add(
			cb.or(
				cb.like(userJoin.get(UserAccount_.name), "%" + keyword + "%"),
				cb.like(userJoin.get(UserAccount_.username), "%" + keyword + "%")
			));
	  }

	  List<Predicate> subQueryPredicates = new ArrayList<Predicate>();
	  Subquery<UserAccount> subquery = query.subquery(UserAccount.class);

	  Root<UserAccount> userAccountRoot = subquery.from(UserAccount.class);

	  ListJoin<UserAccount, String> roleJoin = userAccountRoot.join(UserAccount_.roles);

	  subquery.select(userAccountRoot);

	  subQueryPredicates.add(cb.and(
		  cb.notEqual(roleJoin, "USER"),
		  cb.notEqual(roleJoin, "ENTERPRISE")
	  ));

	  if (StringUtils.hasText(role) && !"ALL".equals(role)) {
		subQueryPredicates.add(cb.equal(roleJoin, role));
	  }

	  subQueryPredicates.add(cb.equal(userJoin.get(UserAccount_.id), userAccountRoot.get(UserAccount_.id)));

	  subquery.where(subQueryPredicates.toArray(new Predicate[]{}));
	  predicates.add(cb.exists(subquery));

	  if (StringUtils.hasText(locked)) {
		predicates.add(cb.equal(userJoin.get(UserAccount_.locked), Boolean.valueOf(locked)));
	  }

	  if (StringUtils.hasText(activeStatus)) {
		predicates.add(cb.equal(userJoin.get(UserAccount_.active), Boolean.valueOf(activeStatus)));
	  }

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

  public static Specification<UserAccount> filterUserAccountsByKeyword(
	  final String keyword,//
	  final UserAccount.Type type,//
	  final String role, //
	  final String activeStatus,//
	  final String locked
  ) {

	return (Root<UserAccount> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

	  List<Predicate> predicates = new ArrayList<>();

	  if (StringUtils.hasText(keyword)) {
		predicates.add(
			cb.or(
				cb.like(root.get(UserAccount_.name), "%" + keyword + "%"),
				cb.like(root.get(UserAccount_.username), "%" + keyword + "%")
			));
	  }

	  if (type != null) {
		predicates.add(cb.equal(root.get(UserAccount_.type), type));
	  }

	  if (StringUtils.hasText(role) && !"ALL".equals(role)) {

		ListJoin<UserAccount, String> roleJoin = root.join(UserAccount_.roles);
		predicates.add(cb.equal(roleJoin, role));

	  }

	  if (StringUtils.hasText(locked)) {
		predicates.add(cb.equal(root.get(UserAccount_.locked), Boolean.valueOf(locked)));
	  }

	  if (StringUtils.hasText(activeStatus)) {
		predicates.add(cb.equal(root.get(UserAccount_.active), Boolean.valueOf(activeStatus)));
	  }

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

  public static Specification<UserAccount> filterUserAccountsByDatetimeRange(
	  final LocalDateTime start, //
	  final LocalDateTime end //
  ) {

	return (Root<UserAccount> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

	  List<Predicate> predicates = new ArrayList<>();

	  predicates.add(cb.equal(root.get(UserAccount_.type), UserAccount.Type.USER));

	  if (start != null) {
		predicates.add(cb.greaterThanOrEqualTo(root.get(UserAccount_.createdDate), start));
	  }

	  if (end != null) {
		predicates.add(cb.lessThan(root.get(UserAccount_.createdDate), end));
	  }

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

  public static Specification<Role> filterRoleByRoleName(final String name, final boolean active) {

	return (root, query, cb) -> {
	  List<Predicate> predicates = new ArrayList<>();
	  if (StringUtils.hasText(name)) {
		predicates.add(cb.like(root.get(Role_.name), "%" + name + "%"));
	  }

	  predicates.add(cb.equal(root.get(Role_.active), active));
	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }
}
