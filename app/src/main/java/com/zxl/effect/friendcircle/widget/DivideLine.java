package com.zxl.effect.friendcircle.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zxl.baselib.utils.Utils;

/**
 * Description
 * Created by zxl on 2018/5/29 下午3:27.
 * Email:444288256@qq.com
 */
public class DivideLine extends RecyclerView.ItemDecoration {
    private int mDivideHeight;

    public DivideLine() {
        this.mDivideHeight = Utils.dp2px(0.5f);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDivideHeight);
    }
}
