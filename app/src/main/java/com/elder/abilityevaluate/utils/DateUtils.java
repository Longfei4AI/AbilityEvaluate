/**   
* @Title: DateUtils.java 
* @Package com.jr001.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author jiaone@163.com
* @date 2015-8-3 下午9:10:16 
* @version V1.0   
*/
package com.elder.abilityevaluate.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class DateUtils {
	/** 
	* @Title: getCurDate 
	* @Description: 获得当前日期
	* @return String    返回类型 
	* @throws 
	*/
	public static String getCurDate() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		// 年
		String date = cal.get(Calendar.YEAR) + "-";
		// 月
		if (cal.get(Calendar.MONTH) + 1 < 10) {
			date = date + "0";
		}
		date = date + (cal.get(Calendar.MONTH) + 1) + "-";
		// 日
		if (cal.get(Calendar.DATE) < 10) {
			date = date + "0";
		}
		date = date + cal.get(Calendar.DATE);
		return date;
	}
	
	/** 
	* @Title: getCurYear 
	* @Description: 获得当前年度 
	* @param   参数 
	* @return String    返回类型 
	* @throws 
	*/
	public static String getCurYear() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		// 年
		return cal.get(Calendar.YEAR)+"";
	}
	
	/** 
	* @Title: getCurTime 
	* @Description: 获得当前时间 
	* @param   参数 
	* @return String    返回类型 
	* @throws 
	*/
	public static String getCurTime() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		String time = "";
		// 时
		if (cal.get(Calendar.HOUR_OF_DAY) < 10) {
			time = time + "0";
		}
		time = time + cal.get(Calendar.HOUR_OF_DAY) + ":";
		// 分
		if (cal.get(Calendar.MINUTE) < 10) {
			time = time + "0";
		}
		time = time + cal.get(Calendar.MINUTE) + ":";
		// 秒
		if (cal.get(Calendar.SECOND) < 10) {
			time = time + "0";
		}
		time = time + cal.get(Calendar.SECOND);

		return time;
	}
	
	/**
	 * 得到当前日期时间
	 * 
	 * @return 当前日期时间
	 */
	public static String getCurDateTime() {
		String datetime = getCurDate() + " " + getCurTime();
		return datetime;
	}
	
	public static String dateFormater(String date){
		if(date==null||date.length()<10){
			return date;
		}
		String year=date.substring(0,4);
		String month=date.substring(5,7);
		String day=date.substring(8,10);
//		String	date_str = new SimpleDateFormat("yyyy年MM月dd日")
//		.format(new Date(date.trim()));
		return year+"年"+month+"月"+day+"日";
	}
}
