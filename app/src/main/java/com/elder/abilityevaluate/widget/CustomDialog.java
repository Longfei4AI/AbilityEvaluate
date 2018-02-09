package com.elder.abilityevaluate.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.utils.SoundUtils;

/**
 * @author wlf 自定义弹出窗口
 */

public class CustomDialog extends Dialog {

	public static final int TYPE_MESSAGE = 0;
	public static final int TYPE_WARNNING = 1;
	public static final int TYPE_QUESTION = 2;
	public static final int TYPE_INFO = 3;
	public static final int TYPE_ERROR = 4;

	String tilte;
	String msg;
	Context mcontext;
	ViewGroup vg;
	private int type = 0;

	public CustomDialog(Context context, int theme) {
		super(context, theme);
		mcontext = context;
	}

	public CustomDialog(Context context) {
		super(context);
		mcontext = context;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		((TextView) (this.findViewById(R.id.title))).setText(title);
	}
	@Override
	public void setCanceledOnTouchOutside(boolean cancel) {
		super.setCanceledOnTouchOutside(cancel);
	}
	public void setTitle(int resId) {
		tilte = mcontext.getString(resId);
		setTitle(tilte);
	}

	/**
	 * 设置内容
	 * 
	 * @param msg
	 */
	public void setMessage(String msg) {
		((TextView) this.findViewById(R.id.message)).setText(msg);
	}

	public void setMessage(int resId) {
		msg = mcontext.getString(resId);
		setMessage(msg);
	}

	/**
	 * 设置弹出类型
	 * 
	 * @param type
	 */
	public void setType(int type) {
		Drawable icon = mcontext.getResources().getDrawable(
				R.drawable.icon_dialog_default);
		this.type = type; 
		switch (type) {
		case TYPE_WARNNING:
			icon = mcontext.getResources().getDrawable(
					R.drawable.icon_dialog_warn);
			break;
		case TYPE_INFO:
			icon = mcontext.getResources().getDrawable(R.drawable.icon_dialog_default);
			break;
		case TYPE_MESSAGE:
			icon = mcontext.getResources().getDrawable(
					R.drawable.icon_dialog_default);
			break;
		case TYPE_QUESTION:
			icon = mcontext.getResources().getDrawable(
					R.drawable.icon_dialog_quest);
			break;
		case TYPE_ERROR:
			icon = mcontext.getResources().getDrawable(
					R.drawable.icon_dialog_error);
			break;
		}
		if (null != icon) {
			((ImageView) this.findViewById(R.id.icon)).setImageDrawable(icon);
		}
	}

	/**
	 * Helper class for creating a custom dialog
	 */

	public static class Builder {

		private Context context;

		private String title;

		private String message;

		private String positiveButtonText;

		private String negativeButtonText;

		private View contentView;

		private Drawable iconDrawable;
		
		private boolean canceledOutSide = true;
		
		private ViewGroup vg;

		private OnClickListener positiveButtonClickListener,
				negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		/**
		 * Set the Dialog message from String
		 * @return
		 */

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * @return
		 */

		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;

		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setIcon(int icon) {
			this.iconDrawable = context.getResources().getDrawable(icon);
			return this;
		}

		/**
		 * Set a custom content view for the Dialog. If a message is set, the
		 * contentView is not added to the Dialog...
		 * 
		 * @param v
		 * @return
		 */

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */

		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the positive button text and it's listener
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */

		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the negative button resource and it's listener
		 * 
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */

		public Builder setNegativeButton(int negativeButtonText,

		OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;

		}

		/**
		 * Set the negative button text and it's listener
		 * 
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */

		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;

		}

		/**
		 * Create the custom dialog
		 */

		public CustomDialog create() {

			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context,
					R.style.Custom_LoadingDialog);
			dialog.setCanceledOnTouchOutside(canceledOutSide);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.custom_alert_dialog, null);
//			dialog.show();
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
//			if (null != iconDrawable) {
//				((ImageView) layout.findViewById(R.id.icon))
//						.setImageDrawable(iconDrawable);
//			}
			
			((TextView) layout.findViewById(R.id.title)).setText(title);
			dialog.tilte = title;
			// set the confirm button
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.positiveButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
									dialog.cancel();
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}

			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
									dialog.cancel();
								}
							});
				}else{
					((Button) layout.findViewById(R.id.negativeButton))
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							dialog.cancel();
						}
					});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			if (positiveButtonText == null && null == negativeButtonText) {
				layout.findViewById(R.id.buttons).setVisibility(View.GONE);
			}
			// set the content message
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			} else if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
			}
			vg = (ViewGroup) layout.findViewById(R.id.dialog_layout);
			dialog.setOnShowListener(new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					AnimationSet animationSet = new AnimationSet(true);
					RotateAnimation rotateAnimation = new RotateAnimation(0, 1, 
							Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
					rotateAnimation.setDuration(400);
					animationSet.addAnimation(rotateAnimation);
					animationSet.setInterpolator(new CycleInterpolator(8));
					vg.startAnimation(animationSet);
				}
			});
			return dialog;
		}

		public boolean isCanceledOutSide() {
			return canceledOutSide;
		}

		public void setCanceledOutSide(boolean canceledOutSide) {
			this.canceledOutSide = canceledOutSide;
		}
	}

	@Override
	public void show() {
		try {
			if (this.getOwnerActivity() != null
					&& (this.getOwnerActivity().isFinishing())) {
				return;
			}
			Window window = this.getWindow();
			window.setGravity(Gravity.CENTER | Gravity.CENTER);// 此处可以设置dialog显示的位置
			if (tilte == null || "".equals(tilte)) {
				tilte = mcontext.getResources().getString(R.string.tip);
			}
			this.setTitle(tilte);
			super.show();
			switch (type) {
			case TYPE_WARNNING:
				new SoundUtils(mcontext).warnning();
				break;
			case TYPE_INFO:
				new SoundUtils(mcontext).info();
				break;
			case TYPE_MESSAGE:
				new SoundUtils(mcontext).info();
				break;
			case TYPE_QUESTION:
				new SoundUtils(mcontext).confirm();
				break;
			case TYPE_ERROR:
				new SoundUtils(mcontext).error();
				break;
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void dismiss() {
		if(this.isShowing()){
			new SoundUtils(mcontext).dialogClose();
		}
		super.dismiss();
	}

}