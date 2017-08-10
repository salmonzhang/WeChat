package com.example.wechat.Utils;

import android.util.Log;

/**
 * author:salmonzhang
 * Description:日志工具类，测试的时候，工具打开，发布的时候工具关闭
 * Date:2017/8/10 0010 21:50
 */

public class LogUtils {

    public static final boolean isShowLog = true;

    public static final String tag = "WeChat";

    //i级别
    public static void i(String message){
        if (isShowLog) {
            Log.i(tag, message);
        }
    }

    //d级别
    public static void d(String message){
        if (isShowLog) {
            Log.i(tag, message);
        }
    }

}
