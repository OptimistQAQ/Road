package com.example.a65667.road.binder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a65667.road.Item.CameraRecordItem;
import com.example.a65667.road.R;
import com.example.a65667.road.activity.CrackDetailActivity;

import me.drakeet.multitype.ItemViewBinder;

public class CameraRecordItemViewBinder extends ItemViewBinder<CameraRecordItem, CameraRecordItemViewBinder.ViewHolder> {

    private CameraRecordItem cameraRecordItem;
    private View root;
    private TextView lvData, lvLast, lvHole, lvCrack, lvTravel;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_camera_record_item, parent, false);
        initView();
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CameraRecordItem cameraRecordItem) {
        holder.setIsRecyclable(false);
        this.cameraRecordItem = cameraRecordItem;
        lvData.setText(cameraRecordItem.getDataTime());
        lvLast.setText(cameraRecordItem.getLastTime());
        lvHole.setText(cameraRecordItem.getHoleCount());
        lvCrack.setText(cameraRecordItem.getCrackCount());
        lvTravel.setText(cameraRecordItem.getTravel());
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), CrackDetailActivity.class);
                intent.putExtra("tvData", cameraRecordItem.getDataTime());
                intent.putExtra("lno", cameraRecordItem.getLno());
                intent.putExtra("video", cameraRecordItem.getVideoUrl());
                root.getContext().startActivity(intent);
            }
        });
    }

    private void initView() {
        lvData = root.findViewById(R.id.lv_data);
        lvLast = root.findViewById(R.id.lv_last_time);
        lvHole = root.findViewById(R.id.lv_hole_count);
        lvCrack = root.findViewById(R.id.lv_crack_count);
        lvTravel = root.findViewById(R.id.lv_travel);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
