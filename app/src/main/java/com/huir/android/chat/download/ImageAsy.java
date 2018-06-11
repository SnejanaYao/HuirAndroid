package com.huir.android.chat.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 异步图片加载
 * @author huir316
 *
 */
public class ImageAsy extends AsyncTask<String, Integer, String> {
    private Context context;
    private ImageView imageView;
    private Bitmap bitmap;
    private PhotoView photoView;

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }
    public ImageAsy(){}

    public ImageAsy(Context context, ImageView imageView,PhotoView photoView){
        this.context = context;
        this.imageView = imageView;
        this.photoView = photoView;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        File file = new File("/sdcard/Pictures/huir/test.jpg");
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
                String name =  "/data/data/com.huir.test/cache/luban_disk_cache/1528697134546";
                Log.e("Success",name);
                Bitmap bitmap = BitmapFactory.decodeFile(name);
                int width  = bitmap.getWidth();
                int height = bitmap.getHeight();
                Log.e("file mber",bitmap.getWidth() + "*" + bitmap.getHeight() + "-->" + bitmap.getByteCount() +"--? path   " +name);
                if(bitmap != null && photoView !=null){
                    Log.e("bitmap"," != null");
                    photoView.setImageBitmap(bitmap);
                    setBitmap(bitmap);
                }else{
                    Log.e("bitmap"," == null");
                }
            }
            @Override
            public void onError(Throwable e) {
                Log.e("Success","faild");
            }
        }).launch(); //启动压缩 ;
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
}