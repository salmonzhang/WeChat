package com.example.wechat.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wechat.R;
import com.example.wechat.Utils.StringUtils;
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
        regist();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regist:
                regist();
                break;
        }
    }

    private void regist() {
        /**
         * 注册按钮被点击后的逻辑：
         * 1：获取用户名和密码
         * 2：校验数据
         * 3：哪个数据不对，就在光标定位到那，并提示错误信息
         * 4：开始注册
         */

        //获取用户名和密码
        String username = mEtRegistUsername.getText().toString().trim();
        String pwd = mEtRegistPwd.getText().toString().trim();

        //校验数据
        if (!StringUtils.checkUsername(username)) {//用户名不合法
            mEtRegistUsername.requestFocus();
            mTilRegistUsername.setEnabled(true);
            mTilRegistUsername.setError("用户名不合法");
        } else if (!StringUtils.checkPwd(pwd)) {//密码不合法
            mEtRegistPwd.requestFocus();
            mTilRegistPwd.setEnabled(true);
            mTilRegistPwd.setError("密码不合法");
        } else {
            mTilRegistUsername.setEnabled(false);
            mTilRegistPwd.setEnabled(false);
            ToastUtil.showToast("恭喜您，注册成功！");
        }
    }
}
