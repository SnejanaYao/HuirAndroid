package com.huir.android.activity;


import com.huir.test.R;
import com.huir.android.tool.Tool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private EditText name ;
	private EditText pwd ;
	private TextView check_userName ;
	private TextView check_userPwd ;
	private String userName ;
	private String userPwd ;
	private boolean checkName;
	private boolean checkPwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		name = (EditText)findViewById(R.id.edit_login_name);
		name.addTextChangedListener(new TextChangedListenerName());
		pwd = (EditText)findViewById(R.id.edit_login_pwd);
		pwd.addTextChangedListener(new TextChangedListenerPwd());
		TextView reg = (TextView)findViewById(R.id.text_reg);
		reg.setOnClickListener(new onClick());
		Button login = (Button)findViewById(R.id.btn_user_login);
		login.setOnClickListener(new onClick());

	}
	
	/**
	 * 验证用户名是否合法
	 * @author huir316
	 *
	 */
	private class TextChangedListenerName implements TextWatcher  {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			 name = (EditText) findViewById(R.id.edit_login_name);
			 check_userName = (TextView) findViewById(R.id.text_check_userName);
			 userName = name.getText().toString();
			if (Tool.checkUserName(userName)) {
				checkName=true;
				check_userName.setTextColor(Color.GREEN);
				check_userName.setText("用户名输入正确...");
			}else {
				checkName = false;
				check_userName.setTextColor(Color.RED);
				check_userName.setText("16个字符，支持英文、数字、减号或下划线");
			}
		}
		@Override
		public void afterTextChanged(Editable s) {
		}
	}
	
	/**
	 * 验证密码是否合法
	 * @author huir316
	 *
	 */
	private class TextChangedListenerPwd implements TextWatcher  {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			pwd = (EditText) findViewById(R.id.edit_login_pwd);
			check_userPwd = (TextView) findViewById(R.id.text_check_userPwd);
			userPwd = pwd.getText().toString();
			if (Tool.checkUserPwd(userPwd)) {
				checkPwd=true;
				check_userPwd.setTextColor(Color.GREEN);
				check_userPwd.setText("密码输入正确...");
			}else {
				checkPwd = false;
				check_userPwd.setTextColor(Color.RED);
				check_userPwd.setText("请输入6-20 位，字母、数字、字符");
			}
		}
		@Override
		public void afterTextChanged(Editable s) {
		}
		
	}

	/**
	 * 点击事件的监听
	 * 
	 * @author huir316
	 *
	 */
	private class onClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.text_reg:
				new Handler(new Handler.Callback() {
					// 处理接收到的消息的方法
					@Override
					public boolean handleMessage(Message arg0) {
						// 实现页面跳转
						startActivity(new Intent(getApplicationContext(), RegActivity.class));
						return false;
					}
				}).sendEmptyMessageDelayed(0, 0); // 表示延时三秒进行任务的执行
				break;
			case R.id.btn_user_login:
				 name = (EditText) findViewById(R.id.edit_login_name);
				 pwd = (EditText) findViewById(R.id.edit_login_pwd);
				 userName = name.getText().toString();
				 userPwd = pwd.getText().toString();
				 if(checkName && checkPwd) {
					if ("admin01".equals(userName) && "123456".equals(userPwd)) {
							Tool.showDialog(MainActivity.this);
							new Handler(new Handler.Callback() {
								// 处理接收到的消息的方法
								@Override
								public boolean handleMessage(Message arg0) {
									Tool.showSuccessDialog(MainActivity.this);
									return false;
								}
							}).sendEmptyMessageDelayed(0, 3000); // 表示延时三秒进行任务的执行
						} else {
							Tool.showFaildDialog(MainActivity.this);
						}
				 }		
				break;
			}
		}
	}
}
