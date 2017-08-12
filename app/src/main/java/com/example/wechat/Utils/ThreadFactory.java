package com.example.wechat.Utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * author:salmonzhang
 * Description:定义一个线程管理类
 * Date:2017/8/12 0012 20:09
 */

public class ThreadFactory {
    public static Executor sThreadPool = Executors.newFixedThreadPool(3);

    //定义一个在子线程中运行的方法
    public static void runOnSubThread(Runnable runnable){
        //用线程池去执行任务
        sThreadPool.execute(runnable);
    }
}
