package com.bolue.scan.repository.network;



import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.CarouselEntity;
import com.bolue.scan.mvp.entity.IndexEntity;
import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.mvp.entity.LoginEntity;
import com.bolue.scan.mvp.entity.LoginRequestEntity;
import com.bolue.scan.mvp.entity.OffLineLessonEntity;
import com.bolue.scan.mvp.entity.ParticipantEntity;
import com.bolue.scan.mvp.entity.SignRequestEntity;
import com.bolue.scan.mvp.entity.base.BaseEntity;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    //线下课详情
    @GET("offlineDetailSign")
    Observable<OffLineLessonEntity> getOffLineDetail(
            @Query("id") int id);

    //报名人详情
    @GET("enrollUserDetail")
    Observable<ParticipantEntity> getParticipantDetail(
            @Query("user_id") int user_id,
            @Query("is_invited") int isInvited,
            @Query("resource_id") int resource_id
            );

    //登录
    @POST("login")
    Observable<LoginEntity> doLogin(
            @Body LoginRequestEntity entity
    );

    //签到
    @POST("doSignOffline")
    Observable<BaseEntity> doSign(
            @Body SignRequestEntity entity
    );

}
