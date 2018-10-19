package com.huir.android.chat.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huir.android.entity.Msg;

import java.io.File;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 异步图片加载
 * @author huir316
 *
 */
public class ImageAsy extends AsyncTask<String, Integer, String> {
    private static final  String TAG = "ImageAsy";
    private ImageAsy imageAsy;

    private Context context;
    private ImageView imageView;

   // private BitMapListener bitMapListener; //TODO 此接口供Adapter使用  目的是传输path 给ChatActivity使用
    private Bitmap bitmap;
    private  String name;
    private String path;
    private List<Msg> data;

    public ImageAsy(){

    }

    public ImageAsy(Context context, ImageView imageView,List<Msg> data){
        this.context = context;
        this.imageView = imageView;
        this.data = data;
        Log.e(TAG, "ImageAsy:   we  get the list form chatViewAdapter    and the size is  "  + data.size());
    }

    public ImageAsy(Context context, ImageView imageView,String path){
        this.context = context;
        this.imageView = imageView;
        this.path = path;
        Log.e(TAG, "ImageAsy:   we  get the list form chatViewAdapter    and the size is  "  + path);
    }

    @Override
    protected String doInBackground(String... sUrl) {
       /* File file = new File(path);
        String fileName = file.getAbsolutePath();
        if(!file.exists()){
            file.mkdirs();
            fileName = file.getName();
            Log.e("Tag","不存在   " + fileName);
        }else{
            Log.e("Tag","存在" +fileName);
        }
        Luban.get(context).load(file).putGear(Luban.THIRD_GEAR).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                Log.e("Success","start");
            }

            @Override
            public void onSuccess(File file) {
                String name = file.getAbsolutePath();
                Log.e("Success",name);
                bitmap = BitmapFactory.decodeFile(name);
                Log.e("file mber",bitmap.getWidth() + "*" + bitmap.getHeight() + "-->" + bitmap.getByteCount() +"--? path   " +name);
                if(bitmap != null && imageView !=null){
                    Log.e("bitmap"," != null");
                    imageView.setImageBitmap(bitmap);
                    bitMapListener.getBitMap(name);
                }else{
                    Log.e("bitmap"," == null");
                }
            }
            @Override
            public void onError(Throwable e) {
                Log.e("Success","faild");
            }
        }).launch(); //启动压缩 ;*/
        return null;
    }
    
    /**
     * doInBackground 方法执行完毕后执行次方法
     */
    @Override
    protected void onPostExecute(String result) {
    	super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
    }


    /**
     * 接口回调
     * @author huir316
     *
     */
    /*public static  interface BitMapListener {
        void getBitMap(String path);
    };

    public void setBitMapListener(BitMapListener bitMapListener) {
        if(bitMapListener !=null) {
            this.bitMapListener = bitMapListener;
        }
    }*/
}