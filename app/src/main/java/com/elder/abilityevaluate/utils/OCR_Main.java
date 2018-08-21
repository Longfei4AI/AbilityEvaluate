package com.elder.abilityevaluate.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUriExposedException;
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
import com.elder.abilityevaluate.activity.BaseInformationEditActivity;
import com.elder.abilityevaluate.widget.CustomToast;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;

public class OCR_Main {

	public static final int PHOTO_CAPTURE = 0x11;// 拍照
	public static final int PHOTO_RESULT = 0x12;// 结果

	private static String LANGUAGE = "chi_sim";
	public static String IMG_PATH = getSDPath() + File.separator
			+ "ocrtest";

	//private boolean chPreTreat = false;
	//private static String textResult;
	public static Bitmap bitmapSelected;
	public static Bitmap bitmapTreated;
	public static final int SHOWRESULT = 0x101;
	public static final int SHOWTREATEDIMG = 0x102;
	private static Activity activity = null;
	private static OCR_Main main = null;

	{
		// 若文件夹不存在 首先创建文件夹
		File path = new File(IMG_PATH);
		if (!path.exists()) {
			path.mkdirs();
		}
	}
	/**
	* @Author: wlf
	* @Time: 2018/3/1 8:37
	* @Desc: 获取单例
	* @Params:
	* @Return:
	*/
	public static OCR_Main getInstance(Activity acti){
		activity = acti;
		return main == null ? new OCR_Main() : main;
	}
	/**
	* @Author: wlf
	* @Time: 2018/3/1 8:50
	* @Desc: 开始裁剪图片
	* @Params:
	* @Return:
	*/
	public void photoCapture(){
		startPhotoCrop(Uri.fromFile(new File(IMG_PATH, "temp.jpg")));
	}
	/**
	* @Author: wlf
	* @Time: 2018/3/1 8:51
	* @Desc: 获取裁剪后的图片
	* @Params:
	* @Return:
	*/
	public Bitmap photoResult(){
		bitmapSelected = decodeUriAsBitmap(Uri.fromFile(new File(IMG_PATH,
				"temp_cropped.jpg")));

		return bitmapSelected;
	}
	/**
	* @Author: wlf
	* @Time: 2018/3/1 8:35
	* @Desc: 开启线程识别图片文字
	* @Params:
	* @Return:
	*/
	public void startOrc(final Handler ocrHandler){
		// 新线程来处理识别
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				boolean isPreTreat = false;
				if(isPreTreat){
					msg.what = SHOWTREATEDIMG;
					bitmapTreated = OCR_ImgPreTreatment
							.doPretreatment(bitmapSelected);
					ocrHandler.sendMessage(msg);
				}else{
					msg.what = SHOWTREATEDIMG;
					bitmapTreated = OCR_ImgPreTreatment
							.converyToGrayImg(bitmapSelected);
					ocrHandler.sendMessage(msg);
				}

				//msg.obj = doOcr(bitmapTreated, LANGUAGE);
				//ocrHandler.sendMessage(msg);

				Message msg2 = new Message();
				msg2.what = SHOWRESULT;
				msg2.obj = doOcr(bitmapTreated, LANGUAGE);
				ocrHandler.sendMessage(msg2);
			}

		}).start();
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
				Uri.fromFile(new File(IMG_PATH, "temp.jpg")));
		try {
			activity.startActivityForResult(intent, PHOTO_CAPTURE);
		}catch (Exception e){
			e.printStackTrace();
			CustomToast.show(activity,"请打开应用程序存储权限!",1);
		}
	}

	/**
	* @Author: wlf
	* @Time: 2018/3/1 8:36
	* @Desc: 从相册选取照片并裁剪
	* @Params:
	* @Return:
	*/
	public void selectFromAlbum() {
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
