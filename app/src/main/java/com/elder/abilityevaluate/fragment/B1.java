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

public class B1 extends BaseFragment {
	public final static int SCANNIN_GREQUEST_CODE = 1;
	public final static int SCANNIN_FARMER_CODE = 2;
	public final static int EVENT_GETDATA = 0x02;
	View rootView;
	String corpCode = "";
	SharedPreferences spf;
	private RadioGroup b_1_1_rg;
	private RadioGroup b_1_2_rg;
	private RadioGroup b_1_3_rg;
	private RadioGroup b_1_4_rg;
	private RadioGroup b_1_5_rg;
	private RadioGroup b_1_6_rg;
	private RadioGroup b_1_7_rg;
	private RadioGroup b_1_8_rg;
	private RadioGroup b_1_9_rg;
	private RadioGroup b_1_10_rg;
	private TextView b_1_score_tv;
	private RadioGroup b_1_level_rg;
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
		rootView = inflater.inflate(R.layout.b1_edit_fragment, null);
		b_1_1_rg = rootView.findViewById(R.id.b_1_1_RG);
		b_1_1_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_1_2_rg = rootView.findViewById(R.id.b_1_2_RG);
		b_1_2_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_1_3_rg = rootView.findViewById(R.id.b_1_3_RG);
		b_1_3_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_1_4_rg = rootView.findViewById(R.id.b_1_4_RG);
		b_1_4_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_1_5_rg = rootView.findViewById(R.id.b_1_5_RG);
		b_1_5_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_1_6_rg = rootView.findViewById(R.id.b_1_6_RG);
		b_1_6_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_1_7_rg = rootView.findViewById(R.id.b_1_7_RG);
		b_1_7_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_1_8_rg = rootView.findViewById(R.id.b_1_8_RG);
		b_1_8_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_1_9_rg = rootView.findViewById(R.id.b_1_9_RG);
		b_1_9_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_1_10_rg = rootView.findViewById(R.id.b_1_10_RG);
		b_1_10_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_1_score_tv = rootView.findViewById(R.id.b_1_score_TV);
		b_1_level_rg = rootView.findViewById(R.id.b_1_level_RG);

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
	private void initData(){
		evaluation = DataBaseHelper.getInstance(this.getActivity(),Evaluation.class).
				findFirst(Selector.from(Evaluation.class).where("baseInfoId","=",baseId));
		if(null == evaluation){
			CustomToast.show(this.getActivity(),"获取数据失败！",CustomToast.LENGTH_SHORT);
			return;
		}
		switch (evaluation.getB_1_1()){
			case 0:
				b_1_1_rg.check(R.id.b_1_1_0_RB);
				break;
			case 5:
				b_1_1_rg.check(R.id.b_1_1_5_RB);
				break;
			case 10:
				b_1_1_rg.check(R.id.b_1_1_10_RB);
				break;
		}
		switch (evaluation.getB_1_2()){
			case 0:
				b_1_2_rg.check(R.id.b_1_2_0_RB);
				break;
			case 5:
				b_1_2_rg.check(R.id.b_1_2_5_RB);
				break;
		}
		switch (evaluation.getB_1_3()){
			case 0:
				b_1_3_rg.check(R.id.b_1_3_0_RB);
				break;
			case 5:
				b_1_3_rg.check(R.id.b_1_3_5_RB);
				break;
		}
		switch (evaluation.getB_1_4()){
			case 0:
				b_1_4_rg.check(R.id.b_1_4_0_RB);
				break;
			case 5:
				b_1_4_rg.check(R.id.b_1_4_5_RB);
				break;
			case 10:
				b_1_4_rg.check(R.id.b_1_4_10_RB);
				break;
		}
		switch (evaluation.getB_1_5()){
			case 0:
				b_1_5_rg.check(R.id.b_1_5_0_RB);
				break;
			case 5:
				b_1_5_rg.check(R.id.b_1_5_5_RB);
				break;
			case 10:
				b_1_5_rg.check(R.id.b_1_5_10_RB);
				break;
		}

		switch (evaluation.getB_1_6()){
			case 0:
				b_1_6_rg.check(R.id.b_1_6_0_RB);
				break;
			case 5:
				b_1_6_rg.check(R.id.b_1_6_5_RB);
				break;
			case 10:
				b_1_6_rg.check(R.id.b_1_6_10_RB);
				break;
		}

		switch (evaluation.getB_1_7()){
			case 0:
				b_1_7_rg.check(R.id.b_1_7_0_RB);
				break;
			case 5:
				b_1_7_rg.check(R.id.b_1_7_5_RB);
				break;
			case 10:
				b_1_7_rg.check(R.id.b_1_7_10_RB);
				break;
		}

		switch (evaluation.getB_1_8()){
			case 0:
				b_1_8_rg.check(R.id.b_1_8_0_RB);
				break;
			case 5:
				b_1_8_rg.check(R.id.b_1_8_5_RB);
				break;
			case 10:
				b_1_8_rg.check(R.id.b_1_8_10_RB);
				break;
			case 15:
				b_1_8_rg.check(R.id.b_1_8_15_RB);
				break;
		}

		switch (evaluation.getB_1_9()){
			case 0:
				b_1_9_rg.check(R.id.b_1_9_0_RB);
				break;
			case 5:
				b_1_9_rg.check(R.id.b_1_9_5_RB);
				break;
			case 10:
				b_1_9_rg.check(R.id.b_1_9_10_RB);
				break;
			case 15:
				b_1_9_rg.check(R.id.b_1_9_15_RB);
				break;
		}

		switch (evaluation.getB_1_10()){
			case 0:
				b_1_10_rg.check(R.id.b_1_10_0_RB);
				break;
			case 5:
				b_1_10_rg.check(R.id.b_1_10_5_RB);
				break;
			case 10:
				b_1_10_rg.check(R.id.b_1_10_10_RB);
				break;
		}

		b_1_score_tv.setText(evaluation.getB_1_score() < 0 ? "" : evaluation.getB_1_score()+"");

		switch (evaluation.getB_1_level()){
			case 0:
				b_1_level_rg.check(R.id.b_1_level_0_RB);
				break;
			case 1:
				b_1_level_rg.check(R.id.b_1_level_1_RB);
				break;
			case 2:
				b_1_level_rg.check(R.id.b_1_level_2_RB);
				break;
			case 3:
				b_1_level_rg.check(R.id.b_1_level_3_RB);
				break;
		}
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/11 17:27
	* @Desc: 保存数据
	* @Params:
	* @Return: void
	*/
	public void save(){
		evaluation = DataBaseHelper.getInstance(this.getActivity(), Evaluation.class).
				findFirst(Selector.from(Evaluation.class).where("baseInfoId", "=", baseId));
		if(null == evaluation){
			CustomToast.show(this.getActivity(),"B1数据异常！",CustomToast.LENGTH_SHORT);
			return;
		}
		switch (b_1_1_rg.getCheckedRadioButtonId()){
			case R.id.b_1_1_0_RB :
				evaluation.setB_1_1(0);
				break;
			case R.id.b_1_1_5_RB :
				evaluation.setB_1_1(5);
				break;
			case R.id.b_1_1_10_RB :
				evaluation.setB_1_1(10);
				break;
		}
		switch (b_1_2_rg.getCheckedRadioButtonId()){
			case R.id.b_1_2_0_RB :
				evaluation.setB_1_2(0);
				break;
			case R.id.b_1_2_5_RB :
				evaluation.setB_1_2(5);
				break;
		}
		switch (b_1_3_rg.getCheckedRadioButtonId()){
			case R.id.b_1_3_0_RB :
				evaluation.setB_1_3(0);
				break;
			case R.id.b_1_3_5_RB :
				evaluation.setB_1_3(5);
				break;
		}
		switch (b_1_4_rg.getCheckedRadioButtonId()){
			case R.id.b_1_4_0_RB :
				evaluation.setB_1_4(0);
				break;
			case R.id.b_1_4_5_RB :
				evaluation.setB_1_4(5);
				break;
			case R.id.b_1_4_10_RB :
				evaluation.setB_1_4(10);
				break;
		}
		switch (b_1_5_rg.getCheckedRadioButtonId()){
			case R.id.b_1_5_0_RB :
				evaluation.setB_1_5(0);
				break;
			case R.id.b_1_5_5_RB :
				evaluation.setB_1_5(5);
				break;
			case R.id.b_1_5_10_RB :
				evaluation.setB_1_5(10);
				break;
		}
		switch (b_1_6_rg.getCheckedRadioButtonId()){
			case R.id.b_1_6_0_RB :
				evaluation.setB_1_6(0);
				break;
			case R.id.b_1_6_5_RB :
				evaluation.setB_1_6(5);
				break;
			case R.id.b_1_6_10_RB :
				evaluation.setB_1_6(10);
				break;
		}
		switch (b_1_7_rg.getCheckedRadioButtonId()){
			case R.id.b_1_7_0_RB :
				evaluation.setB_1_7(0);
				break;
			case R.id.b_1_7_5_RB :
				evaluation.setB_1_7(5);
				break;
			case R.id.b_1_7_10_RB :
				evaluation.setB_1_7(10);
				break;
		}
		switch (b_1_8_rg.getCheckedRadioButtonId()){
			case R.id.b_1_8_0_RB :
				evaluation.setB_1_8(0);
				break;
			case R.id.b_1_8_5_RB :
				evaluation.setB_1_8(5);
				break;
			case R.id.b_1_8_10_RB :
				evaluation.setB_1_8(10);
				break;
			case R.id.b_1_8_15_RB :
				evaluation.setB_1_8(15);
				break;
		}
		switch (b_1_9_rg.getCheckedRadioButtonId()){
			case R.id.b_1_9_0_RB :
				evaluation.setB_1_9(0);
				break;
			case R.id.b_1_9_5_RB :
				evaluation.setB_1_9(5);
				break;
			case R.id.b_1_9_10_RB :
				evaluation.setB_1_9(10);
				break;
			case R.id.b_1_9_15_RB :
				evaluation.setB_1_9(15);
				break;
		}
		switch (b_1_10_rg.getCheckedRadioButtonId()){
			case R.id.b_1_10_0_RB :
				evaluation.setB_1_10(0);
				break;
			case R.id.b_1_10_5_RB :
				evaluation.setB_1_10(5);
				break;
			case R.id.b_1_10_10_RB :
				evaluation.setB_1_10(10);
				break;
		}

		int score = -1;
		if(evaluation.getB_1_1() > -1
				&& evaluation.getB_1_2() > -1
				&& evaluation.getB_1_3() > -1
				&& evaluation.getB_1_4() > -1
				&& evaluation.getB_1_5() > -1
				&& evaluation.getB_1_6() > -1
				&& evaluation.getB_1_7() > -1
				&& evaluation.getB_1_8() > -1
				&& evaluation.getB_1_9() > -1
				&& evaluation.getB_1_10() > -1
				){
			score = evaluation.getB_1_1()
					+ evaluation.getB_1_2()
					+ evaluation.getB_1_3()
					+ evaluation.getB_1_4()
					+ evaluation.getB_1_5()
					+ evaluation.getB_1_6()
					+ evaluation.getB_1_7()
					+ evaluation.getB_1_8()
					+ evaluation.getB_1_9()
					+ evaluation.getB_1_10();
		}
		if(score > -1){
			b_1_score_tv.setText(score+"");
		}
		evaluation.setB_1_score(score);

		if( score == 100 ){
			evaluation.setB_1_level(0);
			b_1_level_rg.check(R.id.b_1_level_0_RB);
		}else if( score >= 61 && score <= 99 ){
			evaluation.setB_1_level(1);
			b_1_level_rg.check(R.id.b_1_level_1_RB);
		}else if( score >= 41 && score <= 60 ){
			evaluation.setB_1_level(2);
			b_1_level_rg.check(R.id.b_1_level_2_RB);
		}else if( score >= 0 && score <= 40 ){
			evaluation.setB_1_level(3);
			b_1_level_rg.check(R.id.b_1_level_3_RB);
		}

		DataBaseHelper.getInstance(this.getActivity(),Evaluation.class).update(evaluation);
		//CustomToast.show(this.getActivity(), "B1保存成功", Toast.LENGTH_SHORT);
	}

	@Override
	public void refreshData(String baseId) {

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
				&& msgEvent.fromClass == B1.class) {

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