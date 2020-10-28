package com.hy.animandview;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author hy
 * @date 2020/9/17
 * ClassDesc:MainActivity.
 **/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_test1).setOnClickListener(view ->
                startActivity(new Intent(this, TestActivity1.class)));

        findViewById(R.id.btn_test2).setOnClickListener(view ->
                startActivity(new Intent(this, MotionActivity.class)));

        findViewById(R.id.btn_test3).setOnClickListener(view ->
                startActivity(new Intent(this, TestActivity3.class)));

        findViewById(R.id.btn_test4).setOnClickListener(view ->
                startActivity(new Intent(this, TestActivity4.class)));

        findViewById(R.id.btn_test5).setOnClickListener(view ->
                startActivity(new Intent(this, TestActivity5.class)));

    }
}