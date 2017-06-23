package com.codbking.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by codbking on 2016/12/18.
 * email:codbking@gmail.com
 * github:https://github.com/codbking
 * blog:http://www.jianshu.com/users/49d47538a2dd/latest_articles
 */

public class CalendarView extends ViewGroup {

    private static final String TAG = "CalendarView";

    private int selectPostion = -1;

    private CaledarAdapter adapter;
    private List<CalendarBean> data;
    private OnItemClickListener onItemClickListener;

    private int row = 6;
    private int column = 7;
    private int itemWidth;
    private int itemHeight;

    private boolean isToday;

    public interface OnItemClickListener {
        void onItemClick(View view, int postion, CalendarBean bean);
    }

    public CalendarView(Context context, int row) {
        super(context);
        this.row = row;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public void setAdapter(CaledarAdapter adapter) {
        this.adapter = adapter;
    }

    public void setData(List<CalendarBean> data,boolean isToday) {
        this.data = data;
        this.isToday=isToday;
        setItem();
        requestLayout();
    }
    public void setData(List<CalendarBean> data){
        this.data = data;
        setItem();
    }
    public List<CalendarBean> getData() {
        return this.data;
    }
    private int getSelectPosition(){

        //判断选中的日期是否属于当月，是则返回position不是则返回-1
        if(!TextUtils.isEmpty(Globle.selectedDate)){
            int year = Integer.parseInt(Globle.selectedDate.split("-")[0]);
            int month = Integer.parseInt(Globle.selectedDate.split("-")[1]);
            int day = Integer.parseInt(Globle.selectedDate.split("-")[2]);

            for (int i = 0; i < data.size(); i++) {
               CalendarBean bean = data.get(i);
               if(bean.year==year&&bean.moth==month&&bean.day==day){
                   return i;
               }
            }

        }

        return -1;
    }
    public void setItem() {

        selectPostion = getSelectPosition();

        if (adapter == null) {
            throw new RuntimeException("adapter is null,please setadapter");
        }

        for (int i = 0; i < data.size(); i++) {
            CalendarBean bean = data.get(i);
            View view = getChildAt(i);
            View chidView = adapter.getView(view, this, bean);

            if (view == null || view != chidView) {
                addViewInLayout(chidView, i, chidView.getLayoutParams(), true);
            }

            if(isToday&&selectPostion==-1&&TextUtils.isEmpty(Globle.selectedDate)){
                int[]date=CalendarUtil.getYMD(new Date());
                if(bean.year==date[0]&&bean.moth==date[1]&&bean.day==date[2]){
                     selectPostion=i;
                    Globle.selectedDate = bean.year+"-"+bean.moth+"-"+bean.day;
                }
            }

            chidView.setSelected(selectPostion==i);

            ((TextView)((ViewGroup)chidView).getChildAt(0)).setTextColor(selectPostion==i? Color.parseColor("#FFFFFF"):Color.parseColor("#000000"));

            setItemClick(chidView, i, bean);

        }
    }

    public Object[] getSelect(){
         if(selectPostion == -1 || selectPostion>= data.size())
             return new Object[]{null,0,data.get(10)};

         return new Object[]{getChildAt(selectPostion),selectPostion,data.get(selectPostion)};
    }

    public void setItemClick(final View view, final int potsion, final CalendarBean bean) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectPostion != -1) {
                    getChildAt(selectPostion).setSelected(false);


                    ((TextView)((RelativeLayout)getChildAt(selectPostion)).getChildAt(0)).setTextColor(Color.parseColor("#000000"));
             }

            selectPostion = potsion;

            getChildAt(potsion).setSelected(true);

            final ScaleAnimation animation =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(200);
            getChildAt(potsion).startAnimation(animation);
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    animation.cancel();
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });

            ((TextView)((RelativeLayout)getChildAt(potsion)).getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));

            Globle.selectedDate = bean.year+"-"+bean.moth+"-"+bean.day;

            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, potsion, bean);
            }
            }
        });
    }

    public int[] getSelectPostion() {
        Rect rect = new Rect();
        try {
            getChildAt(selectPostion).getHitRect(rect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new int[]{rect.left, rect.top, rect.right, rect.top};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int parentWidth = MeasureSpec.getSize(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY));

        itemWidth = parentWidth / column;
        itemHeight = itemWidth;

        View view = getChildAt(0);
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null && params.height > 0) {
            itemHeight = params.height;
        }
        setMeasuredDimension(parentWidth, itemHeight * row);


        for(int i=0;i<getChildCount();i++){
            View childView=getChildAt(i);
            childView.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
        }

        Log.i(TAG, "onMeasure() called with: itemHeight = [" + itemHeight + "], itemWidth = [" + itemWidth + "]");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i <getChildCount(); i++) {
            layoutChild(getChildAt(i), i, l, t, r, b);
        }
    }

    private void layoutChild(View view, int postion, int l, int t, int r, int b) {

        int cc = postion % column;
        int cr = postion / column;

        int itemWidth = view.getMeasuredWidth();
        int itemHeight = view.getMeasuredHeight();

        l = cc * itemWidth;
        t = cr * itemHeight;
        r = l + itemWidth;
        b = t + itemHeight;
        view.layout(l, t, r, b);

    }
}
