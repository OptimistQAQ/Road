package com.example.a65667.road.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a65667.road.R;
import com.example.a65667.road.bean.User;
import com.example.a65667.road.utils.CurrentUserInfo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private ImageButton bt_login;
    private EditText euserName;
    private EditText ePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });

    }

    private void init(){
        bt_login = (ImageButton)findViewById(R.id.bt_login);
        euserName = (EditText) findViewById(R.id.eusername);
        ePassword = (EditText) findViewById(R.id.epassword);
    }

    private void initView(){

        if ("".equals(euserName.getText().toString()) || "".equals(ePassword.getText().toString())){
            Toast.makeText(this, "请正确输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        final String bt_name = euserName.getText().toString();
        String bt_password = ePassword.getText().toString();
        User signIn = new User();
        signIn.setUname(bt_name);
        signIn.setUpassword(bt_password);

        Map<String, String> param = new HashMap<>();

        OkGo.<String>post("http://192.168.0.103:8080/login")
                .params("name", bt_name)
                .params("password", bt_password)
                .tag(this)
//                .upString(JSON.toJSONString(param))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        User user = JSON.parseObject(response.body(), User.class);
                        Log.e("user", response.body());
                        Log.e("user_1", "" + user.getUname() + "-" + user.getUpassword());

                        if (user.getUname() != null) {
                            CurrentUserInfo.user = user;
                            CurrentUserInfo.uno = user.getUno();
                            CurrentUserInfo.name = user.getUname();
                            CurrentUserInfo.password = user.getUpassword();
                            Log.e("123", CurrentUserInfo.name);
                        }

                        Toast.makeText(SignInActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        Intent main2Activity = new Intent(SignInActivity.this, Main2Activity.class);
                        startActivity(main2Activity);
                        finish();
                    }
                });
    }

}
