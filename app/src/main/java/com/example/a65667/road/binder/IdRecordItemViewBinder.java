package com.example.a65667.road.binder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a65667.road.Item.IdRecordItem;
import com.example.a65667.road.R;
import com.example.a65667.road.activity.AuthorityActivity;

import me.drakeet.multitype.ItemViewBinder;

public class IdRecordItemViewBinder extends ItemViewBinder<IdRecordItem, IdRecordItemViewBinder.ViewHolder> {

    private IdRecordItem idRecordItem;
    private View root;

    private TextView icName, icID;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_manage_item, parent, false);
        initView();
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull IdRecordItemViewBinder.ViewHolder holder, @NonNull IdRecordItem item) {

        holder.setIsRecyclable(false);

        this.idRecordItem = item;

        icID.setText(item.getIcID());
        icName.setText(item.getIcName());

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), AuthorityActivity.class);
                intent.putExtra("id", item.getIcID());
                root.getContext().startActivity(intent);
            }
        });
    }

    private void initView()
    {
        icName = root.findViewById(R.id.ic_name);
        icID = root.findViewById(R.id.ic_id);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
