/**   
 * @Title: Paper.java 
 * @Package com.xyyt.demo.fragment 
 * @author jiaone@163.com
 * @date 2015-4-10 上午12:02:18 
 * @version V1.0   
 */
package com.elder.abilityevaluate.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

import de.greenrobot.event.EventBus;

public class B2_View extends Fragment {
	public final static int SCANNIN_GREQUEST_CODE = 1;
	public final static int SCANNIN_FARMER_CODE = 2;
	public final static int EVENT_GETDATA = 0x02;

	View rootView;
	String corpCode = "";
	SharedPreferences spf;
	private TextView b_2_1_tv;
	private TextView b_2_2_tv;
	private TextView b_2_3_tv;
	private TextView b_2_score_tv;
	private TextView b_2_level_tv;
	private ImageView picture_watchIV;
	private Evaluation evaluation = null;
	private String baseId = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getActivity().getIntent();
		baseId = intent.getExtras().getString("id");
		EventBus.getDefault().register(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.b2_view_fragment, null);
		b_2_1_tv = rootView.findViewById(R.id.b_2_1_tv);
		b_2_2_tv = rootView.findViewById(R.id.b_2_2_tv);
		b_2_3_tv = rootView.findViewById(R.id.b_2_3_tv);
		b_2_score_tv = rootView.findViewById(R.id.b_2_score_TV);
		b_2_level_tv = rootView.findViewById(R.id.b_2_level_tv);
		picture_watchIV = rootView.findViewById(R.id.picture_watch);
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
		picture_watchIV.setImageURI(Uri.fromFile(new File(GlobalInfo.PIC_PATH,
				fileName)));
		switch (evaluation.getB_2_1()) {
			case 0:
				b_2_1_tv.setText(getString(R.string.b_2_1_0));
				break;
			case 1:
				b_2_1_tv.setText(getString(R.string.b_2_1_1));
				break;
			case 2:
				b_2_1_tv.setText(getString(R.string.b_2_1_2));
				break;
		}
		switch (evaluation.getB_2_2()) {
			case 0:
				b_2_2_tv.setText(getString(R.string.b_2_2_0));
				break;
			case 1:
				b_2_2_tv.setText(getString(R.string.b_2_2_1));
				break;
			case 2:
				b_2_2_tv.setText(getString(R.string.b_2_2_2));
				break;
		}
		switch (evaluation.getB_2_3()) {
			case 0:
				b_2_3_tv.setText(getString(R.string.b_2_3_0));
				break;
			case 1:
				b_2_3_tv.setText(getString(R.string.b_2_3_1));
				break;
			case 2:
				b_2_3_tv.setText(getString(R.string.b_2_3_2));
				break;
		}

		b_2_score_tv.setText(evaluation.getB_2_score()+"");

		switch (evaluation.getB_2_level()){
			case 0:
				b_2_level_tv.setText(getString(R.string.level_0));
				break;
			case 1:
				b_2_level_tv.setText(getString(R.string.level_1));
				break;
			case 2:
				b_2_level_tv.setText(getString(R.string.level_2));
				break;
			case 3:
				b_2_level_tv.setText(getString(R.string.level_3));
				break;
		}
	}

	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == EVENT_GETDATA
				&& msgEvent.fromClass == B2_View.class) {

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