package com.yy.sagit;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/1/8.
 */

public  class BannerViewPager extends ViewPager {
    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private int lastX;
    private int lastY;
    /**
     * 事件分发
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
// getParent().requestDisallowInterceptTouchEvent(true);//原来的
//完美解决与下拉刷新之间的冲突
        getParent().requestDisallowInterceptTouchEvent(true);
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - lastY;
                int deltaX = x - lastX;
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
