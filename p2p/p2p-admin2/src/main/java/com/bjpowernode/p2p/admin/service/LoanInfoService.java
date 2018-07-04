package com.bjpowernode.p2p.admin.service;

import java.util.List;
import java.util.Map;

import com.bjpowernode.p2p.loan.model.LoanInfo;

/**
 * 标的产品信息Service接口
 * 
 * @author yanglijun
 *
 */
public interface LoanInfoService {

	/**
	 * 分页查询产品列表信息
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<LoanInfo> getLoanInfoByPage(Map<String, Object> paramMap);
	
	/**
	 * 查询所有的产品总数
	 * 
	 * @return
	 */
	public int getLoanInfoByTotal(Map<String, Object> paramMap);
	
	/**
	 * 根据id查询投标产品详情
	 * 
	 * @param id
	 * @return
	 */
	public LoanInfo getLoanInfoById (int id);
	
	/**
	 * 根据产品Id更新产品信息
	 * 
	 * @param loanInfo
	 * @return
	 */
	public int updateLoanInfoById (LoanInfo loanInfo);
	
	/**
	 * 添加产品信息
	 * 
	 * @param loanInfo
	 * @return
	 */
	public int addProduct(LoanInfo loanInfo);
	
	/**
	 * 根据产品ID删除产品
	 * 
	 * @param id
	 * @return
	 */
	public int deleteProductById (Integer id);
	
}
