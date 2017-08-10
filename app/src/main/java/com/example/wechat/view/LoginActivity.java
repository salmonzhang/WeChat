package com.example.wechat.view;

import android.os.Bundle;

import com.example.wechat.R;

public class LoginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        StatusBarUtil.setColor(LoginActivity.this,100);
    }

    @Override
    public boolean isEnableTranslucentStatus() {
        return true;
    }
}
