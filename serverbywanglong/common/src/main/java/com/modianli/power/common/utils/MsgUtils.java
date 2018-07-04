package com.modianli.power.common.utils;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class MsgUtils {

  public static Map<String, String> getMsg(String msg){
	Map<String, String> result = Maps.newHashMap();
	result.put("msg", StringUtils.isNotBlank(msg)? msg: "操作成功");
	return result;
  }

  public static Map<String, Object> getResult(Object result){
	Map<String, Object> resultMap = Maps.newHashMap();
	resultMap.put("result", result);
	return resultMap;
  }
}
