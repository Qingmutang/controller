package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.PrivateMessage;
import com.modianli.power.domain.jpa.PrivateMessage_;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.domain.jpa.UserAccount_;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author
 */
public class PrivateMessageSpecifications {

  public static Specification<PrivateMessage> filterReceivedMessages(final Long id, final Boolean read) {
	return (Root<PrivateMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
	  List<Predicate> predicates = new ArrayList<>();
	  if (read != null) {
		predicates.add(cb.equal(root.get(PrivateMessage_.read), read));
	  }

	  Join<PrivateMessage, UserAccount> to = root.join(PrivateMessage_.to);
	  predicates.add(cb.equal(to.get(UserAccount_.id), id));

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

  public static Specification<PrivateMessage> filterSentMessages(final Long id) {
	return (Root<PrivateMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
	  List<Predicate> predicates = new ArrayList<>();

	  Join<PrivateMessage, UserAccount> from = root.join(PrivateMessage_.from);
	  predicates.add(cb.equal(from.get(UserAccount_.id), id));

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

}
