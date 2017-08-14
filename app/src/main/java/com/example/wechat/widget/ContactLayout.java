package com.example.wechat.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wechat.R;
import com.example.wechat.gloab.WeChatApplication;

import butterknife.BindView;

/**
 * author:salmonzhang
 * Description:自定义组合控件
 * Date:2017/8/14 0014 16:32
 */

public class ContactLayout extends RelativeLayout {
    @BindView(R.id.rv_contact)
    RecyclerView mRvContact;
    @BindView(R.id.srl_contact)
    SwipeRefreshLayout mSrlContact;
    @BindView(R.id.tv_floatView)
    TextView mTvFloatView;
    @BindView(R.id.slideBar)
    SlideBar mSlideBar;

    public ContactLayout(Context context) {
        this(context, null);
    }

    public ContactLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //1.将contact_layout作为子控件添加到当前ViewGroup中
        LayoutInflater.from(WeChatApplication.context).inflate(R.layout.contact_layout, this, true);

    }
}
