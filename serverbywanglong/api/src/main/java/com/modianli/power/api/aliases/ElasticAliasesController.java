package com.modianli.power.api.aliases;

import com.modianli.power.common.service.ElasticAliasesService;
import com.modianli.power.model.ApiConstants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/3/20.
 */
@RestController
@RequestMapping(value = ApiConstants.URI_API_MGT + ApiConstants.URI_ALIASES)
@Slf4j
@Api(description = "elasticsearch管理")
public class ElasticAliasesController {

  @Inject
  private ElasticAliasesService elasticAliasesService;

  @RequestMapping(method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation(value = "添加索引和别名绑定")
  public ResponseEntity<Void> alias(String indexName,String aliasName){
	elasticAliasesService.saveAlias(indexName,aliasName);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(method = RequestMethod.PUT)
  @ResponseBody
  @ApiOperation(value = "修改别名")
  public ResponseEntity<Void> alias(String indexName,String aliasName,String targetAliasName){
	elasticAliasesService.updateAlias(indexName,aliasName,targetAliasName);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "indexes",method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation(value = "查看所有索引")
  public ResponseEntity<List<String>> indexes(){
	return new ResponseEntity<>(elasticAliasesService.getIndexes(),HttpStatus.OK);
  }

}
