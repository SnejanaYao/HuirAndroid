package com.huir.android.tab.setting;

import com.huir.test.R;
import com.huir.android.tool.KeyboardUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * 个人收藏
 * @author huir316
 *
 */
public class SetCollectActivity extends Activity implements OnClickListener {
	private Button collectReturn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏  
		if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {  
	          getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏  
	          getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);   //透明导航栏  
	     }
		View contentView = getLayoutInflater().inflate(R.layout.activity_collect_set, null);
		setContentView(contentView);
		new KeyboardUtil(this, contentView); 
		initView();
	}


	private void initView() {
		collectReturn = (Button) findViewById(R.id.collect_page_return);
		collectReturn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.collect_page_return:
			SetCollectActivity.this.finish();
			break;
		}
	}
}
