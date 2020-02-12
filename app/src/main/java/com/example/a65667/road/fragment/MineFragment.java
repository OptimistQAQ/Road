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
        List<String> startList = new ArrayList<>();
        List<String> endList = new ArrayList<>();
        List<String> lastTime = new ArrayList<>();
        List<String> holeCount = new ArrayList<>();
        List<String> crackCount = new ArrayList<>();


        startList.add("开始: 2019年11月10日 18:02");
        endList.add("结束: 2019年11月10日 18:27");
        lastTime.add("时长:25分钟");
        holeCount.add("坑数:8个");
        crackCount.add("缝数42条");


        startList.add("开始: 2019年10月6日 15:46");
        endList.add("结束: 2019年10月6日 16:42");
        lastTime.add("时长:56分钟");
        holeCount.add("坑数:4个");
        crackCount.add("缝数:11条");

        startList.add("开始: 2019年10月5日 11:43");
        endList.add("结束: 2019年10月5日 12:07");
        lastTime.add("时长:24分钟");
        holeCount.add("坑数:1个");
        crackCount.add("缝数:13条");

        startList.add("开始: 2019年10月5日 9:13");
        endList.add("结束: 2019年10月5日 9:27");
        lastTime.add("时长:14分钟");
        holeCount.add("坑数:3个");
        crackCount.add("缝数:21条");

        recyclerView = root.findViewById(R.id.rv_mine);
        mAdaper = new MultiTypeAdapter();
        mAdaper.register(MineTopItem.class, new MineTopItemViewBinder());
        mAdaper.register(MineRecordItem.class, new MineRecordItemViewBinder());
        recyclerView.setAdapter(mAdaper);
        mItems = new Items();
        mItems.add(new MineTopItem());
        for (int i = 0; i < startList.size(); i++) {
            MineRecordItem mineRecordItem = new MineRecordItem();
            mineRecordItem.setStartTime(startList.get(i));
            mineRecordItem.setEndTime(endList.get(i));
            mineRecordItem.setLastTime(lastTime.get(i));
            mineRecordItem.setHoleCount(holeCount.get(i));
            mineRecordItem.setCrackCount(crackCount.get(i));
            mItems.add(mineRecordItem);
        }

        mAdaper.setItems(mItems);
        mAdaper.notifyDataSetChanged();
    }
}
