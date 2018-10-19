package com.huir.android.chat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huir.android.chat.adapter.PreviewPageViewAdapter;
import com.huir.android.chat.transmission.BigDataTransmission;
import com.huir.android.entity.Image;
import com.huir.test.R;

import java.util.ArrayList;



public class PreviewPicturesActivity extends AppCompatActivity implements  ViewPager.OnPageChangeListener {
    private static final String TAG = "PreviewPicturesActivity";
    public int positions;
    private boolean previewConfirm = false;
    private boolean isShowBar = true;  //默认状态栏等为显示状态
    private Image image;
    private ArrayList<Image> mImages;
    private BitmapDrawable isSelectDrawable;
    private BitmapDrawable unSelectDrawable;
    private ArrayList<Image> mSelectedImages = new ArrayList<>();
    private PreviewPageViewAdapter viewAdapter;


    public void setMImages(ArrayList<Image> mImages){
        this.mImages = mImages;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config();
        View inflate = getLayoutInflater().inflate(R.layout.activity_preview, null);
        setContentView(inflate);// 初始化设置状态栏显示
        getPassOnObj() ;
        initView();
        initEvent();
        Log.e(TAG, "onCreate: " + mSelectedImages.size() );
        if(mImages != null && mImages.size() >0){
            image = mImages.get(positions);
            changeSelect(image); //初始化选中效果
        }
    }

    /**
     * 获取其他界面传递的值
     */
    private void getPassOnObj() {
        Bundle bundle = getIntent().getExtras();
        mImages = BigDataTransmission.getInstance().getList();
        mSelectedImages = bundle.getParcelableArrayList("selectImages");
        positions = bundle.getInt("position");
    }


    /**
     * Activity窗体基本配置
     */
    private void config() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        getSupportActionBar().hide();
    }


    private TextView choice;
    private TextView number;
    private TextView confirm;

    private LinearLayout fBack;
    private LinearLayout isSend;

    private RelativeLayout topBar;
    private RelativeLayout bottomBar;

    private ViewPager previewPager;

    private void initView() {
        choice = findViewById(R.id.rl_tv_select);

        previewPager = findViewById(R.id.rl_vp_image);

        fBack = findViewById(R.id.rl_btn_back);

        confirm = findViewById(R.id.rl_tv_confirm);

        number = findViewById(R.id.rl_tv_indicator);

        topBar = findViewById(R.id.rl_top_bar);

        bottomBar = findViewById(R.id.rl_bottom_bar);

        isSend = findViewById(R.id.rl_btn_confirm);
        isSend.setEnabled(true);

        Resources resources = getResources();
        Bitmap selectBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_image_select);
        isSelectDrawable = new BitmapDrawable(resources, selectBitmap);
        isSelectDrawable.setBounds(0, 0, 52, 52);

        Bitmap unSelectBitMap = BitmapFactory.decodeResource(resources, R.drawable.icon_image_un_select);
        unSelectDrawable = new BitmapDrawable(resources,unSelectBitMap);
        unSelectDrawable.setBounds(0,0,52,52);
    }


    private void initEvent() {
        unSelectedImage();
        number.setText((positions + 1) + "/" + mImages.size());

        fBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });     //点击返回按钮 退出当前界面


        isSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewConfirm = true;   // 点击确定按钮previewConfirm始终为true
                Log.e(TAG, "onClick: " + mSelectedImages.size());
                if( mSelectedImages.size() ==0){
                    mSelectedImages.add(image);
                }
                setSelectImageCount(mSelectedImages.size());
                changeSelect(image);
                finish();

            }
        });

        viewAdapter = new PreviewPageViewAdapter(mImages, this);
        previewPager.setAdapter(viewAdapter);
        previewPager.setCurrentItem(positions, false);
        previewPager.addOnPageChangeListener(this);

        viewAdapter.setPreviewOnItemClickListener(new PreviewPageViewAdapter.PreviewOnItemClickListener() {
            @Override
            public void onClick(int position, Image image) {
                if (isShowBar) {
                    hideBars();
                } else
                    showBars();
            }
        });

        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = previewPager.getCurrentItem();
                if(mImages != null && mImages.size() > positions){
                    image = (Image)mImages.get(position);
                    if(image !=null &&  mSelectedImages.contains(image)){
                        mSelectedImages.remove(image);
                    }else if(image != null  && !mSelectedImages.contains(image) && mSelectedImages.size() < 9){
                        mSelectedImages.add(image);
                    }
                }
                image = (Image) mImages.get(previewPager.getCurrentItem());
                changeSelect(image);
            }
        });
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        number.setText((position+1)+"/"+mImages.size());   //刚刚点击时 显示张数比
    }

    @Override
    public void onPageSelected(int position) {
        number.setText((position+1)+"/"+mImages.size());
        image = mImages.get(position);
        changeSelect(image);
        Log.e(TAG, "onPageSelected:   is  selected   "    + image .getPath() );  //滑动时 显示张数
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void finish() {
        finishPageAndSend();
        super.finish();
    }

    /**
     * 当页面停止运行 带参数返回
     */
    private void finishPageAndSend(){
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("previewSelectedImages",mSelectedImages);  //当前页面操作过的list
        intent.putExtra("previewConfirm",previewConfirm); //是否点击发送
        intent.setClass(getApplicationContext(),PreviewPicturesActivity.class);
        setResult(10000,intent);
    }

    /**
     * 改变是否选中状态
     * @param image
     */
    private void changeSelect(Image image) {
        choice.setCompoundDrawables(mSelectedImages.contains(image) ?
                isSelectDrawable : unSelectDrawable, null, null, null);
        setSelectImageCount(mSelectedImages.size());
    }

    /**
     * TODO 预览 确定 文字的 显示改变
     * @param count
     */
    private  void setSelectImageCount(int count){
        if(count ==0){
            confirm.setText("确定");
        }else if(count < 9){
            confirm.setText("确定" +"("+count+"/9)");
        }else{
            confirm.setText("确定(9)");
        }
    }
    /**
     * 显示状态栏等
     */
    public void showBars() {
        isShowBar = true;
        setStateBarVisible(true);
        topBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(topBar,"translationY",topBar.getTranslationY(),0).setDuration(400);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        setStateBarVisible(true);
                        topBar.setVisibility(View.VISIBLE);
                    }
                });
                animator.start();
            }
        },400);
        ObjectAnimator.ofFloat(bottomBar, "translationY", bottomBar.getTranslationY(), 0)
                .setDuration(300).start();
    };

    /**
     * 隐藏状态栏等
     */
    public void hideBars(){
        isShowBar = false;
        ObjectAnimator animator = ObjectAnimator.ofFloat(topBar,"translationY",topBar.getTranslationY(),0).setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setStateBarVisible(false);
                topBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        topBar.setVisibility(View.GONE);
                    }
                },3);
            }
        });
        animator.start();
        ObjectAnimator.ofFloat(bottomBar,"translationY",bottomBar.getHeight()).setDuration(300).start();

    };

    private void setStateBarVisible(boolean show){
        if(show)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        else
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void unSelectedImage(){
        Drawable drawable = getResources().getDrawable(R.drawable.icon_image_un_select);
        drawable.setBounds(0, 0, 52, 52);    //TODO   测试大小。
        choice.setCompoundDrawables(drawable, null, null, null);   //设置未选择时的图片显示的大小
    }
}
