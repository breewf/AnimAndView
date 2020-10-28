package com.hy.animandview.holder.base;

/**
 * @author hy
 * @date 2020/10/27
 * desc: IViewHolder
 **/
public interface IViewHolder<T> {
    /**
     * bind
     *
     * @param item item
     */
    void bind(T item);
}
