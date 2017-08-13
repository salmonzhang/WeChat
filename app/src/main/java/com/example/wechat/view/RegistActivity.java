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
import com.example.wechat.presenter.RegistPresenter;
import com.example.wechat.presenter.RegistPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistActivity extends BaseActivity implements TextView.OnEditorActionListener, View.OnClickListener ,RegistView{

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

    private RegistPresenter mRegistPresenter;

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
        //注册按钮设置点击事件
        mBtRegist.setOnClickListener(this);

        //获取处理登录的P层对象
        mRegistPresenter = new RegistPresenterImpl(this);
    }

    //密码框EditText的Action键的监听
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        ToastUtil.showToast("被点击了");
        regist();
        return false;
    }

    //注册按钮设置点击事件
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
        if (!StringUtils.checkUsername(username)){//不合法
            mEtRegistUsername.requestFocus();
            //将错误信息显示到TextInputLayout上
            mTilRegistUsername.setErrorEnabled(true);
            mTilRegistUsername.setError("用户名不合法");
            return;
        }else{//合法
            //将TextInputLayout上的错误信息隐藏
            mTilRegistUsername.setErrorEnabled(false);
        }

        if (!StringUtils.checkPwd(pwd)){
            mEtRegistPwd.requestFocus();
            //将错误信息显示到TextInputLayout上
            mTilRegistPwd.setErrorEnabled(true);
            mTilRegistPwd.setError("密码不合法");
            return;
        }else{
            mTilRegistPwd.setErrorEnabled(false);
        }

        //显示一个Dialog，提醒用户正在注册
        showDialog("正在注册中......");

        //让P层去执行注册操作的功能
        mRegistPresenter.regist(username,pwd);
    }

    //P层返回的注册结果
    @Override
    public void onRegist(boolean isSuccess, String username, String pwd, String msg) {
        /**
         * 注册后的逻辑：
         * 1：让Dialog隐藏
         * 2：如果注册成功，跳转到登录页面
         * 3：如果注册失败，弹吐司，告诉用户失败的原因
         */

        //结果返回后，首先应将Dialog隐藏
        hideDialog();
        if (isSuccess) {
            //注册成功后，将用户名和密码保存在SP中
            saveUser(username,pwd);
            //配置登录界面的启动模式，保证任务栈中只有一个实例：singleTask
            startActivity(LoginActivity.class, true);
        } else {
            ToastUtil.showToast(msg);
        }
    }
}
