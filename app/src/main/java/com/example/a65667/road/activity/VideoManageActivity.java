package com.example.a65667.road.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.a65667.road.Item.VideoItem;
import com.example.a65667.road.R;
import com.example.a65667.road.adapter.VideoItemAdapter;
import com.example.a65667.road.bean.SwipeBean;
import com.example.a65667.road.binder.VideoItemViewBinder;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class VideoManageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MultiTypeAdapter mAdapter;
    private Items mItems;

    private ImageView vm_return;


//    private VideoItemAdapter videoItemAdapter;
//    private List<SwipeBean> mDatas;
//    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_manage);

        init();

        vm_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        vm_return = (ImageView)findViewById(R.id.vm_return);

        List<String> fileName = new ArrayList<>();

        fileName.add("南大桥附近");

        fileName.add("中北大学附近");

        fileName.add("文瀛餐厅附近");

        recyclerView = findViewById(R.id.vm_video_manage);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(VideoItem.class, new VideoItemViewBinder());
        recyclerView.setAdapter(mAdapter);

        mItems = new Items();
        for(int i=0; i<fileName.size(); i++){
            VideoItem videoItem = new VideoItem();
            videoItem.setFileName(fileName.get(i));
            mItems.add(videoItem);
        }

        mAdapter.setItems(mItems);
        mAdapter.notifyDataSetChanged();


//        recyclerView = (RecyclerView)findViewById(R.id.vm_video_manage);
//        mDatas = new ArrayList<>();
//        for(int i=0; i < 20; i++){
//            mDatas.add(new SwipeBean("" + i));
//        }
//        videoItemAdapter = new VideoItemAdapter(this, mDatas);
//        videoItemAdapter.setOnDelListener(new VideoItemAdapter.onSwipeListener() {
//            @Override
//            public void onDel(int pos) {
//                if(pos >= 0 && pos < mDatas.size()){
//                    mDatas.remove(pos);
//                    videoItemAdapter.notifyItemRemoved(pos);
//                }
//            }
//        });
//        recyclerView.setAdapter(videoItemAdapter);
//        recyclerView.setLayoutManager(manager = new GridLayoutManager(this, 2));

    }
}
