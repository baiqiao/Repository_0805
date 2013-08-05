package com.example.clientversion_1.view;

/**
 * Title: ScrollTool.java<br>
 * author:Xiaqiulei <br>
 * Date: 2013-2-27<br>
 * Version:v1.0
 */
public class TouchTool {

	private int startX, startY;
	private int endX, endY;

	public TouchTool(int startX, int startY, int endX, int endY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	/**
	 * @param dx
	 * @return
	 */
	public int getScrollX(float dx) {
		 int xx = (int) (startX + (endX - startX) * Math.sin(Math.PI / 2 * (dx - startY) / (endX - startX)));
		//int xx = (int) (startX + dx / 2.5F);
		return xx;
	}

	/**
	 * @param dy
	 * @return
	 */
	public int getScrollY(float dy) {
		System.out.println("-->" + dy);
		 int yy = (int) (startY + (endY - startY) * Math.sin(Math.PI / 2 * dy / (endY - startY)));
		//int yy = (int) (startY + dy / 2.5F);
		System.out.println("-->" + yy);
		return yy;
	}
}