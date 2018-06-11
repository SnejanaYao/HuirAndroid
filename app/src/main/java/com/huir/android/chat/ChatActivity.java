package com.huir.android.chat;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.huir.test.R;
import com.huir.android.chat.download.DownloadFile;
import com.huir.android.chat.keyboard.ChatEditKeyboard;
import com.huir.android.entity.Msg;
import com.huir.android.net.NetService;
import com.huir.android.record.AudioRecorderButton;
import com.huir.android.record.AudioRecorderButton.OnAudioListener;
import com.huir.android.tool.KeyboardUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


@SuppressLint("NewApi")
public class ChatActivity extends Activity implements OnClickListener{
	private String picUrl ="http://img01.mifile.cn/images/accs/xmjsb_11.jpg";  
	private List<Msg> datas = new ArrayList<Msg>();
	private ChatViewAdapter chatViewAdapter;
	private DownloadFile  downloadFile;
	private AudioRecorderButton record;
	
	private EditText et_meg;
	private Button left,right,download,info,returnBtn;
	private TextView title;
	private ImageView image_show ;
	
	private GradientDrawable gd;
	private ListView clist;
    private ProgressDialog dialog;
    
    private int strokeColor = Color.parseColor("#6495ED"); //按下按钮时的边框色
    private int fillColor = Color.parseColor("#6495ED"); //按下按钮时的背景色
    private int unstrokeColor =  Color.parseColor("#B0C4DE");//默认状态下的边框色
    private int unfillColor =  Color.parseColor("#B0C4DE");//默认状态下的背景色
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏  
        if(VERSION.SDK_INT>= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);   //透明导航栏
        }
		View contentView = getLayoutInflater().inflate(R.layout.activity_chat, null);
		setContentView(contentView);
		new KeyboardUtil(this, contentView); 
		initView();
		Intent start = new Intent(ChatActivity.this, NetService.class); //开启服务
		startService(start);
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
		chatViewAdapter = new ChatViewAdapter(datas,ChatActivity.this);
		clist.setAdapter(chatViewAdapter);

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
					 gd.setColor(unfillColor);
					 gd.setCornerRadius(50);
					 gd.setStroke(2, unstrokeColor);
					 left.setBackgroundDrawable(gd);
			    }
			}else{
                chatViewAdapter.addDataToAdapter(new Msg(msg, 4));
                chatViewAdapter.notifyDataSetChanged();
                clist.smoothScrollToPosition(clist.getCount() - 1);
                et_meg.setText("");
                chatViewAdapter.setCallBackClickListener(new ChatViewAdapter.PhotoViewCallBackClickListener() {
                    @Override
                    public void click(View view, PhotoView photoView,ImageView imageView,Bitmap bitmap) {
                        switch (v.getId()){
                           /* case R.id.photo_view_click_show:
                                photoView.setVisibility(View.VISIBLE);
                                PhotoViewAttacher photoViewAttacher =new PhotoViewAttacher(photoView);
                                photoView.setImageBitmap(bitmap);
                                photoViewAttacher.update();
                                break;*/
                        }
                    }
                });
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
		    	gd.setColor(unfillColor);
		    	gd.setCornerRadius(50);
		    	gd.setStroke(2, unstrokeColor);
			    right.setBackgroundDrawable(gd);
		    	}
			}
		    
			break;
		case R.id.btn_download:
			//文件下载  (downLoadManager)
			/*String url= "http://img.zcool.cn/community/018d4e554967920000019ae9df1533.jpg@900w_1l_2o_100sh.jpg";//下载地址
			DownloadManager.Request request= new DownloadManager.Request(Uri.parse(url)); //获取DownloadManager的下载
			request.setTitle("Andorid下载管理");
			// 如果为了让它运行，你必须用  android   3.2编译你的应用程序
			if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
			    request.allowScanningByMediaScanner();//表示允许MediaScanner扫描到这个文件，默认不允许。
			    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			}
			request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS+"/com.android.first/", "test.jpg");
			// 获得下载服务和队列文件
			DownloadManager manager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
			Long id = manager.enqueue(request);
            listener(id);*/
			
			dialog = new ProgressDialog(ChatActivity.this);
			dialog.setMessage("正在下载...");
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			downloadFile = new DownloadFile(dialog,ChatActivity.this);
			downloadFile.execute("http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk");
			break;
		case R.id.btn_chat_info:
			Intent intent = new Intent();
			intent.setClass(ChatActivity.this, GroupChatActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_chat_return:
			ChatActivity.this.finish();
			break;
		}
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
	
	/**
	 * downloadManager 监听事件
	 * @param Id
	 */
    private void listener(final long Id) {  
        // 注册广播监听系统的下载完成事件。  
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);  
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
            @Override  
            public void onReceive(Context context, Intent intent) {  
                long ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);  
                if (ID == Id) {  
                	Toast toast=Toast.makeText(ChatActivity.this,"下载文件目录在: 手机存储/"+Environment.DIRECTORY_DOWNLOADS+"/com.android.first",Toast.LENGTH_SHORT);
                	toast.show();
                }  
            }  
        };  
        registerReceiver(broadcastReceiver, intentFilter);  
    }


}


