package com.huir.android.tab.setting;

import com.huir.android.tab.setting.switchClick.DetailsSwitchClick;
import com.huir.android.tab.setting.switchClick.ReceviedSwitchClick;
import com.huir.android.tab.setting.switchClick.ShockSwitchClick;
import com.huir.android.tab.setting.switchClick.SoundSwitchClick;
import com.huir.test.R;
import com.huir.android.tool.KeyboardUtil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;

/**
 * 通知提醒
 * @author huir316
 *
 */
public class SetNotifyActivity extends Activity implements OnClickListener {
    private static final  String TAG = "SetNotifyActivity";
    private Activity activity = SetNotifyActivity.this;
    private View view;
    private ReceviedSwitchClick receviedSwitchClick = new ReceviedSwitchClick(activity);
    private DetailsSwitchClick detailsSwitchClick = new DetailsSwitchClick(activity);
    private SoundSwitchClick soundSwitchClick = new SoundSwitchClick(activity);
    private ShockSwitchClick shockSwitchClick = new ShockSwitchClick(activity);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        config();
        view= getLayoutInflater().inflate(R.layout.activity_notify_set, null);
		setContentView(view);
		new KeyboardUtil(this, view);
		initView();
		initEvent();
	}

	private  void  config(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏
        if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
        }
    }

    private Button notifyReturn;
	private Switch recevicedSwitch;
    private Switch detailsSwitch;
    private Switch soundSwitch;
    private Switch shockSwitch;
	private void initView() {
		notifyReturn = (Button) findViewById(R.id.notify_page_return);
		notifyReturn.setOnClickListener(this);

		recevicedSwitch = (Switch) findViewById(R.id.notify_receviced_switch);
		recevicedSwitch.setOnCheckedChangeListener(receviedSwitchClick);

        detailsSwitch = (Switch) findViewById(R.id.notify_details_switch);
        detailsSwitch.setOnCheckedChangeListener(detailsSwitchClick);


        soundSwitch = (Switch) findViewById(R.id.notify_sound_switch);
        soundSwitch.setOnCheckedChangeListener(soundSwitchClick);

        shockSwitch = (Switch) findViewById(R.id.notify_shock_swicth);
        shockSwitch.setOnCheckedChangeListener(shockSwitchClick);
	}

	public void initEvent(){
        SharedPreferences rPreferences = receviedSwitchClick.getInstance();
        boolean rState = rPreferences.getBoolean("isReceviedChecked",true);
        recevicedSwitch.setChecked(rState);

        SharedPreferences dPreferences = detailsSwitchClick.getInstance();
        boolean dState = dPreferences.getBoolean("isDetailChecked",true);
        detailsSwitch.setChecked(dState);

        SharedPreferences soundPreferences = soundSwitchClick.getInstance();
        boolean soundState = soundPreferences.getBoolean("isSoundChecked",true);
        soundSwitch.setChecked(soundState);

        SharedPreferences shockPreferences = shockSwitchClick.getInstance();
        boolean shockState = shockPreferences.getBoolean("isShockChecked",true);
        shockSwitch.setChecked(shockState);

    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.notify_page_return:
			SetNotifyActivity.this.finish();
			break;
		}
	}
}
