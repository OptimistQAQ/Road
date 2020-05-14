package com.example.a65667.road.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.example.a65667.road.Item.CameraRecordItem;
import com.example.a65667.road.Item.MineRecordItem;
import com.example.a65667.road.Item.MineTopItem;
import com.example.a65667.road.activity.CamActivity;
import com.example.a65667.road.activity.MainActivity;
import com.example.a65667.road.R;
import com.example.a65667.road.activity.SignInActivity;
import com.example.a65667.road.binder.CameraRecordItemViewBinder;
import com.example.a65667.road.binder.MineRecordItemViewBinder;
import com.example.a65667.road.utils.ActivityCollectorUtil;
import com.github.clans.fab.FloatingActionButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import cn.nodemedia.pusher.view.SourcePushActivity;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class CameraFragment extends Fragment implements View.OnClickListener, LocationSource, AMapLocationListener {

    private View root;
    private RecyclerView recyclerView;

    private MultiTypeAdapter mAdapter;
    private Items mItems;

    private ImageView img_map;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

//    MapView mMapView = null;
//    AMap aMap;   //地图对象
//    MyLocationStyle myLocationStyle;  //定位蓝点
//    private AMapLocationClient mapLocationClient = null;  //定位发起
//    private AMapLocationClientOption mLocationOption = null;  //定位参数
//    //声明mListener对象，定位监听器
//    private OnLocationChangedListener mListener = null;

    public CameraFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.camera_fragment, container, false);
        initView();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mMapView = (MapView) root.findViewById(R.id.map_view1);
//        mMapView.onCreate(savedInstanceState);
//
//        aMap = mMapView.getMap();
    }

    private void initView(){
        initMenu();

        List<String> dataTime = new ArrayList<>();
        List<String> lastTime = new ArrayList<>();
        List<String> holeCount = new ArrayList<>();
        List<String> crackCount = new ArrayList<>();
        List<String> travelWay = new ArrayList<>();
        List<String> lno = new ArrayList<>();
        List<String> videoUrl = new ArrayList<>();

        dataTime.add("2020年3月6日");
        lastTime.add("14分钟");
        holeCount.add("20公里");
        crackCount.add("3个大问题");
        travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");
        videoUrl.add("cc1d3666-fe5994ae.mov");
        lno.add("1");

        dataTime.add("2020年3月10日");
        lastTime.add("20分钟");
        holeCount.add("33公里");
        crackCount.add("9个大问题");
        travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");
        videoUrl.add("cc8d5a50-92a79d46.mov");
        lno.add("2");

        dataTime.add("2020年3月15日");
        lastTime.add("14分钟");
        holeCount.add("20公里");
        crackCount.add("5个大问题");
        travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");
        videoUrl.add("cc9d2250-fba65525.mov");
        lno.add("3");

        dataTime.add("2020年3月20日");
        lastTime.add("24分钟");
        holeCount.add("56公里");
        crackCount.add("11个大问题");
        travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");
        videoUrl.add("ccd901f5-bfabffd7.mov");
        lno.add("4");

        recyclerView = root.findViewById(R.id.rv_video);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(CameraRecordItem.class, new CameraRecordItemViewBinder());
        recyclerView.setAdapter(mAdapter);

        mItems = new Items();
        mItems.add(new CameraRecordItem());
        for (int i = 0; i < dataTime.size(); i++) {
            CameraRecordItem cameraRecordItem = new CameraRecordItem();
            cameraRecordItem.setDataTime(dataTime.get(i));
            cameraRecordItem.setLastTime(lastTime.get(i));
            cameraRecordItem.setHoleCount(holeCount.get(i));
            cameraRecordItem.setCrackCount(crackCount.get(i));
            cameraRecordItem.setTravel(travelWay.get(i));
            cameraRecordItem.setLno(lno.get(i));
            cameraRecordItem.setVideoUrl(videoUrl.get(i));
            mItems.add(cameraRecordItem);
        }
        mAdapter.setItems(mItems);
        mAdapter.notifyDataSetChanged();

//        OkGo.<String>post("http://39.105.172.22:9596/showLine")
//                .tag(this)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        mItems = new Items();
//                        mItems.add(new CameraRecordItem());
//                        Log.e("camera_fragment", response.body());
//                        response.toString();
//                        JSONArray jsonArray = JSON.parseArray(response.body());
//                        for (int i=0; i<jsonArray.size(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            if (!jsonObject.getString("lduration").equals("0")) {
//                                dataTime.add(jsonObject.getString("lbeginDate"));
//                                lastTime.add(jsonObject.getString("lduration") + "分钟");
//                                holeCount.add("20公里");
//                                crackCount.add(jsonObject.getInteger("uno").toString() + "个大问题");
//                                travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");
//                                lno.add(jsonObject.getString("lno"));
//                            }
//                        }
//                        for (int i = 0; i < lastTime.size(); i++) {
//                            CameraRecordItem cameraRecordItem = new CameraRecordItem();
//                            cameraRecordItem.setDataTime(dataTime.get(i));
//                            cameraRecordItem.setLastTime(lastTime.get(i));
//                            cameraRecordItem.setHoleCount(holeCount.get(i));
//                            cameraRecordItem.setCrackCount(crackCount.get(i));
//                            cameraRecordItem.setTravel(travelWay.get(i));
//                            cameraRecordItem.setLno(lno.get(i));
//                            mItems.add(cameraRecordItem);
//                        }
//                        mAdapter.setItems(mItems);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                });
    }

    private void initMenu(){
        img_map = root.findViewById(R.id.map_view1);
        img_map.setOnClickListener(this);
        fab1 = root.findViewById(R.id.camera_fab1);
        fab1.setOnClickListener(this);
        fab2 = root.findViewById(R.id.camera_fab2);
        fab2.setOnClickListener(this);
        fab3 = root.findViewById(R.id.camera_fab3);
        fab3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.map_view1:
                startActivity(new Intent(root.getContext(), MainActivity.class));
                break;

            case R.id.camera_fab1:
                startActivity(new Intent(root.getContext(), SourcePushActivity.class));
                break;

            case R.id.camera_fab2:
                ActivityCollectorUtil.finishAllActivity();
                startActivity(new Intent(root.getContext(), SignInActivity.class));
                break;
            case R.id.camera_fab3:
                startActivity(new Intent(root.getContext(), CamActivity.class));
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
