package com.bolue.scan.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by cty on 2017/6/5.
 */

public class MyViewPager extends ViewPager {
    private boolean isScrollEnable = false;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setScrollEnable(boolean isEnable){
        this.isScrollEnable = isEnable;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScrollEnable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScrollEnable;
    }
}
