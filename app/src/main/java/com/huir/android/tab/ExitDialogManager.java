package com.huir.android.tab;

import com.huir.test.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

/**
 * 退出登录模块的Dialog显示
 * @author huir316
 *
 */
public class ExitDialogManager implements OnClickListener{
	private CallBackClickListener backClickListener;
	private View view;
	private Context context;
	private Dialog dialog;
	private RelativeLayout logout;
	private RelativeLayout logoff;
	public ExitDialogManager(Context context) {
		super();
		this.context = context;
	}

	public void showExitDialog() {
		dialog = new Dialog(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.dialog_log_exit, null);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view);
		logout = (RelativeLayout) dialog.findViewById(R.id.exit_layout);
		logout.setOnClickListener(this);
		logoff=(RelativeLayout) dialog.findViewById(R.id.logoff_layout);
		logoff.setOnClickListener(this);
		dialog.setCancelable(true);
		dialog.show();
	}
	
	public void dimissExitDialog() {
		if(dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	
	/**
	 * 接口回调
	 * @author huir316
	 *
	 */
	public interface CallBackClickListener {
		void click(View view);
	};

	public void setCallBackClickListener(CallBackClickListener backClickListener) {
		if(backClickListener !=null) {
			this.backClickListener = backClickListener;
		}
	}

	@Override
	public void onClick(View v) {
		if(backClickListener != null) {
			backClickListener.click(v);
		}
	}

	
}
