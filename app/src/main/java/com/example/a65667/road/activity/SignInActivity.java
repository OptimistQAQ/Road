package com.example.a65667.road.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a65667.road.R;
import com.example.a65667.road.bean.User;
import com.example.a65667.road.utils.CurrentUserInfo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import cn.nodemedia.pusher.ShareBean;

public class SignInActivity extends AppCompatActivity {

    private ImageButton bt_login;
    private TextView bt_register;
    private EditText euserName;
    private EditText ePassword;
    private CheckBox remember_key;
    private CheckBox automatic_login;

    private SharedPreferences sp;

    private Boolean rem_isCheck = false;
    private Boolean auto_isCheck = false;

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

    private void init() {
        bt_login = (ImageButton) findViewById(R.id.bt_login);
        bt_register = (TextView) findViewById(R.id.register);
        euserName = (EditText) findViewById(R.id.eusername);
        ePassword = (EditText) findViewById(R.id.epassword);
        remember_key = (CheckBox) findViewById(R.id.remember_key);
        automatic_login = (CheckBox) findViewById(R.id.automatic_login);

        rem_isCheck = remember_key.isChecked();
        auto_isCheck = automatic_login.isChecked();
        remember_key.setChecked(true);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //实现自动登录
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (sp.getBoolean("rem_isCheck", false)) {
            //设置默认是记录密码的状态
            remember_key.setChecked(true);
            euserName.setText(sp.getString("user_name", ""));
            ePassword.setText(sp.getString("password", ""));

            //判断自动登录多选框状态
            if (sp.getBoolean("auto_isCheck", false)) {
                //设置默认为自动登录状态
                automatic_login.setChecked(true);
                Intent intent = new Intent(SignInActivity.this, Main2Activity.class);
                SignInActivity.this.startActivity(intent);
                finish();
            }
        }
    }

    private void initView() {

        if ("".equals(euserName.getText().toString()) || "".equals(ePassword.getText().toString())) {
            Toast.makeText(this, "请正确输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String bt_name = euserName.getText().toString();
        String bt_password = ePassword.getText().toString();
        Integer bt_time = 10;
        Integer bt_distance = 10;
        Integer bt_line = 10;

        // module间信息共享
        OkGo.<String>post("http://39.105.172.22:9596/login")
                .params("name", bt_name)
                .params("password", bt_password)
                .params("time", bt_time)
                .params("distance", bt_distance)
                .params("line", bt_line)
                .tag(this)
//                .upString(JSON.toJSONString(bt_name))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        User user = JSON.parseObject(response.body(), User.class);
                        Log.e("user_body", response.body());
                        Log.e("user_1", "" + user.getUname() + "-" + user.getUpassword());

                        if (user.getUname() != null) {
                            CurrentUserInfo.user = user;
                            CurrentUserInfo.uno = user.getUno();
                            CurrentUserInfo.name = user.getUname();
                            CurrentUserInfo.password = user.getUpassword();
                            CurrentUserInfo.time = bt_time.toString() + "小时";
                            CurrentUserInfo.distance = bt_distance.toString() + "公里";
                            CurrentUserInfo.line = bt_line.toString() + "条";
                            CurrentUserInfo.profilePhoto = user.getUprofilePhoto();
                            Log.e("123", CurrentUserInfo.name);
                            ShareBean.uname = user.getUname();
                            ShareBean.unickName = user.getUnickName();
                            ShareBean.uno = user.getUno();
                            ShareBean.upassword = user.getUpassword();
                            ShareBean.uprofilePhoto = user.getUprofilePhoto();
                            ShareBean.utitle = user.getUtitle();
                            ShareBean.utotalTime = user.getUtotalTime();
                            ShareBean.utotalDistance = user.getUtotalDistance();
                            ShareBean.utotalLine = user.getUtotalLine();

                            Toast.makeText(SignInActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("user_name", bt_name);
                            editor.putString("password", bt_password);
                            editor.putBoolean("rem_isCheck", rem_isCheck);
                            editor.putBoolean("auto_isCheck", auto_isCheck);
                            editor.commit();
                            Intent main2Activity = new Intent(SignInActivity.this, Main2Activity.class);
                            startActivity(main2Activity);
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, "账号或密码错误，无法登录", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
