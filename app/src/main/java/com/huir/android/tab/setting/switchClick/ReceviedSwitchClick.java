package com.huir.android.tab.setting.switchClick;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CompoundButton;

public class ReceviedSwitchClick implements CompoundButton.OnCheckedChangeListener {
    private static final  String TAG = "ReceviedSwitchClick";
    private static SharedPreferences sharedPreferences;
    private static Activity activity;

    public ReceviedSwitchClick(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setSwitchState(isChecked);
        if(isChecked){
            Log.d(TAG, "onCheckedChanged:  recevicedSwitch is checked");
        }else{
            Log.d(TAG, "onCheckedChanged: recevicedSwitch is not checked");
        }
    }

    /**
     *保存开关状态
     * @param isChecked
     */
    public void setSwitchState(boolean isChecked){
        sharedPreferences = getInstance();
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        editor.putBoolean("isReceviedChecked",isChecked);
        editor.apply();
    }

    /**
     * 获取SharedPreferences
     * @return SharedPreferences
     */
    public static SharedPreferences getInstance(){
        if(sharedPreferences==null){
            sharedPreferences =activity.getPreferences(Context.MODE_PRIVATE);
        }
        return  sharedPreferences;
    }
}
