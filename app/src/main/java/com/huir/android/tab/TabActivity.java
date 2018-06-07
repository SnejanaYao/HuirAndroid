package com.huir.android.tab;

import java.util.ArrayList;
import java.util.List;

import com.huir.test.R;
import com.huir.android.chat.ChatActivity;
import com.huir.android.entity.UserShow;
import com.huir.android.tab.ExitDialogManager.CallBackClickListener;
import com.huir.android.tab.app.AppEmailActivity;
import com.huir.android.tab.app.AppFindActivity;
import com.huir.android.tab.app.AppMessageActivity;
import com.huir.android.tab.setting.SetCollectActivity;
import com.huir.android.tab.setting.SetNotifyActivity;
import com.huir.android.tab.setting.SettingActivity;
import com.huir.android.tool.KeyboardUtil;
import com.huir.android.tool.Tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TabActivity extends Activity implements OnClickListener,OnItemClickListener,OnChildClickListener,CallBackClickListener{
	private ViewPager viewPager; //viewPager
	private List<View> vlist = new ArrayList<View>(); //页面的集合
	private List<UserShow> ulist = new ArrayList<UserShow>();
	private ExpandableListView dpList;
	
	private ShowUsersAdapater sAdapater;//message页面listview
	private DeExpandableAdapater deAdapater;
	
	private ExitDialogManager dialogManager;
	
	
	private ListView listChat;
	private PagerAdapter pageAdapter; //pager适配器
	private LinearLayout messageTab;
	private LinearLayout friendTab;
	private LinearLayout appTab;
	private LinearLayout settingsTab;
	
	private ImageButton chatImage;
	private ImageButton friImage;
	private ImageButton appImage;
	private ImageButton setImage;
	
	private ProgressDialog initDialog;
	private ProgressDialog checkDialog;
	
	private static final int MESSAGE_MINE_EMAIL=0;
	private static final int MESSAGE_MINE_MESSAGE=1;
	private static final int MESSAGE_MINE_FIND=2;
	private static final int MESSAGE_SET_CHECK=3;
	private static final int MESSAGE_SET_NOTIFY=4;
	private static final int MESSAGE_SET_COLLECT=5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);   //透明导航栏
        }
		View contentView = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		setContentView(contentView);
		new KeyboardUtil(this, contentView); 
		initView();
		initEvents();
	}
	
	private void initEvents() {
		/*导航栏点击事件**/
		messageTab.setOnClickListener(this);
		friendTab.setOnClickListener(this);
		appTab.setOnClickListener(this);
		settingsTab.setOnClickListener(this);
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				int pageSize = viewPager.getCurrentItem();
				resetImage();
				switch (pageSize) {
				case 0:
					chatImage.setImageResource(R.drawable.chat_iamge_press);
					break;
				case 1:
					friImage.setImageResource(R.drawable.friend_iamge_press);
					break;
				case 2:
					appImage.setImageResource(R.drawable.app_iamge_press);
					break;
				case 3:
					setImage.setImageResource(R.drawable.settings_image_press);
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}

	/**
	 * 初始化控件
	 */
	public void initView() {
        viewPager =(ViewPager) findViewById(R.id.tab_view_pager);

        messageTab = (LinearLayout) findViewById(R.id.navigation_chat);
        friendTab = (LinearLayout) findViewById(R.id.navigation_friend);
        appTab = (LinearLayout) findViewById(R.id.navigation_app);
        settingsTab= (LinearLayout) findViewById(R.id.navigation_settings);

        chatImage =  (ImageButton) findViewById(R.id.tab_navigation_chat);
        friImage = (ImageButton) findViewById(R.id.tab_navigation_friend);
        appImage =(ImageButton) findViewById(R.id.tab_navigation_app);
        setImage = (ImageButton) findViewById(R.id.tab_navigation_settings);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		/**ViewPager的页面切换**/
		View message = inflater.inflate(R.layout.tab_message, null);
		View friend = inflater.inflate(R.layout.tab_friend, null);
		View app = inflater.inflate(R.layout.tab_app, null);
		View settings = inflater.inflate(R.layout.tab_settings, null);
		vlist.add(message);
		vlist.add(friend);
		vlist.add(app);
		vlist.add(settings);
		
		/**页面适配器**/
		pageAdapter = new PagerAdapter() {
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(vlist.get(position));
			}
		
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = vlist.get(position);
				container.addView(view);
				return view;
			}
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				return vlist.size();
			}
		};
		
		viewPager.setAdapter(pageAdapter);
		
		messageInitAdapert(message);
		
		fInitAdapater(friend);
		
		appEvents(app);
		
		settingEvents(settings);
	}
	

	/**
	 * 联系人的列表初始化与装载
	 */
	private void messageInitAdapert(View message) {
		sAdapater = new ShowUsersAdapater(ulist, message.getContext());
		listChat = (ListView) message.findViewById(R.id.user_show_chat_list);
		listChat.setAdapter(sAdapater);
		//TODO 测试联系人列表
		sAdapater.addDataToAdapter(new UserShow(0, 0, "用户名昵称", null, "", "10:01"));
	    listChat.setOnItemClickListener(this);
	}
	
	
	private LinearLayout department;
	private LinearLayout adresslist;
	private LinearLayout group;
	private LinearLayout company;
	private TextView textListName;
	private ImageView item_group_head;
	
	private void fInitAdapater(View friend) {
        department = (LinearLayout)friend.findViewById(R.id.app_top_department);
        adresslist = (LinearLayout)friend.findViewById(R.id.app_top_adresslist);
        group = (LinearLayout)friend.findViewById(R.id.app_top_group);
        company = (LinearLayout)friend.findViewById(R.id.app_top_company);
        textListName = (TextView)friend.findViewById(R.id.text_listname);
        item_group_head = (ImageView) friend.findViewById(R.id.item_group_head);
		
		department.setBackgroundResource(R.drawable.top_line_blue); //(department)初始化界面bottom底色
		
		department.setOnClickListener(this);
		adresslist.setOnClickListener(this);
		group.setOnClickListener(this);
		company.setOnClickListener(this);
		deAdapater = new DeExpandableAdapater(friend.getContext());
		dpList = (ExpandableListView)friend.findViewById(R.id.list_department);
		dpList.setAdapter(deAdapater);
		dpList.setOnChildClickListener(this);
		
	}

	private RelativeLayout myEmailLayout;
	private RelativeLayout myMessageLayout;
	private RelativeLayout findPersonLayout;
	private void appEvents(View app) {
		myEmailLayout = (RelativeLayout)app.findViewById(R.id.myemail_layout);
		myEmailLayout.setOnClickListener(this);
		
		myMessageLayout = (RelativeLayout)app.findViewById(R.id.mymessage_layout);
		myMessageLayout.setOnClickListener(this);
		
		findPersonLayout = (RelativeLayout)app.findViewById(R.id.find_person_layout);
		findPersonLayout.setOnClickListener(this);
	}

	private RelativeLayout userLayout;
	private RelativeLayout versionLayout;
	private RelativeLayout logoutLayout;
	private RelativeLayout safeLayout;
	private RelativeLayout notifyLayout;
	private RelativeLayout collectLayout;
	private void settingEvents(View settings) {
		userLayout = (RelativeLayout)settings.findViewById(R.id.user_layout);
		userLayout.setOnClickListener(this);
		
		versionLayout = (RelativeLayout)settings.findViewById(R.id.version_layout);
		versionLayout.setOnClickListener(this);
		
		notifyLayout = (RelativeLayout)settings.findViewById(R.id.notify_layout);
		notifyLayout.setOnClickListener(this);
		
		collectLayout = (RelativeLayout)settings.findViewById(R.id.collect_layout);
		collectLayout.setOnClickListener(this);
		
		safeLayout =  (RelativeLayout)settings.findViewById(R.id.safe_layout);
		safeLayout.setOnClickListener(this);
		
		logoutLayout= (RelativeLayout)settings.findViewById(R.id.logout_layout);
		logoutLayout.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		resetImage();
		switch (v.getId()) {
		case R.id.navigation_chat:
			viewPager.setCurrentItem(0);
			chatImage.setImageResource(R.drawable.chat_iamge_press);
			break;
		case R.id.navigation_friend:
			viewPager.setCurrentItem(1);
			friImage.setImageResource(R.drawable.friend_iamge_press);
			break;
		case R.id.navigation_app:
			viewPager.setCurrentItem(2);
			appImage.setImageResource(R.drawable.app_iamge_press);
			break;
		case R.id.navigation_settings:
			viewPager.setCurrentItem(3);
			setImage.setImageResource(R.drawable.settings_image_press);
			break;
		case R.id.app_top_department:
			department.setBackgroundResource(R.drawable.top_line_blue);
			adresslist.setBackgroundResource(R.drawable.bottom_top_line1);
			group.setBackgroundResource(R.drawable.bottom_top_line1);
			company.setBackgroundResource(R.drawable.bottom_line1);//设置背景底线变色
			textListName.setText(R.string.str_fri_department);
			break;
		case R.id.app_top_adresslist:
			adresslist.setBackgroundResource(R.drawable.top_line_blue);
			department.setBackgroundResource(R.drawable.bottom_top_line1);
			group.setBackgroundResource(R.drawable.bottom_top_line1);
			company.setBackgroundResource(R.drawable.bottom_line1);//设置背景底线变色
			textListName.setText(R.string.str_fri_addresslist);
			break;
		case R.id.app_top_group:
			group.setBackgroundResource(R.drawable.top_line_blue);
			adresslist.setBackgroundResource(R.drawable.bottom_top_line1);
			department.setBackgroundResource(R.drawable.bottom_top_line1);
			company.setBackgroundResource(R.drawable.bottom_line1);//设置背景底线变色
			textListName.setText(R.string.str_fri_group);
			break;
		case R.id.app_top_company:
			company.setBackgroundResource(R.drawable.top_line_blue);
			group.setBackgroundResource(R.drawable.bottom_top_line1);
			adresslist.setBackgroundResource(R.drawable.bottom_top_line1);
			department.setBackgroundResource(R.drawable.bottom_top_line1); //设置背景底线变色
			textListName.setText(R.string.str_fri_list_company_name);
			break;
		case R.id.myemail_layout:
			initDialog = Tool.showProgressDialog(null,TabActivity.this , "正在加载,请稍候...");
			initDialog.setCancelable(false);
			//TODO 加载完成后 跳转"我的邮件"的界面
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						handler.sendEmptyMessage(MESSAGE_MINE_EMAIL);  
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.mymessage_layout:
			initDialog = Tool.showProgressDialog(null,TabActivity.this , "正在加载,请稍候...");
			initDialog.setCancelable(false);
			//TODO 加载完成后 跳转"我的消息"的界面
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						handler.sendEmptyMessage(MESSAGE_MINE_MESSAGE);  
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.find_person_layout:
			initDialog = Tool.showProgressDialog(null,TabActivity.this , "正在加载,请稍候...");
			initDialog.setCancelable(false);
			//TODO 加载完成后 跳转"找人找群"的界面
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						handler.sendEmptyMessage(MESSAGE_MINE_FIND);  
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.user_layout:
			Intent intent = new Intent();
			intent.setClass(TabActivity.this, SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.version_layout:
			checkDialog = Tool.showProgressDialog(null,TabActivity.this , "正在检查更新");
			checkDialog.setCancelable(false);
			//TODO 检测版本更新  检测完成后 关掉dialog显示检测结果
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						handler.sendEmptyMessage(MESSAGE_SET_CHECK);  
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.notify_layout:
			initDialog = Tool.showProgressDialog(null,TabActivity.this , "正在加载,请稍候...");
			initDialog.setCancelable(false);
			//TODO 加载完成跳转通知提醒界面
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						handler.sendEmptyMessage(MESSAGE_SET_NOTIFY);  
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.collect_layout:
			initDialog = Tool.showProgressDialog(null,TabActivity.this , "正在加载,请稍候...");
			initDialog.setCancelable(false);
			//TODO 加载完成跳转个人收藏界面
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						handler.sendEmptyMessage(MESSAGE_SET_COLLECT);  
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.safe_layout:
			 final EditText inputServer = new EditText(this);
		        inputServer.setFocusable(true);
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle("修改密码").setView(inputServer).setNegativeButton("取消", null);
		        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int which) {
		                        String inputName = inputServer.getText().toString();
		                    }
		                });
		        builder.show();
			break;
		case R.id.logout_layout:
			dialogManager = new ExitDialogManager(TabActivity.this);
			dialogManager.showExitDialog();
			dialogManager.setCallBackClickListener(this);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent();
		intent.setClass(TabActivity.this, ChatActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		Intent intent = new Intent();
		String title = (String) deAdapater.getChild(groupPosition, childPosition);
		intent.putExtra("title", title);
		intent.setClass(TabActivity.this, ChatActivity.class);
		startActivity(intent);
		return true;
	}

	/**
	 * 退出登录Dialog的点击接口回调
	 */
	@Override
	public void click(View view) {
		switch (view.getId()) {
		case R.id.exit_layout:
			Toast.makeText(TabActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
			break;

		case R.id.logoff_layout:
			Toast.makeText(TabActivity.this, "注销登录", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	/**
	 * 导航栏图片切换暗色
	 */
	private void resetImage() {
		chatImage.setImageResource(R.drawable.chat_iamge_normal);
		friImage.setImageResource(R.drawable.friend_iamge_normal);
		appImage.setImageResource(R.drawable.app_iamge_normal);
		setImage.setImageResource(R.drawable.settings_image_normal);
	}


	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_MINE_EMAIL:
				if(initDialog.isShowing()) {
					initDialog.dismiss();
				}  
				startActivity(new Intent(TabActivity.this, AppEmailActivity.class));
				break;
			case MESSAGE_MINE_MESSAGE:
				if(initDialog.isShowing()) { //关闭dialog
					initDialog.dismiss();
				}
				startActivity(new Intent(TabActivity.this, AppMessageActivity.class));
				break;
			case MESSAGE_MINE_FIND:
				if(initDialog.isShowing()) { //关闭dialog
					initDialog.dismiss();
				}
				startActivity(new Intent(TabActivity.this, AppFindActivity.class));
				break;
			case MESSAGE_SET_CHECK:
				if(checkDialog.isShowing()) {
					checkDialog.dismiss();
				}
				Toast.makeText(getApplicationContext(), "当前已经是最新版本", Toast.LENGTH_LONG).show();  
				break;
			case MESSAGE_SET_NOTIFY:
				if(initDialog.isShowing()) { //关闭dialog
					initDialog.dismiss();
				}
				startActivity(new Intent(TabActivity.this, SetNotifyActivity.class));
				break;
			case MESSAGE_SET_COLLECT:
				if(initDialog.isShowing()) { //关闭dialog
					initDialog.dismiss();
				}
				startActivity(new Intent(TabActivity.this, SetCollectActivity.class));
				break;
			}
		}
	};
}
