package com.bjpowernode.p2p.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamUtil {

	public static void main(String[] args) {
		
		List<String> strList = new ArrayList<String>();
		strList.add("aa");
		strList.add("ab");
		strList.add("ac");
		strList.add("dd");
		strList.add("ee");
		strList.add("ff");
		
		List<String> collect = strList.stream().filter((String str)->{return str.startsWith("a");}).collect(Collectors.toList());
		
		System.out.println(collect);
		
		

	}

}
