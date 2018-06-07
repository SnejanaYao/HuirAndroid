package com.huir.android.tool;

import android.content.Context;


/**
 * 
 * 语音聊天工具类
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


}
