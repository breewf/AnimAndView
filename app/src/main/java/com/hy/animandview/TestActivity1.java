package com.hy.animandview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hy.animandview.view.DimpleView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author hy
 * @date 2020/10/12
 * desc: TestActivity1
 **/
public class TestActivity1 extends AppCompatActivity {

    private ImageView mImageView;
    private DimpleView mDimpleView;

    private ObjectAnimator mObjectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        mImageView = findViewById(R.id.music_avatar);
        mDimpleView = findViewById(R.id.dimple_view);

        findViewById(R.id.btn).setOnClickListener(view -> {
            if (mDimpleView != null) {
                if (mDimpleView.isPause()) {
                    mDimpleView.start();
                    startAnim();
                } else {
                    mDimpleView.pause();
                    pauseAnim();
                }
            }
        });

        loadImage();
    }

    private void startAnim() {
        if (mObjectAnimator != null && mObjectAnimator.isPaused()) {
            mObjectAnimator.resume();
            return;
        }
        mObjectAnimator = ObjectAnimator.ofFloat(mImageView, View.ROTATION, 0f, 360f);
        mObjectAnimator.setDuration(14000);
        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
        mObjectAnimator.start();
    }

    private void pauseAnim() {
        if (mObjectAnimator == null) {
            return;
        }
        mObjectAnimator.pause();
    }

    private void cancelAnim() {
        if (mObjectAnimator == null) {
            return;
        }
        mObjectAnimator.cancel();
        mObjectAnimator = null;
    }

    private void loadImage() {
        String url = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1820718332,2563454361&fm=26&gp=0.jpg";
        Glide.with(this)
                .load(url)
                .circleCrop()
                .into(mImageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAnim();
    }
}