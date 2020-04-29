package com.example.a65667.road.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.a65667.road.Item.CrackListItem;
import com.example.a65667.road.R;
import com.example.a65667.road.binder.CrackListItemViewBinder;
import com.example.a65667.road.utils.CurrentUserInfo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class CrackListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MultiTypeAdapter mAdapter;
    private Items mItems;
    private TextView ac_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crack_list);
        init();
    }

    private void init(){

        ac_name = (TextView) findViewById(R.id.ac_name);
        ac_name.setText(CurrentUserInfo.name);

        List<String> nameList = new ArrayList<>();
        List<String> disList = new ArrayList<>();
        List<String> numList = new ArrayList<>();

//        nameList.add("南大桥附近");
//        disList.add("距离：1.8公里");
//        numList.add("12");
//
//        nameList.add("中北大学附近");
//        disList.add("距离：2.0公里");
//        numList.add("10");
//
//        nameList.add("文瀛餐厅附近");
//        disList.add("距离：1.2公里");
//        numList.add("2");

        recyclerView = findViewById(R.id.ic_crack_list);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(CrackListItem.class, new CrackListItemViewBinder());
        recyclerView.setAdapter(mAdapter);

//        mItems = new Items();
//        for(int i=0; i<nameList.size(); i++){
//            CrackListItem crackListItem = new CrackListItem();
//            crackListItem.setRc_name(nameList.get(i));
//            crackListItem.setRc_distance(disList.get(i));
//            crackListItem.setRc_num(numList.get(i));
//            mItems.add(crackListItem);
//        }
//        mAdapter.setItems(mItems);
//        mAdapter.notifyDataSetChanged();

        OkGo.<String>post("http://39.105.172.22:9596/showLine")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("crack_list", response.body());
                        response.toString();
                        JSONArray jsonArray = JSON.parseArray(response.body());
                        for (int i=0; i<jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            disList.add("距离：1.8公里");
                            nameList.add(jsonObject.getString("lpushStramName"));
                            numList.add(jsonObject.getInteger("uno").toString());
                        }
                        mItems = new Items();
                        for (int i=0; i<nameList.size(); i++) {
                            CrackListItem crackListItem = new CrackListItem();
                            crackListItem.setRc_num(numList.get(i));
                            crackListItem.setRc_distance(disList.get(i));
                            crackListItem.setRc_name(nameList.get(i));
                            mItems.add(crackListItem);
                        }
                        mAdapter.setItems(mItems);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

}
