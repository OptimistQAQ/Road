package com.example.a65667.road.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a65667.road.Item.MineRecordItem;
import com.example.a65667.road.Item.MineTopItem;
import com.example.a65667.road.R;
import com.example.a65667.road.binder.MineRecordItemViewBinder;
import com.example.a65667.road.binder.MineTopItemViewBinder;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MineFragment extends Fragment {

    private View root;

    private MultiTypeAdapter mAdaper;
    private RecyclerView recyclerView;
    private Items mItems;

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
        initView();
        return root;
    }

    private void initView(){
        List<String> dataTime = new ArrayList<>();
        List<String> lastTime = new ArrayList<>();
        List<String> holeCount = new ArrayList<>();
        List<String> crackCount = new ArrayList<>();

        dataTime.add("12月31日");
        lastTime.add("96分钟");
        holeCount.add("16.3公里");
        crackCount.add("10条");

        dataTime.add("1月9号");
        lastTime.add("80分钟");
        holeCount.add("13.9公里");
        crackCount.add("12条");

        dataTime.add("1月20号");
        lastTime.add("105分钟");
        holeCount.add("19.4公里");
        crackCount.add("8条");

        dataTime.add("2月4号");
        lastTime.add("96分钟");
        holeCount.add("16.3公里");
        crackCount.add("10条");

        recyclerView = root.findViewById(R.id.rv_mine);
        mAdaper = new MultiTypeAdapter();
        mAdaper.register(MineTopItem.class, new MineTopItemViewBinder());
        mAdaper.register(MineRecordItem.class, new MineRecordItemViewBinder());
        recyclerView.setAdapter(mAdaper);
        mItems = new Items();
        mItems.add(new MineTopItem());
        for (int i = 0; i < lastTime.size(); i++) {
            MineRecordItem mineRecordItem = new MineRecordItem();
            mineRecordItem.setDataTime(dataTime.get(i));
            mineRecordItem.setLastTime(lastTime.get(i));
            mineRecordItem.setHoleCount(holeCount.get(i));
            mineRecordItem.setCrackCount(crackCount.get(i));
            mItems.add(mineRecordItem);
        }

        mAdaper.setItems(mItems);
        mAdaper.notifyDataSetChanged();
    }
}
