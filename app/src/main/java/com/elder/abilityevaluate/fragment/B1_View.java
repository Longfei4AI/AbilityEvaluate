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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.entity.BaseInformation;
import com.elder.abilityevaluate.entity.Evaluation;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.utils.DataBaseHelper;
import com.elder.abilityevaluate.widget.CustomToast;
import com.lidroid.xutils.db.sqlite.Selector;
import de.greenrobot.event.EventBus;

public class B1_View extends Fragment {
	public final static int SCANNIN_GREQUEST_CODE = 1;
	public final static int SCANNIN_FARMER_CODE = 2;
	public final static int EVENT_GETDATA = 0x02;
	View rootView;
	String corpCode = "";
	SharedPreferences spf;
	private TextView b_1_1_tv;
	private TextView b_1_2_tv;
	private TextView b_1_3_tv;
	private TextView b_1_4_tv;
	private TextView b_1_5_tv;
	private TextView b_1_6_tv;
	private TextView b_1_7_tv;
	private TextView b_1_8_tv;
	private TextView b_1_9_tv;
	private TextView b_1_10_tv;
	private TextView b_1_score_tv;
	private TextView b_1_level_tv;
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
		rootView = inflater.inflate(R.layout.b1_view_fragment, null);
		b_1_1_tv = rootView.findViewById(R.id.b_1_1_tv);
		b_1_2_tv = rootView.findViewById(R.id.b_1_2_tv);
		b_1_3_tv = rootView.findViewById(R.id.b_1_3_tv);
		b_1_4_tv = rootView.findViewById(R.id.b_1_4_tv);
		b_1_5_tv = rootView.findViewById(R.id.b_1_5_tv);
		b_1_6_tv = rootView.findViewById(R.id.b_1_6_tv);
		b_1_7_tv = rootView.findViewById(R.id.b_1_7_tv);
		b_1_8_tv = rootView.findViewById(R.id.b_1_8_tv);
		b_1_9_tv = rootView.findViewById(R.id.b_1_9_tv);
		b_1_10_tv = rootView.findViewById(R.id.b_1_10_tv);
		b_1_score_tv = rootView.findViewById(R.id.b_1_score_tv);
		b_1_level_tv = rootView.findViewById(R.id.b_1_level_tv);
		this.initData();
		return rootView;
	}

	/**
	* @Author: wlf
	* @Time: 2018/2/10 10:13
	* @Desc: 填充显示数据
	* @Params:
	* * @Return: void
	*/
	private void initData(){
		evaluation = DataBaseHelper.getInstance(this.getActivity(),Evaluation.class).
				findFirst(Selector.from(Evaluation.class).where("baseInfoId","=",baseId));
		if(null == evaluation){
			CustomToast.show(this.getActivity(),"获取数据失败！",CustomToast.LENGTH_SHORT);
			return;
		}

		switch (evaluation.getB_1_1()){
			case 0:
				b_1_1_tv.setText(getString(R.string.b_1_1_0));
				break;
			case 5:
				b_1_1_tv.setText(getString(R.string.b_1_1_5));
				break;
			case 10:
				b_1_1_tv.setText(getString(R.string.b_1_1_10));
				break;
		}
		switch (evaluation.getB_1_2()){
			case 0:
				b_1_2_tv.setText(getString(R.string.b_1_2_0));
				break;
			case 5:
				b_1_2_tv.setText(getString(R.string.b_1_2_5));
				break;
		}
		switch (evaluation.getB_1_3()){
			case 0:
				b_1_3_tv.setText(getString(R.string.b_1_3_0));
				break;
			case 5:
				b_1_3_tv.setText(getString(R.string.b_1_3_5));
				break;
		}
		switch (evaluation.getB_1_4()){
			case 0:
				b_1_4_tv.setText(getString(R.string.b_1_4_0));
				break;
			case 5:
				b_1_4_tv.setText(getString(R.string.b_1_4_5));
				break;
			case 10:
				b_1_4_tv.setText(getString(R.string.b_1_4_10));
				break;
		}
		switch (evaluation.getB_1_5()){
			case 0:
				b_1_5_tv.setText(getString(R.string.b_1_5_0));
				break;
			case 5:
				b_1_5_tv.setText(getString(R.string.b_1_5_5));
				break;
			case 10:
				b_1_5_tv.setText(getString(R.string.b_1_5_10));
				break;
		}

		switch (evaluation.getB_1_6()){
			case 0:
				b_1_6_tv.setText(getString(R.string.b_1_6_0));
				break;
			case 5:
				b_1_6_tv.setText(getString(R.string.b_1_6_5));
				break;
			case 10:
				b_1_6_tv.setText(getString(R.string.b_1_6_10));
				break;
		}

		switch (evaluation.getB_1_7()){
			case 0:
				b_1_7_tv.setText(getString(R.string.b_1_7_0));
				break;
			case 5:
				b_1_7_tv.setText(getString(R.string.b_1_7_5));
				break;
			case 10:
				b_1_7_tv.setText(getString(R.string.b_1_7_10));
				break;
		}

		switch (evaluation.getB_1_8()){
			case 0:
				b_1_8_tv.setText(getString(R.string.b_1_8_0));
				break;
			case 5:
				b_1_8_tv.setText(getString(R.string.b_1_8_5));
				break;
			case 10:
				b_1_8_tv.setText(getString(R.string.b_1_8_10));
				break;
			case 15:
				b_1_8_tv.setText(getString(R.string.b_1_8_15));
				break;
		}

		switch (evaluation.getB_1_9()){
			case 0:
				b_1_9_tv.setText(getString(R.string.b_1_9_0));
				break;
			case 5:
				b_1_9_tv.setText(getString(R.string.b_1_9_5));
				break;
			case 10:
				b_1_9_tv.setText(getString(R.string.b_1_9_10));
				break;
			case 15:
				b_1_9_tv.setText(getString(R.string.b_1_9_15));
				break;
		}

		switch (evaluation.getB_1_10()){
			case 0:
				b_1_10_tv.setText(getString(R.string.b_1_10_0));
				break;
			case 5:
				b_1_10_tv.setText(getString(R.string.b_1_10_5));
				break;
			case 10:
				b_1_10_tv.setText(getString(R.string.b_1_10_10));
				break;
		}

		b_1_score_tv.setText(evaluation.getB_1_score()+"");

		switch (evaluation.getB_1_level()){
			case 0:
				b_1_level_tv.setText(getString(R.string.level_0));
				break;
			case 1:
				b_1_level_tv.setText(getString(R.string.level_1));
				break;
			case 2:
				b_1_level_tv.setText(getString(R.string.level_2));
				break;
			case 3:
				b_1_level_tv.setText(getString(R.string.level_3));
				break;
		}
	}

	/**
	* @Author: wlf
	* @Time: 2018/2/11 17:26
	* @Desc: 相应EventBus事件
	* @Params: msgEvent 发送的事件
	* @Return: void
	*/
	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == EVENT_GETDATA
				&& msgEvent.fromClass == B1_View.class) {

		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}