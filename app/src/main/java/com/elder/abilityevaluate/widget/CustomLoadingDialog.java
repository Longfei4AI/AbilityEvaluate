package com.elder.abilityevaluate.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.elder.abilityevaluate.R;

public class CustomLoadingDialog extends Dialog {
	private LayoutInflater inflater;
	private Context mContext;
	private LayoutParams lp;
	private ImageView loadingImageView;
	private TextView tv;
	private AnimationDrawable anim;

	/**
	 * @param context
	 */
	public CustomLoadingDialog(Context context) {
		super(context, R.style.Custom_LoadingDialog);
		setCanceledOnTouchOutside(false);
		this.mContext = context;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.setCanceledOnTouchOutside(false);
		this.setCancelable(false);
		View layout = inflater.inflate(R.layout.custom_loading_dialog, null);
		setContentView(layout);
		lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
//		lp.dimAmount = 0; //
		lp.alpha = 1.0f;
		getWindow().setAttributes(lp);
		loadingImageView = (ImageView) findViewById(R.id.loading_imageView_info);
		tv = (TextView) findViewById(R.id.load_info_text);
		anim = (AnimationDrawable) loadingImageView.getBackground();
		anim.start();
	}

	public void hideLoading() {
		anim.stop();
		dismiss();
	}
	
	public void showLoading() {
		anim.start();
		show();
	}

	public void setMsg(String msg) {
		tv.setText(msg);
	}

	public void setMsg(int msgId) {
		tv.setText(msgId);
	}
}
