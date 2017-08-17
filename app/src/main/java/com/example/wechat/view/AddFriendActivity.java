package com.example.wechat.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wechat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFriendActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.iv_nodata)
    ImageView mIvNodata;
    @BindView(R.id.rv_add_friend)
    RecyclerView mRvAddFriend;
    @BindView(R.id.tv_add_friend)
    TextView mTvAddFriend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolBar();
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
        return true;
    }
}
