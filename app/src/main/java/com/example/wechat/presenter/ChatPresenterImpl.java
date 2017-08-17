package com.example.wechat.presenter;

import com.example.wechat.view.ChatView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/17 0017 23:15
 */

public class ChatPresenterImpl implements ChatPresenter {
    public static final int PAGE_SIZE = 0;
    private List<EMMessage> mEMMessageList = new ArrayList<>();
    private ChatView mChatView;

    public ChatPresenterImpl(ChatView chatView) {
        mChatView = chatView;
    }

    @Override
    public void initChat(String username) {
        //从环信服务器获取与当前传入的username的聊天记录
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        if (conversation != null) {
            //获取最近一次的聊天记录
            EMMessage lastMessage = conversation.getLastMessage();
            ////获取当前msgId之前的PAGE_SIZE条
            List<EMMessage> emMessages = conversation.loadMoreMsgFromDB(lastMessage.getMsgId(), PAGE_SIZE - 1);
            //将最近的一次聊天记录，与前面的聊天记录一起放在mEMMessageList中
            mEMMessageList.addAll(emMessages);
            mEMMessageList.add(lastMessage);
        }

        mChatView.onInitChat(mEMMessageList);

    }
}
