package com.elder.abilityevaluate.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

/**
 * @author xcj 环境信息获取类
 */
public class GlobalInfo {
	public final static String PIC_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/evaluate/images";
	public static String userName;
	public static String userRealName;
	/**
	 * 判断wifi 是否开启
	 * 
	 * @return
	 */
	public static boolean isWifiON(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获得wifi状态
		NetworkInfo wifinetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifinetworkInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断sdcard是否可用
	 * 
	 * @return
	 */
	public static boolean isSDCardAvailable() {
		String status = Environment.getExternalStorageState();
		// System.err.println(Environment.getExternalStorageState());
		return status.equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获得sdcard路径
	 * 
	 * @return
	 */
	public static String getSdcardPath() {
		if (isSDCardAvailable()) {
			// System.err.println(Environment.getExternalStorageDirectory()
			// .getAbsolutePath());
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		} else {
			return "";
		}
	}

	/**
	 * 判断是否有可用的数据连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isInternetAvailable(Context context) {

		// 检测网络是否可用
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(null == connectivityManager){
			return false;
		}else{
			return true;
		}

		// 获得GPRS数据连接状态
		//NetworkInfo mobilenetworkInfo = connectivityManager
		//		.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		//NetworkInfo mobilenetworkInfo = connectivityManager.getActiveNetworkInfo();
		/**
		if (mobilenetworkInfo.isConnectedOrConnecting() || isWifiON(context)) {
			return true;
		} else {
			return false;
		}
		*/
	}

	/**
	 * 获得当前版本号
	 * 
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public static int getVersionCode(Context context)
			throws NameNotFoundException {
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
		int version = packInfo.versionCode;
		return version;
	}
	/**
	 * 获得当前版本名
	 * 
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public static String getVersionName(Context context){
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		String versionName = "";
		try {
			packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionName;
	}


	/**
	 * 判断是否有相机
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得摄像头
	 * 
	 * @return
	 */
	public static Camera getCameraInstance() {
		int cameraCount = 0;
        CameraInfo cameraInfo = new CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
		Camera c = null;
		for(int i = 0; i < cameraCount; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if(cameraInfo.facing  == CameraInfo.CAMERA_FACING_BACK) {
				try {
					c = Camera.open(i);
				} catch (Exception e) {
					System.err.println("Camera is unavailable!");
				}
			}
		}
		return c; 
	}

	public static int px2dip(int pxValue){
		final float scale = Resources.getSystem().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	public static int dip2px(float dipValue){
		final float scale = Resources.getSystem().getDisplayMetrics().density;
		return  (int)(dipValue * scale + 0.5);
	}
}
