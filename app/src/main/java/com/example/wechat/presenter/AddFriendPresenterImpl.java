package com.example.wechat.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.wechat.Utils.ThreadFactory;
import com.example.wechat.view.AddFriendView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import static com.hyphenate.chat.a.a.a.f;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/17 0017 11:21
 */

public class AddFriendPresenterImpl implements AddFriendPresenter {
    private AddFriendView mAddFriendView;
    public AddFriendPresenterImpl(AddFriendView addFriendView) {
        mAddFriendView = addFriendView;
    }

    @Override
    public void search(String keyword) {
        //从云数据库平台查询数据
        /**
         * 1:修改云数据库上的find权限
         * 2：云数据库提供的查询代码AVQuery<AVUser> userQuery = new AVQuery<>("_User");
         */
        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        //指定查询的字段
        userQuery.whereContains("username", keyword);
        //不包含当前用户本身
        String currentUser = EMClient.getInstance().getCurrentUser();
        userQuery.whereNotEqualTo("username", currentUser);

        //执行查询操作
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e != null) {//有异常
                    mAddFriendView.onSearchResult(false,null,e.getMessage());
                } else {//无异常
                    mAddFriendView.onSearchResult(true,list,"success");
                }
            }
        });

    }

    //给环信服务器发送添加好友的请求
    @Override
    public void AddFriendRequest(final String username, final String reason) {
        ThreadFactory.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {//请求成功
                    EMClient.getInstance().contactManager().addContact(username,reason);
                    ThreadFactory.runOnSubThread(new Runnable() {
                        @Override
                        public void run() {
                            mAddFriendView.onAddResult(true,username,"success");
                        }
                    });
                } catch (final HyphenateException e) {//请求失败
                    e.printStackTrace();
                    ThreadFactory.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAddFriendView.onAddResult(false,username,e.getMessage());
                        }
                    });
                }
            }
        });
    }
}
