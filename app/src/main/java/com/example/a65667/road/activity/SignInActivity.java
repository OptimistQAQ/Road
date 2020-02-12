package com.example.a65667.road.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a65667.road.R;

public class SignInActivity extends AppCompatActivity {

    ImageButton bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                Intent main2Activity = new Intent(SignInActivity.this, Main2Activity.class);
                startActivity(main2Activity);
            }
        });

    }

    private void init(){
        bt_login = (ImageButton)findViewById(R.id.bt_login);
    }

}
