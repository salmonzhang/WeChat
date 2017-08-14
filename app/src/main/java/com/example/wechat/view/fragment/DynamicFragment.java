package com.example.wechat.view.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.wechat.R;
import com.example.wechat.Utils.ToastUtil;
import com.example.wechat.presenter.LogoutPresenter;
import com.example.wechat.presenter.LogoutPresenterImpl;
import com.example.wechat.view.BaseActivity;
import com.example.wechat.view.LoginActivity;
import com.hyphenate.chat.EMClient;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/14 10:58
 */

public class DynamicFragment extends BaseFragment implements View.OnClickListener ,LogoutView {


    private LogoutPresenter mLogoutPresenter;
    private Button mBtnDynamicLogout;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_dynamic, null);
        mBtnDynamicLogout = (Button) view.findViewById(R.id.btn_dynamic_logout);
        return view;
    }


    @Override
    public void initData() {
        //设置退出Button的文字
        String currentUser = EMClient.getInstance().getCurrentUser();
        mBtnDynamicLogout.setText("退（"+currentUser+"）出");

        //给Button设置点击事件
        mBtnDynamicLogout.setOnClickListener(this);

        //获取P层处理登录逻辑的对象
        mLogoutPresenter = new LogoutPresenterImpl(this);
    }

    //处理Button的点击事件
    @Override
    public void onClick(View v) {
        //1.显示一个对话框
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            baseActivity.showDialog("正在退出中。。。");
        }

        //让P层去实现退出功能
        mLogoutPresenter.logout();
    }

    //处理P层返回的退出结果
    @Override
    public void onLogout(boolean isSuccess, String message) {
        /**
         * 1:隐藏对话框
         * 2：如果退出失败，弹吐司说明失败的原因
         * 3：无论失败或成功，返回到登录界面
         */

        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            baseActivity.hideDialog();
        }

        if (isSuccess) {
            startActivity(new Intent(mContext, LoginActivity.class));
            //销毁MainActivity
            getActivity().finish();
        } else {
            ToastUtil.showToast(message);
        }

    }
}
