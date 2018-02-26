package com.elder.abilityevaluate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.entity.BaseInformation;
import com.elder.abilityevaluate.entity.EvaluationReport;
import com.elder.abilityevaluate.fragment.B1;
import com.elder.abilityevaluate.utils.DataBaseHelper;
import com.elder.abilityevaluate.widget.CustomToast;
import com.lidroid.xutils.db.sqlite.Selector;

import java.util.List;

public class EvaluateReportEditActivity extends BasicActiviy{
	private TextView report_1_1_tv;
	private TextView report_1_2_tv;
	private TextView report_1_3_tv;
	private TextView report_1_4_tv;
	private TextView report_2_1_tv;
	private TextView report_3_1_tv;
	private TextView report_3_2_tv;
	private TextView report_3_3_tv;
	private TableRow report_3_1_tr;
	private TableRow report_3_2_tr;
	private LinearLayout report_3_2_ll;
	private TableRow report_3_3_tr;
	private LinearLayout report_3_3_ll;
	private TextView report_4_1_tv;
	private String baseId = null;
	private EvaluationReport report = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		baseId = intent.getExtras().getString("id");
		setContentView(R.layout.report_evaluation_edit_activity);
	}

	@Override
	public void init() {
		report_1_1_tv = findViewById(R.id.report_1_1_tv);
		report_1_2_tv = findViewById(R.id.report_1_2_tv);
		report_1_3_tv = findViewById(R.id.report_1_3_tv);
		report_1_4_tv = findViewById(R.id.report_1_4_tv);
		report_2_1_tv = findViewById(R.id.report_2_1_tv);
		report_3_1_tv = findViewById(R.id.report_3_1_tv);
		report_3_2_tv = findViewById(R.id.report_3_2_tv);
		report_3_3_tv = findViewById(R.id.report_3_3_tv);
		report_3_1_tr = findViewById(R.id.report_3_1_tr);
		report_3_2_tr = findViewById(R.id.report_3_2_tr);
		report_3_2_ll = findViewById(R.id.report_3_2_ll);
		report_3_3_tr = findViewById(R.id.report_3_3_tr);
		report_3_3_ll = findViewById(R.id.report_3_3_ll);
		report_4_1_tv = findViewById(R.id.report_4_1_tv);
		this.initData();
	}
	/**
	 * @Author: wlf
	 * @Time: 2018/2/10 10:13
	 * @Desc: 为TextView填充数据
	 * @Params:
	 * @Return: void
	 */
	private void initData() {
		report = DataBaseHelper.getInstance(this, EvaluationReport.class).
				findFirst(Selector.from(EvaluationReport.class).where("baseInfoId", "=", baseId));
		if (null == report) {
			CustomToast.show(this, "获取数据失败！", CustomToast.LENGTH_SHORT);
			return;
		}
		switch (report.getB_1_grade()){
			case 0:
				report_1_1_tv.setText(getString(R.string.level_0));
				break;
			case 1:
				report_1_1_tv.setText(getString(R.string.level_1));
				break;
			case 2:
				report_1_1_tv.setText(getString(R.string.level_2));
				break;
			case 3:
				report_1_1_tv.setText(getString(R.string.level_3));
				break;
		}
		switch (report.getB_2_grade()){
			case 0:
				report_1_2_tv.setText(getString(R.string.level_0));
				break;
			case 1:
				report_1_2_tv.setText(getString(R.string.level_1));
				break;
			case 2:
				report_1_2_tv.setText(getString(R.string.level_2));
				break;
			case 3:
				report_1_2_tv.setText(getString(R.string.level_3));
				break;
		}
		switch (report.getB_3_grade()){
			case 0:
				report_1_3_tv.setText(getString(R.string.level_0));
				break;
			case 1:
				report_1_3_tv.setText(getString(R.string.level_1));
				break;
			case 2:
				report_1_3_tv.setText(getString(R.string.level_2));
				break;
			case 3:
				report_1_3_tv.setText(getString(R.string.level_3));
				break;
		}
		switch (report.getB_4_grade()){
			case 0:
				report_1_4_tv.setText(getString(R.string.level_0));
				break;
			case 1:
				report_1_4_tv.setText(getString(R.string.level_1));
				break;
			case 2:
				report_1_4_tv.setText(getString(R.string.level_2));
				break;
			case 3:
				report_1_4_tv.setText(getString(R.string.level_3));
				break;
		}
		switch (report.getE_grade_pre()){
			case 0:
				report_2_1_tv.setText(getString(R.string.grade_0));
				break;
			case 1:
				report_2_1_tv.setText(getString(R.string.grade_1));
				break;
			case 2:
				report_2_1_tv.setText(getString(R.string.grade_2));
				break;
			case 3:
				report_2_1_tv.setText(getString(R.string.grade_3));
				break;
		}

		String changeItem = report.getGrade_change_item() == null ? "" : report.getGrade_change_item();
		if(changeItem.contains("1")){
			report_3_1_tr.setVisibility(View.VISIBLE);
			report_3_1_tv.setText(getString(R.string.item_change_1));
		}
		if(changeItem.contains("2")){
			report_3_2_tr.setVisibility(View.VISIBLE);
			report_3_2_ll.setVisibility(View.VISIBLE);
			report_3_2_tv.setText(getString(R.string.item_change_2));
		}
		if(changeItem.contains("3")){
			report_3_3_tr.setVisibility(View.VISIBLE);
			report_3_3_ll.setVisibility(View.VISIBLE);
			report_3_3_tv.setText(getString(R.string.item_change_3));
		}

		switch (report.getE_grade_final()){
			case 0:
				report_4_1_tv.setText(getString(R.string.grade_0));
				break;
			case 1:
				report_4_1_tv.setText(getString(R.string.grade_1));
				break;
			case 2:
				report_4_1_tv.setText(getString(R.string.grade_2));
				break;
			case 3:
				report_4_1_tv.setText(getString(R.string.grade_3));
				break;
		}
	}

	@Override
	public void initFinished() {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			GoActivityWithFinishing(MainListActivity.class, null);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			List<Fragment> list = getSupportFragmentManager().getFragments();
			B1 fragment = null;
			for(Fragment fment : list){
				if(fment instanceof B1){
					fragment = (B1) fment;
				}
			}
			//if(null!=fragment)fragment.codeACTV.setText(data.getExtras().getString("result"));
		}
        switch (requestCode) {
		case B1.SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				System.out.println(data.getExtras().getString("result"));
			}
			break;
	    case B1.SCANNIN_FARMER_CODE:
			if(resultCode == RESULT_OK){
				
			}
			break;
		}
        super.onActivityResult(requestCode, resultCode, data);
    }	
	public void back(View v){
		GoActivityWithFinishing(EvaluateReportListActivity.class, null);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
