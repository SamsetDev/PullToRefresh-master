package com.samset.user.pulltorefreshsample.pullrefresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class CircleFaceView extends View {

    private static final String Tag = CircleFaceView.class.getSimpleName();
    private static final int DEFAULT_SUN_RADIUS = 12;
    private static final int DEFAULT_EYES_RADIUS = 2;

    private int mHeight, mWidth;
    private Paint mCirclePaint;
    private int mSunRadius;//sun radius
    private int mEyesRadius = DEFAULT_EYES_RADIUS;
    private Rect debugRect;
    private RectF mouthRect;
    private int mSunColor;//sun color
    private boolean isDrawFace = true;
    private int mouthStro = 3;

    public CircleFaceView(Context context) {
        this(context, null);
    }

    public CircleFaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleFaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.i(Tag, "init");

        mSunRadius = changeDp(DEFAULT_SUN_RADIUS);
        mEyesRadius = changeDp(DEFAULT_EYES_RADIUS);


        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.FILL);

        debugRect = new Rect();
        mouthRect = new RectF();
    }


    public void setSunRadius(int sunRadius) {
        mSunRadius = changeDp(sunRadius);
        invalidate();
    }


    public void setEyesSize(int eyesSize){
        mEyesRadius = changeDp(eyesSize);
        invalidate();
    }

    public void setMouthStro(int mouthStro){
        this.mouthStro = mouthStro;
        invalidate();
    }



    public void setPerView(int sunRadius ,float per){
        sunRadius = changeDp(sunRadius);
        if(per>=0.8){
            isDrawFace = true;
        }else {
            isDrawFace = false;
        }
        per = Math.min(per,1);
        float tempRadius =  sunRadius*per;
        mSunRadius = (int) tempRadius;
        mCirclePaint.setAlpha((int)per*255);
        invalidate();
    }


    public void setSunColor(int sunColor) {
        mSunColor = sunColor;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(Tag, "w---->" + w + "  -------  h----->" + h);
        mWidth = w;
        mHeight = h;


        debugRect.left = mWidth / 2 - mSunRadius;
        debugRect.right = mWidth / 2 + mSunRadius;
        debugRect.top = mHeight / 2 - mSunRadius;
        debugRect.bottom = mHeight / 2 + mSunRadius;


        mouthRect.left = mWidth / 2 - mSunRadius / 2;
        mouthRect.right = mWidth / 2 + mSunRadius / 2;
        mouthRect.top = mHeight / 2 - mSunRadius / 2;
        mouthRect.bottom = mHeight / 2 + mSunRadius / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(Tag, "onMeasure");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mSunRadius * 2 + getPaddingRight() + getPaddingLeft();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {

            height = mSunRadius * 2 + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        mCirclePaint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(debugRect, mCirclePaint);
//        canvas.drawRect(mouthRect, mCirclePaint);
    }


    private void drawCircle(Canvas canvas) {
        mCirclePaint.setColor(mSunColor);
        mCirclePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mSunRadius, mCirclePaint);
        mCirclePaint.setColor(Color.WHITE);
        if(isDrawFace)
        {
            canvas.save();
            canvas.drawCircle(mWidth / 2 - mSunRadius / 2 + mEyesRadius, mHeight / 2 - mSunRadius / 2 + mEyesRadius, mEyesRadius, mCirclePaint);
           canvas.drawCircle(mWidth / 2 + mSunRadius / 2 - mEyesRadius, mHeight / 2 - mSunRadius / 2 + mEyesRadius, mEyesRadius, mCirclePaint);
           mCirclePaint.setStyle(Paint.Style.STROKE);
            mCirclePaint.setStrokeWidth(mouthStro);
            canvas.drawArc(mouthRect, 20, 140, false, mCirclePaint);
            canvas.restore();
        }

    }


    public int changeDp(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}
