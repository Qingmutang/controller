package com.bjpowernode.p2p.mapper.user;

import java.util.Map;

import com.bjpowernode.p2p.model.user.FinanceAccount;

public interface FinanceAccountMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    FinanceAccount getFinanceAccountByUid (Long uid);
    
    int updateFinanceAccount (Map<String, Object> paramMap);
    
    int updateFinanceAccountByAdd (Map<String, Object> paramMap);
    
    int updateFinanceAccountByBidAndIncomeMoneyAdd (Map<String, Object> paramMap);
}