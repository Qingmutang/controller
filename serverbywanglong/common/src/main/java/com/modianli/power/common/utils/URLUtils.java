package com.modianli.power.common.utils;

import java.net.URLDecoder;

public class URLUtils {

  public static String decode(String attr){
	try {
	  return URLDecoder.decode(URLDecoder.decode(attr, "utf-8"), "utf-8");
	} catch (Exception e) {
	  return null;
	}
  }
}
