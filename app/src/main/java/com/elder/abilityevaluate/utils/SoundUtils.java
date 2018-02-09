/**   
 * @Title: SoundUtils.java 
 * @Package com.custom.tpc.utils
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 山东山大新元易通信息科技有限公司 www.sdcustom.com
 * @date 2015-2-10 下午4:26:15 
 * @version V1.0   
 */
package com.elder.abilityevaluate.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.elder.abilityevaluate.R;

import java.util.ArrayList;
import java.util.List;

public class SoundUtils {
	public static final int SOUND_ERROR = R.raw.error1;
	public static final int SOUND_INFO = R.raw.info;
	public static final int SOUND_WARNNING = R.raw.warn_1;
	public static final int SOUND_SUCCESS = R.raw.success_1;
	public static final int SOUND_FAILED = R.raw.failed_1;
	public static final int SOUND_DIALOG_C = R.raw.dialog_c3;
	public static final int SOUND_BUTTON = R.raw.bclick_1;
	public static final int SOUND_MENU = R.raw.menu_click_1;
	public static final int SOUND_BOOK = R.raw.book_o1;
	public static final int SOUND_CONFIRM = R.raw.confirm_1;
	public static final int SOUND_TOGGLE_OFF = R.raw.toggleoff;
	public static final int SOUND_TOGGLE_ON = R.raw.toggleon;
	public static final List<MediaPlayer> medialist = new ArrayList<MediaPlayer>();

	private Context context;

	public SoundUtils(Context c) {
		super();
		context = c;
	}

	public void toggleOff() {
		playSound(SOUND_TOGGLE_OFF);
	}

	public void toggleOn() {
		playSound(SOUND_TOGGLE_ON);
	}

	public void confirm() {
		playSound(SOUND_CONFIRM);
	}

	public void openBook() {
		playSound(SOUND_BOOK);
	}

	public void dialogClose() {
		playSound(SOUND_DIALOG_C);
	}

	public void failed() {
		playSound(SOUND_FAILED);
	}

	public void warnning() {
		playSound(SOUND_WARNNING);
	}

	public void info() {
		playSound(SOUND_INFO);
	}

	public void success() {
		playSound(SOUND_SUCCESS);
	}

	public void error() {
		playSound(SOUND_ERROR);
	}

	public void buttonClick() {
		playSound(SOUND_BUTTON);
	}

	public void menuClick() {
		playSound(SOUND_MENU);
	}

	class PlaySoundThread extends Thread {
		private int res;

		public PlaySoundThread(int res) {
			super();
			this.res = res;
		}

		public void run() {
			SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
			final int sourceid = soundPool.load(context, res,0);
			soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
				@Override
				public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
					soundPool.play(sampleId, 2, 2, 0, 0, 1);
				}
			});
			try {
				Thread.sleep(1000);
				soundPool.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 播放声音R.raw.tip
	public void playSound(int resId) {
		new PlaySoundThread(resId).start();
	}

	/**
	 * 创建本地MP3
	 * 
	 * @return
	 */
	private MediaPlayer createLocalMp3(int resId) {
		MediaPlayer mp = MediaPlayer.create(context, resId);
		if (mp != null) {
			mp.stop();
		}
		for (int i = 0; i < medialist.size(); i++) {
			MediaPlayer m = medialist.get(i);
			try {
				if (m != null && !m.isPlaying()) {
					m.release();
				}
			} catch (IllegalStateException e) {
			} finally {
				medialist.remove(m);
				m = null;
			}
		}
		medialist.add(mp);
		return mp;
	}
}
