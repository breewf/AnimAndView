package com.hy.animandview;

import android.app.Application;
import android.os.Handler;

/**
 * @author hy
 * @date 2020/9/17
 * Desc:App
 */
public class App extends Application {

    private static App sInstance;

    private static Handler mHandler;

    public static App getInstance() {
        return sInstance;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mHandler = new Handler();
    }
}
