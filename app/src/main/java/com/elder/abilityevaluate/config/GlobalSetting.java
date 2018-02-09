/**   
 * @Title: GlobalSetting.java 
 * @Package com.custom.jr001.config
 * @Description: 全局参数设置
 * @author jiaone@163.com
 * @date 2015-4-8 上午11:08:35 
 * @version V1.0   
 */
package com.elder.abilityevaluate.config;


public class GlobalSetting {
	// 是否全屏
	public static final boolean FULL_SCREEN = true;
	// 是否常亮
	public static final boolean KEEP_SCREEN_ON = true;
	// 是否显示界面切换动画
	public static final boolean ACTIVITY_ANIMATION = true;
	// 数据刷新时间
	public static final long QUERY_REFRESH_TIME = 3 * 60 * 1000;

	// 默认服务器IP
	public static final String DEFAULT_SERVER_IP = "192.168.1.168";
	// 默认服务器端口
	public static final String DEFAULT_SERVER_PORT = "80";
	// 日志保存路径设置
	public static final String LOG_PATH = "/custom/elder/log/";
	// apk保存路径设置
	public static final String SAVE_APKPATH = "/custom/elder/download/";
	// apk下载路径设置
	public static final String DOWNLOAD_APKPATH = "/AbilityEvaluate/download/elder/";
	// 版本文件名
	public static final String VERSIONINFO_NAME = "version_elder.txt";
	// APK名
	public static final String APK_NAME = "AbilityEvaluate.apk";
	// ws 接口路径
	public static final String WEBSERVICE_URL = "/services/AbilityEvaluateService";
	// preference name
	public static final String PREFERENCE_NAME = "custom_preference";
	
	// ws 连接超时时间
	public static final int CONNECT_TIME_OUT = 100000;

}
