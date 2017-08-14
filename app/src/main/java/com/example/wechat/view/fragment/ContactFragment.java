package com.example.wechat.view.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.example.wechat.R;
import com.example.wechat.presenter.ContactPresenter;
import com.example.wechat.presenter.ContactPresenterImpl;
import com.example.wechat.widget.ContactLayout;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/14 0014 10:44
 */

public class ContactFragment extends BaseFragment implements ContactView {


    private ContactLayout mContactLayout;
    private ContactPresenter mContactPresenter;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_contact, null);
        mContactLayout = (ContactLayout) view.findViewById(R.id.contactLayout);
        mContactPresenter = new ContactPresenterImpl(this);
        return view;
    }

    @Override
    public void initData() {

        mContactPresenter.initContact();

    }
}
