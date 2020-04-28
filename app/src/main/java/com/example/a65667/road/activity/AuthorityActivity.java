package com.example.a65667.road.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.example.a65667.road.R;
import com.example.a65667.road.bean.Permission;
import com.example.a65667.road.utils.CurrentUserInfo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class AuthorityActivity extends AppCompatActivity {

    private ImageView tc_return;

    private ImageView per_base, per_photo, per_video, per_info, per_admin;
    private String rc_id;
    private Boolean base = false, photoSeg = false, videoSeg = false, infoCheck = false, permissionAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority);
        init();
    }

    private void init(){

        tc_return = (ImageView)findViewById(R.id.tc_return);
        per_base = (ImageView) findViewById(R.id.permission_base);
        per_photo = (ImageView) findViewById(R.id.permission_photo);
        per_video = (ImageView) findViewById(R.id.permission_video);
        per_info = (ImageView) findViewById(R.id.permission_info);
        per_admin = (ImageView) findViewById(R.id.permission_admin);

        Intent rc_name = getIntent();
        rc_id = rc_name.getStringExtra("id");
        Log.e("pass_id", rc_id);

        tc_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Permission permission = new Permission();

        per_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (base){
                    base = false;
                    permission.setPbase(base);
                    per_base.setImageDrawable(getResources().getDrawable(R.drawable.tv_start));
                    OkGo.<String>post("http://39.105.172.22:9596/decisionPermission")
                            .tag(this)
                            .params("Uno", permission.getUno())
                            .params("base", permission.getPbase())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("pbase", response.body());
                                    CurrentUserInfo.pbase = base;
                                }
                            });
                }
                else {
                    base = true;
                    permission.setPbase(base);
                    per_base.setImageDrawable(getResources().getDrawable(R.drawable.tv_off));
                    OkGo.<String>post("http://39.105.172.22:9596/decisionPermission")
                            .tag(this)
                            .params("base", permission.getPbase())
                            .params("id", permission.getUno())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("base", response.body());
                                    CurrentUserInfo.pbase = base;
                                }
                            });
                }
            }
        });

        per_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoSeg){
                    photoSeg = false;
                    per_photo.setImageDrawable(getResources().getDrawable(R.drawable.tv_start));
                    OkGo.<String>post("http://39.105.172.22:9596/decisionPermission")
                            .tag(this)
                            .params("photoSeg", photoSeg)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("photo", response.body());
                                    CurrentUserInfo.photo = photoSeg;
                                }
                            });
                }
                else{
                    photoSeg = true;
                    per_photo.setImageDrawable(getResources().getDrawable(R.drawable.tv_off));
                    OkGo.<String>post("http://39.105.172.22:9596/decisionPermission")
                            .tag(this)
                            .params("photoSeg", photoSeg)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("photo", response.body());
                                    CurrentUserInfo.photo = photoSeg;
                                }
                            });
                }
            }
        });

        per_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoSeg){
                    videoSeg = false;
                    per_video.setImageDrawable(getResources().getDrawable(R.drawable.tv_start));
                    OkGo.<String>post("http://39.105.172.22:9596/decisionPermission")
                            .tag(this)
                            .params("videoSeg", videoSeg)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("video", response.body());
                                    CurrentUserInfo.pvideo = videoSeg;
                                }
                            });
                }
                else {
                    videoSeg = true;
                    per_video.setImageDrawable(getResources().getDrawable(R.drawable.tv_off));
                    OkGo.<String>post("http://39.105.172.22:9596/decisionPermission")
                            .tag(this)
                            .params("videoSeg", videoSeg)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("video", response.body());
                                    CurrentUserInfo.pvideo = videoSeg;
                                }
                            });
                }
            }
        });

        per_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoCheck){
                    infoCheck = false;
                    per_info.setImageDrawable(getResources().getDrawable(R.drawable.tv_start));
                    OkGo.<String>post("http://39.105.172.22:9596/decisionPermission")
                            .tag(this)
                            .params("infoCheck", infoCheck)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("info", response.body());
                                    CurrentUserInfo.pinfo = infoCheck;
                                }
                            });
                }
                else {
                    infoCheck = true;
                    per_info.setImageDrawable(getResources().getDrawable(R.drawable.tv_off));
                    OkGo.<String>post("http://39.105.172.22:9596/decisionPermission")
                            .tag(this)
                            .params("infoCheck", infoCheck)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("info", response.body());
                                    CurrentUserInfo.pinfo = infoCheck;
                                }
                            });
                }
            }
        });

        per_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissionAdmin){
                    permissionAdmin = false;
                    per_admin.setImageDrawable(getResources().getDrawable(R.drawable.tv_start));
                    OkGo.<String>post("http://39.105.172.22:9596/decisionPermission")
                            .tag(this)
                            .params("permissionAdmin", permissionAdmin)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("admin", response.body());
                                    CurrentUserInfo.padmin = permissionAdmin;
                                }
                            });
                }
                else {
                    permissionAdmin = true;
                    per_admin.setImageDrawable(getResources().getDrawable(R.drawable.tv_off));
                    OkGo.<String>post("http://39.105.172.22:9596/decisionPermission")
                            .tag(this)
                            .params("permissionAdmin", permissionAdmin)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("admin", response.body());
                                    CurrentUserInfo.padmin = permissionAdmin;
                                }
                            });
                }
            }
        });

    }
}
