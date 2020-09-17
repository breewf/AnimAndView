package com.hy.animandview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author hy
 * @date 2020/9/17
 * ClassDesc:MainActivity.
 **/
public class MainActivity extends AppCompatActivity {

    private LoadingLineView mLoadingLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {
            if (mLoadingLineView != null) {
                mLoadingLineView.start();
            }
        });

        mLoadingLineView = findViewById(R.id.loading_line_view);

    }
}