package com.elder.abilityevaluate.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
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
import com.elder.abilityevaluate.entity.Evaluation;
import com.elder.abilityevaluate.entity.EvaluationReport;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import de.greenrobot.event.EventBus;

public class BaseInformationEditActivity extends BasicActiviy {
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

	private ImageView picture_front_iv;
	private ImageView picture_back_iv;
	private ImageView picture_full_iv;

	private CustomLoadingDialog customLoadingDialog;
	private CustomDialog alertDialog;
	private CustomDialog confirmDialog;
	private int dialogType = 0;
	private String baseId = null;
	private BaseInformation baseInfo = null;
	private EditText dateEditText = null;
	private View picture_view = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		baseId = intent.getExtras().getString("id");
		showInitedLoading = false;
		setContentView(R.layout.base_information_edit_activity);
		//解决 Android N 上报错：android.os.FileUriExposedException
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
			StrictMode.setVmPolicy(builder.build());
		}

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

		this.initData();
	}
	/**
	 * @Author: wlf
	 * @Time: 2018/2/10 10:13
	 * @Desc: 当为修改操作时,填充显示数据
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
		a_1_1_et.setText(baseInfo.getA_1_1());
		a_1_2_et.setText(baseInfo.getA_1_2());
		String a_1_3 = baseInfo.getA_1_3() == null ? "" : baseInfo.getA_1_3();
		switch (a_1_3){
			case "1":
				a_1_3_rg.check(R.id.a_1_3_1_RB);
				break;
			case "2":
				a_1_3_rg.check(R.id.a_1_3_2_RB);
				break;
			case "3":
				a_1_3_rg.check(R.id.a_1_3_3_RB);
				break;
			case "4":
				a_1_3_rg.check(R.id.a_1_3_4_RB);
				break;
		}

		a_2_1_et.setText(baseInfo.getA_2_1());
		String a_2_2 = baseInfo.getA_2_2() == null ? "" : baseInfo.getA_2_2();
		switch (a_2_2){
			case "1":
				a_2_2_rg.check(R.id.a_2_2_1_RB);
				break;
			case "2" :
				a_2_2_rg.check(R.id.a_2_2_2_RB);
				break;
		}
		a_2_3_et.setText(baseInfo.getA_2_3());
		a_2_4_et.setText(baseInfo.getA_2_4());
		a_2_5_et.setText(baseInfo.getA_2_5());
		a_2_6_et.setText(baseInfo.getA_2_6_1());
		String a_2_6 = baseInfo.getA_2_6() == null ? "" : baseInfo.getA_2_6();
		switch (a_2_6){
			case "1":
				a_2_6_rg.check(R.id.a_2_6_1_RB);
				break;
			case "2" :
				a_2_6_rg.check(R.id.a_2_6_2_RB);
				break;
		}
		String a_2_7 = baseInfo.getA_2_7() == null ? "" : baseInfo.getA_2_7();
		switch (a_2_7){
			case "1":
				a_2_7_rg.check(R.id.a_2_7_1_RB);
				break;
			case "2" :
				a_2_7_rg.check(R.id.a_2_7_2_RB);
				break;
			case "3" :
				a_2_7_rg.check(R.id.a_2_7_3_RB);
				break;
			case "4" :
				a_2_7_rg.check(R.id.a_2_7_4_RB);
				break;
			case "5" :
				a_2_7_rg.check(R.id.a_2_7_5_RB);
				break;
			case "6" :
				a_2_7_rg.check(R.id.a_2_7_6_RB);
				break;
		}
		a_2_8_et.setText(baseInfo.getA_2_8_1());
		String a_2_8 = baseInfo.getA_2_8() == null ? "" : baseInfo.getA_2_8();
		switch (a_2_8){
			case "1":
				a_2_8_rg.check(R.id.a_2_8_1_RB);
				break;
			case "2" :
				a_2_8_rg.check(R.id.a_2_8_2_RB);
				break;
		}
		String a_2_9 = baseInfo.getA_2_9() == null ? "" : baseInfo.getA_2_9();
		switch (a_2_9){
			case "1":
				a_2_9_rg.check(R.id.a_2_9_1_RB);
				break;
			case "2" :
				a_2_9_rg.check(R.id.a_2_9_2_RB);
				break;
			case "3" :
				a_2_9_rg.check(R.id.a_2_9_3_RB);
				break;
			case "4" :
				a_2_9_rg.check(R.id.a_2_9_4_RB);
				break;
			case "5" :
				a_2_9_rg.check(R.id.a_2_9_5_RB);
				break;
		}
		String a_2_10 = baseInfo.getA_2_10() == null ? "" : baseInfo.getA_2_10();
		switch (a_2_10){
			case "1":
				a_2_10_rg.check(R.id.a_2_10_1_RB);
				break;
			case "2" :
				a_2_10_rg.check(R.id.a_2_10_2_RB);
				break;
			case "3" :
				a_2_10_rg.check(R.id.a_2_10_3_RB);
				break;
			case "4" :
				a_2_10_rg.check(R.id.a_2_10_4_RB);
				break;
			case "5" :
				a_2_10_rg.check(R.id.a_2_10_5_RB);
				break;
			case "6" :
				a_2_10_rg.check(R.id.a_2_10_6_RB);
				break;
			case "7" :
				a_2_10_rg.check(R.id.a_2_10_7_RB);
				break;
			case "8" :
				a_2_10_rg.check(R.id.a_2_10_8_RB);
				break;
		}
		a_2_11_et.setText(baseInfo.getA_2_11_1());
		String a_2_11 = baseInfo.getA_2_11() == null ? "" : baseInfo.getA_2_11();
		if(a_2_11.contains("1")) a_2_11_1_cb.setChecked(true);
		if(a_2_11.contains("2")) a_2_11_2_cb.setChecked(true);
		if(a_2_11.contains("3")) a_2_11_3_cb.setChecked(true);
		if(a_2_11.contains("4")) a_2_11_4_cb.setChecked(true);
		if(a_2_11.contains("5")) a_2_11_5_cb.setChecked(true);
		if(a_2_11.contains("6")) a_2_11_6_cb.setChecked(true);
		if(a_2_11.contains("7")) a_2_11_7_cb.setChecked(true);
		if(a_2_11.contains("8")) a_2_11_8_cb.setChecked(true);

		String a_2_12 = baseInfo.getA_2_12() == null ? "" : baseInfo.getA_2_12();
		if(a_2_12.contains("1")) a_2_12_1_cb.setChecked(true);
		if(a_2_12.contains("2")) a_2_12_2_cb.setChecked(true);
		if(a_2_12.contains("3")) a_2_12_3_cb.setChecked(true);
		if(a_2_12.contains("4")) a_2_12_4_cb.setChecked(true);
		String a_2_13_1 = baseInfo.getA_2_13_1() == null ? "" : baseInfo.getA_2_13_1();
		switch (a_2_13_1){
			case "1":
				a_2_13_1_rg.check(R.id.a_2_13_1_1_RB);
				break;
			case "2" :
				a_2_13_1_rg.check(R.id.a_2_13_1_2_RB);
				break;
			case "3" :
				a_2_13_1_rg.check(R.id.a_2_13_1_3_RB);
				break;
			case "4" :
				a_2_13_1_rg.check(R.id.a_2_13_1_4_RB);
				break;
		}
		String a_2_13_2 = baseInfo.getA_2_13_2() == null ? "" : baseInfo.getA_2_13_2();
		switch (a_2_13_2){
			case "1":
				a_2_13_2_rg.check(R.id.a_2_13_2_1_RB);
				break;
			case "2" :
				a_2_13_2_rg.check(R.id.a_2_13_2_2_RB);
				break;
			case "3" :
				a_2_13_2_rg.check(R.id.a_2_13_2_3_RB);
				break;
			case "4" :
				a_2_13_2_rg.check(R.id.a_2_13_2_4_RB);
				break;
			case "5" :
				a_2_13_2_rg.check(R.id.a_2_13_2_5_RB);
				break;
			case "6" :
				a_2_13_2_rg.check(R.id.a_2_13_2_6_RB);
				break;
			case "7" :
				a_2_13_2_rg.check(R.id.a_2_13_2_7_RB);
				break;
		}
		a_2_13_3_et.setText(baseInfo.getA_2_13_3());

		switch (baseInfo.getA_2_14_1()){
			case 0:
				a_2_14_1_rg.check(R.id.a_2_14_1_1_RB);
				break;
			case 1 :
				a_2_14_1_rg.check(R.id.a_2_14_1_2_RB);
				break;
			case 2 :
				a_2_14_1_rg.check(R.id.a_2_14_1_3_RB);
				break;
			case 3 :
				a_2_14_1_rg.check(R.id.a_2_14_1_4_RB);
				break;
		}

		switch (baseInfo.getA_2_14_2()){
			case 0:
				a_2_14_2_rg.check(R.id.a_2_14_2_1_RB);
				break;
			case 1 :
				a_2_14_2_rg.check(R.id.a_2_14_2_2_RB);
				break;
			case 2 :
				a_2_14_2_rg.check(R.id.a_2_14_2_3_RB);
				break;
			case 3 :
				a_2_14_2_rg.check(R.id.a_2_14_2_4_RB);
				break;
		}
		switch (baseInfo.getA_2_14_3()){
			case 0:
				a_2_14_3_rg.check(R.id.a_2_14_3_1_RB);
				break;
			case 1 :
				a_2_14_3_rg.check(R.id.a_2_14_3_2_RB);
				break;
			case 2 :
				a_2_14_3_rg.check(R.id.a_2_14_3_3_RB);
				break;
			case 3 :
				a_2_14_3_rg.check(R.id.a_2_14_3_4_RB);
				break;
		}
		switch (baseInfo.getA_2_14_4()){
			case 0:
				a_2_14_4_rg.check(R.id.a_2_14_4_1_RB);
				break;
			case 1 :
				a_2_14_4_rg.check(R.id.a_2_14_4_2_RB);
				break;
			case 2 :
				a_2_14_4_rg.check(R.id.a_2_14_4_3_RB);
				break;
			case 3 :
				a_2_14_4_rg.check(R.id.a_2_14_4_4_RB);
				break;
		}
		a_2_14_5_et.setText(baseInfo.getA_2_14_5());

		a_3_1_et.setText(baseInfo.getA_3_1());
		String a_3_2 = baseInfo.getA_3_2() == null ? "" : baseInfo.getA_3_2();
		switch (a_3_2){
			case "1":
				a_3_2_rg.check(R.id.a_3_2_1_RB);
				break;
			case "2" :
				a_3_2_rg.check(R.id.a_3_2_2_RB);
				break;
			case "3" :
				a_3_2_rg.check(R.id.a_3_2_3_RB);
				break;
			case "4" :
				a_3_2_rg.check(R.id.a_3_2_4_RB);
				break;
			case "5" :
				a_3_2_rg.check(R.id.a_3_2_5_RB);
				break;
		}
		a_3_3_et.setText(baseInfo.getA_3_3());
		a_3_4_et.setText(baseInfo.getA_3_4());

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
		//a_1_2_et.setText(choose_date);
		dateEditText.setText(choose_date);
	}

	public void takePhoto(View v){
		GoActivityWithOutFinishing(OCR_Activity.class,null);
		if(1==1){
			return;
		}
		this.picture_view = v;
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = null;
		try {
			file = new File(Environment.getExternalStorageDirectory(),"temp.jpg");
		}catch (Exception e){
			e.printStackTrace();
		}
		Uri imageUri =Uri.fromFile(file);
		//指定照片保存路径（SD卡），temp.jpg为一个临时文件，每次拍照后这个图片都会被替换
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(cameraIntent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK){
			String sdStatus = Environment.getExternalStorageState();
			if(!sdStatus.equals(Environment.MEDIA_MOUNTED)){
				System.out.println(" ------------- sd card is not avaiable ---------------");
				return;
			}
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
			Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/temp.jpg");
			FileOutputStream out = null;
			File file = Environment.getExternalStorageDirectory();
			File myfile = null;
			try{
				myfile = new File(file.getCanonicalPath()+"/evaluate/images");
				if(!myfile.exists()){
					myfile.mkdirs();
				}
				System.out.println(myfile + "/" +fileName);
				out = new FileOutputStream(myfile + "/" +fileName);
				bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
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
			if(picture_view.getId() == R.id.picture_front_btn) {
				picture_front_iv.setImageBitmap(bitmap);
			}else if(picture_view.getId() == R.id.picture_back_btn) {
				picture_back_iv.setImageBitmap(bitmap);
			}else if(picture_view.getId() == R.id.picture_full_btn){
				picture_full_iv.setImageBitmap(bitmap);
			}
			//Uri uri = Uri.fromFile(new File(myfile + "/" +fileName));
			//((ImageView) findViewById(R.id.picture)).setImageURI(uri);
			if(null != bitmap){
			//	bitmap.recycle();
			}
		}
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

		DataBaseHelper.getInstance(this,BaseInformation.class).update(baseInfo);
		CustomToast.show(this, "保存成功", Toast.LENGTH_SHORT);
		Bundle bundle = new Bundle();
		bundle.putString("id",baseInfo.getBaseInfoId());
		GoActivityWithFinishing(BaseInformationViewActivity.class,bundle);
		this.updateReport();
	}
	/**
	 * @Author: wlf
	 * @Time: 2018/2/22 18:49
	 * @Desc: 更新评估报告内容
	 * @Params:
	 * @Return:
	 */
	private void updateReport(){
		EvaluationReport evaluationReport = baseInfo.getEvaluationReport(this);
		Evaluation evaluation = baseInfo.getEvaluation(this);
		if(null == evaluationReport || null == evaluation){
			return;
		}
		int b_1_level = evaluation.getB_1_level();
		int b_2_level = evaluation.getB_2_level();
		int b_3_level = evaluation.getB_3_level();
		int b_4_level = evaluation.getB_4_level();
		evaluationReport.setB_1_grade(b_1_level);
		evaluationReport.setB_2_grade(b_2_level);
		evaluationReport.setB_3_grade(b_3_level);
		evaluationReport.setB_4_grade(b_4_level);
		int grade_pre = -1;
		int grade_final = -1;
		String change_item = "";
		grade_pre = grade_pre < b_1_level ? b_1_level : grade_pre;
		grade_pre = grade_pre < b_2_level ? b_2_level : grade_pre;
		grade_pre = grade_pre < b_3_level ? b_3_level : grade_pre;
		grade_pre = grade_pre < b_4_level ? b_4_level : grade_pre;
		grade_final = grade_pre;
		if(evaluation.getB_3_1() == 3){
			grade_pre = 3;
			grade_final = 3;
			change_item = "3";
		}else{
			String a_2_13_1 = baseInfo.getA_2_13_1();
			String a_2_13_2 = baseInfo.getA_2_13_2();
			if( (a_2_13_1 != null && !"".equals(a_2_13_1) && !"1".equals(a_2_13_1))
					|| (a_2_13_2 != null && !"".equals(a_2_13_2) && !"1".equals(a_2_13_2))){
				change_item += "1;";
				grade_final += 1;
			}
			if(baseInfo.getA_2_14_1() >= 2
					|| baseInfo.getA_2_14_2() >= 2
					|| baseInfo.getA_2_14_3() >= 2
					|| baseInfo.getA_2_14_4() >=2){
				change_item += "2;";
				grade_final += 1;
			}
		}
		evaluationReport.setGrade_change_item(change_item);
		evaluationReport.setE_grade_pre(grade_pre);//初步等级
		evaluationReport.setE_grade_final(grade_final > 3 ? 3 : grade_final);//最终等级
		evaluationReport.setE_name(evaluation.getBaseInformation(this).getA_2_1());
		DataBaseHelper.getInstance(this,EvaluationReport.class).update(evaluationReport);
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
			String result = (String) msgEvent.values.get("result");
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
