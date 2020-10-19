package com.hy.animandview;

import android.os.Bundle;

import com.hy.animandview.view.ClockView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author hy
 * @date 2020/10/12
 * desc: TestActivity2
 **/
public class TestActivity2 extends AppCompatActivity {

    private ClockView mClockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        findViewById(R.id.btn).setOnClickListener(view -> {
            if (mClockView != null) {
                mClockView.start();
            }
        });

        mClockView = findViewById(R.id.clock_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClockView != null) {
            mClockView.cancelAnim();
        }
    }
}