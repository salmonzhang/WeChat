package com.example.wechat.presenter;

import com.hyphenate.chat.EMMessage;

import java.io.File;

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

    //发送图片消息
    void sendImageMsg(File mFile, String username);
}
