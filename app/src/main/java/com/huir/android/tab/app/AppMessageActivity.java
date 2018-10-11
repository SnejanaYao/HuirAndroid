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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 我的消息
 * @author huir316
 *
 */
public class AppMessageActivity extends Activity implements OnClickListener{
	private LinearLayout broadLayout;
	private LinearLayout sysLayout;
	
	private Button pageReturn;
	
	private TextView broadText;
	private TextView sysText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏  
        if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态
        }
		View contentView = getLayoutInflater().inflate(R.layout.activity_message_mine, null);
		setContentView(contentView);
		new KeyboardUtil(this, contentView); 
		initView();
	}

	private void initView() {
		broadLayout = (LinearLayout) findViewById(R.id.message_broad_layout);
		broadLayout.setOnClickListener(this);
		broadText =  (TextView) findViewById(R.id.message_broad_text);
		
		sysLayout = (LinearLayout) findViewById(R.id.message_system_layout);
		sysLayout.setOnClickListener(this);
		sysText =  (TextView) findViewById(R.id.message_system_text);
		
		pageReturn =  (Button)findViewById(R.id.message_mine_page_return);
		pageReturn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int bule = Color.parseColor("#00BFFF");
		int black = Color.parseColor("#000000");
		switch (v.getId()) {
		case R.id.message_mine_page_return:
			AppMessageActivity.this.finish();
			break;
		case R.id.message_broad_layout:
			broadLayout.setBackgroundResource(R.drawable.top_line_blue);
			broadText.setTextColor(bule);
			sysLayout.setBackgroundResource(R.drawable.bottom_top_line1); //设置背景色
			sysText.setTextColor(black);
			break;
		case R.id.message_system_layout:
			sysLayout.setBackgroundResource(R.drawable.top_line_blue);
			sysText.setTextColor(bule);
			broadLayout.setBackgroundResource(R.drawable.bottom_top_line1);//设置背景色
			broadText.setTextColor(black);
			break;
		}
	}
}
