package com.hy.animandview.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.hy.animandview.listener.SimpleAnimatorListener;

/**
 * @author hy
 * @date 2020/10/29
 * desc:
 **/
public class LoadingJumpView extends View {

    private Shape mCurrentShape;

    private Paint mPaint;
    private int mHeight, mWidth;

    private float mTranslateY;
    private float mScale, mRotateRect, mRotateTriangle;

    private AnimatorSet mUpAnimatorSet, mDownAnimatorSet;

    private final int mRectLength = 100;

    public LoadingJumpView(Context context) {
        this(context, null);
    }

    public LoadingJumpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingJumpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mCurrentShape = Shape.SHAPE_CIRCLE;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawLoading(canvas);
        canvas.restore();

        canvas.save();
        drawShadow(canvas);
        canvas.restore();

        canvas.save();
        drawText(canvas);
        canvas.restore();
    }

    private void drawLoading(Canvas canvas) {
        // 上抛下落 平移
        canvas.translate(0, mTranslateY);
        // 三角形的旋转中心和正方形有所区别
        if (mCurrentShape == Shape.SHAPE_TRIANGLE) {
            canvas.rotate(mRotateTriangle,
                    mWidth / 2f, mHeight / 2f - mRectLength / 2f + (200 / 3f));
        } else {
            canvas.rotate(mRotateRect, mWidth / 2f, mHeight / 2f);
        }
        switch (mCurrentShape) {
            case SHAPE_CIRCLE:
                drawCircle(canvas);
                break;
            case SHAPE_RECT:
                drawRect(canvas);
                break;
            case SHAPE_TRIANGLE:
                drawTriangle(canvas);
                break;
            default:
                break;
        }
    }

    /**
     * 画圆
     */
    private void drawCircle(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#aa738ffe"));
        float radius = mRectLength / 2f;
        canvas.drawCircle(mWidth / 2f, mHeight / 2f, radius, mPaint);
    }

    /**
     * 画正方形
     */
    private void drawRect(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#aae84e49"));
        canvas.drawRect(mWidth / 2f - mRectLength / 2f, mHeight / 2f - mRectLength / 2f,
                mWidth / 2f + mRectLength / 2f, mHeight / 2f + mRectLength / 2f, mPaint);
    }

    /**
     * 画三角形（正三角形）
     */
    private void drawTriangle(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#aa72d572"));
        Path path = new Path();
        path.moveTo(mWidth / 2f, mHeight / 2f - mRectLength / 2f);
        path.lineTo((float) (mWidth / 2f - Math.sqrt(Math.pow(mRectLength, 2) / 3f)), mHeight / 2f + mRectLength / 2f);
        path.lineTo((float) (mWidth / 2f + Math.sqrt(Math.pow(mRectLength, 2) / 3f)), mHeight / 2f + mRectLength / 2f);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    /**
     * 画阴影部分椭圆
     */
    private void drawShadow(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#25808080"));
        // 椭圆缩放
        canvas.scale(mScale, mScale, mWidth / 2f, mHeight / 2f + 90);
        canvas.drawArc(mWidth / 2f - mRectLength / 2f, mHeight / 2f + 80,
                mWidth / 2f + 50, mHeight / 2f + 100, 0, 360, false, mPaint);
    }

    /**
     * 文字
     */
    private void drawText(Canvas canvas) {
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                16f, getContext().getResources().getDisplayMetrics()));
        mPaint.setColor(Color.GRAY);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("加载中…", mWidth / 2f + 18, mHeight / 2f + 170, mPaint);
    }

    /**
     * 形状的枚举
     */
    public enum Shape {
        // 三角
        SHAPE_TRIANGLE,
        // 四边形
        SHAPE_RECT,
        // 圆形
        SHAPE_CIRCLE
    }

    private void exchangeDraw() {
        switch (mCurrentShape) {
            case SHAPE_CIRCLE:
                mCurrentShape = Shape.SHAPE_RECT;
                break;
            case SHAPE_RECT:
                mCurrentShape = Shape.SHAPE_TRIANGLE;
                break;
            case SHAPE_TRIANGLE:
                mCurrentShape = Shape.SHAPE_CIRCLE;
                break;
            default:
                break;
        }
    }

    /**
     * 上抛动画
     */
    private void upAnimation() {
        final ValueAnimator upAnimation = ValueAnimator.ofFloat(0, -200);
        upAnimation.setInterpolator(new DecelerateInterpolator(1.2f));
        upAnimation.addUpdateListener(animation -> {
            mTranslateY = (float) upAnimation.getAnimatedValue();
            invalidate();
        });

        final ValueAnimator scaleAnimation = ValueAnimator.ofFloat(1, 0.3f);
        scaleAnimation.setInterpolator(new DecelerateInterpolator(1.2f));
        scaleAnimation.addUpdateListener(animation -> mScale = (float) scaleAnimation.getAnimatedValue());

        final ValueAnimator rotateTriangleAnimation = ValueAnimator.ofFloat(0, 120);
        rotateTriangleAnimation.setInterpolator(new DecelerateInterpolator(1.2f));
        rotateTriangleAnimation.addUpdateListener(animation -> mRotateTriangle = (float) rotateTriangleAnimation.getAnimatedValue());

        final ValueAnimator rotateRectAnimation = ValueAnimator.ofFloat(0, 180);
        rotateRectAnimation.setInterpolator(new DecelerateInterpolator(1.2f));
        rotateRectAnimation.addUpdateListener(animation -> mRotateRect = (float) rotateRectAnimation.getAnimatedValue());

        mUpAnimatorSet = new AnimatorSet();
        mUpAnimatorSet.setDuration(300);
        mUpAnimatorSet.playTogether(upAnimation, scaleAnimation, rotateTriangleAnimation, rotateRectAnimation);
        mUpAnimatorSet.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                downAnimation();
            }
        });
        mUpAnimatorSet.start();
    }

    /**
     * 下落动画
     */
    private void downAnimation() {
        final ValueAnimator downAnimation = ValueAnimator.ofFloat(-200, 0);
        downAnimation.setInterpolator(new DecelerateInterpolator(1.2f));
        downAnimation.addUpdateListener(animation -> {
            mTranslateY = (float) downAnimation.getAnimatedValue();
            invalidate();
        });

        final ValueAnimator scaleAnimation = ValueAnimator.ofFloat(0.3f, 1);
        scaleAnimation.setInterpolator(new DecelerateInterpolator(1.2f));
        scaleAnimation.addUpdateListener(animation -> mScale = (float) scaleAnimation.getAnimatedValue());

        mDownAnimatorSet = new AnimatorSet();
        mDownAnimatorSet.setDuration(500);
        mDownAnimatorSet.playTogether(downAnimation, scaleAnimation);
        mDownAnimatorSet.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                upAnimation();
                exchangeDraw();
            }
        });
        mDownAnimatorSet.start();
    }

    public void start() {
        stop();
        upAnimation();
    }

    public void stop() {
        if (mUpAnimatorSet != null) {
            mUpAnimatorSet.removeAllListeners();
            mUpAnimatorSet.cancel();
        }
        if (mDownAnimatorSet != null) {
            mDownAnimatorSet.removeAllListeners();
            mDownAnimatorSet.cancel();
        }
    }
}
