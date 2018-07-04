package com.bjpowernode.p2p.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class P2pAdmin2Application {

	public static void main(String[] args) {
		SpringApplication.run(P2pAdmin2Application.class, args);
	}
}
