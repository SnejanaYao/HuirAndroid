package com.huir.android.tab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huir.test.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 扩展列表
 * @author huir316
 *
 */
public class DeExpandableAdapater extends BaseExpandableListAdapter{
	private Map<String, List<String>> dataset = new HashMap<>();
	private String[] parentList = new String[]{"first", "second", "third"};  
	private List<String> childrenList1 = new ArrayList<>();  
	private List<String> childrenList2 = new ArrayList<>();  
	private List<String> childrenList3 = new ArrayList<>();  
	
	private ViewHolder viewHolder;
	private Context context;
	public DeExpandableAdapater(Context context) {
		super();
		//TODO 暂时先实验数据
		initialData();
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return dataset.get(parentList[groupPosition]).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.item_child_list, null);
			viewHolder = new  ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.item_child.setVisibility(View.VISIBLE);
		String group_user = dataset.get(parentList[groupPosition]).get(childPosition);
		viewHolder.depatermentChild.setText(group_user);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return dataset.get(parentList[groupPosition]).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return dataset.get(parentList[groupPosition]);
	}

	@Override
	public int getGroupCount() {
		return dataset.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.item_father_list, null);
			viewHolder = new  ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.item_father.setVisibility(View.VISIBLE);
		viewHolder.depaterment.setText(parentList[groupPosition]);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	class ViewHolder {
		private View rootView;
		private RelativeLayout item_father;
		private TextView depaterment;
		private RelativeLayout item_child;
		private TextView depatermentChild;
		
		public ViewHolder(View rootView) {
			this.rootView = rootView;
			this.item_father = (RelativeLayout) rootView.findViewById(R.id.item_father_layout);
			this.depaterment =(TextView)rootView.findViewById(R.id.item_group_name);
			this.item_child= (RelativeLayout) rootView.findViewById(R.id.item_child_layout);
			this.depatermentChild = (TextView)rootView.findViewById(R.id.friend_text_exlist_item_name);
		}
	}
	
	
	private void initialData() {  
	    childrenList1.add(parentList[0] + "-" + "first");  
	    childrenList1.add(parentList[0] + "-" + "second");  
	    childrenList1.add(parentList[0] + "-" + "third");  
	    childrenList2.add(parentList[1] + "-" + "first");  
	    childrenList2.add(parentList[1] + "-" + "second");  
	    childrenList2.add(parentList[1] + "-" + "third");  
	    childrenList3.add(parentList[2] + "-" + "first");  
	    childrenList3.add(parentList[2] + "-" + "second");  
	    childrenList3.add(parentList[2] + "-" + "third");  
	    dataset.put(parentList[0], childrenList1);  
	    dataset.put(parentList[1], childrenList2);  
	    dataset.put(parentList[2], childrenList3);  
	}  
}
