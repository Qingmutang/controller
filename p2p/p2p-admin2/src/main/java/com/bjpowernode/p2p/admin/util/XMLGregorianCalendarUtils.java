package com.bjpowernode.p2p.admin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * javax.xml.datatype.XMLGregorianCalendar处理工具类
 * 
 * @author yanglijun
 *
 */
public class XMLGregorianCalendarUtils {

	/**
	 * 日期java.util.Date转javax.xml.datatype.XMLGregorianCalendar
	 * 
	 * @param date java.util.Date日期对象
	 * @return
	 */
	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
		XMLGregorianCalendar gc = null;
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gc;
	}

	/**
	 * XMLGregorianCalendar转 Date对象
	 * 
	 * @param cal
	 * @return
	 */
	public static Date convertToDate(XMLGregorianCalendar cal) {
		GregorianCalendar ca = cal.toGregorianCalendar();
		return ca.getTime();
	}

	public static void main(String[] args) {
		XMLGregorianCalendar d = XMLGregorianCalendarUtils.convertToXMLGregorianCalendar(new Date());
		System.out.println(d.getDay());
		XMLGregorianCalendar cal = null;
		try {
			cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			cal.setMonth(06);
			cal.setYear(2010);
			Date date = XMLGregorianCalendarUtils.convertToDate(cal);
			String format = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			System.out.println(formatter.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
