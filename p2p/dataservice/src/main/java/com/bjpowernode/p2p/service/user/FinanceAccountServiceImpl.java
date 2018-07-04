package com.bjpowernode.p2p.service.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.model.user.FinanceAccount;

/**
 * 资金账户相关处理Service实现
 * 
 *
 */
@Service("financeAccountServiceImpl")
public class FinanceAccountServiceImpl implements FinanceAccountService {

	@Autowired
	private FinanceAccountMapper financeAccountMapper;
	
	@Override
	public FinanceAccount getFinanceAccountByUid(Long uid) {
		return financeAccountMapper.getFinanceAccountByUid(uid);
	}
	
	/**
	 * 更新用户资金金额，收益回款操作
	 * 
	 * @param paramMap
	 * @return
	 */
	public int updateFinanceAccountByBidAndIncomeMoneyAdd(Map<String, Object> paramMap) {
		return financeAccountMapper.updateFinanceAccountByBidAndIncomeMoneyAdd(paramMap);
	}
}