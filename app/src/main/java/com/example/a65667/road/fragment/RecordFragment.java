package com.example.a65667.road.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.example.a65667.road.R;
import com.example.a65667.road.RecordGPSAndUpload;
import com.example.a65667.road.activity.AuthorityManageActivity;
import com.example.a65667.road.activity.Main2Activity;
import com.example.a65667.road.activity.VideoActivity;
import com.example.a65667.road.activity.VideoManageActivity;
import com.example.a65667.road.bean.LocalSaveGPSPointJson;
import com.example.a65667.road.utils.SaveAndLoadLocalFile;
import com.example.a65667.road.utils.UriToPath;

import java.io.File;

import javax.security.auth.login.LoginException;

import cn.nodemedia.pusher.ShareBean;
import cn.nodemedia.pusher.view.SourcePushActivity;

public class RecordFragment extends Fragment {
    private static OSS oss;
    private View root;
    private ImageView tv_power, tvVideo, tv_Photo;
    private static final int VIDEO_WITH_CAMERA = 1;
    private RecordGPSAndUpload recordGPSAndUpload;

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

    private void initPhotoError(){
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    private void initView() {
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
                                // TODO: 开启线路准备----------------------
                                ShareBean.utotalLine++;

                                dialog.dismiss();
                                Toast.makeText(root.getContext(), "本地录制视频上传", Toast.LENGTH_SHORT).show();
                                recordGPSAndUpload = new RecordGPSAndUpload();
                                recordGPSAndUpload.start();
                                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                File file = new File(getContext().getExternalCacheDir(), "gt_" + ShareBean.uno + "_" + (ShareBean.utotalLine - 1) + ".mp4");
                                Log.e("files_dir", file.getAbsolutePath() + "");
                                Uri uri = FileProvider.getUriForFile(getContext().getApplicationContext(),
                                        getContext().getApplicationContext().getPackageName() + ".provider", file);

                                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                                startActivityForResult(intent, VIDEO_WITH_CAMERA);
                            }
                        }).create();
                dialog.show();
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == Activity.RESULT_OK && requestCode == VIDEO_WITH_CAMERA) {
                recordGPSAndUpload.interrupt(); // 停止获取位置
                Uri uri = data.getData();
                String path = UriToPath.getFilePathFromURI(getContext(), uri);
                // TODO: 修改位置
                SaveAndLoadLocalFile saveAndLoadLocalFile = new SaveAndLoadLocalFile(getContext());

                String json = JSON.toJSONString(ShareBean.gpsPoints);
                String fileKeyName = "gt_" + ShareBean.uno + "_" + (ShareBean.utotalLine - 1) + ".json";
                saveAndLoadLocalFile.save(json, fileKeyName);
                Log.e("vmuri", fileKeyName + "***" + uri.toString() + "---" + path + "----" + ShareBean.gpsPoints.size() + "--" + json);


//                upload(path, "or_" + ShareBean.uno + "_" + (ShareBean.utotalLine - 1) + ".mp4");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
