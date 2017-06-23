package com.bolue.scan.mvp.presenter.impl;



import com.bolue.scan.mvp.entity.CarouselEntity;
import com.bolue.scan.mvp.interactor.impl.CarouselInteractorImpl;
import com.bolue.scan.mvp.presenter.base.BasePresenterImpl;
import com.bolue.scan.mvp.view.CarouselView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/6/7.
 */

public class CarouselPresenterImpl extends BasePresenterImpl<CarouselView,CarouselEntity> {
    private CarouselInteractorImpl mCarouselInteractorImpl;

    @Inject
    public CarouselPresenterImpl(CarouselInteractorImpl carouselInteractorImpl){
        mCarouselInteractorImpl = carouselInteractorImpl;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getCarousel(){
        mSubscription = mCarouselInteractorImpl.getCarousel(this);

    }

    @Override
    public void success(CarouselEntity data) {
        super.success(data);
        mView.getCarouselCompleted(data);
    }
}
