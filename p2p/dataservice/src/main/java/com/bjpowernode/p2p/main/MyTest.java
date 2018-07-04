package com.bjpowernode.p2p.main;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bjpowernode.p2p.constants.Constants;
import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import com.bjpowernode.p2p.mapper.user.UserMapper;
import com.bjpowernode.p2p.model.loan.LoanInfo;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MyTest {

	@Autowired
	private LoanInfoMapper loanInfoMapper;
	private LoanInfo loanInfoById;
	
	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;
	
	@Autowired
	private UserMapper uUserMapper;
	
	@org.junit.Test
	public void test02() {
		BoundValueOperations<String, Serializable> boundValueOperations = redisTemplate.boundValueOps(Constants.ALL_USER_COUNT);
		Long allUserCount = (Long)boundValueOperations.get();
		if (null == allUserCount) {
			allUserCount = uUserMapper.selectByUserCount();
			boundValueOperations.set(allUserCount);
			boundValueOperations.expire(15, TimeUnit.MINUTES);
		}
		
	}

}
