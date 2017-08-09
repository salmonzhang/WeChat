package com.example.wechat.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/9 0009 22:07
 */

public class BaseActivity extends AppCompatActivity {

    //启动Activity,并判断是否销毁前一个Activity
    public void startActivity(Class<? extends BaseActivity> clazz , boolean isFinish){
        startActivity(new Intent(this,clazz));
        if (isFinish) {
            finish();
        }
    }
}
