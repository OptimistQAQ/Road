package com.example.a65667.road.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.a65667.road.Item.CrackListItem;
import com.example.a65667.road.R;
import com.example.a65667.road.binder.CrackListItemViewBinder;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class CrackListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MultiTypeAdapter mAdapter;
    private Items mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crack_list);
        init();
    }

    private void init(){
        List<String> nameList = new ArrayList<>();
        List<String> disList = new ArrayList<>();
        List<String> numList = new ArrayList<>();

        nameList.add("南大桥附近");
        disList.add("距离：1.8公里");
        numList.add("12");

        nameList.add("中北大学附近");
        disList.add("距离：2.0公里");
        numList.add("10");

        nameList.add("文瀛餐厅附近");
        disList.add("距离：1.2公里");
        numList.add("2");

        recyclerView = findViewById(R.id.ic_crack_list);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(CrackListItem.class, new CrackListItemViewBinder());
        recyclerView.setAdapter(mAdapter);
        mItems = new Items();
        for(int i=0; i<nameList.size(); i++){
            CrackListItem crackListItem = new CrackListItem();
            crackListItem.setRc_name(nameList.get(i));
            crackListItem.setRc_distance(disList.get(i));
            crackListItem.setRc_num(numList.get(i));
            mItems.add(crackListItem);
        }

        mAdapter.setItems(mItems);
        mAdapter.notifyDataSetChanged();
    }

}
