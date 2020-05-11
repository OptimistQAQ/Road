package com.example.a65667.road.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a65667.road.Item.MineTopItem;
import com.example.a65667.road.R;
import com.example.a65667.road.utils.CurrentUserInfo;

import me.drakeet.multitype.ItemViewBinder;

public class MineTopItemViewBinder extends ItemViewBinder<MineTopItem, MineTopItemViewBinder.ViewHolder> {

    private View root;
    private TextView tv_user_name;
    private TextView tv_ID_number;
    private TextView tv_count;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_mine_top_item, parent, false);
        init();
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MineTopItem mineTopItem) {
    }

    private void init() {
        tv_user_name = root.findViewById(R.id.tv_user_name);
        tv_ID_number = root.findViewById(R.id.tv_ID_number);
        tv_count = root.findViewById(R.id.tv_count);
        tv_user_name.setText(CurrentUserInfo.name);
        tv_ID_number.setText(CurrentUserInfo.uno.toString());
        tv_count.setText(CurrentUserInfo.line);
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}