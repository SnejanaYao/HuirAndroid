package com.huir.android.chat.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.huir.android.entity.Folder;
import com.huir.android.entity.Image;
import com.huir.test.R;

import java.io.File;
import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private int mSelectItem;
    private ArrayList<Folder> mFolders;
    private OnFolderSelectListener mListener;


    public FolderAdapter(Context context, ArrayList<Folder> mFolders) {
        this.context = context;
        this.mFolders = mFolders;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final  Folder folder = mFolders.get(position);
        ArrayList<Image> images = folder.getImages();
        holder.folderName.setText(folder.getName());
        holder.ivSelect.setVisibility(mSelectItem == position ? View.VISIBLE: View.GONE);
        if(images !=null && !images.isEmpty() ){
            holder.folderSIze.setText(images.size() + "张");
            Glide.with(context).load(new File(images.get(0).getPath())).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(holder.ivImage);
        }else{
            holder.folderSIze.setText("0张");
            holder.ivImage.setImageBitmap(null);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectItem  = holder.getAdapterPosition();
                notifyDataSetChanged();
                if(mListener !=null){
                    mListener.OnFolderSelect(folder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFolders == null ? 0: mFolders.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder{
        public ImageView ivImage;
        public ImageView ivSelect;
        public TextView folderName;
        public TextView folderSIze;
        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.folder_iv_image);
            ivSelect = (ImageView) itemView.findViewById(R.id.folder_iv_select);
            folderName = (TextView) itemView.findViewById(R.id.tv_folder_name);
            folderSIze = (TextView) itemView.findViewById(R.id.tv_folder_size);
        }
    };

    public  interface OnFolderSelectListener {
        void  OnFolderSelect(Folder folder);
    }

    public void OnFolderSelectListener(OnFolderSelectListener listener ){
        if(listener != null){
                this.mListener = listener;
        }
    }
}
