package com.example.a65667.road.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceListener;
import com.amap.api.trace.TraceLocation;
import com.example.a65667.road.R;
import com.example.a65667.road.bean.PathRecord;
import com.example.a65667.road.utils.ActivityCollectorUtil;
import com.example.a65667.road.utils.CurrentUserInfo;
import com.example.a65667.road.utils.TraceUtil;
import com.github.clans.fab.FloatingActionButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class CrackDetailActivity extends AppCompatActivity implements LocationSource,TraceListener, AMapLocationListener, View.OnClickListener, AMap.OnMarkerClickListener {

    MapView detailMap = null;
    AMap deta_Map;   //地图对象
    MyLocationStyle myLocationStyle;  //定位蓝点
    private AMapLocationClient mapLocationClient = null;  //定位发起
    private AMapLocationClientOption mLocationOption = null;  //定位参数
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;

    private ImageView tc_re;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;
    private TextView tc_name;
    private TextView tc_data;
    private String date = "";
    private String lno = "";
    private JzvdStd jzvdStd;
    private String videoUrl = "http://ishero.net/share/valvideo/";
    private String url = "";
    private boolean isFirstLoc = true;
    private String lduration = "";

    private List<LatLng> mOriginLatLngList = new ArrayList<>();   //原始轨迹
    private List<LatLng> mGraspLatLngList = new ArrayList<>();    //纠偏轨迹

    private ExecutorService mThreadPool;
    private Marker mGraspStartMarker, mGraspEndMarker, mGraspRoleMarker;
    private String dataid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crack_detail);
        ActivityCollectorUtil.addActivity(this);

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

//        //设置定位
//        deta_Map.setLocationSource(this);  //定位监听
//        myLocationStyle = new MyLocationStyle();
//        myLocationStyle.strokeColor(android.R.color.holo_blue_light);  //设置定位蓝点精确度圆圈的边框
//        myLocationStyle.radiusFillColor(android.R.color.holo_blue_light);  //设置定位蓝点精确度圆圈的填充
//        deta_Map.setMyLocationStyle(myLocationStyle);
//        deta_Map.setTrafficEnabled( true );
//        deta_Map.setMyLocationEnabled(true);    //显示定位蓝点
//        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
//        deta_Map.setLocationSource(this);//设置了定位的监听
//
//        location();

        init();

        getLng();
    }

    private void init(){

        tc_re = (ImageView)findViewById(R.id.tc_re);
        tc_data = (TextView) findViewById(R.id.tc_data);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        jzvdStd = (JzvdStd) findViewById(R.id.jz_video);
        tc_name = (TextView) findViewById(R.id.tc_name);
        tc_name.setText(CurrentUserInfo.name);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        url = getIntent().getStringExtra("video");
        lduration = getIntent().getStringExtra("lduration");

        jzvdStd.setUp(url, "路面回放", JzvdStd.SCREEN_NORMAL);

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmoothMoveMarker smoothMoveMarker = new SmoothMoveMarker(deta_Map);
                        smoothMoveMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_2));

                //设置滑动的轨迹左边点
                smoothMoveMarker.setPoints(mOriginLatLngList);
                //设置滑动总时间
                smoothMoveMarker.setTotalDuration(Integer.parseInt(lduration));
                //开始滑动
                smoothMoveMarker.startSmoothMove();

                jzvdStd.startVideo();

            }
        });

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

        lno = getIntent().getStringExtra("lno");

        date = getIntent().getStringExtra("tvData");
        tc_data.setText(date);

//        List<String> sourceVideo = new ArrayList<>();

//        OkGo.<String>post("http://39.105.172.22:9596/showCrackDetail")
//                .tag(this)
//                .params("lno", lno)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        Log.e("crack_detail", response.body());
//                        JSONArray jsonArray = JSON.parseArray(response.body());
//                        for (int i=0; i<jsonArray.size(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            sourceVideo.add(jsonObject.getString("sourceVideo"));
//                        }
//                        videoUrl = "http://ishero.net/hls/" + sourceVideo.get(0);
//                        Log.e("videoUrl", videoUrl);
//                        if (!videoUrl.equals("")) {
//                            jzvdStd.setUp(videoUrl, "路面回放", JzvdStd.SCREEN_NORMAL);
//                        }
//                    }
//                });
    }

    private void addPolyline() {
        deta_Map.addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.grasp_trace_line))
                .addAll(mOriginLatLngList)
                .useGradient(true)
                .width(30));
    }

    private void mapControl() {
        deta_Map.moveCamera(CameraUpdateFactory.zoomBy(16));
        setUpMap();
        deta_Map.moveCamera(CameraUpdateFactory.changeLatLng(mOriginLatLngList.get(0)));
        int threadPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 3;
        mThreadPool = Executors.newFixedThreadPool(threadPoolSize);
        showLine();
    }

    private void showLine() {
        addPolyline();
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i=0; i < mOriginLatLngList.size(); i++) {
            b.include(mOriginLatLngList.get(i));
        }
        LatLng startPoint = mOriginLatLngList.get(0);
        LatLng endPoint = mOriginLatLngList.get(mOriginLatLngList.size() - 1);
        mGraspStartMarker = deta_Map.addMarker(new MarkerOptions()
                .position(startPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        mGraspEndMarker = deta_Map.addMarker(new MarkerOptions()
                .position(endPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
    }

    private void getLng() {
        PathRecord pathRecord = new PathRecord();
        dataid = lno;
        Log.e("dataid", dataid);

        List<String> xGPS = new ArrayList<>();
        List<String> yGPS = new ArrayList<>();

        OkGo.<String>post("http://39.105.172.22:9596/showTrackdetail")
                .params("lno", dataid)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("trackDetail", response.body());
                        response.toString();
                        JSONArray jsonArray = JSON.parseArray(response.body());
                        for (int i=0; i<jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (!jsonObject.getString("lat").equals("0.0")) {
                                xGPS.add(jsonObject.getString("lat"));
                                yGPS.add(jsonObject.getString("lon"));
                                mOriginLatLngList.add(new LatLng(Double.valueOf(xGPS.get(i)), Double.valueOf(yGPS.get(i))));
////                                Log.e("mOrigin", String.valueOf(mOriginLatLngList.get(i).latitude));
                                pathRecord.addpoint(TraceUtil.parseLocation(xGPS.get(i) + ", " + yGPS.get(i)));
                            }
                        }
                        List<AMapLocation> recordList = pathRecord.getPathline();
                        List<TraceLocation> mGraspTraceLocationList = TraceUtil.parseTraceLocationList(recordList);
                        LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
                        mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList, LBSTraceClient.TYPE_AMAP,CrackDetailActivity.this);
                        mapControl();
                    }
                });

        OkGo.<String>post("http://39.105.172.22:9596/showCrackDetail")
                .params("lno", dataid)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("crackDetail", response.body());
                        response.toString();
                        JSONArray jsonArray = JSON.parseArray(response.body());
                        for (int i=0; i<jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (!jsonObject.getString("lat").equals("0.0")) {
                                add_point_in_map(new LatLng(Double.valueOf(jsonObject.getString("lon")), Double.valueOf(jsonObject.getString("lat"))), R.drawable.ic_warn_sew);
                            }
                        }
                    }
                });

//        if (dataid.equals("21_0")) {
//            mOriginLatLngList.add(new LatLng(39.9890197102, 116.4206492901));
//            mOriginLatLngList.add(new LatLng(39.9890936910, 116.4206331968));
//            mOriginLatLngList.add(new LatLng(39.9890484805, 116.4206385612));
//            mOriginLatLngList.add(new LatLng(39.9889375093, 116.4206331968));
//            mOriginLatLngList.add(new LatLng(39.9888429782, 116.4206278324));
//            mOriginLatLngList.add(new LatLng(39.9888429782, 116.4206278324));
//            mOriginLatLngList.add(new LatLng(39.9887525569, 116.4206385612));
//            mOriginLatLngList.add(new LatLng(39.9886662456, 116.4206278324));
//            mOriginLatLngList.add(new LatLng(39.9885963745, 116.4206278324));
//            mOriginLatLngList.add(new LatLng(39.9885141730, 116.4206278324));
//            mOriginLatLngList.add(new LatLng(39.9884319715, 116.4206331968));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9890197102, 116.4206492901"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9890936910, 116.4206331968"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9890484805, 116.4206385612"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9889375093, 116.4206331968"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9888429782, 116.4206278324"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9888429782, 116.4206278324"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9887525569, 116.4206385612"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9886662456, 116.4206278324"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9885963745, 116.4206278324"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9885141730, 116.4206278324"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9884319715, 116.4206331968"));
//            List<AMapLocation> recordList = pathRecord.getPathline();
//            List<TraceLocation> mGraspTraceLocationList = TraceUtil.parseTraceLocationList(recordList);
//            LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
//            mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList, LBSTraceClient.TYPE_AMAP,this);
//            add_point_in_map(new LatLng(39.9890197102, 116.4206492901), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(39.9889375093, 116.4206331968), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(39.9885963745, 116.4206278324), R.drawable.ic_warn_sew);
//
//        }
//        else if (dataid.equals("21_2")) {
//            mOriginLatLngList.add(new LatLng(39.9891183512, 116.4138525724));
//            mOriginLatLngList.add(new LatLng(39.9891347914, 116.4137881994));
//            mOriginLatLngList.add(new LatLng(39.9892005519, 116.4138203859));
//            mOriginLatLngList.add(new LatLng(39.9893074127, 116.4139115810));
//            mOriginLatLngList.add(new LatLng(39.9893608430, 116.4139491320));
//            mOriginLatLngList.add(new LatLng(39.9894348234, 116.4140027761));
//            mOriginLatLngList.add(new LatLng(39.9894800335, 116.4140188694));
//            mOriginLatLngList.add(new LatLng(39.9895293537, 116.4140456915));
//            mOriginLatLngList.add(new LatLng(39.9895663438, 116.4140349627));
//            mOriginLatLngList.add(new LatLng(39.9895786739, 116.4139169455));
//            mOriginLatLngList.add(new LatLng(39.9896156639, 116.4138257504));
//            mOriginLatLngList.add(new LatLng(39.9896074439, 116.4137023687));
//            mOriginLatLngList.add(new LatLng(39.9896115539, 116.4135414362));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9891183512,116.4138525724"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9891347914,116.4137881994"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9892005519,116.4138203859"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9893074127,116.4139115810"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9893608430,116.4139491320"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9894348234,116.4140027761"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9894800335,116.4140188694"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9895293537,116.4140456915"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9895663438,116.4140349627"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9895786739,116.4139169455"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9896156639,116.4138257504"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9896074439,116.4137023687"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9896115539,116.4135414362"));
//
//            List<AMapLocation> recordList = pathRecord.getPathline();
//            List<TraceLocation> mGraspTraceLocationList = TraceUtil.parseTraceLocationList(recordList);
//            LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
//            mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList, LBSTraceClient.TYPE_AMAP, this);
//
//            add_point_in_map(new LatLng(39.9891183512, 116.4138525724), R.drawable.ic_red_sew);
//            add_point_in_map(new LatLng(39.9891347914, 116.4137881994), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(39.9894348234, 116.4140027761), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(39.9895786739, 116.4139169455), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(39.9896115539, 116.4135414362), R.drawable.ic_warn_sew);
//
//        } else if (dataid.equals("3_0")) {
//            mOriginLatLngList.add(new LatLng(39.9885799342, 116.4206385612));
//            mOriginLatLngList.add(new LatLng(39.9886703557, 116.4206385612));
//            mOriginLatLngList.add(new LatLng(39.9887114563, 116.4206278324));
//            mOriginLatLngList.add(new LatLng(39.9887854374, 116.4206171036));
//            mOriginLatLngList.add(new LatLng(39.9888183178, 116.4206171036));
//            mOriginLatLngList.add(new LatLng(39.9888265379, 116.4206385612));
//            mOriginLatLngList.add(new LatLng(39.9889909399, 116.4206278324));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9885799342,116.4206385612"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9886703557,116.4206385612"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9887114563,116.4206278324"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9887854374,116.4206171036"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9888183178,116.4206171036"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9888265379,116.4206385612"));
//            pathRecord.addpoint(TraceUtil.parseLocation("39.9889909399,116.4206278324"));
//
//            List<AMapLocation> recordList = pathRecord.getPathline();
//            List<TraceLocation> mGraspTraceLocationList = TraceUtil.parseTraceLocationList(recordList);
//            LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
//            mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList, LBSTraceClient.TYPE_AMAP, this);
//
//            add_point_in_map(new LatLng(39.9886703557, 116.4206385612), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(39.9887854374, 116.4206171036), R.drawable.ic_warn_sew);
//
//        } else if (dataid.equals("4_0")) {
//            mOriginLatLngList.add(new LatLng(38.0167389718, 112.4465274811));
//            mOriginLatLngList.add(new LatLng(38.0167643293, 112.4467635155));
//            mOriginLatLngList.add(new LatLng(38.0167601030, 112.4469137192));
//            mOriginLatLngList.add(new LatLng(38.0167770081, 112.4471014738));
//            mOriginLatLngList.add(new LatLng(38.0168150443, 112.4474501610));
//            mOriginLatLngList.add(new LatLng(38.0168488543, 112.4478954077));
//            mOriginLatLngList.add(new LatLng(38.0168953430, 112.4487966299));
//            mOriginLatLngList.add(new LatLng(38.0169249267, 112.4492740631));
//            mOriginLatLngList.add(new LatLng(38.0169587367, 112.4498480558));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0167389718,112.4465274811"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0167643293,112.4467635155"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0167601030,112.4469137192"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0167770081,112.4471014738"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0168150443,112.4474501610"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0168488543,112.4478954077"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0168953430,112.4487966299"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0169249267,112.4492740631"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0169587367,112.4498480558"));
//
//            List<AMapLocation> recordList = pathRecord.getPathline();
//            List<TraceLocation> mGraspTraceLocationList = TraceUtil.parseTraceLocationList(recordList);
//            LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
//            mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList, LBSTraceClient.TYPE_AMAP, this);
//
//            add_point_in_map(new LatLng(38.0167643293, 112.4467635155), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(38.0167601030, 112.4469137192), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(38.0168150443, 112.4474501610), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(38.0168953430, 112.4487966299), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(38.0169587367, 112.4498480558), R.drawable.ic_warn_sew);
//
//        } else if (dataid.equals("5")) {
//            mOriginLatLngList.add(new LatLng(38.0040295241, 112.4446821213));
//            mOriginLatLngList.add(new LatLng(38.0029896780, 112.4451810122));
//            mOriginLatLngList.add(new LatLng(38.0026050157, 112.4453687668));
//            mOriginLatLngList.add(new LatLng(38.0018272309, 112.4457228184));
//            mOriginLatLngList.add(new LatLng(38.0010663465, 112.4460929632));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0040295241,112.4446821213"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0029896780,112.4451810122"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0026050157,112.4453687668"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0018272309,112.4457228184"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0010663465,112.4460929632"));
//            List<AMapLocation> recordList = pathRecord.getPathline();
//            List<TraceLocation> mGraspTraceLocationList = TraceUtil.parseTraceLocationList(recordList);
//            LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
//            mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList, LBSTraceClient.TYPE_AMAP, this);
//
//            add_point_in_map(new LatLng(38.0040295241, 112.4446821213), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(38.0026050157, 112.4453687668), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(38.0010663465, 112.4460929632), R.drawable.ic_warn_sew);
//
//        } else if (dataid.equals("6")) {
//            mOriginLatLngList.add(new LatLng(38.0068023751, 112.4434214830));
//            mOriginLatLngList.add(new LatLng(38.0070602110, 112.4432712793));
//            mOriginLatLngList.add(new LatLng(38.0073180461, 112.4431639910));
//            mOriginLatLngList.add(new LatLng(38.0077026837, 112.4429923296));
//            mOriginLatLngList.add(new LatLng(38.0081253600, 112.4429011345));
//            mOriginLatLngList.add(new LatLng(38.0084550458, 112.4428206682));
//            mOriginLatLngList.add(new LatLng(38.0087931835, 112.4427723885));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0068023751,112.4434214830"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0070602110,112.4432712793"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0073180461,112.4431639910"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0077026837,112.4429923296"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0081253600,112.4429011345"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0084550458,112.4428206682"));
//            pathRecord.addpoint(TraceUtil.parseLocation("38.0087931835,112.4427723885"));
//
//            List<AMapLocation> recordList = pathRecord.getPathline();
//            List<TraceLocation> mGraspTraceLocationList = TraceUtil.parseTraceLocationList(recordList);
//            LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
//            mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList, LBSTraceClient.TYPE_AMAP, this);
//
//            add_point_in_map(new LatLng(38.0068023751, 112.4434214830), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(38.0073180461, 112.4431639910), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(38.0077026837, 112.4429923296), R.drawable.ic_warn_sew);
//            add_point_in_map(new LatLng(38.0084550458, 112.4428206682), R.drawable.ic_warn_sew);
//
//        }

        add_point_in_map(new LatLng(38.0140510249, 112.4519723654), R.drawable.ic_red_sew);
        add_point_in_map(new LatLng(38.0151794674, 112.4518007040), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0142454395, 112.4519455433), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0154288213, 112.4531257153), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0154710846, 112.4545526505), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0168530806, 112.4545258284), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0170982025, 112.4545258284), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0171235599, 112.4537909031), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0170855238, 112.4525517225), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0170263565, 112.4515432119), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0169883203, 112.4508565664), R.drawable.ic_warn_sew);
        add_point_in_map(new LatLng(38.0154415003, 112.4531847239), R.drawable.ic_warn_hole);
        add_point_in_map(new LatLng(38.0154372740, 112.4525624514), R.drawable.ic_warn_hole);
    }

    private void add_point_in_map(LatLng lng, int id) {
        MarkerOptions markerOptions = new MarkerOptions();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        markerOptions.icon(bitmapDescriptor);
        markerOptions.position(lng);
        markerOptions.draggable(false);
        deta_Map.addMarker(markerOptions);
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

    private void setUpMap() {
        deta_Map.setLocationSource(this);
        deta_Map.getUiSettings().setMyLocationButtonEnabled(false);
        deta_Map.setMyLocationEnabled(true);
        deta_Map.setOnMarkerClickListener(this);
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
        Jzvd.releaseAllVideos();
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
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

//        if (aMapLocation != null) {
//            if (aMapLocation.getErrorCode() == 0) {
//                if (isFirstLoc) {
//                    //将地图移动到定位点
//                    deta_Map.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
//                    //点击定位按钮 能够将地图的中心移动到定位点
//                    mListener.onLocationChanged(aMapLocation);
//                    isFirstLoc = false;
//                }
//
//                //可在其中解析amapLocation获取相应内容。
//                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
//                aMapLocation.getLatitude();//获取纬度
//                aMapLocation.getLongitude();//获取 经度
//                aMapLocation.getAccuracy();//获取精度信息
//                aMapLocation.getSpeed();
//                aMapLocation.getBearing();
//
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(aMapLocation.getTime());
//                df.format(date);//定位时间
//
//            }else {
//                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                Log.e("AmapError","location Error, ErrCode:"
//                        + aMapLocation.getErrorCode() + ", errInfo:"
//                        + aMapLocation.getErrorInfo());
//            }
//        }

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
//        mListener = onLocationChangedListener;
//        if(mapLocationClient == null){
//            //初始化定位
//            mapLocationClient = new AMapLocationClient(this);
//            //初始化定位参数
//            mLocationOption = new AMapLocationClientOption();
//            //设置定位回调监听
//            mapLocationClient.setLocationListener(this);
//            //设置高精度定位模式
//            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//            //设置定位参数
//            mapLocationClient.setLocationOption(mLocationOption);
//            mapLocationClient.startLocation();//启动定位
//        }
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

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
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

    @Override
    public void onRequestFailed(int i, String s) {

    }

    @Override
    public void onTraceProcessing(int i, int i1, List<LatLng> list) {

    }

    @Override
    public void onFinished(int i, List<LatLng> list, int i1, int i2) {
        mGraspLatLngList = list;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        startActivity(new Intent(this, CrackPictureActivity.class));
        return false;
    }
}
