package com.example.wechat.presenter;

import com.example.wechat.adapter.MyEMCallBack;
import com.example.wechat.view.ChatView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/17 0017 23:15
 */

public class ChatPresenterImpl implements ChatPresenter {
    public static final int PAGE_SIZE = 20;
    private List<EMMessage> mEMMessageList = new ArrayList<>();
    private ChatView mChatView;
    private String username;

    public ChatPresenterImpl(ChatView chatView) {
        mChatView = chatView;
    }

    @Override
    public void initChat(String username) {
        this.username = username;
        mEMMessageList.clear();
        //从环信服务器获取与当前传入的username的聊天记录
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        if (conversation != null) {
            //获取最近一次的聊天记录
            EMMessage lastMessage = conversation.getLastMessage();
            //获取当前msgId之前的PAGE_SIZE条
            List<EMMessage> emMessages = conversation.loadMoreMsgFromDB(lastMessage.getMsgId(), PAGE_SIZE - 1);
            //将最近的一次聊天记录，与前面的聊天记录一起放在mEMMessageList中
            mEMMessageList.addAll(emMessages);
            mEMMessageList.add(lastMessage);
        }

        mChatView.onInitChat(mEMMessageList);

    }

    @Override
    public void loadMoreMsg(String username) {
        /**
         * 1:获取与当前username的会话
         * 2：获取mEMMessageList的第一条消息
         * 3：根据第一条消息获取更老的PAGE_SIZE条消息
         * 4：将获取的结果返回给V层
         */
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        if (conversation != null) {
            EMMessage emMessage = mEMMessageList.get(0);
            List<EMMessage> MoreEmMessages = conversation.loadMoreMsgFromDB(emMessage.getMsgId(), PAGE_SIZE);
            //将获取到的消息添加到mEMMessageList中（注：是从mEMMessageList索引0开始添加）
            mEMMessageList.addAll(0, MoreEmMessages);
            mChatView.onLoadMore(true,MoreEmMessages.size());
        } else {
            mChatView.onLoadMore(false,0);
        }

    }

    @Override
    public void sendTextMsg(String msg, String username) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(msg, username);
        //将发送的消息添加到mEMMessageList中
        mEMMessageList.add(message);
        //让ChatAdapter更新界面
        mChatView.onChatUpdate();
        //监听消息发送的状态
        message.setMessageStatusCallback(new MyEMCallBack() {
            @Override
            public void onMainSuccess() {
                //让ChatAdapter更新界面
                mChatView.onChatUpdate();
            }

            @Override
            public void onMainError(int code, String message) {
                //让ChatAdapter更新界面
                mChatView.onChatUpdate();
            }
        });

        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    @Override
    public void addMessage(EMMessage message) {
        mEMMessageList.add(message);
    }

    //发送图片消息
    @Override
    public void sendImageMsg(File mFile, String username) {
        /**
         * 1:创建一条图片消息EMMessage
         * 2：将创建的message显示到界面，即将消息添加到mEMMessageList集合中
         * 3：让ChatAdapter更新界面
         * 4：发送图片消息
         */
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage message = EMMessage.createImageSendMessage(mFile.getAbsolutePath(), false, username);
        mEMMessageList.add(message);
        mChatView.onChatUpdate();
        EMClient.getInstance().chatManager().sendMessage(message);

    }
}
