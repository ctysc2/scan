package com.bolue.scan.mvp.view;

import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.view.base.BaseView;
import com.codbking.calendar.CalendarView;

/**
 * Created by cty on 2017/6/14.
 */

public interface ReserveView extends BaseView{
    void getReserveListCompleted(CalendarView updateView,CalendarEntity entity);
}
