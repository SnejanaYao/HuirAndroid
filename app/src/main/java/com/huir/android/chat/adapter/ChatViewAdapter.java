package com.huir.android.chat.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.huir.android.chat.download.ImageAsy;

import android.util.Log;
import android.view.View.OnClickListener;
import com.huir.test.R;
import com.huir.android.entity.Msg;
import com.huir.android.tool.CommonsUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ChatViewAdapter extends BaseAdapter {
    private static final String  TAG = "ChatViewAdapter";
    private PhotoViewCallBackClickListener photoViewCallBackClickListener;
    private ImageAsy imageAsy ;

	private static ViewHolder viewHolder;
	private List<Msg> datas = new ArrayList<>();
	private Context context;

	private String path;

    public ChatViewAdapter(Context context) {
        super();
        this.context = context;
    }

	//给adapter添加数据
	public void addDataToAdapter(Msg e) {
	        datas.add(e);
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
        Log.e(TAG, "getView: " + position );
		if(convertView ==null) {
            convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_chat,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
        Msg entity = datas.get(position);
		int type= entity.getType();
		String msg =  entity.getMsg();
		switch (type){
            case 1:
                l_talk(msg);
                break;
            case 2:
                r_talk(msg);
                break;
            case 3:
                int second = entity.getSecond();
                boolean isPlayed= entity.isPlayed();
                l_voice(second,isPlayed);
                RelativeLayout.LayoutParams ps = (RelativeLayout.LayoutParams)viewHolder.chat_left_voiceLine.getLayoutParams();
                ps.width = CommonsUtils.getVoiceLineWight(context, second);
                viewHolder.chat_left_voiceLine.setLayoutParams(ps);
                break;
            case 4:

                showImage(entity,position);
                 /*
                    imageAsy = new ImageAsy(context,viewHolder.image_show,datas);
                    imageAsy= new ImageAsy(context,viewHolder.image_show, getMsg.getPath());
                    imageAsy.setBitMapListener(this);
                    imageAsy.execute();*/
                break;
        }
		return convertView;
	}
 public class ViewHolder {
       public View rootView;
       public TextView text_left;
       public LinearLayout left; //左侧文字发送

       public TextView text_right;
       public LinearLayout right; //右侧文字发送

       public TextView vc_left;  //语音秒数
       public ImageView chat_left_red; //小红点
       public ImageView chat_left_voiceLine; //语音条
       public LinearLayout voiceLeft; //左侧语音发送
       public LinearLayout sign; //语音标志

      public LinearLayout imageLayout;
      public ImageView image_show;

       public ViewHolder(View rootView) {
       	    this.rootView = rootView;
            this.text_left = (TextView) rootView.findViewById(R.id.text_left);
            this.left = (LinearLayout)rootView.findViewById(R.id.chat_left);
            
            this.text_right = (TextView)rootView.findViewById(R.id.text_right);
            this.right = (LinearLayout)rootView.findViewById(R.id.chat_right);
            
            this.voiceLeft = (LinearLayout)rootView.findViewById(R.id.chat_left_vc);
            this.vc_left = (TextView)rootView.findViewById(R.id.chat_left_voicetime);
            this.chat_left_red=(ImageView) rootView.findViewById(R.id.chat_left_red);
            this.chat_left_voiceLine =(ImageView) rootView.findViewById(R.id.chat_left_voiceLine);
            this.sign = (LinearLayout)rootView.findViewById(R.id.chat_left_singer);

            this.imageLayout = (LinearLayout)rootView.findViewById(R.id.image_left_layout);
            this.image_show = (ImageView) rootView.findViewById(R.id.chat_image_show);
       }
 }

    private void l_talk(String msg) {
        viewHolder.left.setVisibility(View.VISIBLE);
        viewHolder.right.setVisibility(View.INVISIBLE);
        viewHolder.voiceLeft.setVisibility(View.INVISIBLE);
        viewHolder.imageLayout.setVisibility(View.INVISIBLE);
        viewHolder.text_left.setText(msg);//发送消息
    }

    private void r_talk(String msg) {
        viewHolder.right.setVisibility(View.VISIBLE);
        viewHolder.left.setVisibility(View.INVISIBLE);
        viewHolder.voiceLeft.setVisibility(View.INVISIBLE);
        viewHolder.imageLayout.setVisibility(View.INVISIBLE);

        viewHolder.text_right.setText(msg); //发送消息
    }

    private void l_voice(int second,boolean isPlayed) {
        viewHolder.voiceLeft.setVisibility(View.VISIBLE);
        viewHolder.left.setVisibility(View.INVISIBLE);
        viewHolder.right.setVisibility(View.INVISIBLE);
        viewHolder.imageLayout.setVisibility(View.INVISIBLE);
        viewHolder.vc_left.setText(second<=0 ? 1 +" ''" : second+" '' ");
        if(isPlayed) { //未读与已读的红点状态
            viewHolder.chat_left_red.setVisibility(View.GONE);
        }else {
            viewHolder.chat_left_red.setVisibility(View.VISIBLE);
        }
    }

    private void l_image(){
        viewHolder.imageLayout.setVisibility(View.VISIBLE);
        viewHolder.voiceLeft.setVisibility(View.INVISIBLE);
        viewHolder.left.setVisibility(View.INVISIBLE);
        viewHolder.right.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示已经选中的图片
     * @param entity   图片路径
     * @param position  当前下标
     */
    private void showImage(Msg entity,int position){
            Log.e(TAG, "getView:  !=  null" +new File(entity.getPath()).exists() );
            l_image();
            try{
                Glide.with(viewHolder.image_show)
                        .load(new File(entity.getPath()))
                        .into(viewHolder.image_show);
            }catch(Exception e){
                Log.e(TAG, "showImage: "  + e.getStackTrace() );
            }
            viewHolder.image_show.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(photoViewCallBackClickListener !=null){
                        photoViewCallBackClickListener.click(v,datas.get(position).getPath());
                    }
                }
            });
    }

    /**
     * 接口回调
     * @author huir316
     *
     */
    public interface PhotoViewCallBackClickListener {
        //void click(View view,int position,List<Msg> mList);
        void click(View view,String  path);
    };

    public void setCallBackClickListener(PhotoViewCallBackClickListener photoViewCallBackClickListener) {
        if(photoViewCallBackClickListener !=null) {
            this.photoViewCallBackClickListener = photoViewCallBackClickListener;
        }
    }
}




