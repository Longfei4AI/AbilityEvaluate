package com.elder.abilityevaluate.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.config.GlobalSetting;
import com.elder.abilityevaluate.config.PreferenceParams;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.utils.FileUtil;
import com.elder.abilityevaluate.utils.GlobalInfo;
import com.elder.abilityevaluate.utils.SystemUpdate;
import com.elder.abilityevaluate.widget.AboutDialog;
import com.elder.abilityevaluate.widget.CustomDialog;
import com.elder.abilityevaluate.widget.CustomLoadingDialog;
import com.elder.abilityevaluate.widget.CustomToast;

import de.greenrobot.event.EventBus;

public class SettingActivity extends BasicActiviy {
	static final int DIALOG_NEW_VERSION = 0x01;

	private static final String PACKAGE_SAVE_PATH = GlobalInfo.getSdcardPath()
			+ GlobalSetting.SAVE_APKPATH + GlobalSetting.APK_NAME;

	boolean fromLogin = false;
	AboutDialog aboutDialog;
	EditText ipET;
	EditText portET;

	TextView versionTV;

	CustomLoadingDialog customLoadingDialog;
	CustomDialog alertDialog;
	CustomDialog confirmDialog;
	int dialogType = 0;

	// 下载
	private ProgressDialog dowbpBar = null;

	SystemUpdate.DownProcessThread downT;
	SystemUpdate.CheckUpdateThread checkUpdateThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null) {
			String flag = intent.getExtras().getString("fromActivity");
			if (flag.equals("LoginActivity")) {
				fromLogin = true;
			}
		}
		showInitedLoading = false;
		setContentView(R.layout.setting_activity);
		EventBus.getDefault().register(this);
	}

	@Override
	public void init() {
		ipET = (EditText) findViewById(R.id.et_ipaddress);
		portET = (EditText) findViewById(R.id.et_port);
		versionTV = (TextView) findViewById(R.id.tv_version_name);
	}

	@Override
	public void initFinished() {
		aboutDialog = new AboutDialog(this);
		customLoadingDialog = new CustomLoadingDialog(this);
		
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
		SharedPreferences spf = getSharedPreferences(
				GlobalSetting.PREFERENCE_NAME, Context.MODE_PRIVATE);
		ipET.setText(spf.getString(PreferenceParams.SERVER_IP,
				GlobalSetting.DEFAULT_SERVER_IP));
		portET.setText(spf.getString(PreferenceParams.SERVER_PORT,
				GlobalSetting.DEFAULT_SERVER_PORT));
		versionTV.setText("当前版本：" + GlobalInfo.getVersionName(this));

		dowbpBar = new ProgressDialog(SettingActivity.this,AlertDialog.THEME_HOLO_LIGHT);
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
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back(null);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void back(View v) {
		if (!fromLogin) {
			GoActivityWithFinishing(MainListActivity.class, null);
		} else {
			GoActivityWithFinishing(LoginActivity.class, null);
		}
	}

	public void save(View v) {
		String ip = ipET.getText().toString();
		String port = portET.getText().toString();
		if (ip.equals("")) {
			CustomToast.show(this, "服务器地址不能为空", Toast.LENGTH_SHORT);
			return;
		}
		SharedPreferences spf = getSharedPreferences(
				GlobalSetting.PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = spf.edit();
		editor.putString(PreferenceParams.SERVER_IP, ip);
		editor.putString(PreferenceParams.SERVER_PORT, port);
		editor.commit();
		CustomToast.show(this, "保存成功", Toast.LENGTH_SHORT);
	}

	public void revert(View v) {
		ipET.setText(GlobalSetting.DEFAULT_SERVER_IP);
		portET.setText(GlobalSetting.DEFAULT_SERVER_PORT);
	}

	public void update(View v) {
		checkUpdate();
	}

	public void about(View v) {
		aboutDialog.show();
	}

	/**
	 * 检查更新
	 */
	private void checkUpdate() {
		// 检测链接是否可用
		if (!GlobalInfo.isInternetAvailable(SettingActivity.this)) {
			CustomToast.show(this, "没有可用网络，请打开wifi或数据连接！ ", Toast.LENGTH_SHORT);
			return;
		}
		customLoadingDialog.setTitle("在线升级");
		customLoadingDialog.setMsg("正在检测更新，请稍候...");
		customLoadingDialog.show();
		checkUpdateThread = new SystemUpdate.CheckUpdateThread(this);
		checkUpdateThread.start();

	}

	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == SystemUpdate.EVENT_FIND
				&& msgEvent.fromClass == SettingActivity.class) {
			customLoadingDialog.dismiss();
			checkUpdateThread = null;
			String result = (String)msgEvent.values.get("result");
			if(result.equals("3")){
				alertDialog.setType(CustomDialog.TYPE_ERROR);
				alertDialog.setMessage("连接服务器失败！\n请检查网络和服务器参数设置！");
				alertDialog.show();
			}else if(result.equals("1")){
				dialogType = DIALOG_NEW_VERSION;
				confirmDialog.setType(CustomDialog.TYPE_QUESTION);
				confirmDialog.setMessage("检测到新版本，是否更新?");
				confirmDialog.show();
			}else if(result.equals("2")){
				alertDialog.setType(CustomDialog.TYPE_INFO);
				alertDialog.setMessage("当前是最新版本，无需更新！");
				alertDialog.show();
			}
		}else if (msgEvent.type == SystemUpdate.EVENT_DOWN
				&& msgEvent.fromClass == SettingActivity.class) {
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
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
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
