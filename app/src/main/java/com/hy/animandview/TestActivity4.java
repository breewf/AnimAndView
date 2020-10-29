package com.hy.animandview;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.hy.animandview.view.DragImageView;
import com.hy.animandview.view.LoadingJumpView;
import com.hy.animandview.view.LoadingSmile;
import com.hy.animandview.view.LoadingCircleView;
import com.hy.animandview.view.LoadingFaceView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author hy
 * @date 2020/10/12
 * desc: TestActivity4
 **/
public class TestActivity4 extends AppCompatActivity {

    private DragImageView mDragImageView;

    private LoadingCircleView mLoadingCircleView;
    private LoadingJumpView mLoadingJumpView;
    private LoadingFaceView mLoadingFaceView;

    private LoadingSmile mLoadingSmile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);

        mDragImageView = findViewById(R.id.image_drag);
        mLoadingCircleView = findViewById(R.id.loading_circle_view);
        mLoadingJumpView = findViewById(R.id.loading_jump_view);
        mLoadingFaceView = findViewById(R.id.loading_face_view);
        mLoadingSmile = findViewById(R.id.loading_smile);

        findViewById(R.id.btn).setOnClickListener(view -> {
            if (mLoadingFaceView != null) {
                mLoadingFaceView.start();
            }

            if (mLoadingSmile != null) {
                mLoadingSmile.startAnim();
            }

            if (mLoadingCircleView != null) {
                mLoadingCircleView.start();
            }

            if (mLoadingJumpView != null) {
                mLoadingJumpView.start();
            }
        });

        String url = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2070453827,1163403148&fm=26&gp=0.jpg";
        Glide.with(this)
                .load(url)
                .circleCrop()
                .into(mDragImageView);

        mDragImageView.setImageUrl(url);
        mDragImageView.initDragView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}