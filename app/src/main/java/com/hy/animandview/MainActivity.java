package com.hy.animandview;

import android.content.Intent;
import android.os.Bundle;

import com.hy.animandview.view.LVCircularSmile;
import com.hy.animandview.view.LoadingFaceView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author hy
 * @date 2020/9/17
 * ClassDesc:MainActivity.
 **/
public class MainActivity extends AppCompatActivity {

    private LoadingFaceView mLoadingFaceView;

    private LVCircularSmile mLVCircularSmile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_test1).setOnClickListener(view ->
                startActivity(new Intent(this, TestActivity1.class)));

        findViewById(R.id.btn_test2).setOnClickListener(view ->
                startActivity(new Intent(this, MotionActivity.class)));

        findViewById(R.id.btn_test3).setOnClickListener(view ->
                startActivity(new Intent(this, TestActivity2.class)));

        findViewById(R.id.btn).setOnClickListener(view -> {
            if (mLoadingFaceView != null) {
                mLoadingFaceView.start();
            }

            if (mLVCircularSmile != null) {
                mLVCircularSmile.startAnim();
            }
        });

        mLoadingFaceView = findViewById(R.id.loading_face_view);
        mLVCircularSmile = findViewById(R.id.loading_smile);

    }
}