package com.bjpowernode.p2p.service.loan;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjpowernode.p2p.mapper.loan.RechargeRecordMapper;
import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.model.loan.RechargeRecord;

/**
 * 充值相关处理Service实现
 * 
 *
 */
@Service("rechargeServiceImpl")
public class RechargeServiceImpl implements RechargeService {
	
	@Autowired
	private FinanceAccountMapper financeAccountMapper;
	
	@Autowired
	private RechargeRecordMapper rechargeRecordMapper;
	
	@Override
	public int addRechargeRecord(RechargeRecord rechargeRecord) {
		return rechargeRecordMapper.insertSelective(rechargeRecord);
	}
	
	/**
	 * 充值处理
	 * 
	 */
	@Override
	public int recharge(String rechargeNo, Double rechargeMoney, int rechargeStatus) {
		
		Map<String, Object> paramMap = new ConcurrentHashMap<String, Object>();
		paramMap.put("rechargeNo", rechargeNo);
		paramMap.put("rechargeStatus", rechargeStatus);
		paramMap.put("rechargeMoney", rechargeMoney);
		
		//修改充值订单状态
		int updateRechargeStatus = rechargeRecordMapper.updateRechargeStatus(paramMap);
		
		//先要查一下用户的id
		RechargeRecord rechargeRecord = rechargeRecordMapper.selectByRechargeNo(rechargeNo);
		//更新用户可用余额
		paramMap.put("uid", rechargeRecord.getUid());
		
		int updaterechargeMoney = financeAccountMapper.updateFinanceAccountByAdd(paramMap);
		
		if (1==updateRechargeStatus && 1==updaterechargeMoney) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * 查询充值记录总数
	 * 
	 */
	public int getRechargeRecordByCount (int uid) {
		return rechargeRecordMapper.selectByRechargeCount(uid);
	}
	
	/**
	 * 分页查询充值记录列表
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<RechargeRecord> getRechargeRecordByPage (Map<String, Object> paramMap) {
		return rechargeRecordMapper.selectByRechargePage(paramMap);
	}
}