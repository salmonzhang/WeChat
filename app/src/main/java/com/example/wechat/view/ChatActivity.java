package com.example.wechat.view;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wechat.R;
import com.example.wechat.Utils.ToastUtil;
import com.example.wechat.adapter.ChatAdapter;
import com.example.wechat.presenter.ChatPresenter;
import com.example.wechat.presenter.ChatPresenterImpl;
import com.example.wechat.widget.KeyboardListenerLinearLayout;
import com.hyphenate.chat.EMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity implements TextWatcher ,ChatView, SwipeRefreshLayout.OnRefreshListener {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private static final int REQUEST_CAMERA = 1;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_chat)
    RecyclerView mRvChat;
    @BindView(R.id.srl_chat)
    SwipeRefreshLayout mSrlChat;
    @BindView(R.id.et_msg)
    EditText mEtMsg;
    @BindView(R.id.btn_send)
    Button mBtnSend;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.iv_camera)
    ImageView mIvCamera;
    @BindView(R.id.ll_root)
    KeyboardListenerLinearLayout mLlRoot;
    private String mUsername;
    private ChatPresenter mChatPresenter;
    private ChatAdapter mChatAdapter;
    private File mFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取传过来的联系人
        Intent intent = getIntent();
        mUsername = intent.getStringExtra("contact");
        if (TextUtils.isEmpty(mUsername)) {
            ToastUtil.showToast("没找到对应的联系人");
            finish();
            return;
        }
        //初始化ToolBar
        initToolBar();

        //初始化EditText
        initEditText();

        mChatPresenter = new ChatPresenterImpl(this);

        //初始化历史消息
        initHistoryMsg();

        //设置下拉刷新
        mSrlChat.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorAccent));
        mSrlChat.setOnRefreshListener(this);

        //将ChatActivity注册为EventBus订阅者
        EventBus.getDefault().register(this);
    }

    //初始化历史聊天记录
    private void initHistoryMsg() {
        //从P层获取消息
        mChatPresenter.initChat(mUsername);
    }

    private void initEditText() {
        String message = mEtMsg.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mBtnSend.setEnabled(false);
        } else {
            mBtnSend.setEnabled(false);
        }

        //给EditText设置文本改变监听
        mEtMsg.addTextChangedListener(this);
    }

    private void initToolBar() {
        mToolbar.setTitle("");
        mTvTitle.setText("与" + mUsername + "聊天中");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString().trim();
        if (TextUtils.isEmpty(str)) {
            mBtnSend.setEnabled(false);
        } else {
            mBtnSend.setEnabled(true);
        }
    }

    //设置发送消息按钮、图片、Camera的点击事件
    @OnClick({R.id.btn_send, R.id.iv_pic, R.id.iv_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                //发送消息
                sendTextMsg();
                break;
            case R.id.iv_pic:
                break;
            case R.id.iv_camera:
                //调用系统相机拍照
                takePhoto();
                break;
        }
    }

    //使用系统相机拍照
    private void takePhoto() {


        //动态获取相机权限

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {
            //如果还未被授权，则动态申请
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
            return;
        }

        // 指定开启系统相机的Action
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mFile = new File(Environment.getExternalStorageDirectory(), new Date().getTime()+".jpg");
        //设置系统相机拍摄照片完成后图片文件的存放地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(mFile));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(this, "com.example.wechat.fileprovider", mFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
        }

        startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    /**
     * 该方法是动态申请权限弹出的对话框被处理后的回调方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                //被授权了
                ToastUtil.showToast("获得拍照权限");
                takePhoto();
            } else {
                //被拒绝了
               ToastUtil.showToast("拒绝拍照权限");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ) {
            if (resultCode == RESULT_OK) {
                //照相完成
                ToastUtil.showToast("照相完成");
                //照相完成后，让P层去发送图片
                if(mFile != null){
                    //传入图片的地址和正在聊天的用户名
                    mChatPresenter.sendImageMsg(mFile,mUsername);
                }

            } else {
                //没有照相
                ToastUtil.showToast("没有照相");
            }
        }
    }

    //发送文本消息
    private void sendTextMsg() {
        /**
         * 1：获取EditText上的文本信息
         * 2：让EditText的文本消失
         * 3：让P层去实现文本发送功能
         */
        String msg = mEtMsg.getText().toString();
        mEtMsg.getText().clear();
        mChatPresenter.sendTextMsg(msg,mUsername);
    }

    //返回P层获取到的历史信息
    @Override
    public void onInitChat(List<EMMessage> emMessageList) {
        //让返回的历史信息在RecyclerView上显示
        mChatAdapter = new ChatAdapter(emMessageList);
        mRvChat.setLayoutManager(new LinearLayoutManager(this));
        mRvChat.setAdapter(mChatAdapter);
        //让RecyclerView滚动到最后一条记录
        mRvChat.scrollToPosition(emMessageList.size() - 1);
    }

    @Override
    public void onRefresh() {
        //由P层去完成下拉刷新功能
        mChatPresenter.loadMoreMsg(mUsername);
    }

    //P层下来刷新返回的结果
    @Override
    public void onLoadMore(boolean isSuccess, int size) {
        /**
         * 1：隐藏下拉刷新进度圈
         * 2：如果成功，让Adapter刷新界面
         * 3：如果失败，或者size = 0，则弹吐司
         */
        mSrlChat.setRefreshing(false);
        if (isSuccess && size > 0) {
            mChatAdapter.notifyDataSetChanged();
            ToastUtil.showToast("亲，又加载了"+size+"条消息");
        } else {
            ToastUtil.showToast("亲，已经没有更多数据了");
        }
    }

    @Override
    public void onChatUpdate() {
        if (mChatAdapter != null) {
            mChatAdapter.notifyDataSetChanged();
            mRvChat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
        }
    }

    //定义一个EventBus处理方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EMMessage message){
        /**
         * 1：判断发送过来的消息是否是当前聊天用户发送过来的
         * 2：将消息添加到大集合中
         * 3：更新界面
         */
        if (message.getFrom().equals(mUsername)) {
            mChatPresenter.addMessage(message);
            if (mChatAdapter != null) {
                mChatAdapter.notifyDataSetChanged();
                mRvChat.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
