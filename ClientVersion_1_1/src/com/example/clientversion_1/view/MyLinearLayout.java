package com.example.clientversion_1.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.clientversion_1.R;

/**
 * Title: MyLinearLayout.java<br>
 * Version:v1.0
 */
public class MyLinearLayout extends LinearLayout {
	ImageView iv1, iv2;
	int left, top;
	float startX, startY;
	float currentX, currentY;

	int rootW, rootH;

	int iv1H;
	Scroller scroller;
	TouchTool tool;

	// �?��移动距离,实际情况可以变化
	static final int LEN = 200;

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		//iv1 = (ImageView) findViewById(R.id.iv1);
		//iv2 = (ImageView) findViewById(R.id.iv);
		setLongClickable(true);
		scroller = new Scroller(getContext(),
				new AccelerateDecelerateInterpolator());
	}

	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (!scroller.isFinished()) {
			return super.onTouchEvent(event);
		}
		currentX = event.getX();
		currentY = event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			left = iv2.getLeft();
			top = iv2.getTop();

			rootW = getWidth();
			rootH = getHeight();

			iv1H = iv1.getHeight();

			startX = currentX;
			startY = currentY;
			// TouchTool 是滑动工具，是下面的imageview移动的时候，计算能够移动的距�?			
			tool = new TouchTool(iv2.getLeft(), iv2.getTop(), iv2.getLeft(),
					iv2.getTop() + LEN);
			break;
		case MotionEvent.ACTION_MOVE:
			// int l = (int) (left + currentX - startX);
			// int t = (int) (top + currentY - startY);
			if (tool != null) {

				int l = tool.getScrollX(currentX - startX);
				int t = tool.getScrollY(currentY - startY);
				if (t >= top && t <= iv2.getTop() + LEN) {
					// 滑动时�?，重新定位上面的布局和下面的布局
					iv2.layout(left, t, left + iv2.getWidth(),
							t + iv2.getHeight());
					iv1.layout(0, 0, iv1.getWidth(), t);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			// iv2.layout(left, top, left + iv2.getWidth(), top +
			// iv2.getHeight());
			// iv1.layout(0, 0, iv1.getWidth(), iv1H);
			System.out.printf("left1:%s left2:%s\n", iv2.getLeft(), 0);
			System.out.printf("top1:%s top2:%s\n", iv2.getTop(), iv1H);
			// 抬起手的时�?，慢慢的换到原先位置
			scroller.startScroll(iv2.getLeft(), iv2.getTop(), 0 - iv2.getLeft(), iv1H - iv2.getTop(), 200);
			invalidate();
			break;
		}
		return super.onTouchEvent(event);
	}

	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			int x = scroller.getCurrX();
			int y = scroller.getCurrY();
			System.out.println("x=" + x);
			System.out.println("y=" + y);
			iv1.layout(0, 0, x + iv1.getWidth(), y);
			iv2.layout(x, y, x + iv2.getWidth(), y + iv2.getHeight());
			invalidate();
		}
	}
}

