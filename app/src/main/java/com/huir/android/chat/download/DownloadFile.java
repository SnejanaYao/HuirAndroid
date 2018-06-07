package com.huir.android.chat.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * 下载文件
 * @author huir316
 *
 */
public class DownloadFile extends AsyncTask<String, Integer, String> {
	
	private ProgressDialog dialog;
	private Context context;
	private int FileLength; //文件总大小 
	private BufferedInputStream in; //输入流 
	private FileOutputStream out; //输出流
	private int count;//获取到的数据
	private int total; //获取到的总数据
	private Toast toast; //提示
	
    public DownloadFile(ProgressDialog dialog,Context context) {
		super();
		this.dialog = dialog;
		this.context = context;
	}

    @Override
    protected String doInBackground(String... sUrl) {
        try {
        	URL url = new URL(sUrl[0]);//建立url
        	URLConnection connection = url.openConnection(); //获取连接
        	if(connection != null) {
        		connection.connect();//打开连接
        		FileLength= connection.getContentLength(); //获取
            	in = new BufferedInputStream(url.openStream());//建立输入流 放入从连接获取的输入流
            	file (); //创建文件夹
            	 out = new FileOutputStream("/sdcard/DownLoad/com.huir.download/wandoujia.apk"); //获取 文件输出流 写入手机的具体位置  
            	byte[] read = new byte[1024]; //准备byte
            	total = 0;    //累加读取的数据  为 下面进度条计算百分比做准备
            	while((count= in.read(read)) != -1) {
            		total += count;//累加读取到数据
            		publishProgress((total *100)/FileLength);  //更新进度条
            		out.write(read, 0, count);
            	}
            	 dialog.dismiss();
                 out.flush();
    	         out.close();
    	         in.close();
        	}
        } catch (Exception e) {
        	Log.e("ChatActivity ", "下载出错["+e.getMessage()+"]");
        }
        return null;
    }
    
    /**
     * doInBackground 方法执行完毕后执行次方法
     */
    @Override
    protected void onPostExecute(String result) {
    	super.onPostExecute(result);
    	toast=Toast.makeText(context,"下载文件目录在: /sdcard/DownLoad/com.huir.download/",Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        dialog.setProgress(progress[0]);
    }
    
    public void file () {
    	File file = new File("/sdcard/DownLoad/com.huir.download");
    	if(!file.exists()) {
    		file.mkdir();
    	}
    }
}