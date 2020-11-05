package com.hy.animandview.manage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.hy.animandview.App;
import com.hy.animandview.R;
import com.hy.animandview.bean.GoodsInfo;
import com.hy.animandview.bean.GoodsLocation;
import com.hy.animandview.layout.GoodsLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

/**
 * @author hy
 * @date 2020/10/29
 * desc:
 **/
public class GoodsShowManage {

    public static final String TAG = "GoodsShowManage";

    public static final int NUM_ALL = 10;

    private final Context mContext;

    /**
     * 一个控件的宽，等于高
     */
    private final int mViewWidth;
    private final int mViewHeight;

    /**
     * 控件之间的间距
     */
    private final int mSpace;

    private GoodsLayout mGoodsLayout;

    private List<GoodsInfo> mDataList = new ArrayList<>();
    private List<View> mViewList = new ArrayList<>();
    private final List<GoodsLocation> mGoodsLocations = new ArrayList<>();

    private boolean mIsAnim;
    private boolean mIsDownTouch;

    public GoodsShowManage(Context context) {
        mContext = context;
        int screenWidth = ScreenUtils.getScreenWidth();
        mSpace = ConvertUtils.dp2px(5);
        // 一共5个间距的宽度
        mViewWidth = (screenWidth - mSpace * 5) / 4;
        mViewHeight = (screenWidth - mSpace * 5) / 4;
    }

    public void attachView(GoodsLayout goodsLayout) {
        mGoodsLayout = goodsLayout;
    }

    public void setDataList(List<GoodsInfo> dataList) {
        mDataList = dataList;
    }

    public void init() {
        initViewList();

        addViewList();

        if (mGoodsLayout != null) {
            mGoodsLayout.setTouchListener(new GoodsLayout.TouchListener() {
                @Override
                public void down(boolean isDown) {
                    if (isDown) {
                        mIsDownTouch = true;
                        // 按下--停止自动滚动
                    } else {
                        mIsDownTouch = false;
                        // 松手--开始自动滚动
                        checkAutoStartAnim();
                    }
                }

                @Override
                public void touch(float moveX) {
//                    if (moveX == 0) {
//                        return;
//                    }
//                    touchMoveX(moveX);
                }
            });
        }
    }

    @SuppressLint("InflateParams")
    private void initViewList() {
        if (mViewList == null) {
            mViewList = new ArrayList<>();
        }
        mViewList.clear();
        for (int i = 0; i < NUM_ALL; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_goods_intro, null);
            CardView cardView = view.findViewById(R.id.goods_card);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) cardView.getLayoutParams();
            params.width = mViewWidth;
            params.height = mViewHeight;
            cardView.setLayoutParams(params);
            mViewList.add(view);
        }
    }

    private void addViewList() {
        if (mGoodsLayout == null) {
            return;
        }
        if (ObjectUtils.isEmpty(mViewList)) {
            return;
        }
        if (ObjectUtils.isEmpty(mDataList)) {
            return;
        }
        if (mViewList.size() < NUM_ALL || mDataList.size() < NUM_ALL) {
            return;
        }
        // 从左向右，从上到下排列
        for (int i = 0; i < NUM_ALL; i++) {
            GoodsInfo goodsInfo = mDataList.get(i);

            View view = mViewList.get(i);

            ImageView imageViewBg = view.findViewById(R.id.bg);
            TextView title = view.findViewById(R.id.title);
            TextView desc = view.findViewById(R.id.desc);

            Glide.with(mContext).load(goodsInfo.url).into(imageViewBg);
            title.setText(goodsInfo.title);
            title.setBackgroundColor(ContextCompat.getColor(mContext, goodsInfo.color));
            desc.setText(goodsInfo.desc);
            desc.setBackgroundColor(ContextCompat.getColor(mContext, goodsInfo.color));

            GoodsLocation goodsLocation = new GoodsLocation();

            // 上边一排、下边一排的quotient从左到右均为:0,1,2,3,4
            int quotient = i / 2;
            int transX = quotient * mViewWidth + (quotient + 1) * mSpace;
            goodsLocation.transX = transX;
            view.setTranslationX(transX);
            boolean isCenter = quotient == 1 || quotient == 2;

            if (quotient != 4) {
                // 前面8个默认可见
                view.setVisibility(View.VISIBLE);
            } else {
                // 最后2个默认不可见--动画开始才可见
                view.setVisibility(View.INVISIBLE);
            }

            int transY;
            if (i % 2 == 0) {
                // 上边一排
                transY = mSpace * 2 + (isCenter ? mSpace * 2 : 0);
            } else {
                // 下边一排
                transY = mViewHeight + mSpace * 3 + (isCenter ? mSpace * 2 : 0);
            }
            goodsLocation.transY = transY;
            view.setTranslationY(transY);
            mGoodsLayout.addView(view);
            mGoodsLocations.add(goodsLocation);
        }
    }

    /**
     * 开始动画
     */
    public void startAnim() {
        if (mIsAnim) {
            return;
        }
        if (ObjectUtils.isEmpty(mViewList)) {
            return;
        }
        if (ObjectUtils.isEmpty(mGoodsLocations)) {
            return;
        }
        if (mViewList.size() != mGoodsLocations.size()) {
            return;
        }

        for (int i = 0; i < mViewList.size(); i++) {
            View view = mViewList.get(i);

            view.setVisibility(View.VISIBLE);

            GoodsLocation location = mGoodsLocations.get(i);

            float alpha;
            float toTransX;
            float toTransY;
            float scaleX;
            float scaleY;
            float rotationY;

            // 前2个以中心点翻转宽度的一半
            if (i < 2) {
                // 翻转中心点
                view.setPivotX(view.getWidth() / 2f);
                view.setPivotY(view.getHeight() / 2f);

                toTransX = -view.getWidth() / 2f;
                toTransY = location.transY;
                scaleX = 0.8f;
                scaleY = 1.5f;
                rotationY = -90f;
                alpha = 0.0f;
            } else if (i < 8) {
                // 后边的6个移动到前面相应的位置
                toTransX = mGoodsLocations.get(i - 2).transX;
                toTransY = mGoodsLocations.get(i - 2).transY;
                scaleX = 1.0f;
                scaleY = 1.0f;
                rotationY = 0f;
                alpha = 1.0f;
            } else {
                // 最后两个
                toTransX = mGoodsLocations.get(i - 2).transX;
                toTransY = mGoodsLocations.get(i - 2).transY;
                scaleX = 1.0f;
                scaleY = 1.0f;
                rotationY = 0f;
                alpha = 1.0f;

                // 翻转中心点
                view.setPivotX(0);
                view.setPivotY(view.getHeight() / 2f);

                view.setAlpha(0.0f);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
                view.setRotationY(90f);
                view.setTranslationX(location.transX - mSpace);
                view.setTranslationY(location.transY);
            }

            int finalI = i;
            view.animate()
                    .alpha(alpha)
                    .translationX(toTransX).translationY(toTransY)
                    .scaleX(scaleX).scaleY(scaleY)
                    .rotationY(rotationY)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(800)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            if (!mIsAnim) {
                                mIsAnim = true;
                            }
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.animate().setListener(null);
                            view.setAlpha(1f);
                            view.setScaleX(1f);
                            view.setScaleY(1f);
                            view.setRotationY(0f);

                            // 变更位置信息
                            location.transX = toTransX;
                            location.transY = toTransY;
                            location.scaleX = scaleX;
                            location.scaleY = scaleY;
                            location.rotationY = rotationY;

                            if (finalI < 2) {
                                view.setVisibility(View.INVISIBLE);

                                // 前两个的位置重置为最后两个
                                location.transX = 4 * mViewWidth + (4 + 1) * mSpace;
                                if (finalI % 2 == 0) {
                                    // 上边一排
                                    location.transY = mSpace * 2;
                                } else {
                                    // 下边一排
                                    location.transY = mViewHeight + mSpace * 3;
                                }
                            }

                            if (mIsAnim) {
                                // 交换列表位置--交换两次把前两个交换到最后
                                for (int k = 0; k < mViewList.size() - 1; k++) {
                                    Collections.swap(mViewList, k, k + 1);
                                }
                                for (int k = 0; k < mViewList.size() - 1; k++) {
                                    Collections.swap(mViewList, k, k + 1);
                                }

                                for (int k = 0; k < mGoodsLocations.size() - 1; k++) {
                                    Collections.swap(mGoodsLocations, k, k + 1);
                                }
                                for (int k = 0; k < mGoodsLocations.size() - 1; k++) {
                                    Collections.swap(mGoodsLocations, k, k + 1);
                                }

                                mIsAnim = false;

                                checkAutoStartAnim();
                            }
                        }
                    })
                    .start();
        }
    }

    private void touchMoveX(float moveX) {
        if (ObjectUtils.isEmpty(mViewList)) {
            return;
        }
        if (ObjectUtils.isEmpty(mGoodsLocations)) {
            return;
        }
        if (mViewList.size() != mGoodsLocations.size()) {
            return;
        }
        if (moveX < 0) {
            // 向左滑动
        } else {
            // 向右滑动
        }

        float x = moveX / 2f;
        float onceX = mViewWidth + mSpace;

        // 滑动边界
        if (Math.abs(x) > onceX) {
            return;
        }

        float scale = Math.abs(x) / mViewWidth;
        scale = Math.max(scale, 0f);
        scale = Math.min(scale, 1f);

        Log.i(TAG, "touchMoveX----x:" + x
                + " onceX:" + onceX
                + " scale:" + scale);

        float changeTransY = 0f;

        for (int i = 0; i < mViewList.size(); i++) {
            View view = mViewList.get(i);
            GoodsLocation location = mGoodsLocations.get(i);

            if (i < 2) {
                changeTransY = 0f;
            } else if (i < 4) {
                changeTransY = -mSpace * 2 * scale;
            } else if (i < 6) {
                changeTransY = 0f;
            } else if (i < 8) {
                changeTransY = mSpace * 2 * scale;
            } else {
                changeTransY = 0f;
            }

            view.setTranslationX(location.transX + x);
            view.setTranslationY(location.transY + changeTransY);
        }
    }

    private void checkAutoStartAnim() {
        App.getMainHandler().postDelayed(() -> {
            // 重复播放
            if (!mIsDownTouch) {
                startAnim();
            }
        }, 1000);
    }
}
