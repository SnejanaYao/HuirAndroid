package com.huir.android.tab.setting.switchClick;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CompoundButton;

public class SoundSwitchClick implements CompoundButton.OnCheckedChangeListener {
    private static final  String TAG = "SoundSwitchClick";
    private static SharedPreferences sharedPreferences;
    private static Activity activity;

    public SoundSwitchClick(Activity activity) {
        this.activity = activity;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setSwitchState(isChecked);
        if(isChecked){
            Log.d(TAG, "onCheckedChanged: soundSwitch is checked");
        }else{
            Log.d(TAG, "onCheckedChanged: soundSwitch is not checked");
        }
    }

    /**
     *保存开关状态
     * @param isChecked
     */
    public void setSwitchState(boolean isChecked){
        sharedPreferences = getInstance();
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        editor.putBoolean("isSoundChecked",isChecked);
        editor.apply();
    }

    /**
     * 获取SharedPreferences
     * @return SharedPreferences
     */
    public static  SharedPreferences getInstance(){
        if(sharedPreferences==null){
            sharedPreferences =activity.getPreferences(Context.MODE_PRIVATE);
        }
        return  sharedPreferences;
    }
}
