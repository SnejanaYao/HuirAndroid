package com.huir.android.tab.setting.dialog;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.huir.test.R;

/**
 * 退所在地模块的Dialog显示
 * @author huir316
 *
 */
public class AreaDialogManager implements OnClickListener{
	private AreaSpinnerCallBackClickListener spinnerBackClickListener;
	private ArrayAdapter<CharSequence> adapter;

	private View view;
	private Context context;
	private Dialog dialog;

    private RelativeLayout country;
	private RelativeLayout provice;
	private RelativeLayout city;


    private Spinner countrySp;
	private Spinner pSp;
	private Spinner cSp;


	private Button sureBtn;
	private Button cancleBtn;

	private String strCountry;
	private String strProvice;
	private String strCity;


	public AreaDialogManager(Context context) {
		super();
		this.context = context;
	}

	public void showAreaDialog() {
		dialog = new Dialog(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.dialog_area, null);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
        lp.y = 500; // 新位置Y坐标
        dialogWindow.setAttributes(lp);

        provice = (RelativeLayout) dialog.findViewById(R.id.area_province_layout);
        provice.setOnClickListener(this);

        city=(RelativeLayout)dialog.findViewById(R.id.area_city_layout);
        city.setOnClickListener(this);

        country=(RelativeLayout)dialog.findViewById(R.id.area_country_layout);
        country.setOnClickListener(this);

        countrySp = (Spinner)dialog.findViewById(R.id.area_country_spinner);
        pSp =  (Spinner)dialog.findViewById(R.id.area_province_spinner);
        cSp = (Spinner)dialog.findViewById(R.id.area_city_spinner);

        strProvice = (String)pSp.getSelectedItem();
        strCity = (String)cSp.getSelectedItem();
		selectedListener(); //设置监听事件

		sureBtn = (Button) dialog.findViewById(R.id.area_save_btn);
		sureBtn.setOnClickListener(this);

		cancleBtn = (Button) dialog.findViewById(R.id.area_cancel_btn);
		cancleBtn.setOnClickListener(this);

		dialog.show();
	}

	/**
	 * 销毁dialog
	 */
	public void dismissBornDialog() {
		if(dialog!=null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 下拉框选择事件监听
	 */
	public void selectedListener() {
	    countrySp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCountry = (String)countrySp.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pSp.setOnItemSelectedListener(new OnItemSelectedListener() { //年份下拉框的监听
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    strProvice = (String)pSp.getSelectedItem();
                    setCity();
			}
		});

        cSp.setOnItemSelectedListener(new OnItemSelectedListener() {// 月份下拉框的监听
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO
                strCity=(String) cSp.getSelectedItem();
			}
		});
	}


	/**
	 * 接口回调
	 * @author huir316
	 *
	 */
	public interface AreaSpinnerCallBackClickListener {
		void click(View view, String strCountry,String strProvice, String strCity);
	};

	public void setAreaCallBackClickListener(AreaSpinnerCallBackClickListener spinnerBackClickListener) {
		if(spinnerBackClickListener !=null) {
			this.spinnerBackClickListener = spinnerBackClickListener;
		}
	}

	@Override
	public void onClick(View v) {
		if(spinnerBackClickListener != null) {
			spinnerBackClickListener.click(v,this.strCountry,this.strProvice,this.strCity);  //传view, intYrS,intMths,intDays 值到接口回调 实现点击事件
		}
	}

	
	/**
	 * 各个省份的市区
	 */
	public void setCity() {
        switch (strProvice){
            case "北京":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.beijing, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "天津":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.tianjin, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "上海":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.shanghai, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "重庆":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.chongqing, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "河北省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.hebei, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "山西省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.shanxi, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "辽宁省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.liaoning, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "吉林省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.beijing, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "黑龙江省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.jilin, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "江苏省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.jiangsu, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "浙江省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.zhejiang, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "安徽省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.anhui, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "福建省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.fujian, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "江西省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.jiangxi, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "山东省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.shandong, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "河南省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.henan, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "湖北省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.hubei, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "湖南省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.hunan, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "广东省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.guangdong, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "海南省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.hainan, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "四川省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.sichuan, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "贵州省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.guizhou, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "云南省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.yunan, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "陕西省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.shanxis, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "甘肃省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.gansu, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "青海省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.qinghai, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "台湾省":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.taiwan, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "内蒙古自治区":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.neimenggu, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "广西壮族自治区":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.guangxi, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "西藏自治区":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.xizang, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "宁夏回族自治区":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.ningxia, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "新疆维吾尔自治区":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.xinjiang, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "香港特别行政区":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.xianggang, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
            case "澳门特别行政区":
                adapter=ArrayAdapter.createFromResource(this.context, R.array.aomen, android.R.layout.simple_dropdown_item_1line);
                cSp.setAdapter(adapter);
                break;
        }
	}
}
