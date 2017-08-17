package com.example.wechat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.wechat.R;
import com.example.wechat.Utils.StringUtils;
import com.example.wechat.db.DBUtils;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/17 0017 13:09
 */

public class AddFriendAdapter extends RecyclerView.Adapter <AddFriendAdapter.AddFriendViewHolder>{

    private List<AVUser> mShowItems = new ArrayList<>();
    private final List<String> mContact;

    public AddFriendAdapter(List<AVUser> list) {
        mShowItems = list;
        //从数据库中获取当前用户的好友列表
        mContact = DBUtils.getContact(EMClient.getInstance().getCurrentUser());
    }

    //创建布局
    @Override
    public AddFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_addfriend, parent, false);
        return new AddFriendViewHolder(view);
    }
    //填充数据
    @Override
    public void onBindViewHolder(AddFriendViewHolder holder, int position) {
        AVUser avUser = mShowItems.get(position);
        final String username = avUser.getUsername();
        holder.tvUsername.setText(username);
        Date date = avUser.getCreatedAt();
        holder.tvTime.setText(StringUtils.getDateString(date));

        //判断当前用户是否是好友，如果是则Button显示已经添加，如果不是则显示添加
        if (mContact.contains(username)) {
            //已经是好友
            holder.btnAdd.setText("已经添加");
            holder.btnAdd.setEnabled(false);
        } else {
            //不是好友
            holder.btnAdd.setText("添加");
            holder.btnAdd.setEnabled(true);
            holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnAddFriendClickListener != null) {
                        mOnAddFriendClickListener.onAddFriendClick(username);
                    }
                 }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mShowItems.size();
    }

    private OnAddFriendClickListener mOnAddFriendClickListener;
    public void setOnAddFriendClickListener(OnAddFriendClickListener onAddFriendClickListener){
        this.mOnAddFriendClickListener = onAddFriendClickListener;
    }
    public interface OnAddFriendClickListener{
        void onAddFriendClick(String username);
    }

    public class AddFriendViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivAvatar;
        private TextView tvUsername;
        private TextView tvTime;
        private Button btnAdd;
        public AddFriendViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvUsername = (TextView) itemView.findViewById(R.id.tv_username);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            btnAdd = (Button) itemView.findViewById(R.id.btn_add);
        }
    }
}
