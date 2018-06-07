package com.huir.android.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 判断网络状态
 * @author huir316
 *
 */
public class NetWorkTool {
	/**
	 * 判断当前网络是否可用
	 * @param context
	 * @return boolean
	 */
	public static  boolean isNetWorkConnected(Context context){
		if(context !=null) {
			ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();
			if(networkInfo !=null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 判断wifi是否可用
	 * @param context
	 * @return boolean
	 */
	public static boolean isWifiConnected(Context context) {
		if(context !=null) {
			ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if(networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 判断移动数据是否可用
	 * @param context
	 * @return boolean
	 */
	public static boolean isMobileConnected(Context context) {
		if(context !=null) {
			ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if(networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}
	/**
	 * 判断当前网络状态
	 * @param context
	 * @return type
	 */
	public static int typeConnected(Context context) {
			int type=0;
			ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo[] infos=manager.getAllNetworkInfo();
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();
			if(networkInfo ==null) {
				return type;
			}
			int nType = networkInfo.getType();
			switch (nType) {
			case ConnectivityManager.TYPE_WIFI:
				boolean wifi = isWifiConnected(context);
				if(wifi) {
					type=1;//wifi
				}
				break;
			case ConnectivityManager.TYPE_MOBILE:
				TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				int mType = networkInfo.getSubtype();
				boolean mobile = isMobileConnected(context);
				if(mobile) {
					switch (mType) {
					case TelephonyManager.NETWORK_TYPE_LTE:
							type = 2; //4G
							break;
					case TelephonyManager.NETWORK_TYPE_UMTS:
			        case TelephonyManager.NETWORK_TYPE_EVDO_0:
			        case TelephonyManager.NETWORK_TYPE_EVDO_A:
			        case TelephonyManager.NETWORK_TYPE_HSDPA:
			        case TelephonyManager.NETWORK_TYPE_HSUPA:
			        case TelephonyManager.NETWORK_TYPE_HSPA:
			        case TelephonyManager.NETWORK_TYPE_EVDO_B:
			        case TelephonyManager.NETWORK_TYPE_EHRPD:
			        case TelephonyManager.NETWORK_TYPE_HSPAP:
							type=3; //3G
							break;
			        case TelephonyManager.NETWORK_TYPE_GPRS:
			        case TelephonyManager.NETWORK_TYPE_EDGE:
			        case TelephonyManager.NETWORK_TYPE_CDMA:
			        case TelephonyManager.NETWORK_TYPE_1xRTT:
			        case TelephonyManager.NETWORK_TYPE_IDEN:
							type =4; //2G
							break;
						}
					}
				break;
			}
			return type;
	}
}
