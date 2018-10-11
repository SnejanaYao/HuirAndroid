package com.huir.android.activity;

import com.huir.test.R;
import com.huir.android.tab.TabActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.support.v7.app.AppCompatActivity;


public class WelcomeActivity extends AppCompatActivity {
    private static final  String TAG = "WelcomeActity";
	private boolean isLogin = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		config();
		setContentView(R.layout.activity_welcome);
		if(!isLogin) {
		    new Handler(new Handler.Callback() {
		          //处理接收到的消息的方法
		         @Override
		         public boolean handleMessage(Message arg0) {
		           //实现页面跳转
		            startActivity(new Intent(getApplicationContext(),MainActivity.class));
		            finish(); //销毁当前页
		            return false;
		         }
		     }).sendEmptyMessageDelayed(0, 3000); //表示延时三秒进行任务的执行
		}else{
			new Handler(new Handler.Callback() {
		          //处理接收到的消息的方法
		         @Override
		         public boolean handleMessage(Message arg0) {
		              //实现页面跳转
		        	 startActivity(new Intent(getApplicationContext(),TabActivity.class));
		        	 finish(); //销毁当前页
		            return false;
		         }
		     }).sendEmptyMessageDelayed(0, 3000); //表示延时三秒进行任务的执行
		}
	}

	private void  config(){
	    requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏
        if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
        }
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);//隐藏状态栏
        getSupportActionBar().hide();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(TAG, "销毁");
	}
	  
}


