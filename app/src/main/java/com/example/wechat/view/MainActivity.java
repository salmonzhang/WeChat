package com.example.wechat.view;

import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.wechat.R;
import com.example.wechat.Utils.ToastUtil;
import com.example.wechat.view.fragment.FragmentFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fl_main_content)
    FrameLayout mFlMainContent;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    private String [] titles = {"首页","消息","动态"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    //初始化方法
    private void init() {
        initToolBar();
        initFragment();
        initBottomNavigation();
    }

    //初始化ToolBar
    private void initToolBar() {
        //让ToolBar的标题隐藏
        mToolbar.setTitle("");
        //让ToolBar替换ActionBar
        setSupportActionBar(mToolbar);
        //显示ToolBar上的返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //给菜单条目设置点击事件
        mToolbar.setOnMenuItemClickListener(this);
    }

    //添加ToolBar的菜单条目
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //使用菜单填充器加载menu布局
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //让条目中的图标显示
        ((MenuBuilder) menu).setOptionalIconsVisible(true);

        return true;
    }

    //初始化中间的Fragment
    private void initFragment() {
        //先添加一个Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_main_content, FragmentFactory.getFragment(0))
                .commit();

        //初始化ToolBar的标题
        mTvTitle.setText(titles[0]);
    }

    //初始化底部导航栏
    private void initBottomNavigation() {

        /**
         * 注意：条目颜色的设置一定要在mBottomNavigationBar初始化之前
         */
        //条目选中的颜色
        mBottomNavigationBar.setActiveColor(R.color.colorPrimary);
        //条目未选中的颜色
        mBottomNavigationBar.setInActiveColor(R.color.colorActive);

        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.conversation_selected_2, titles[0]))
                .addItem(new BottomNavigationItem(R.mipmap.contact_selected_2, titles[1]))
                .addItem(new BottomNavigationItem(R.mipmap.plugin_selected_2, titles[2]))
                .initialise();

        //底部导航栏的点击事件
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });

    }

    //菜单条目的点击事件
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_friend:
                ToastUtil.showToast("添加好友");
                break;
            case R.id.menu_share_friend:
                ToastUtil.showToast("分享好友");
                break;
            case R.id.menu_about:
                ToastUtil.showToast("关于更多");
                break;
        }
        return true;
    }

}
