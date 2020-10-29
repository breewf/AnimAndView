package com.hy.animandview.bean;

/**
 * @author hy
 * @date 2020/10/27
 * desc: 商品信息
 **/
public class GoodsInfo extends BaseModel {

    public int goodsId;
    public String url;
    public String title;
    public String desc;
    public int color;

    public GoodsInfo() {

    }

    public GoodsInfo(int goodsId, String url, String title, String desc, int color) {
        this.goodsId = goodsId;
        this.url = url;
        this.title = title;
        this.desc = desc;
        this.color = color;
    }
}
