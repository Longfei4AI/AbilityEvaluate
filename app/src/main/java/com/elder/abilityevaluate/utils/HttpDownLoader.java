package com.elder.abilityevaluate.utils;

import com.elder.abilityevaluate.config.GlobalSetting;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * @author xcj
 * 
 */
public class HttpDownLoader {

	private URL url = null;

	public static final String CONNECT_SUCCESS = "connect_success_0";
	public static final String CONNECT_FAILE = "connect_faile_-1";
	public static final String CONNECT_TIMEOUT = "connect_timeout_-2";
	public static final String CONNECT_IOEXCEPTION = "connect_IOexception_-3";


	/**
	 * 根据地址下载文件字符串
	 * 
	 * @param urlstr
	 *            地址
	 * @return String 0:连接成功；-1：无法创建链接；-2连接超时；-3：IO异常
	 * 
	 */
	public String down(String urlstr) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		String line = null;
		try {
			url = new URL(urlstr);
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			// 超时
			hc.setConnectTimeout(GlobalSetting.CONNECT_TIME_OUT);
//			int state = hc.getResponseCode();
			br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (SocketTimeoutException e) {
			System.err.println("连接超时！");
			return CONNECT_TIMEOUT;
		} catch (MalformedURLException e) {
			System.err.println("无法创建链接！");
			return CONNECT_FAILE;
		} catch (ConnectException e) {
			System.err.println("地址无法解析！");
			return CONNECT_FAILE;
		} catch (IOException e) {
			e.printStackTrace();
			return CONNECT_IOEXCEPTION;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @param url
	 * @param filePath
	 * @param fileName
	 * @return -1:文件已存在;-2:路径不合法;0:下载成功.
	 */
	public int downFile(String url, String filePath, String fileName) {
		FileUtil fileUtil = new FileUtil();
		// if the File already exists,return
		if (FileUtil.isFileExists(filePath + fileName))
			return -1;
		// if the path is illegal,return
		if (null == fileUtil.creatDir(filePath)) {
			return -2;
		}
		// create the File Firstly;
		File file = fileUtil.creatFile(filePath, fileName);
		// if the File is directory ,return
		if (file.isDirectory()) {
			return -2;
		}
		fileUtil.write2File(filePath + fileName, getInputStream(url));

		return 0;
	}

	/**
	 * 获得输入字节流
	 * 
	 * @param url
	 * @return
	 */
	public InputStream getInputStream(String url) {
		InputStream is = null;
		try {
			HttpURLConnection huc = (HttpURLConnection) (new URL(url)
					.openConnection());
			is = huc.getInputStream();
		} catch (MalformedURLException e) {
			System.err.println("打开链接异常！");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}
}
