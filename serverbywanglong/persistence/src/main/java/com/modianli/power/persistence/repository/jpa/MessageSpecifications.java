package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Messages;
import com.modianli.power.domain.jpa.Messages_;
import com.modianli.power.model.MessageSearchForm;
import com.modianli.power.model.enums.MessageType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

/**
 * Created by dell on 2017/4/14.
 */
public class MessageSpecifications {

  public static Specification<Messages> findByParams(final MessageSearchForm form){
    return (root, cq, cb) -> {
	  List<Predicate> predicates = new ArrayList<>();

	  if (StringUtils.isNotBlank(form.getContent())){
		Predicate predicate = cb.or(
			cb.like(root.get(Messages_.name), "%"+form.getContent()+"%"),
			cb.equal(root.get(Messages_.phone), form.getContent()));
		predicates.add(predicate);
	  }
	  if(StringUtils.isNoneBlank(form.getProvince())){
	    predicates.add(cb.equal(root.get(Messages_.province),form.getProvince()));
	  }
	  if(StringUtils.isNoneBlank(form.getCity())){
	    predicates.add(cb.equal(root.get(Messages_.city),form.getCity()));
	  }
	  if(StringUtils.isNoneBlank(form.getRequirementType())){
	    predicates.add(cb.equal(root.get(Messages_.requirementType),form.getRequirementType()));
	  }
	  if(StringUtils.isNoneBlank(form.getMessageType())){
	    predicates.add(cb.equal(root.get(Messages_.type), MessageType.valueOf(form.getMessageType())));
	  }
	  if (null != form.getMessageTime()){
	    predicates.add(cb.equal(root.get(Messages_.messageTime),form.getMessageTime()));
	  }


	  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	};
  }

}
