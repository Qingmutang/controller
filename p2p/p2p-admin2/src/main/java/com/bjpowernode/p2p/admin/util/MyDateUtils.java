package com.bjpowernode.p2p.admin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类
 * 
 * @author 动力节点705班
 *
 */
public class MyDateUtils {
	
	public final static String FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	/**
	 * 字符串日期转java.util.Date
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate (String strDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		    return sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当前日期的格式化信息
	 * 
	 * @return
	 */
	public static String getCurrentDateByFormat () {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSSSSS);
		return format.format(new Date());
	}
}
