package com.elder.abilityevaluate.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;

import java.util.List;

public class WelcomeActivity extends BasicActiviy {
	public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
	TextView adTV1;
	TextView adTV2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		showInitedLoading = false;
		checkRunning();
	}
	
	@Override
	public void init() {
		if (hasShortCut()) {
		} else {
			addShortcut(getResources().getString(R.string.app_name));
		}
		adTV1 = findViewById(R.id.tv_ad1);
		adTV2 = findViewById(R.id.tv_ad2);
	}
	
	/**
	 * 增加快捷方式
	 * @param name
	 */
	private void addShortcut(String name) {
		Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
		addShortcutIntent.putExtra("duplicate", false);//
		addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
		addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(WelcomeActivity.this,
						R.drawable.ic_launcher));
		Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
		launcherIntent.setClass(WelcomeActivity.this, WelcomeActivity.class);
		launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		addShortcutIntent
				.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
		sendBroadcast(addShortcutIntent);
	}
	
	/**
	 * 判断是否有快捷方式
	 * @return
	 */
	private boolean hasShortCut() {
		String url = "content://"
				+ getAuthorityFromPermission(this,
						"com.android.launcher.permission.READ_SETTINGS")
				+ "/favorites?notify=true";
		Uri uri = Uri.parse(url);
		ContentResolver resolver = getContentResolver();
		Cursor cursor = resolver.query(uri, null, "title=?",
				new String[] { getString(R.string.app_name) }, null);
		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return false;
	}
	
	/**
	 * @param context
	 * @param permission
	 * @return
	 */
	private String getAuthorityFromPermission(Context context, String permission) {
		if (permission == null)
			return null;
		List<PackageInfo> packs = context.getPackageManager()
				.getInstalledPackages(PackageManager.GET_PROVIDERS);
		if (packs != null) {
			for (PackageInfo pack : packs) {
				ProviderInfo[] providers = pack.providers;
				if (providers != null) {
					for (ProviderInfo provider : providers) {
						if (permission.equals(provider.readPermission))
							return provider.authority;
						if (permission.equals(provider.writePermission))
							return provider.authority;
					}
				}
			}
		}
		return null;
	}

	@Override
	public void initFinished() {
		//使用AnimatorSet和ObjectAnimator设置动画
		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator alAnimator1 = ObjectAnimator.ofFloat(adTV1,
				"alpha", 0, 10).setDuration(1500);
		ObjectAnimator alAnimator2 = ObjectAnimator.ofFloat(adTV2,
				"alpha", 0, 10).setDuration(1500);
		animatorSet.playSequentially(alAnimator1,alAnimator2);
		//animatorSet.setTarget(adTV1);
		animatorSet.setStartDelay(500);
		animatorSet.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				GoActivityWithFinishing(LoginActivity.class,null);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		animatorSet.start();
	}

	/**
	 * 判断是否程序已经在运行
	 */
	private void checkRunning() {
		String lastactivity = getRunningActivity();
		if (lastactivity.equals("")
				|| lastactivity.equals(this.getClass().getName())) {
			return;
		}
		// 跳转到正在运行的Activity
		try {
			Intent intent = new Intent();
			intent.setClass(this, Class.forName(lastactivity));
			startActivity(intent);
		} catch (ClassNotFoundException e) {
			System.err.println("Class not find");
			e.printStackTrace();
		}
		this.finish();
	}
	
	/** 
	* @Title: getRunningActivity 
	* @Description: 获得正在后台运行的Activity
	* @return String    返回类型 
	* @throws 
	*/
	public String getRunningActivity(){
		ActivityManager am = (ActivityManager) this
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		String MY_PKG_NAME = this.getPackageName();
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(MY_PKG_NAME)
					&& info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
				if(!info.topActivity.getClassName().equals(this.getClass().getName())){
					return info.topActivity.getClassName();
				}
			}
		}
		return "";
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			// Home键无效
			return true;
		case KeyEvent.KEYCODE_BACK:
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
