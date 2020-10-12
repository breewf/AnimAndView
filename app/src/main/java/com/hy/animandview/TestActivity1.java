package com.hy.animandview;

import android.os.Bundle;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        mImageView = findViewById(R.id.music_avatar);
        mDimpleView = findViewById(R.id.dimple_view);

        findViewById(R.id.btn).setOnClickListener(view -> {
            if (mDimpleView != null) {
                mDimpleView.start();
            }
        });

    }
}