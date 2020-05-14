package com.example.a65667.road.binder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a65667.road.Item.MineRecordItem;
import com.example.a65667.road.R;
import com.example.a65667.road.activity.CrackDetailActivity;

import me.drakeet.multitype.ItemViewBinder;

public class MineRecordItemViewBinder extends ItemViewBinder<MineRecordItem, MineRecordItemViewBinder.ViewHolder> {

    private MineRecordItem mineRecordItem;
    private View root;
    private TextView tvData, tvLast, tvHole, tvCrack, tvTravel;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_mine_record_item, parent, false);
        initView();
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MineRecordItem mineRecordItem) {

        holder.setIsRecyclable(false);

        this.mineRecordItem = mineRecordItem;
        tvData.setText(mineRecordItem.getDataTime());
        tvLast.setText(mineRecordItem.getLastTime());
        tvHole.setText(mineRecordItem.getHoleCount());
        tvCrack.setText(mineRecordItem.getCrackCount());
        tvTravel.setText(mineRecordItem.getTravel());
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println( mineRecordItem.getDataTime());
                Intent intent = new Intent(root.getContext(), CrackDetailActivity.class);
                intent.putExtra("tvData", mineRecordItem.getDataTime());
                intent.putExtra("lno", mineRecordItem.getLno());
                intent.putExtra("video", mineRecordItem.getVideoUrl());
                root.getContext().startActivity(intent);
            }
        });

    }

    private void initView() {
        tvData = root.findViewById(R.id.tv_data);
        tvLast = root.findViewById(R.id.tv_last_time);
        tvHole = root.findViewById(R.id.tv_hole_count);
        tvCrack = root.findViewById(R.id.tv_crack_count);
        tvTravel = root.findViewById(R.id.tv_travel);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
