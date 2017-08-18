package com.example.wechat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/18 0018 19:55
 */

public class ChatAdapter extends RecyclerView.Adapter <ChatAdapter.ChatViewHolder>{

    private static final int SEND_TXT = 1;
    private static final int RECEIVE_TXT = 2;
    private static final int SEND_IMAGE = 3;
    private static final int RECEIVE_IMAGE = 4;
    private List<EMMessage> mMessageList;

    public ChatAdapter(List<EMMessage> messageList) {
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList == null?0:mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        /**
         * 根据position的位置判断当前条目的类型
         * 类型分类：
         * 发送的消息、接收的消息、发送的图片、接收的图片
         */
        //根据position获取当前条目对应的数据
        EMMessage emMessage = mMessageList.get(position);
        //获取条目的类型（判断是消息还是图片）
        EMMessage.Type type = emMessage.getType();
        //获取条目的方向（判断是接收还是发送）SEND, RECEIVE;
        EMMessage.Direct direct = emMessage.direct();
        switch (type) {
            case TXT:
                if (direct == EMMessage.Direct.SEND) {
                    return SEND_TXT;
                } else {
                    return RECEIVE_TXT;
                }
            case IMAGE:
                if (direct == EMMessage.Direct.SEND) {
                    return SEND_IMAGE;
                } else {
                    return RECEIVE_IMAGE;
                }
            default:
                break;
        }

        return super.getItemViewType(position);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

    }


    class ChatViewHolder extends RecyclerView.ViewHolder {

        public ChatViewHolder(View itemView) {
            super(itemView);
        }
    }
}
