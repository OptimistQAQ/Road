package com.example.a65667.road.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a65667.road.R;
import com.example.a65667.road.activity.AuthorityManageActivity;
import com.example.a65667.road.activity.VideoActivity;
import com.example.a65667.road.activity.VideoManageActivity;

import cn.nodemedia.pusher.ShareBean;
import cn.nodemedia.pusher.view.SourcePushActivity;

public class RecordFragment extends Fragment {

    private View root;
    private ImageView tv_power, tvVideo, tv_Photo;
    private static final int VIDEO_WITH_CAMERA = 1;

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
        tvVideo = root.findViewById(R.id.tv_video);
        tvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), VideoManageActivity.class));
            }
        });
        tv_Photo = root.findViewById(R.id.photo);
        tv_Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(root.getContext())
                        .setTitle("请选择方式：")
                        .setNegativeButton("实时推流", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Toast.makeText(root.getContext(), "实时推流", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(root.getContext(), SourcePushActivity.class));
                            }
                        })
                        .setPositiveButton("本地录制视频上传", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Toast.makeText(root.getContext(), "本地录制视频上传", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                Toast.makeText(root.getContext(), ShareBean.Latitude + " " + ShareBean.Longitude, Toast.LENGTH_LONG).show();
                                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                startActivityForResult (intent, VIDEO_WITH_CAMERA);
                            }
                        }).create();
                dialog.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (resultCode == Activity.RESULT_OK && requestCode == VIDEO_WITH_CAMERA) {
                Uri uri = data.getData();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
