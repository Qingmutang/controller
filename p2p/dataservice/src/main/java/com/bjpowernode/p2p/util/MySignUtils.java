package com.bjpowernode.p2p.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class MySignUtils {
	
	
	public String getSortParameter(Map<String,Object> paramMap) {
		TreeMap<String ,Object> treeMap = new TreeMap<String,Object>(paramMap);
		
		Set<Entry<String,Object>> entrySet = treeMap.entrySet();
		Iterator<Entry<String, Object>> iterator = entrySet.iterator();
		
		while(iterator.hasNext()) {
			
			Entry<String, Object> next = iterator.next();
			
			next.getKey();
			next.getValue();
			
		}
		
		
		return null;
	}

}
