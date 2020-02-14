package com.example.a65667.road.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a65667.road.Item.MineTopItem;
import com.example.a65667.road.R;

import me.drakeet.multitype.ItemViewBinder;

public class MineTopItemViewBinder extends ItemViewBinder<MineTopItem, MineTopItemViewBinder.ViewHolder> {

    private View root;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_mine_top_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MineTopItem mineTopItem) {
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
