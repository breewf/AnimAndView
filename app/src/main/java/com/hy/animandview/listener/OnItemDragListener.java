package com.hy.animandview.listener;

/**
 * @author hy
 * @date 2020/10/27
 * desc:
 **/
public interface OnItemDragListener {
    /**
     * onItemMove
     *
     * @param fromPosition fromPosition
     * @param toPosition   toPosition
     */
    void onItemMove(int fromPosition, int toPosition);

    /**
     * onItemRemoved
     *
     * @param position position
     */
    void onItemRemoved(int position);
}
