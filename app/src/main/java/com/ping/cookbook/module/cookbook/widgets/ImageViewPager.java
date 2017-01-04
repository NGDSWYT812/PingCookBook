package com.ping.cookbook.module.cookbook.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Description <适配嵌套两层ViewPager并支持底层ViewPager多事件>
 * @author 温华平
 * @date 2015-10-22
 */
public class ImageViewPager extends ViewPager {
	public ImageViewPager(Context context) {
		super(context);
	}

	public ImageViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean performClick() {// 响应哦你ClickListener
		isClick = true;
		return super.performClick();
	};

	private float touchDownX;// 记录初始点X
	private float touchDownY;// 记录初始点Y

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		try {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:// 在按下的时候拦截父容器分发事件
				getParent().requestDisallowInterceptTouchEvent(true);// 禁止父类拦截事件
				if (event.getPointerCount() > 1) {// 在多指操作的情况下,getX getY 会抛异常：java.lang.IllegalArgumentException: pointerIndex out of range
					return checkDisallowInterceptStatus(event);
				}
				touchDownX = event.getX();
				touchDownY = event.getY();
				isClick = false;// 记录当前状态为点击
				break;
			case MotionEvent.ACTION_MOVE:
				if (event.getPointerCount() < 1 && Math.abs(event.getY() - touchDownY) > 10 || Math.abs(event.getX() - touchDownX) > 10) {
					isClick = true;// 如果有移动的话则不是点击事件
				}
			default:
				break;
			}
			return checkDisallowInterceptStatus(event);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return checkDisallowInterceptStatus(event);
	}

	private boolean checkDisallowInterceptStatus(MotionEvent event) {//
		if (isDisallowIntercept == false) {// 如果子类禁止父类拦截事件此处为True
			return super.onInterceptTouchEvent(event);// 子类处理完事件后，通过requestDisallowInterceptTouchEvent方法允许父类拦截事件，所以当前类先操作事件
		} else {// 如果子类禁止父类拦截事件，即子类欲先处理事件，所以此处为True，直接返回False将事件分发给子类
			return false;
		}
	}

	private boolean isDisallowIntercept = false;// 是否不允许拦截事件，True为不允许拦截事件即 当前类不做处理直接交给子类，False 则由当前类做处理

	@Override
	public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
		this.isDisallowIntercept = disallowIntercept;// 若子类禁止父类拦截事件
		if (disallowIntercept) {// 通知父类不允许拦截事件
			getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
		}
	}

	boolean isClick = true;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				if (isClick == true) {// 如果是点击事件的话
					performClick();// 通知响应点击事件
				}
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_MOVE:
				if (event.getPointerCount() > 1) {// 当前不需要操作多指事件,所以，直接将事件交给父类操作
					return super.onTouchEvent(event);
				}
				if (getCurrentItem() == getAdapter().getCount() - 1 && event.getX() < touchDownX) {
					getParent().requestDisallowInterceptTouchEvent(false); // 如果当前项是最后一项，并且是从右往左滑动，则表明当前动作欲滑出下一项，即外层下一项，通知父类允许拦截
				} else if (getCurrentItem() == 0 && event.getX() > touchDownX) {
					getParent().requestDisallowInterceptTouchEvent(false); // 如果当前项是第一项，并且是从左往右滑动，则表明当前动作欲滑出上一项，即外层上一项，通知父类允许拦截

				}
				break;
			default:
				break;
			}
			return super.onTouchEvent(event);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return super.onTouchEvent(event);
	}

}
