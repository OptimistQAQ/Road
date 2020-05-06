package com.example.a65667.road.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.a65667.road.Item.IdRecordItem;
import com.example.a65667.road.R;
import com.example.a65667.road.binder.IdRecordItemViewBinder;
import com.example.a65667.road.utils.CurrentUserInfo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class AuthorityManageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MultiTypeAdapter mAdapter;
    private Items mItems;

    private ImageView ic_arrow_left_w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_manage);
        init();

        ic_arrow_left_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void init(){

        ic_arrow_left_w = (ImageView)findViewById(R.id.ic_arrow_left_w);

        List<String> nameList = new ArrayList<>();
        List<String> idList = new ArrayList<>();

        nameList.add(CurrentUserInfo.name);
        idList.add(CurrentUserInfo.uno.toString());

        recyclerView = findViewById(R.id.rv_record);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(IdRecordItem.class, new IdRecordItemViewBinder());
        recyclerView.setAdapter(mAdapter);

        Log.e("user", CurrentUserInfo.name + CurrentUserInfo.uno);

        OkGo.<String>post("http://39.105.172.22:9596/showLogin")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mItems = new Items();
                        mItems.add(new IdRecordItem());

                        Log.e("user_list", response.body());
                        response.toString();
                        JSONArray jsonArray = JSON.parseArray(response.body());
                        for (int i=0; i<jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idList.add(jsonObject.getString("uno"));
                            nameList.add(jsonObject.getString("uname"));
//                            Log.e("2user", nameList.size() + " " + nameList.get(i+1));
                        }
                        mItems = new Items();
                        for(int i=0; i<nameList.size(); i++){
                            IdRecordItem idRecordItem = new IdRecordItem();
                            idRecordItem.setIcName(nameList.get(i));
                            idRecordItem.setIcID(idList.get(i));
                            mItems.add(idRecordItem);
                        }
                        mAdapter.setItems(mItems);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
