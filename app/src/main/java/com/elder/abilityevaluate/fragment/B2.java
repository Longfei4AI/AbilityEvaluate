/**   
 * @Title: Paper.java 
 * @Package com.xyyt.demo.fragment 
 * @author jiaone@163.com
 * @date 2015-4-10 上午12:02:18 
 * @version V1.0   
 */
package com.elder.abilityevaluate.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.entity.BaseInformation;
import com.elder.abilityevaluate.entity.Evaluation;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.utils.DataBaseHelper;
import com.elder.abilityevaluate.utils.GlobalInfo;
import com.elder.abilityevaluate.utils.OCR_Main;
import com.elder.abilityevaluate.widget.CustomToast;
import com.lidroid.xutils.db.sqlite.Selector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.greenrobot.event.EventBus;

public class B2 extends BaseFragment {
	public final static int SCANNIN_GREQUEST_CODE = 1;
	public final static int SCANNIN_FARMER_CODE = 2;
	public final static int EVENT_GETDATA = 0x02;
	View rootView;
	String corpCode = "";
	SharedPreferences spf;
	private RadioGroup b_2_1_rg;
	private RadioGroup b_2_2_rg;
	private RadioGroup b_2_3_rg;
	private TextView b_2_score_tv;
	private ImageView picture_watchIV;
	private RadioGroup b_2_level_rg;
	private Evaluation evaluation = null;
	private String baseId = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseId = getActivity().getIntent().getExtras().getString("id");
		//解决 Android N 上报错：android.os.FileUriExposedException
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
			StrictMode.setVmPolicy(builder.build());
		}
		EventBus.getDefault().register(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.b2_edit_fragment, null);
		b_2_1_rg = rootView.findViewById(R.id.b_2_1_RG);
		b_2_1_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_2_2_rg = rootView.findViewById(R.id.b_2_2_RG);
		b_2_2_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_2_3_rg = rootView.findViewById(R.id.b_2_3_RG);
		b_2_3_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_2_score_tv = rootView.findViewById(R.id.b_2_score_TV);
		b_2_level_rg = rootView.findViewById(R.id.b_2_level_RG);
		picture_watchIV = rootView.findViewById(R.id.picture_watch);
		picture_watchIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				takePhoto();
			}
		});
		this.initData();
		return rootView;
	}

	/**
	 * @Author: wlf
	 * @Time: 2018/2/10 10:13
	 * @Desc: 当为修改操作时,填充显示数据
	 * @Params:
	 * * @Return: void
	 */
	private void initData() {
		evaluation = DataBaseHelper.getInstance(this.getActivity(), Evaluation.class).
				findFirst(Selector.from(Evaluation.class).where("baseInfoId", "=", baseId));
		if (null == evaluation) {
			CustomToast.show(this.getActivity(), "获取数据失败！", CustomToast.LENGTH_SHORT);
			return;
		}
		String fileName = baseId + "_watch.jpg";
		File file_watch = new File(GlobalInfo.PIC_PATH, fileName);
		if(file_watch.exists()){
			picture_watchIV.setImageURI(Uri.fromFile(file_watch));
		}

		switch (evaluation.getB_2_1()) {
			case 0:
				b_2_1_rg.check(R.id.b_2_1_0_RB);
				break;
			case 1:
				b_2_1_rg.check(R.id.b_2_1_1_RB);
				break;
			case 2:
				b_2_1_rg.check(R.id.b_2_1_2_RB);
				break;
		}
		switch (evaluation.getB_2_2()) {
			case 0:
				b_2_2_rg.check(R.id.b_2_2_0_RB);
				break;
			case 1:
				b_2_2_rg.check(R.id.b_2_2_1_RB);
				break;
			case 2:
				b_2_2_rg.check(R.id.b_2_2_2_RB);
				break;
		}
		switch (evaluation.getB_2_3()) {
			case 0:
				b_2_3_rg.check(R.id.b_2_3_0_RB);
				break;
			case 1:
				b_2_3_rg.check(R.id.b_2_3_1_RB);
				break;
			case 2:
				b_2_3_rg.check(R.id.b_2_3_2_RB);
				break;
		}

		b_2_score_tv.setText(evaluation.getB_2_score() < 0 ? "" : evaluation.getB_2_score()+"");

		switch (evaluation.getB_2_level()){
			case 0:
				b_2_level_rg.check(R.id.b_2_level_0_RB);
				break;
			case 1:
				b_2_level_rg.check(R.id.b_2_level_1_RB);
				break;
			case 2:
				b_2_level_rg.check(R.id.b_2_level_2_RB);
				break;
			case 3:
				b_2_level_rg.check(R.id.b_2_level_3_RB);
				break;
		}
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/11 18:41
	* @Desc: 保存数据
	* @Params:
	* @Return:
	*/
	public void save() {
		evaluation = DataBaseHelper.getInstance(this.getActivity(), Evaluation.class).
				findFirst(Selector.from(Evaluation.class).where("baseInfoId", "=", baseId));
		if(null == evaluation){
			CustomToast.show(this.getActivity(),"B2数据异常！",CustomToast.LENGTH_SHORT);
			return;
		}
		switch (b_2_1_rg.getCheckedRadioButtonId()) {
			case R.id.b_2_1_0_RB:
				evaluation.setB_2_1(0);
				break;
			case R.id.b_2_1_1_RB:
				evaluation.setB_2_1(1);
				break;
			case R.id.b_2_1_2_RB:
				evaluation.setB_2_1(2);
				break;
		}
		switch (b_2_2_rg.getCheckedRadioButtonId()) {
			case R.id.b_2_2_0_RB:
				evaluation.setB_2_2(0);
				break;
			case R.id.b_2_2_1_RB:
				evaluation.setB_2_2(1);
				break;
			case R.id.b_2_2_2_RB:
				evaluation.setB_2_2(2);
				break;
		}
		switch (b_2_3_rg.getCheckedRadioButtonId()) {
			case R.id.b_2_3_0_RB:
				evaluation.setB_2_3(0);
				break;
			case R.id.b_2_3_1_RB:
				evaluation.setB_2_3(1);
				break;
			case R.id.b_2_3_2_RB:
				evaluation.setB_2_3(2);
				break;
		}

		int score = -1;
		if(evaluation.getB_2_1() > -1
				&& evaluation.getB_2_2() > -1
				&& evaluation.getB_2_3() > -1
				){
			score = evaluation.getB_2_1()
					+ evaluation.getB_2_2()
					+ evaluation.getB_2_3();
		}
		if(score > -1){
			b_2_score_tv.setText(score+"");
		}
		evaluation.setB_2_score(score);

		if( score == 0 ){
			evaluation.setB_2_level(0);
			b_2_level_rg.check(R.id.b_2_level_0_RB);
		}else if( score == 1 ){
			evaluation.setB_2_level(1);
			b_2_level_rg.check(R.id.b_2_level_1_RB);
		}else if( score >= 2 && score <= 3 ){
			evaluation.setB_2_level(2);
			b_2_level_rg.check(R.id.b_2_level_2_RB);
		}else if( score >= 4 && score <= 6 ){
			evaluation.setB_2_level(3);
			b_2_level_rg.check(R.id.b_2_level_3_RB);
		}

		DataBaseHelper.getInstance(this.getActivity(),Evaluation.class).update(evaluation);
		//CustomToast.show(this.getActivity(), "B2保存成功", Toast.LENGTH_SHORT);
	}

	@Override
	public void refreshData(String baseId) {

	}
	/**
	 * @Author: wlf
	 * @Time: 2018/3/1 8:36
	 * @Desc: 拍照
	 * @Params:
	 * @Return:
	 */
	public void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//指定照片保存路径（SD卡），temp.jpg为一个临时文件，每次拍照后这个图片都会被替换
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(OCR_Main.IMG_PATH, "temp.jpg")));
		try {
			startActivityForResult(intent, OCR_Main.PHOTO_CAPTURE);
		}catch (Exception e){
			e.printStackTrace();
			CustomToast.show(this.getActivity(),"请打开应用程序存储权限!",1);
		}
	}
	/**
	 * @Author: wlf
	 * @Time: 2018/3/1 8:43
	 * @Desc: 调用系统图片编辑进行裁剪
	 * @Params:
	 * @Return:
	 */
	public void startPhotoCrop(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(OCR_Main.IMG_PATH, "temp_cropped.jpg")));
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, OCR_Main.PHOTO_RESULT);
	}
	/**
	 * 根据URI获取位图
	 *
	 * @param uri
	 * @return 对应的位图
	 */
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(this.getActivity().getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("B2.onActivityResult");
		if (resultCode == Activity.RESULT_CANCELED){
			return;
		}
		if (requestCode == OCR_Main.PHOTO_CAPTURE) {
			startPhotoCrop(Uri.fromFile(new File(OCR_Main.IMG_PATH, "temp.jpg")));//裁剪图片
			return;
		}
		if (requestCode == OCR_Main.PHOTO_RESULT){
			Bitmap bitmapSelected = decodeUriAsBitmap(Uri.fromFile(new File(OCR_Main.IMG_PATH,
					"temp_cropped.jpg")));
			picture_watchIV.setImageBitmap(bitmapSelected);
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
		String fileName = baseId + "_watch.jpg";
		FileOutputStream out = null;
		File file = Environment.getExternalStorageDirectory();
		File myfile = null;
		try{
			myfile = new File(file.getAbsolutePath()+"/evaluate/images");
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
	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == EVENT_GETDATA
				&& msgEvent.fromClass == B2.class) {

		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}