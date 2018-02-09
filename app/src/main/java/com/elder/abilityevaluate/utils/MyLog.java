/**   
 * @Title: MyLog.java 
 * @Package com.custom.qualitytrace.util
 * @Description: 
 * @Copyright 山东山大新元易通信息科技有限公司 www.sdcustom.com
 * @date 2015-3-16 下午3:34:22 
 * @version V1.0   
 */
package com.elder.abilityevaluate.utils;

import android.util.Log;

/** 
 * @ClassName: MyLog 
 * @Description: 日志管理类
 * @Copyright 山东山大新元易通信息科技有限公司 www.sdcustom.com
 * @date 2015-3-16 下午3:34:22 
 */
public class MyLog {
	
	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int NOTHING = 6;
	public static final int LEVEL = DEBUG;//在这里选择要输出日志的等级
	
	
	public static void d(String tag,String msg)
	{
		if (LEVEL <= DEBUG)
		{
			Log.d(tag, msg);
		}
	}
	
	public static void i(String tag,String msg)
	{
		if (LEVEL <= INFO)
		{
			Log.i(tag, msg);
		}
	}
	
	public static void w(String tag,String msg)
	{
		if (LEVEL <= WARN)
		{
			Log.w(tag, msg);
		}
	}
	
	public static void e(String tag,String msg)
	{
		if (LEVEL <= ERROR)
		{
			Log.e(tag, msg);
		}
	}
}
