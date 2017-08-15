package com.example.wechat.presenter;

import com.example.wechat.Utils.ThreadFactory;
import com.example.wechat.db.DBUtils;
import com.example.wechat.view.fragment.ContactView;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/14 0014 17:18
 */

public class ContactPresenterImpl implements ContactPresenter {

    private  ContactView mContactView;
    private List<String> mContactsList = new ArrayList<>();

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

        //从本地获取缓存数据
        final String currentUser = EMClient.getInstance().getCurrentUser();
        List<String> contacts = DBUtils.getContact(currentUser);

        //将缓存获取到的数据保存在P层的集合中
        if (mContactsList != null) {
            mContactsList.clear();
            mContactsList.addAll(contacts);
        }

        //将P层的集合数据返回到V层
        if (mContactsList != null && mContactsList.size() > 0) {
            mContactView.onInitContacts(mContactsList);
        }

        //从环信服务器上获取通讯录数据
        EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> contacts) {//获取数据成功
                //将数据添加到集合中
                if (mContactsList != null) {
                    mContactsList.clear();
                    mContactsList.addAll(contacts);
                }
                //对数据集合进行排序
                Collections.sort(mContactsList, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareToIgnoreCase(o2);
                    }
                });

                //更新本地缓存数据
                DBUtils.updateContacts(mContactsList,currentUser);
                ThreadFactory.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mContactView.onUpdateContact(true,"success");
                    }
                });
            }

            @Override
            public void onError(int i, final String s) {
                //获取数据失败
                ThreadFactory.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mContactView.onUpdateContact(false,s);
                    }
                });
            }
        });
    }
}
