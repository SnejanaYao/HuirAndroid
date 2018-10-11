package com.huir.android.tab.adapter;

import java.util.ArrayList;
import java.util.List;

import com.huir.test.R;
import com.huir.android.entity.UserShow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 联系人对话框的主页显示
 * @author huir316
 *
 */
public class ShowUsersAdapter extends BaseAdapter {
	private ViewHolder viewHolder;
	private List<UserShow> datas = new ArrayList<UserShow>();
	private Context context;
	
	public ShowUsersAdapter(List<UserShow> datas, Context context) {
		super();
		this.datas = datas;
		this.context = context;
	}

    public ShowUsersAdapter(Context context) {
        super();
        this.context = context;
    }

	//给adapter添加数据
	public void addDataToAdapter(UserShow user) {
		      datas.add(user);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.item_activity_show_users, null);
			viewHolder = new  ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.showUsersLayout.setVisibility(View.VISIBLE);
		return convertView;
	}

	class ViewHolder {
		private View rootView;
		private ImageView chatUserImage; //联系人头像
		private TextView nickChatName; //联系人昵称
		private TextView message;
		private TextView date;
		private RelativeLayout showUsersLayout;
		
		
		public ViewHolder(View rootView) {
			this.rootView = rootView;
			this.showUsersLayout =(RelativeLayout) rootView.findViewById(R.id.show_users_layout);
			this.chatUserImage = (ImageView) rootView.findViewById(R.id.show_users_iamge);
			this.nickChatName = (TextView) rootView.findViewById(R.id.show_users_nickname);
			this.message = (TextView) rootView.findViewById(R.id.show_users_message);
			this.date = (TextView) rootView.findViewById(R.id.show_users_date);
		}
	}
}
