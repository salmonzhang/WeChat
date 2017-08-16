package com.example.wechat.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.example.wechat.R;
import com.example.wechat.Utils.ToastUtil;
import com.example.wechat.adapter.ContactAdapter;
import com.example.wechat.event.ContactUpdateEvent;
import com.example.wechat.presenter.ContactPresenter;
import com.example.wechat.presenter.ContactPresenterImpl;
import com.example.wechat.widget.ContactLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/14 0014 10:44
 */

public class ContactFragment extends BaseFragment implements ContactView, SwipeRefreshLayout.OnRefreshListener {


    private ContactLayout mContactLayout;
    private ContactPresenter mContactPresenter;
    private ContactAdapter mContactAdapter;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_contact, null);
        mContactLayout = (ContactLayout) view.findViewById(R.id.contactLayout);
        mContactPresenter = new ContactPresenterImpl(this);

        //将当前的Fragment作为EventBus的订阅者
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void initData() {

        mContactPresenter.initContact();

        //监听下拉刷新
        mContactLayout.setFreshListener(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ContactUpdateEvent event) {
        ToastUtil.showToast("服务器"+(event.isAdded?"添加了:":"删除了:")+event.contact);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Fragment消失时，注销EventBus
        EventBus.getDefault().unregister(this);
    }

    //获取P层返回的联系人数据
    @Override
    public void onInitContacts(List<String> contactsList) {
        //将获取到的数据填充到ContactLayout中的RecyclerView上，（使用代理设计模式）
        mContactAdapter = new ContactAdapter(contactsList);
        mContactLayout.setAdapter(mContactAdapter);

    }

    //更新联系人数据
    @Override
    public void onUpdateContact(boolean isSuccess, String message) {
        //通知适配器更新数据
        mContactAdapter.notifyDataSetChanged();
        mContactLayout.setRefresh(false);
    }

    @Override
    public void onRefresh() {
        //由P层去完成监听下拉刷新的操作
        mContactPresenter.updateContacts();
    }
}
