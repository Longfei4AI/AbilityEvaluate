package com.elder.abilityevaluate.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.entity.BaseInformation;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.utils.DataBaseHelper;
import com.elder.abilityevaluate.utils.DateUtils;
import com.elder.abilityevaluate.utils.GlobalInfo;
import com.elder.abilityevaluate.utils.OCR_Main;
import com.elder.abilityevaluate.utils.SystemUpdate;
import com.elder.abilityevaluate.widget.CustomDialog;
import com.elder.abilityevaluate.widget.CustomLoadingDialog;
import com.elder.abilityevaluate.widget.CustomToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import de.greenrobot.event.EventBus;

public class BaseInformationAddActivity extends BasicActiviy {
	public static final int DIALOG_NEW_VERSION = 0x01;
	private EditText a_1_1_et;
	private EditText a_1_2_et;
	private RadioGroup a_1_3_rg;
	private EditText a_2_1_et;
	private RadioGroup a_2_2_rg;
	private EditText a_2_3_et;
	private EditText a_2_4_et;
	private EditText a_2_5_et;
	private EditText a_2_6_et;
	private RadioGroup a_2_6_rg;
	private RadioGroup a_2_7_rg;
	private EditText a_2_8_et;
	private RadioGroup a_2_8_rg;
	private RadioGroup a_2_9_rg;
	private RadioGroup a_2_10_rg;
	private EditText a_2_11_et;
	private CheckBox a_2_11_1_cb;
	private CheckBox a_2_11_2_cb;
	private CheckBox a_2_11_3_cb;
	private CheckBox a_2_11_4_cb;
	private CheckBox a_2_11_5_cb;
	private CheckBox a_2_11_6_cb;
	private CheckBox a_2_11_7_cb;
	private CheckBox a_2_11_8_cb;
	private CheckBox a_2_12_1_cb;
	private CheckBox a_2_12_2_cb;
	private CheckBox a_2_12_3_cb;
	private CheckBox a_2_12_4_cb;
	private RadioGroup a_2_13_1_rg;
	private RadioGroup a_2_13_2_rg;
	private EditText a_2_13_3_et;
	private RadioGroup a_2_14_1_rg;
	private RadioGroup a_2_14_2_rg;
	private RadioGroup a_2_14_3_rg;
	private RadioGroup a_2_14_4_rg;
	private EditText a_2_14_5_et;

	private EditText a_3_1_et;
	private RadioGroup a_3_2_rg;
	private EditText a_3_3_et;
	private EditText a_3_4_et;

	private CustomLoadingDialog customLoadingDialog;
	private CustomDialog alertDialog;
	private CustomDialog confirmDialog;
	private int dialogType = 0;
	private EditText dateEditText = null;

	private ImageView picture_front_iv;
	private ImageView picture_back_iv;
	private ImageView picture_full_iv;
	private String baseId = null;

	private View picture_view = null;
	private OCR_Main ocrMain = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showInitedLoading = false;
		setContentView(R.layout.base_information_add_activity);
		baseId = UUID.randomUUID().toString().replaceAll("-","");
		//解决 Android N 上报错：android.os.FileUriExposedException
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
			StrictMode.setVmPolicy(builder.build());
		}
		ocrMain = OCR_Main.getInstance(this);
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
		a_1_1_et = findViewById(R.id.a_1_1_et);
		a_1_2_et = findViewById(R.id.a_1_2_et);
		a_1_3_rg = findViewById(R.id.a_1_3_RG);
		a_2_1_et = findViewById(R.id.a_2_1_et);
		a_2_2_rg = findViewById(R.id.a_2_2_RG);
		a_2_3_et = findViewById(R.id.a_2_3_et);
		a_2_4_et = findViewById(R.id.a_2_4_et);
		a_2_5_et = findViewById(R.id.a_2_5_et);
		a_2_6_et = findViewById(R.id.a_2_6_et);
		a_2_6_rg = findViewById(R.id.a_2_6_RG);
		a_2_7_rg = findViewById(R.id.a_2_7_RG);
		a_2_8_et = findViewById(R.id.a_2_8_et);
		a_2_8_rg = findViewById(R.id.a_2_8_RG);
		a_2_9_rg = findViewById(R.id.a_2_9_RG);
		a_2_10_rg = findViewById(R.id.a_2_10_RG);
		a_2_11_et = findViewById(R.id.a_2_11_et);
		a_2_11_1_cb = findViewById(R.id.a_2_11_1_cb);
		a_2_11_2_cb = findViewById(R.id.a_2_11_2_cb);
		a_2_11_3_cb = findViewById(R.id.a_2_11_3_cb);
		a_2_11_4_cb = findViewById(R.id.a_2_11_4_cb);
		a_2_11_5_cb = findViewById(R.id.a_2_11_5_cb);
		a_2_11_6_cb = findViewById(R.id.a_2_11_6_cb);
		a_2_11_7_cb = findViewById(R.id.a_2_11_7_cb);
		a_2_11_8_cb = findViewById(R.id.a_2_11_8_cb);
		a_2_12_1_cb = findViewById(R.id.a_2_12_1_cb);
		a_2_12_2_cb = findViewById(R.id.a_2_12_2_cb);
		a_2_12_3_cb = findViewById(R.id.a_2_12_3_cb);
		a_2_12_4_cb = findViewById(R.id.a_2_12_4_cb);
		a_2_13_1_rg = findViewById(R.id.a_2_13_1_RG);
		a_2_13_2_rg = findViewById(R.id.a_2_13_2_RG);
		a_2_13_3_et = findViewById(R.id.a_2_13_3_et);
		a_2_14_1_rg = findViewById(R.id.a_2_14_1_RG);
		a_2_14_2_rg = findViewById(R.id.a_2_14_2_RG);
		a_2_14_3_rg = findViewById(R.id.a_2_14_3_RG);
		a_2_14_4_rg = findViewById(R.id.a_2_14_4_RG);
		a_2_14_5_et = findViewById(R.id.a_2_14_5_et);
		a_3_1_et = findViewById(R.id.a_3_1_et);
		a_3_2_rg = findViewById(R.id.a_3_2_RG);
		a_3_3_et = findViewById(R.id.a_3_3_et);
		a_3_4_et = findViewById(R.id.a_3_4_et);

		picture_front_iv = findViewById(R.id.picture_front);
		picture_back_iv = findViewById(R.id.picture_back);
		picture_full_iv = findViewById(R.id.picture_full);
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
		String a_1_1 = a_1_1_et.getText().toString();
		String a_1_2 = a_1_2_et.getText().toString();
		int a_1_3_rb_id = a_1_3_rg.getCheckedRadioButtonId();
		String a_2_1 = a_2_1_et.getText().toString();
		int a_2_2_rb_id = a_2_2_rg.getCheckedRadioButtonId();
		String a_2_3 = a_2_3_et.getText().toString();
		String a_2_4 = a_2_4_et.getText().toString();
		String a_2_5 = a_2_5_et.getText().toString();
		String a_2_6 = a_2_6_et.getText().toString();
		int a_2_6_rb_id = a_2_6_rg.getCheckedRadioButtonId();
		int a_2_7_rb_id = a_2_7_rg.getCheckedRadioButtonId();
		String a_2_8 = a_2_8_et.getText().toString();
		int a_2_8_rb_id = a_2_8_rg.getCheckedRadioButtonId();
		int a_2_9_rb_id = a_2_9_rg.getCheckedRadioButtonId();
		int a_2_10_rb_id = a_2_10_rg.getCheckedRadioButtonId();
		String a_2_11_1 = a_2_11_et.getText().toString();
		StringBuffer a_2_11 = new StringBuffer("");
		if(a_2_11_1_cb.isChecked()) a_2_11.append(",1");
		if(a_2_11_2_cb.isChecked()) a_2_11.append(",2");
		if(a_2_11_3_cb.isChecked()) a_2_11.append(",3");
		if(a_2_11_4_cb.isChecked()) a_2_11.append(",4");
		if(a_2_11_5_cb.isChecked()) a_2_11.append(",5");
		if(a_2_11_6_cb.isChecked()) a_2_11.append(",6");
		if(a_2_11_7_cb.isChecked()) a_2_11.append(",7");
		if(a_2_11_8_cb.isChecked()) a_2_11.append(",8");

		StringBuffer a_2_12 = new StringBuffer("");
		if(a_2_12_1_cb.isChecked()) a_2_12.append(",1");
		if(a_2_12_2_cb.isChecked()) a_2_12.append(",2");
		if(a_2_12_3_cb.isChecked()) a_2_12.append(",3");
		if(a_2_12_4_cb.isChecked()) a_2_12.append(",4");

		int a_2_13_1_rb_id = a_2_13_1_rg.getCheckedRadioButtonId();
		int a_2_13_2_rb_id = a_2_13_2_rg.getCheckedRadioButtonId();
		String a_2_13_3 = a_2_13_3_et.getText().toString();
		int a_2_14_1_rb_id = a_2_14_1_rg.getCheckedRadioButtonId();
		int a_2_14_2_rb_id = a_2_14_2_rg.getCheckedRadioButtonId();
		int a_2_14_3_rb_id = a_2_14_3_rg.getCheckedRadioButtonId();
		int a_2_14_4_rb_id = a_2_14_4_rg.getCheckedRadioButtonId();
		String a_2_14_5 = a_2_14_5_et.getText().toString();

		String a_3_1 = a_3_1_et.getText().toString();
		int a_3_2_rb_id = a_3_2_rg.getCheckedRadioButtonId();
		String a_3_3 = a_3_3_et.getText().toString();
		String a_3_4 = a_3_4_et.getText().toString();

		BaseInformation baseInfo = new BaseInformation();
		baseInfo.setBaseInfoId(baseId);
		baseInfo.setA_1_1(a_1_1);
		baseInfo.setA_1_2(a_1_2);

		switch (a_1_3_rb_id){
			case R.id.a_1_3_1_RB :
				baseInfo.setA_1_3("1");
				break;
			case R.id.a_1_3_2_RB :
				baseInfo.setA_1_3("2");
				break;
			case R.id.a_1_3_3_RB :
				baseInfo.setA_1_3("3");
				break;
			case R.id.a_1_3_4_RB :
				baseInfo.setA_1_3("4");
				break;
		}
		baseInfo.setA_2_1(a_2_1);

		switch (a_2_2_rb_id){
			case R.id.a_2_2_1_RB :
				baseInfo.setA_2_2("1");
				break;
			case R.id.a_2_2_2_RB :
				baseInfo.setA_2_2("2");
				break;
		}
		baseInfo.setA_2_3(a_2_3);
		baseInfo.setA_2_4(a_2_4);
		baseInfo.setA_2_5(a_2_5);
		switch (a_2_6_rb_id){
			case R.id.a_2_6_1_RB :
				baseInfo.setA_2_6("1");
				a_2_6 = "";
				break;
			case R.id.a_2_6_2_RB :
				baseInfo.setA_2_6("2");
				break;
		}
		baseInfo.setA_2_6_1(a_2_6);
		switch (a_2_7_rb_id){
			case R.id.a_2_7_1_RB :
				baseInfo.setA_2_7("1");
				break;
			case R.id.a_2_7_2_RB :
				baseInfo.setA_2_7("2");
				break;
			case R.id.a_2_7_3_RB :
				baseInfo.setA_2_7("3");
				break;
			case R.id.a_2_7_4_RB :
				baseInfo.setA_2_7("4");
				break;
			case R.id.a_2_7_5_RB :
				baseInfo.setA_2_7("5");
				break;
			case R.id.a_2_7_6_RB :
				baseInfo.setA_2_7("6");
				break;
		}
		baseInfo.setA_2_8(a_2_8);
		switch (a_2_8_rb_id){
			case R.id.a_2_8_1_RB :
				baseInfo.setA_2_8("1");
				break;
			case R.id.a_2_8_2_RB :
				baseInfo.setA_2_8("2");
				break;
		}

		switch (a_2_9_rb_id){
			case R.id.a_2_9_1_RB :
				baseInfo.setA_2_9("1");
				break;
			case R.id.a_2_9_2_RB :
				baseInfo.setA_2_9("2");
				break;
			case R.id.a_2_9_3_RB :
				baseInfo.setA_2_9("3");
				break;
			case R.id.a_2_9_4_RB :
				baseInfo.setA_2_9("4");
				break;
			case R.id.a_2_9_5_RB :
				baseInfo.setA_2_9("5");
				break;
		}

		switch (a_2_10_rb_id){
			case R.id.a_2_10_1_RB :
				baseInfo.setA_2_10("1");
				break;
			case R.id.a_2_10_2_RB :
				baseInfo.setA_2_10("2");
				break;
			case R.id.a_2_10_3_RB :
				baseInfo.setA_2_10("3");
				break;
			case R.id.a_2_10_4_RB :
				baseInfo.setA_2_10("4");
				break;
			case R.id.a_2_10_5_RB :
				baseInfo.setA_2_10("5");
				break;
			case R.id.a_2_10_6_RB :
				baseInfo.setA_2_10("6");
				break;
			case R.id.a_2_10_7_RB :
				baseInfo.setA_2_10("7");
				break;
			case R.id.a_2_10_8_RB :
				baseInfo.setA_2_10("8");
				break;
		}

		baseInfo.setA_2_11(a_2_11.toString());
		baseInfo.setA_2_11_1(a_2_11_1);
		baseInfo.setA_2_12(a_2_12.toString());

		switch (a_2_13_1_rb_id){
			case R.id.a_2_13_1_1_RB :
				baseInfo.setA_2_13_1("1");
				break;
			case R.id.a_2_13_1_2_RB :
				baseInfo.setA_2_13_1("2");
				break;
			case R.id.a_2_13_1_3_RB :
				baseInfo.setA_2_13_1("3");
				break;
			case R.id.a_2_13_1_4_RB :
				baseInfo.setA_2_13_1("4");
				break;
		}

		switch (a_2_13_2_rb_id){
			case R.id.a_2_13_2_1_RB :
				baseInfo.setA_2_13_2("1");
				break;
			case R.id.a_2_13_2_2_RB :
				baseInfo.setA_2_13_2("2");
				break;
			case R.id.a_2_13_2_3_RB :
				baseInfo.setA_2_13_2("3");
				break;
			case R.id.a_2_13_2_4_RB :
				baseInfo.setA_2_13_2("4");
				break;
			case R.id.a_2_13_2_5_RB :
				baseInfo.setA_2_13_2("5");
				break;
			case R.id.a_2_13_2_6_RB :
				baseInfo.setA_2_13_2("6");
				break;
			case R.id.a_2_13_2_7_RB :
				baseInfo.setA_2_13_2("7");
				break;
		}
		baseInfo.setA_2_13_3(a_2_13_3);

		switch (a_2_14_1_rb_id){
			case R.id.a_2_14_1_1_RB :
				baseInfo.setA_2_14_1(0);
				break;
			case R.id.a_2_14_1_2_RB :
				baseInfo.setA_2_14_1(1);
				break;
			case R.id.a_2_14_1_3_RB :
				baseInfo.setA_2_14_1(2);
				break;
			case R.id.a_2_14_1_4_RB :
				baseInfo.setA_2_14_1(3);
				break;
		}

		switch (a_2_14_2_rb_id){
			case R.id.a_2_14_2_1_RB :
				baseInfo.setA_2_14_2(0);
				break;
			case R.id.a_2_14_2_2_RB :
				baseInfo.setA_2_14_2(1);
				break;
			case R.id.a_2_14_2_3_RB :
				baseInfo.setA_2_14_2(2);
				break;
			case R.id.a_2_14_2_4_RB :
				baseInfo.setA_2_14_2(3);
				break;
		}

		switch (a_2_14_3_rb_id){
			case R.id.a_2_14_3_1_RB :
				baseInfo.setA_2_14_3(0);
				break;
			case R.id.a_2_14_3_2_RB :
				baseInfo.setA_2_14_3(1);
				break;
			case R.id.a_2_14_3_3_RB :
				baseInfo.setA_2_14_3(2);
				break;
			case R.id.a_2_14_3_4_RB :
				baseInfo.setA_2_14_3(3);
				break;
		}

		switch (a_2_14_4_rb_id){
			case R.id.a_2_14_4_1_RB :
				baseInfo.setA_2_14_4(0);
				break;
			case R.id.a_2_14_4_2_RB :
				baseInfo.setA_2_14_4(1);
				break;
			case R.id.a_2_14_4_3_RB :
				baseInfo.setA_2_14_4(2);
				break;
			case R.id.a_2_14_4_4_RB :
				baseInfo.setA_2_14_4(3);
				break;
		}
		baseInfo.setA_2_14_5(a_2_14_5);

		baseInfo.setA_3_1(a_3_1);
		switch (a_3_2_rb_id){
			case R.id.a_3_2_1_RB :
				baseInfo.setA_3_2("1");
				break;
			case R.id.a_3_2_2_RB :
				baseInfo.setA_3_2("2");
				break;
			case R.id.a_3_2_3_RB :
				baseInfo.setA_3_2("3");
				break;
			case R.id.a_3_2_4_RB :
				baseInfo.setA_3_2("4");
				break;
			case R.id.a_3_2_5_RB :
				baseInfo.setA_3_2("5");
				break;
		}
		baseInfo.setA_3_3(a_3_3);
		baseInfo.setA_3_4(a_3_4);
		baseInfo.setState(BaseInformation.NOT_EVALUATED);
		baseInfo.setRegisterTime(DateUtils.getCurDateTime());
		DataBaseHelper.getInstance(this,BaseInformation.class).save(baseInfo);

		CustomToast.show(this, "保存成功", Toast.LENGTH_SHORT);
		Bundle bundle = new Bundle();
		bundle.putString("id",baseInfo.getBaseInfoId());
		GoActivityWithOutFinishing(BaseInformationViewActivity.class,bundle);
	}
	/**
	 * @Author: wlf
	 * @Time: 2018/2/10 16:09
	 * @Desc: 设置评估基准日期
	 * @Params:
	 * @Return:
	 */
	public void setDate(View v) {
		dateEditText = (EditText) v;
		String dateStr = dateEditText.getText().toString().trim();
		if(dateStr.length() == 10){
			c_year = Integer.parseInt(dateStr.substring(0,4));
			c_month = Integer.parseInt(dateStr.substring(5,7))-1;
			c_day = Integer.parseInt(dateStr.substring(8,10));
		}
		datePickerDialog.updateDate(c_year, c_month, c_day);
		datePickerDialog.show();
	}

	/**
	 * @Author: wlf
	 * @Time: 2018/2/10 16:09
	 * @Desc: 设置日期完成后调用
	 * @Params:
	 * @Return:
	 */
	@Override
	public void afterSetDate(int y, int m, int d) {
		Calendar c = Calendar.getInstance();
		c.set(y, m, d);
		c_year = y;
		c_month = m;
		c_day = d;
		choose_date = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		//date_str = new SimpleDateFormat("yyyy年MM月dd日").format(c.getTime());
		//checkDate = choose_date;
		dateEditText.setText(choose_date);
	}
	public void takePhoto(View v){
		this.picture_view = v;
		ocrMain.takePhoto();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_CANCELED){
			return;
		}
		if (requestCode == OCR_Main.PHOTO_CAPTURE) {
			ocrMain.photoCapture();//裁剪图片
			return;
		}
		if (requestCode == OCR_Main.PHOTO_RESULT){
			Bitmap bitmapSelected = ocrMain.photoResult();
			if(picture_view.getId() == R.id.picture_front_btn) {
				ocrMain.startOrc(ocrHandler);
				picture_front_iv.setImageBitmap(bitmapSelected);
			}else if(picture_view.getId() == R.id.picture_back_btn) {
				picture_back_iv.setImageBitmap(bitmapSelected);
			}else if(picture_view.getId() == R.id.picture_full_btn){
				picture_full_iv.setImageBitmap(bitmapSelected);
			}
			//Uri uri = Uri.fromFile(new File(myfile + "/" +fileName));
			//((ImageView) findViewById(R.id.picture)).setImageURI(uri);
			this.savePicture(bitmapSelected);
			if(null != bitmapSelected){
				//	bitmap.recycle();
			}
		}
	}
	/**
	 * @Author: wlf
	 * @Time: 2018/2/28 23:16
	 * @Desc: 保存图片
	 * @Params:
	 * @Return:
	 */
	private void savePicture(Bitmap bitmapSelected){
		String flag = "";
		if(picture_view.getId() == R.id.picture_front_btn) {
			flag = "_1";
		}else if(picture_view.getId() == R.id.picture_back_btn) {
			flag = "_2";
		}else if(picture_view.getId() == R.id.picture_full_btn){
			flag = "_3";
		}
		String fileName = baseId+ flag + ".jpg";
		//Bundle bundle = data.getExtras();
		//该方式图片会被压缩
		//Bitmap bitmap = (Bitmap) bundle.get("data");
		//Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/temp.jpg");
		FileOutputStream out = null;
		File myfile = null;
		try{
			myfile = new File(GlobalInfo.PIC_PATH);
			if(!myfile.exists()){
				myfile.mkdirs();
			}
			out = new FileOutputStream(myfile + "/" +fileName);
			bitmapSelected.compress(Bitmap.CompressFormat.JPEG,100,out);
		}catch (IOException e){
			e.printStackTrace();
			return;
		}finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	// 该handler用于处理修改结果的任务
	public Handler ocrHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case OCR_Main.SHOWRESULT:
					String result = msg.obj.toString();
					if (result.equals("")){
						CustomToast.show(BaseInformationAddActivity.this,"识别失败",1);
					}else{
						if(result.indexOf("\n")>-1){
							String[] arr = result.split("\n");
							if(arr.length<5){
								CustomToast.show(BaseInformationAddActivity.this,"识别失败",1);
							}else{
								System.out.println(result);
								System.out.println("**********************************");
								String nameStr = "";
								String sex_natioinStr = "";
								String idStr = "";
								for(int i = 0 ; i < arr.length ; i++){
									String temp = arr[i].trim().replaceAll(" ","");
									if(temp.contains("姓名")){
										nameStr = temp.substring(temp.indexOf("姓名"));
									}else if(temp.contains("性别") || (temp.contains("性") && temp.contains("民"))){
										sex_natioinStr = temp.substring(temp.indexOf("性"));
										if("".equals(nameStr)){
											nameStr = arr[i-1].trim().replaceAll(" ","");
										}
									}
									int count = 0;
									if(temp.contains("公")){
										++ count ;
									}
									if(temp.contains("民")){
										++ count;
									}
									if(temp.contains("身")){
										++ count;
									}
									if(temp.contains("份")){
										++ count;
									}
									if(temp.contains("号")){
										++ count;
									}
									if(temp.contains("码")){
										++ count;
									}
									if(count > 1){
										idStr = temp;
									}
								}

								String name = nameStr.length() > 2 ? nameStr.substring(2) : "";
								String sex = sex_natioinStr.length() > 3 ? sex_natioinStr.substring(2,3) : "";
								String nation = sex_natioinStr.length() > 5 ? sex_natioinStr.substring(5) : "";
								String idNo = "";
								String birthDay = "";
								if(idStr.length()>18){
									idNo = idStr.substring(idStr.length()-18)
											.replaceAll("z","2").replaceAll("Z","2")
											.replaceAll("l","1").replaceAll("L","1")
											.replaceAll("o","0").replaceAll("O","0")
											.replaceAll("一","1").replaceAll("二","2")
											.replaceAll("三","3").replaceAll("四","4")
											.replaceAll("五","5").replaceAll("六","6")
											.replaceAll("七","7").replaceAll("八","8")
											.replaceAll("九","9").replaceAll("g","9")
											.replace("?","9").replace("?","9").replace("?","9")
											.replaceAll("…","3").replaceAll("龚","4")
											.replaceAll("~","4").replaceAll("彝","4")
											.replaceAll("橼","4").replaceAll("辱","4");

									birthDay = idNo.substring(6,10)+"-"+idNo.substring(10,12)+"-"+idNo.substring(12,14);
								}
								//身份证第17位：奇数为男，偶数为女
								int sexNo = -1;
								if(idNo.length() > 2){
									try {
										sexNo = Integer.parseInt(idNo.substring(idNo.length()-2,idNo.length()-1));
									}catch (Exception e){
										e.printStackTrace();
									}
								}

								if(sexNo > -1){
									if(sexNo%2 == 0){
										a_2_2_rg.check(R.id.a_2_2_2_RB);
									}else{
										a_2_2_rg.check(R.id.a_2_2_1_RB);
									}
								}

								a_2_1_et.setText(name);
								switch (sex){
									case "男":
										a_2_2_rg.check(R.id.a_2_2_1_RB);
										break;
									case "女" :
										a_2_2_rg.check(R.id.a_2_2_2_RB);
										break;
								}

								try {
									double testBirthday = Integer.parseInt(birthDay.replaceAll("-",""));
									if(!Double.isNaN(testBirthday)){
										a_2_3_et.setText(birthDay);
									}
								}catch (Exception e){
									e.printStackTrace();
								}

								a_2_4_et.setText(idNo);

								if(nation.equals("汉")){
									a_2_6_rg.check(R.id.a_2_6_1_RB);
								}else{
									a_2_6_rg.check(R.id.a_2_6_2_RB);
									a_2_6_et.setText(nation);
								}
							}
						}
					}
					break;
				case OCR_Main.SHOWTREATEDIMG:
					CustomToast.show(BaseInformationAddActivity.this,"识别中......",1);
					//picture_back_iv.setImageBitmap(ocrMain.bitmapTreated);
					break;
			}
			super.handleMessage(msg);
		}

	};

	/**
	* @Author: wlf
	* @Time: 2018/2/8 11:41
	* @Desc: EventBus事件接收
	* @Params:
	* @Return: void
	*/
	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == SystemUpdate.EVENT_FIND
				&& msgEvent.fromClass == BaseInformationAddActivity.class) {
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
