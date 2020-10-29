package com.hy.animandview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author hy
 * @date 2020/10/29
 * desc: LoadingSmile
 **/
public class LoadingSmile extends View {

    private Paint mPaint;

    private float mWidth = 0f;
    private float mEyeWidth = 0f;

    private float mPadding = 0f;
    private float startAngle = 0f;
    private boolean isSmile = false;
    private boolean isAnim = false;
    private float mAnimatedValue = 0f;
    private ValueAnimator valueAnimator;
    RectF rectF = new RectF();

    public LoadingSmile(Context context) {
        this(context, null);
    }

    public LoadingSmile(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingSmile(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initPaint();
    }

    private void init() {
        rectF = new RectF();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mPadding = dp2px(10);
        mEyeWidth = dp2px(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF.set(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        mPaint.setStyle(Paint.Style.STROKE);
        // 绘制下巴
        canvas.drawArc(rectF, startAngle, 180, false, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        if (!isAnim) {
            // 绘制默认的两个小眼睛
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
        }
        if (isSmile) {
            // 下巴旋转时不绘制眼睛
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
        }
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(dp2px(2));
    }

    private void onAnimationUpdate(ValueAnimator valueAnimator) {
        mAnimatedValue = (float) valueAnimator.getAnimatedValue();
        if (mAnimatedValue < 0.5) {
            isSmile = false;
            startAngle = 720 * mAnimatedValue;
        } else {
            startAngle = 720;
            isSmile = true;
        }
        invalidate();
    }

    /**
     * 开启动画
     */
    public void startAnim() {
        stopAnim();
        startViewAnim();
    }

    /**
     * 停止动画
     */
    public void stopAnim() {
        isAnim = false;
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();

            isSmile = false;
            mAnimatedValue = 0f;
            startAngle = 0f;
        }
    }

    private void startViewAnim() {
        isAnim = true;
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());

        valueAnimator.setRepeatCount(setAnimRepeatCount());

        if (ValueAnimator.RESTART == setAnimRepeatMode()) {
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        } else if (ValueAnimator.REVERSE == setAnimRepeatMode()) {
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        }
        valueAnimator.addUpdateListener(this::onAnimationUpdate);
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
    }

    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int setAnimRepeatMode() {
        return ValueAnimator.RESTART;
    }

    private int setAnimRepeatCount() {
        return ValueAnimator.INFINITE;
    }
}
