package com.example.a65667.road.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

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
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.trace.TraceListener;
import com.example.a65667.road.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

public class CrackMapActivity extends AutoLayoutActivity implements LocationSource, AMapLocationListener, TraceListener, AMap.OnMarkerClickListener {

    private MapView mapView;
    private ImageButton backButton, localMeButton;
    public AMapLocationClient mapLocationClient;
    public AMapLocationClientOption mapLocationClientOption = null;
    private LocationSource.OnLocationChangedListener mListener = null;
    LatLng currentLng = new LatLng(38.0335575326, 112.5519275665);
    private AMap map;
    private PolylineOptions mPolyoptions;
    private Polyline mpolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crack_map);
        mapView = findViewById(R.id.crack_map);
        mapView.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        backButton = findViewById(R.id.ib_back_map);
        localMeButton = findViewById(R.id.ib_local_me);

        if (map == null) {
            map = mapView.getMap();
            map.moveCamera(CameraUpdateFactory.changeLatLng(currentLng));
            map.moveCamera(CameraUpdateFactory.zoomBy(5));
            setUpMap();
        }

        initPolyLine();

//        add_point_in_map(new LatLng(39.9890197102, 116.4206492901), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(39.9889375093, 116.4206331968), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(39.9885963745, 116.4206278324), R.drawable.ic_warn_sew);

//        add_point_in_map(new LatLng(39.9891183512, 116.4138525724), R.drawable.ic_red_sew);
//        add_point_in_map(new LatLng(39.9891347914, 116.4137881994), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(39.9894348234, 116.4140027761), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(39.9895786739, 116.4139169455), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(39.9896115539, 116.4135414362), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(39.9886703557, 116.4206385612), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(39.9887854374, 116.4206171036), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(38.0167643293, 112.4467635155), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(38.0167601030, 112.4469137192), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(38.0168150443, 112.4474501610), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(38.0168953430, 112.4487966299), R.drawable.ic_warn_sew);
//        add_point_in_map(new LatLng(38.0169587367, 112.4498480558), R.drawable.ic_warn_sew);

        add_point_in_map(new LatLng(38.0040295241, 112.4446821213), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0026050157, 112.4453687668), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0010663465, 112.4460929632), R.drawable.ic_warn_sew);

        add_point_in_map(new LatLng(38.0068023751, 112.4434214830), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0073180461, 112.4431639910), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0077026837, 112.4429923296), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0084550458, 112.4428206682), R.drawable.ic_warn_sew);

        add_point_in_map(new LatLng(38.0335575326,112.5519275665), R.drawable.ic_red_sew);
        add_point_in_map(new LatLng(38.0301730019,112.5488591194), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0297039722,112.5517344475), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0291969097,112.5565195084), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0301138452,112.5473409891), R.drawable.ic_red_sew);
        add_point_in_map(new LatLng(38.0321082458,112.5520670414), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0321082458,112.5520670414), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0342800526,112.5516164303), R.drawable.ic_warn_hole);
        add_point_in_map(new LatLng(38.0374869413,112.5503289700), R.drawable.ic_warn_hole);
        add_point_in_map(new LatLng(38.0319857101,112.5528502464), R.drawable.ic_warn_hole);

    }

    private void setUpMap() {
        map.setLocationSource(this);// 设置定位监听
        map.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        map.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_map_mark);
//        BitmapDescriptor myLocationIcon = BitmapDescriptorFactory.fromBitmap(bitmap);
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(myLocationIcon);
//        myLocationStyle.interval(2000);
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
//        myLocationStyle.strokeColor(android.R.color.holo_blue_light);//设置定位蓝点精度圆圈的边框颜色的方法。
//        myLocationStyle.radiusFillColor(android.R.color.holo_blue_light);//设置定位蓝点精度圆圈的填充颜色的方法。
//        mAMap.setMyLocationStyle(myLocationStyle);
        map.setMyLocationEnabled(true);// 是否可触
        map.setOnMarkerClickListener(this);
    }

    private void add_point_in_map(LatLng lng, int id) {
        MarkerOptions markerOptions = new MarkerOptions();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        markerOptions.icon(bitmapDescriptor);
        markerOptions.position(lng);
        markerOptions.draggable(false);
        map.addMarker(markerOptions);
    }

    private void initPolyLine() {
        mPolyoptions = new PolylineOptions();
        mPolyoptions.width(10f);
        mPolyoptions.color(Color.GREEN);
    }

    private void local_me() {
        // 定位按钮事件
        if (map == null) {
            map = mapView.getMap();
            setUpMap();
            map.moveCamera(CameraUpdateFactory.changeLatLng(currentLng));
        }
    }

    private void startlocation() {
        if (mapLocationClient == null) {
            mapLocationClient = new AMapLocationClient(this);
            mapLocationClientOption = new AMapLocationClientOption();
            // 设置定位监听
            mapLocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mapLocationClientOption.setInterval(200000);
            // 设置定位参数
            mapLocationClient.setLocationOption(mapLocationClientOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mapLocationClient.startLocation();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        // 当定位源获取的位置信息发生变化时回调此接口
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
                LatLng mylocation = new LatLng(aMapLocation.getLatitude(),
                        aMapLocation.getLongitude());
                currentLng = mylocation;
                mPolyoptions.add(mylocation);
                map.addPolyline(mPolyoptions);

//                mAMap.moveCamera(CameraUpdateFactory.changeLatLng(mylocation));

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": "
                        + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        startActivity(new Intent(this, CrackPictureActivity.class));
        return false;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        //startlocation();
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onRequestFailed(int i, String s) {

    }

    @Override
    public void onTraceProcessing(int i, int i1, List<LatLng> list) {

    }

    @Override
    public void onFinished(int i, List<LatLng> list, int i1, int i2) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
