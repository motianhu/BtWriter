package com.smona.btwriter.goods.holder;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.goods.bean.CategoryContract;
import com.smona.btwriter.goods.bean.ShoppingCardBean;
import com.smona.image.loader.ImageLoaderDelegate;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCardListHolder extends XViewHolder {

    private ImageView iconIv;
    private TextView nameTv;
    private TextView categoryTv;
    private EditText numTv;
    private TextView priceTv;
    private View minusView;
    private View addView;
    public View delView;

    private CategoryContract categoryContract;

    public ShoppingCardListHolder(View itemView) {
        super(itemView);
        categoryContract = new CategoryContract(itemView.getContext());
        iconIv = itemView.findViewById(R.id.icon);
        nameTv = itemView.findViewById(R.id.name);
        categoryTv = itemView.findViewById(R.id.category);
        priceTv = itemView.findViewById(R.id.price);
        minusView = itemView.findViewById(R.id.minus);
        numTv = itemView.findViewById(R.id.num);
        ;
        addView = itemView.findViewById(R.id.plus);
        delView = itemView.findViewById(R.id.delete);
    }

    public void bindViews(ShoppingCardBean goodsBean) {
        Context context = itemView.getContext();
        ImageLoaderDelegate.getInstance().showCornerImage(goodsBean.getCoverImg(), iconIv, context.getResources().getDimensionPixelSize(R.dimen.dimen_10dp), 0);
        nameTv.setText(goodsBean.getName());
        if (goodsBean.getGoodsKind() == 1) {
            categoryTv.setVisibility(View.VISIBLE);
            categoryTv.setText(context.getResources().getString(R.string.category) + "  " + categoryContract.getCategoryName(goodsBean.getGoodsType() + ""));
        } else {
            categoryTv.setVisibility(View.GONE);
        }
        priceTv.setText(context.getString(R.string.rmb_sign) + ": " + goodsBean.getTotalPrice());
        if (goodsBean.getTmpCount() == 0) {
            goodsBean.setTmpCount(goodsBean.getAmount());
        }
        numTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    numTv.setText("1");
                } else if (s.toString().startsWith("0") && s.toString().length() == 1) {
                    numTv.setText("1");
                } else if (s.toString().startsWith("0") && s.toString().length() > 1) {
                    numTv.setText(s.toString().substring(1));
                }
                if (!TextUtils.isEmpty(s.toString())) {
                    goodsBean.setTmpCount(Integer.parseInt(s.toString()));
                }
            }
        });
        setNumText(goodsBean.getTmpCount());
        minusView.setOnClickListener(v -> clickMinus(goodsBean));
        addView.setOnClickListener(v -> clickPlus(goodsBean));
    }

    private void clickMinus(ShoppingCardBean goodsBean) {
        if (goodsBean.getTmpCount() <= 1) {
            minusView.setEnabled(false);
        } else {
            minusView.setEnabled(true);
            goodsBean.setTmpCount(goodsBean.getTmpCount() - 1);
            setNumText(goodsBean.getTmpCount());
        }
    }

    private void clickPlus(ShoppingCardBean goodsBean) {
        minusView.setEnabled(true);
        goodsBean.setTmpCount(goodsBean.getTmpCount() + 1);
        setNumText(goodsBean.getTmpCount());
    }

    private void setNumText(int amount) {
        numTv.setText(amount + "");
    }
}
