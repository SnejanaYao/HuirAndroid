package com.huir.android.net;

import com.huir.android.tool.NetWorkTool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 监听网络状态广播
 * @author huir316
 *
 */
public class NetBroad extends BroadcastReceiver {
	private final String TAG = "NetBroad";
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			int type = NetWorkTool.typeConnected(context);
			switch (type) {
			case 0:
				Toast.makeText(context, "没有网络连接....", Toast.LENGTH_LONG).show();
				break;
			case 1:
				Toast.makeText(context, "网络切换到WIFI状态....", Toast.LENGTH_LONG).show();
				break;
			case 2:
				Toast.makeText(context, "网络切换到4G状态....", Toast.LENGTH_LONG).show();
				break;
			case 3:
				Toast.makeText(context, "网络切换到3G状态....", Toast.LENGTH_LONG).show();
				break;
			case 4:
				Toast.makeText(context, "网络切换到2G状态....", Toast.LENGTH_LONG).show();
				break;
			}
		}else {
			Log.e(TAG, "当前网络连接不可用请检查网络.......");
		}
	}
}

