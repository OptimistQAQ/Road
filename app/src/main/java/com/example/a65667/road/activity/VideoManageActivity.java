package com.example.a65667.road.activity;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.a65667.road.Item.VideoItem;
import com.example.a65667.road.R;
import com.example.a65667.road.adapter.VideoItemAdapter;
import com.example.a65667.road.bean.SwipeBean;
import com.example.a65667.road.binder.VideoItemViewBinder;
import com.example.a65667.road.utils.ActivityCollectorUtil;

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

        ActivityCollectorUtil.addActivity(this);

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
        List<VideoItem> videoItems;

        fileName.add("南大桥附近");

        fileName.add("中北大学附近");

        fileName.add("文瀛餐厅附近");

        recyclerView = findViewById(R.id.vm_video_manage);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(VideoItem.class, new VideoItemViewBinder());
        recyclerView.setAdapter(mAdapter);

//        mItems = new Items();
//        for(int i=0; i<fileName.size(); i++){
//            VideoItem videoItem = new VideoItem();
//            videoItem.setFileName(fileName.get(i));
//            mItems.add(videoItem);
//        }
//
//        mAdapter.setItems(mItems);
//        mAdapter.notifyDataSetChanged();

        videoItems = getList(VideoManageActivity.this);
        mItems = new Items();
        for (int i=0; i<videoItems.size(); i++) {
            mItems.add(videoItems.get(i));
            Log.e("url", videoItems.get(i).getFileUrl());
        }
        Log.e("size", String.valueOf(videoItems.size()));
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

    public List<VideoItem> getList(Context context) {
        List<VideoItem> sysVideoList = new ArrayList<>();
        // MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
        String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID};
        // 视频其他信息的查询条件
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION};

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media
                        .EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);

        if (cursor == null) {
            return sysVideoList;
        }
        if (cursor.moveToFirst()) {
            do {
                VideoItem info = new VideoItem();
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Video.Media._ID));
                Cursor thumbCursor = context.getContentResolver().query(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                + "=" + id, null, null);
                if (thumbCursor.moveToFirst()) {
                    info.setFileUrl(thumbCursor.getString(thumbCursor
                            .getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                }
                info.setFileUrl(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media
                        .DATA)));
                info.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.DURATION)));
                info.setFileImg(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                sysVideoList.add(info);
            } while (cursor.moveToNext());
        }
        return sysVideoList;
    }

}
