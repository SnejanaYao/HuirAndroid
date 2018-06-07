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
 * 找人找群
 * @author huir316
 *
 */
public class AppFindActivity extends Activity implements OnClickListener{
	private LinearLayout groupLayout;
	private LinearLayout peopleLayout;
	
	private Button findReturn;
	
	private TextView groupText;
	private TextView peopleText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏  
        if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);   //透明导航栏
        }
		View contentView = getLayoutInflater().inflate(R.layout.activity_find_mine, null);
		setContentView(contentView);
		new KeyboardUtil(this, contentView); 
		initView();
	}

	private void initView() {
		groupLayout = (LinearLayout) findViewById(R.id.find_group_layout);
		groupLayout.setOnClickListener(this);
		groupText =  (TextView) findViewById(R.id.find_group_text);
		
		peopleLayout = (LinearLayout)findViewById(R.id.find_people_layout);
		peopleLayout.setOnClickListener(this);
		peopleText = (TextView)findViewById(R.id.find_people_text);
		
		findReturn =  (Button) findViewById(R.id.find_page_return);
		findReturn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int bule = Color.parseColor("#00BFFF");
		int black = Color.parseColor("#000000");
		switch (v.getId()) {
		case R.id.find_page_return:
			AppFindActivity.this.finish();
			break;
		case R.id.find_group_layout:
			groupLayout.setBackgroundResource(R.drawable.top_line_blue);
			groupText.setTextColor(bule);
			peopleLayout.setBackgroundResource(R.drawable.bottom_top_line1); //设置背景色
			peopleText.setTextColor(black);
			break;
		case R.id.find_people_layout:
			peopleLayout.setBackgroundResource(R.drawable.top_line_blue);
			peopleText.setTextColor(bule);
			groupLayout.setBackgroundResource(R.drawable.bottom_top_line1);//设置背景色
			groupText.setTextColor(black);
			break;
		}
	}
}
