package com.huir.android.chat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.huir.android.tool.KeyboardUtil;
import com.huir.test.R;


import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class PhotoViewActivity extends AppCompatActivity implements OnClickListener{
    private PhotoView photoShow;
    private LinearLayout photoLayout;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config();
		View contentView = getLayoutInflater().inflate(R.layout.activity_photo_view, null);
        setContentView(contentView);
		new KeyboardUtil(this, contentView);
		initView();
	}


    /**
     *Activity窗体基本配置
     */
	private  void  config(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏
        if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
        }
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);//隐藏状态栏
        getSupportActionBar().hide();
    }
	
	/**
	 * 初始化控件
	 */
	private void initView() {
	    Intent intent = getIntent();
	    String path = intent.getStringExtra("path");
	    photoLayout = findViewById(R.id.photo_layout);
        photoLayout.setOnClickListener(this);

        photoShow = findViewById(R.id.photo_show_chat_image);
        photoShow.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                PhotoViewActivity.this.finish();
            }
        });
        if(path != null){
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Log.e("Photo file mber",bitmap.getWidth() + "*" + bitmap.getHeight() + "-->" + bitmap.getByteCount() +"--? path   " +path);
            photoShow.setImageBitmap(bitmap);
        }else{
            Log.e("tag","bitmap 错误");
        }

	}


	@Override
	public void onClick(View v) {
        switch (v.getId()){
            case R.id.photo_layout:
                PhotoViewActivity.this.finish();
                break;
        }
	}
}


