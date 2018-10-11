package com.huir.android.tab.setting;

import com.huir.android.tab.setting.dialog.AreaDialogManager;
import com.huir.android.tab.setting.dialog.AreaDialogManager.AreaSpinnerCallBackClickListener;
import com.huir.test.R;
import com.huir.android.tab.setting.dialog.BornDialogManager;
import com.huir.android.tab.setting.dialog.BornDialogManager.BornSpinnerCallBackClickListener;
import com.huir.android.tool.KeyboardUtil;
import com.huir.android.tool.Tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * 个人信息主页
 * @author huir316
 *
 */
public class SettingActivity extends Activity  implements OnClickListener{
	private BornDialogManager bornDialogManager;
	private AreaDialogManager areaDialogManager;
	
	private Button returnBack;
	private RelativeLayout userLayout;
	private RelativeLayout mianpageLayout;
	private RelativeLayout signLayout;
	private RelativeLayout zipCodeLayout;
	private RelativeLayout telLayout;
	private RelativeLayout mobileLayout;
	private RelativeLayout emailLayout;
	private RelativeLayout adressLayout;
	private RelativeLayout bornLayout;
	private RelativeLayout areaLayout;
	
	
	private static final int USER=1;
	private static final String CHANGE_NAME="用户名称修改";
	
	private static final int SIGN=2;
	private static final String CHANGE_SIGN="修改个人备注信息";
	
	private static final int MAINPAGE=3;
	private static final String CHANGE_MAINPAGE="主页";
	
	private static final int ZIPCODE=4;
	private static final String CHANGE_ZIPCODE="邮编";
	
	private static final int TEL=5;
	private static final String CHANGE_TEL="联系电话";
	
	private static final int MOBILE=6;
	private static final String CHANGE_MOBILE="手机号码";
	
	private static final int EMAIL=7;
	private static final String CHANGE_EMAIL="邮箱地址";
	
	private static final int ADRESS=8;
	private static final String CHANGE_ADRESS="详细地址";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        config();
		View contentView = getLayoutInflater().inflate(R.layout.activity_personal_info, null);
		setContentView(contentView);
		new KeyboardUtil(this, contentView); 
		initView();
	}

	private  void  config(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏
        if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
        }
    }

	private void initView() {
		returnBack = (Button) findViewById(R.id.personal_chat_return);
		returnBack.setOnClickListener(this);
		
		userLayout = (RelativeLayout) findViewById(R.id.personal_detail_layout);
		userLayout.setOnClickListener(this);
		
		signLayout = (RelativeLayout) findViewById(R.id.personal_sign_layout);
		signLayout.setOnClickListener(this);
		
		mianpageLayout = (RelativeLayout) findViewById(R.id.personal_mainpage_layout);
		mianpageLayout.setOnClickListener(this);
		
		zipCodeLayout = (RelativeLayout) findViewById(R.id.personal_zipcode_layout);
		zipCodeLayout.setOnClickListener(this);
		
		telLayout = (RelativeLayout) findViewById(R.id.personal_tel_layout);
		telLayout.setOnClickListener(this);
		
		mobileLayout = (RelativeLayout) findViewById(R.id.personal_mobile_layout);
		mobileLayout.setOnClickListener(this);
		
		emailLayout = (RelativeLayout) findViewById(R.id.personal_email_layout);
		emailLayout.setOnClickListener(this);
		
		adressLayout = (RelativeLayout) findViewById(R.id.personal_adress_layout);
		adressLayout.setOnClickListener(this);
		
		bornLayout = (RelativeLayout) findViewById(R.id.personal_born_layout);
		bornLayout.setOnClickListener(this);

        areaLayout = (RelativeLayout) findViewById(R.id.personal_area_layout);
        areaLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.personal_chat_return:
			SettingActivity.this.finish();
			break;
		case R.id.personal_detail_layout:
			starChangeInfoActivity(USER,CHANGE_NAME);
			break;
		case R.id.personal_sign_layout:
			starChangeInfoActivity(SIGN,CHANGE_SIGN);
			break;
		case R.id.personal_mainpage_layout:
			starChangeInfoActivity(MAINPAGE,CHANGE_MAINPAGE);
			break;
		case R.id.personal_zipcode_layout:
			starChangeInfoActivity(ZIPCODE,CHANGE_ZIPCODE);
			break;
		case R.id.personal_tel_layout:
			starChangeInfoActivity(TEL,CHANGE_TEL);
			break;
		case R.id.personal_mobile_layout:
			starChangeInfoActivity(MOBILE,CHANGE_MOBILE);
			break;
		case R.id.personal_email_layout:
			starChangeInfoActivity(EMAIL,CHANGE_EMAIL);
			break;
		case R.id.personal_adress_layout:
			starChangeInfoActivity(ADRESS,CHANGE_ADRESS);
			break;
		case R.id.personal_born_layout:
			bornDialogManager = new BornDialogManager(SettingActivity.this);
			bornDialogManager.showBornDialog();
			bornDialogManager.setBornCallBackClickListener(new BornSpinnerCallBackClickListener() {
                @Override
                public void click(View view, Integer yr, Integer mth, Integer day) {
                    // TODO 点击事件监听
                    switch (view.getId()) {
                        case R.id.born_save_btn:
                            Tool.showBornSureDialog(SettingActivity.this, yr, mth, day);
                            bornDialogManager.dismissBornDialog();
                            break;
                        case R.id.born_cancel_btn:
                            bornDialogManager.dismissBornDialog();
                            break;
                    }
                }
            });
			break;
		case R.id.personal_area_layout:
            areaDialogManager  = new AreaDialogManager(SettingActivity.this);
            areaDialogManager.showAreaDialog();
            areaDialogManager.setAreaCallBackClickListener(new AreaSpinnerCallBackClickListener() {
                @Override
                public void click(View view, String strCountry,String strProvice, String strCity) {
                    switch (view.getId()){
                        case R.id.area_save_btn:
                            Tool.showAreaSureDialog(SettingActivity.this,strCountry,strProvice,strCity);
                            areaDialogManager.dismissBornDialog();
                            break;
                        case R.id.area_cancel_btn:
                            areaDialogManager.dismissBornDialog();
                            break;
                    }
                }
            });
			break;
		}
	}
	
	private void starChangeInfoActivity(int id,String title){
		Intent intent = new Intent();
		intent.putExtra("id", id);
		intent.putExtra("title", title);
		intent.setClass(SettingActivity.this, ChangeInfoActivity.class);
		startActivity(intent);
	}
}
