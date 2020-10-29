package com.hy.animandview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hy.animandview.R;

import androidx.core.content.ContextCompat;

/**
 * @author hy
 * @date 2020/10/29
 * desc:
 **/
public class LoadingCircleView extends View {

    private Path mPath;
    private Path mDst;
    private PathMeasure mPathMeasure;

    private Paint mPaint;

    private int mHeight;
    private int mWidth;

    /**
     * path路径的长度
     */
    private float mLength;

    private ValueAnimator mValueAnimator;
    private float mAnimatorValue;

    public LoadingCircleView(Context context) {
        this(context, null);
    }

    public LoadingCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mDst = new Path();
        mPaint = new Paint();
        mPathMeasure = new PathMeasure();

        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                3.4f, getContext().getResources().getDisplayMetrics()));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        float radius = mWidth / 2f - 20f;

        mPath.reset();
        mPath.addCircle(mWidth / 2f, mHeight / 2f, radius, Path.Direction.CW);
        // 生成pathMeasure对象
        mPathMeasure.setPath(mPath, true);
        // 获取path的长度
        mLength = mPathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray1));
        canvas.rotate(360.0f * mAnimatorValue - 45.0f, mWidth / 2f, mHeight / 2f);
        // 初始化截取的路径
        mDst.reset();
        float stop = mAnimatorValue * mLength;
        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
        // 截取路径后，并绘制路径
        mPathMeasure.getSegment(start, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);
    }

    public void start() {
        if (mValueAnimator != null) {
            mValueAnimator.removeAllListeners();
            mValueAnimator.cancel();
        }
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.addUpdateListener(animation -> {
            // 获取动画进行的百分比
            mAnimatorValue = (float) animation.getAnimatedValue();
            postInvalidate();
        });
        mValueAnimator.setDuration(2200);
        mValueAnimator.setRepeatCount(1000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.start();
    }
}
