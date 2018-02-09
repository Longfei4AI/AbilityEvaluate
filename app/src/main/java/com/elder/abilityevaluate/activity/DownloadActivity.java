package com.elder.abilityevaluate.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;

public class DownloadActivity extends BasicActiviy {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showInitedLoading = false;
		setContentView(R.layout.login_activity);
	}
	
	@Override
	public void init() {
	}

	@Override
	public void initFinished() {
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			GoActivityWithFinishing(MainMenuActivity.class, null);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
