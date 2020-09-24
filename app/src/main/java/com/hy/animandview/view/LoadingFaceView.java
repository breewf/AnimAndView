package com.hy.animandview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import com.hy.animandview.App;
import com.hy.animandview.R;
import com.hy.animandview.listener.SimpleAnimatorListener;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @author hy
 * @date 2020/9/17
 * Desc:LoadingFaceView
 */
public class LoadingFaceView extends View {

    public static final String TAG = "LoadingFaceView";

    private final static int DEFAULT_TIME = 1500;
    private int mAnimTime = DEFAULT_TIME;

    private Context mContext;

    private float mWidth;
    private float mHeight;

    private int mEyeTransX;
    private int mEyeTransY;
    private int mMouseTransX;
    private int mEyeHolderTransY;

    private Paint mPaint;
    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaintEye;

    private Rect mRect;

    private boolean mIsReserve;

    private ValueAnimator mValueAnimator;
    private ValueAnimator mValueAnimator2;
    private ValueAnimator mValueAnimator3;

    public LoadingFaceView(Context context) {
        super(context);
        init(context);
    }

    public LoadingFaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LoadingFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mRect = new Rect();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.holo_blue_light));

        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setDither(true);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setColor(ContextCompat.getColor(mContext, R.color.white));
        mPaint1.setStrokeWidth(dp2px(8));
        mPaint1.setStrokeJoin(Paint.Join.ROUND);
        mPaint1.setStrokeCap(Paint.Cap.ROUND);

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setDither(true);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setColor(ContextCompat.getColor(mContext, R.color.white));

        mPaintEye = new Paint();
        mPaintEye.setAntiAlias(true);
        mPaintEye.setDither(true);
        mPaintEye.setStyle(Paint.Style.FILL);
        mPaintEye.setColor(ContextCompat.getColor(mContext, R.color.black));
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
        setBackgroundColor(ContextCompat.getColor(mContext, R.color.holo_blue_light));

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);

        // canvas.drawCircle(0, 0, 140, mPaint);

        canvas.drawCircle(-60, -30, 40, mPaint2);
        canvas.drawCircle(60, -30, 40, mPaint2);

        canvas.translate(-90 + mEyeTransX, -30 - mEyeTransY);
        canvas.drawCircle(0, 0, 16, mPaintEye);

        canvas.translate(120, 0);
        canvas.drawCircle(0, 0, 16, mPaintEye);
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2 + 50);
        canvas.drawLine(-40 + mMouseTransX, 0, 20 + mMouseTransX, 0, mPaint1);
        canvas.restore();

        canvas.translate(mWidth / 2, mHeight / 2);
        mRect.set(-120, -80, 120, -80 + mEyeHolderTransY);
        canvas.drawRect(mRect, mPaint);

    }

    public void start() {
        mIsReserve = false;
        clearAnimation();
        cancelAnim();
        startAnim(0, 60);
    }

    private void startAnim(int start, int end) {
        mValueAnimator = ValueAnimator.ofInt(start, end);
        mValueAnimator.setDuration(mAnimTime);
        mValueAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        mValueAnimator.addUpdateListener(animation -> {
            mEyeTransX = (int) animation.getAnimatedValue();
            invalidate();
        });
        mValueAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (!mIsReserve) {
                    startAnim2(0, 30);
                } else {
                    startAnim2(30, 0);
                }

                if (!mIsReserve) {
                    startAnim3(0, 35);
                } else {
                    startAnim3(35, 0);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!mIsReserve) {
                    mIsReserve = true;
                    startAnim(60, 0);
                } else {
                    mIsReserve = false;
                    startAnim(0, 60);
                }
            }
        });
        mValueAnimator.start();
    }

    private void startAnim2(int start, int end) {
        mValueAnimator2 = ValueAnimator.ofInt(start, end);
        mValueAnimator2.setDuration(mAnimTime);
        mValueAnimator2.setInterpolator(new AnticipateOvershootInterpolator());
        mValueAnimator2.addUpdateListener(animation -> {
            mMouseTransX = (int) animation.getAnimatedValue();
        });
        mValueAnimator2.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        mValueAnimator2.start();
    }

    private void startAnim3(int start, int end) {
        mValueAnimator3 = ValueAnimator.ofInt(start, end);
        mValueAnimator3.setDuration(mAnimTime);
        mValueAnimator3.setInterpolator(new AnticipateOvershootInterpolator());
        mValueAnimator3.addUpdateListener(animation -> {
            mEyeHolderTransY = (int) animation.getAnimatedValue();
        });
        mValueAnimator3.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        mValueAnimator3.start();
    }

    private void cancelAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.removeAllListeners();
            mValueAnimator.cancel();
            mValueAnimator = null;
        }

        if (mValueAnimator2 != null) {
            mValueAnimator2.removeAllListeners();
            mValueAnimator2.cancel();
            mValueAnimator2 = null;
        }

        if (mValueAnimator3 != null) {
            mValueAnimator3.removeAllListeners();
            mValueAnimator3.cancel();
            mValueAnimator3 = null;
        }
    }

    public static int dp2px(float dpValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) ((dpValue * scale) + 0.5f);
    }
}
