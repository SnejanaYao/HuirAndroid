package com.huir.android.chat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huir.android.chat.adapter.FolderAdapter;
import com.huir.android.chat.adapter.ImageViewAdapter;
import com.huir.android.chat.image.ImageModle;
import com.huir.android.entity.Folder;
import com.huir.android.entity.Image;
import com.huir.android.tool.KeyboardUtil;
import com.huir.test.R;

import java.util.ArrayList;


public class SelectorViewActivity extends Activity implements OnClickListener{
    private static final String TAG = "SelectorViewActivity";

    private Folder mFolder;
    private boolean isOpenFolder;
    private boolean isInitFolder;   //是否初始化文件夹
    private ImageViewAdapter mAdapter;
    private boolean useCamera = true;
    private ArrayList<Folder> mFolders;
    private ArrayList<Image> mImages = new ArrayList<>();
    private boolean applyLoadImage = false;
    private GridLayoutManager mLayoutManager;


    private Intent intent;
    private Bundle bundle;

    private static SelectorViewActivity selectorViewActivity;


    public static  SelectorViewActivity getInstance(){
        if(selectorViewActivity == null){
            selectorViewActivity  = new SelectorViewActivity();
        }
        return selectorViewActivity;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏
		super.onCreate(savedInstanceState);
		config();
		View contentView = getLayoutInflater().inflate(R.layout.activity_image_selector, null);
        setContentView(contentView);
		new KeyboardUtil(this, contentView);
		initView();
		hideFolderList();
		initImageList();
        getImage();

	}

    @Override
    public void finish() {

        super.finish();
    }

    /**
     *Activity窗体基本配置
     */
	private  void  config(){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#373c3d"));
    }

    private View masking;
    private TextView confirmText;
	private TextView timeText;
	private TextView folderText;
	private TextView previewText;
    private RecyclerView imageView;
    private RecyclerView folderView;
    private FrameLayout sureFrameLayout;
	private FrameLayout backFrameLayout;
	private FrameLayout folderFrameLayout;
	private FrameLayout previewFrameLayout;
	/**
	 * 初始化控件
	 */
	private void initView() {
        confirmText = (TextView) findViewById(R.id.selector_confirm);

        previewText  = (TextView) findViewById(R.id.selector_tv_preview);

        folderText = (TextView) findViewById(R.id.selector_folder_name);

        timeText = (TextView) findViewById(R.id.selector_image_time);

        imageView = (RecyclerView) findViewById(R.id.selector_image_view);
        imageView.scrollToPosition(3);
        imageView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        folderView = (RecyclerView) findViewById(R.id.selector_rv_folder);
        folderView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        backFrameLayout = (FrameLayout) findViewById(R.id.selector_btn_back);

        previewFrameLayout = (FrameLayout) findViewById(R.id.selector_btn_preview);
        previewFrameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sureFrameLayout = (FrameLayout) findViewById(R.id.selector_sure);    //图片选择完毕
        sureFrameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        folderFrameLayout = (FrameLayout) findViewById(R.id.selector_btn_folder);
        folderFrameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInitFolder){   //已经加载并打开文件夹列表   关闭它
                    if(isOpenFolder){
                        closeFolder();
                    }else{
                        openFolder();   //开启文件夹列表
                    }
                }
            }
        });

        masking = (View) findViewById(R.id.masking);
        masking.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFolder();
            }
        });

	}

	private void initImageList(){
        Configuration configuration = getResources().getConfiguration();
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            mLayoutManager = new GridLayoutManager(this,3);
        }else mLayoutManager = new GridLayoutManager(this,5);

        imageView.setLayoutManager(mLayoutManager);
        mAdapter = new ImageViewAdapter(this);
        imageView.setAdapter(mAdapter);
        if(mFolders != null && !mFolders.isEmpty()){
            setFolder(mFolders.get(0));
        }
       mAdapter.setOnImageSelectListener(new ImageViewAdapter.OnImageSelectListener() {
            @Override
            public void OnImageSelect(Image image, boolean isSelect, int selectCount) {
                setSelectImageCount(selectCount);
            }
        });
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new ImageViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Image image, int position) {
                Log.e(TAG, "OnItemClick: "+ image.toString() );
                ArrayList<Image> selectImages = mAdapter.getSelectImages();   // 选择要发送出去的图片数组
                Log.e(TAG, "OnItemClick:   size  is   "  + selectImages.size() );
                intent = new Intent();
                bundle = new Bundle();
                getInstance().setImages(mImages);   // 设置new过后的mImages 有值
                //bundle.putParcelableArrayList("mImages",mImages);
                bundle.putParcelableArrayList("selectImages",selectImages);
                bundle.putInt("position",position);
                String name = mImages.get(position).getName();
                String path = mImages.get(position).getPath();
                Log.e(TAG, "OnItemClick:  "+name +" /n   "  + path   + "      position   " + position ); // TODO    position , imageList,
                intent.setClass(getApplicationContext(),PreviewPicturesActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,10000);
            }

            @Override
            public void OnCameraClick() {

            }
        });
    }

    /**
     * 初始化图片文件夹列表
     */
    private void initFolderList() {
        if (mFolders != null && !mFolders.isEmpty()) {
            isInitFolder = true;
            folderView.setLayoutManager(new LinearLayoutManager(SelectorViewActivity.this));
            FolderAdapter  folderAdapter = new FolderAdapter(this,mFolders);
            folderAdapter.OnFolderSelectListener(new FolderAdapter.OnFolderSelectListener() {
                @Override
                public void OnFolderSelect(Folder folder) {
                    setFolder(folder);
                    closeFolder(); //关闭文件列表
                }
            });
            folderView.setAdapter(folderAdapter);
        }
    }

    /**
     * 弹起文件夹列表
     */
    public void openFolder(){
        if(!isOpenFolder){
            masking.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(folderView, "translationY", folderView.getHeight(), 0).setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    folderView.setVisibility(View.VISIBLE);
                }
            });
            animator.start();
            isOpenFolder= true;
        }

    }

    /**
     * 收起文件夹列表
     */
    public void closeFolder(){
        if(isOpenFolder){
            masking.setVisibility(View.GONE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(folderView, "translationY",
                    0, folderView.getHeight()).setDuration(330);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    folderView.setVisibility(View.GONE);
                }
            });
            animator.start();
            isOpenFolder= false;
        }
    }

    /**
     * 初始化时隐藏文件夹列表
     */
    public void hideFolderList(){
        folderView.post(new Runnable() {
            @Override
            public void run() {
                folderView.setTranslationY(folderView.getHeight());
                folderView.setVisibility(View.GONE);
            }
        });
    }

    private void getImage(){
        if(!applyLoadImage){   //TODO  应该为  if(applyLoadImage)
            applyLoadImage = false;
            checkPermissionAndLoadImages();
        }
    }


    private  void setSelectImageCount(int count){
        //TODO 预览 确定 文字的 显示改变
        if(count ==0){
            confirmText.setText("确定");
            previewText.setText("预览");
        }else if(count < 9){
            confirmText.setText("确定" +"("+count+"/9)");
            previewText.setText("预览"+"("+count+")");
        }else{
            confirmText.setText("确定(9)");
            previewText.setText("预览(9)");
        }
    }

    private void checkPermissionAndLoadImages(){
        Log.e(TAG, "checkPermissionAndLoadImages:      进入checkPermissionAndLoadImages "   );
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this, "没有图片", Toast.LENGTH_LONG).show();
            return;
        }
        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(getApplication(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED){
            loadImageForSDCard();
        }
    };

    private void loadImageForSDCard(){
        ImageModle.loadImageForSDCard(this, new ImageModle.DataCallback() {
            @Override
            public void onSuccess(ArrayList<Folder> folders) {
                    mFolders = folders;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mFolders != null && !mFolders.isEmpty()) {
                                initFolderList();
                                mFolders.get(0).setUseCamera(useCamera);
                                mImages = mFolders.get(0).getImages();
                                setFolder(mFolders.get(0));  //文件夹显示
                            }
                        }
                    });
                }
        });
    }

    public void chakanfolder(){
        for (int i=0;i<mFolders.size();i++){
            Log.e(TAG, i + "文件名   " + mFolders.get(i).getName() );
        }
    }


    /**
     * 设置选中的文件夹，同时刷新图片列表
     *
     * @param folder
     */
    private void setFolder(Folder folder) {
        if (folder != null && mAdapter != null && !folder.equals(mFolder)) {
            mFolder = folder;
            folderText.setText(folder.getName());
            imageView.scrollToPosition(0);
            mImages= folder.getImages();
            mAdapter.refresh(mImages, folder.isUseCamera());
        }
    }

    public ArrayList<Image> getImages() {
        return mImages;
    }

    public void  setImages(ArrayList<Image> images){
        this.mImages = images;
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*if(!applyLoadImage){   //TODO   if(applyLoadImage)
            applyLoadImage = false;
            checkPermissionAndLoadImages();//TODO 内存消耗 相对 OnCreate来说 比较小  但是 每次返回此界面都会重新加载一边造成图片滑动无法定位。
        }*/
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume: " );
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 10000) {
            mAdapter.notifyDataSetChanged();  // 刷新全部可见的item
            ArrayList<Image> images = data.getParcelableArrayListExtra("previewSelectedImages");
            setSelectImageCount(images.size());
            mAdapter.setSelectImages(images);
            if(images != null){
                Log.e(TAG, "onActivityResult:   list   " + images.size() );
            }else {
                Log.e(TAG, "onActivityResult:   list  is  null  " );
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


