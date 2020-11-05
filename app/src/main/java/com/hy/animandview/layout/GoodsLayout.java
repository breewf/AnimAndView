package com.hy.animandview.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author hy
 * @date 2020/10/29
 * desc:
 **/
public class GoodsLayout extends FrameLayout {

    public static final String TAG = "GoodsLayout";

    private Context mContext;

    private int mTouchSlop;
    private float mDownX, mDownY;
    private VelocityTracker mVelocityTracker;

    private static final int MAX_SCROLL_VELOCITY = 500;

    private TouchListener mTouchListener;

    public interface TouchListener {
        /**
         * down
         *
         * @param isDown isDown
         */
        void down(boolean isDown);

        /**
         * touch
         *
         * @param moveX moveX
         */
        void touch(float moveX);
    }

    public GoodsLayout(@NonNull Context context) {
        this(context, null);
    }

    public GoodsLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mVelocityTracker = VelocityTracker.obtain();
    }

    public void setTouchListener(TouchListener touchListener) {
        mTouchListener = touchListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                Log.i(TAG, "dispatchTouchEvent----mDownX:" + mDownX
                        + " mDownY:" + mDownY);
                if (mTouchListener != null) {
                    mTouchListener.down(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float evX = ev.getX();
                float evY = ev.getY();

                float moveX = evX - mDownX;
                float moveY = evY - mDownY;
                Log.i(TAG, "dispatchTouchEvent----moveX:" + moveX
                        + " moveY:" + moveY);

                if (mTouchListener != null) {
                    mTouchListener.touch(moveX);
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mTouchListener != null) {
                    mTouchListener.down(false);
                }
//                float yVelocity = mVelocityTracker.getYVelocity();
//                if (Math.abs(yVelocity) > MAX_SCROLL_VELOCITY) {
//
//                }
//                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return true;
        // return super.dispatchTouchEvent(ev);
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
}
