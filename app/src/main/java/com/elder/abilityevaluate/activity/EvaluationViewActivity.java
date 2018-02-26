package com.elder.abilityevaluate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.fragment.B1;
import com.elder.abilityevaluate.fragment.B1_View;
import com.elder.abilityevaluate.fragment.B2_View;
import com.elder.abilityevaluate.fragment.B3_View;
import com.elder.abilityevaluate.fragment.B4_View;
import com.elder.abilityevaluate.fragment.EvaluatePaper;
import com.elder.abilityevaluate.view.CustomViewPaper;
import com.elder.abilityevaluate.viewpagerindicator.TabPageIndicator;

import java.util.List;

public class EvaluationViewActivity extends BasicActiviy{
	private TabPageIndicator mIndicator;
	private CustomViewPaper mViewPager;
	private FragmentPagerAdapter mAdapter;
	public String baseId = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation_view_activity);
		baseId = getIntent().getExtras().getString("id");
	}

	@Override
	public void init() {
		mViewPager = findViewById(R.id.id_pager);
		mAdapter = new TabAdapter(getSupportFragmentManager());
		mIndicator = findViewById(R.id.id_indicator);
	}

	@Override
	public void initFinished() {
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOffscreenPageLimit(2);
		mIndicator.setViewPager(mViewPager, 0);
		// 如果我们要对ViewPager设置监听，用indicator设置就行了
		mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
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
			GoActivityWithFinishing(MainListActivity.class, null);
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
				fragment = new B1_View();
				break;
			case 1:
				fragment = new B2_View();
				break;
			case 2:
				fragment = new B3_View();
				break;
			case 3:
				fragment = new B4_View();
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
		GoActivityWithFinishing(EvaluationListActivity.class, null);
	}
	public void edit(View v){
		Bundle bundle = new Bundle();
		bundle.putString("id",baseId);
		GoActivityWithFinishing(EvaluationEditActivity.class, bundle);
	}
	@Override
	protected void onDestroy() {
		mViewPager.removeAllViews();
		super.onDestroy();
	}
}
