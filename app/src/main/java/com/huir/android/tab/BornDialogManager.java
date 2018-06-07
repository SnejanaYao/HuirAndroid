package com.huir.android.tab;

import com.huir.test.R;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

/**
 * 退出登录模块的Dialog显示
 * @author huir316
 *
 */
public class BornDialogManager implements OnClickListener{
	private SpinnerCallBackClickListener spinnerBackClickListener;
	private ArrayAdapter<CharSequence> adapter;
	
	private boolean isLeapYear;
	
	private View view;
	private Context context;
	private Dialog dialog;
	
	private RelativeLayout yr;
	private RelativeLayout mth;
	private RelativeLayout day;
	
	private Spinner yrSp;
	private Spinner mthSp;
	private Spinner daySp;
	
	private Button sureBtn;
	private Button cancleBtn;
	
	private Integer intYrS;
	private Integer intMths;
	private Integer intDays;
	public BornDialogManager(Context context) {
		super();
		this.context = context;
	}

	public void showBornDialog() {
		dialog = new Dialog(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.dialog_born, null);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
        lp.y = 500; // 新位置Y坐标
        dialogWindow.setAttributes(lp);

		yr = (RelativeLayout) dialog.findViewById(R.id.born_yr_layout);
		yr.setOnClickListener(this);
		
		mth=(RelativeLayout)dialog.findViewById(R.id.born_mth_layout);
		mth.setOnClickListener(this);
		
		day=(RelativeLayout)dialog.findViewById(R.id.born_day_layout);
		day.setOnClickListener(this);
		
		yrSp =  (Spinner)dialog.findViewById(R.id.born_yr_spinner);
		mthSp = (Spinner)dialog.findViewById(R.id.born_mth_spinner);
		daySp = (Spinner)dialog.findViewById(R.id.born_day_spinner);
		intYrS = Integer.parseInt((String)yrSp.getSelectedItem());
		intMths = Integer.parseInt((String)mthSp.getSelectedItem());
		intDays = Integer.parseInt((String)daySp.getSelectedItem());
		
		if(((intYrS % 4 == 0 && intYrS % 100 != 0) || (intYrS % 400 == 0))) { //初始化获取闰年
			Log.e("TAG", "闰年 + " +intYrS);
			normalMonthsDay();
			//TODO 29号  闰年
			if(intMths ==2) {
				Log.e("TAG", "2");
				leapYearFeb();
			}
		}else {
			Log.e("TAG", "平年  + " +intYrS);
			normalMonthsDay();
			//TODO 28号  平年
			if(intMths ==2) {
				Log.e("TAG", "2");
				notLeapYearFeb();
			}
		}
		
		selectedListener(); //设置监听事件
		
		sureBtn = (Button) dialog.findViewById(R.id.born_save_btn);
		sureBtn.setOnClickListener(this);
		
		cancleBtn = (Button) dialog.findViewById(R.id.born_cancel_btn);
		cancleBtn.setOnClickListener(this);
		
		dialog.show();
	}
	
	/**
	 * 销毁dialog
	 */
	public void dimissBornDialog() {
		if(dialog!=null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	
	/**
	 * 下拉框选择事件监听
	 */
	public void selectedListener() {
		yrSp.setOnItemSelectedListener(new OnItemSelectedListener() { //年份下拉框的监听
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO 获取年份 判断月份  天数
				String yrs = (String) yrSp.getSelectedItem();
				intYrS = Integer.parseInt(yrs);
				if(intYrS != null) { 
					if(((intYrS % 4 == 0 && intYrS % 100 != 0) || (intYrS % 400 == 0))) { //闰年
						isLeapYear =true;
						Log.e("TAG", "闰年 + " +intYrS);
						if(intMths != null) {
							normalMonthsDay();
							if(intMths ==2) {
								Log.e("TAG", "2");
								leapYearFeb();
							}
						}
					}else {
						isLeapYear =false;
						if(intMths != null) {
							normalMonthsDay();
							if(intMths ==2) {
								Log.e("TAG", "2");
								notLeapYearFeb();
							}
						}
					}
				}
			}
		});
		
		mthSp.setOnItemSelectedListener(new OnItemSelectedListener() {// 月份下拉框的监听
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO 获取月份通过年份 判断 天数
				String mths = (String)mthSp.getSelectedItem();
				intMths = Integer.parseInt(mths);
				/**
				 * 判断是否是闰年
				 */
			    if(isLeapYear) {
			    	Log.e("TAG", "闰年 + " +intYrS);
					normalMonthsDay();
					//TODO 29号  闰年
					if(intMths ==2) {
						Log.e("TAG", "2");
						leapYearFeb();
					}
				}else {
					Log.e("TAG", "平年  + " +intYrS);
					normalMonthsDay();
					//TODO 28号  平年
					if(intMths ==2) {
						Log.e("TAG", "2");
						notLeapYearFeb();
					}
				}
				
			}
		});
		
		daySp.setOnItemSelectedListener(new OnItemSelectedListener() {//天数下拉框的监听
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO 获取下拉框选中的值
				String days = (String) daySp.getSelectedItem();
				intDays = Integer.parseInt(days);
			}
		});
		
	}
	
	
	/**
	 * 接口回调
	 * @author huir316
	 *
	 */
	public interface SpinnerCallBackClickListener {
		void click(View view,Integer yr,Integer mth,Integer day);
	};

	public void setCallBackClickListener(SpinnerCallBackClickListener spinnerBackClickListener) {
		if(spinnerBackClickListener !=null) {
			this.spinnerBackClickListener = spinnerBackClickListener;
		}
	}

	@Override
	public void onClick(View v) {
		if(spinnerBackClickListener != null) {
			spinnerBackClickListener.click(v,this.intYrS,this.intMths,this.intDays);  //传view, intYrS,intMths,intDays 值到接口回调 实现点击事件
		}
	}

	
	/**
	 * 每年不变的月份 日数
	 */
	public void normalMonthsDay() {
		if(intMths == 1 || intMths == 3 || intMths == 5 || intMths == 7 || intMths == 8 || intMths == 10 || intMths == 12) {
			Log.e("TAG", "1 3 5 7 8 10 12");
			//TODO 31号 大月
			 adapter = ArrayAdapter.createFromResource(this.context, R.array.day_thirty_one, android.R.layout.simple_dropdown_item_1line);
			 daySp.setAdapter(adapter);  
		}
		if(intMths == 4 || intMths == 6  || intMths == 9  || intMths == 11) {
			Log.e("TAG", "4 6 9 11");
			//TODO 30号 小月
			adapter = ArrayAdapter.createFromResource(this.context, R.array.day_thirty, android.R.layout.simple_dropdown_item_1line);
			daySp.setAdapter(adapter);
		}
	}
	
	/**
	 * 闰年二月份
	 */
	public void leapYearFeb() {
		adapter = ArrayAdapter.createFromResource(context, R.array.day_twenty_nine, android.R.layout.simple_dropdown_item_1line);
		daySp.setAdapter(adapter);
	}
	
	/**
	 * 平年二月份
	 */
	public void notLeapYearFeb() {
		adapter = ArrayAdapter.createFromResource(context, R.array.day_twenty_eight, android.R.layout.simple_dropdown_item_1line);
		daySp.setAdapter(adapter);
	}
}
