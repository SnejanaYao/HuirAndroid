package com.huir.android.chat;

import com.huir.test.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GroupChatActivity extends Activity implements OnClickListener{
	private Button btn_return;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);   //透明导航栏
		//TODO 跳转页面
		setContentView(R.layout.activity_member_list);
		initView();
	}
	/**
	 * 初始化界面控件
	 */
	private void initView() {
		btn_return = (Button) findViewById(R.id.member_chat_return);
		btn_return.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.member_chat_return:
			GroupChatActivity.this.finish();
			break;
		}
	}
}
