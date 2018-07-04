package com.bjpowernode.p2p.mapper.loan;

import java.util.List;
import java.util.Map;

import com.bjpowernode.p2p.model.loan.DealRecord;

public interface DealRecordMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(DealRecord record);

    int insertSelective(DealRecord record);

    DealRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DealRecord record);

    int updateByPrimaryKey(DealRecord record);
    
    List<DealRecord> getDealRecordByUid(Map<String, Object> paramMap);
    
}