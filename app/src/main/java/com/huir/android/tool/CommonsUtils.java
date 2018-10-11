package com.huir.android.tool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.huir.android.chat.ChatActivity;
import com.huir.android.chat.record.MediaManager;

import java.util.List;


/**
 * 
 * 关于手机配置等问题的工具类
 * 
 */
public class CommonsUtils {
    //根据时间长短计算语音条宽度:220dp
    public synchronized static int getVoiceLineWight(Context context, int seconds) {
        //1-2s是最短的。2-10s每秒增加一个单位。10-60s每10s增加一个单位。
        if (seconds <= 2) {
            return dip2px(context, 60);
        } else if (seconds <= 10) {
            //60~140
            return dip2px(context, 60 + 8 * seconds);
        } else {
            //140~240
            return dip2px(context, 140 + 10 * (seconds / 10));

        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取系统版本号
     * @return   安卓系统大版本号
     */
    public static Integer getMobileSystemVersion() {
        String release = Build.VERSION.RELEASE;
        return  Integer.parseInt(release.substring(0, 1));
    };


    public static boolean isForeground(Activity activity){
       return isForeground(activity,activity.getClass().getName());
    }

    /**
     * 判断某个Acitivy是否处在栈顶
     * @param context
     * @param className
     * @return
     */
    public static boolean isForeground(Context context,String className){
        if(context == null  || TextUtils.isEmpty(className)){
            return false;
        }
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(1);   //获取当前正在运行的Task
        if(list != null && list.size() >0){
            ComponentName topActivity = list.get(0).topActivity;   //取得处在栈顶的Activity
            if(topActivity.getClassName().equals(className)){
                return true;
            }
        }
        return false;
    }

    /**
     * 关闭音源播放并重置动画
     */
   /* public static void stopMediaPlayer(){
        MediaManager mediaManager = ChatViewAdapter.mediaManager;
        AnimationDrawable animation = ChatViewAdapter.getAnimation();
        if(mediaManager !=null  &&  animation!=null){
            if(mediaManager.isplaying()){
                mediaManager.stop();
                animation.stop();
                animation.selectDrawable(0); //重置动画到未播放状态
            }
        }else   return; //当按返回键时关闭正在播放的声音
    }*/

    public static void stopMediaPlayer(){
        MediaManager mediaManager = ChatActivity.mediaManager;
        AnimationDrawable animation = ChatActivity.getAnimation();
        if(mediaManager !=null  &&  animation!=null){
            if(mediaManager.isplaying()){
                mediaManager.stop();
                animation.stop();
                animation.selectDrawable(0); //重置动画到未播放状态
            }
        }else   return; //当按返回键时关闭正在播放的声音
    }

    /**
     * 返回home桌面
     * @return
     */
    public static Intent returnHomeActivity(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return  intent;
    }


    /**@param bMute 值为true时为关闭背景音乐。*/
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static boolean muteAudioFocus(Context context, boolean bMute) {
        if (context == null) {
            Log.d("ANDROID_LAB", "context is null.");
            return false;
        }
        boolean bool = false;
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (bMute) {
            int result = am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        } else {
            int result = am.abandonAudioFocus(null);
            bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        }
        Log.d("ANDROID_LAB", "pauseMusic bMute=" + bMute + " result=" + bool);
        return bool;
    }



}
