package com.example.wechat.view.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.example.wechat.R;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/14 0014 10:58
 */

public class DynamicFragment extends BaseFragment {


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_dynamic, null);
    }

    @Override
    public void initData() {

    }
}
