package com.modianli.power.task;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modianli.power.common.service.RequirementService;
import com.modianli.power.domain.jpa.Requirements;
import com.modianli.power.persistence.repository.jpa.RequirementBiddingRepository;
import com.modianli.power.persistence.repository.jpa.RequirementRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class RequirementTask {

  @Inject
  private ObjectMapper MAPPER;

  @Inject
  private RedisTemplate<String, String> redisTemplate;

  @Inject
  private RequirementRepository requirementRepository;

  @Inject
  private RequirementBiddingRepository biddingRepository;

  @Inject
  private RequirementService requirementService;

  private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

  private final static String PREFIX_REDIS = "POWER-REQUIREMENT-EP_";

  private final static String PREFIX_REDIS_PV = "POWER-REQUIREMENT-PV_";

  /**
   * 每天凌晨2点，更新浏览次数到数据
   */
  @Schedules({
	  @Scheduled(cron = "0 0 2 * * ?"),
	  //@Scheduled(cron = "*/5 * * * * ?")//test 每五秒
  })
  public void batchPageView(){
    log.debug("update page view.");
	Set<String> keys = redisTemplate.keys(PREFIX_REDIS_PV + "*");
	keys.stream().forEach((key) ->{
	  String pageViewStr = redisTemplate.opsForValue().get(key);
	  int pageView = Integer.parseInt(pageViewStr);

	  String uuid = key.split("_")[1];
	  requirementRepository.updateRequirementByUUIDIdAndPageView(uuid, pageView);
	});
  }

  @Schedules({
	  @Scheduled(cron = "0 */5 * * * ?"),
	 // @Scheduled(cron = "*/5 * * * * ?")//test 每五秒
  })
  @Transactional
  public void autoMatchEnterprise(){
	log.debug("auto match enterprise.");
	Set<String> keys = redisTemplate.keys(PREFIX_REDIS + "*");
	if(keys == null || keys.size() < 1){
	  log.debug("There are not requirements need to be matched.");
	  return ;
	}

	//遍历所有缓存中的KEY, 根据时间间隔判断是否进行匹配操作
	for(String key : keys){
	  try{
		String value = redisTemplate.opsForValue().get(key);
		//当前对应的需求分页数据和时间间隔数据存在
		if(StringUtils.isBlank(value)){
		  continue;
		}

		Map<String, String> params = MAPPER.readValue(value, Map.class);
		LocalDateTime beforeDate = LocalDateTime.parse(params.get("currentDate"));
		LocalDateTime currentDate = LocalDateTime.now();
		//缓存时间+1小时后在当前时间之前，进行派单
		if(beforeDate.plusHours(1L).isBefore(currentDate)){
		  Long requirementId = Long.parseLong(params.get("requirementId"));
		  Pageable page = new PageRequest(Integer.parseInt(params.get("page")) + 1,
										  Integer.parseInt(params.get("size")));
		  boolean result = requirementService.matchEnterprise(requirementId, page);//派单
		  if(!result){//派单失败, 无法进行派单删除当前缓存
			//redisTemplate.delete(key);
			log.info("current requirement of id is {} cannot be matched enterprise.", requirementId);
		  }else{
			//派单成功， 更新缓存信息
			params.put("page", page.getPageNumber() + "");
			params.put("currentDate", currentDate.toString());
			ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
			valueOperations.set(PREFIX_REDIS + requirementId, MAPPER.writeValueAsString(params));
		  }
		}
	  } catch(Exception ex){
		log.error("Exception when execute matching.", ex);
	  }

	}
  }

  /**
   * 执行匹配方法体
   */
  @Deprecated
  private void executeBody(String key){

  }

  @Schedules({
  	@Scheduled(cron = "0 0 0 * * ?"),
	//@Scheduled(cron = "0 30 10 26 5 ?")//5月26日 10:30更新
	//@Scheduled(cron = "*/5 * * * * ?")//test 每五秒
  })
  @Transactional
  public void endRequirements(){
    log.debug(">>>> 更新已经过期的需求以及需求的配单");
    LocalDate now = LocalDate.now();
    List<Requirements.Status> statuses = Lists.newArrayList();
    statuses.add(Requirements.Status.BIDDING);
	statuses.add(Requirements.Status.MATCHED);
	List<Requirements> requirementsList = requirementRepository.findByBiddingEndDateAndStatus(now, statuses);

	//更新需求
	requirementRepository.updateRequirementBatch(LocalDate.now(), Requirements.Status.BE_FINISHED, statuses);
	//更新需求配单
	List<Long> ids = requirementsList.parallelStream().map(Requirements::getId).collect(toList());
	log.debug(">>>> 更新的需求列表：{}", ids);
	if(ids.size() > 0){
	  biddingRepository.updateRequirementBiddingByRequirementAndActive(false, ids);

	  ids.forEach(id -> redisTemplate.delete(PREFIX_REDIS + id));
	}
  }
}
