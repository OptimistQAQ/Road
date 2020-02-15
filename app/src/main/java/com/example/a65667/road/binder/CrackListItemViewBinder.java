package com.example.a65667.road.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a65667.road.Item.CrackListItem;
import com.example.a65667.road.R;

import me.drakeet.multitype.ItemViewBinder;

public class CrackListItemViewBinder extends ItemViewBinder<CrackListItem, CrackListItemViewBinder.ViewHolder> {

    private View root;
    private CrackListItem crackListItem;

    private TextView rcName, rcDistance, rcNum;


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_list_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CrackListItem item) {
        crackListItem = item;
        initView();
    }

    private void initView(){
        rcName = root.findViewById(R.id.rc_name);
        rcDistance = root.findViewById(R.id.rc_distance);
        rcNum = root.findViewById(R.id.rc_num);

        rcName.setText(crackListItem.getRc_name());
        rcDistance.setText(crackListItem.getRc_distance());
        rcNum.setText(crackListItem.getRc_num());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
