package com.example.a65667.road.binder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a65667.road.Item.CrackListItem;
import com.example.a65667.road.R;
import com.example.a65667.road.activity.CrackPictureActivity;

import me.drakeet.multitype.ItemViewBinder;

public class CrackListItemViewBinder extends ItemViewBinder<CrackListItem, CrackListItemViewBinder.ViewHolder> {

    private View root;
    private CrackListItem crackListItem;

    private TextView rcName, rcDistance, rcNum;


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_list_item, parent, false);
        initView();
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CrackListItem item) {
        holder.setIsRecyclable(false);
        crackListItem = item;
        rcName.setText(item.getRc_name());
        rcDistance.setText(item.getRc_distance());
        rcNum.setText(item.getRc_num());

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), CrackPictureActivity.class);
                intent.putExtra("rcName", rcName.getText().toString());
                root.getContext().startActivity(intent);
            }
        });
    }

    private void initView(){
        rcName = root.findViewById(R.id.rc_name);
        rcDistance = root.findViewById(R.id.rc_distance);
        rcNum = root.findViewById(R.id.rc_num);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
