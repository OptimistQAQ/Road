package com.example.a65667.road.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a65667.road.R;

public class RegisterActivity extends AppCompatActivity {

    ImageButton bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            Intent signInActivity = new Intent(RegisterActivity.this, SignInActivity.class);
            startActivity(signInActivity);
            finish();
        }
        });
    }

    private void init(){
        bt_register = (ImageButton)findViewById(R.id.bt_register);
    }

}
