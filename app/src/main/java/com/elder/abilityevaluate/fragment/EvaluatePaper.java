/**   
 * @Title: Paper.java 
 * @Package com.xyyt.demo.fragment 
 * @author jiaone@163.com
 * @date 2015-4-10 上午12:02:18 
 * @version V1.0   
 */
package com.elder.abilityevaluate.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elder.abilityevaluate.R;

public class EvaluatePaper extends Fragment {

	public static final String[] TITLE = new String[] { "日常活动", "精神状态", "感知沟通", "社会参与"};
	private int newsType = 0;

	public EvaluatePaper(){

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_viewpaper, null);
		TextView tip = (TextView) view.findViewById(R.id.content);
		tip.setText(TITLE[newsType]);
		return view;
	}

}