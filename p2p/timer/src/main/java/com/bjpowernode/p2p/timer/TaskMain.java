package com.bjpowernode.p2p.timer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskMain {

	public static void main (String[] args) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		context.start();
	}
}