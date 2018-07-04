package com.bjpowernode.p2p.mapper.loan;

import java.util.List;
import java.util.Map;

import com.bjpowernode.p2p.model.loan.IncomeRecord;

public interface IncomeRecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(IncomeRecord record);

    int insertSelective(IncomeRecord record);

    IncomeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IncomeRecord record);
    
    int getIncomeRecordByTotal(Map<String, Object> paramMap);

    List<IncomeRecord> getIncomeRecordByPage(Map<String, Object> paramMap);
    
    List<IncomeRecord> getIncomeRecordByIncomeDateIsCurrentDate();
}