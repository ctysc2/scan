package com.bolue.scan.repository.network;

import android.support.annotation.NonNull;
import android.util.SparseArray;


import com.bolue.scan.application.App;
import com.bolue.scan.common.ApiConstants;
import com.bolue.scan.common.HostType;
import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.CarouselEntity;
import com.bolue.scan.mvp.entity.HeaderEntity;
import com.bolue.scan.mvp.entity.IndexEntity;
import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.mvp.entity.LoginEntity;
import com.bolue.scan.mvp.entity.LoginRequestEntity;
import com.bolue.scan.mvp.entity.OffLineLessonEntity;
import com.bolue.scan.mvp.entity.ParticipantEntity;
import com.bolue.scan.utils.PreferenceUtils;
import com.bolue.scan.utils.SystemTool;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

import static okhttp3.internal.Util.UTF_8;

/**
 * Created by cty on 2016/12/10.
 */

public class RetrofitManager {

    private RWService mRWService;

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    private static volatile OkHttpClient sOkHttpClient;

    private static SparseArray<RetrofitManager> sRetrofitManager = new SparseArray<>(HostType.TYPE_COUNT);

    public RetrofitManager(@HostType.HostTypeChecker int hostType) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.getHost(hostType))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        mRWService = retrofit.create(RWService.class);
    }

    private OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "HttpCache"),
                        1024 * 1024 * 100);
                if (sOkHttpClient == null) {
                    sOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .addInterceptor(mLoggingInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor).build();

                }
            }
        }
        return sOkHttpClient;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!SystemTool.isNetworkAvailable()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                KLog.d("no network");
            }
            //header添加bolueClient和MyAuth
            Response originalResponse;
            String openid = PreferenceUtils.getPrefString(App.getAppContext(),"openid","");
            String code = PreferenceUtils.getPrefString(App.getAppContext(),"code","");
            String phone = PreferenceUtils.getPrefString(App.getAppContext(),"phone","");
            String imei = PreferenceUtils.getPrefString(App.getAppContext(),"imei","");

//            String openid = "c2cacc74-09ef-11e7-bc9a-288023a21480";
//            String code = "2e43f338a0bcbbf024ac44d4fbb30670";

            request = request.newBuilder()
                    .header("bolueClient", "android")
                    .header("MyAuth", new Gson().toJson(new HeaderEntity(openid, code)))
                    .header("systemType","9")
                    .header("phone",phone)
                    .header("imei",imei)
                    .build();

            //添加公共参数yyyTS
            HttpUrl.Builder authorizedUrlBuilder = request.url()
                    .newBuilder()
                    .scheme(request.url().scheme())
                    .host(request.url().host())
                    .addQueryParameter("yyyTS", String.valueOf(new Date().getTime()));


            // 新的请求
            Request newRequest = request.newBuilder()
                    .method(request.method(), request.body())
                    .url(authorizedUrlBuilder.build())
                    .build();

            KLog.i(String.format(" Retrofit Sending request: %s", newRequest.url()));

            KLog.i("Retrofit", "headers:" + newRequest.headers());
            originalResponse = chain.proceed(newRequest);


            if (SystemTool.isNetworkAvailable()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                //String cacheControl = request.cacheControl().toString();

                String cacheControl = getCacheControl();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();

            }
        }
    };

    private final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            KLog.i(String.format(Locale.getDefault(), "Retrofit Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            String logResponseBody = buffer.clone().readString(UTF_8);
            KLog.i("Retrofit", "response.body:" + logResponseBody);
            return response;
        }
    };


    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
            return retrofitManager;
        }
        return retrofitManager;
    }


    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    private String getCacheControl() {
        return SystemTool.isNetworkAvailable() ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    //获取首页信息
    public Observable<IndexEntity> getIndex() {
        return mRWService.getIndex();
    }

    //获取首页轮播图
    public Observable<CarouselEntity> getCarousel() {
        return mRWService.getCarousel();
    }

    //获取当月预约列表
    public Observable<CalendarEntity> getReserveList(int year ,int day,int city_id) {
        return mRWService.getReserveList(year,day,city_id);
    }

    //获取线下课城市列表
    public Observable<LabelEntity> getLabelList(String type) {
        return mRWService.getLabelList(type);
    }

    //获取线下课详情
    public Observable<OffLineLessonEntity> getOffLineDetail(int id){
        return mRWService.getOffLineDetail(id);
    }

    //获取报名人详情
    //报名人详情
    public Observable<ParticipantEntity> getParticipantDetail(int user_id,
                                                              boolean isInvited,
                                                              int resource_id
    ){
        return mRWService.getParticipantDetail(user_id,isInvited == true?1:0,resource_id);
    }

    //登录
    public Observable<LoginEntity> doLogin(LoginRequestEntity entity){

        return mRWService.doLogin(entity);
    }
}

