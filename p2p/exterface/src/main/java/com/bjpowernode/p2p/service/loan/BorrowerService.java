package com.bjpowernode.p2p.service.loan;

import java.util.List;
import java.util.Map;

import com.bjpowernode.p2p.model.loan.BorrowerInfo;

public interface BorrowerService {

	public List<BorrowerInfo> getBorrowerByPage (Map<String, Object> paramMap);
	
	public int getBorrowerInfoByTotal ();
}
