package com.bjpowernode.p2p.loan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bjpowernode.p2p.loan.model.LoanInfo;

/**
 * 产品相关处理Mapper
 * 
 * @author yanglijun
 *
 */
public interface LoanInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(LoanInfo record);

    int insertSelective(LoanInfo record);

    LoanInfo selectByPrimaryKey(Integer id);
    
    /**
     * 分页查询投标产品信息
     * 
     * @param paramMap
     * @return
     */
    List<LoanInfo> getLoanInfoByPage(Map<String, Object> paramMap);
    
    /**
	 * 查询所有的产品总数
	 * 
	 * @return
	 */
	int getLoanInfoByTotal(Map<String, Object> paramMap);
	
	/**
	 * 根据id查询投标产品详情
	 * 
	 * @param id
	 * @return
	 */
	LoanInfo getLoanInfoById(int id);
	
	/**
	 * 根据状态获取产品及对应产品下的投标记录
	 * 
	 * @param productStatus
	 * @return
	 */
	List<LoanInfo> getBidRecordForLoanInfo(Integer productStatus);

	List<LoanInfo> getLoanInfoByProductStatus(int productStatus);
	
	/**
	 * 查询产品历史平均年化收益
	 * 
	 * @return
	 */
	Double getLoanInfoByHistoryAverageRate();
	
    int updateByPrimaryKeySelective(LoanInfo record);

    int updateByPrimaryKeyWithBLOBs(LoanInfo record);

    int updateByPrimaryKey(LoanInfo record);
    
    int updateLoanInfo (Map<String, Object> paramMap);
}