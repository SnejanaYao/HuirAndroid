package com.huir.android.chat;

import com.huir.android.chat.adapter.ChatViewAdapter;
import com.huir.android.chat.record.MediaManager;
import com.huir.android.entity.Image;
import com.huir.android.tool.CommonsUtils;
import com.huir.test.R;
import com.huir.android.chat.download.DownloadFile;
import com.huir.android.chat.keyboard.ChatEditKeyboard;
import com.huir.android.entity.Msg;
import com.huir.android.chat.record.AudioRecorderButton;
import com.huir.android.chat.record.AudioRecorderButton.OnAudioListener;
import com.huir.android.tool.KeyboardUtil;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.huir.android.chat.adapter.ChatViewAdapter.PhotoViewCallBackClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ChatActivity extends Activity implements OnClickListener,PhotoViewCallBackClickListener,AdapterView.OnItemClickListener{
    private static final  String TAG = "ChatActivity";
    private Context context = ChatActivity.this;
    private boolean bMute = true;
	private String picUrl ="http://img01.mifile.cn/images/accs/xmjsb_11.jpg";
    public static  MediaManager mediaManager = MediaManager.getInstance();
    private static  AnimationDrawable animation; //动画
    private Msg msg;
	private ChatViewAdapter chatViewAdapter;
	private DownloadFile  downloadFile;
	private AudioRecorderButton record;
	private ArrayList<String> slist = new ArrayList<>();

	private EditText et_meg;
	private Button left,right,download,info,returnBtn;
	private TextView title;
	private ImageView image_show ;
    private int pos;

	private GradientDrawable gd;
	private ListView clist;
    private ProgressDialog dialog;
    
    private int strokeColor = Color.parseColor("#6495ED"); //按下按钮时的边框色
    private int fillColor = Color.parseColor("#6495ED"); //按下按钮时的背景色
    private int unStrokeColor =  Color.parseColor("#B0C4DE");//默认状态下的边框色
    private int unFillColor =  Color.parseColor("#B0C4DE");//默认状态下的背景色
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        config();
		View contentView = getLayoutInflater().inflate(R.layout.activity_chat, null);
		setContentView(contentView);
		new KeyboardUtil(this, contentView);
        initView();
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK){
	        CommonsUtils.stopMediaPlayer();//当按返回键时关闭正在播放的声音
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
	 * 初始化控件
	 */
	private void initView() {
		Intent intent  = getIntent();
		String str_title  = intent.getStringExtra("title");
		
		if(str_title !=null) {
			title = (TextView)findViewById(R.id.chat_text_title);
			title.setText(str_title);
		}

		et_meg = (EditText) findViewById(R.id.et_meg);
		et_meg.addTextChangedListener(new changeBtn());
		
		left=(Button) findViewById(R.id.btn_left);
		if(left !=null){
            left.setOnClickListener(this);
        }
		
		right= (Button) findViewById(R.id.btn_right);
		right.setOnClickListener(this);
		
		download= (Button) findViewById(R.id.btn_download);
		download.setOnClickListener(this);
		
		info= (Button) findViewById(R.id.btn_chat_info);
		info.setOnClickListener(this);
		
		returnBtn= (Button) findViewById(R.id.btn_chat_return);
		returnBtn.setOnClickListener(this);
		
		clist = (ListView) findViewById(R.id.chatList);
		chatViewAdapter = new ChatViewAdapter(context);
		clist.setAdapter(chatViewAdapter);
        clist.setOnItemClickListener(this);

		record = (AudioRecorderButton)findViewById(R.id.btn_record);
		record.setOnAudioListener(new OnAudioListener(){ //设置录音监听
			@Override
			public void onFinish(float second, String filePath) {
				//TODO 测试
				chatViewAdapter.addDataToAdapter(new Msg("", 3,"",filePath,(int)second,false,false));
				chatViewAdapter.notifyDataSetChanged();
			    clist.smoothScrollToPosition(clist.getCount() - 1);

			}
		});
		et_meg.setOnKeyListener(new ChatEditKeyboard(chatViewAdapter, clist, et_meg));
	}



	public void config(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏
        if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
        }
}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String msg = et_meg.getText().toString().trim();
		switch (v.getId()) {
		case R.id.btn_left:
			if(!msg.isEmpty()) {
                chatViewAdapter.addDataToAdapter(new Msg(msg, 1));
				chatViewAdapter.notifyDataSetChanged();
			    clist.smoothScrollToPosition(clist.getCount() - 1);
			    et_meg.setText("");

			    if(et_meg.getText().toString().equals("")) {
			    	 gd = new GradientDrawable();//创建drawable
					 gd.setColor(unFillColor);
					 gd.setCornerRadius(50);
					 gd.setStroke(2, unStrokeColor);
					 left.setBackgroundDrawable(gd);
			    }
			}else{
                startActivityForResult(new Intent(context,SelectorViewActivity.class),2000);
            }
			break;
		case R.id.btn_right:
		  if(!msg.isEmpty()) {
		    chatViewAdapter.addDataToAdapter(new Msg(msg, 2));
			chatViewAdapter.notifyDataSetChanged();
		    clist.smoothScrollToPosition(clist.getCount() - 1);
		    et_meg.setText("");
		    if(et_meg.getText().toString().equals("")) {
		    	gd= new GradientDrawable();//创建drawable
		    	gd.setColor(unFillColor);
		    	gd.setCornerRadius(50);
		    	gd.setStroke(2, unStrokeColor);
			    right.setBackgroundDrawable(gd);
		    }
		  }else{
              //视频
              //startActivityForResult(intentPic,3);
              break;
          }
			break;
		case R.id.btn_download:
			dialog = new ProgressDialog(context);
			dialog.setMessage("正在下载...");
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			downloadFile = new DownloadFile(dialog,context);
			downloadFile.execute("http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk");
			break;
		case R.id.btn_chat_info:
			Intent intent = new Intent();
			intent.setClass(context, GroupChatActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_chat_return:
		    CommonsUtils.stopMediaPlayer();
			ChatActivity.this.finish();
			break;
		}
	}


    @Override
    public void click(View view, String path) {
        switch (view.getId()){
            case R.id.chat_image_show:
                Log.e(TAG,"进入");
                Log.e(TAG, "click:  path "  +  path);
               if(path != null){
                    Log.e(TAG,"path !=null");
                    Intent intent = new Intent();
                    intent.setClass(context,PhotoViewActivity.class);
                    intent.putExtra("path",path);
                    startActivity(intent);
                }else{
                    Log.e(TAG,"path==null");
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout leftViceLayout = view.findViewById(R.id.chat_left_vc);
        View singer = leftViceLayout.findViewById(R.id.chat_left_singer);
        View red = leftViceLayout.findViewById(R.id.chat_left_red);
        View chat_left_voiceLine = leftViceLayout.findViewById(R.id.chat_left_voiceLine);
        msg = (Msg)parent.getItemAtPosition(position);
        switch (msg.getType()){
            case 3:
                bMute = true;
                CommonsUtils.muteAudioFocus(context,bMute);
                VoiceChat(red,singer,position);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: is in  " );
        if(data != null){
            if(requestCode == 2000){
                boolean sendPhoto = data.getBooleanExtra("sendPhoto", false);
                ArrayList<Image> images = data.getParcelableArrayListExtra("resultImages");
                if(sendPhoto){
                    if(images != null && images .size()>0){
                        Log.e(TAG, "onActivityResult:  out of case 2's  if     sendPhoto  is "   +  sendPhoto);
                        for(Image image: images){
                            chatViewAdapter.addDataToAdapter(new Msg(null, image.getPath(), 4) );
                            chatViewAdapter.notifyDataSetChanged();
                            clist.smoothScrollToPosition(clist.getCount() - 1);
                            et_meg.setText("");
                            chatViewAdapter.setCallBackClickListener(this);
                        }
                    }
                }else return;
            }else {
                Log.e(TAG, "onActivityResult:  don't send the list" );
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        Log.e(TAG, "onResume: "     );
        super.onResume();
    }

    public static AnimationDrawable getAnimation() {
        return animation;
    }

    private void VoiceChat(View red,View singer,int position ){
        red.setVisibility(View.GONE);  //被点击则红点消失
        chatViewAdapter.notifyDataSetInvalidated();
        msg.setPlayed(true);
        if(pos == position) {
            if(msg.isPlaying()) {
                bMute = false;
                CommonsUtils.muteAudioFocus(context,bMute);
                msg.setPlaying(false);
                mediaManager.release();
                animation.stop();
                animation.selectDrawable(0);//reset
                return;
            }else {
                bMute = true;
                CommonsUtils.muteAudioFocus(context,bMute);
                msg.setPlaying(true);
            }
            bMute = true;
            CommonsUtils.muteAudioFocus(context,bMute);
            msg.setPlaying(false);
        }
        if(animation != null) {
            animation.selectDrawable(0);//显示动画第一帧
            animation.stop();
        }
        animation = (AnimationDrawable)singer.getBackground();  //播放动画
        animation.start();
        pos = position;
        msg.setPlaying(true);
        //播放前重置。
        mediaManager.release();
        mediaManager.playSound(msg.getPath(),new MediaPlayer.OnCompletionListener() {//播放不同的地址音频
            @Override
            public void onCompletion(MediaPlayer mp) {
                bMute = false;
                CommonsUtils.muteAudioFocus(context,bMute);
                msg.setPlaying(false);
                animation.selectDrawable(0);//显示动画第一帧
                animation.stop();
                pos = -1;//播放完毕，当前播放索引置为-1。
            }
        });
    }

    /**
     * 当输入文字时 改变按钮颜色
     * @author huir316
     *
     */
    class changeBtn implements TextWatcher{
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            left=(Button) findViewById(R.id.btn_left);
            right=(Button) findViewById(R.id.btn_right);

            gd = new GradientDrawable();//创建drawable
            gd.setColor(fillColor);
            gd.setCornerRadius(50);
            gd.setStroke(2,strokeColor);
            left.setBackgroundDrawable(gd);
            right.setBackgroundDrawable(gd);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}




