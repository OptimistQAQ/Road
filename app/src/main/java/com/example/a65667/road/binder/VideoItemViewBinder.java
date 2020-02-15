package com.example.a65667.road.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a65667.road.Item.VideoItem;
import com.example.a65667.road.R;

import me.drakeet.multitype.ItemViewBinder;

public class VideoItemViewBinder extends ItemViewBinder<VideoItem, VideoItemViewBinder.ViewHolder> {

    private View root;
    private VideoItem videoItem;
    private TextView fileName;

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
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
