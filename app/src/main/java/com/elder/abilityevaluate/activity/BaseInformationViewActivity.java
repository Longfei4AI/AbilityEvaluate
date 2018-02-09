package com.elder.abilityevaluate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.utils.SystemUpdate;
import com.elder.abilityevaluate.widget.CustomDialog;
import com.elder.abilityevaluate.widget.CustomLoadingDialog;

import de.greenrobot.event.EventBus;

public class BaseInformationViewActivity extends BasicActiviy {
	public static final int DIALOG_NEW_VERSION = 0x01;
	private EditText ipET;
	private EditText portET;
	private TextView versionTV;
	private CustomLoadingDialog customLoadingDialog;
	private CustomDialog alertDialog;
	private CustomDialog confirmDialog;
	private int dialogType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();

		showInitedLoading = false;
		setContentView(R.layout.base_information_view_activity);
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
		GoActivityWithFinishing(BaseInformationListActivity.class, null);
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/7 20:55
	* @Desc: 修改基本信息
	* @Params: v
	* @Return: void
	*/
	public void edit(View v) {
		GoActivityWithFinishing(BaseInformationEditActivity.class, null);
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/7 20:56
	* @Desc: 开始能力评估
	* @Params: v
	* @Return: void
	*/
	public void evaluate(View v) {
		GoActivityWithFinishing(EvaluationEditActivity.class, null);
	}


	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == SystemUpdate.EVENT_FIND
				&& msgEvent.fromClass == BaseInformationViewActivity.class) {
			customLoadingDialog.dismiss();
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
				&& msgEvent.fromClass == BaseInformationViewActivity.class) {
			String result = (String)msgEvent.values.get("result");
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

			}
			break;
		default:
			break;
		}
	}
}
