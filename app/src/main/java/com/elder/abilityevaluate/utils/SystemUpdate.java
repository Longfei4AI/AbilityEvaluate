/**   
 * @Title: SystemUpdate.java 
 * @Package com.jr001.utils 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author jiaone@163.com
 * @date 2015-8-11 下午8:09:53 
 * @version V1.0   
 */
package com.elder.abilityevaluate.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import com.elder.abilityevaluate.config.GlobalSetting;
import com.elder.abilityevaluate.config.PreferenceParams;
import com.elder.abilityevaluate.eventBus.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import de.greenrobot.event.EventBus;

public class SystemUpdate {

	public final static int EVENT_DOWN = 0x9A;
	public final static int EVENT_FIND = 0x9B;

	/**
	 * 安装更新包
	 */
	public static void installAPK(Context context, String packageSavePath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + packageSavePath),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 获取服务器版本号
	 * 
	 * @return -1 ：版本号 为空；-2：连接异常
	 */
	private static int getLatestCode(Context context) {
		HttpDownLoader hdl = new HttpDownLoader();
		SharedPreferences preferences = context.getSharedPreferences(
				GlobalSetting.PREFERENCE_NAME, Context.MODE_PRIVATE);
		String ip = preferences.getString(PreferenceParams.SERVER_IP, "");
		String port = preferences.getString(PreferenceParams.SERVER_PORT, "80");
		String code = hdl.down("http://" + ip + ":" + port
				+ GlobalSetting.DOWNLOAD_APKPATH
				+ GlobalSetting.VERSIONINFO_NAME);
		code = code.split(";")[0];
		System.out.println("建设"+ip);
		if (HttpDownLoader.CONNECT_TIMEOUT.equals(code)
				|| HttpDownLoader.CONNECT_FAILE.equals(code)
				|| HttpDownLoader.CONNECT_IOEXCEPTION.equals(code)) {
			return -2;
		}
		if ("".equals(code)) {
			return -1;
		}
		return Integer.parseInt(code);
	}

	/**
	 * @author xcj 下载线程
	 */
	public static class DownProcessThread extends Thread {
		private Activity activity;
		private boolean interrupt = false;
		private String packageSavePath;

		public DownProcessThread(Activity activity, String psp) {
			super();
			this.activity = activity;
			interrupt = false;
			packageSavePath = psp;
		}

		public void setInterrupt(boolean b) {
			interrupt = b;
		}

		@Override
		public void run() {
			interrupt = false;
			FileUtil fileUtil = new FileUtil();
			// 创建路径
			fileUtil.creatDir(GlobalSetting.SAVE_APKPATH);

			File file = new File(packageSavePath);
			if (file.exists()) {// 删除已存在的文件
				file.delete();
			}
			FileOutputStream fos = null;
			// 已下载的长度
			int downloadsize = 0;
			// 字节流总长度
			int length = 0;
			InputStream is = null;
			Map<String, Object> result = new HashMap<String, Object>();
			Event.MsgEvent event = new Event.MsgEvent(EVENT_DOWN, result);
			event.fromClass = activity.getClass();
			try {
				SharedPreferences preferences = activity.getSharedPreferences(
						GlobalSetting.PREFERENCE_NAME, Context.MODE_PRIVATE);
				String ip = preferences.getString(PreferenceParams.SERVER_IP,
						"");
				String port = preferences.getString(
						PreferenceParams.SERVER_PORT, "80");
				HttpURLConnection huc = (HttpURLConnection) (new URL("http://"
						+ ip + ":" + port + GlobalSetting.DOWNLOAD_APKPATH
						+ GlobalSetting.APK_NAME).openConnection());
				is = huc.getInputStream();
				length = huc.getContentLength();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				fos = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				do {
					if (interrupt) {
						break;
					}
					int num = is.read(bytes);
					if (num == -1)
						break;
					fos.write(bytes, 0, num);
					downloadsize += num;
					// 发送下载进度
					result.put("result", "2");
					result.put("progress", (int) (downloadsize * 100 / length));
					EventBus.getDefault().post(event);
				} while (true);
				if (interrupt) {
					result.put("result", "3");
					interrupt = false;
				} else {
					result.put("result", "1");
				}
				EventBus.getDefault().post(event);
				fos.flush();
			} catch (FileNotFoundException e) {
				result.put("result", "0");
				EventBus.getDefault().post(event);
				e.printStackTrace();
				return;
			} catch (IOException e) {
				result.put("result", "0");
				EventBus.getDefault().post(event);
				e.printStackTrace();
				return;
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						System.err.println("文件关闭异常！");
					}
				}
			}

		}
	};

	/**
	 * @author xcj 检查更新线程
	 */
	public static class CheckUpdateThread extends Thread {
		int myVC;
		private int latestVC = 0;
		private Activity activity;

		public CheckUpdateThread(Activity act) {
			this.activity = act;
			try {
				myVC = GlobalInfo.getVersionCode(activity);
			} catch (NameNotFoundException e) {
				System.err.println("获取包信息失败！");
				e.printStackTrace();
			}
		}

		public void run() {
			// 连接服务器，返回最新包的大小，路径，版本。
			latestVC = SystemUpdate.getLatestCode(activity);
			Map<String, Object> result = new HashMap<String, Object>();
			Event.MsgEvent event = new Event.MsgEvent(EVENT_FIND, result);
			event.fromClass = activity.getClass();
			if (latestVC == -1 || latestVC == -2) {
				result.put("result", "3");
			} else {
				if (myVC < latestVC) {
					result.put("result", "1");
				} else {
					result.put("result", "2");
				}
			}
			EventBus.getDefault().post(event);
		}
	}
}
