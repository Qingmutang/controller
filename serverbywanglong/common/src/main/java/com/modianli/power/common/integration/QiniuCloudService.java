package com.modianli.power.common.integration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.modianli.power.MessagingConstants;
import com.modianli.power.domain.jpa.Resource;
import com.modianli.power.persistence.repository.jpa.ResourceRepository;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by gao on 17-2-22.
 */
@Service
@Slf4j

public class QiniuCloudService {

  @Value("${spring.qiniu.accessKey}")
  private String accessKey = "";

  @Value("${spring.qiniu.secretKey}")
  private String secretKey = "";

  @Value("${spring.qiniu.bucket}")
  private String bucket = "";

  @Value("${spring.qiniu.callbackUrl}")
  private String callbackUrl = "";

  @Value("${spring.qiniu.resourceUrl}")
  private String resourceUrl = "";

  private Auth auth = null;

  @Inject
  private ResourceRepository resourceRepository;

  @Inject
  private RabbitTemplate rabbitTemplate;

  @PostConstruct
  public void init() {
	log.debug("init create auth accessKey {} secretKey {} ", accessKey, secretKey);
	auth = Auth.create(accessKey, secretKey);
  }

  public String getUploadToken() {
	return auth.uploadToken(bucket);
  }

  public String getUploadToken(String bucket) {
	return auth.uploadToken(bucket);
  }

  public String getUploadToken(String bucket, String key) {
	return auth.uploadToken(bucket, key);
  }

  public String getCallbackUploadToken() {
	StringMap putPolicy = new StringMap();
	putPolicy.put("callbackUrl", callbackUrl);
//	putPolicy.put("callbackBody", "{\"key\":\"$(key)\"}");
	putPolicy.put("callbackBody", "key=$(key)");
//	putPolicy.put("callbackBodyType", "application/json");
	long expireSeconds = 3600;
	String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
	return upToken;
  }

  public boolean isValidCallback(String callbackAuthHeader, String callbackUrl, byte[] callbackBody, String callbackBodyType) {
	boolean validCallback = auth.isValidCallback(callbackAuthHeader, callbackUrl, callbackBody, callbackBodyType);
	return validCallback;
  }

  @Transactional
  public void saveResource(Resource resource){
	resourceRepository.save(resource);
  }

  public void deleteInvalidPicture(String[] urls){
	for (int i=0;i<urls.length;i++) {
	  urls[i] = urls[i].substring(resourceUrl.length());
	}

	rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_DIRECT_PICTURE,MessagingConstants.ROUTING_PICTURE_BATCH,urls);
  }

  @Transactional
  public void batchDeleteResource(String[] keyList){
	List<Resource> list = Lists.newArrayList();

    for (String key:keyList){
	  Resource resource = resourceRepository.findByUrl(key);
	  if (null == resource || !resource.getActive()){
		continue;
	  }

	  resource.setActive(false);
	  list.add(resource);
	}

	resourceRepository.batchUpdateResource(list);
  }

  public List<Resource> findCanDeleteResources(){
	return resourceRepository.findByActiveAndDeleteStatus(Boolean.FALSE, Boolean.FALSE);
  }

  @Transactional
  public void batchDeleteFile(List<Resource> resources){
    List<Resource> list = Lists.newArrayList();
	String[] keyList = new String[resources.size()];
	Map<String,Resource> map = Maps.newHashMap();
	for (int i=0;i<resources.size();i++){
	  Resource resource = resources.get(i);
	  keyList[i] = resource.getUrl();
	  map.put(resource.getUrl(),resource);
	}

	Configuration cfg = new Configuration(Zone.zone0());
	BucketManager bucketManager = new BucketManager(auth, cfg);
	try {
	  //单次批量请求的文件数量不得超过1000
	  BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
	  batchOperations.addDeleteOp(bucket, keyList);
	  Response response = bucketManager.batch(batchOperations);
	  BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);

	  for (int i = 0; i < keyList.length; i++) {
		BatchStatus status = batchStatusList[i];
		String key = keyList[i];
		log.debug(key + "\t");
		if (status.code == 200) {
		  log.debug("delete success");
		  Resource resource = map.get(key);
		  if (null != resource){
		    resource.setDeleteStatus(true);
			list.add(resource);
		  }
		} else {
		  log.error("qiniu delete error {}",status.data.error);
		}
	  }
	  resourceRepository.batchUpdateResource(list);
	} catch (QiniuException ex) {
	  log.error("QiniuException {}",ex.response.toString());
	}
  }

}
