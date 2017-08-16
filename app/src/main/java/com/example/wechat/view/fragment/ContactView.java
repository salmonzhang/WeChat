package com.example.wechat.view.fragment;

import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/14 0014 17:17
 */

public interface ContactView {

    void onInitContacts(List<String> contactsList);

    void onUpdateContact(boolean isSuccess, String message);

    void onDeleteContact(boolean isSuccess, String contact, String message);
}
