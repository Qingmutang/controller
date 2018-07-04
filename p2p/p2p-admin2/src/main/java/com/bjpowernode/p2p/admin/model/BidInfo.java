package com.bjpowernode.p2p.admin.model;

import java.math.BigDecimal;

/**
 * 债权下的产品投资及用户信息对象
 * 
 * @author yanglijun
 *
 */
public class BidInfo {

	/**投资用户ID*/
	private Integer userId;

	/**投资用户手机号*/
    private String phone;
    
    /**投资用户姓名*/
    private String name;
    
    /**投资用户身份证号码*/
    private String idCard;
    
    /**投资记录ID*/
    private Integer bidId;
    
    /**投资金额*/
    private BigDecimal bidMoney;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}

	public BigDecimal getBidMoney() {
		return bidMoney;
	}

	public void setBidMoney(BigDecimal bidMoney) {
		this.bidMoney = bidMoney;
	}
}
