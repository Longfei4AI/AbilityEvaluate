package com.elder.abilityevaluate.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.entity.BaseInformation;
import com.elder.abilityevaluate.entity.Evaluation;
import com.elder.abilityevaluate.entity.EvaluationReport;
import com.elder.abilityevaluate.fragment.B1;
import com.elder.abilityevaluate.fragment.B2;
import com.elder.abilityevaluate.fragment.B3;
import com.elder.abilityevaluate.fragment.B4;
import com.elder.abilityevaluate.fragment.BaseFragment;
import com.elder.abilityevaluate.fragment.EvaluatePaper;
import com.elder.abilityevaluate.utils.DataBaseHelper;
import com.elder.abilityevaluate.utils.GeneratorReportDocument;
import com.elder.abilityevaluate.view.CustomViewPaper;
import com.elder.abilityevaluate.viewpagerindicator.TabPageIndicator;
import com.elder.abilityevaluate.widget.CustomToast;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

public class EvaluationEditActivity extends BasicActiviy{
	private TabPageIndicator mIndicator;
	private CustomViewPaper mViewPager;
	private FragmentPagerAdapter mAdapter;
	private String activity = null;
	private String baseId = null;
	private Evaluation evaluation = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation_edit_activity);
		activity = getIntent().getExtras().getString("activity");
		baseId = getIntent().getExtras().getString("id");
	}

	@Override
	public void init() {
		evaluation = DataBaseHelper.getInstance(this,Evaluation.class).
				findFirst(Selector.from(Evaluation.class).where("baseInfoId","=",baseId));
		if(null == evaluation){
			evaluation = new Evaluation();
			evaluation.setBaseInfoId(baseId);
			DataBaseHelper.getInstance(this,Evaluation.class).save(evaluation);
		}
		mViewPager = findViewById(R.id.id_pager);
		mAdapter = new TabAdapter(getSupportFragmentManager());
		mIndicator = findViewById(R.id.id_indicator);
	}

	@Override
	public void initFinished() {
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOffscreenPageLimit(4);
		mIndicator.setViewPager(mViewPager, 0);
		// 如果我们要对ViewPager设置监听，用indicator设置就行了
		mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				BaseFragment baseFragment =(BaseFragment) mAdapter.getItem(arg0);
				baseFragment.refreshData(baseId);
				//CustomToast.show(EvaluationEditActivity.this,arg0+"",1);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back(null);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class TabAdapter extends FragmentPagerAdapter {
		public TabAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = null;
			switch (arg0) {
			case 0:
				fragment = new B1();
				break;
			case 1:
				fragment = new B2();
				break;
			case 2:
				fragment = new B3();
				break;
			case 3:
				fragment = new B4();
				break;
			default:
				fragment = new EvaluatePaper();
				break;
			}

			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return EvaluatePaper.TITLE[position % EvaluatePaper.TITLE.length];
		}

		@Override
		public int getCount() {
			return EvaluatePaper.TITLE.length;
		}

	}

	public void back(View v){
		if(null != activity && "baseInfo".equals(activity)){
			Bundle bundle = new Bundle();
			bundle.putString("activity", "baseInfo");
			bundle.putString("id",baseId);
			GoActivityWithFinishing(BaseInformationViewActivity.class, bundle);
		}else{
			GoActivityWithFinishing(EvaluationListActivity.class, null);
		}

	}
	public void save(View v){
		evaluation = DataBaseHelper.getInstance(this,Evaluation.class).
				findFirst(Selector.from(Evaluation.class).where("baseInfoId","=",baseId));
		if(null == evaluation){
			CustomToast.show(this,"数据异常！",CustomToast.LENGTH_SHORT);
			return;
		}else{
			if(evaluation.getB_1_score() > -1
					&& evaluation.getB_2_score() > -1
					&& evaluation.getB_3_score() > -1
					&& evaluation.getB_4_score() > -1){
				generateReport();
			}else{
				CustomToast.show(this,"有未完成的评估内容，请核实！",CustomToast.LENGTH_SHORT);
				return;
			}
		}

	}
	/**
	* @Author: wlf
	* @Time: 2018/2/13 0:26
	* @Desc: 生成评估报告
	* @Params:
	* @Return:
	*/
	private void generateReport(){
		EvaluationReport evaluationReport = DataBaseHelper.getInstance(this,EvaluationReport.class).
				findFirst(Selector.from(EvaluationReport.class).where("baseInfoId","=",baseId));

		if(null == evaluationReport){
			evaluationReport = new EvaluationReport();
			evaluationReport.setBaseInfoId(baseId);
			evaluationReport.setState(EvaluationReport.NOT_FINISHED);
			evaluationReport = this.updateReport(evaluationReport);
			DataBaseHelper.getInstance(this,EvaluationReport.class).save(evaluationReport);
			evaluation.setState(Evaluation.FINISHED);
			DataBaseHelper.getInstance(this,Evaluation.class).update(evaluation);
			BaseInformation baseInformation = evaluation.getBaseInformation(this);
			baseInformation.setState(BaseInformation.EVALUATED);
			DataBaseHelper.getInstance(this,BaseInformation.class).update(baseInformation);
		}else {
			//更新报告
			evaluationReport = this.updateReport(evaluationReport);
			DataBaseHelper.getInstance(this,EvaluationReport.class).update(evaluationReport);
		}
		Bundle bundle = new Bundle();
		bundle.putString("id",baseId);
		GoActivityWithFinishing(EvaluationViewActivity.class, bundle);
	}
	/**
	* @Author: wlf
	* @Time: 2018/2/22 18:49
	* @Desc: 更新评估报告内容
	* @Params:
	* @Return:
	*/
	private EvaluationReport updateReport(EvaluationReport evaluationReport){
		BaseInformation baseInfor = DataBaseHelper.getInstance(this,BaseInformation.class).
				findFirst(Selector.from(BaseInformation.class).where("baseInfoId","=",baseId));
		int b_1_level = evaluation.getB_1_level();
		int b_2_level = evaluation.getB_2_level();
		int b_3_level = evaluation.getB_3_level();
		int b_4_level = evaluation.getB_4_level();
		evaluationReport.setB_1_grade(b_1_level);
		evaluationReport.setB_2_grade(b_2_level);
		evaluationReport.setB_3_grade(b_3_level);
		evaluationReport.setB_4_grade(b_4_level);
		int grade_pre = -1;
		int grade_final = -1;
		String change_item = "";
		grade_pre = grade_pre < b_1_level ? b_1_level : grade_pre;
		grade_pre = grade_pre < b_2_level ? b_2_level : grade_pre;
		grade_pre = grade_pre < b_3_level ? b_3_level : grade_pre;
		grade_pre = grade_pre < b_4_level ? b_4_level : grade_pre;
		grade_final = grade_pre;
		if(evaluation.getB_3_1() == 3){
			grade_pre = 3;
			grade_final = 3;
			change_item = "3";
		}else{
			String a_2_13_1 = baseInfor.getA_2_13_1();
			String a_2_13_2 = baseInfor.getA_2_13_2();
			if( (a_2_13_1 != null && !"".equals(a_2_13_1) && !"1".equals(a_2_13_1))
					|| (a_2_13_2 != null && !"".equals(a_2_13_2) && !"1".equals(a_2_13_2))){
				change_item += "1;";
				grade_final += 1;
			}
			if(baseInfor.getA_2_14_1() >= 2
					|| baseInfor.getA_2_14_2() >= 2
					|| baseInfor.getA_2_14_3() >= 2
					|| baseInfor.getA_2_14_4() >=2){
				change_item += "2;";
				grade_final += 1;
			}
		}
		evaluationReport.setGrade_change_item(change_item);
		evaluationReport.setE_grade_pre(grade_pre);//初步等级
		evaluationReport.setE_grade_final(grade_final > 3 ? 3 : grade_final);//最终等级
		evaluationReport.setE_name(evaluation.getBaseInformation(this).getA_2_1());
		// 生成评估报告文档
		GeneratorReportDocument.getInstance(this).Save(evaluationReport);
		return  evaluationReport;
	}
	@Override
	protected void onDestroy() {
		mViewPager.removeAllViews();
		super.onDestroy();
	}
}
