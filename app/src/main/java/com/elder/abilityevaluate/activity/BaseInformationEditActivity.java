package com.elder.abilityevaluate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.utils.SystemUpdate;
import com.elder.abilityevaluate.widget.CustomDialog;
import com.elder.abilityevaluate.widget.CustomLoadingDialog;
import com.elder.abilityevaluate.widget.CustomToast;

import de.greenrobot.event.EventBus;

public class BaseInformationEditActivity extends BasicActiviy {
	public static final int DIALOG_NEW_VERSION = 0x01;
	private EditText a_1_1_et;
	private EditText a_1_2_et;
	private EditText a_1_3_et;
	private EditText a_1_4_et;
	private EditText a_1_5_et;
	private CustomLoadingDialog customLoadingDialog;
	private CustomDialog alertDialog;
	private CustomDialog confirmDialog;
	private int dialogType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		showInitedLoading = false;
		setContentView(R.layout.base_information_edit_activity);
		EventBus.getDefault().register(this);
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/8 11:42
	* @Desc: 初始化工作
	* @Params:
	* @Return: void
	*/
	@Override
	public void init() {
		a_1_1_et = (EditText) findViewById(R.id.a_1_1_et);
		a_1_2_et = (EditText) findViewById(R.id.a_1_2_et);
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/8 11:42
	* @Desc: 初始化完毕
	* @Params:
	* @Return: void
	*/
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
	/**
	* @Author: wlf
	* @Time: 2018/2/8 11:37
	* @Desc: 处理返回时间
	* @Params: v
	* @Return: void
	*/
	public void back(View v) {
		GoActivityWithFinishing(BaseInformationListActivity.class, null);
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/8 11:37
	* @Desc: 处理保存时间
	* @Params: v
	* @Return: void
	*/
	public void save(View v) {
		CustomToast.show(this, "保存成功", Toast.LENGTH_SHORT);
		GoActivityWithFinishing(BaseInformationViewActivity.class, null);
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/8 11:41
	* @Desc: EventBus事件接收
	* @Params:
	* @Return: void
	*/
	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == SystemUpdate.EVENT_FIND
				&& msgEvent.fromClass == BaseInformationEditActivity.class) {
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
				&& msgEvent.fromClass == BaseInformationEditActivity.class) {
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
