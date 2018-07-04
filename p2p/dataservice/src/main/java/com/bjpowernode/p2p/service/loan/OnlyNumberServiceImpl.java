package com.bjpowernode.p2p.service.loan;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bjpowernode.p2p.constants.Constants;

/**
 * 生成全局唯一数字
 * 
 *
 */
@Service("onlyNumberServiceImpl")
public class OnlyNumberServiceImpl implements OnlyNumberService {

	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;
	
	/**
	 * 生成唯一数字
	 * 
	 * @return
	 */
	public Long getOnlyNumber() {
		Long onlyNumber = redisTemplate.opsForValue().increment(Constants.ONLY_NUMBER, 1L);
		return onlyNumber;
	}
}
