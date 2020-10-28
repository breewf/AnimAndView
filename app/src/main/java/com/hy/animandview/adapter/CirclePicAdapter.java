package com.hy.animandview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hy.animandview.TestActivity5;
import com.hy.animandview.bean.CirclePic;
import com.hy.animandview.holder.CircleImageAddViewHolder;
import com.hy.animandview.holder.CircleImageHolder;
import com.hy.animandview.listener.OnItemDragListener;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author hy
 * @date 2020/10/27
 * desc: CirclePicAdapter
 **/
public class CirclePicAdapter extends BaseMultiItemQuickAdapter<CirclePic, BaseViewHolder> implements OnItemDragListener {

    private final Context mContext;

    public CirclePicAdapter(Context context, List<CirclePic> data) {
        super(data);
        mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CirclePic.IMAGE:
                return new CircleImageHolder(LayoutInflater.from(parent.getContext()).inflate(CircleImageHolder.RES_ID, parent, false));
            case CirclePic.ADD:
                return new CircleImageAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(CircleImageAddViewHolder.RES_ID, parent, false));
            default:
                break;
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, CirclePic item) {
        if (item == null) {
            return;
        }
        switch (item.getItemType()) {
            case CirclePic.IMAGE:
                CircleImageHolder imageHolder = (CircleImageHolder) viewHolder;
                imageHolder.itemView.setVisibility(View.VISIBLE);
                ((CircleImageHolder) viewHolder).bind(item);
                break;
            case CirclePic.ADD:
                ((CircleImageAddViewHolder) viewHolder).bind(item);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getData(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemRemoved(int position) {
        removeAt(position);
        if (mContext instanceof TestActivity5) {
            Toast.makeText(mContext, "删除了:" + position, Toast.LENGTH_SHORT).show();
            ((TestActivity5) mContext).refreshAdapter();
        }
    }

}
