package com.example.a65667.road.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.a65667.road.R;
import com.example.a65667.road.utils.ActivityCollectorUtil;
import com.example.a65667.road.utils.CameraUtils;
import com.example.a65667.road.utils.HWPusher;
import com.example.a65667.road.utils.ImageUtils;
import com.example.a65667.road.view.MySurfaceView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class CamActivity extends AppCompatActivity implements View.OnClickListener {

    private MySurfaceView mySurfaceView;
    private Button btTake;
    private int mOrientation;
    private Camera.AutoFocusCallback myAutoFocusCallback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
        ActivityCollectorUtil.addActivity(this);

        initView();
    }

    private void initView() {
        mOrientation = CameraUtils.calculateCameraPreviewOrientation(CamActivity.this);
        mySurfaceView = findViewById(R.id.mSurfaceView);
        btTake = findViewById(R.id.bt_take_pic);
        CameraUtils.startPreview();
        btTake.setOnClickListener(this);

        mySurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUtils.getmCamera().autoFocus(myAutoFocusCallback);
            }
        });

        myAutoFocusCallback = (success, camera) -> {
            // TODO Auto-generated method stub
            if (success)//success表示对焦成功
            {
                Log.i("autofocus", "myAutoFocusCallback: success...");
                //myCamera.setOneShotPreviewCallback(null);

            } else {
                //未对焦成功
                Log.i("autofocus", "myAutoFocusCallback: 失败了...");
            }
        };
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private void takePicture() {

        Log.e("123", "take_picture");

        CameraUtils.takePicture(new Camera.ShutterCallback() {
            @Override
            public void onShutter() {

            }
        }, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {


                CameraUtils.stopPreview();

                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                if (bitmap != null) {
                    bitmap = ImageUtils.getRotatedBitmap(bitmap, mOrientation);
                    String path = Environment.getExternalStorageDirectory() + "/DCIM/Camera/"
                            + System.currentTimeMillis() + ".jpg";

                    try {
                        FileOutputStream fout = new FileOutputStream(path);
                        BufferedOutputStream bos = new BufferedOutputStream(fout);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                        bos.flush();
                        bos.close();
                        fout.close();

                        Luban.with(getApplicationContext())
                                .load(new File(path))
                                .ignoreBy(100)
                                .setFocusAlpha(false)
                                .setTargetDir(getPath())
                                .filter(path1 -> true)
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        HWPusher hw = new HWPusher();
                                        hw.push(file, CamActivity.this);

                                    }
                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                }).launch();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_take_pic:
                takePicture();
                break;
        }
    }

}
