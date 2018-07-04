package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.UserProfile;
import com.modianli.power.domain.jpa.UserProfile_;
import com.modianli.power.model.UserEnterpriseCertificateForm;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

/**
 * Created by dell on 2017/2/23.
 */
public class UserProfileSpecifications {

  public static Specification<UserProfile> searchUserProfileSpecifications(final UserEnterpriseCertificateForm form) {

	return (root, query, cb) -> {

	  List<Predicate> predicates = new ArrayList<>();


	  if (StringUtils.hasText(form.getStatus())) {
		predicates.add(cb.equal(root.get(UserProfile_.certificateStatus),
								Enum.valueOf(UserProfile.CertificateType.class,form.getStatus())));
	  }

	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }


}
