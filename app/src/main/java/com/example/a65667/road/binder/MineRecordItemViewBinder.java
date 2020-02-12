package com.example.a65667.road.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a65667.road.Item.MineRecordItem;
import com.example.a65667.road.R;

import me.drakeet.multitype.ItemViewBinder;

public class MineRecordItemViewBinder extends ItemViewBinder<MineRecordItem, MineRecordItemViewBinder.ViewHolder> {
    private MineRecordItem mineRecordItem;
    private View root;
    private TextView tvData, tvLast, tvHole, tvCrack;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_mine_record_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MineRecordItem mineRecordItem) {
        this.mineRecordItem = mineRecordItem;
        initView();
    }

    private void initView() {
        tvData = root.findViewById(R.id.tv_data);
        tvLast = root.findViewById(R.id.tv_last_time);
        tvHole = root.findViewById(R.id.tv_hole_count);
        tvCrack = root.findViewById(R.id.tv_crack_count);
        tvData.setText(mineRecordItem.getDataTime());
        tvLast.setText(mineRecordItem.getLastTime());
        tvHole.setText(mineRecordItem.getHoleCount());
        tvCrack.setText(mineRecordItem.getCrackCount());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
