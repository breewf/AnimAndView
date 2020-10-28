package com.hy.animandview.holder;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hy.animandview.R;
import com.hy.animandview.bean.CirclePic;
import com.hy.animandview.holder.base.IViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hy
 * @date 2020/10/27
 * desc:
 **/
public class CircleImageHolder extends BaseViewHolder implements IViewHolder<CirclePic> {

    public static final int RES_ID = R.layout.item_circle_image;

    public static final String TAG = "CircleImageHolder";

    @Bind(R.id.publish_image) ImageView mImageIv;

    private Activity mActivity;
    private CirclePic mItem;

    public CircleImageHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.mActivity = (Activity) view.getContext();
    }

    @Override
    public void bind(CirclePic item) {
        this.mItem = item;
        Log.i(TAG, "imageId:" + item.imageId);
        mImageIv.setImageResource(item.imageId);
    }

    @OnClick({R.id.publish_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_image:
                Toast.makeText(mActivity, "点了图片:" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
