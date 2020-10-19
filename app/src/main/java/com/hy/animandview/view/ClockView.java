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

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @author hy
 * @date 2020/10/19
 * desc: ClockView
 **/
public class ClockView extends View {

    public static final String TAG = "ClockView";

    private final static int DEFAULT_TIME = 1000;
    private int mAnimTime = DEFAULT_TIME;

    private final int NUM = 12;

    private Context mContext;

    private float mWidth;
    private float mHeight;

    private float mHourDegrees;
    private float mMinuteDegrees;
    private float mSecondDegrees;

    private float mClockLine;

    private Paint mPaint;
    private Paint mPaintBg;
    private Paint mPaintLine;

    private ValueAnimator mValueAnimator;

    public ClockView(Context context) {
        super(context);
        init(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        initPaint();
        mClockLine = dp2px(16);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(dp2px(8));
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.holo_blue_light));

        mPaintLine = new Paint();
        mPaintLine.setAntiAlias(true);
        mPaintLine.setDither(true);
        mPaintLine.setStyle(Paint.Style.FILL);
        mPaintLine.setStrokeWidth(dp2px(8));
        mPaintLine.setColor(ContextCompat.getColor(mContext, R.color.holo_blue_light));

        mPaintBg = new Paint();
        mPaintBg.setAntiAlias(true);
        mPaintBg.setDither(true);
        mPaintBg.setStyle(Paint.Style.FILL);
        mPaintBg.setColor(ContextCompat.getColor(mContext, R.color.holo_gray_light));
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
//        setBackgroundColor(ContextCompat.getColor(mContext, R.color.holo_gray_light));
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0, 0, mHeight / 2, mPaintBg);
        canvas.restore();

        canvas.save();
        // 表盘
        for (int i = 0; i < NUM; i++) {
            if (i != 0) {
                canvas.rotate(30, mWidth / 2, mHeight / 2);
            }
            canvas.drawLine(0, mHeight / 2, mClockLine, mHeight / 2, mPaint);
        }
        canvas.restore();

        // 固定表针圆
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0, 0, 40, mPaint);

        // 时针--默认指向0点
        mPaintLine.setStrokeWidth(dp2px(8));
        canvas.save();
        canvas.rotate(mHourDegrees);
        canvas.drawLine(0, 80, 0, -200, mPaintLine);
        canvas.restore();

        // 分针--默认指向0点
        mPaintLine.setStrokeWidth(dp2px(6));
        canvas.save();
        canvas.rotate(mMinuteDegrees);
        canvas.drawLine(0, 100, 0, -280, mPaintLine);
        canvas.restore();

        // 秒针--默认指向0点
        mPaintLine.setStrokeWidth(dp2px(3));
        canvas.save();
        canvas.rotate(mSecondDegrees);
        canvas.drawLine(0, 120, 0, -340, mPaintLine);
        canvas.restore();
    }

    public void start() {
        cancelAnim();
        startAnim();
    }

    private void calculatingTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
//        Toast.makeText(mContext, hour + ":" + minute + ":" + second, Toast.LENGTH_SHORT).show();

        int hour12 = hour >= 12 ? hour - 12 : hour;
        mHourDegrees = (hour12 * 60 * 60 + minute * 60 + second) / ((float) 12 * 60 * 60) * 360;
        if (mHourDegrees >= 360) {
            mHourDegrees = 0;
        }

        mMinuteDegrees = (minute * 60 + second) / ((float) 60 * 60) * 360;
        if (mMinuteDegrees >= 360) {
            mMinuteDegrees = 0;
        }

        mSecondDegrees = (second) / ((float) 60) * 360;
        if (mSecondDegrees >= 360) {
            mSecondDegrees = 0;
        }
    }

    private void startAnim() {
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setDuration(mAnimTime);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                calculatingTime();
                invalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startAnim();
            }
        });
        mValueAnimator.start();
    }

    public void cancelAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.removeAllListeners();
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
    }

    public static int dp2px(float dpValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) ((dpValue * scale) + 0.5f);
    }
}
