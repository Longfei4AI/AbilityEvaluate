package com.elder.abilityevaluate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.StringBuilderPrinter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.entity.BaseInformation;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.utils.DataBaseHelper;
import com.elder.abilityevaluate.utils.SystemUpdate;
import com.elder.abilityevaluate.widget.CustomDialog;
import com.elder.abilityevaluate.widget.CustomLoadingDialog;
import com.elder.abilityevaluate.widget.CustomToast;
import com.lidroid.xutils.db.sqlite.Selector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.greenrobot.event.EventBus;

public class BaseInformationViewActivity extends BasicActiviy {
	public static final int DIALOG_NEW_VERSION = 0x01;
	private TextView a_1_1_tv;
	private TextView a_1_2_tv;
	private TextView a_1_3_tv;

	private TextView a_2_1_tv;
	private TextView a_2_2_tv;
	private TextView a_2_3_tv;
	private TextView a_2_4_tv;
	private TextView a_2_5_tv;
	private TextView a_2_6_tv;
	private TextView a_2_7_tv;
	private TextView a_2_8_tv;
	private TextView a_2_9_tv;
	private TextView a_2_10_tv;
	private TextView a_2_11_tv;
	private TextView a_2_12_tv;
	private TextView a_2_13_1_tv;
	private TextView a_2_13_2_tv;
	private TextView a_2_13_3_tv;
	private TextView a_2_14_1_tv;
	private TextView a_2_14_2_tv;
	private TextView a_2_14_3_tv;
	private TextView a_2_14_4_tv;
	private TextView a_2_14_5_tv;

	private TextView a_3_1_tv;
	private TextView a_3_2_tv;
	private TextView a_3_3_tv;
	private TextView a_3_4_tv;

	private ImageView picture_front_iv;
	private ImageView picture_back_iv;
	private ImageView picture_full_iv;

	private CustomLoadingDialog customLoadingDialog;
	private CustomDialog alertDialog;
	private CustomDialog confirmDialog;
	private int dialogType = 0;
	private String baseId = null;
	private BaseInformation baseInfo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		baseId = intent.getExtras().getString("id");
		showInitedLoading = false;
		setContentView(R.layout.base_information_view_activity);
		EventBus.getDefault().register(this);
	}

	@Override
	public void init() {
		a_1_1_tv = findViewById(R.id.a_1_1_tv);
		a_1_2_tv = findViewById(R.id.a_1_2_tv);
		a_1_3_tv = findViewById(R.id.a_1_3_tv);
		a_2_1_tv = findViewById(R.id.a_2_1_tv);
		a_2_2_tv = findViewById(R.id.a_2_2_tv);
		a_2_3_tv = findViewById(R.id.a_2_3_tv);
		a_2_4_tv = findViewById(R.id.a_2_4_tv);
		a_2_5_tv = findViewById(R.id.a_2_5_tv);
		a_2_6_tv = findViewById(R.id.a_2_6_tv);
		a_2_7_tv = findViewById(R.id.a_2_7_tv);
		a_2_8_tv = findViewById(R.id.a_2_8_tv);
		a_2_9_tv = findViewById(R.id.a_2_9_tv);
		a_2_10_tv = findViewById(R.id.a_2_10_tv);
		a_2_11_tv = findViewById(R.id.a_2_11_tv);
		a_2_12_tv = findViewById(R.id.a_2_12_tv);
		a_2_13_1_tv = findViewById(R.id.a_2_13_1_tv);
		a_2_13_2_tv = findViewById(R.id.a_2_13_2_tv);
		a_2_13_3_tv = findViewById(R.id.a_2_13_3_tv);
		a_2_14_1_tv = findViewById(R.id.a_2_14_1_tv);
		a_2_14_2_tv = findViewById(R.id.a_2_14_2_tv);
		a_2_14_3_tv = findViewById(R.id.a_2_14_3_tv);
		a_2_14_4_tv = findViewById(R.id.a_2_14_4_tv);
		a_2_14_5_tv = findViewById(R.id.a_2_14_5_tv);
		a_3_1_tv = findViewById(R.id.a_3_1_tv);
		a_3_2_tv = findViewById(R.id.a_3_2_tv);
		a_3_3_tv = findViewById(R.id.a_3_3_tv);
		a_3_4_tv = findViewById(R.id.a_3_4_tv);
		picture_front_iv = findViewById(R.id.picture_front);
		picture_back_iv = findViewById(R.id.picture_back);
		picture_full_iv = findViewById(R.id.picture_full);
		this.initData();
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/10 10:13
	* @Desc: 为TextView填充数据
	* @Params: 
	* @Return: void
	*/
	private void initData(){
		baseInfo = DataBaseHelper.getInstance(this,BaseInformation.class).
				findFirst(Selector.from(BaseInformation.class).where("baseInfoId","=",baseId));
		if(null == baseInfo){
			CustomToast.show(this,"获取数据失败！",CustomToast.LENGTH_SHORT);
			return;
		}
		a_1_1_tv.setText(baseInfo.getA_1_1());
		a_1_2_tv.setText(baseInfo.getA_1_2());

		String a_1_3 = baseInfo.getA_1_3() == null ? "" : baseInfo.getA_1_3();
		switch (a_1_3){
			case "1":
				a_1_3_tv.setText(getString(R.string.a_1_3_1).substring(2));
				break;
			case "2":
				a_1_3_tv.setText(getString(R.string.a_1_3_2).substring(2));
				break;
			case "3":
				a_1_3_tv.setText(getString(R.string.a_1_3_3).substring(2));
				break;
			case "4":
				a_1_3_tv.setText(getString(R.string.a_1_3_4).substring(2));
				break;
		}


		a_2_1_tv.setText(baseInfo.getA_2_1());

		String a_2_2 = baseInfo.getA_2_2() == null ? "" : baseInfo.getA_2_2();
		switch (a_2_2){
			case "1":
				a_2_2_tv.setText(getString(R.string.a_2_2_1));
				break;
			case "2" :
				a_2_2_tv.setText(getString(R.string.a_2_2_2));
				break;
		}

		a_2_3_tv.setText(baseInfo.getA_2_3());
		a_2_4_tv.setText(baseInfo.getA_2_4());
		a_2_5_tv.setText(baseInfo.getA_2_5());
		String a_2_6 = baseInfo.getA_2_6() == null ? "" : baseInfo.getA_2_6();
		switch (a_2_6){
			case "1":
				a_2_6_tv.setText(getString(R.string.a_2_6_1_));
				break;
			case "2" :
				String a_2_6Str = ( baseInfo.getA_2_6_1() == null || baseInfo.getA_2_6_1().equals("") ) ?
						getString(R.string.a_2_6_2_) : baseInfo.getA_2_6_1();
				a_2_6_tv.setText(a_2_6Str);
				break;
		}
		String a_2_7 = baseInfo.getA_2_7() == null ? "" : baseInfo.getA_2_7();
		switch (a_2_7){
			case "1":
				a_2_7_tv.setText(getString(R.string.a_2_7_1));
				break;
			case "2" :
				a_2_7_tv.setText(getString(R.string.a_2_7_2));
				break;
			case "3" :
				a_2_7_tv.setText(getString(R.string.a_2_7_3));
				break;
			case "4" :
				a_2_7_tv.setText(getString(R.string.a_2_7_4));
				break;
			case "5" :
				a_2_7_tv.setText(getString(R.string.a_2_7_5));
				break;
			case "6" :
				a_2_7_tv.setText(getString(R.string.a_2_7_6));
				break;
		}

		String a_2_8 = baseInfo.getA_2_8() == null ? "" : baseInfo.getA_2_8();
		switch (a_2_8){
			case "1":
				a_2_8_tv.setText(getString(R.string.a_2_8_1_));
				break;
			case "2" :
				a_2_8_tv.setText(baseInfo.getA_2_8_1());
				break;
		}

		String a_2_9 = baseInfo.getA_2_9() == null ? "" : baseInfo.getA_2_9();
		switch (a_2_9){
			case "1":
				a_2_9_tv.setText(getString(R.string.a_2_9_1));
				break;
			case "2" :
				a_2_9_tv.setText(getString(R.string.a_2_9_2));
				break;
			case "3" :
				a_2_9_tv.setText(getString(R.string.a_2_9_3));
				break;
			case "4" :
				a_2_9_tv.setText(getString(R.string.a_2_9_4));
				break;
			case "5" :
				a_2_9_tv.setText(getString(R.string.a_2_9_5));
				break;
		}

		String a_2_10 = baseInfo.getA_2_10() == null ? "" : baseInfo.getA_2_10();
		switch (a_2_10){
			case "1":
				a_2_10_tv.setText(getString(R.string.a_2_10_1));
				break;
			case "2" :
				a_2_10_tv.setText(getString(R.string.a_2_10_2));
				break;
			case "3" :
				a_2_10_tv.setText(getString(R.string.a_2_10_3));
				break;
			case "4" :
				a_2_10_tv.setText(getString(R.string.a_2_10_4));
				break;
			case "5" :
				a_2_10_tv.setText(getString(R.string.a_2_10_5));
				break;
			case "6" :
				a_2_10_tv.setText(getString(R.string.a_2_10_6));
				break;
			case "7" :
				a_2_10_tv.setText(getString(R.string.a_2_10_7));
				break;
			case "8" :
				a_2_10_tv.setText(getString(R.string.a_2_10_8));
				break;
		}

		String a_2_11 = baseInfo.getA_2_11() == null ? "" : baseInfo.getA_2_11();
		StringBuffer strBuff_2_11 =  new StringBuffer();
		if(a_2_11.contains("1")) strBuff_2_11.append(getString(R.string.a_2_11_1_)).append(";");
		if(a_2_11.contains("2")) strBuff_2_11.append(getString(R.string.a_2_11_2)).append(";");
		if(a_2_11.contains("3")) strBuff_2_11.append(getString(R.string.a_2_11_3)).append(";");
		if(a_2_11.contains("4")) strBuff_2_11.append(getString(R.string.a_2_11_4)).append(";");
		if(a_2_11.contains("5")) strBuff_2_11.append(getString(R.string.a_2_11_5)).append(";");
		if(a_2_11.contains("6")) strBuff_2_11.append(getString(R.string.a_2_11_6)).append(";");
		if(a_2_11.contains("7")) strBuff_2_11.append(getString(R.string.a_2_11_7)).append(";");
		if(a_2_11.contains("8")) strBuff_2_11.append(baseInfo.getA_2_11_1()).append(";");
		a_2_11_tv.setText(strBuff_2_11.toString());

		String a_2_12 = baseInfo.getA_2_12() == null ? "" : baseInfo.getA_2_12();
		StringBuffer strBuff_a_2_12 = new StringBuffer();
		if(a_2_12.contains("1")) strBuff_a_2_12.append(getString(R.string.a_2_12_1)).append(";");
		if(a_2_12.contains("2")) strBuff_a_2_12.append(getString(R.string.a_2_12_2)).append(";");
		if(a_2_12.contains("3")) strBuff_a_2_12.append(getString(R.string.a_2_12_3)).append(";");
		if(a_2_12.contains("4")) strBuff_a_2_12.append(getString(R.string.a_2_12_4)).append(";");
		a_2_12_tv.setText(strBuff_a_2_12.toString());

		String a_2_13_1 = baseInfo.getA_2_13_1() == null ? "" : baseInfo.getA_2_13_1();
		switch (a_2_13_1){
			case "1":
				a_2_13_1_tv.setText(getString(R.string.a_2_13_1_1));
				break;
			case "2" :
				a_2_13_1_tv.setText(getString(R.string.a_2_13_1_2));
				break;
			case "3" :
				a_2_13_1_tv.setText(getString(R.string.a_2_13_1_3));
				break;
			case "4" :
				a_2_13_1_tv.setText(getString(R.string.a_2_13_1_4));
				break;
		}

		String a_2_13_2 = baseInfo.getA_2_13_2() == null ? "" : baseInfo.getA_2_13_2();
		switch (a_2_13_2){
			case "1":
				a_2_13_2_tv.setText(getString(R.string.a_2_13_2_1));
				break;
			case "2" :
				a_2_13_2_tv.setText(getString(R.string.a_2_13_2_2));
				break;
			case "3" :
				a_2_13_2_tv.setText(getString(R.string.a_2_13_2_3));
				break;
			case "4" :
				a_2_13_2_tv.setText(getString(R.string.a_2_13_2_4));
				break;
			case "5" :
				a_2_13_2_tv.setText(getString(R.string.a_2_13_2_5));
				break;
			case "6" :
				a_2_13_2_tv.setText(getString(R.string.a_2_13_2_6));
				break;
			case "7" :
				a_2_13_2_tv.setText(getString(R.string.a_2_13_2_7));
				break;
		}

		a_2_13_3_tv.setText(baseInfo.getA_2_13_3());

		switch (baseInfo.getA_2_14_1()){
			case 0:
				a_2_14_1_tv.setText(getString(R.string.a_2_14_1_1));
				break;
			case 1 :
				a_2_14_1_tv.setText(getString(R.string.a_2_14_1_2));
				break;
			case 2 :
				a_2_14_1_tv.setText(getString(R.string.a_2_14_1_3));
				break;
			case 3 :
				a_2_14_1_tv.setText(getString(R.string.a_2_14_1_4));
				break;
		}

		switch (baseInfo.getA_2_14_2()){
			case 0:
				a_2_14_2_tv.setText(getString(R.string.a_2_14_1_1));
				break;
			case 1 :
				a_2_14_2_tv.setText(getString(R.string.a_2_14_1_2));
				break;
			case 2 :
				a_2_14_2_tv.setText(getString(R.string.a_2_14_1_3));
				break;
			case 3 :
				a_2_14_2_tv.setText(getString(R.string.a_2_14_1_4));
				break;
		}

		switch (baseInfo.getA_2_14_3()){
			case 0:
				a_2_14_3_tv.setText(getString(R.string.a_2_14_1_1));
				break;
			case 1 :
				a_2_14_3_tv.setText(getString(R.string.a_2_14_1_2));
				break;
			case 2 :
				a_2_14_3_tv.setText(getString(R.string.a_2_14_1_3));
				break;
			case 3 :
				a_2_14_3_tv.setText(getString(R.string.a_2_14_1_4));
				break;
		}

		switch (baseInfo.getA_2_14_4()){
			case 0:
				a_2_14_4_tv.setText(getString(R.string.a_2_14_1_1));
				break;
			case 1 :
				a_2_14_4_tv.setText(getString(R.string.a_2_14_1_2));
				break;
			case 2 :
				a_2_14_4_tv.setText(getString(R.string.a_2_14_1_3));
				break;
			case 3 :
				a_2_14_4_tv.setText(getString(R.string.a_2_14_1_4));
				break;
		}
		a_2_14_5_tv.setText(baseInfo.getA_2_14_5());

		a_3_1_tv.setText(baseInfo.getA_3_1());
		String a_3_2 = baseInfo.getA_3_2() == null ? "" : baseInfo.getA_3_2();
		switch (a_3_2){
			case "1":
				a_3_2_tv.setText(getString(R.string.a_3_2_1).substring(2));
				break;
			case "2" :
				a_3_2_tv.setText(getString(R.string.a_3_2_2).substring(2));
				break;
			case "3" :
				a_3_2_tv.setText(getString(R.string.a_3_2_3).substring(2));
				break;
			case "4" :
				a_3_2_tv.setText(getString(R.string.a_3_2_4).substring(2));
				break;
			case "5" :
				a_3_2_tv.setText(getString(R.string.a_3_2_5).substring(2));
				break;
		}

		a_3_3_tv.setText(baseInfo.getA_3_3());
		a_3_4_tv.setText(baseInfo.getA_3_4());

		File file = Environment.getExternalStorageDirectory();
		File myfile = null;
		try{
			myfile = new File(file.getCanonicalPath()+"/evaluate/images");
		}catch (IOException e){
			e.printStackTrace();
			return;
		}
		picture_front_iv.setImageURI(Uri.fromFile(new File(myfile + "/" + baseId + "_1.jpg")));
		picture_back_iv.setImageURI(Uri.fromFile(new File(myfile + "/" + baseId + "_2.jpg")));
		picture_full_iv.setImageURI(Uri.fromFile(new File(myfile + "/" + baseId + "_3.jpg")));
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
		Bundle bundle = new Bundle();
		bundle.putString("id",null == baseInfo ? "" : baseInfo.getBaseInfoId());
		GoActivityWithFinishing(BaseInformationEditActivity.class, bundle);
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/7 20:56
	* @Desc: 开始能力评估
	* @Params: v
	* @Return: void
	*/
	public void evaluate(View v) {
		Bundle bundle = new Bundle();
		bundle.putString("activity", "baseInfo");
		bundle.putString("id",null == baseInfo ? "" : baseInfo.getBaseInfoId());
		GoActivityWithFinishing(EvaluationEditActivity.class, bundle);
	}

	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == SystemUpdate.EVENT_FIND
				&& msgEvent.fromClass == BaseInformationViewActivity.class) {
			customLoadingDialog.dismiss();
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
