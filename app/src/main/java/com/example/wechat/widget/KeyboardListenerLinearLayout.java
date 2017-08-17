package com.example.wechat.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/17 0017 20:43
 */

public class KeyboardListenerLinearLayout extends LinearLayout {
    public KeyboardListenerLinearLayout(Context context) {
        this(context,null);
    }

    public KeyboardListenerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public KeyboardListenerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
