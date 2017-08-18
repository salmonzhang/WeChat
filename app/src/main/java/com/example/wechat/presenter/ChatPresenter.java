package com.example.wechat.presenter;

import com.hyphenate.chat.EMMessage;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/17 0017 23:14
 */

public interface ChatPresenter {
    void initChat(String username);

    void loadMoreMsg(String username);

    void sendTextMsg(String msg, String username);

    void addMessage(EMMessage message);
}
