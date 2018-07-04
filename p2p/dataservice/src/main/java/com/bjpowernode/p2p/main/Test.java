package com.bjpowernode.p2p.main;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bjpowernode.p2p.model.commons.ReturnObject;
import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.service.loan.BidInfoServiceImpl;
import com.bjpowernode.p2p.service.loan.LoanInfoServiceImpl;

public class Test {

	/**
	 * 超卖问题测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		
		/*LoanInfoServiceImpl loanInfoServiceImpl = (LoanInfoServiceImpl)context.getBean("loanInfoServiceImpl");
		
		List<LoanInfo>  ss = loanInfoServiceImpl.getBidRecordForLoanInfo(1);
		
		for (LoanInfo loanInfo : ss) {
			System.out.println(loanInfo.getId());
			List<BidInfo> d = loanInfo.getBidInfoList();
			for (BidInfo bidInfo :  d) {
				System.out.println(bidInfo.getId());
			}
		}
		System.out.println(ss);*/
		 
		BidInfoServiceImpl bidInfoServiceImpl = (BidInfoServiceImpl)context.getBean("bidInfoServiceImpl");
		
		Map<String, Object> paramMap = new ConcurrentHashMap<String, Object>();
		paramMap.put("bidMoney", 1.0D);
		paramMap.put("loanId", 7);
		paramMap.put("uid", 1);
		
		//多线程投资，验证投资产品会不会被超卖问题
		ExecutorService executorService = Executors.newFixedThreadPool(300);
		for (int i=0; i<10000; i++) {
			executorService.submit(new Callable<ReturnObject>() {
				@Override
				public ReturnObject call() throws Exception {
					//去投资
					ReturnObject returnObject = bidInfoServiceImpl.addBidInfo(paramMap);
					System.out.println("投资结果：" + Thread.currentThread().getName() + returnObject.getErrorCode() + returnObject.getErrorMessage());
					return returnObject;
				}
			});
		}
		//executorService.shutdown();
	}
}