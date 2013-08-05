package com.example.clientversion_1.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.clientversion_1.R;
import com.example.clientversion_1.entity.ProjectInfo;

public class MasterAdapter extends BaseAdapter {

	private List<ProjectInfo> proinfos;
	private LayoutInflater inflater;
	int count = 5;
	
	public MasterAdapter(List<ProjectInfo> proinfos, LayoutInflater inflater) {
		this.proinfos = proinfos;
		this.inflater = inflater;
	}
	
	@Override
	public int getCount() {
		//return proinfos.size();
		return count;
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.item_master_page, null);
		//LinearLayout view = (LinearLayout) inflater.inflate(R.layout.test, null);
		RelativeLayout master_list_rl1 = (RelativeLayout)view.findViewById(R.id.master_list_rl1);
		ProjectInfo proinfo = proinfos.get(arg0);
		//ImageView master_item_iv_bg = (ImageView)view.findViewById(R.id.master_item_iv_bg);
		ProgressBar masterpage_pbar = (ProgressBar)view.findViewById(R.id.master_item_pbar);
		TextView master_item_tv_reachnum = (TextView)view.findViewById(R.id.master_item_tv_reachnum);
		TextView master_item_tv_supportnum = (TextView)view.findViewById(R.id.master_item_tv_supportnum);
		TextView master_item_tv_remaintime = (TextView)view.findViewById(R.id.master_item_tv_remaintime);
		TextView master_item_tv_attentionnum = (TextView)view.findViewById(R.id.master_item_tv_attentionnum);
		TextView master_item_tv_discussnum = (TextView)view.findViewById(R.id.master_item_tv_discussnum);
		TextView master_item_tv_sharenum = (TextView)view.findViewById(R.id.master_item_tv_sharenum);
		
		masterpage_pbar.setProgress(proinfo.getProgressNum());
		master_item_tv_reachnum.setText(proinfo.getReachNum() + "%已达到");
		master_item_tv_supportnum.setText(proinfo.getSupportNum() + "已获支持");
		master_item_tv_remaintime.setText(proinfo.getRemainTime() + "天剩余时间");
		master_item_tv_attentionnum.setText(proinfo.getAttentionNum() + "");
		master_item_tv_discussnum.setText(proinfo.getDiscussNum() + "");
		master_item_tv_sharenum.setText(proinfo.getSharedNum() + "");
		
		
		if(arg0 % 2 == 0){
			master_list_rl1.setBackgroundResource(R.drawable.masterlist_item_bg);
		}
		/*
		view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					
					v.setAlpha(0.5f);
					v.invalidate();
				}
				else {
					v.setAlpha(1);
					v.invalidate();
				}
				return false;
			}
		});*/
		return view;
	}

}
