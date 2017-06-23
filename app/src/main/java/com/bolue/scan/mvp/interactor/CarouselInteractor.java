package com.bolue.scan.mvp.interactor;


import com.bolue.scan.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/6/7.
 */

public interface CarouselInteractor<T> {
    Subscription getCarousel(RequestCallBack<T> callback);
}
