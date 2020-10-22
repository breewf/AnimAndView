package com.hy.animandview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.hy.animandview.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author hy
 * @date 2020/10/22
 * desc: DragImageView
 **/
public class DragImageView extends androidx.appcompat.widget.AppCompatImageView {

    public static final String TAG = "DragImageView";

    private Context mContext;

    private int mWidth;
    private int mHeight;

    private String mImageUrl;

    private final float[] mLocation = new float[2];

    private final int mChildCount = 5;
    private final List<ImageView> mChildrenList = new ArrayList<>();

    private final int mDragDelay = 100;

    private float mDownX = 0;
    private float mDownY = 0;

    public DragImageView(Context context) {
        super(context);
        init(context);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setImageUrl(String url) {
        mImageUrl = url;
    }

    private void init(Context context) {
        this.mContext = context;
    }

    public void initDragView() {
        post(() -> {
            mWidth = getWidth();
            mHeight = getHeight();

            mLocation[0] = ConvertUtils.dp2px(20);
            mLocation[1] = ConvertUtils.dp2px(80);
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "起始Location---->>x:" + mLocation[0] + "  y:" + mLocation[1]);
            }

            addChildImage();
        });
    }

    private void addChildImage() {
        mChildrenList.clear();
        for (int i = 0; i < mChildCount; i++) {
            ImageView imageView = new ImageView(mContext);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.width = mWidth;
            layoutParams.height = mHeight;
            imageView.setLayoutParams(layoutParams);
            imageView.setTranslationX(mLocation[0]);
            imageView.setTranslationY(mLocation[1]);
            float alpha = i == mChildCount - 1 ? 0.8f : 0.5f / mChildCount * (i + 1);
            imageView.setAlpha(alpha);

            Glide.with(this)
                    .load(mImageUrl)
                    .circleCrop()
                    .into(imageView);

            mChildrenList.add(imageView);

            FrameLayout frameLayout = (FrameLayout) getParent();
            if (frameLayout != null) {
                frameLayout.addView(imageView);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            mDownX = event.getRawX();
            mDownY = event.getRawY();
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "onTouchEvent---->>downX:" + mDownX + "  downY:" + mDownY);
            }

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            if (BuildConfig.DEBUG) {
//                Log.i(TAG, "onTouchEvent---->>getX:" + event.getX() + "  getY:" + getY());
//                Log.i(TAG, "onTouchEvent---->>getRawX:" + event.getRawX() + "  getRawY:" + event.getRawY());
//            }

            float moveX = event.getRawX() - mDownX;
            float moveY = event.getRawY() - mDownY;

//            if (BuildConfig.DEBUG) {
//                Log.i(TAG, "onTouchEvent---->>moveX:" + moveX + "  moveY:" + moveY);
//            }

            setTranslationX(moveX);
            setTranslationY(moveY);

            dragView(moveX, moveY);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            vibrator();

            animate()
                    .translationX(0)
                    .translationY(0)
                    .setDuration(600)
                    .setInterpolator(new AnticipateOvershootInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }
                    })
                    .start();

            releaseChildView();
        }
        return true;
    }

    private void vibrator() {
        if (mContext == null) {
            return;
        }
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(50);
        }
    }

    private void dragView(final float currentX, final float currentY) {
        for (int i = 0; i < mChildCount; i++) {
            final View child_i = mChildrenList.get(i);
            final int delay = mDragDelay * (mChildCount - 1 - i);
            child_i.postDelayed(() -> {
                child_i.setTranslationX(currentX + mLocation[0]);
                child_i.setTranslationY(currentY + mLocation[1]);
            }, delay);
        }
    }

    private void releaseChildView() {
        final Interpolator interpolator = new OvershootInterpolator();
        final int duration = 700;
        for (int i = 0; i < mChildCount; i++) {
            final View child_i = mChildrenList.get(i);
            final int delay = mDragDelay * (mChildCount - 1 - i);
            child_i.postDelayed(() ->
                    child_i.animate()
                            .translationX(mLocation[0])
                            .translationY(mLocation[1])
                            .setDuration(duration)
                            .setInterpolator(interpolator)
                            .start(), delay);
        }
    }

}
