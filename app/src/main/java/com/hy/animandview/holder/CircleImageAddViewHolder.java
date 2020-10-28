package com.hy.animandview.holder;

import android.app.Activity;
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
public class CircleImageAddViewHolder extends BaseViewHolder implements IViewHolder<CirclePic> {

    public static final int RES_ID = R.layout.item_circle_image_add;

    @Bind(R.id.publish_image) ImageView mPublishImage;

    private Activity mActivity;

    public CircleImageAddViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.mActivity = (Activity) view.getContext();
    }

    @Override
    public void bind(CirclePic item) {

    }

    @OnClick({R.id.add_icon})
    public void onClick(View v) {
        if (v.getId() == R.id.add_icon) {
            Toast.makeText(mActivity, "添加图片", Toast.LENGTH_SHORT).show();
        }
    }

}
