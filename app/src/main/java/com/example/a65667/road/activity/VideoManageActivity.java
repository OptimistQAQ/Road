package com.example.a65667.road.activity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private boolean check_date(String filepath) {
        File f = new File(filepath);
        if (f.exists()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lastModified = new Date(f.lastModified());
            try {
                Date now = format.parse("2020-08-15 12:00:00");
                return lastModified.after(now);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private void init() {


        vm_return = (ImageView) findViewById(R.id.vm_return);

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
        videoItems = getList();

//        videoItems = getList(VideoManageActivity.this);
        mItems = new Items();
        for (int i = 0; i < videoItems.size(); i++) {
            if (check_date(videoItems.get(i).getFileUrl())) {
                mItems.add(videoItems.get(i));
                Log.e("item_file_url", videoItems.get(i).getFileUrl());

            }
        }
        Log.e("size", String.valueOf(mItems.size()));
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

    public List<VideoItem> getList() {
        List<VideoItem> sysVideoList = new ArrayList<>();
        File dirs = getExternalCacheDir();
        File[] listDir = dirs.listFiles();
        for (File f : listDir) {

            if (f.getName().endsWith(".mp4")){
                Log.e("list_dir", f.getName()+ "--" + f.getAbsolutePath()+ "--"+f.getPath());
                VideoItem vitem = new VideoItem();
                vitem.setFileName(f.getName());
                vitem.setFileUrl(f.getAbsolutePath());
                vitem.setDate(new Date(f.lastModified()));
                vitem.setFileImg("");   // TODO: fix
                sysVideoList.add(vitem);
            }
        }
        return sysVideoList;
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
                Log.e("urinum", info.getFileName() + info.getFileName() + "");
                info.setUriNumber("");
                sysVideoList.add(info);
            } while (cursor.moveToNext());
        }
        return sysVideoList;
    }

    public static Uri getImageStreamFromExternal(String imageName) {
        File externalPubPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        );

        File picPath = new File(imageName);
        Uri uri = null;
        if (picPath.exists()) {
            uri = Uri.fromFile(picPath);
        }

        return uri;
    }

}
