package com.hy.animandview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.utils.widget.ImageFilterView;

/**
 * @author hy
 * @date 2020/10/13
 * desc: MotionActivity
 **/
public class MotionActivity extends AppCompatActivity {

    public static final String TAG = "MotionActivity";

    private MotionLayout mMotionLayout;
    private SeekBar mSeekBar;

    private ImageFilterView mImageView;
    private ImageFilterView mImageView2;

    private Button btnToStartScene;
    private Button btnToEndScene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        mMotionLayout = findViewById(R.id.motionLayout);
        mImageView = findViewById(R.id.image);
        mImageView2 = findViewById(R.id.image2);
        mSeekBar = findViewById(R.id.seekBar);
        btnToStartScene = findViewById(R.id.btnToStartScene);
        btnToEndScene = findViewById(R.id.btnToEndScene);

//        Glide.with(this)
//                .load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=360089235,730433134&fm=26&gp=0.jpg")
//                .into(mImageView);
        Glide.with(this)
                .load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=141092993,2986552179&fm=26&gp=0.jpg")
                .into(mImageView2);

        btnToStartScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 切换到 Start 场景
                mMotionLayout.transitionToStart();
            }
        });

        btnToEndScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 切换到 End 场景
                mMotionLayout.transitionToEnd();
            }
        });

        mSeekBar.setMax(0);
        mSeekBar.setMax(100);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMotionLayout.setProgress((float) (progress * 0.01));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mMotionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {
                Log.i(TAG, "onTransitionStarted");
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                Log.i(TAG, "onTransitionChange: " + v);
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                Log.i(TAG, "onTransitionCompleted");
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {
                Log.i(TAG, "onTransitionTrigger: " + v);
            }
        });
    }
}