package com.example.a65667.road.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.a65667.road.R;
import com.github.clans.fab.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CrackDetailActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, View.OnClickListener {

    MapView detailMap = null;
    AMap deta_Map;   //地图对象
    MyLocationStyle myLocationStyle;  //定位蓝点
    private AMapLocationClient mapLocationClient = null;  //定位发起
    private AMapLocationClientOption mLocationOption = null;  //定位参数
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;

    private ImageView tc_re;
    private FloatingActionButton fab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crack_detail);

        detailMap = (MapView)findViewById(R.id.detail_map);
        detailMap.onCreate(savedInstanceState);

        //申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.setStatusBarColor( getResources().getColor( R.color.white) );
            window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR  );
        }
        requestPermissions();

        deta_Map = detailMap.getMap();

        //设置定位
        deta_Map.setLocationSource(this);  //定位监听
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(android.R.color.holo_blue_light);  //设置定位蓝点精确度圆圈的边框
        myLocationStyle.radiusFillColor(android.R.color.holo_blue_light);  //设置定位蓝点精确度圆圈的填充
        deta_Map.setMyLocationStyle(myLocationStyle);
        deta_Map.setTrafficEnabled( true );
        deta_Map.setMyLocationEnabled(true);    //显示定位蓝点
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        deta_Map.setLocationSource(this);//设置了定位的监听
        location();

        init();

    }

    private void init(){

        tc_re = (ImageView)findViewById(R.id.tc_re);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        tc_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrackDetailActivity.this, CrackListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void location(){
        mapLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mapLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为High_Accuracy高精度模式，Battery_Saving为低功耗模式，Sensors是仅设备模式;
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置，默认为false
        mLocationOption.setMockEnable(true);
        mLocationOption.setInterval(2000);
        mapLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailMap.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        detailMap.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        detailMap.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tc_re:
                finish();
                break;
            case R.id.fab2:
            {
                Intent intent = new Intent(CrackDetailActivity.this, CrackListActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {

                //可在其中解析amapLocation获取相应内容。
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取 经度
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getSpeed();
                aMapLocation.getBearing();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if(mapLocationClient == null){
            //初始化定位
            mapLocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mapLocationClient.setLocationListener(this);
            //设置高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mapLocationClient.setLocationOption(mLocationOption);
            mapLocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if(mapLocationClient != null){
            mapLocationClient.stopLocation();
            mapLocationClient.onDestroy();
        }
        mapLocationClient = null;
    }

    //权限申请
    private void requestPermissions() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,Manifest.permission.CAMERA};
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for(int i = 0;i < permissions.length;i ++) {
                    int permission = ActivityCompat.checkSelfPermission( this, permissions[i]);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( this,permissions, 0x0010 );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
