package com.example.wechat.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.wechat.Utils.Constant;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/9 0009 22:07
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private SharedPreferences mSp;

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

        //获取一个SP对象
        mSp = getSharedPreferences("config", MODE_PRIVATE);
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

    //显示对话框
    public void showDialog(String msg){
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            //设置对话框不可取消
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    //隐藏对话框
    public void hideDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    //保存用户名和密码
    public void saveUser(String username,String pwd){
        mSp.edit()
                .putString(Constant.SP_USERNAME, username)
                .putString(Constant.SP_PWD, pwd)
                .commit();
    }

    //获取用户名
    public String getUsername() {
        return mSp.getString(Constant.SP_USERNAME, "");
    }

    //获取密码
    public String getPwd() {
        return mSp.getString(Constant.SP_PWD, "");
    }
}
