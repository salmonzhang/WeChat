package com.example.wechat.view;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/17 0017 23:12
 */

public interface ChatView {
    void onInitChat(List<EMMessage> emMessageList);
}
