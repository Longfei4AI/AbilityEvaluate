/**   
* @Title: CustomViewPaper.java
* @Package com.custom.wms.view
* @Description: TODO(用一句话描述该文件做什么) 
* @author jiaone@163.com
* @date 2015-8-4 下午3:28:23 
* @version V1.0   
*/
package com.elder.abilityevaluate.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPaper extends ViewPager{

	// 滑动距离及坐标
	private float xDistance, yDistance, xLast, yLast;
	
	public CustomViewPaper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public CustomViewPaper(Context context) {
		super(context);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;
			if (xDistance > yDistance) {
				return true; // 表示向下传递事件
			}
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	
}
