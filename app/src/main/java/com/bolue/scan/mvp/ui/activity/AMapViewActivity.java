package com.bolue.scan.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bolue.scan.R;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.zxing.activity.CaptureActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import rx.functions.Action1;

public class AMapViewActivity extends BaseActivity {


    @BindView(R.id.map)
    MapView mMapView;

    private AMap aMap;

    RxPermissions rxPermissions;




    @Override
    public int getLayoutId() {
        return R.layout.activity_amap_view;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(
                         Manifest.permission.ACCESS_FINE_LOCATION,
                         Manifest.permission.WRITE_EXTERNAL_STORAGE,
                         Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean grant) {
                        if(grant){
                            aMap = mMapView.getMap();
                            LatLng ll = new LatLng(31.217735,121.357759);
                            aMap.addMarker(new MarkerOptions().position(ll).title("大酒店"));
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 18));
                          Log.i("Location","权限通过");
                        }else{
                            Log.i("Location","权限被拒绝");
                        }

                    }
                });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        initViews();

   }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

    }
    private void addMarker(double lat, double lon, String title,
                           String snippet, BitmapDescriptor icon) {
        MarkerOptions markerOption = new MarkerOptions();
        LatLng markerPoint = new LatLng(lat, lon);
        markerOption.position(markerPoint);
        markerOption.title(title).snippet(snippet);
        markerOption.icon(icon);
        aMap.addMarker(markerOption);
    }

    /**
     * 添加模拟位置地图展示
     *
     * @param snippet
     */
    private void addTestLocationMarker(String snippet) {
//        aMap.clear();
//        String title = "我的位置";
//        addMarker(POI_POINT.getLatitude(), POI_POINT.getLongitude(), title,
//                snippet,
//                BitmapDescriptorFactory
//                        .fromResource(R.drawable.location_marker));
//        LatLng latlng = new LatLng(POI_POINT.getLatitude(),
//                POI_POINT.getLongitude());
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));
    }
}
