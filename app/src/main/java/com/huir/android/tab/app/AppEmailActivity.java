package com.huir.android.tab.app;

import com.huir.test.R;
import com.huir.android.tool.KeyboardUtil;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * 我的邮箱
 * @author huir316
 *
 */
public class AppEmailActivity extends Activity implements OnClickListener{
	private Button emailReturn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏  
        if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);   //透明导航栏
        }
		View contentView = getLayoutInflater().inflate(R.layout.activity_email_mine, null);
		setContentView(contentView);
		new KeyboardUtil(this, contentView); 
		initView();
	}

	private void initView() {
		emailReturn = (Button) findViewById(R.id.email_page_return);
		emailReturn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int bule = Color.parseColor("#00BFFF");
		int black = Color.parseColor("#000000");
		switch (v.getId()) {
		case R.id.email_page_return:
			AppEmailActivity.this.finish();
			break;
		}
	}
}
