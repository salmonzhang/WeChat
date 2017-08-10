package com.example.wechat.Utils;

import android.content.res.Resources;

import com.example.wechat.gloab.WeChatApplication;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/10 0010 22:00
 */

public class Utils {

    //这个是在主线程去更新ui,在没有上下文的环境,
    public static void runOnUIThread(Runnable runnable)
    {
        WeChatApplication.mHandler.post(runnable);

    }

    //得到资源管理的类
    public static Resources getResources()
    {
        return WeChatApplication.context.getResources();
    }

    //在屏幕适配时候使用,让代码中使用dip属性
    public static int getDimens(int resId)
    {

        return getResources().getDimensionPixelSize(resId);
    }

    //得到颜色
    public static int getColor(int resId)
    {
        return getResources().getColor(resId);
    }

    //得到字符串数组信息
    public static String[] getStringArray(int resId)
    {
        return getResources().getStringArray(resId);
    }

}
