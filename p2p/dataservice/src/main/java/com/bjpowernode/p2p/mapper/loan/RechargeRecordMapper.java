package com.bjpowernode.p2p.mapper.loan;

import java.util.List;
import java.util.Map;

import com.bjpowernode.p2p.model.loan.RechargeRecord;

public interface RechargeRecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);
    
    RechargeRecord selectByRechargeNo(String rechargeNo);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);
    
    int updateRechargeStatus(Map<String, Object> paramMap);
    
    int selectByRechargeCount(Integer uid);
    
    List<RechargeRecord> selectByRechargePage(Map<String, Object> paramMap);
}