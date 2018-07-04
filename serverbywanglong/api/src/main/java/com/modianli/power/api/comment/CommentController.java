package com.modianli.power.api.comment;

import com.modianli.power.common.service.CommentService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.CommentDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-6-27.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_COMMENT)
public class CommentController {

  @Inject
  private CommentService commentService;

  @GetMapping(value = "")
  public ResponseEntity<Page<CommentDetails>> getAllComments(
	  @RequestParam(value = "active", required = false) String active,//
	  @RequestParam(value = "enterpriseName", required = false) String enterpriseName,//
	  @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {

	log.debug("get all orders  enterpriseName {} active {}", enterpriseName, active);

	Page<CommentDetails> result = commentService.findComments(enterpriseName, active, page);

	return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, params = {"action=ACTIVATE"})
  @ResponseBody
  public ResponseEntity<Void> activateComment(@PathVariable("id") Long id) {

	log.debug("activate comment @ {} ", id);

	commentService.activateComment(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, params = {"action=DEACTIVATE"})
  @ResponseBody
  public ResponseEntity<Void> deactivateComment(@PathVariable("id") Long id) {

	log.debug("activate comment @ {} ", id);

	commentService.deactivateComment(id);

	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
