package com.huir.android.tab.setting;

import com.huir.test.R;
import com.huir.android.tool.KeyboardUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * 个人信息文本框修改适配页面
 * @author huir316
 *
 */
public class ChangeInfoActivity extends Activity implements OnClickListener {
	private TextView changeInfo;
	private Button returnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏  
		if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
	          getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);   //透明导航栏  
	     }
		View contentView = getLayoutInflater().inflate(R.layout.change_personal_info, null);
		setContentView(contentView);
		new KeyboardUtil(this, contentView); 
		initView();
	}

	private void initView() {
		changeInfo = (TextView) findViewById(R.id.change_personal_text_title);
		Intent intent = getIntent();
		String strID = intent.getStringExtra("id");
		String title = intent.getStringExtra("title");
		changeInfo.setText(title);
		
		returnBack = (Button) findViewById(R.id.change_personal_chat_return);
		returnBack.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_personal_chat_return:
			ChangeInfoActivity.this.finish();
			break;
		}
	}
}
