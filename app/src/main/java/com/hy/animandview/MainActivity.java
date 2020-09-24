package com.hy.animandview;

import android.os.Bundle;

import com.hy.animandview.view.LVCircularSmile;
import com.hy.animandview.view.LoadingLineView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author hy
 * @date 2020/9/17
 * ClassDesc:MainActivity.
 **/
public class MainActivity extends AppCompatActivity {

    private LoadingLineView mLoadingLineView;

    private LVCircularSmile mLVCircularSmile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {
//            if (mLoadingLineView != null) {
//                mLoadingLineView.start();
//            }

            if (mLVCircularSmile != null) {
                mLVCircularSmile.startAnim();
            }
        });

        mLoadingLineView = findViewById(R.id.loading_line_view);
        mLVCircularSmile = findViewById(R.id.loading_smile);

    }
}