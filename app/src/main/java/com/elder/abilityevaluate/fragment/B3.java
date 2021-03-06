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
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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

public class B3 extends BaseFragment {
	public final static int SCANNIN_GREQUEST_CODE = 1;
	public final static int SCANNIN_FARMER_CODE = 2;
	public final static int EVENT_GETDATA = 0x02;
	View rootView;
	String corpCode = "";
	SharedPreferences spf;
	private RadioGroup b_3_1_rg;
	private RadioGroup b_3_2_rg;
	private RadioGroup b_3_3_rg;
	private RadioGroup b_3_4_rg;
	private TextView b_3_score_tv;
	private RadioGroup b_3_level_rg;
	private Evaluation evaluation = null;
	private String baseId = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseId = getActivity().getIntent().getExtras().getString("id");
		EventBus.getDefault().register(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.b3_edit_fragment, null);
		b_3_1_rg = rootView.findViewById(R.id.b_3_1_RG);
		b_3_1_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_3_2_rg = rootView.findViewById(R.id.b_3_2_RG);
		b_3_2_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_3_3_rg = rootView.findViewById(R.id.b_3_3_RG);
		b_3_3_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_3_4_rg = rootView.findViewById(R.id.b_3_4_RG);
		b_3_4_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_3_score_tv = rootView.findViewById(R.id.b_3_score_TV);
		b_3_level_rg = rootView.findViewById(R.id.b_3_level_RG);
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
		switch (evaluation.getB_3_1()) {
			case 0:
				b_3_1_rg.check(R.id.b_3_1_0_RB);
				break;
			case 1:
				b_3_1_rg.check(R.id.b_3_1_1_RB);
				break;
			case 2:
				b_3_1_rg.check(R.id.b_3_1_2_RB);
				break;
			case 3:
				b_3_1_rg.check(R.id.b_3_1_3_RB);
				break;
		}

		switch (evaluation.getB_3_2()) {
			case 0:
				b_3_2_rg.check(R.id.b_3_2_0_RB);
				break;
			case 1:
				b_3_2_rg.check(R.id.b_3_2_1_RB);
				break;
			case 2:
				b_3_2_rg.check(R.id.b_3_2_2_RB);
				break;
			case 3:
				b_3_2_rg.check(R.id.b_3_2_3_RB);
				break;
			case 4:
				b_3_2_rg.check(R.id.b_3_2_4_RB);
				break;
		}

		switch (evaluation.getB_3_3()) {
			case 0:
				b_3_3_rg.check(R.id.b_3_3_0_RB);
				break;
			case 1:
				b_3_3_rg.check(R.id.b_3_3_1_RB);
				break;
			case 2:
				b_3_3_rg.check(R.id.b_3_3_2_RB);
				break;
			case 3:
				b_3_3_rg.check(R.id.b_3_3_3_RB);
				break;
			case 4:
				b_3_3_rg.check(R.id.b_3_3_4_RB);
				break;
		}

		switch (evaluation.getB_3_4()) {
			case 0:
				b_3_4_rg.check(R.id.b_3_4_0_RB);
				break;
			case 1:
				b_3_4_rg.check(R.id.b_3_4_1_RB);
				break;
			case 2:
				b_3_4_rg.check(R.id.b_3_4_2_RB);
				break;
			case 3:
				b_3_4_rg.check(R.id.b_3_4_3_RB);
				break;
		}

		b_3_score_tv.setText(evaluation.getB_3_score() < 0 ? "" : evaluation.getB_3_score()+"");

		switch (evaluation.getB_3_level()){
			case 0:
				b_3_level_rg.check(R.id.b_3_level_0_RB);
				break;
			case 1:
				b_3_level_rg.check(R.id.b_3_level_1_RB);
				break;
			case 2:
				b_3_level_rg.check(R.id.b_3_level_2_RB);
				break;
			case 3:
				b_3_level_rg.check(R.id.b_3_level_3_RB);
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
			CustomToast.show(this.getActivity(),"B3数据异常！",CustomToast.LENGTH_SHORT);
			return;
		}
		switch (b_3_1_rg.getCheckedRadioButtonId()) {
			case R.id.b_3_1_0_RB:
				evaluation.setB_3_1(0);
				break;
			case R.id.b_3_1_1_RB:
				evaluation.setB_3_1(1);
				break;
			case R.id.b_3_1_2_RB:
				evaluation.setB_3_1(2);
				break;
			case R.id.b_3_1_3_RB:
				evaluation.setB_3_1(3);
				break;
		}
		switch (b_3_2_rg.getCheckedRadioButtonId()) {
			case R.id.b_3_2_0_RB:
				evaluation.setB_3_2(0);
				break;
			case R.id.b_3_2_1_RB:
				evaluation.setB_3_2(1);
				break;
			case R.id.b_3_2_2_RB:
				evaluation.setB_3_2(2);
				break;
			case R.id.b_3_2_3_RB:
				evaluation.setB_3_2(3);
				break;
			case R.id.b_3_2_4_RB:
				evaluation.setB_3_2(4);
				break;
		}
		switch (b_3_3_rg.getCheckedRadioButtonId()) {
			case R.id.b_3_3_0_RB:
				evaluation.setB_3_3(0);
				break;
			case R.id.b_3_3_1_RB:
				evaluation.setB_3_3(1);
				break;
			case R.id.b_3_3_2_RB:
				evaluation.setB_3_3(2);
				break;
			case R.id.b_3_3_3_RB:
				evaluation.setB_3_3(3);
				break;
			case R.id.b_3_3_4_RB:
				evaluation.setB_3_3(4);
				break;
		}

		switch (b_3_4_rg.getCheckedRadioButtonId()) {
			case R.id.b_3_4_0_RB:
				evaluation.setB_3_4(0);
				break;
			case R.id.b_3_4_1_RB:
				evaluation.setB_3_4(1);
				break;
			case R.id.b_3_4_2_RB:
				evaluation.setB_3_4(2);
				break;
			case R.id.b_3_4_3_RB:
				evaluation.setB_3_4(3);
				break;
		}

		int score = -1;
		if(evaluation.getB_3_1() > -1
				&& evaluation.getB_3_2() > -1
				&& evaluation.getB_3_3() > -1
				&& evaluation.getB_3_4() > -1
				){
			score = evaluation.getB_3_1()
					+ evaluation.getB_3_2()
					+ evaluation.getB_3_3()
					+ evaluation.getB_3_4();
		}
		if(score > -1){
			b_3_score_tv.setText(score+"");

			if( ( (evaluation.getB_3_1() == 0 || evaluation.getB_3_1() == 1) && ( evaluation.getB_3_2() == 4 || evaluation.getB_3_3() == 4 ) )
					|| evaluation.getB_3_4() == 3
					|| ( evaluation.getB_3_4() >= 2 ) ){
				evaluation.setB_3_level(3);
				b_3_level_rg.check(R.id.b_3_level_3_RB);
			}else if( ( (evaluation.getB_3_1() == 0 && ( evaluation.getB_3_2() == 3 || evaluation.getB_3_3() == 3 ))
												|| evaluation.getB_3_4() == 2 )
					||( evaluation.getB_3_1() == 1
												&& ( evaluation.getB_3_2() >= 3 || evaluation.getB_3_3() >= 3 )
												&& (evaluation.getB_3_4() >= 2) ) ){
				evaluation.setB_3_level(2);
				b_3_level_rg.check(R.id.b_3_level_2_RB);
			}else if( (evaluation.getB_3_1() == 0
												&& ( evaluation.getB_3_2() == 2 || evaluation.getB_3_3() == 2 ) )
					|| evaluation.getB_3_4() == 1){
				evaluation.setB_3_level(1);
				b_3_level_rg.check(R.id.b_3_level_1_RB);
			}else if( evaluation.getB_3_1() == 0
					&& ( evaluation.getB_3_2() == 0 || evaluation.getB_3_2() == 1 )
					&& ( evaluation.getB_3_3() == 0 || evaluation.getB_3_3() == 1 )
					&& evaluation.getB_3_4() == 0 ){
				evaluation.setB_3_level(0);
				b_3_level_rg.check(R.id.b_3_level_0_RB);
			}
		}
		evaluation.setB_3_score(score);

		DataBaseHelper.getInstance(this.getActivity(),Evaluation.class).update(evaluation);
		//CustomToast.show(this.getActivity(), "B3保存成功", Toast.LENGTH_SHORT);
	}
	@Override
	public void refreshData(String baseId) {

	}
	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == EVENT_GETDATA
				&& msgEvent.fromClass == B3.class) {

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