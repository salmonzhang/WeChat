package com.example.wechat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wechat.R;
import com.example.wechat.Utils.ToastUtil;
import com.example.wechat.widget.KeyboardListenerLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_chat)
    RecyclerView mRvChat;
    @BindView(R.id.srl_chat)
    SwipeRefreshLayout mSrlChat;
    @BindView(R.id.et_msg)
    EditText mEtMsg;
    @BindView(R.id.btn_send)
    Button mBtnSend;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.iv_camera)
    ImageView mIvCamera;
    @BindView(R.id.ll_root)
    KeyboardListenerLinearLayout mLlRoot;
    private String mUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取传过来的联系人
        Intent intent = getIntent();
        mUsername = intent.getStringExtra("contact");
        if (TextUtils.isEmpty(mUsername)) {
            ToastUtil.showToast("没找到对应的联系人");
            finish();
            return;
        }
        //初始化ToolBar
        initToolBar();

        //初始化EditText
        initEditText();
    }

    private void initEditText() {
        String message = mEtMsg.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mBtnSend.setEnabled(false);
        } else {
            mBtnSend.setEnabled(false);
        }

        //给EditText设置文本改变监听
        mEtMsg.addTextChangedListener(this);
    }

    private void initToolBar() {
        mTvTitle.setText("");
        mToolbar.setTitle("与" + mUsername + "聊天中");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString().trim();
        if (TextUtils.isEmpty(str)) {
            mBtnSend.setEnabled(false);
        } else {
            mBtnSend.setEnabled(true);
        }
    }

    //设置发送消息按钮、图片、Camera的点击事件
    @OnClick({R.id.btn_send, R.id.iv_pic, R.id.iv_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                break;
            case R.id.iv_pic:
                break;
            case R.id.iv_camera:
                break;
        }
    }
}
