package com.example.clientversion_1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.clientversion_1.adapter.MasterAdapter;
import com.example.clientversion_1.adapter.RightAdapter;
import com.example.clientversion_1.entity.ProjectInfo;
import com.example.clientversion_1.view.HomeCenterLayout;
import com.example.clientversion_1.view.ImageTextButton;

public class MainActivity extends Activity implements OnClickListener, OnTouchListener, OnItemClickListener{
	
	private HomeCenterLayout centerLayout;
	private LinearLayout leftLayout;
	private LinearLayout rightLayout;
	/** 左边界面控件*/
	private ImageView iv_userhead;
	
	
	/**中间界面控件*/
	private ImageButton leftBtn;
	private ImageButton rightBtn; 
	private ListView HomelistView;
	
	
	/**右边界面控件*/
	private ListView right_list;
	private ImageTextButton right_btn_recommend;
	private ImageTextButton right_btn_hot;
	private ImageTextButton right_btn_new;
	private ImageTextButton right_btn_willtofinish;
	
	
	private LayoutInflater inflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		inflater = this.getLayoutInflater();
		

		leftLayout = (LinearLayout)this.findViewById(R.id.homeLeft);
		rightLayout = (LinearLayout)this.findViewById(R.id.homeRight);
		centerLayout = (HomeCenterLayout)this.findViewById(R.id.homeCenter);
		leftLayout.setVisibility(View.GONE);
		rightLayout.setVisibility(View.GONE);
		centerLayout.setBrotherLayout(leftLayout, rightLayout);
		
		initView();
		
	}
	
	private void initView() {
		/**LEFT*/
		iv_userhead = (ImageView)this.findViewById(R.id.left_iv_userhead);
		/**HOME*/
		leftBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnLeft);
		rightBtn = (ImageButton)this.findViewById(R.id.ivTitleBtnRigh);
		HomelistView = (ListView)this.findViewById(R.id.lvhome);
		/**RIGHT*/
		right_btn_recommend = (ImageTextButton)this.findViewById(R.id.right_btn_recommend);
		right_btn_hot = (ImageTextButton)this.findViewById(R.id.right_btn_hot);
		right_btn_new = (ImageTextButton)this.findViewById(R.id.right_btn_new);
		right_btn_willtofinish = (ImageTextButton)this.findViewById(R.id.right_btn_willtofinish);
		right_list = (ListView)this.findViewById(R.id.right_list);
		
		
		/**LEFT*/
		iv_userhead.setOnClickListener(this);
		iv_userhead.setOnTouchListener(this);
		
		/**HOME*/
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
		HomelistView.setOnItemClickListener(this);
		List<ProjectInfo> proinfos = new ArrayList<ProjectInfo>();
		for(int i=0; i<5; i++) {
			ProjectInfo proinfo = new ProjectInfo();
			proinfo.setProgressNum(76);
			proinfo.setReachNum(91);
			proinfo.setSupportNum(8426);
			proinfo.setRemainTime(16);
			proinfo.setAttentionNum(30);
			proinfo.setDiscussNum(15);
			proinfo.setSharedNum(675);
			
			proinfos.add(proinfo);
		}
		HomelistView.setAdapter(new MasterAdapter(proinfos, inflater));
		
		/**RIGHT*/
		right_list.setAdapter(new RightAdapter(inflater));
		setListViewHeight(right_list);
		right_list.setOnItemClickListener(this);
		right_btn_recommend.setOnClickListener(this);
		right_btn_hot.setOnClickListener(this);
		right_btn_new.setOnClickListener(this);
		right_btn_willtofinish.setOnClickListener(this);
		
	}

	@Override
	public void onBackPressed() {
		if (centerLayout.getPage() != HomeCenterLayout.MIDDLE)
			centerLayout.setPage(HomeCenterLayout.MIDDLE);
		else
			super.onBackPressed();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			((ImageView)v).getDrawable().setAlpha(150);
			((ImageView)v).invalidate();
		}
		else {
			((ImageView)v).getDrawable().setAlpha(255);
			((ImageView)v).invalidate();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.ivTitleBtnLeft){
			if (centerLayout.getPage() == HomeCenterLayout.MIDDLE)
				centerLayout.setPage(HomeCenterLayout.LEFT);
			else
				centerLayout.setPage(HomeCenterLayout.MIDDLE);
		}
		else if(v.getId() == R.id.ivTitleBtnRigh) {
			if (centerLayout.getPage() == HomeCenterLayout.MIDDLE)
				centerLayout.setPage(HomeCenterLayout.RIGHT);
			else
				centerLayout.setPage(HomeCenterLayout.MIDDLE);
		}
		else if(v.getId() == R.id.left_iv_userhead) {
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			int version =  Integer.valueOf(android.os.Build.VERSION.SDK);       
			if(version > 5 ){         
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);       
			} 
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch(arg0.getId()) {
		case R.id.right_list :
			centerLayout.setPage(HomeCenterLayout.MIDDLE);
			break;
		case R.id.lvhome :
			
			break;
		default:
			break;	
		}
		
	}
	
	
	
	/**  
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题  
     * @param listView  
     */  
    public void setListViewHeight(ListView listView) {    
            
        // 获取ListView对应的Adapter    
        ListAdapter listAdapter = listView.getAdapter();    
        
        if (listAdapter == null) {    
            return;    
        }    
        int totalHeight = 0;    
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目    
            View listItem = listAdapter.getView(i, null, listView);    
            listItem.measure(0, 0); // 计算子项View 的宽高    
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度    
        }    
        
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));    
        listView.setLayoutParams(params);    
    }    

}