package com.example.a65667.road.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.a65667.road.Item.MineRecordItem;
import com.example.a65667.road.Item.MineTopItem;
import com.example.a65667.road.R;
import com.example.a65667.road.binder.MineRecordItemViewBinder;
import com.example.a65667.road.binder.MineTopItemViewBinder;
import com.example.a65667.road.utils.CurrentUserInfo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MineFragment extends Fragment {

    private View root;

    private MultiTypeAdapter mAdaper;
    private RecyclerView recyclerView;
    private Items mItems;

    private SwipeRefreshLayout swipeMineRefresh;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.mine_fragment, container, false);
        initMenu();
        initView();
        return root;
    }

    private void initView(){
        List<String> dataTime = new ArrayList<>();
        List<String> lastTime = new ArrayList<>();
        List<String> holeCount = new ArrayList<>();
        List<String> crackCount = new ArrayList<>();
        List<String> travelWay = new ArrayList<>();
        List<String> lno = new ArrayList<>();
        List<String> videoUrl = new ArrayList<>();
        List<String> lprocessState = new ArrayList<>();
        List<String> lduration = new ArrayList<>();

//        dataTime.add("2020年3月6日");
//        lastTime.add("14分钟");
//        holeCount.add("20公里");
//        crackCount.add("3个大问题");
//        travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");
//        videoUrl.add("cc1d3666-fe5994ae.mov");
//        lno.add("1");
//
//        dataTime.add("2020年3月10日");
//        lastTime.add("20分钟");
//        holeCount.add("33公里");
//        crackCount.add("9个大问题");
//        travelWay.add("途经：南环路 > 傅山园北街 > 新兰路 > 滨河东路");
//        videoUrl.add("cc8d5a50-92a79d46.mov");
//        lno.add("2");
//
//        dataTime.add("2020年3月15日");
//        lastTime.add("14分钟");
//        holeCount.add("20公里");
//        crackCount.add("5个大问题");
//        travelWay.add("途经：中央大道  >  西环路  >  北环  >  东环");
//        videoUrl.add("cc9d2250-fba65525.mov");
//        lno.add("3");
//
//        dataTime.add("2020年3月20日");
//        lastTime.add("24分钟");
//        holeCount.add("56公里");
//        crackCount.add("11个大问题");
//        travelWay.add("途经：G228  >  G94  >  建设南路  >  横琴大桥");
//        videoUrl.add("ccd901f5-bfabffd7.mov");
//        lno.add("4");

        recyclerView = root.findViewById(R.id.rv_mine);
        mAdaper = new MultiTypeAdapter();
        mAdaper.register(MineTopItem.class, new MineTopItemViewBinder());
        mAdaper.register(MineRecordItem.class, new MineRecordItemViewBinder());
        recyclerView.setAdapter(mAdaper);

//        mItems = new Items();
//        mItems.add(new MineTopItem());
//        for (int i = 0; i < dataTime.size(); i++) {
//            MineRecordItem mineRecordItem = new MineRecordItem();
//            mineRecordItem.setDataTime(dataTime.get(i));
//            mineRecordItem.setLastTime(lastTime.get(i));
//            mineRecordItem.setHoleCount(holeCount.get(i));
//            mineRecordItem.setCrackCount(crackCount.get(i));
//            mineRecordItem.setTravel(travelWay.get(i));
//            mineRecordItem.setLno(lno.get(i));
//            mineRecordItem.setVideoUrl(videoUrl.get(i));
//            mItems.add(mineRecordItem);
//        }
//        mAdaper.setItems(mItems);
//        mAdaper.notifyDataSetChanged();


        OkGo.<String>post("http://39.105.172.22:9596/showLine")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        swipeMineRefresh.setRefreshing(false);
                        mItems = new Items();
                        mItems.add(new MineTopItem());
                        Log.e("minefragment", response.body());
                        response.toString();
                        JSONArray jsonArray = JSON.parseArray(response.body());
                        for (int i=0; i<jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            !jsonObject.getString("lduration").equals("0") &&
                            if (jsonObject.getString("uno").equals(CurrentUserInfo.uno.toString())) {
                                dataTime.add(jsonObject.getString("lbeginDate"));
                                lastTime.add(jsonObject.getString("lduration") + "分钟");
                                holeCount.add("20公里");
                                crackCount.add(jsonObject.getInteger("lproblem").toString() + "个大问题");
                                travelWay.add("途经：" + jsonObject.getString("lgoby"));
                                lno.add(jsonObject.getString("lno"));
                                videoUrl.add(jsonObject.getString("lpushStramName"));
                                lduration.add(jsonObject.getString("luration"));
                                lprocessState.add("状态：" + jsonObject.getString("lprocessState"));
                            }
                        }
                        for (int i = 0; i < lastTime.size(); i++) {
                            MineRecordItem mineRecordItem = new MineRecordItem();
                            mineRecordItem.setDataTime(dataTime.get(i));
                            mineRecordItem.setLastTime(lastTime.get(i));
                            mineRecordItem.setHoleCount(holeCount.get(i));
                            mineRecordItem.setCrackCount(crackCount.get(i));
                            mineRecordItem.setTravel(travelWay.get(i));
                            mineRecordItem.setLno(lno.get(i));
                            mineRecordItem.setVideoUrl(videoUrl.get(i));
                            mineRecordItem.setLprocessState(lprocessState.get(i));
                            mineRecordItem.setLduration(lduration.get(i));
                            mItems.add(mineRecordItem);
                        }
                        mAdaper.setItems(mItems);
                        mAdaper.notifyDataSetChanged();
                    }
                });
    }

    private void initMenu() {
        swipeMineRefresh = root.findViewById(R.id.swipe_mine_refresh);
        swipeMineRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeMineRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
            }
        });
    }
}
