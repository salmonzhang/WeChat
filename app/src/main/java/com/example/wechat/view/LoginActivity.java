package com.example.wechat.view;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wechat.R;
import com.example.wechat.Utils.LogUtils;
import com.example.wechat.Utils.StringUtils;
import com.example.wechat.Utils.ToastUtil;
import com.example.wechat.presenter.LoginPresenter;
import com.example.wechat.presenter.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements TextView.OnEditorActionListener, View.OnClickListener,LoginView {

    private static final int REQUEST_STORAGE = 1;
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
    private LoginPresenter mLoginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //密码框EditText的Action键的监听
        mEtLoginPwd.setOnEditorActionListener(this);

        //新用户点击后跳转到注册界面
        mTvNewuser.setOnClickListener(this);

        //登录按钮的点击事件
        mBtLogin.setOnClickListener(this);

        //获取一个LoginPresenter对象
        mLoginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public boolean isEnableTranslucentStatus() {
        return true;
    }

    //数据回显
    @Override
    protected void onResume() {
        super.onResume();
        mEtLoginUsername.setText(getUsername());
        mEtLoginPwd.setText(getPwd());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        LogUtils.i("Action被点击了");
        login();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_newuser:
                startActivity(RegistActivity.class,false);
                break;
            case R.id.bt_login:
                login();
                break;
        }
    }

    //完成登录功能的逻辑
    private void login() {
        /**
         * 登录按钮被点击后的逻辑：
         * 1：获取用户名和密码
         * 2：校验数据
         * 3：哪个数据不对，就在光标定位到那，并提示错误信息
         * 4：开始登录
         */

        //获取用户名和密码
        String username = mEtLoginUsername.getText().toString().trim();
        String pwd = mEtLoginPwd.getText().toString().trim();

        //校验数据
        if (!StringUtils.checkUsername(username)){//不合法
            mEtLoginUsername.requestFocus();
            //将错误信息显示到TextInputLayout上
            mTilLoginUsername.setErrorEnabled(true);
            mTilLoginUsername.setError("用户名不合法");
            return;
        }else{//合法
            //将TextInputLayout上的错误信息隐藏
            mTilLoginUsername.setErrorEnabled(false);
        }

        if (!StringUtils.checkPwd(pwd)){
            mEtLoginPwd.requestFocus();
            //将错误信息显示到TextInputLayout上
            mTilLoginPwd.setErrorEnabled(true);
            mTilLoginPwd.setError("密码不合法");
            return;
        }else{
            mTilLoginPwd.setErrorEnabled(false);
        }

        //动态申请SDCard权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
            return;
        }

        //显示一个Dialog，提醒用户正在登录
        showDialog("正在登录中......");

        //让P层去完成登录功能
        mLoginPresenter.login(username,pwd);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                login();
            }
        }
    }

    //P层返回的登录结果
    @Override
    public void onLogin(boolean isSuccess, String username, String pwd, String message) {
        /**
         * 登录后的逻辑：
         * 1：隐藏Dialog
         * 2：如果登录成功，则跳转到主界面
         * 3：如果登录失败，则弹吐司，告诉用户失败的原因
         */

        hideDialog();
        if (isSuccess) {
            saveUser(username, pwd);
            startActivity(MainActivity.class, true);
            ToastUtil.showToast("登录成功");
        } else {
            ToastUtil.showToast(message);
        }
    }
}
