package com.samset.user.pulltorefreshsample.pullrefresh;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;


public class SunLayout extends FrameLayout implements HeadListener {

    private final static String Tag = SunLayout.class.getSimpleName();
    protected static final int DEFAULT_SUN_RADIUS = 12;
    private static final int DEFAULT_SUN_COLOR = Color.GREEN;
    private static final int DEFAULT_SUN_EYES_SIZE = 2;
    private static final int DEFAULT_LINE_HEIGHT = 3;
    private static final int DEFAULT_LINE_WIDTH = 1;
    private static final int DEFAULT_LINE_LEVEL = 30;
    private static final int DEFAULT_MOUTH_WIDTH = 3;
    private static final int DEFAULT_LINE_COLOR = Color.RED;

    protected CircleFaceView mSunView;
    protected CircleLineView mLineView;
    private int mSunRadius;
    private int mSunColor;
    private int mEyesSize;
    private int mLineLevel;
    private int mMouthStro;
    private int mLineColor, mLineWidth, mLineHeight;

    private ObjectAnimator mAnimator;

    public SunLayout(Context context) {
        this(context, null);
    }

    public SunLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mSunRadius = DEFAULT_SUN_RADIUS;
        mSunColor = DEFAULT_SUN_COLOR;
        mEyesSize = DEFAULT_SUN_EYES_SIZE;
        mLineColor = DEFAULT_LINE_COLOR;
        mLineHeight = DEFAULT_LINE_HEIGHT;
        mLineWidth = DEFAULT_LINE_WIDTH;
        mLineLevel = DEFAULT_LINE_LEVEL;
        mMouthStro = DEFAULT_MOUTH_WIDTH;

        Context context = getContext();
        mSunView = new CircleFaceView(context);
        mSunView.setSunRadius(mSunRadius);
        mSunView.setSunColor(mSunColor);
        mSunView.setEyesSize(mEyesSize);
        mSunView.setMouthStro(mMouthStro);
        addView(mSunView);

        mLineView = new CircleLineView(context);
        mLineView.setSunRadius(mSunRadius);
        mLineView.setLineLevel(mLineLevel);
        mLineView.setLineColor(mLineColor);
        mLineView.setLineHeight(mLineHeight);
        mLineView.setLineWidth(mLineWidth);
        addView(mLineView);

        startSunLineAnim(mLineView);
    }


    public void setSunRadius(int sunRadius) {
        mSunRadius = sunRadius;
        mSunView.setSunRadius(mSunRadius);
        mLineView.setSunRadius(mSunRadius);
    }


    public void setSunColor(int sunColor) {
        mSunColor = sunColor;
        mSunView.setSunColor(mSunColor);
    }


    public void setEyesSize(int eyesSize) {
        mEyesSize = eyesSize;
        mSunView.setEyesSize(mEyesSize);
    }


    public void setLineLevel(int level) {
        mLineLevel = level;
        mLineView.setLineLevel(mLineLevel);
    }


    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
        mLineView.setLineColor(mLineColor);
    }


    public void setLineWidth(int lineWidth) {
        mLineWidth = lineWidth;
        mLineView.setLineWidth(mLineWidth);
    }


    public void setLineHeight(int lineHeight) {
        mLineHeight = lineHeight;
        mLineView.setLineHeight(mLineHeight);
    }


    public void setMouthStro(int mouthStro) {
        mMouthStro = mouthStro;
        mSunView.setMouthStro(mMouthStro);
    }



    public void startSunLineAnim(View v) {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(v, "rotation", 0f, 720f);
            mAnimator.setDuration(7 * 1000);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        }
        if (!mAnimator.isRunning())
            mAnimator.start();
    }


    public void cancelSunLineAnim() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }

    @Override
    public void onComlete(PullRefreshLayout materialRefreshLayout) {
        cancelSunLineAnim();
        ViewCompat.setScaleX(this, 0);
        ViewCompat.setScaleY(this, 0);
    }

    @Override
    public void onBegin(PullRefreshLayout materialRefreshLayout) {

        ViewCompat.setScaleX(this, 0.001f);
        ViewCompat.setScaleY(this, 0.001f);
    }

    @Override
    public void onPull(PullRefreshLayout materialRefreshLayout, float fraction) {
        float a = Util.limitValue(1, fraction);
        if (a >= 0.7) {
            mLineView.setVisibility(View.VISIBLE);
        } else {
            mLineView.setVisibility(View.GONE);
        }
        mSunView.setPerView(mSunRadius, a);
        ViewCompat.setScaleX(this, a);
        ViewCompat.setScaleY(this, a);
        ViewCompat.setAlpha(this, a);
    }

    @Override
    public void onRelease(PullRefreshLayout materialRefreshLayout, float fraction) {

    }

    @Override
    public void onRefreshing(PullRefreshLayout materialRefreshLayout) {
        startSunLineAnim(mLineView);
    }
}
