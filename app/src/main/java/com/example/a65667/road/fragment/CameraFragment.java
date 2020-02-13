package com.example.a65667.road.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a65667.road.R;

public class CameraFragment extends Fragment implements View.OnClickListener {

    private View root;

    public CameraFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.camera_fragment, container, false);
        initView();
        return root;
    }

    private void initView(){

    }

    @Override
    public void onClick(View v) {

    }
}
