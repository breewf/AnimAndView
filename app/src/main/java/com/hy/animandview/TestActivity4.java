package com.hy.animandview;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.hy.animandview.view.DragImageView;
import com.hy.animandview.view.LVCircularSmile;
import com.hy.animandview.view.LoadingFaceView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author hy
 * @date 2020/10/12
 * desc: TestActivity4
 **/
public class TestActivity4 extends AppCompatActivity {

    private FrameLayout mRootLayout;
    private DragImageView mDragImageView;

    private LoadingFaceView mLoadingFaceView;

    private LVCircularSmile mLVCircularSmile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);

        mRootLayout = findViewById(R.id.root_layout);
        mDragImageView = findViewById(R.id.image_drag);
        mLoadingFaceView = findViewById(R.id.loading_face_view);
        mLVCircularSmile = findViewById(R.id.loading_smile);

        findViewById(R.id.btn).setOnClickListener(view -> {
            if (mLoadingFaceView != null) {
                mLoadingFaceView.start();
            }

            if (mLVCircularSmile != null) {
                mLVCircularSmile.startAnim();
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