package com.example.clientversion_1.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clientversion_1.R;
import com.example.clientversion_1.entity.RightItem;

public class RightAdapter extends BaseAdapter {

	private List<RightItem> ritem;
	private LayoutInflater inflater;
	private String[] itemname = {"设计", "科技", "出版", "影视", "动漫", "食品", "摄影", "其他"};
	private int[] itemnum = {63, 43, 88, 90, 111, 675, 7421, 64};

	public RightAdapter(List<RightItem> ritem, LayoutInflater inflater) {
		this.ritem = ritem;
		this.inflater = inflater;
	}
	public RightAdapter(LayoutInflater inflater){
		this.inflater = inflater;
		ritem = new ArrayList<RightItem>();
		getData();
	}

	@Override
	public int getCount() {
		return ritem.size();
	}

	@Override
	public RightItem getItem(int position) {
		return ritem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.item_rightlist, null);
		
		TextView right_tv_itemname = (TextView)view.findViewById(R.id.right_tv_itemname);
		TextView right_tv_itemnum = (TextView)view.findViewById(R.id.right_tv_itemnum);
		
		right_tv_itemname.setText(ritem.get(position).getItemname());
		right_tv_itemnum.setText(ritem.get(position).getItemnum());
		
		return view;
	}
	
	private void getData() {
		
		for(int i=0; i<itemname.length; i++) {
			RightItem item = new RightItem();
			item.setItemname(itemname[i]);
			item.setItemnum(itemnum[i]+"");
			ritem.add(item);
		}
	}
	
}