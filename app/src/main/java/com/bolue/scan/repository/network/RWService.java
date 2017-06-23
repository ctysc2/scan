package com.bolue.scan.repository.network;



import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.CarouselEntity;
import com.bolue.scan.mvp.entity.IndexEntity;
import com.bolue.scan.mvp.entity.LabelEntity;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cty on 2016/12/10.
 */

public interface RWService {

    //获取首页信息
    @GET("index")
    Observable<IndexEntity> getIndex();

    //首页轮播图
    @GET("topic")
    Observable<CarouselEntity> getCarousel();

    //预约的报名
    @GET("offlineByDate")
    Observable<CalendarEntity> getReserveList(
            @Query("year") int year,
            @Query("month") int month,
            @Query("city_id") int city_id);

    //线下课城市列表
    @GET("labelList")
    Observable<LabelEntity> getLabelList(
            @Query("type") String type);

}
