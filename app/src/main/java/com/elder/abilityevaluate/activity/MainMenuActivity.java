package com.elder.abilityevaluate.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.config.GlobalSetting;
import com.elder.abilityevaluate.config.PreferenceParams;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.service.LogService;
import com.elder.abilityevaluate.utils.GlobalInfo;
import com.elder.abilityevaluate.utils.LocalMethod;
import com.elder.abilityevaluate.utils.MyLog;
import com.elder.abilityevaluate.widget.CustomDialog;
import com.elder.abilityevaluate.widget.CustomLoadingDialog;
import com.elder.abilityevaluate.widget.UserInfoDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class MainMenuActivity extends BasicActiviy {
	
	static final int DIALOG_LOGOUT = 0x01;
	static final int DIALOG_EXIT = 0x02;
	
	public final static int EVENT_DOWNLOAD = 0x0A;
	CustomLoadingDialog loadingDialog;
	CustomDialog confirmDialog;
	CustomDialog alertDialog;
	UserInfoDialog userInfoDialog;
	//
	LinearLayout menu1;
	//
	LinearLayout menu2;
	//档案下载
	LinearLayout menu3;
	//系统设置
	LinearLayout menu4;
	//
	LinearLayout menu5;
	//
	LinearLayout menu6;
	//
	LinearLayout menu7;
	//
	LinearLayout menu8;
	//
	LinearLayout menu9;
	//
	LinearLayout menu10;
	

	TextView userName;
	DownloadThread dowloadThread;
	int[] rights = {0,0,0,0,0,0,0,0,0,0};
	int dialogType = 0;
	String corpCode = "";
	
	SharedPreferences spf;
	
	int gradeSize = 0;
	int employeeSize = 0;
	int stationSize  = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showInitedLoading = false;
		setContentView(R.layout.main_menu_activity);
		EventBus.getDefault().register(this);
	}

	@Override
	public void init() {
		spf = getSharedPreferences(
				GlobalSetting.PREFERENCE_NAME, Context.MODE_PRIVATE);
		String rightStr = spf.getString(PreferenceParams.USER_RIGHT,
				"[]");
		corpCode = spf.getString(PreferenceParams.CORP_CODE, "");
		try {
			JSONArray jsonArray = new JSONArray(rightStr);
			String r = "";
			for(int i = 0;i<jsonArray.length();i++){
				r = jsonArray.getString(i);
				if(r.equals("001")){
					rights[0] = 1;
				}else if(r.equals("002")){
					rights[1] = 1;
					rights[2] = 1;
					rights[3] = 1;
					rights[4] = 1;
					rights[5] = 1;
					rights[6] = 1;
					rights[7] = 1;
					
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		menu1 = (LinearLayout) findViewById(R.id.menu1);
		menu2 = (LinearLayout) findViewById(R.id.menu2);
		menu3 = (LinearLayout) findViewById(R.id.menu3);
		menu4 = (LinearLayout) findViewById(R.id.menu4);
		menu5 = (LinearLayout) findViewById(R.id.menu5);
		menu6 = (LinearLayout) findViewById(R.id.menu6);
		menu7 = (LinearLayout) findViewById(R.id.menu7);
		menu8 = (LinearLayout) findViewById(R.id.menu8);
		menu9 = (LinearLayout) findViewById(R.id.menu9);
		menu10= (LinearLayout) findViewById(R.id.menu10);
		userName = (TextView) findViewById(R.id.tv_username);
		if (rights[0] == 0) {
			menu1.setClickable(false);
			menu1.setBackgroundResource(R.drawable.bg_menu_noright);
		}
		if (rights[1] == 0) {
			menu2.setClickable(false);
			menu2.setBackgroundResource(R.drawable.bg_menu_noright);
			menu3.setClickable(false);
			menu3.setBackgroundResource(R.drawable.bg_menu_noright);
			menu4.setClickable(false);
			menu4.setBackgroundResource(R.drawable.bg_menu_noright);
			menu5.setClickable(false);
			menu5.setBackgroundResource(R.drawable.bg_menu_noright);
			menu6.setClickable(false);
			menu6.setBackgroundResource(R.drawable.bg_menu_noright);
			menu9.setClickable(false);
			menu9.setBackgroundResource(R.drawable.bg_menu_noright);
			menu10.setClickable(false);
			menu10.setBackgroundResource(R.drawable.bg_menu_noright);
		}
		menu1.setOnTouchListener(menuTouchListener);
		menu2.setOnTouchListener(menuTouchListener);
		menu3.setOnTouchListener(menuTouchListener);
		menu4.setOnTouchListener(menuTouchListener);
		menu5.setOnTouchListener(menuTouchListener);
		menu6.setOnTouchListener(menuTouchListener);
		menu7.setOnTouchListener(menuTouchListener);
		menu8.setOnTouchListener(menuTouchListener);
		menu9.setOnTouchListener(menuTouchListener);
		menu10.setOnTouchListener(menuTouchListener);
	}

	@Override
	public void initFinished() {
		// TODO 设置用户姓名
		// userName
		userName.setText(spf.getString(PreferenceParams.USER_NAME, "未登录"));
		loadingDialog = new CustomLoadingDialog(this);
		userInfoDialog = new UserInfoDialog(this,new UserInfoDialog.CallBack() {
			@Override
			public void logOut() {
				dialogType = DIALOG_LOGOUT;
				confirmDialog.setType(CustomDialog.TYPE_QUESTION);
				confirmDialog.setMessage("确定注销当前登录状态？");
				confirmDialog.show();
			}
		});
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle("提示")
				.setPositiveButton(R.string.sure,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								doAfterConfirm(true);
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								doAfterConfirm(false);
							}
						});
		confirmDialog = builder.create();
		builder = new CustomDialog.Builder(this);
		builder.setTitle("提示").setNegativeButton(R.string.close,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						alertDialog.cancel();
					}
				});
		alertDialog =  builder.create();
	}

	View.OnTouchListener menuTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.menu1:
				if (rights[0] == 1) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						v.setBackgroundResource(R.drawable.bg_menu01_s);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						v.setBackgroundResource(R.drawable.bg_menu01);
					}
				}
				break;
			case R.id.menu2:
				if (rights[1] == 1) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						v.setBackgroundResource(R.drawable.bg_menu02_s);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						v.setBackgroundResource(R.drawable.bg_menu02);
					}
				}
				break;
			case R.id.menu3:
				if (rights[2] == 1) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						v.setBackgroundResource(R.drawable.bg_menu03_s);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						v.setBackgroundResource(R.drawable.bg_menu03);
					}
				}
				
				break;
			case R.id.menu4:
				if (rights[3] == 1) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						v.setBackgroundResource(R.drawable.bg_menu04_s);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						v.setBackgroundResource(R.drawable.bg_menu04);
					}
				}
				
				break;
			case R.id.menu5:
				if (rights[4] == 1) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						v.setBackgroundResource(R.drawable.bg_menu01_s);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						v.setBackgroundResource(R.drawable.bg_menu01);
					}
				}
				break;
			case R.id.menu6:
				if (rights[5] == 1) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						v.setBackgroundResource(R.drawable.bg_menu02_s);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						v.setBackgroundResource(R.drawable.bg_menu02);
					}
				}
				break;
			case R.id.menu7:
				if (rights[6] == 1) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						v.setBackgroundResource(R.drawable.bg_menu03_s);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						v.setBackgroundResource(R.drawable.bg_menu03);
					}
				}
				break;
			case R.id.menu8:
				if (rights[7] == 1) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						v.setBackgroundResource(R.drawable.bg_menu04_s);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						v.setBackgroundResource(R.drawable.bg_menu04);
					}
				}
				break;
			case R.id.menu9:
				if(rights[8] == 1) {
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						v.setBackgroundResource(R.drawable.bg_menu03_s);
					} else if(event.getAction() == MotionEvent.ACTION_UP){
						v.setBackgroundResource(R.drawable.bg_menu03);
					}
				}
				break;
			case R.id.menu10:
				if(rights[9] == 1) {
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						v.setBackgroundResource(R.drawable.bg_menu03_s);
					} else if(event.getAction() == MotionEvent.ACTION_UP){
						v.setBackgroundResource(R.drawable.bg_menu03);
					}
				}
				break;
			default:
				break;
			}
			return false;
		}
	};
	
	/** 
	* @Title: logOut 
	* @Description: 注销
	* @return void    返回类型 
	* @throws 
	*/
	public void logOut(View v){
		userInfoDialog.show();
	}

	public void menuClick(View v) {
		switch (v.getId()) {
		case R.id.menu1:
			System.out.println("rights==="+validateGrade());
			if (rights[0] == 1) {
				if(validateGrade()){
					//GoActivityWithOutFinishing(Check.class, null);
				}else{
					alertDialog.setType(CustomDialog.TYPE_WARNNING);
					alertDialog.setMessage("请先下载档案信息");
					alertDialog.show();
				}
			}
			break;
		case R.id.menu2:
			if (rights[1] == 1) {
				if(validateGrade()){
					//GoActivityWithOutFinishing(Weight.class, null);
				}else{
					alertDialog.setType(CustomDialog.TYPE_WARNNING);
					alertDialog.setMessage("请先下载档案信息");
					alertDialog.show();
				}
			}
			break;
		case R.id.menu3:
			if(validateGrade()){
				//GoActivityWithOutFinishing(LeafChangeGradeActivity.class, null);
			}else{
				alertDialog.setType(CustomDialog.TYPE_WARNNING);
				alertDialog.setMessage("请先下载档案信息");
				alertDialog.show();
			}
			break;
		case R.id.menu4:
			if(validateGrade()){
				//GoActivityWithOutFinishing(StoreExportActivity.class, null);
			}else{
				alertDialog.setType(CustomDialog.TYPE_WARNNING);
				alertDialog.setMessage("请先下载档案信息");
				alertDialog.show();
			}
			break;
		case R.id.menu5:
			if(validateGrade()){
				//GoActivityWithOutFinishing(StoreCheckActivity.class, null);
			}else{
				alertDialog.setType(CustomDialog.TYPE_WARNNING);
				alertDialog.setMessage("请先下载档案信息");
				alertDialog.show();
			}
			break;
		case R.id.menu6:
			if(validateGrade()){
				//GoActivityWithOutFinishing(StoreMoveActivity.class, null);
			}else{
				alertDialog.setType(CustomDialog.TYPE_WARNNING);
				alertDialog.setMessage("请先下载档案信息");
				alertDialog.show();
			}
			break;
		case R.id.menu7:
			if(dowloadThread!=null){
				return;
			}else{
//				检查网络是否开启
				if(!GlobalInfo.isInternetAvailable(this)){
					alertDialog.setMessage("网络连接异常，不能进行档案下载！");
					alertDialog.show();
					return;
				}
				dowloadThread = new DownloadThread();
				dowloadThread.start();
				loadingDialog.setMsg("正在下载等级档案，请稍候");
				loadingDialog.showLoading();
			}
			break;
		case R.id.menu8:
			GoActivityWithOutFinishing(SettingActivity.class, null);
			break;
		case R.id.menu9:
			if(validateGrade()){
				//GoActivityWithOutFinishing(StoreSumActivity.class, null);
				break;
			}
		case R.id.menu10:
			if(validateGrade()){
				//GoActivityWithOutFinishing(ChangeGradeActivity.class, null);
			}else{
				alertDialog.setType(CustomDialog.TYPE_WARNNING);
				alertDialog.setMessage("请先下载档案信息");
				alertDialog.show();
			}
			break;
		default:
			break;
		}
	}

	private boolean validateGrade(){

		return true;

	}

	private void doAfterConfirm(boolean flag) {
		confirmDialog.dismiss();
		switch (dialogType) {
		case DIALOG_EXIT:
			if (flag) {
				Intent stateService = new Intent(this,
						LogService.class);
				this.stopService(stateService);
				System.exit(-1);
			} else {
			}
			break;
		case DIALOG_LOGOUT:
			if (flag) {
				userInfoDialog.dismiss();
				Intent stateService = new Intent(this,
						LogService.class);
				this.stopService(stateService);
				GoActivityWithFinishing(LoginActivity.class, null);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialogType = DIALOG_EXIT;
			confirmDialog.setType(CustomDialog.TYPE_QUESTION);
			confirmDialog.setMessage("确定退出本系统吗？");
			confirmDialog.show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	class DownloadThread extends Thread {
		@Override
		public void run() {
			LocalMethod localMethod = new LocalMethod(MainMenuActivity.this);
			Map<String, Object> result = new HashMap<String, Object>();
			Event.MsgEvent event = new Event.MsgEvent(EVENT_DOWNLOAD, result);
			event.fromClass = MainMenuActivity.class;

			String employes = localMethod.getArcEmployees(corpCode);
			MyLog.i("employes", employes);
			if(employes.equals("-1")){
				result.put("result", "-1");
				EventBus.getDefault().post(event);
			}else{
				result.put("result", "2");
				result.put("data", employes);
				EventBus.getDefault().post(event);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}

	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == EVENT_DOWNLOAD
				&& msgEvent.fromClass == MainMenuActivity.class) {
			String result = (String)msgEvent.values.get("result");
			String data = (String)msgEvent.values.get("data");
			if (result.equals("-1")) { 
				dowloadThread = null;
				loadingDialog.hideLoading();
				alertDialog.setType(CustomDialog.TYPE_ERROR);
				alertDialog.setMessage("连接服务器异常，下载失败！");
				alertDialog.show();
			} else if (result.equals("1")) {
				//gradeSize = ArcGrade.insertByJson(this, data);
				loadingDialog.setMsg("正在下载职工档案，请稍候");
			} else if (result.equals("2")){
				//employeeSize = BaseInformationListActivity.insertByJson(this, data);
				loadingDialog.setMsg("正在下载单位档案，请稍候");
			} else if (result.equals("3")) {
				loadingDialog.hideLoading();
				dowloadThread = null;
				//stationSize = ArcStation.insertByJson(this, data);
				alertDialog.setMessage("下载等级 "+gradeSize+" 条。\n职工档案 "+employeeSize+" 条。 \n单位档案 "+stationSize+" 条。");
				alertDialog.show();
			}
		}
	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
}
