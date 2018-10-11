package com.huir.android.application;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.huir.android.chat.ChatActivity;
import com.huir.android.message.MsgDispatcher;
import com.huir.android.net.NetService;
import com.huir.android.tool.CommonsUtils;
import com.huir.test.R;

import java.util.Calendar;

import cn.jpush.android.api.JPushInterface;

public class PushApplication extends Application {
    private static final String TAG = "PushApplication";
    private static final MsgDispatcher msgDispatcher = new MsgDispatcher();
    private  int count;
    private int id;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        boolean booleans = JPushInterface.isPushStopped(this);
        String id = JPushInterface.getRegistrationID(this);
        Log.e(TAG, "设备ID "+id +"isPushStopped " + booleans);
        Intent start = new Intent(PushApplication.this, NetService.class); //开启网络连接服务以及网络状态广播
        startService(start);
        isStoppedOrStarted();
    }

    /**
     * 是否在前台运行，
     */
    private void  isStoppedOrStarted(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStopped(Activity activity) {
                id =  randomID(); //获取随机id
                Log.e(TAG, "onActivityStopped: "+id );
               count--;
                if (count == 0) {
                    Log.e(TAG, ">>>>>>>>>>>>>>>>>>>切到后台  " );
                    CommonsUtils.stopMediaPlayer();
                    //eventPush();
               }
            }

            @Override
            public void onActivityStarted(Activity activity) {
               if (count == 0) {
                    Log.e(TAG, ">>>>>>>>>>>>>>>>>>>切到前台  ");
              }
                count++;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                /*Log.e(TAG, activity + "onActivitySaveInstanceState");*/
            }

            @Override
            public void onActivityResumed(Activity activity) {
                /*Log.e(TAG, activity + "onActivityResumed");*/
            }

            @Override
            public void onActivityPaused(Activity activity) {
               /* Log.e(TAG, activity + "onActivityPaused");*/
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
               /* Log.e(TAG, activity + "onActivityDestroyed");*/
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                /*Log.e(TAG, activity + "onActivityCreated");*/
            }
        });
    }

    /**
     * 获取ID
     * @return int
     */
    public int randomID(){
        int total =0;
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) +"";
        String mon = calendar.get(Calendar.MONTH) +1+"";
        String minute = calendar.get(Calendar.MINUTE) +"";
        String second  = calendar.get(Calendar.SECOND)+"";
        String sum = year+mon+minute+second;
        try{
            total = Integer.parseInt(sum)/10;
        }catch (Exception e){
            Log.e(TAG, "randomID: " +e.getMessage());
        }
        return total;
    }

    /**
     * 触发推送
     */
    public  void eventPush(){
        new Thread(new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    Thread.sleep(5000);
                }catch (Exception e){

                }
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(PushApplication.this)//
                        .setSmallIcon(R.drawable.ic_launcher)//设置显示的小图标
                        .setContentTitle("HuirAndroid")//设置显示的标题
                        .setContentText("message from server push")//设置显示的内容
                        .setWhen(System.currentTimeMillis())//设置到达的时间
                        .setTicker("message from server push")//设置第一次到达显示提示信息
                        .setDefaults(Notification.DEFAULT_SOUND);
                builder.setDefaults(Notification.DEFAULT_ALL);
                PendingIntent pendingIntent = PendingIntent.getActivity(PushApplication.this,0,new Intent(PushApplication.this,ChatActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                manager.notify(id,notification);
            }
        }).start();
    }
}


/**
 *
 * notification.defaults |= Notification.DEFAULT_SOUND;
 notification.flags |= Notification.FLAG_AUTO_CANCEL; //设定通知当用户单击后自动消失
 notification.flags |= Notification.FLAG_SHOW_LIGHTS; //呼吸灯
 notification.ledARGB = 0xff00ff00; //设置呼吸灯颜色
 notification.ledOnMS = 800;  //亮灯时间
 notification.ledOffMS = 600; //暗灯时间*/
