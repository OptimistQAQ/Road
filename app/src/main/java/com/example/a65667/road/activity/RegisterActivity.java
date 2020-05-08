package com.example.a65667.road.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a65667.road.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton bt_register;
    private EditText euserName1;
    private EditText ePassword, rePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
        }
        });
    }

    private void init(){
        bt_register = (ImageButton)findViewById(R.id.bt_register);
        euserName1 = (EditText)findViewById(R.id.eusername1);
        ePassword = (EditText)findViewById(R.id.epassword);
        rePassword = (EditText) findViewById(R.id.repassword);
    }

    private void initView(){
        if("".equals(euserName1.getText().toString())){
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(ePassword.getText().toString())){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(rePassword.getText().toString())){
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = ePassword.getText().toString();
        if(!password.equals(rePassword.getText().toString()))
        {
            Toast.makeText(this, "两次输入密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals(rePassword.getText().toString()))
        {
            Map<String, String> param = new HashMap<>();
            param.put("name", euserName1.getText().toString());
            param.put("password", ePassword.getText().toString());
            OkGo.<String>post("http://39.105.172.22:9596/register")
                    .params("name", euserName1.getText().toString())
                    .params("password", ePassword.getText().toString())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("pass", rePassword.getText().toString());
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent signInActivity = new Intent(RegisterActivity.this, SignInActivity.class);
                            startActivity(signInActivity);
                            finish();
                        }
                    });
        }
    }
}
