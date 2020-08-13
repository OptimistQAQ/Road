package com.example.a65667.road.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a65667.road.R;

public class HWResultActivity extends AppCompatActivity {

    private int crack_weight = 0;
    private String img_base64 = "";
    private ImageView ivResult;
    private TextView tvLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hwresult);
        initView();
        duceIntent();
    }

    private void initView() {
        ivResult = findViewById(R.id.iv_hw_result);
        tvLevel = findViewById(R.id.tv_crack_level);
    }

    private void duceIntent() {
        crack_weight = getIntent().getIntExtra("weight", 0);
        img_base64 = getIntent().getStringExtra("gt");
        byte[] decodedString = Base64.decode(img_base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        tvLevel.setText("破损指数：" + crack_weight);
        ivResult.setImageBitmap(decodedByte);
    }
}
