package com.huir.android.activity;

import com.huir.test.R;
import com.huir.android.tab.TabActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeActity extends Activity {
	private boolean isLogin = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏
        if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);   //透明导航栏
        }
		setContentView(R.layout.activity_welcome);
		if(!isLogin) {
		    new Handler(new Handler.Callback() {
		          //处理接收到的消息的方法
		         @Override
		         public boolean handleMessage(Message arg0) {
		           //实现页面跳转
		            startActivity(new Intent(getApplicationContext(),MainActivity.class));
		            return false;
		         }
		     }).sendEmptyMessageDelayed(0, 3000); //表示延时三秒进行任务的执行
		}else {
			new Handler(new Handler.Callback() {
		          //处理接收到的消息的方法
		         @Override
		         public boolean handleMessage(Message arg0) {
		           //实现页面跳转
		        	 startActivity(new Intent(getApplicationContext(),TabActivity.class));
		            return false;
		         }
		     }).sendEmptyMessageDelayed(0, 3000); //表示延时三秒进行任务的执行
		}
	
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("WelcomeActivity", "销毁");
	}
	  
}


