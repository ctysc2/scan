package com.bolue.scan.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.MyLocationStyle;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.utils.LogUtils;
import com.bolue.scan.R;
import com.bolue.scan.adapter.NormalRecyclerAdapter;
import com.bolue.scan.application.App;
import com.bolue.scan.listener.AlertDialogListener;
import com.bolue.scan.listener.OnItemClickListener;
import com.bolue.scan.listener.ReLoginEvent;
import com.bolue.scan.mvp.entity.CalendarEntity;
import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.mvp.entity.OffLineLessonEntity;
import com.bolue.scan.mvp.interactor.impl.ReserveInteractorImpl;
import com.bolue.scan.mvp.presenter.impl.LabelPresenterImpl;
import com.bolue.scan.mvp.presenter.impl.ReservePresenterImpl;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.mvp.view.LabelView;
import com.bolue.scan.mvp.view.ReserveView;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.utils.DimenUtil;
import com.bolue.scan.utils.PreferenceUtils;
import com.bolue.scan.utils.RxBus;
import com.bolue.scan.utils.TransformUtils;
import com.bolue.scan.widget.CityPopUpWindow;
import com.bolue.scan.zxing.activity.CaptureActivity;
import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarLayout;
import com.codbking.calendar.CalendarOnPageChangedListener;
import com.codbking.calendar.CalendarUtil;
import com.codbking.calendar.CalendarView;
import com.codbking.calendar.CalendarViewCreatedListener;
import com.codbking.calendar.Globle;
import com.codbking.calendar.entity.CalendarData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements CalendarViewCreatedListener,ReserveView,CalendarOnPageChangedListener,AMapLocationListener,LabelView {

    @BindView(R.id.calendarDateView)
    CalendarDateView mCalendarDateView;

    @BindView(R.id.xrefreshview)
    XRefreshView xRefreshView;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.calendarLayout)
    CalendarLayout mCalendarLayout;

    RxPermissions rxPermissions;

    @Inject
    LabelPresenterImpl mLabelPresenterImpl;

    @BindView(R.id.tv_station)
    TextView mStation;

    @BindView(R.id.ll_container)
    LinearLayout mLLContainer;

    @BindView(R.id.ll_location)
    LinearLayout mLLLocation;

    @BindView(R.id.rl_menu)
    RelativeLayout mRlMenu;

    ArrayList<LabelEntity.DataEntity.City> mCityList = new ArrayList<>();

    private NormalRecyclerAdapter adapter;

    private LinearLayoutManager layoutManager;

    private boolean hasMore = false;

    private boolean isFirstLoad = true;

    private boolean isShiftCity = false;

    private AMapLocationClientOption mLocationOption = null;

    private AMapLocationClient mlocationClient = null;
    //线下课数据源
    private ArrayList<CalendarData> dataSource = new ArrayList<>();

    private CityPopUpWindow mPopupWindow;

    private final int TYPE_LABEL = 1;

    private SlidingMenu menu;

    private Button mLogOut;

    private boolean isExit = false;
    //城市id
    private int mCity_id = -1;

    private LinearLayout mLLOffLine;

    private TextView mTvName;

    @OnClick({R.id.rl_last_month,R.id.rl_next_month,R.id.ll_location,R.id.rl_menu})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_last_month:
                mCalendarLayout.reCheckIsOpen();
                mCalendarDateView.setCurrentItem(mCalendarDateView.getCurrentItem()-1);
                break;
            case R.id.rl_next_month:
                mCalendarLayout.reCheckIsOpen();
                mCalendarDateView.setCurrentItem(mCalendarDateView.getCurrentItem()+1);
                break;
            case R.id.ll_location:
                createPopWin();
                break;
            case R.id.rl_menu:
                menu.toggle();
                break;
        }

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    private void initLabel(){
        mLabelPresenterImpl.attachView(this);
        mLabelPresenterImpl.getLabelList(TYPE_LABEL,"offline");
        mLabelPresenterImpl.beforeRequest();
        mStation.setText("上海");
    }
    @Override
    public void initViews() {


        mCalendarDateView.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                TextView view;
                View event;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_calendar, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int)DimenUtil.dp2px(50), (int)DimenUtil.dp2px(50));
                    convertView.setLayoutParams(params);
                }

                view = (TextView) convertView.findViewById(R.id.text);
                event = (View) convertView.findViewById(R.id.event);
                if(bean.hasEvent == true){
                    event.setVisibility(View.VISIBLE);
                }else{
                    event.setVisibility(View.INVISIBLE);
                }

                view.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    convertView.setVisibility(View.INVISIBLE);
                    view.setTextColor(0xff9299a1);
                } else {
                    convertView.setVisibility(View.VISIBLE);
                    view.setTextColor(0xff000000);
                }

                return convertView;
            }
        });
        mCalendarDateView.setOnCalenderViewCreatedListener(this);
        mCalendarDateView.setOnPageChangedListener(this);
        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {

                updateRecycleView(bean);

            }
        });

        int[] data = CalendarUtil.getYMD(new Date());
        //mTitle.setText(data[0] + "/" + data[1] + "/" + data[2]);
        mTitle.setText(data[0] + "年" + getDisPlayNumber(data[1]) + "月");
        initRecycleView();

        //暂时取消定位功能固定上海
        //startLocation();


        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setFadeDegree(0.35f);
        menu.setOffsetFadeDegree(0.2f);
        menu.setBehindWidth((int)DimenUtil.dp2px(160));
        menu.setFadeEnabled(true);
        menu.setMenu(R.layout.menu_item);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        mLogOut = (Button)findViewById(R.id.bt_logout);
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogOut();
            }
        });
        mLLOffLine = (LinearLayout)findViewById(R.id.ll_offline);
        mLLOffLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OffLineSignListActivity.class));
            }
        });
        mTvName = (TextView)findViewById(R.id.tv_name);
        mTvName.setText(PreferenceUtils.getPrefString(this,"userName",""));

        mSubscription = RxBus.getInstance().toObservable(ReLoginEvent.class)
                .subscribe(new Action1<ReLoginEvent>() {
                    @Override
                    public void call(ReLoginEvent reLoginEvent) {

                        Observable.timer(500, TimeUnit.MILLISECONDS).compose(TransformUtils.<Object>defaultSchedulers())
                                .subscribe(new Observer<Object>() {
                                    @Override
                                    public void onCompleted() {
                                        if(mAlertDialog == null){
                                            mAlertDialog = DialogUtils.create(App.getActivity());
                                            mAlertDialog.show(new AlertDialogListener() {
                                                @Override
                                                public void onConFirm() {
                                                    mAlertDialog.dismiss();
                                                    mAlertDialog = null;
                                                    doLogOut();
                                                }

                                                @Override
                                                public void onCancel() {

                                                }
                                            }, "账户异常", "检测到您的账户在其他地方登录,请重新登录", "重新登录");


                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Object data) {

                                    }

                                });





                    }
                });
    }

    private void updateRecycleView(CalendarBean bean){

        ArrayList<CalendarData> lessons = bean.getLessons();
        if(lessons == null || lessons.size() == 0){
            dataSource = new ArrayList<CalendarData>();
        }else{
            dataSource = lessons;
        }
        adapter.setData(dataSource);
    }

    private void initRecycleView(){

        recyclerView.setHasFixedSize(true);

        adapter = new NormalRecyclerAdapter(dataSource, this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this,OfflineDetailActivity.class);
                intent.putExtra("id",dataSource.get(position).getId());
                intent.putExtra("isOnlineMode",true);
                startActivity(intent);



            }
        });
        // 设置静默加载模式
//        xRefreshView1.setSilenceLoadMore();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 静默加载模式不能设置footerview
        recyclerView.setAdapter(adapter);
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setAutoLoadMore(false);
        xRefreshView.setPullRefreshEnable(false);
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);
        //设置静默加载时提前加载的item个数
//        xRefreshView1.setPreLoadCount(4);
        //设置Recyclerview的滑动监听
        xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtils.e("onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtils.e("onScrolled");
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initLabel();

    }

    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }



    @Override
    public void onViewCreated(CalendarView view,List<CalendarBean> bean) {

        Log.i("Calendar","onViewCreated year:"+bean.get(bean.size()/2).year+" month:"+bean.get(bean.size()/2).moth+" mCity_id:"+mCity_id);


        if(mCity_id != -1){
            ReservePresenterImpl mReservePresenterImpl = new ReservePresenterImpl(new ReserveInteractorImpl());
            mReservePresenterImpl.attachView(this);
            mReservePresenterImpl.getReserveList(view,bean.get(bean.size()/2).year,bean.get(bean.size()/2).moth,mCity_id);
       }
   }

    @Override
    public void showProgress(int reqType) {
        if(reqType == TYPE_LABEL){
            if(mLoadDialog == null){
                mLoadDialog = DialogUtils.create(this);
                mLoadDialog.show("正在获取数据");
            }

        }
    }

    @Override
    public void hideProgress(int reqType) {
        if(reqType == TYPE_LABEL){
            if(mLoadDialog != null){
                mLoadDialog.dismiss();
                mLoadDialog = null;
            }

        }
    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        if(reqType == TYPE_LABEL){
            if(mLoadDialog != null){
                mLoadDialog.dismiss();
                mLoadDialog = null;
            }
            Toast.makeText(this,"获取城市列表失败:"+msg,Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void getReserveListCompleted(CalendarView view,CalendarEntity entity) {
        List<CalendarBean> src = view.getData();
        List<CalendarBean> des = new ArrayList<>();
        int[] data = CalendarUtil.getYMD(new Date());
        int j = 0;
        int year = 0;
        int month = 0;
        int day = 0;

        if(!TextUtils.isEmpty(Globle.selectedDate)){
             year = Integer.parseInt(Globle.selectedDate.split("-")[0]);
             month = Integer.parseInt(Globle.selectedDate.split("-")[1]);
             day = Integer.parseInt(Globle.selectedDate.split("-")[2]);
        }

        for(int i = 0;i<src.size();i++){

            CalendarBean bean =  src.get(i);

            //忽略开头的上月数据和结尾的下月数据，只修改本月数据
            if(bean.moth == src.get(10).moth){

                String date = entity.getResult().get(j).getDate();
                ArrayList<CalendarData> lesson = entity.getResult().get(j).getLessons();
                //Log.i("Calendar","date:"+date+"bean:"+bean);

                if(lesson == null || lesson.size() == 0){
                    bean.setHasEvent(false);
                    bean.setLessons(null);
                }else{
                    bean.setHasEvent(true);
                    bean.setLessons(lesson);
                }

                j++;

                if(isFirstLoad && data[0] == bean.year && data[1] == bean.moth && data[2] == bean.day){

                    updateRecycleView(bean);
                    isFirstLoad = false;

                }

                if(isShiftCity && year == bean.year && month == bean.moth && day == bean.day ){

                    updateRecycleView(bean);
                    isShiftCity = false;

                }


            }
            des.add(bean);


        }
        view.setData(des);





    }

    @Override
    public void onPageChanged(int position, CalendarBean entity) {
        mTitle.setText(entity.year + "年" + getDisPlayNumber(entity.moth) + "月");
        mCalendarDateView.update();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Globle.selectedDate = "";
        if(mlocationClient != null){
            mlocationClient.onDestroy();
        }
        if(mSubscription!=null)
            mSubscription.unsubscribe();
        Log.i("Calendar","onDestroy");
    }

    //定位模块
    private void startLocation(){

        rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean grant) {
                        if(grant){
                            mlocationClient = new AMapLocationClient(MainActivity.this);
                            //初始化定位参数
                            mLocationOption = new AMapLocationClientOption();

                            //设置定位监听
                            mlocationClient.setLocationListener(MainActivity.this);

                            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
                            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

                            //单次定位
                            mLocationOption.setOnceLocation(true);

                            //设置定位参数
                            mlocationClient.setLocationOption(mLocationOption);

                           //启动定位
                            mlocationClient.startLocation();
                            Log.i("Location","首页权限通过");
                        }else{
                            Log.i("Location","首页权限被拒绝");
                        }

                    }
                });

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //Log.i("Location","onLocationChanged city:"+aMapLocation.getCity());
        //Log.i("Location","onLocationChanged Latitude:"+aMapLocation.getLatitude());
        //Log.i("Location","onLocationChanged Longitude:"+aMapLocation.getLongitude());
        Toast.makeText(this,aMapLocation.getCity(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getLabelViewCompleted(LabelEntity entity) {
        if(entity.getErr() == null){

            ArrayList<LabelEntity.DataEntity.City> list = entity.getResult().getCity();
            for (LabelEntity.DataEntity.City city:
                    list) {
                if(city.getCity_name().equals("上海")){
                    mCity_id = city.getCity_id();
                    city.setSelected(true);
                }
            }
            mCityList = list;

            mCalendarLayout.reCheckIsOpen();
            //Log.i("Calendar","notifyDataSetChanged");
            mCalendarDateView.getAdapter().notifyDataSetChanged();
        }else{
            Toast.makeText(this,"获取城市列表失败",Toast.LENGTH_SHORT).show();
        }
    }
    //创建城市选择列表
    private void createPopWin(){

        mPopupWindow = new CityPopUpWindow(this,mLLLocation,mCityList);
        mPopupWindow.create();
        mPopupWindow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                for (LabelEntity.DataEntity.City city:
                    mCityList) {
                    city.setSelected(false);
                }
                mCityList.get(position).setSelected(true);
                mStation.setText(mCityList.get(position).getCity_name());
                mCity_id = mCityList.get(position).getCity_id();
                isShiftCity = true;
                mCalendarLayout.reCheckIsOpen();
                Log.i("Calendar","notifyDataSetChanged");
                mCalendarDateView.getAdapter().notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onBackPressed() {

        if(menu!=null && menu.isMenuShowing() == true){
            menu.toggle();
            return;
        }

        if(isExit){
            finish();
        }else{
            isExit = true;
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            Observable.timer(2, TimeUnit.SECONDS).compose(TransformUtils.<Object>defaultSchedulers())
                    .subscribe(new Observer<Object>() {
                        @Override
                        public void onCompleted() {
                            isExit = false;
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Object data) {

                        }

                    });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isFirstLoad && mCalendarDateView!=null)
            mCalendarDateView.getAdapter().notifyDataSetChanged();
    }

    private void doLogOut(){

        PreferenceUtils.setPrefString(MainActivity.this,"userName","");
        PreferenceUtils.setPrefString(MainActivity.this,"passWord","");
        PreferenceUtils.setPrefString(MainActivity.this,"openid","");
        PreferenceUtils.setPrefString(MainActivity.this,"code","");

        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
