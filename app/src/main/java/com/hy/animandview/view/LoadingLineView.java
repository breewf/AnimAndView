package com.hy.animandview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.hy.animandview.App;
import com.hy.animandview.R;
import com.hy.animandview.listener.SimpleAnimatorListener;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @author hy
 * @date 2020/9/17
 * Desc:LoadingLineView
 */
public class LoadingLineView extends View {

    public static final String TAG = "LoadingLineView";

    private Context mContext;

    private float mWidth;
    private float mHeight;

    private float mRadius;

    private float mRate;

    private Paint mPaint;

    public LoadingLineView(Context context) {
        super(context);
        init(context);
    }

    public LoadingLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LoadingLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        mRadius = dp2px(10);

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.red));
//        mPaint.setStrokeWidth(dp2px(10));
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mWidth == 0 || mHeight == 0) {
            mWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray2));

        float cx = mRadius + ((mWidth - mRadius * 2) * mRate);
        canvas.drawCircle(cx, mHeight / 2, mRadius, mPaint);

        float cy = mRadius + ((mHeight - mRadius * 2) * mRate);
        canvas.drawCircle(mWidth / 2, cy, mRadius, mPaint);
    }

    public void start() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            mRate = (float) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        valueAnimator.start();
    }

    public static int dp2px(float dpValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) ((dpValue * scale) + 0.5f);
    }
}
