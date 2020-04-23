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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.example.a65667.road.Item.MineRecordItem;
import com.example.a65667.road.activity.MainActivity;
import com.example.a65667.road.R;
import com.example.a65667.road.binder.MineRecordItemViewBinder;
import com.github.clans.fab.FloatingActionButton;

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

        img_map = root.findViewById(R.id.map_view1);
        img_map.setOnClickListener(this);

        List<String> dataTime = new ArrayList<>();
        List<String> lastTime = new ArrayList<>();
        List<String> holeCount = new ArrayList<>();
        List<String> crackCount = new ArrayList<>();
        List<String> travelWay = new ArrayList<>();

        dataTime.add("12月31日");
        lastTime.add("96分钟");
        holeCount.add("16.3公里");
        crackCount.add("10个大问题");
        travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");

        dataTime.add("1月9号");
        lastTime.add("80分钟");
        holeCount.add("13.9公里");
        crackCount.add("12个大问题");
        travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");

        dataTime.add("1月20号");
        lastTime.add("105分钟");
        holeCount.add("19.4公里");
        crackCount.add("8个大问题");
        travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");

        dataTime.add("2月4号");
        lastTime.add("96分钟");
        holeCount.add("16.3公里");
        crackCount.add("10个大问题");
        travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");

        recyclerView = root.findViewById(R.id.rv_video);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(MineRecordItem.class, new MineRecordItemViewBinder());
        recyclerView.setAdapter(mAdapter);
        mItems = new Items();

        for (int i = 0; i < lastTime.size(); i++) {
            MineRecordItem mineRecordItem = new MineRecordItem();
            mineRecordItem.setDataTime(dataTime.get(i));
            mineRecordItem.setLastTime(lastTime.get(i));
            mineRecordItem.setHoleCount(holeCount.get(i));
            mineRecordItem.setCrackCount(crackCount.get(i));
            mineRecordItem.setTravel(travelWay.get(i));
            mItems.add(mineRecordItem);
        }

        mAdapter.setItems(mItems);
        mAdapter.notifyDataSetChanged();
    }

    private void initMenu(){
        fab1 = root.findViewById(R.id.camera_fab1);
        fab1.setOnClickListener(this);
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
