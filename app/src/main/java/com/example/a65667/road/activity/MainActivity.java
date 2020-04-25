package com.example.a65667.road.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.example.a65667.road.R;
import com.example.a65667.road.bean.CurrentLocalGPS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, View.OnClickListener {

    MapView mMapView = null;
    AMap aMap;   //地图对象
    MyLocationStyle myLocationStyle;  //定位蓝点
    private AMapLocationClient mapLocationClient = null;  //定位发起
    private AMapLocationClientOption mLocationOption = null;  //定位参数
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private AMapLocation priLocation;
    private PolylineOptions mPolyoptions, tracePolytion;
    private Polyline mPolyline;
    private double distance;
    private static double  xGPS = 0, yGPS = 0;
     private Marker markerOwner;  //自己的位置

    private List<Double> gpsx;
    private List<Double> gpsy;
    private List<String> time;
    private List<String> roadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView)findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);

        //申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.setStatusBarColor( getResources().getColor( R.color.white) );
            window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR  );
        }
        requestPermissions();

        aMap = mMapView.getMap();

        //设置定位
        aMap.setLocationSource(this);  //定位监听
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(android.R.color.holo_blue_light);  //设置定位蓝点精确度圆圈的边框
        myLocationStyle.radiusFillColor(android.R.color.holo_blue_light);  //设置定位蓝点精确度圆圈的填充
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setTrafficEnabled( true );
        aMap.setMyLocationEnabled(true);    //显示定位蓝点
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);

        aMap.getUiSettings().setCompassEnabled(true); //显示指南针

        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        aMap.setLocationSource(this);//设置了定位的监听
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);

        location();

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
        mLocationOption.setInterval(1000);
        mapLocationClient.startLocation();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                     //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }

                xGPS = aMapLocation.getLatitude();
                yGPS = aMapLocation.getLongitude();
                CurrentLocalGPS.Latitude = xGPS;
                CurrentLocalGPS.Longitude = yGPS;

                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                //点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(aMapLocation);

                priLocation = aMapLocation;
                drawLines(aMapLocation);
                distance += distance;
                //Toast.makeText(MainActivity.this,"经纬度" + distance + "KM", Toast.LENGTH_SHORT).show();

                //可在其中解析amapLocation获取相应内容。
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                gpsy.add(aMapLocation.getLatitude());//获取纬度
                gpsx.add(aMapLocation.getLongitude());//获取 经度
                roadList.add(aMapLocation.getStreet());   //获取街道信息

                 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                time.add(df.format(new Date()));   //获取定位时间

            }
            else {
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
        mLocationOption = null;
    }

    private void redrawline() {
        if(mPolyline.getPoints().size() > 1){
            if (mPolyline != null) {
                mPolyline.setColor(R.color.colorAccent);
                mPolyline.setPoints(mPolyline.getPoints());
            } else {
                mPolyline = aMap.addPolyline(mPolyoptions);
            }
        }
    }

    public void drawLines(AMapLocation curLocation) {
        if (priLocation == null) {
            return ;
        }
        PolylineOptions options = new PolylineOptions();
        options.add(new LatLng(priLocation.getLatitude(), priLocation.getLongitude()));
        //当前的经纬度
        options.add(new LatLng(curLocation.getLatitude(), priLocation.getLongitude()));
        options.width(15).geodesic(true).color(Color.GREEN);
        mPolyline = aMap.addPolyline(options);
        distance = AMapUtils.calculateLineDistance(
                new LatLng(priLocation.getLatitude(), priLocation.getLongitude()),
                new LatLng(curLocation.getLatitude(), curLocation.getLongitude())
        );
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
