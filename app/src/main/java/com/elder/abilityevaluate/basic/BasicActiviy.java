/**   
 * @Title: BasicActiviy.java 
 * @Package com.custom.jr001.basic
 * @Description: Activity 基类
 * @author jiaone@163.com
 * @date 2015-4-8 10:51:47 
 * @version V1.0   
 */
package com.elder.abilityevaluate.basic;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.config.GlobalSetting;
import com.elder.abilityevaluate.utils.DateUtils;
import com.elder.abilityevaluate.view.LoadingLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BasicActiviy extends FragmentActivity {
	
	public boolean activity_inited = false;
	public boolean showInitedLoading = false;
	//页面加载布局
	LoadingLayout loadingLayout = null;
	Timer initTimer = null;

	protected DatePickerDialog datePickerDialog;
	protected DatePickerDialog datePickerDialog1;
	protected String choose_date = "";
	protected String choose_date1 = "";
	protected String date_str = "";
	protected String date_str1 = "";
	protected int c_year, c_month, c_day = 0;

	Handler initedHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (initTimer != null) {
					initTimer.cancel();
				}
				if (loadingLayout != null) {
					loadingLayout.destroy();
				}
				if (!showInitedLoading) {
					afterInit();
				}
				loadingLayout = null;
				initTimer = null;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 设置无标题必须在setContentView之前设置
		Window window = getWindow();
		window.setBackgroundDrawable(null);
		// 设置全屏
		if (GlobalSetting.FULL_SCREEN) {
			window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		// 保持长亮
		if (GlobalSetting.KEEP_SCREEN_ON) {
			window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
		// 切换动画
		if (GlobalSetting.ACTIVITY_ANIMATION) {
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_left_out);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!activity_inited) {
			// 加载界面
			if (showInitedLoading) {
				if (loadingLayout == null) {
					loadingLayout = new LoadingLayout(this,
							new LoadingLayout.CallBack() {
								@Override
								public void dispear() {
									afterInit();
								}
							});
					this.addContentView(loadingLayout, new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
				}
			}
			if (initTimer == null) {
				initTimer = new Timer();
				initTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (activity_inited == true) {
							Message msg = initedHandler.obtainMessage();
							msg.what = 1;
							initedHandler.sendMessage(msg);
						}
					}
				}, 50, 400);
			}
			new InitThread().start();
		} else {
			if (loadingLayout != null) {
				loadingLayout.setVisibility(View.INVISIBLE);
				loadingLayout = null;
			}
		}
	}

	public abstract void init();

	public abstract void initFinished();
	
	/**
	 * 
	 * @Title: afterSetDate 
	 * @Description: 日期被设定后进行的处理
	 * @param @param y 年
	 * @param @param m 月
	 * @param @param d 日
	 * @return void  
	 * @throws
	 */
	public void afterSetDate(int y, int m, int d){
	}
	public void afterSetDate1(int y, int m, int d){
	}
	
	private void afterInit(){
		choose_date = DateUtils.getCurDate();
		choose_date1 = DateUtils.getCurDate();
		Calendar cal = Calendar.getInstance(TimeZone
				.getTimeZone("GMT+8:00"));
		c_year = cal.get(Calendar.YEAR);
		c_month = cal.get(Calendar.MONTH);
		c_day = cal.get(Calendar.DATE);
		datePickerDialog = new DatePickerDialog(BasicActiviy.this,
				AlertDialog.THEME_HOLO_LIGHT, onDateSetListener, c_year,
				c_month, c_day);
		datePickerDialog1 = new DatePickerDialog(BasicActiviy.this,
				AlertDialog.THEME_HOLO_LIGHT, onDateSetListener1, c_year,
				c_month, c_day);
		datePickerDialog.getDatePicker().setCalendarViewShown(false);
		datePickerDialog1.getDatePicker().setCalendarViewShown(false);
		date_str = new SimpleDateFormat("yyyy年MM月dd日")
				.format(cal.getTime());
		date_str1 = new SimpleDateFormat("yyyy年MM月dd日")
		.format(cal.getTime());
		initFinished();
	}

	private class InitThread extends Thread {
		@Override
		public void run() {
			init();
			activity_inited = true;
		}
	}

	@Override
	protected void onDestroy() {
		if (null != initTimer) {
			initTimer.cancel();
			initTimer = null;
		}
		super.onDestroy();
		// 垃圾回收
		System.gc();
	}

	/**
	 * 
	 * @Title: GoActivityWithFinishing 
	 * @Description: 跳转到指定的Activity，并结束当前Activity
	 * @param @param c 指定的class
	 * @param @param bundle  携带的参数值
	 * @return void  
	 * @throws
	 */
	public void GoActivityWithFinishing(Class c, Bundle bundle) {
		GoActivityWithOutFinishing(c, bundle);
		finish();
	}

	/**
	 * 
	 * @Title: GoActivityWithOutFinishing 
	 * @Description: 跳转到指定的Activity
	 * @param @param c
	 * @param @param bundle  
	 * @return void  
	 * @throws
	 */
	public void GoActivityWithOutFinishing(Class c, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, c);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/**
	 * 声明一个DatePickerDialog.OnDateSetListener的匿名内部类，并实例化。 该实例变量是一个监听器。 
	 */
	DatePickerDialog.OnDateSetListener onDateSetListener = 
			new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int y, int monthOfYear,
				int dayOfMonth) {
			//日期被设定后进行的处理
			afterSetDate(y, monthOfYear, dayOfMonth);
		}
	};
	DatePickerDialog.OnDateSetListener onDateSetListener1 = 
			new DatePickerDialog.OnDateSetListener() {
		 

		@Override
		public void onDateSet(DatePicker view, int y, int monthOfYear,
				int dayOfMonth) {
			//日期被设定后进行的处理
			afterSetDate1(y, monthOfYear, dayOfMonth);
		}
	};

	/** 
	 * @Title: onMeasure 
	 * @Description: 
	 * @param @param widthMeasureSpec
	 * @param @param heightMeasureSpec  
	 * @return void  
	 * @throws 
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO 自动生成的方法存根
		
	}
}
