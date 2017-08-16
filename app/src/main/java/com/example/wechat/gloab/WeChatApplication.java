package com.example.wechat.gloab;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.example.wechat.event.ContactUpdateEvent;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.List;


/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/9 0009 21:15
 */

public class WeChatApplication extends Application {

    public static Context context;
    public static final String TAG = "WeChatApplication";
    public static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mHandler = new Handler();

        //初始化环信
        initHuanXin();
        //初始化LeanCloud
        initLeanCloud();
        //初始化环信好友变化监听
        initContactListener();
    }

    private void initContactListener() {
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
                Log.d(TAG, "onContactAdded: ");
                //使用EventBus传递消息
                EventBus.getDefault().post(new ContactUpdateEvent(username,true));
            }

            @Override
            public void onContactDeleted(String username) {
                //删除了联系人时回调此方法
                Log.d(TAG, "onContactDeleted: ");
                EventBus.getDefault().post(new ContactUpdateEvent(username,false));
            }

            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
                Log.d(TAG, "onContactInvited: ");
            }

            @Override
            public void onFriendRequestAccepted(String username) {
                //好友请求被接收
                Log.d(TAG, "onFriendRequestAccepted: ");
            }

            @Override
            public void onFriendRequestDeclined(String username) {
                //好友请求被拒绝
                Log.d(TAG, "onFriendRequestDeclined: ");
            }
        });
    }

    private void initLeanCloud() {
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"VSBHMjLlpVrm0Ht2xSqBvg7L-gzGzoHsz","coR7oIzfmqRkfBI4shwkT6Vt");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
    }

    private void initHuanXin() {
        EMOptions options = new EMOptions();
    // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
    // 如果APP启用了远程的service，此application:onCreate会被调用2次
    // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
    // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(getPackageName())) {
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

    //初始化
        EMClient.getInstance().init(this, options);
    //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

}
