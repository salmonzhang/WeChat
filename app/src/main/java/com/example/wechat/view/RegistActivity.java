package com.example.wechat.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wechat.R;
import com.example.wechat.Utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistActivity extends BaseActivity implements TextView.OnEditorActionListener, View.OnClickListener {

    @BindView(R.id.et_regist_username)
    EditText mEtRegistUsername;
    @BindView(R.id.til_regist_username)
    TextInputLayout mTilRegistUsername;
    @BindView(R.id.et_regist_pwd)
    EditText mEtRegistPwd;
    @BindView(R.id.til_regist_pwd)
    TextInputLayout mTilRegistPwd;
    @BindView(R.id.bt_regist)
    Button mBtRegist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //密码框EditText的Action键的监听
        mEtRegistPwd.setOnEditorActionListener(this);
        //登录按钮设置点击事件
        mBtRegist.setOnClickListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        ToastUtil.showToast("被点击了");
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regist:

                break;
        }
    }
}
