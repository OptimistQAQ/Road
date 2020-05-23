package com.example.a65667.road.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a65667.road.Item.VideoItem;
import com.example.a65667.road.R;

import cn.jzvd.JzvdStd;
import me.drakeet.multitype.ItemViewBinder;

public class VideoItemViewBinder extends ItemViewBinder<VideoItem, VideoItemViewBinder.ViewHolder> {

    private View root;
    private VideoItem videoItem;
    private TextView fileName;
    private Button btnDelete;

    private JzvdStd jzvdStd;
    private String videoUrl = "http://ishero.net/share/valvideo/ccd901f5-bfabffd7.mov";

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_video_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull VideoItem item) {
        videoItem = item;
        initView();
    }

    private void initView(){
        fileName = root.findViewById(R.id.file_name);
        fileName.setText(videoItem.getFileName());
        btnDelete = root.findViewById(R.id.btnDelete);
        jzvdStd = root.findViewById(R.id.file_video);

        jzvdStd.setUp(videoUrl, "路面回放", JzvdStd.SCREEN_NORMAL);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
