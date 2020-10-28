package com.hy.animandview.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author hy
 * @date 2020/10/27
 * desc: CirclePic
 **/
public class CirclePic extends BaseModel implements MultiItemEntity {

    public static final int ADD = 0;
    public static final int IMAGE = 1;

    private int itemType = IMAGE;

    public int imageId;
    public String path;

    public CirclePic(int itemType) {
        this.itemType = itemType;
    }

    public CirclePic(int itemType, String path) {
        this.itemType = itemType;
        this.path = path;
    }

    public CirclePic(int itemType, int imageId) {
        this.itemType = itemType;
        this.imageId = imageId;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

}
