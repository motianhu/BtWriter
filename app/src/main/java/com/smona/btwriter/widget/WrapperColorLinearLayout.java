package com.smona.btwriter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.goods.bean.GoodsTypeBean;

import java.util.List;

/**
 * description:
 *
 * @author motianhu
 * @email motianhu@qq.com
 * created on: 4/26/19 12:23 PM
 */
public class WrapperColorLinearLayout extends LinearLayout {

    private static SparseIntArray colors = new SparseIntArray(2);

    static {
        colors.put(0, R.color.color_BABABA);
        colors.put(1, R.color.color_F56C5E);
    }


    public WrapperColorLinearLayout(Context context) {
        super(context);
    }

    public WrapperColorLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapperColorLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updateData(List<GoodsTypeBean> data) {
        removeAllViews();
        if (data == null) {
            return;
        }

        int size = Math.min(3, data.size());
        for (int i = 0; i < size; i++) {
            GoodsTypeBean bean = data.get(i);
            TextView text = (TextView) View.inflate(getContext(), R.layout.item_goods_type, null);
            text.setText(bean.getTypeName());
            text.setTag(bean);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 0;
            params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.dimen_4dp);
            addView(text, params);
        }
    }

    public int getSelectId() {
        if(getChildCount() == 0) {
            return 0;
        }
        GoodsTypeBean bean = (GoodsTypeBean)getChildAt(0).getTag();
        return bean.getId();
    }
}
