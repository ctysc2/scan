package com.bolue.scan.mvp.view;


import com.bolue.scan.mvp.entity.CarouselEntity;
import com.bolue.scan.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/6/7.
 */

public interface CarouselView extends BaseView {
    void getCarouselCompleted(CarouselEntity entity);
}
