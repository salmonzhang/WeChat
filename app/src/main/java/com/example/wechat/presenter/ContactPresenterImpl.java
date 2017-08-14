package com.example.wechat.presenter;

import com.example.wechat.view.fragment.ContactView;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/14 0014 17:18
 */

public class ContactPresenterImpl implements ContactPresenter {

    private  ContactView mContactView;

    public ContactPresenterImpl(ContactView contactView) {
        this.mContactView = contactView;
    }

    @Override
    public void initContact() {
        /**
         * 1. ContactFragment初始化时加载好友列表
         * 2. 步骤
         * 1. 当初始化通讯录时，先获取本地缓存的通讯录(P)
         * 2. 获取到本地的通讯录后，直接显示到界面(V)
         * 3. 访问环信网络，获取最新的通讯录(P)
         * 4. 当获取到网络上返回的通讯录时，更新界面，(V)
         * 5. 缓存到本地(P)
         */
    }
}
