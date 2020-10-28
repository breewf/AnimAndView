package com.hy.animandview;

import android.os.Bundle;
import android.widget.TextView;

import com.hy.animandview.adapter.CirclePicAdapter;
import com.hy.animandview.bean.CirclePic;
import com.hy.animandview.callback.ItemDragHelperCallback;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author hy
 * @date 2020/10/27
 * desc: TestActivity5
 **/
public class TestActivity5 extends AppCompatActivity {

    private static final int MAX_SELECT_IMAGE_NUM = 9;

    private RecyclerView mRecyclerView;
    private TextView mDelTv;

    private CirclePicAdapter mAdapter;
    private ArrayList<CirclePic> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test5);

        mRecyclerView = findViewById(R.id.rv_pic);
        mDelTv = findViewById(R.id.del_layout);

        initDataList();

        initRecyclerView();
    }

    private void initDataList() {
        mDataList.clear();
        CirclePic circlePic = new CirclePic(CirclePic.IMAGE, R.mipmap.image_001);
        mDataList.add(circlePic);
        CirclePic circlePic2 = new CirclePic(CirclePic.IMAGE, R.mipmap.image_002);
        mDataList.add(circlePic2);
        CirclePic circlePic3 = new CirclePic(CirclePic.IMAGE, R.mipmap.touxiang01);
        mDataList.add(circlePic3);
        CirclePic circlePic4 = new CirclePic(CirclePic.IMAGE, R.mipmap.touxiang02);
        mDataList.add(circlePic4);
        CirclePic circlePic5 = new CirclePic(CirclePic.IMAGE, R.mipmap.touxiang03);
        mDataList.add(circlePic5);
        CirclePic circlePic6 = new CirclePic(CirclePic.IMAGE, R.mipmap.touxiang04);
        mDataList.add(circlePic6);
        CirclePic circlePic7 = new CirclePic(CirclePic.IMAGE, R.mipmap.touxiang05);
        mDataList.add(circlePic7);
        CirclePic circlePic8 = new CirclePic(CirclePic.IMAGE, R.mipmap.touxiang06);
        mDataList.add(circlePic8);
        CirclePic circlePic9 = new CirclePic(CirclePic.IMAGE, R.mipmap.biaoqing1);
        mDataList.add(circlePic9);

        CirclePic circlePic10 = new CirclePic(CirclePic.IMAGE, R.mipmap.biaoqing1);
        mDataList.add(circlePic10);
        CirclePic circlePic11 = new CirclePic(CirclePic.IMAGE, R.mipmap.biaoqing1);
        mDataList.add(circlePic11);
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        ItemDragHelperCallback callback = new ItemDragHelperCallback(this, mDelTv);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter = new CirclePicAdapter(this, mDataList));
        mAdapter.setNewInstance(mDataList);

        refreshAdapter();
    }

    /**
     * 刷新列表
     */
    public void refreshAdapter() {
        if (mAdapter == null) {
            return;
        }
        // 多余九张图片则删除多余的图片
        int imageSize = mAdapter.getData().size();
        if (imageSize > MAX_SELECT_IMAGE_NUM) {
            int overNum = imageSize - MAX_SELECT_IMAGE_NUM;
            for (int i = 0; i < overNum; i++) {
                mAdapter.removeAt(mAdapter.getData().size() - 1);
            }
        }

        // 不足九张图片且不存在+号则添加+号
        imageSize = mAdapter.getData().size();
        if (imageSize < MAX_SELECT_IMAGE_NUM) {
            boolean hasAdd = false;
            for (int i = imageSize - 1; i >= 0; i--) {
                if (mAdapter.getData().get(i).getItemType() == CirclePic.ADD) {
                    hasAdd = true;
                    break;
                }
            }
            if (!hasAdd) {
                CirclePic circlePicAdd = new CirclePic(CirclePic.ADD);
                mAdapter.addData(circlePicAdd);
            }
        }
    }
}