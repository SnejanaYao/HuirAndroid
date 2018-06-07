package com.huir.android.activity;

import com.huir.test.R;
import com.huir.android.tool.Tool;

import android.app.Activity;
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

public class RegActivity extends Activity {
	private EditText name;
	private EditText pwd;
	private EditText confirm_pwd;
	private TextView check_userName;
	private TextView check_userPwd;
	private TextView check_confirm_userPwd;
	private String userName;
	private String userPwd;
	private String userConfrimPwd;
	private Boolean checkUser;
	private Boolean checkPwd;
	private Boolean checkCondfirmPwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		name = (EditText)findViewById(R.id.edit_reg_login_name);
		name.addTextChangedListener(new TextChangedListenerName());
		
		pwd = (EditText)findViewById(R.id.edit_reg_login_pwd);
		pwd.addTextChangedListener(new TextChangedListenerPwd());
		
		confirm_pwd = (EditText)findViewById(R.id.edit_reg_confirm_pwd);
		confirm_pwd.addTextChangedListener(new TextChangedListenerConfirmPwd());
		
		Button regLogin = (Button) findViewById(R.id.btn_reg_user);
		regLogin.setOnClickListener(new onClick());
	}
	
	/**
	 * 验证输入的用户名是否合法
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
			name =  (EditText)findViewById(R.id.edit_reg_login_name);
			check_userName = (TextView)findViewById(R.id.text_reg_check_userName);
			userName = name.getText().toString();
			if (Tool.checkUserName(userName)) {
				checkUser= true;
				check_userName.setTextColor(Color.GREEN);
				check_userName.setText("用户名输入正确...");
			}else {
				 checkUser =false;
				 check_userName.setTextColor(Color.RED);
				 check_userName.setText("16个字符，支持中英文、数字、减号或下划线");
			}
		}
		@Override
		public void afterTextChanged(Editable s) {
		}
		
	}
	
	/**
	 * 验证两次密码是否相同
	 * @author huir316
	 *
	 */
	private class TextChangedListenerConfirmPwd implements TextWatcher  {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			confirm_pwd = (EditText)findViewById(R.id.edit_reg_confirm_pwd);
			pwd = (EditText)findViewById(R.id.edit_reg_login_pwd);
			check_confirm_userPwd = (TextView)findViewById(R.id.text_reg_check_confirm_userPwd);
			userConfrimPwd = confirm_pwd.getText().toString();
			userPwd = pwd.getText().toString();
			if(userConfrimPwd.equals(userPwd)) {
				checkCondfirmPwd=true;
				check_confirm_userPwd.setTextColor(Color.GREEN);
				check_confirm_userPwd.setText("两次密码输入一致");
			}else {
				checkCondfirmPwd=false;
				check_confirm_userPwd.setTextColor(Color.RED);
				check_confirm_userPwd.setText("两次密码输入不一致");
			}
		}
		@Override
		public void afterTextChanged(Editable s) {
		}
		
	}
	
	/**
	 * 验证输入的密码是否合法
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
			pwd = (EditText) findViewById(R.id.edit_reg_login_pwd);
			check_userPwd = (TextView) findViewById(R.id.text_reg_check_userPwd);
			userPwd = pwd.getText().toString();
			if (Tool.checkUserPwd(userPwd)) {
				checkPwd=true;
				check_userPwd.setTextColor(Color.GREEN);
				check_userPwd.setText("密码输入正确...");
			}else {
				checkPwd=false;
				check_userPwd.setTextColor(Color.RED);
				check_userPwd.setText("请输入6-20 位，字母、数字、字符");
			}
		}
		@Override
		public void afterTextChanged(Editable s) {
		}
		
	}
	
	/**
	 * 按钮点击事件
	 * @author huir316
	 *
	 */
	class onClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_reg_user:
				if(checkUser && checkPwd &&  checkCondfirmPwd) {
					Tool.showDialog(RegActivity.this);
					new Handler(new Handler.Callback() {
				          //处理接收到的消息的方法
						@Override
						public boolean handleMessage(Message msg) {
							// TODO Auto-generated method stub
							Tool.showRegSuccessDialog(RegActivity.this);
							return false;
						}
				     }).sendEmptyMessageDelayed(0, 3000); //表示延时三秒进行任务的执行
				}
				break;
			}
		}
	}
	
	/*
	 * new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText name = (EditText) findViewById(R.id.edit_reg_login_name);
				EditText pwd = (EditText) findViewById(R.id.edit_reg_login_pwd);
				EditText confirm_pwd = (EditText) findViewById(R.id.edit_reg_confirm_pwd);
				
				TextView check_userName = (TextView) findViewById(R.id.text_reg_check_userName);
				TextView check_userPwd = (TextView) findViewById(R.id.text_reg_check_userPwd);
				TextView check_confirm_userPwd = (TextView) findViewById(R.id.text_reg_check_confirm_userPwd);
				String userName = name.getText().toString();
				String userPwd = pwd.getText().toString();
				String userConfrimPwd = confirm_pwd.getText().toString();
				Boolean checkUser;
				Boolean checkPwd;
				Boolean checkCondfirmPwd;
				if(Tool.checkUserName(userName)) {
					checkUser= true;
					check_userName.setTextColor(Color.GREEN);
					check_userName.setText("用户名输入正确...");
					
				}else{
					 checkUser =false;
					 check_userName.setTextColor(Color.RED);
					 check_userName.setText("16个字符，支持中英文、数字、减号或下划线");
				}
				
				if(Tool.checkUserPwd(userPwd)) {
					checkPwd=true;
					check_userPwd.setTextColor(Color.GREEN);
					check_userPwd.setText("密码输入正确...");
				}else {
					checkPwd=false;
					check_userPwd.setTextColor(Color.RED);
					check_userPwd.setText("请输入6-20 位，字母、数字、字符");
				}
				
				if(userConfrimPwd.equals(userPwd)) {
					checkCondfirmPwd=true;
					check_confirm_userPwd.setTextColor(Color.GREEN);
					check_confirm_userPwd.setText("两次密码输入一致");
				}else {
					checkCondfirmPwd=false;
					check_confirm_userPwd.setTextColor(Color.RED);
					check_confirm_userPwd.setText("两次密码输入不一致");
				}
				
				if(checkUser && checkPwd &&  checkCondfirmPwd) {
					Tool.showDialog(RegActivity.this);
					new Handler(new Handler.Callback() {
				          //处理接收到的消息的方法
						@Override
						public boolean handleMessage(Message msg) {
							// TODO Auto-generated method stub
							Tool.showRegSuccessDialog(RegActivity.this);
							return false;
						}
				     }).sendEmptyMessageDelayed(0, 3000); //表示延时三秒进行任务的执行
				}
			}
		});
	 * 
	 * */
}
