package com.example.a65667.road.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.a65667.road.R;
import com.example.a65667.road.activity.AuthorityManageActivity;

public class RecordFragment extends Fragment {

    private View root;
    private ImageView tv_power;

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.record_fragment, container, false);
        initView();
        return root;
    }

    private void initView(){
        tv_power = root.findViewById(R.id.tv_power);
        tv_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), AuthorityManageActivity.class));
            }
        });
    }

}
