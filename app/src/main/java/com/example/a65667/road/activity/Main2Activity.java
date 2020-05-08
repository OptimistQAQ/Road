package com.example.a65667.road.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.example.a65667.road.R;
import com.example.a65667.road.utils.ActivityCollectorUtil;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActivityCollectorUtil.addActivity(this);

        //申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.setStatusBarColor( getResources().getColor( R.color.white) );
            window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR  );
        }
        requestPermissions();
    }

    //权限申请
    private void requestPermissions() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,Manifest.permission.CAMERA};
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for(int i = 0;i < permissions.length;i ++) {
                    int permission = ActivityCompat.checkSelfPermission( this, permissions[i]);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( this,permissions, 0x0010 );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
