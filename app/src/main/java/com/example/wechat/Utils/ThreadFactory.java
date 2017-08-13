package com.example.wechat.Utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * author:salmonzhang
 * Description:定义一个线程管理类
 * Date:2017/8/12 0012 20:09
 */

public class ThreadFactory {
    public static Executor sThreadPool = Executors.newFixedThreadPool(3);

    /**
     * 传入Looper.getMainLooper()，保证该Handler一定在主线程中运行
     * 如果Handler是默认的构造函数，则会从当前线程中找Loop对象
     */

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    //封装一个在子线程中运行的方法
    public static void runOnSubThread(Runnable runnable){
        //用线程池去执行任务
        sThreadPool.execute(runnable);
    }

    //封装一个在主线程中运行的方法
    public static void runOnUiThread(Runnable runnable) {
        sHandler.post(runnable);
    }
}
