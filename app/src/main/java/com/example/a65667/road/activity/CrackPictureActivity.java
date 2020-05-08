package com.example.a65667.road.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a65667.road.R;
import com.example.a65667.road.utils.ActivityCollectorUtil;

public class CrackPictureActivity extends AppCompatActivity {

    private ImageView rc_return;

    private String rc_Name;
    private TextView rcTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crack_picture);
        ActivityCollectorUtil.addActivity(this);

        ductIntent();
        init();

    }

    private void init(){
        rc_return = (ImageView)findViewById(R.id.rc_return);
        rcTotal = (TextView)findViewById(R.id.rc_total);
        rcTotal.setText(rc_Name);
        rc_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ductIntent(){
        rc_Name = getIntent().getStringExtra("rcName");
    }
}
