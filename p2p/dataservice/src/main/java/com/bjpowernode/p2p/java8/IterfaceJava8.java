package com.bjpowernode.p2p.java8;
@FunctionalInterface
public interface IterfaceJava8 {
	default String sayHi() {
		
		return "hi";
	}
	
	static String work() {
		
		return "working...";
	}
	
	void dowork();
	

}
