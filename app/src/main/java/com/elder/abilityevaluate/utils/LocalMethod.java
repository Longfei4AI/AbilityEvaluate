/**   
 * @Title: LocalMethod.java 
 * @Package com.jr001.utils 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author jiaone@163.com
 * @date 2015-8-9 下午2:46:20 
 * @version V1.0   
 */
package com.elder.abilityevaluate.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.elder.abilityevaluate.config.GlobalSetting;
import com.elder.abilityevaluate.config.PreferenceParams;

import java.util.LinkedHashMap;
import java.util.Map;

public class LocalMethod {

	Context context;

	public LocalMethod(Context context) {
		this.context = context;
	}

	/**
	 * @Title: getLatestVer
	 * @Description: 获得最新的软件版本
	 * @return String 返回类型
	 * @throws
	 */
	public String getLatestVer() {
		return WebserviceUtil.invokeMethodOnServer(getServerUrl(),
				WebserviceUtil.METHOD_GET_LATESTVER, null);
	}

	/**
	 * @Title: login
	 * @Description: 登录
	 *            loginName 登录名,pwd 密码
	 * @return String 返回类型
	 * @throws
	 */
	public String login(String loginName, String pwd) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(WebserviceUtil.PARAM_LOGIN_LOGINNAME, loginName);
		map.put(WebserviceUtil.PARAM_LOGIN_PWD, pwd);
		return WebserviceUtil.invokeMethodOnServer(getServerUrl(),
				WebserviceUtil.METHOD_LOGIN, map);
	}


	public String getArcEmployees(String corpCode) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(WebserviceUtil.PARAM_GET_CORPCODE, corpCode);
		return WebserviceUtil.invokeMethodOnServer(getServerUrl(),
				WebserviceUtil.METHOD_GET_ARCEMPLOYEES, map);
	}


	/**
	 * @Title: getServerUrl
	 * @Description: 组装WebService地址
	 * @return String 返回类型
	 * @throws
	 */
	private String getServerUrl() {
		SharedPreferences preferences = context.getSharedPreferences(
				GlobalSetting.PREFERENCE_NAME, Context.MODE_PRIVATE);
		String ip = preferences.getString(PreferenceParams.SERVER_IP,
				GlobalSetting.DEFAULT_SERVER_IP);
		String port = preferences.getString(PreferenceParams.SERVER_PORT,
				GlobalSetting.DEFAULT_SERVER_PORT);
		return "http://" + ip + ":" + port;
	}

}
