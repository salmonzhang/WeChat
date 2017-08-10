package com.example.wechat.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/9 0009 22:07
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (isEnableTranslucentStatus()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {// 4.4 全透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {// 5.0 全透明实现
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);// calculateStatusColor(Color.WHITE, (int) alphaValue)
            }
            // 以上代码基本解决适配各种版本全透明状态栏（如导航栏有需求可以再加导航栏）
        }
    }

    //是否要实现沉浸式状态栏
    public boolean isEnableTranslucentStatus() {
        return false;
    }

    //启动Activity,并判断是否销毁前一个Activity
    public void startActivity(Class<? extends BaseActivity> clazz , boolean isFinish){
        startActivity(new Intent(this,clazz));
        if (isFinish) {
            finish();
        }
    }
}
