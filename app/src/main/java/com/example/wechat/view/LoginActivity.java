package com.example.wechat.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wechat.R;
import com.example.wechat.Utils.LogUtils;
import com.example.wechat.Utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements TextView.OnEditorActionListener, View.OnClickListener {

    @BindView(R.id.et_login_username)
    EditText mEtLoginUsername;
    @BindView(R.id.til_login_username)
    TextInputLayout mTilLoginUsername;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @BindView(R.id.til_login_pwd)
    TextInputLayout mTilLoginPwd;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.tv_newuser)
    TextView mTvNewuser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        StatusBarUtil.setColor(LoginActivity.this,100);

        //密码框EditText的Action键的监听
        mEtLoginPwd.setOnEditorActionListener(this);

        //新用户点击后跳转到注册界面
        mTvNewuser.setOnClickListener(this);
    }

    @Override
    public boolean isEnableTranslucentStatus() {
        return true;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        LogUtils.i("Action被点击了");
        ToastUtil.showToast("Action被点击了");
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_newuser:
                startActivity(RegistActivity.class,false);
                break;
        }
    }
}
