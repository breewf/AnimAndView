package com.hy.animandview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.hy.animandview.R;
import com.hy.animandview.bean.Particle;
import com.hy.animandview.listener.SimpleAnimatorListener;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @author hy
 * @date 2020/10/12
 * desc:
 **/
public class DimpleView extends View {

    private Context mContext;
    private Paint mPaint;

    private Path mPath;
    /**
     * 路径，用于测量扩散圆某一处的X,Y值
     */
    private PathMeasure mPathMeasure;

    /**
     * 扩散圆上某一点的x,y
     */
    private float[] pos = new float[2];
    /**
     * 扩散圆上某一点切线
     */
    private float[] tan = new float[2];

    private float centerX, centerY;

    private float mCircleRadius = 280f;

    private ValueAnimator mValueAnimator;

    private boolean mPause = true;

    private ArrayList<Particle> mParticleList = new ArrayList<>();

    public DimpleView(Context context) {
        this(context, null);
    }

    public DimpleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mCircleRadius = dp2px(100);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.white));

        mPath = new Path();
        mPathMeasure = new PathMeasure();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = ((float) w / 2);
        centerY = ((float) h / 2);

        mPath.addCircle(centerX, centerY, mCircleRadius, Path.Direction.CCW);
        // 添加path
        mPathMeasure.setPath(mPath, false);

        addParticle();
    }

    private void addParticle() {
        if (mParticleList == null) {
            mParticleList = new ArrayList<>();
        }
        mParticleList.clear();

        Random random = new Random();
        int num = 2000;
        float nextX;
        float nextY;
        float offSet;
        double angle;
        float speed;
        float maxOffset;

        for (int i = 0; i < num; i++) {
            // 按比例测量路径上每一点的值
            mPathMeasure.getPosTan(i / (float) num * mPathMeasure.getLength(), pos, tan);
            // X值随机偏移
            nextX = pos[0] + random.nextInt(6) - 3f;
            // Y值随机偏移
            nextY = pos[1] + random.nextInt(6) - 3f;

            offSet = random.nextInt(200);

            // 反余弦函数可以得到角度值，是弧度
            angle = Math.acos(((pos[0] - centerX) / mCircleRadius));

            // 速度
            speed = random.nextInt(2) + 1f;
            maxOffset = random.nextInt(200);
            mParticleList.add(new Particle(nextX, nextY, 2f, speed, 100, offSet, angle, maxOffset));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(ContextCompat.getColor(mContext, R.color.black));

        if (mParticleList == null || mParticleList.size() == 0) {
            return;
        }

        for (int i = 0; i < mParticleList.size(); i++) {
            Particle particle = mParticleList.get(i);

            // 设置画笔的透明度
            mPaint.setAlpha(particle.alpha);
            canvas.drawCircle(particle.x, particle.y, particle.radius, mPaint);
        }
    }

    public void start() {
        if (mPause && mValueAnimator != null) {
            mValueAnimator.start();
            mPause = false;
            return;
        }

        reset();

        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setDuration(5000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(animation -> {
            updateParticle((Float) animation.getAnimatedValue());
            invalidate();
        });
        mValueAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.start();
        mPause = false;
    }

    public void pause() {
        if (mValueAnimator == null) {
            return;
        }
        mValueAnimator.pause();
        mPause = true;
    }

    public boolean isPause() {
        return mPause;
    }

    private void reset() {
        addParticle();
    }

    private void updateParticle(float animatedValue) {
        if (mParticleList == null || mParticleList.size() == 0) {
            return;
        }
        for (int i = 0; i < mParticleList.size(); i++) {
            Particle particle = mParticleList.get(i);

            if (particle.offset > particle.maxOffset) {
                particle.offset = 0;
                particle.speed = new Random().nextInt(2) + 1f;
            }

            particle.alpha = (int) ((1f - particle.offset / particle.maxOffset) * 225f);
            particle.x = (float) (centerX + Math.cos(particle.angle) * (mCircleRadius + particle.offset));
            if (particle.y > centerY) {
                particle.y = (float) (Math.sin(particle.angle) * (mCircleRadius + particle.offset) + centerY);
            } else {
                particle.y = (float) (centerY - Math.sin(particle.angle) * (mCircleRadius + particle.offset));
            }
            float randomX = new Random().nextInt(2) - 1f;
            float randomY = new Random().nextInt(2) - 1f;
            particle.x = particle.x + randomX;
            particle.y = particle.y + randomY;
            particle.offset += particle.speed;
        }
    }

    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
