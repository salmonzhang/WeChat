package com.example.wechat.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wechat.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/18 0018 19:55
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

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
        return mMessageList == null ? 0 : mMessageList.size();
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
        View view = null;

        switch (viewType) {
            case SEND_TXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_send_text, parent, false);
                break;
            case RECEIVE_TXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_receive_text, parent, false);
                break;
            case SEND_IMAGE:

                break;
            case RECEIVE_IMAGE:

                break;
        }

        ChatViewHolder chatViewHolder = new ChatViewHolder(view);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        EMMessage emMessage = mMessageList.get(position);
        //设置时间
        long msgTime = emMessage.getMsgTime();
        //将时间转换为字符串的形式
        String times = DateUtils.getTimestampString(new Date(msgTime));
        processTime(holder, position, msgTime, times);

        //处理消息
        EMMessageBody body = emMessage.getBody();
        if (emMessage.getType() == EMMessage.Type.TXT) {
            EMTextMessageBody textMessageBody = (EMTextMessageBody) body;
            String message = textMessageBody.getMessage();
            holder.tvMsg.setText(message);
        } else if (emMessage.getType() == EMMessage.Type.IMAGE) {
            // TODO: 2017/8/18 后续处理
        }

        //处理发送的状态（发送消息特有）
        EMMessage.Status status = emMessage.status();
        if (emMessage.direct() == EMMessage.Direct.SEND) {
            //判断发送的是文本还是图片
            if (emMessage.getType() == EMMessage.Type.TXT) {
                switch (status) {
                    case CREATE://新创建

                    case INPROGRESS://正在发送
                        holder.ivStatus.setVisibility(View.VISIBLE);
                        //将帧动画资源设置给ImageViewStatus
                        holder.ivStatus.setImageResource(R.drawable.sending_frame_anim);
                        //播放帧动画
                        AnimationDrawable animationDrawable = (AnimationDrawable) holder.ivStatus.getDrawable();
                        if (animationDrawable.isRunning()) {
                            animationDrawable.stop();
                        }
                        animationDrawable.start();
                        break;
                    case SUCCESS:
                        holder.ivStatus.setVisibility(View.GONE);
                        break;
                    case FAIL:
                        holder.ivStatus.setVisibility(View.VISIBLE);
                        holder.ivStatus.setImageResource(R.mipmap.msg_error);
                        break;
                }
            } else if (emMessage.getType() == EMMessage.Type.IMAGE) {
                // TODO: 2017/8/18 后续处理
            }
        }
    }

    //处理时间的显示
    private void processTime(ChatViewHolder holder, int position, long msgTime, String times) {
        //判断时间是否要显示
        if (position == 0) {
            //一定要显示
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(times);
        } else {
            /**
             * 判断当前消息的时间与上一条消息的时间差是否大于60秒
             * 如果大于60秒，则显示
             * 如果小于60秒，则不显示
             */
            //获取上一条消息的时间
            EMMessage preEmMessage = mMessageList.get(position - 1);
            long preMsgTime = preEmMessage.getMsgTime();
            if ((msgTime - preMsgTime) > 60000) {
                //显示时间
                holder.tvTime.setVisibility(View.VISIBLE);
                holder.tvTime.setText(times);
            } else {
                //不显示时间
                holder.tvTime.setVisibility(View.GONE);
            }
        }
    }


    class ChatViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTime;
        private ImageView ivStatus;
        private TextView tvMsg;

        public ChatViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            ivStatus = (ImageView) itemView.findViewById(R.id.iv_status);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
        }
    }
}
