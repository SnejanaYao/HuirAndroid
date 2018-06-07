package com.huir.android.record;

import java.io.File;
import java.util.UUID;

import android.media.MediaRecorder;
import android.util.Log;

/**
 * 录音
 * @author huir316
 *
 */
public class AudioRecoderUtils {
	private static final String TAG = "AudioRecoderUtils";
    private String mCurrentFilePath;//文件路径
    private String mDir;//文件夹路径
    private boolean isPrepared;
	private MediaRecorder mediaRecorder;
	public MediaRecorder getMediaRecorder() {
		return mediaRecorder;
	}

	public void setMediaRecorder(MediaRecorder mediaRecorder) {
		this.mediaRecorder = mediaRecorder;
	}
	
	private static AudioRecoderUtils mInstance;
	
	/**
	 * 单例
	 * @param dir 
	 * @return AudioRecoderUtils
	 */
	public static AudioRecoderUtils getInstance(String dir) {
		if(mInstance ==null) {
			synchronized (AudioRecoderUtils.class) {
				if(mInstance ==null) {
					mInstance = new AudioRecoderUtils(dir);
				}
			}
		}
		return mInstance;
	}
	
	private AudioRecoderUtils(String dir) {
		this.mDir = dir;
	}; 
	
	/**
	 * 回调准备完毕
	 * @author huir316
	 *
	 */
	public interface AudioStateListener{
		void wellPrepared();
	}
	
	public AudioStateListener mListener;
	
	public void setOnAudioStateListener(AudioStateListener audioStateListener) {
		mListener = audioStateListener;
	}
	
	
	
	public void prepareAudio() {
		try {
			isPrepared = false;
			File dir = new File(mDir);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			String fileName = generateFile();
			File file = new File(mDir,fileName);
			mCurrentFilePath = file.getAbsolutePath(); //获取绝对路径
			mediaRecorder = new MediaRecorder();
			mediaRecorder.setOutputFile(file.getAbsolutePath()); //输出文件
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //音频源麦克风 
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR); //音频文件格式
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mediaRecorder.prepare();
			mediaRecorder.start();
			isPrepared = true;
			if(mListener != null) {
				mListener.wellPrepared();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	/**
	 * 随即生成文件名称
	 * @return
	 */
	private String generateFile() {
		return UUID.randomUUID().toString()+".amr";
	}

	/**
	 * 获取音量等级
	 * @param maxLevel
	 * @return 音量等级
	 */
	public int getLevel(int maxLevel) {
		if(isPrepared) {
			try {
				//mediaRecorder.getMaxAmplitude()/32768   1-32767
				int sound = maxLevel * mediaRecorder.getMaxAmplitude()/2000+1;
				if(sound > 0) {
					return sound;
				}
			}catch (Exception e) {
			}
		}
		return 1;
	}
	
	public void release() {
		/**
		 * 处理错误：RuntimeException:stop failed
		 */
		try {
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
		} catch (Exception e) {
		}
	}
	
	public void cancel() {
		release();
		if(mCurrentFilePath !=null) {
			File file = new File(mCurrentFilePath);
			file.delete();
			mCurrentFilePath = null;
		}
	}

	public String getCurrentFilePath() {
		return mCurrentFilePath;
	}
}