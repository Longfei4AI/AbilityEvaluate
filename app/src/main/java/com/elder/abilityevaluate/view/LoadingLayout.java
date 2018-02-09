/**   
 * @Title: LoadingLayout.java 
 * @Package com.jr001.widget 
 * @Description: 页面加载布局
 * @author jiaone@163.com
 * @date 2015-4-2 上午10:34:02 
 * @version V1.0   
 */
package com.elder.abilityevaluate.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elder.abilityevaluate.R;

public class LoadingLayout extends LinearLayout{
	boolean isAnimation = true;
	LinearLayout linearLayout;
	TextView tv;
	Context mContext;
	CallBack callBack;
	public LoadingLayout(Context context,CallBack cb) {
		super(context);
		mContext = context;
		callBack = cb;
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_loading_layout, this);
        linearLayout = (LinearLayout)findViewById(R.id.root);
        tv = (TextView)findViewById(R.id.msg);
	}
	
	public void setMsg(int resId){
		tv.setText(mContext.getResources().getString(resId));
	}
	
	public void destroy(){
		AnimationSet animationSet = new AnimationSet(true);
		if(linearLayout==null){
			return;
		}
//		Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0f,-90f,linearLayout.getMeasuredWidth(),linearLayout.getMeasuredHeight()/2,0,true);
//		rotate3dAnimation.setDuration(600);
//		animationSet.addAnimation(rotate3dAnimation);
		Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.zoom_exit);
		animationSet.addAnimation(animation);
		animationSet.setInterpolator(new AnticipateInterpolator(2.0f));
		animationSet.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				linearLayout.setVisibility(View.GONE);
				callBack.dispear();
			}
		});
		linearLayout.startAnimation(animationSet);
	}
	public interface CallBack{
		public void dispear();
	}
}
