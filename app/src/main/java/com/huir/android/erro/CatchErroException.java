package com.huir.android.erro;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * 异常处理类
 */
public class CatchErroException implements Thread.UncaughtExceptionHandler {
    private static  final  String TAG = "CatchErroException";

    private Context context;
    private Thread.UncaughtExceptionHandler defaultHandler;
    private Map<String,String> deviceInfo = new HashMap<>();
    private static CatchErroException catchErroException = new CatchErroException();
    private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());

    public static CatchErroException getInstance() {
        return  catchErroException;
    }


    public void setHandler(Context context){
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    /**
     * 当发生UncaughtException时会回调此函数
     * @param thread  发生异常的线程
     * @param ex  抛出的异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean isDone = doException(ex);
        if(isDone){
            System.exit(10);
        }
        Log.e("崩溃",thread.getName());
    }

    /**
     * 是否有异常出现
     * @param ex
     * @return   boolean
     */
    public boolean doException(Throwable ex){
        if(ex == null){
            return  true;
        }

        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context,"程序出现错误强制退出！",Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        collectDeviceInfo(context);
        saveExceptionToFile(ex);
        return true;
    }

    /**
     * 获取设备相关信息
     */
    public void collectDeviceInfo(Context context){
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if(packageInfo != null){
                deviceInfo.put("versionName",packageInfo.versionName);
                deviceInfo.put("versionCode",packageInfo.versionCode+"");
                deviceInfo.put("MODEL", Build.MODEL);
                deviceInfo.put("SDK_INT",Build.VERSION.SDK_INT+"");
                deviceInfo.put("PRODUCT",Build.PRODUCT);
                deviceInfo.put("TIME",getCurrentTime());
            }

        }catch (Exception e){

        }
        Field[] declaredFields = Build.class.getDeclaredFields();
        for(Field filed : declaredFields){
            try{
                filed.setAccessible(true);
                deviceInfo.put(filed.getName(),filed.get(null).toString());
            }catch (Exception e){

            }
        }
    }

    public void saveExceptionToFile(Throwable ex){
        StringBuffer stringBuffer = new StringBuffer();
        for(Map.Entry<String,String>  entry : deviceInfo.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuffer.append(key +"="+ value+"\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while(cause != null){
             cause.printStackTrace(printWriter);
             cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        stringBuffer.append(result);
        try{
            String time = df.format(new Date());
            String fileName = getCurrentTime()+".txt";
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                fileName();
                String path = fileName().getAbsolutePath()+"/"+fileName;
                FileOutputStream fileOutputStream = new FileOutputStream(path + fileName);
                fileOutputStream.write(stringBuffer.toString().getBytes());
                fileOutputStream.close();
            }
        }catch (Exception e){

        }
    };

    public String getCurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mmm-dd HH:mm:sss");
        String time =  simpleDateFormat.format(new Date());
        Log.e("TAG     ", "getCurrentTime: " + time  );
        return time;
    };

    /**
     * 判断文件夹是否存在 不存在就新建
     */
    public File fileName() {
        File file = new File("/sdcard/DownLoad/com.huir.download/ErrLog");
        if(!file.exists()) {
            file.mkdir();
        }
        return  file;
    }

}
