package com.huir.android.chat.adapter;

import android.app.Activity;
import android.icu.lang.UCharacter;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huir.android.chat.PreviewPicturesActivity;
import com.huir.android.entity.Image;

import java.io.File;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 预览图片的PageViewAdapter
 */
public class PreviewPageViewAdapter extends PagerAdapter {
    private static final  String TAG = "PreviewPageViewAdapter";
    private Image image;
    private Activity activity;
    private ArrayList<Image> imageList;

    private PhotoView photoView;
    private PreviewOnItemClickListener previewOnItemClickListener;




    public PreviewPageViewAdapter(ArrayList<Image> imageList, Activity activity) {
        this.imageList = imageList;
        this.activity = activity;
    }



    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        image =(Image) imageList.get(position);
        String path = image.getPath();
        photoView = new PhotoView(activity);
        setImage(path,photoView);
        viewGroup.addView(photoView);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                    previewOnItemClickListener.onClick(position,image);
               }
            }
        );
        return photoView;
    }


    /**
     * 显示图片
     * @param path
     * @param photoViewInstance
     */
    private void setImage(String path, PhotoView photoViewInstance){
        Glide.with(photoViewInstance)
                .load(new File(path))
                .into(photoViewInstance);
    }

    @Override
    public int getCount() {
        return imageList!= null ? imageList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position,  Object object) {
        view.removeView((PhotoView)object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface  PreviewOnItemClickListener{
        void onClick(int position,Image image);
    }

    public void setPreviewOnItemClickListener(PreviewOnItemClickListener previewOnItemClickListener){
        if(previewOnItemClickListener != null){
            this.previewOnItemClickListener = previewOnItemClickListener;
        }
    }
}
