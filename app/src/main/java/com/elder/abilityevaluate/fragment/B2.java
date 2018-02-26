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

public class B2 extends BaseFragment {
	public final static int SCANNIN_GREQUEST_CODE = 1;
	public final static int SCANNIN_FARMER_CODE = 2;
	public final static int EVENT_GETDATA = 0x02;
	View rootView;
	String corpCode = "";
	SharedPreferences spf;
	private RadioGroup b_2_1_rg;
	private RadioGroup b_2_2_rg;
	private RadioGroup b_2_3_rg;
	private TextView b_2_score_tv;
	private RadioGroup b_2_level_rg;
	private Evaluation evaluation = null;
	private String baseId = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseId = getActivity().getIntent().getExtras().getString("id");
		System.out.println("baseId=="+baseId);
		EventBus.getDefault().register(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.b2_edit_fragment, null);
		b_2_1_rg = rootView.findViewById(R.id.b_2_1_RG);
		b_2_1_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_2_2_rg = rootView.findViewById(R.id.b_2_2_RG);
		b_2_2_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_2_3_rg = rootView.findViewById(R.id.b_2_3_RG);
		b_2_3_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				save();
			}
		});
		b_2_score_tv = rootView.findViewById(R.id.b_2_score_TV);
		b_2_level_rg = rootView.findViewById(R.id.b_2_level_RG);
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
		switch (evaluation.getB_2_1()) {
			case 0:
				b_2_1_rg.check(R.id.b_2_1_0_RB);
				break;
			case 1:
				b_2_1_rg.check(R.id.b_2_1_1_RB);
				break;
			case 2:
				b_2_1_rg.check(R.id.b_2_1_2_RB);
				break;
		}
		switch (evaluation.getB_2_2()) {
			case 0:
				b_2_2_rg.check(R.id.b_2_2_0_RB);
				break;
			case 1:
				b_2_2_rg.check(R.id.b_2_2_1_RB);
				break;
			case 2:
				b_2_2_rg.check(R.id.b_2_2_2_RB);
				break;
		}
		switch (evaluation.getB_2_3()) {
			case 0:
				b_2_3_rg.check(R.id.b_2_3_0_RB);
				break;
			case 1:
				b_2_3_rg.check(R.id.b_2_3_1_RB);
				break;
			case 2:
				b_2_3_rg.check(R.id.b_2_3_2_RB);
				break;
		}

		b_2_score_tv.setText(evaluation.getB_2_score() < 0 ? "" : evaluation.getB_2_score()+"");

		switch (evaluation.getB_2_level()){
			case 0:
				b_2_level_rg.check(R.id.b_2_level_0_RB);
				break;
			case 1:
				b_2_level_rg.check(R.id.b_2_level_1_RB);
				break;
			case 2:
				b_2_level_rg.check(R.id.b_2_level_2_RB);
				break;
			case 3:
				b_2_level_rg.check(R.id.b_2_level_3_RB);
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
			CustomToast.show(this.getActivity(),"B2数据异常！",CustomToast.LENGTH_SHORT);
			return;
		}
		switch (b_2_1_rg.getCheckedRadioButtonId()) {
			case R.id.b_2_1_0_RB:
				evaluation.setB_2_1(0);
				break;
			case R.id.b_2_1_1_RB:
				evaluation.setB_2_1(1);
				break;
			case R.id.b_2_1_2_RB:
				evaluation.setB_2_1(2);
				break;
		}
		switch (b_2_2_rg.getCheckedRadioButtonId()) {
			case R.id.b_2_2_0_RB:
				evaluation.setB_2_2(0);
				break;
			case R.id.b_2_2_1_RB:
				evaluation.setB_2_2(1);
				break;
			case R.id.b_2_2_2_RB:
				evaluation.setB_2_2(2);
				break;
		}
		switch (b_2_3_rg.getCheckedRadioButtonId()) {
			case R.id.b_2_3_0_RB:
				evaluation.setB_2_3(0);
				break;
			case R.id.b_2_3_1_RB:
				evaluation.setB_2_3(1);
				break;
			case R.id.b_2_3_2_RB:
				evaluation.setB_2_3(2);
				break;
		}

		int score = -1;
		if(evaluation.getB_2_1() > -1
				&& evaluation.getB_2_2() > -1
				&& evaluation.getB_2_3() > -1
				){
			score = evaluation.getB_2_1()
					+ evaluation.getB_2_2()
					+ evaluation.getB_2_3();
		}
		if(score > -1){
			b_2_score_tv.setText(score+"");
		}
		evaluation.setB_2_score(score);

		if( score == 0 ){
			evaluation.setB_2_level(0);
			b_2_level_rg.check(R.id.b_2_level_0_RB);
		}else if( score == 1 ){
			evaluation.setB_2_level(1);
			b_2_level_rg.check(R.id.b_2_level_1_RB);
		}else if( score >= 2 && score <= 3 ){
			evaluation.setB_2_level(2);
			b_2_level_rg.check(R.id.b_2_level_2_RB);
		}else if( score >= 4 && score <= 6 ){
			evaluation.setB_2_level(3);
			b_2_level_rg.check(R.id.b_2_level_3_RB);
		}

		DataBaseHelper.getInstance(this.getActivity(),Evaluation.class).update(evaluation);
		//CustomToast.show(this.getActivity(), "B2保存成功", Toast.LENGTH_SHORT);
	}

	@Override
	public void refreshData(String baseId) {

	}

	public void onEventMainThread(Event.MsgEvent msgEvent) {
		if (msgEvent.type == EVENT_GETDATA
				&& msgEvent.fromClass == B2.class) {

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