package com.ping.cookbook.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/****************************************
 * 屏蔽掉子view的所有事件 目前主要用于进度条展示
 *
 *****************************************/
public class RelativeLayoutNoEvent extends RelativeLayout {

	public RelativeLayoutNoEvent(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RelativeLayoutNoEvent(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return true;
	}
}
