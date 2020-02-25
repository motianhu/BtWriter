package com.smona.btwriter.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration {
    private int horizontalSpace; // 整个RecyclerView与左右两侧的间距
    private int verticalSpace; // 整个RecyclerView与上下的间距

    public CommonItemDecoration(int horizontalSpace, int verticalSpace) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = horizontalSpace;
        outRect.right = horizontalSpace;
        outRect.bottom = verticalSpace;
        outRect.top = verticalSpace;
    }
}
