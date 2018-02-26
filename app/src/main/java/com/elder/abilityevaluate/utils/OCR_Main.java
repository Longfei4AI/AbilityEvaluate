package com.elder.abilityevaluate.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.elder.abilityevaluate.R;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;

public class OCR_Main {

	private static final int PHOTO_CAPTURE = 0x11;// 拍照
	private static final int PHOTO_RESULT = 0x12;// 结果

	private static String LANGUAGE = "chi_sim";
	private static String IMG_PATH = getSDPath() + File.separator
			+ "ocrtest";

	private boolean chPreTreat = false;
	private static String textResult;
	public static Bitmap bitmapSelected;
	public static Bitmap bitmapTreated;
	private static final int SHOWRESULT = 0x101;
	private static final int SHOWTREATEDIMG = 0x102;
	private static Activity activity = null;
	private OCR_Main main = null;

	{
		// 若文件夹不存在 首先创建文件夹
		File path = new File(IMG_PATH);
		if (!path.exists()) {
			path.mkdirs();
		}
	}

	public OCR_Main getInstance(Activity acti){
		activity = acti;
		return main == null ? new OCR_Main() : main;
	}
	// 该handler用于处理修改结果的任务
	public static Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOWRESULT:
					if (textResult.equals("")){
						//tvResult.setText("识别失败");
					}else{
						//tvResult.setText(textResult);
					}
					break;
				case SHOWTREATEDIMG:
					//tvResult.setText("识别中......");
					//showPicture(ivTreated, bitmapTreated);
					break;
			}
			super.handleMessage(msg);
		}

	};


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_CANCELED)
			return;

		if (requestCode == PHOTO_CAPTURE) {
			//tvResult.setText("abc");
			startPhotoCrop(Uri.fromFile(new File(IMG_PATH, "temp.jpg")));
		}

		// 处理结果
		if (requestCode == PHOTO_RESULT) {
			bitmapSelected = decodeUriAsBitmap(Uri.fromFile(new File(IMG_PATH,
					"temp_cropped.jpg")));
			if (chPreTreat) {
				//tvResult.setText("预处理中......");
			}else {
				//tvResult.setText("识别中......");
			}
			// 显示选择的图片
			//showPicture(ivSelected, bitmapSelected);

			// 新线程来处理识别
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (chPreTreat) {
						bitmapTreated = OCR_ImgPreTreatment
								.doPretreatment(bitmapSelected);
						Message msg = new Message();
						msg.what = SHOWTREATEDIMG;
						myHandler.sendMessage(msg);
						textResult = doOcr(bitmapTreated, LANGUAGE);
					} else {
						bitmapTreated = OCR_ImgPreTreatment
								.converyToGrayImg(bitmapSelected);
						Message msg = new Message();
						msg.what = SHOWTREATEDIMG;
						myHandler.sendMessage(msg);
						textResult = doOcr(bitmapTreated, LANGUAGE);
					}
					Message msg2 = new Message();
					msg2.what = SHOWRESULT;
					myHandler.sendMessage(msg2);
				}

			}).start();
		}
	}

	// 拍照识别
	class cameraButtonListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(IMG_PATH, "temp.jpg")));
			activity.startActivityForResult(intent, PHOTO_CAPTURE);
		}
	};

	// 从相册选取照片并裁剪
	class selectButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("scale", true);
			intent.putExtra("return-data", false);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(IMG_PATH, "temp_cropped.jpg")));
			intent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", true); // no face detection
			activity.startActivityForResult(intent, PHOTO_RESULT);
		}

	}

	/**
	 * 进行图片识别
	 *
	 * @param bitmap
	 *            待识别图片
	 * @param language
	 *            识别语言
	 * @return 识别结果字符串
	 */
	public String doOcr(Bitmap bitmap, String language) {
		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.init(getSDPath(), language);
		// 必须加此行，tess-two要求BMP必须为此配置
		bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
		baseApi.setImage(bitmap);
		String text = baseApi.getUTF8Text();
		baseApi.clear();
		baseApi.end();
		return text;
	}

	/**
	 * 获取sd卡的路径
	 *
	 * @return 路径的字符串
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取外存目录
		}
		return sdDir.toString();
	}

	/**
	 * 调用系统图片编辑进行裁剪
	 */
	public void startPhotoCrop(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(IMG_PATH, "temp_cropped.jpg")));
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, PHOTO_RESULT);
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
			bitmap = BitmapFactory.decodeStream(activity.getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
}
