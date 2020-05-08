package com.example.a65667.road.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    private CheckBox show_pass;
    private CheckBox show_pass_again;

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
        show_pass = (CheckBox) findViewById(R.id.show_pass);
        show_pass_again = (CheckBox) findViewById(R.id.show_pass_again);

        ePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        rePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        //监听显示密码多选框事件
        show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (show_pass.isChecked()) {
                    //如果选中，显示密码
                    ePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    ePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //监听显示密码多选框事件
        show_pass_again.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (show_pass_again.isChecked()) {
                    //如果选中，显示密码
                    ePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    ePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
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
