package com.huir.android.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class NetService extends Service {
	private String TAG = "NetService";
	private String THR = "Thread";
		
	private final int PORT=8989;
	private final String ADRESS="10.10.10.11";
		
	private boolean flagConnect =false;
	private boolean canSend = true;
	private boolean flag103 = true;
	private boolean flagThread = true;
		
	private Socket socket;
	private InputStream inPut;
	private OutputStream outPut;
		
	private byte command;
	private int str_length;
	private int all_length;
	private String  msg;
	
	private NetBroad netBroad = new NetBroad();
	@Override
	public IBinder onBind(Intent intent) {
			return null;
	}
		
		
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "onStartCommand");
		flag103=true;
		if(flagConnect) {
			if(canSend) {
				Log.e(TAG, "can  send message ......");
			}else {
				Log.e(TAG, "cant send message ......");
			}
		}else {
			Log.e(TAG, "wait connect");
		}
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onCreate() {
		Log.e(TAG, "onCreate");
		super.onCreate();
		connection(ADRESS, PORT);
		
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(netBroad, mFilter);
	}
	
	@Override
	public void onDestroy() {
		 Log.e(TAG, "onDestroy");
		super.onDestroy();
		flagThread=false;
		unregisterReceiver(netBroad);
		
	}
		
	/**
	 * 建立socket连接
	 * @param ip 连接地址
	 * @param port 连接端口
	 */
	private  void connection(final String ip,final int port) {
		socket = new Socket();
		new Thread() {
			public void run() {
				try {
					//建立socket连接
					SocketAddress socketAddress = new InetSocketAddress(ip, port);
					socket.connect(socketAddress);
				} catch (Exception e) {
					Log.e(TAG, "建立连接失败.....");
				}
			};
			}.start();
	   }
	
	
	
	
	/** 
	* 初始化socket
	* @param ip,port
	* @return socket
	*/ 
	private Socket init(final String ip,final int port) {
		try {
		//建立socket连接
		SocketAddress socketAddress = new InetSocketAddress(ip, port);
		socket = new Socket();
		socket.connect(socketAddress);
		socket.setKeepAlive(true);//开启保持活动状态的套接字  
		socket.setSoTimeout(30);//设置超时时间
		}catch (Exception e) {
			Log.e(THR, " 建立连接失败.......");
		}
		return socket;
	}
		
	private void deCode() {
		new Thread() {
			@Override
			public void run() {
				byte[] temp = null;
				turn01:
					while (flagThread) {
						try {
							sleep(1000);
							inPut = socket.getInputStream();
							int len  = inPut.available();
							if(len<0) {
								sleep(3000);
							}else {
								Log.e(THR,len+"");
							}
							byte[] tempb = new byte[len];
							inPut.read(tempb, 0, len);
							if(temp != null) {
								sleep(1000);
								Log.e(THR , "《===断包后的处理===》");
								Log.e(THR , "上次读取的长度为: " + temp.length);
								Log.e(THR , "本次读取到的长度为: " + tempb.length);
								byte[] all = new byte[temp.length+tempb.length];
								System.arraycopy(temp, 0, all, 0, temp.length);
								System.arraycopy(tempb, 0, all, temp.length, tempb.length);
								tempb = all;
								Log.e(THR,"断包 处理完成后的长度为: " + tempb.length);
							}
								sleep(1000);
								turn02:
									while (flag103) {
										sleep(1000);
										Log.e("tempb  " , new String(tempb,"utf-8"));
										if(tempb[0] ==-103) {
											sleep(2000);
											String heart = new String(tempb, "utf-8");
											Log.e(THR,"《====读到心跳包====》 "+heart);
											if(len>1) {
												byte[] b = new byte[len-1]; //去除心跳包后的长 用来实现复制
												byte[] t = tempb; //
												System.arraycopy(t,1,b,0,b.length);
												tempb = b;
												len = len-1;
											}
											continue turn01;
										}
										Log.e(THR, "《================开始解码================》");
										command = tempb[0]; //标识位
										Log.e(THR,"command "  + command);
										str_length = tempb[1];
										if(str_length >0) { //过滤掉非法的数据包
											String str = new String(tempb,2,str_length,"utf-8");
											int lenlne = Integer.parseInt(str);
											all_length = 2+str_length+lenlne;
											Log.e(THR,"  command ["+command+"] str_length[" + str_length+"] lenlen[" +lenlne+"]");
											if(all_length >len) {
												sleep(1000);
												Log.e(THR,"数据读取不完整.....");
												temp = new byte[len];
												System.arraycopy(tempb, 0, temp, 0, tempb.length);
												Log.e(THR,"第一次读到的长度-----》 " + temp.length);
												sleep(500);
												continue turn01;
											}
											msg = new String(tempb,2+str_length,lenlne,"utf-8");
											Log.e(THR,"  mgs "+msg);
											flag103 = false;
												
											if(all_length<len) {
												sleep(1000);
												Log.e(THR ,"粘包了...实际读到的数据就只有["+all_length+"] 读到的字节数组长度为 ["+len+"]");//读多了
												byte[] more = new byte[len-all_length]; //原先长度 减去 读取到的所有字节长度 即为 粘包了的包长度
												byte[] t = tempb;  
												System.arraycopy(t,all_length,more,0,more.length); //浅复制
												tempb = more;
												len = len - all_length;
												flag103 = true;
												continue turn02;
											}
										}else {
											continue turn01;
										}
									}
							} catch (Exception e) {
								Log.e(THR,"数据异常");
						}
					}
				}
			}.start();
	}
		
		
		private Handler mHandlr = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case ConnectAPI.STATE_CONNECT:
					String obj = (String)msg.obj;
					if(obj.equals(ConnectAPI.CONNECT_SUCCESS+"")) {
						flagConnect=true;
						deCode();
					}else {
						flagConnect=false;
					}
					break;
				case ConnectAPI.STATE_SEND:
					canSend=true;
					break;
				default:
					break;
				}
			};
		};
	}
