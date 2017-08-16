package com.example.wechat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wechat.R;
import com.hyphenate.util.DensityUtil;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/14 0014 16:11
 */

public class SlideBar extends View {
    private static String[] SECTIONS = {"搜", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int mHeight;
    private int mWidth;
    private float mAverHeight;
    private Paint mPaint;
    private TextView mTvFloatView;

    public SlideBar(Context context) {
        this(context, null);
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化一个画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(DensityUtil.dip2px(getContext(), 12));
        mPaint.setColor(0xff9c9c9c);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取测量的宽高
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        //获取平均高度
        mAverHeight = (mHeight + 0.0f) / SECTIONS.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < SECTIONS.length; i++) {
            float X = mWidth / 2;
            float Y = mAverHeight * (i + 0.6f);
            canvas.drawText(SECTIONS[i], X, Y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                /**
                 * 1: 改变SlideBar的背景颜色
                 * 2：修改FloatTextView文本，并让其显示
                 * 3：当选中的字母与通讯录中的字母一致时，让RecyclerView滚动
                 */
                setBackgroundResource(R.drawable.shape_slidebar_bg);
                float y = event.getY();
                showFloatViewAndScrollRecyclerView(y);
                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 1：改变SlideBar的背景颜色
                 * 2：隐藏FloatTextView文本
                 */
                setBackgroundColor(Color.TRANSPARENT);
                if (mTvFloatView != null) {
                    mTvFloatView.setVisibility(GONE);
                }

                break;
        }
        return true;
    }

    //显示FloatView并滚动RecyclerView
    private void showFloatViewAndScrollRecyclerView(float y) {
        //通过手指点击的位置，获取当前索引
        int position = (int)(y / mAverHeight);
        if (position < 0) {
            position = 0;
        }
        if (position > SECTIONS.length - 1) {
            position = SECTIONS.length - 1;
        }

        //通过父控件找到兄弟控件
        if (mTvFloatView == null) {
            ViewGroup parent = (ViewGroup) getParent();
            mTvFloatView = (TextView) parent.findViewById(R.id.tv_floatView);
        }

        mTvFloatView.setText(SECTIONS[position]);
        mTvFloatView.setVisibility(VISIBLE);
    }
}
