package com.hy.animandview;

import android.os.Bundle;

import com.hy.animandview.bean.GoodsInfo;
import com.hy.animandview.layout.GoodsLayout;
import com.hy.animandview.manage.GoodsShowManage;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author hy
 * @date 2020/10/29
 * desc: TestActivity6
 **/
public class TestActivity6 extends AppCompatActivity {

    private GoodsLayout mGoodsLayout;
    private List<GoodsInfo> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test6);

        mGoodsLayout = findViewById(R.id.goods_layout);

        initDataList();

        GoodsShowManage manage = new GoodsShowManage(this);
        manage.attachView(mGoodsLayout);
        manage.setDataList(mDataList);
        manage.init();
    }

    private void initDataList() {
        mDataList.clear();

        GoodsInfo goodsInfo1 = new GoodsInfo(
                1,
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1801164193,3602394305&fm=26&gp=0.jpg",
                "影像生活", "满300减100",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo2 = new GoodsInfo(
                2,
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1801164193,3602394305&fm=26&gp=0.jpg",
                "领千元现金", "",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo3 = new GoodsInfo(
                3,
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=25668084,2889217189&fm=26&gp=0.jpg",
                "口罩", "1元开抢",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo4 = new GoodsInfo(
                4,
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2531384262,3699915741&fm=26&gp=0.jpg",
                "宠物预售榜", "",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo5 = new GoodsInfo(
                5,
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1466681785,2890500789&fm=26&gp=0.jpg",
                "汽车11.11", "订金低千元",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo6 = new GoodsInfo(
                6,
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3547703274,3363083080&fm=11&gp=0.jpg",
                "品质生活", "",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo7 = new GoodsInfo(
                7,
                "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3348271096,2240890581&fm=11&gp=0.jpg",
                "机不离手", "",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo8 = new GoodsInfo(
                8,
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=457162810,2795871992&fm=11&gp=0.jpg",
                "无人机", "你也能飞",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo9 = new GoodsInfo(
                9,
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2860640912,1833965317&fm=26&gp=0.jpg",
                "智能家居榜", "来了老弟",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo10 = new GoodsInfo(
                10,
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4255547098,2077529314&fm=11&gp=0.jpg",
                "和平精英", "",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo11 = new GoodsInfo(
                11,
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3874067489,2447793058&fm=11&gp=0.jpg",
                "英雄联盟", "你好召唤师",
                R.color.colorAccent
        );
        GoodsInfo goodsInfo12 = new GoodsInfo(
                12,
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1181805538,1146807157&fm=26&gp=0.jpg",
                "王者荣耀", "百里守约",
                R.color.colorAccent
        );
    }

}