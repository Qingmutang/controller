package com.bjpowernode.p2p.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数字处理工具类
 * 
 *
 */
public class NumberUtils {

	/**
	 * 四舍五入保留两位小数
	 * 
	 * @param num
	 * @return
	 */
	public static double number2Two (double num) {
		//保留两位小数
        BigDecimal bg = new BigDecimal(num).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
	}
	
	public static void main(String[] args) {
		System.out.println(number2Two(1236.3610));
	}
}
