package com.bjpowernode.p2p.admin.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 债权数据对象
 * 
 * @author yanglijun
 *
 */
public class CreditorContract {
	
	/**债权ID*/
	private Integer id;

	/**借款申请编号*/
    private String applyNo;
    
    /**申请借款目的*/
    private String applyPurpose;
    
    /**债权审批借款金额*/
    private BigDecimal auditLoanMoney;
    
    /**债权审批借款期限（月）*/
    private Integer auditLoanTerm;
    
    /**借款人真实姓名*/
    private String borrowerRealname;
    
    /**借款人身份证号*/
    private String borrowerIdcard;
    
    /**借款人性别*/
    private String borrowerSex;
    
    /**借款人现住址*/
    private String borrowerPresentAddress;
    
    /**资金募集完成时间*/
    private Date collectFinishTime;
    
    /**债权下的产品投资信息及用户信息*/
    private List<BidInfo> bidInfoList = new ArrayList<BidInfo>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getApplyPurpose() {
		return applyPurpose;
	}

	public void setApplyPurpose(String applyPurpose) {
		this.applyPurpose = applyPurpose;
	}

	public BigDecimal getAuditLoanMoney() {
		return auditLoanMoney;
	}

	public void setAuditLoanMoney(BigDecimal auditLoanMoney) {
		this.auditLoanMoney = auditLoanMoney;
	}

	public Integer getAuditLoanTerm() {
		return auditLoanTerm;
	}

	public void setAuditLoanTerm(Integer auditLoanTerm) {
		this.auditLoanTerm = auditLoanTerm;
	}

	public String getBorrowerRealname() {
		return borrowerRealname;
	}

	public void setBorrowerRealname(String borrowerRealname) {
		this.borrowerRealname = borrowerRealname;
	}

	public String getBorrowerIdcard() {
		return borrowerIdcard;
	}

	public void setBorrowerIdcard(String borrowerIdcard) {
		this.borrowerIdcard = borrowerIdcard;
	}

	public String getBorrowerSex() {
		return borrowerSex;
	}

	public void setBorrowerSex(String borrowerSex) {
		this.borrowerSex = borrowerSex;
	}

	public String getBorrowerPresentAddress() {
		return borrowerPresentAddress;
	}

	public void setBorrowerPresentAddress(String borrowerPresentAddress) {
		this.borrowerPresentAddress = borrowerPresentAddress;
	}

	public Date getCollectFinishTime() {
		return collectFinishTime;
	}

	public void setCollectFinishTime(Date collectFinishTime) {
		this.collectFinishTime = collectFinishTime;
	}

	public List<BidInfo> getBidInfoList() {
		return bidInfoList;
	}

	public void setBidInfoList(List<BidInfo> bidInfoList) {
		this.bidInfoList = bidInfoList;
	}
}
