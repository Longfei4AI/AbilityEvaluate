package com.elder.abilityevaluate.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.config.GlobalSetting;
import com.elder.abilityevaluate.config.PreferenceParams;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.service.LogService;
import com.elder.abilityevaluate.utils.DateUtils;
import com.elder.abilityevaluate.utils.FileUtil;
import com.elder.abilityevaluate.utils.GlobalInfo;
import com.elder.abilityevaluate.utils.LocalMethod;
import com.elder.abilityevaluate.utils.SystemUpdate;
import com.elder.abilityevaluate.widget.CustomDialog;
import com.elder.abilityevaluate.widget.CustomLoadingDialog;
import com.elder.abilityevaluate.widget.CustomToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class LoginActivity extends BasicActiviy {
	
	public final static int EVENT_CHECKUSER = 0x01;
	
	static final int DIALOG_NEW_VERSION = 0x01;
	
	EditText userET;
	EditText pwdET;

	TextView msgTV;

	String userName;
	String pwd;

	CustomLoadingDialog loadingDialog;
	CustomDialog alertDialog;
	CustomDialog confirmDialog;
	int dialogType = 0;

	// 下载
	private ProgressDialog dowbpBar = null;

	SystemUpdate.DownProcessThread downT;
	SystemUpdate.CheckUpdateThread checkUpdateThread;

	private long mExitTime;
	
	private boolean netOff = false;
	
	private static final String PACKAGE_SAVE_PATH = GlobalInfo.getSdcardPath()
			+ GlobalSetting.SAVE_APKPATH + GlobalSetting.APK_NAME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showInitedLoading = false;
		setContentView(R.layout.login_activity);
		loadingDialog = new CustomLoadingDialog(this);
		// 日志监听服务
		Intent stateService = new Intent(this,
				LogService.class);
		startService(stateService);
		EventBus.getDefault().register(this);
	}

	@Override
	public void init() {
		userET = (EditText) findViewById(R.id.et_user);
		pwdET = (EditText) findViewById(R.id.et_pwd);
		msgTV = (TextView) findViewById(R.id.tv_error);
	}

	@Override
	public void initFinished() {
		SharedPreferences spf = getSharedPreferences(
				GlobalSetting.PREFERENCE_NAME,
				Context.MODE_PRIVATE);
		userET.setText(spf.getString(PreferenceParams.LAST_USER_NAME, ""));
		pwdET.setText(spf.getString(PreferenceParams.LAST_PWD, ""));
		pwdET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				switch (actionId) {
				case EditorInfo.IME_ACTION_GO:
					login(null);
					break;
				case EditorInfo.IME_ACTION_SEND:
					login(null);
					break;
				default:
					break;
				}
				return false;
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
		dowbpBar = new ProgressDialog(LoginActivity.this,AlertDialog.THEME_HOLO_LIGHT);
		dowbpBar.setTitle("正在下载");
		dowbpBar.setMessage("请稍候...");
		dowbpBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dowbpBar.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						if(downT!=null){
							downT.setInterrupt(true);
						}
					}
				});
		// 不可通过返回取消正在下载的任务
		dowbpBar.setCancelable(false);
		//checkUpdate();
	}

	/**
	 * @Title: login
	 * @Description: 登录
	 * @return void 返回类型
	 * @throws
	 */
	public void login(View v) {
		msgTV.setVisibility(View.INVISIBLE);
		userName = userET.getText() + "";
		pwd = pwdET.getText() + "";
		boolean error = false;
		msgTV.setClickable(false);
		if (userName.trim().equals("")) {
			msgTV.setText(R.string.error_login_01);
			error = true;
		} else if (pwd.equals("")) {
			msgTV.setText(R.string.error_login_02);
			error = true;
		} else if (!GlobalInfo.isInternetAvailable(this)) {
			msgTV.setClickable(true);
			netOff = true;
			msgTV.setText(R.string.error_global_net_off);
			error = true;
		}
		if (error) {
			msgTV.setVisibility(View.VISIBLE);
			return;
		}
		loadingDialog.setMsg(R.string.msg_landing);
		loadingDialog.showLoading();
		new CheckNameThread().start();
	}

	/**
	 * @Title: setNet
	 * @Description: 设置网络
	 * @return void 返回类型
	 * @throws
	 */
	public void setNet(View v) {
		if(netOff){
			Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
			startActivity(intent);
		}else{
			setting(null);
		}
	}

	public void setting(View v) {
		Bundle bundle = new Bundle();
		bundle.putString("fromActivity", "LoginActivity");
		GoActivityWithOutFinishing(SettingActivity.class, bundle);
	}

	class CheckNameThread extends Thread {
		@Override
		public void run() {
			LocalMethod localMethod = new LocalMethod(LoginActivity.this);
			//通过Webservice到服务器验证
			//String rstr = localMethod.login(userName, pwd);
			//本地验证
			JSONObject json = new JSONObject();
			try{
				json.put("result", "0");
				json.put("userId", "123456789");
				json.put("userName", "评估员");
				json.put("corpCode", "0000");
				json.put("corpName", "0000");
				json.put("date", DateUtils.getCurDate());
				json.put("right", new String[]{});
			}catch (JSONException e){
				e.printStackTrace();
			}
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("result", json.toString());
			Event.MsgEvent event = new Event.MsgEvent(EVENT_CHECKUSER, result);
			EventBus.getDefault().post(event);
		}
	}
	
	/**
	 * 检查更新
	 */
	private void checkUpdate() {
		// 检测链接是否可用
		if (!GlobalInfo.isInternetAvailable(LoginActivity.this)) {
			CustomToast.show(this, "检测新版本失败 ", Toast.LENGTH_SHORT);
			return;
		}
		loadingDialog.setTitle("在线升级");
		loadingDialog.setMsg("正在检测更新，请稍候");
		loadingDialog.show();
		checkUpdateThread = new SystemUpdate.CheckUpdateThread(this);
		checkUpdateThread.start();

	}

	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == EVENT_CHECKUSER) {
			loadingDialog.hideLoading();
			Map<String, Object> value = msgEvent.values;
			String resultStr = (String) value.get("result");
			boolean error = false;
			if (resultStr.equals("-1")) {
				msgTV.setText(R.string.error_login_FF);
				netOff = false;
				msgTV.setClickable(true);
				error = true;
			} else {
				try {
					JSONObject jsonObject = new JSONObject(resultStr);
					String resultCode = ""+jsonObject.getInt("result");
					if (resultCode.equals("1")) {
						msgTV.setText(R.string.error_login_03);
						error = true;
					} else if (resultCode.equals("2")) {
						msgTV.setText(R.string.error_login_04);
						error = true;
					} else if (resultCode.equals("0")) {
						GlobalInfo.userName=userName;
						GlobalInfo.userRealName=jsonObject.getString("userName");
						// 登录成功
						error = false;
						SharedPreferences spf = getSharedPreferences(
								GlobalSetting.PREFERENCE_NAME,
								Context.MODE_PRIVATE);
						Editor editor = spf.edit();
						editor.putString(PreferenceParams.USER_ID, userName);
						editor.putString(PreferenceParams.USER_PWD, pwd);
						editor.putString(PreferenceParams.LAST_USER_NAME,
								userName);
						editor.putString(PreferenceParams.LAST_PWD, pwd);
						editor.putString(PreferenceParams.USER_NAME,
								jsonObject.getString("userName"));
						editor.putString(PreferenceParams.CORP_CODE,
								jsonObject.getString("corpCode"));
						editor.putString(PreferenceParams.CORP_NAME,
								jsonObject.getString("corpName"));
						editor.putString(PreferenceParams.USER_RIGHT,
								jsonObject.getString("right"));
						editor.putString(PreferenceParams.LOGIN_TIME,
								DateUtils.getCurDateTime());
						editor.commit();
						//GoActivityWithFinishing(MainMenuActivity.class, null);
						GoActivityWithFinishing(MainListActivity.class, null);
					}
				} catch (JSONException e) {
					error = true;
					netOff = false;
					msgTV.setClickable(true);
					msgTV.setText(R.string.error_login_FF);
				}
			}

			if (error) {
				msgTV.setVisibility(View.VISIBLE);
				return;
			}
		}else if (msgEvent.type == SystemUpdate.EVENT_FIND
				&& msgEvent.fromClass == LoginActivity.class) {
			loadingDialog.dismiss();
			checkUpdateThread = null;
			String result = (String)msgEvent.values.get("result");
			if(result.equals("3")){
				CustomToast.show(this,"连接服务器失败！\n请检查网络和服务器参数设置！",Toast.LENGTH_SHORT);
			}else if(result.equals("1")){
				dialogType = DIALOG_NEW_VERSION;
				confirmDialog.setType(CustomDialog.TYPE_QUESTION);
				confirmDialog.setMessage("检测到新版本，是否更新?");
				confirmDialog.show();
			}else if(result.equals("2")){
			}
		}else if (msgEvent.type == SystemUpdate.EVENT_DOWN
				&& msgEvent.fromClass == LoginActivity.class) {
			String result = (String)msgEvent.values.get("result");
			if(result.equals("1")){
				downT = null;
				dowbpBar.cancel();
				// 安装更新包
				SystemUpdate.installAPK(this,PACKAGE_SAVE_PATH);
			}else if(result.equals("2")){
				int progress = (Integer)msgEvent.values.get("progress");
				dowbpBar.setProgress(progress);
			}else if(result.equals("3")){
				dowbpBar.cancel();
				downT = null;
				FileUtil.deleteFile(PACKAGE_SAVE_PATH);
			}else if(result.equals("0")){
				alertDialog.setType(CustomDialog.TYPE_ERROR);
				alertDialog.setMessage("下载安装包失败！");
				alertDialog.show();
				dowbpBar.cancel();
				downT = null;
				FileUtil.deleteFile(PACKAGE_SAVE_PATH);
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - mExitTime > 3000) {
				CustomToast.show(this, R.string.msg_exit_one_more_press,
						Toast.LENGTH_SHORT);
				mExitTime = System.currentTimeMillis();
			} else {
				// 停止日志监听服务
				Intent stateService = new Intent(this,
						LogService.class);
				this.stopService(stateService);
				this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	private void doAfterConfirm(boolean flag) {
		confirmDialog.dismiss();
		switch (dialogType) {
		case DIALOG_NEW_VERSION:
			if (flag) {
				dowbpBar.setProgress(0);
				dowbpBar.show();
				downT = new SystemUpdate.DownProcessThread(this,PACKAGE_SAVE_PATH);
				downT.start();
			}
			break;
		default:
			break;
		}
	}
}
