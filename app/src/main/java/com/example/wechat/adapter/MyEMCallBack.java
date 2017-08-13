package com.example.wechat.adapter;

import com.example.wechat.Utils.ThreadFactory;
import com.hyphenate.EMCallBack;

/**
 * author:salmonzhang
 * Description:使用适配器模式实现EMCallBack接口，保证方法在主线程中运行
 * Date:2017/8/13 0013 20:29
 */

public abstract class MyEMCallBack implements EMCallBack {
    @Override
    public void onSuccess() {
        ThreadFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onMainSuccess();
            }
        });
    }

    public abstract void onMainSuccess();

    @Override
    public void onError(final int i, final String s) {
        ThreadFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onMainError(i,s);
            }
        });
    }

    public abstract void onMainError(int code,String message);

    @Override
    public void onProgress(int i, String s) {

    }
}
