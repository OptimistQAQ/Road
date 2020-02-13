package com.example.a65667.road.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a65667.road.Item.IdRecordItem;
import com.example.a65667.road.R;

import me.drakeet.multitype.ItemViewBinder;

public class IdRecordItemViewBinder extends ItemViewBinder<IdRecordItem, IdRecordItemViewBinder.ViewHolder> {

    private View root;
    private IdRecordItem idRecordItem;

    private TextView icName, icID;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_manage_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull IdRecordItemViewBinder.ViewHolder holder, @NonNull IdRecordItem item) {
        idRecordItem = item;
        initView();
    }

    private void initView()
    {
        icName = root.findViewById(R.id.ic_name);
        icID = root.findViewById(R.id.ic_id);

        icName.setText(idRecordItem.getIcName());
        icID.setText(idRecordItem.getIcID());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
