package com.example.wechat.presenter;

import com.example.wechat.view.AddFriendView;

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

    }
}
