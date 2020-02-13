package com.example.a65667.road.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.a65667.road.Item.IdRecordItem;
import com.example.a65667.road.R;
import com.example.a65667.road.binder.IdRecordItemViewBinder;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class AuthorityManageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MultiTypeAdapter mAdapter;
    private Items mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_manage);
        init();
    }


    private void init(){

        List<String> nameList = new ArrayList<>();
        List<String> idList = new ArrayList<>();

        nameList.add("张三");
        idList.add("10000001");

        nameList.add("李斯");
        idList.add("10000002");

        nameList.add("张三丰");
        idList.add("10000003");

        recyclerView = findViewById(R.id.rv_record);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(IdRecordItem.class, new IdRecordItemViewBinder());
        recyclerView.setAdapter(mAdapter);
        mItems = new Items();
        mItems.add(new IdRecordItem());
        for(int i=0; i<nameList.size(); i++){
            IdRecordItem idRecordItem = new IdRecordItem();
            idRecordItem.setIcName(nameList.get(i));
            idRecordItem.setIcID(idList.get(i));
            mItems.add(idRecordItem);
        }

        mAdapter.setItems(mItems);
        mAdapter.notifyDataSetChanged();
    }
}
