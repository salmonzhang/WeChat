package com.example.wechat.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.wechat.R;
import com.example.wechat.Utils.ToastUtil;
import com.example.wechat.adapter.AddFriendAdapter;
import com.example.wechat.presenter.AddFriendPresenter;
import com.example.wechat.presenter.AddFriendPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFriendActivity extends BaseActivity implements SearchView.OnQueryTextListener ,AddFriendView {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.iv_nodata)
    ImageView mIvNodata;
    @BindView(R.id.rv_add_friend)
    RecyclerView mRvAddFriend;
    @BindView(R.id.tv_add_friend)
    TextView mTvAddFriend;
    private AddFriendPresenter mAddFriendPresenter;
    private SearchView mSearchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolBar();
        mAddFriendPresenter = new AddFriendPresenterImpl(this);
    }

    //初始化ToolBar
    private void initToolBar() {
        mTvAddFriend.setText("搜");
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        //显示ToolBar的返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //添加返回键的点击事件
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //给AddFriendActivity添加右侧菜单

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);

        //从Menu中获取searchView对象
        MenuItem menuItem = menu.findItem(R.id.menu_add_friend_activity);
        //根据menuItem获取searchView对象
        mSearchView = (SearchView) menuItem.getActionView();

        /**
         * 当searchView获取焦点时，隐藏“搜”字
         */
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mTvAddFriend.setVisibility(View.INVISIBLE);
                } else {
                    mTvAddFriend.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * 监听searchView文本框中文字改变监听
         */
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    /**
     * 输入文本框中文字后，点击软键盘上的查询按钮，触发该监听事件
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        //由P层去完成查询的操作
        if (query != null) {
            mAddFriendPresenter.search(query);
            showDialog("正在加载中......");
        }
        return false;
    }

    /**
     * SearchView文本框中的文字发生变化时，触发该监听事件
     * @param newText
     * @return
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null) {
            ToastUtil.showToast(newText);
        }

        return false;
    }

    @Override
    public void onSearchResult(boolean isSuccess, List<AVUser> list, String message) {
        /**
         * 1：隐藏进度圈
         * 2：如果成功，将list数据显示到RecyclerView上
         * 3：如果失败，弹吐司
         * 4：将SearchView的焦点去掉
         */
        hideDialog();
        mSearchView.clearFocus();
        mTvAddFriend.setVisibility(View.INVISIBLE);
        if (isSuccess) {//查询数据成功
            if (list != null && list.size() > 0) {
                //将返回的数据用Adapter条目显示
                AddFriendAdapter friendAdapter = new AddFriendAdapter(list);
                mRvAddFriend.setLayoutManager(new LinearLayoutManager(this));
                mRvAddFriend.setAdapter(friendAdapter);
                mIvNodata.setVisibility(View.GONE);
                mRvAddFriend.setVisibility(View.VISIBLE);
            } else {
                ToastUtil.showToast("没有查询到符合条件的结果");
                mIvNodata.setVisibility(View.VISIBLE);
                mRvAddFriend.setVisibility(View.INVISIBLE);
            }

        } else {//查询数据失败
            ToastUtil.showToast("查询失败"+message);
            mIvNodata.setVisibility(View.VISIBLE);
            mRvAddFriend.setVisibility(View.INVISIBLE);
        }
    }
}
