package com.example.wechat.view;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.wechat.R;
import com.example.wechat.adapter.AnimatorAdapter;
import com.example.wechat.presenter.SplashPresenter;
import com.example.wechat.presenter.SplashPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashView {

    @BindView(R.id.iv_splash_logo)
    ImageView mIvSplashLogo;
    private SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        /**
         * 闪屏界面的逻辑：
         * 1：先判断是否登录，
         * 2：如果未登录，则进入登录界面
         * 3：如果已登录，则闪屏2秒后，直接进入主界面
         */

        //1.检查是否登录
        mSplashPresenter = new SplashPresenterImpl(this);
        mSplashPresenter.CheckLogin();
    }

    @Override
    public void onCheckLogin(boolean isLogin) {
        //判断是否登录，由Presenter去实现

        //2：如果已登录，则进入主界面
        if (isLogin) {
            startActivity(MainActivity.class, true);
        } else {
            //3：如果未登录，则闪屏2秒后，直接进入登录

            //给闪屏界面设置动画
            ObjectAnimator animator = ObjectAnimator.ofFloat(mIvSplashLogo,"alpha",0.0f, 1.0f);


            //给闪屏界面设置动画监听，2秒后跳转到主界面
            animator.addListener(new AnimatorAdapter(){
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    startActivity(LoginActivity.class,true);
                }
            });

            //设置动画时常
            animator.setDuration(2000);
            //开启动画
            animator.start();
        }
    }
}
