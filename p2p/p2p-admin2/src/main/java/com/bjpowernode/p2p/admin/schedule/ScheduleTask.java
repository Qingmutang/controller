package com.bjpowernode.p2p.admin.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.bjpowernode.p2p.admin.service.CreditorRightsService;

@Configuration
//@EnableScheduling //该注解启用定时任务
public class ScheduleTask {
	
	/**
	 * 注入债权service对象
	 */
	@Autowired
	private CreditorRightsService creditorRightsService;

	/**
	 * 接收第三方债权的定时任务
	 * 
	 */
	//每5秒执行一次
	@Scheduled(cron = "* * 3 * * *")
    public void creditorScheduler() {
        System.out.println(">>>>>>>>>>>>> scheduled start ... ");
        
        creditorRightsService.receiveCreditor();
        
        System.out.println(">>>>>>>>>>>>> scheduled end ... ");
    }
	
	/**
	 * 生成合同和签章的定时任务
	 * 
	 */
	//每5秒执行一次
	@Scheduled(cron = "0/5 * * * * *")
    public void sealPdfScheduler() {
		
        System.out.println(">>>>>>>>>>>>> scheduled start ... ");
        
        //生成签章借款合同
        //creditorRightsService.sealPdf(null);
        
        System.out.println(">>>>>>>>>>>>> scheduled end ... ");
    }
}
