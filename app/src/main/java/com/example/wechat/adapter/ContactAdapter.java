package com.example.wechat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wechat.R;
import com.example.wechat.Utils.StringUtils;

import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/15 0015 19:33
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<String> mContactList;

    public ContactAdapter(List<String> contactsList) {
        mContactList = contactsList;
    }

    @Override
    public int getItemCount() {
        return mContactList == null?0:mContactList.size();
    }

    //创建布局
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contact,parent,false);
        return new ContactViewHolder(view);
    }



    //绑定数据
    @Override
    public void onBindViewHolder(ContactViewHolder holder,int position) {
        String contact = mContactList.get(position);

        //填充数据
        holder.mTvUsername.setText(contact);
        //获取联系人的首字母
        String firstNum = StringUtils.getInitial(contact);
        holder.mTvSection.setText(firstNum);

        /**
         * 1:如果position = 0,则首字母显示
         * 2:如果position != 0,则判断当前位置的首字母与前一个的首字母是否相等，
         *      2.1 如果相等，则不显示
         *      2.2 如果不相等，则显示
         */
        if (position == 0) {
            holder.mTvSection.setVisibility(View.VISIBLE);
        } else {
            String preFirstNum = StringUtils.getInitial(mContactList.get(position - 1));
            if (preFirstNum.equalsIgnoreCase(firstNum)) {
                holder.mTvSection.setVisibility(View.GONE);
            } else {
                holder.mTvSection.setVisibility(View.VISIBLE);
            }
        }
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        private final TextView mTvSection;
        private final TextView mTvUsername;

        public ContactViewHolder(View itemView) {
            super(itemView);
            mTvSection = (TextView) itemView.findViewById(R.id.tv_section);
            mTvUsername = (TextView) itemView.findViewById(R.id.tv_username);
        }
    }
}
