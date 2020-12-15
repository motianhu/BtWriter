package com.smona.btwriter.widget;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class ClickSpan extends ClickableSpan {
    private View.OnClickListener clickListener;
    private int highColor;

    public ClickSpan(View.OnClickListener listener, int highColor) {
        clickListener = listener;
        this.highColor = highColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(highColor);
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        clickListener.onClick(widget);
    }
}
