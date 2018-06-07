package com.huir.android.record;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.util.Log;

/**
 * 播放音频
 * @author huir316
 *
 */
public class MediaManager {
	private static final String TAG="MediaManager";
	private static MediaPlayer mediaPlayer;
	private static boolean isPuse;
	public static void playSound(String path,OnCompletionListener onCompletionListener) {
		if(mediaPlayer ==null) {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					mediaPlayer.reset();
					return false;
				}
			});
		}
		try {
			mediaPlayer.reset();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(onCompletionListener);
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	public static void pause() {
		if(mediaPlayer !=null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			isPuse= true;
		}
	}
	
	public static void resume() {
		if(mediaPlayer !=null && isPuse) {
			mediaPlayer.start();
			isPuse= false;
		}
	}
	
	public static void release() {
		if(mediaPlayer !=null ) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
	
}
