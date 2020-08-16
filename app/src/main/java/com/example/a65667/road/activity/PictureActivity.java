package com.example.a65667.road.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a65667.road.R;

public class PictureActivity extends AppCompatActivity {

    private ImageView imageView;
    private String source, sourceLen, sourceWidth, sourceHole;
    private TextView imgLen, imgWidth, imgHole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        imageView = (ImageView) findViewById(R.id.image_picture);
        imgLen = (TextView) findViewById(R.id.img_len);
        imgWidth = (TextView) findViewById(R.id.img_width);
        imgHole = (TextView) findViewById(R.id.img_hole);
        source = getIntent().getStringExtra("source");
        sourceLen = getIntent().getStringExtra("sourceLen");
        sourceWidth = getIntent().getStringExtra("sourceWidth");
        sourceHole = getIntent().getStringExtra("sourceHole");
        Log.e("source", source);
        Glide.with(PictureActivity.this).load(source).into(imageView);
        imgLen.setText("裂缝长度：" + sourceLen);
        imgWidth.setText("裂缝最大宽度：" + sourceWidth);
        if (sourceHole.equals("single_crack")) {
            imgHole.setText("破损类型：横向裂纹");
        } else {
            imgHole.setText("破损类型：纵向裂纹");
        }
    }
}
