package com.modianli.power.common.service;

import com.modianli.power.DTOUtils;
import com.modianli.power.domain.jpa.Comments;
import com.modianli.power.model.CommentDetails;
import com.modianli.power.persistence.repository.jpa.CommentSpecifications;
import com.modianli.power.persistence.repository.jpa.CommentsRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-6-27.
 */
@Slf4j
@Service
@Transactional
public class CommentService {

  @Inject
  private CommentsRepository commentsRepository;

  public Page<CommentDetails> findComments(String enterpriseName, String active, Pageable page) {

	log.info("findRecruits  in  => ");
	log.debug("findRecruits enterpriseName {} ,active {} ,page {} ", enterpriseName, active, page);

	Page<Comments> users
		= commentsRepository.findAll(
		CommentSpecifications.searchComments(enterpriseName, active), page);

	log.debug("total elements@", users.getTotalElements());

	log.info("findRecruits  out  <= ");
	return DTOUtils.mapPage(users, CommentDetails.class);
  }

  public void deactivateComment(Long id) {
	Assert.notNull(id, "comment id can not be null");
	commentsRepository.updateActiveStatus(id, false);
  }

  public void activateComment(Long id) {
	Assert.notNull(id, "comment id can not be null");
	commentsRepository.updateActiveStatus(id, true);
  }

}
