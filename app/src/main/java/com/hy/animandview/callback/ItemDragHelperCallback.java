package com.hy.animandview.callback;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.hy.animandview.BuildConfig;
import com.hy.animandview.R;
import com.hy.animandview.holder.CircleImageAddViewHolder;
import com.hy.animandview.listener.OnItemDragListener;
import com.hy.animandview.listener.SimpleAnimatorListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author hy
 * @date 2020/10/27
 * desc:
 **/
public class ItemDragHelperCallback extends ItemTouchHelper.Callback {

    public static final String TAG = "ItemDragHelperCallback";

    private final Context mContext;
    private final TextView mDelTv;

    /**
     * 是否显示删除区域
     */
    private boolean mShowDelLayout;

    /**
     * 拖拽到删除区域后触发的动画
     */
    private boolean mDelAreaAnim;

    /**
     * 拖拽后松手
     */
    private boolean mReleaseDrag;

    /**
     * 要删除的position
     */
    private int mDelPos = -1;

    /**
     * 拖拽item的ViewHolder
     */
    private RecyclerView.ViewHolder mDragViewHolder;

    public ItemDragHelperCallback(Context context, TextView delTv) {
        mContext = context;
        mDelTv = delTv;
    }

    /**
     * 设置item是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // 添加按钮不可移动
        if (viewHolder instanceof CircleImageAddViewHolder) {
            return 0;
        }
        int dragFlags;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 当用户从item原来的位置拖动item到新位置的过程中调用
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // 不同Type之间不可移动
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "onMove--fromPosition:" + fromPosition + " toPosition:" + toPosition);
        }
        // 移动item回调
        if (recyclerView.getAdapter() instanceof OnItemDragListener) {
            OnItemDragListener listener = ((OnItemDragListener) recyclerView.getAdapter());
            listener.onItemMove(fromPosition, toPosition);

            mDelPos = toPosition;
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "onMove--mDelPos:" + mDelPos);
            }
        }
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 当长按选中item的时候（拖拽开始的时候）调用
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // 不在闲置状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            mDragViewHolder = viewHolder;
            mDelPos = viewHolder.getAdapterPosition();
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "onSelectedChanged--开始拖拽--mDelPos:" + mDelPos);
            }
            showDelLayout();

            viewHolder.itemView.animate()
                    .scaleX(1.15f).scaleY(1.15f)
                    .setDuration(200)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        } else {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "onSelectedChanged--停止拖拽--mDelPos:" + mDelPos);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    private void showDelLayout() {
        if (mShowDelLayout) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(mDelTv,
                "translationY", mDelTv.getHeight(), 0);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mShowDelLayout = true;
                mDelTv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        animator.start();

        // 震动
        vibrator();
    }

    private void hideDelLayout() {
        if (!mShowDelLayout) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(mDelTv,
                "translationY", 0, mDelTv.getHeight());
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mShowDelLayout = false;
                mDelTv.setVisibility(View.INVISIBLE);
            }
        });
        animator.start();
    }

    /**
     * 震动
     */
    private void vibrator() {
        if (mContext != null) {
            Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(30);
                }
            }
        }
    }

    /**
     * 当用户与item的交互结束并且item也完成了动画时调用
     */
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        resetDragFlag();
        hideDelLayout();
        viewHolder.itemView.animate()
                .scaleX(1.0f).scaleY(1.0f)
                .setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .start();
        super.clearView(recyclerView, viewHolder);
    }

    private void resetDragFlag() {
        mReleaseDrag = false;
        mDelPos = -1;
    }

    /**
     * 当手指离开之后，view回到指定位置动画的持续时间
     */
    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        // 松手释放
        mReleaseDrag = true;
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

    /**
     * RecyclerView调用onDraw时调用，如果想自定义item对用户互动的响应，可以重写该方法
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (mContext == null || mDelTv == null) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }
        int[] delLocation = new int[2];
        mDelTv.getLocationInWindow(delLocation);
        int delAreaX = delLocation[0];
        int delAreaY = delLocation[1];

        int itemWidth = viewHolder.itemView.getWidth();
        int itemHeight = viewHolder.itemView.getHeight();

        int[] itemLocation = new int[2];
        viewHolder.itemView.getLocationInWindow(itemLocation);
        int itemX = itemLocation[0];
        int itemY = itemLocation[1];

        if (itemY + itemHeight > delAreaY) {
            // 拖动到触发删除区域
            if (!mDelAreaAnim) {
                mDelAreaAnim = true;
                // 动画
                viewHolder.itemView.animate()
                        .scaleX(0.8f).scaleY(0.8f)
                        .alpha(0.8f)
                        .setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();
                mDelTv.animate()
                        .scaleX(1.35f).scaleY(1.35f)
                        .translationY(-mDelTv.getHeight() / 6f)
                        .setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();
                // 震动
                vibrator();
                // 更改文本
                mDelTv.setText(mContext.getString(R.string.str_release_del));
            }

            if (mDelAreaAnim && mReleaseDrag && mDelPos >= 0) {
                if (recyclerView.getAdapter() instanceof OnItemDragListener) {
                    // 删除item回调
                    viewHolder.itemView.setVisibility(View.INVISIBLE);
                    OnItemDragListener listener = ((OnItemDragListener) recyclerView.getAdapter());
                    listener.onItemRemoved(mDelPos);
                }
                resetDragFlag();
            }
        } else {
            // 拖动离开触发删除区域
            if (mDelAreaAnim) {
                mDelAreaAnim = false;

                viewHolder.itemView.animate()
                        .scaleX(1.0f).scaleY(1.0f)
                        .setDuration(200)
                        .alpha(1.0f)
                        .setInterpolator(new AccelerateInterpolator())
                        .start();
                mDelTv.animate()
                        .scaleX(1.0f).scaleY(1.0f)
                        .translationY(0f)
                        .setDuration(200)
                        .setInterpolator(new AccelerateInterpolator())
                        .start();

                mDelTv.setText(mContext.getString(R.string.str_del));
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 长按拖拽功能
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        // 不支持滑动功能
        return false;
    }

}
