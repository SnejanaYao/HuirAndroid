package com.huir.android.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.huir.android.chat.PreviewPicturesActivity;
import com.huir.android.entity.Image;
import com.huir.test.R;

import java.io.File;
import java.util.ArrayList;


/**
 * 图片的Adapter
 */
public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.ViewHolder>{
    private static final String TAG = "ImageViewAdapter";

    private static final int TYPE_CAMERA= 1;
    private static final int TYPE_IMAGE = 2;
    private static  ImageViewAdapter imageViewAdapter;

    private int maxCount = 0;
    private Context context;
    private boolean isSelect = false;
    private boolean useCamera;
    private ArrayList<Image> mImages; //所有图片
    private LayoutInflater mInflater;
    private OnImageSelectListener mSelectListener;
    private OnItemClickListener mItemClickListener;
    private OnPreviewPictures previewPictures;
    public ArrayList<Image> mSelectImages = new ArrayList<>();//保存选中的图片

    public ImageViewAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public ImageViewAdapter(){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_IMAGE){
            view  = mInflater.inflate(R.layout.adapter_images_item, parent, false);
            return new ViewHolder(view);
        }
        else {
            view = mInflater.inflate(R.layout.adapter_camera, parent, false);
            return  new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            if(getItemViewType(position)  == TYPE_IMAGE){
                final  Image image = getImage(position);
                Glide.with(context).load(new File(image.getPath()))
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                        .into(holder.ivImage);
                setItemSelect(holder,mSelectImages.contains(image));  //初始化选中状态。 防止选中状态丢失
                holder.ivGIF.setVisibility(image.isGif() ? View.VISIBLE : View.GONE);
                holder.ivSelectIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mSelectImages.contains(image)){
                            maxCount--;
                            isSelect = false;
                            unSelectImage(image); //从数组中移除
                            setItemSelect(holder, isSelect);
                        }else if(maxCount<9){
                            isSelect = true;
                            maxCount++;
                            selectImage(image); //添加到数组中
                            setItemSelect(holder, isSelect);
                        }
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            int p = holder.getAdapterPosition();
                            Log.e(TAG, "onClick: " + p );
                            mItemClickListener.OnItemClick(image, useCamera ? p-1: p);
                        }
                    }
                });
            } else if (getItemViewType(position) == TYPE_CAMERA) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            mItemClickListener.OnCameraClick();
                        }
                    }
                });
            }
    }

    @Override
    public int getItemViewType(int position) {
        if(useCamera && position == 0){
            return  TYPE_CAMERA;
        }else{
            return TYPE_IMAGE;
        }
    }

    @Override
    public int getItemCount() {
        return useCamera ? getImageCount() + 1 : getImageCount();
    }

    private int getImageCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public ArrayList<Image> getData(){
        return mImages;
    }

    private Image getImage(int position){
        return mImages.get(useCamera ? position-1 : position);
    }

    private  void setItemSelect(ViewHolder holder,boolean isSelect){
        if(isSelect){
            holder.ivSelectIcon.setImageResource(R.drawable.icon_image_select);
            holder.ivMasking.setAlpha(0.5f);
        }else{
            holder.ivSelectIcon.setImageResource(R.drawable.icon_image_un_select);
            holder.ivMasking.setAlpha(0.2f);
        }
    }


    /**
     * 选中图片
     *
     * @param image
     */
    private void selectImage(Image image) {
        mSelectImages.add(image);
        Log.e(TAG, "selectImage: " + mSelectImages.size());
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(image, true, mSelectImages.size());
        }
    }

    /**
     * 取消选中图片
     *
     * @param image
     */
    private void unSelectImage(Image image) {
        mSelectImages.remove(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(image, false, mSelectImages.size());
        }
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        public ImageView ivImage;
        public ImageView ivSelectIcon;
        public ImageView ivMasking;
        public ImageView ivGIF;
        public ImageView ivCamera;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivImage = itemView.findViewById(R.id.iv_image);
            this.ivSelectIcon = itemView.findViewById(R.id.iv_select);
            this.ivMasking = itemView.findViewById(R.id.iv_masking);
            this.ivGIF = itemView.findViewById(R.id.iv_gif);
            this.ivCamera = itemView.findViewById(R.id.iv_camera);
        }
    };

    public interface OnImageSelectListener {
        void OnImageSelect(Image image, boolean isSelect, int selectCount);
    }

    public interface OnItemClickListener {
        void OnItemClick(Image image, int position);

        void OnCameraClick();
    }

    public Image getFirstVisibleImage(int firstVisibleItem){
        if(mImages != null){
            if(useCamera){
                return  mImages.get(firstVisibleItem ==0 ? 0:firstVisibleItem-1);
            }else {
                return mImages.get(firstVisibleItem);
            }
        }
        return null;
    }

   /* public Image getFirstVisibleImage(int firstVisibleItem) {
        if (mImages != null) {
            if (useCamera) {
                return mImages.get(firstVisibleItem == 0 ? 0 : firstVisibleItem - 1);
            } else {
                return mImages.get(firstVisibleItem);
            }
        }
        return null;
    }*/

    public void setSelectedImages(ArrayList<String> selected) {
        if (mImages != null && selected != null) {
            for (String path : selected) {
                if (isFull()) {
                    return;
                }
                for (Image image : mImages) {
                    if (path.equals(image.getPath())) {
                        if (!mSelectImages.contains(image)) {
                            mSelectImages.add(image);
                        }
                        break;
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    private void clearImageSelect() {
        if (mImages != null && mSelectImages.size() == 1) {
            int index = mImages.indexOf(mSelectImages.get(0));
            mSelectImages.clear();
            if (index != -1) {
                notifyItemChanged(useCamera ? index + 1 : index);
            }
        }
    }

    private boolean isFull() {
        if (maxCount > 0 && mSelectImages.size() == maxCount) {
            return true;
        } else {
            return false;
        }
    }

    public void refresh(ArrayList<Image> data, boolean useCamera) {
        mImages = data;
        this.useCamera = useCamera;
        notifyDataSetChanged();
    }

    public ArrayList<Image> getSelectImages() {
        return mSelectImages;
    }

    public void setSelectImages(ArrayList<Image> mSelectImages){
        this.mSelectImages = mSelectImages;
    }

    public void setOnImageSelectListener(OnImageSelectListener listener) {
        this.mSelectListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface  OnPreviewPictures{
        void PreviewPictures(View view,int position);
    }

    public void SetOnPreviewPictures(OnPreviewPictures onPreviewPictures){
        if(onPreviewPictures != null){
            this.previewPictures = onPreviewPictures;
        }
    }
}
