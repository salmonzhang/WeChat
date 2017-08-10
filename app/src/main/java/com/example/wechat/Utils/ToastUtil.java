package com.example.wechat.Utils;

import android.widget.Toast;

import com.example.wechat.gloab.WeChatApplication;

/**
 * author:salmonzhang
 * Description:功能强大的吐司
 * Date:2017/8/10 0010 22:06
 */

public class ToastUtil {

    public static Toast sToast;

    public static void showToast(final String msg){

        Utils.runOnUIThread(new Runnable() {//吐司只能在主线程中弹
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = Toast.makeText(WeChatApplication.context, msg, Toast.LENGTH_SHORT);
                } else {
                    sToast.setText(msg);//如果不为空，则直接改变当前toast的文本
                }
                sToast.show();
            }
        });
    }
}
