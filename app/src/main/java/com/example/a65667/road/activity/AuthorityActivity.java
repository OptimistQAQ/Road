package com.example.a65667.road.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.a65667.road.R;
import com.example.a65667.road.bean.Permission;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class AuthorityActivity extends AppCompatActivity {

    private ImageView tc_return;

    private ImageView per_base, per_photo, per_video, per_info, per_admin;
    private String uNo;
    private boolean pBase = false, pPhoto = false, pVideo = false, pInfo = false, pAdmin = false;

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

        tc_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        per_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pBase){
                    pBase = false;
                    per_base.setImageDrawable(getResources().getDrawable(R.drawable.tv_start));
                    OkGo.<String>post("http://192.168.0.107:8080/decisionPermission")
                            .tag(this)
                            .params("Pbase", pBase)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("base", response.body());
                                }
                            });
                }
                else {
                    pBase = true;
                    per_base.setImageDrawable(getResources().getDrawable(R.drawable.tv_off));
                    OkGo.<String>post("http://192.168.0.107:8080/decisionPermission")
                            .tag(this)
                            .params("Pbase", pBase)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("base", response.body());
                                }
                            });
                }
            }
        });

        per_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pPhoto){
                    pPhoto = false;
                    per_photo.setImageDrawable(getResources().getDrawable(R.drawable.tv_start));
                    OkGo.<String>post("http://192.168.0.107:8080/decisionPermission")
                            .tag(this)
                            .params("Pphoto_seg", pBase)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("photo", response.body());
                                }
                            });
                }
                else{
                    pPhoto = true;
                    per_photo.setImageDrawable(getResources().getDrawable(R.drawable.tv_off));
                    OkGo.<String>post("http://192.168.0.107:8080/decisionPermission")
                            .tag(this)
                            .params("Pphoto_seg", pBase)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("photo", response.body());
                                }
                            });
                }
            }
        });

        per_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pVideo){
                    pVideo = false;
                    per_video.setImageDrawable(getResources().getDrawable(R.drawable.tv_start));
                    OkGo.<String>post("http://192.168.0.107:8080/decisionPermission")
                            .tag(this)
                            .params("Pbase", pVideo)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("video", response.body());
                                }
                            });
                }
                else {
                    pVideo = true;
                    per_video.setImageDrawable(getResources().getDrawable(R.drawable.tv_off));
                    OkGo.<String>post("http://192.168.0.107:8080/decisionPermission")
                            .tag(this)
                            .params("Pbase", pVideo)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("video", response.body());
                                }
                            });
                }
            }
        });

        per_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pInfo){
                    pInfo = false;
                    per_info.setImageDrawable(getResources().getDrawable(R.drawable.tv_start));
                    OkGo.<String>post("http://192.168.0.107:8080/decisionPermission")
                            .tag(this)
                            .params("Pbase", pInfo)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("info", response.body());
                                }
                            });
                }
                else {
                    pInfo = true;
                    per_info.setImageDrawable(getResources().getDrawable(R.drawable.tv_off));
                    OkGo.<String>post("http://192.168.0.107:8080/decisionPermission")
                            .tag(this)
                            .params("Pbase", pInfo)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("info", response.body());
                                }
                            });
                }
            }
        });

        per_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pAdmin){
                    pAdmin = false;
                    per_admin.setImageDrawable(getResources().getDrawable(R.drawable.tv_start));
                    OkGo.<String>post("http://192.168.0.107:8080/decisionPermission")
                            .tag(this)
                            .params("Pbase", pAdmin)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("admin", response.body());
                                }
                            });
                }
                else {
                    pAdmin = true;
                    per_admin.setImageDrawable(getResources().getDrawable(R.drawable.tv_off));
                    OkGo.<String>post("http://192.168.0.107:8080/decisionPermission")
                            .tag(this)
                            .params("Pbase", pAdmin)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("admin", response.body());
                                }
                            });
                }
            }
        });

    }
}
