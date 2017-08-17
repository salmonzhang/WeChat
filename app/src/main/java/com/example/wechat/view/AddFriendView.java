package com.example.wechat.view;

import com.avos.avoscloud.AVUser;

import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/17 0017 11:19
 */

public interface AddFriendView {
    void onSearchResult(boolean isSuccess, List<AVUser> list, String message);
}
