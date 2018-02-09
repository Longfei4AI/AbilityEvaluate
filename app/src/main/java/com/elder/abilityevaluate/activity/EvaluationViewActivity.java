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
import com.elder.abilityevaluate.fragment.EvaluatePaper;
import com.elder.abilityevaluate.view.CustomViewPaper;
import com.elder.abilityevaluate.viewpagerindicator.TabPageIndicator;

import java.util.List;

public class EvaluationViewActivity extends BasicActiviy{
	private TabPageIndicator mIndicator;
	private CustomViewPaper mViewPager;
	private FragmentPagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluate_activity);
	}

	@Override
	public void init() {
		mViewPager = (CustomViewPaper) findViewById(R.id.id_pager);
		mAdapter = new TabAdapter(getSupportFragmentManager());
		mIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
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
				fragment = new B1();
				break;
			case 1:
				fragment = new B1();
				break;
			case 2:
				fragment = new B1();
				break;
			case 3:
				fragment = new B1();
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
		GoActivityWithFinishing(MainListActivity.class, null);
	}

	@Override
	protected void onDestroy() {
		mViewPager.removeAllViews();
		super.onDestroy();
	}
}
