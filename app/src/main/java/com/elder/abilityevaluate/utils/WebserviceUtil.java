package com.elder.abilityevaluate.utils;

import com.elder.abilityevaluate.config.GlobalSetting;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.Map;

/**
 * @author xcj webservice 调用类
 */
public class WebserviceUtil {

	// ws 登录校验接口名
	public static final String METHOD_GET_LATESTVER = "getLatestVer";
	public static final String METHOD_LOGIN = "login";
	public static final String METHOD_GET_ARCGRADES = "getArcGrades";
	public static final String METHOD_GET_ARCEMPLOYEES = "getArcEmployees";
	public static final String METHOD_GET_ARCSTATIONS = "getStations";
	public static final String METHOD_GET_TRANSFERENCES = "getTransferences";
	public static final String METHOD_GET_IMPORTGRADECHECK = "getImportGradeCheck";
	public static final String METHOD_GET_IMPORTWEIGHTCHECK = "getImportWeightCheck";
	public static final String METHOD_GET_STOREHOUSE = "getStoreHouses";
	public static final String METHOD_GET_STORESUM = "getStoreSum";
	public static final String METHOD_GET_STOREHOUSEDTS = "getStoreHouseDts";
	public static final String METHOD_GET_SHIFTING = "getShifting";
	public static final String METHOD_GET_STORECHECK = "getStoreCheck";
	public static final String METHOD_GET_STOREEXPORT = "getStoreExport";
	public static final String METHOD_GET_PURIFY = "getPurify";
	public static final String METHOD_GET_LISENCEDATA = "getLisenceData";

	public static final String METHOD_COMMIT_IMPORTGRADECHECK = "commitImportGradeCheck";
	public static final String METHOD_COMMIT_IMPORTWEIGHTCHECK = "commitImportWeightCheck";
	public static final String METHOD_COMMIT_SHIFTING = "commitShifting";
	public static final String METHOD_COMMIT_STORECHECK = "commitStoreCheck";
	public static final String METHOD_COMMIT_STOREEXPORT = "commitStoreExport";
	public static final String METHOD_COMMIT_PURIFY = "commitPurify";
	public static final String METHOD_COMMIT_CHANGE = "commitChangeGrade";

	public static final String METHOD_ACCOUNT_IMPORTGRADECHECK = "accountImportGradeCheck";
	public static final String METHOD_ACCOUNT_IMPORTWEIGHTCHECK = "accountImportWeightCheck";
	public static final String METHOD_ACCOUNT_SHIFTING = "accountShifting";
	public static final String METHOD_ACCOUNT_STORECHECK = "accountStoreCheck";
	public static final String METHOD_ACCOUNT_STOREEXPORT = "accountStoreExport";
	public static final String METHOD_ACCOUNT_PURIFY = "accountPurify";
	

	public static final String PARAM_LOGIN_LOGINNAME = "loginName";
	public static final String PARAM_LOGIN_PWD = "pwd";

	public static final String PARAM_GET_YEAR = "year";
	public static final String PARAM_GET_CORPCODE = "corpCode";
	public static final String PARAM_GET_TYPE = "type";
	public static final String PARAM_GET_STATE = "state";
	public static final String PARAM_GET_LEAFGRADE = "leafGrade";
	public static final String PARAM_GET_LEAFSTYLE = "style";
	public static final String PARAM_GET_ENDDATA   ="endData";
	public static final String PARAM_GET_STARDATA  ="starData";
	public static final String PARAM_GET_CWID = "cwId";
	public static final String PARAM_DATA = "data";
	public static final String PARAM_CODE = "code";
	public static final String PARAM_GET_USERNAME = "userName";

	/**
	 * @Title: invokeMethodOnServer
	 * @Description: 调用webservice方法
	 *            params 参数键值对 无参数时，请传null
	 * @return String 方法返回值 Json字符串
	 * @throws
	 */
	public static String invokeMethodOnServer(String IPurl, String method,
			Map<String, Object> params) {
		if (IPurl == null) {
			return null;
		}

		String serviceNamespace = "http://service.ws.custom.com";
		String serviceURL = IPurl + GlobalSetting.WEBSERVICE_URL;

		try {
			SoapObject request = new SoapObject(serviceNamespace, method);
			if (params != null) {
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					request.addProperty(entry.getKey(), entry.getValue());
				}
			} else {
			}
			int timeout = GlobalSetting.CONNECT_TIME_OUT; // 超时设置
			HttpTransportSE hts = new HttpTransportSE(serviceURL, timeout);
			hts.debug = true;
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.bodyOut = request;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			// 注册
			hts.call(null, envelope);
			return envelope.getResponse().toString();
		} catch (Exception e) {
			
			e.printStackTrace();
			return "-1";
		}
	}
}
