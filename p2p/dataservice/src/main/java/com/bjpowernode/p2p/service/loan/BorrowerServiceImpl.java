package com.bjpowernode.p2p.service.loan;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bjpowernode.p2p.model.loan.BorrowerInfo;

/**
 * 借款人相关处理Service实现
 * 
 *
 */
@Service("borrowerServiceImpl")
public class BorrowerServiceImpl implements BorrowerService {

	@Override
	public List<BorrowerInfo> getBorrowerByPage(Map<String, Object> arg0) {
		return null;
	}

	@Override
	public int getBorrowerInfoByTotal() {
		return 0;
	}
}
