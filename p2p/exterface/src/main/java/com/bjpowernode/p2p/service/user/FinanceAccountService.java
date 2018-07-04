package com.bjpowernode.p2p.service.user;

import java.util.Map;

import com.bjpowernode.p2p.model.user.FinanceAccount;

public interface FinanceAccountService {

	/**
	 * 根据用户ID查询用户财务资金信息
	 * 
	 * @param uid
	 * @return
	 */
	public FinanceAccount getFinanceAccountByUid (Long uid);
	
	/**
	 * 更新用户资金金额，收益回款操作
	 * 
	 * @param paramMap
	 * @return
	 */
	public int updateFinanceAccountByBidAndIncomeMoneyAdd(Map<String, Object> paramMap);
}
 