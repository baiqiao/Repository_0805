package com.example.clientversion_1.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.clientversion_1.R;
/**
 * ���ܣ��Զ����ͼƬ�����ֵİ��?
 * @author Administrator
 *
 */
public class ImageTextButton extends Button {
	private final String namespace =  "http://www.ligong.android.com/yaolei";
	private int resourceId =0;  
    private Bitmap bitmap;
    /**
     * ���췽��
     * @param context
     * @param attrs
     */
    public ImageTextButton(Context context,AttributeSet attrs)
    {
    	super(context, attrs);
    	setClickable(true);
    	//Ĭ��ʹ��R.drawable.icon����ͼƬ��ͨ��icon�����������ȡͼ�?
    	resourceId = attrs.getAttributeResourceValue(namespace, "icon", R.drawable.left_iv_support);
    	bitmap = BitmapFactory.decodeResource(getResources(), resourceId);  
    }
    /**
     * ��Ҫ�����Զ������ͼ����ķ���
     */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		
		int x=0;
        int y=(this.getMeasuredHeight() - bitmap.getHeight()) >> 2;   
        canvas.drawBitmap(bitmap, x, y, null);   
    
        canvas.translate((this.getMeasuredWidth()>>1) - 7*(int)this.getTextSize()/2, 0);  
		super.onDraw(canvas);
	}
	/**
	 * ����ͼƬ
	 * @param bitmap
	 */
	public void setIcon(Bitmap bitmap)
	{  
        this.bitmap=bitmap;  
        invalidate();  
    }
	/**
	 * ͨ��ͼƬ��Դ����ͼƬ
	 * @param resourceId
	 */
    public void setIcon(int resourceId)
    {  
        this.bitmap=BitmapFactory.decodeResource(getResources(), resourceId);  
        invalidate();  
    }  
}
