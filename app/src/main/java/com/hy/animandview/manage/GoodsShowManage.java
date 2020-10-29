package com.hy.animandview.manage;

import android.content.Context;

import com.hy.animandview.bean.GoodsInfo;
import com.hy.animandview.layout.GoodsLayout;

import java.util.List;

/**
 * @author hy
 * @date 2020/10/29
 * desc:
 **/
public class GoodsShowManage {

    private Context mContext;

    private GoodsLayout mGoodsLayout;

    private List<GoodsInfo> mDataList;

    public GoodsShowManage(Context context) {
        mContext = context;
    }

    public void attachView(GoodsLayout goodsLayout) {
        mGoodsLayout = goodsLayout;
    }

    public void setDataList(List<GoodsInfo> dataList) {
        mDataList = dataList;
    }

    public void init() {

    }
}
