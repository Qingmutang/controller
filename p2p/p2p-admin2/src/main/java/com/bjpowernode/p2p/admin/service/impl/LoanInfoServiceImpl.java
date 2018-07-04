package com.bjpowernode.p2p.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjpowernode.p2p.loan.model.LoanInfo;
import com.bjpowernode.p2p.admin.service.LoanInfoService;
import com.bjpowernode.p2p.loan.mapper.LoanInfoMapper;

/**
 * 标的产品信息Service接口
 * 
 * @author yanglijun
 *
 */
@Service("loanInfoService")
public class LoanInfoServiceImpl implements LoanInfoService {
	
	@Autowired
	private LoanInfoMapper loanInfoMapper;

	/**
	 * 分页查询产品列表信息
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<LoanInfo> getLoanInfoByPage(Map<String, Object> paramMap) {
		return loanInfoMapper.getLoanInfoByPage(paramMap);
	}
	
	/**
	 * 查询所有的产品总数
	 * 
	 * @return
	 */
	public int getLoanInfoByTotal(Map<String, Object> paramMap) {
		return loanInfoMapper.getLoanInfoByTotal(paramMap);
	}
	
	/**
	 * 根据id查询投标产品详情
	 * 
	 * @param id
	 * @return
	 */
	public LoanInfo getLoanInfoById (int id) {
		return loanInfoMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据产品Id更新产品信息
	 * 
	 * @param loanInfo
	 * @return
	 */
	public int updateLoanInfoById (LoanInfo loanInfo) {
		return loanInfoMapper.updateByPrimaryKeySelective(loanInfo);
	}
	
	/**
	 * 添加产品信息
	 * 
	 * @param loanInfo
	 * @return
	 */
	public int addProduct(LoanInfo loanInfo) {
		return loanInfoMapper.insertSelective(loanInfo);
	}
	
	/**
	 * 根据产品ID删除产品
	 * 
	 * @param id
	 * @return
	 */
	public int deleteProductById (Integer id) {
		return loanInfoMapper.deleteByPrimaryKey(id);
	}
}