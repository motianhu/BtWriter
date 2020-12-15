package com.smona.btwriter.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.smona.btwriter.R;

public class CommonDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitBtn;
    private TextView cancelTv;


    private Context mContext;
    private String content;
    private String positiveName;
    private String cancel;
    private String title;
    private OnCommitListener listener;

    private SpannableString spannableString;
    private boolean isContainLink = false;

    public CommonDialog(Context context) {
        this(context, null, null, null);
    }

    public CommonDialog(Context context, String title, String content) {
        this(context, title, content, null);
    }

    public CommonDialog(Context context, String title, String content, OnCommitListener listener) {
        super(context, R.style.commdialog);
        this.mContext = context;
        this.title = title;
        this.content = content;
        this.listener = listener;
        setCanceledOnTouchOutside(false);
    }

    public CommonDialog setCommitListener(OnCommitListener listener) {
        this.listener = listener;
        return this;
    }

    public CommonDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CommonDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public CommonDialog setCancel(String cancel) {
        this.cancel = cancel;
        return this;
    }

    public CommonDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public CommonDialog setContainLink(boolean isLink){
        this.isContainLink = isLink;
        return this;
    }

    public CommonDialog setSpannable(SpannableString spannable) {
        this.spannableString = spannable;
        return this;
    }

    public CommonDialog setInterceptKey(int key){
        setOnKeyListener((dialog, keyCode, event) -> keyCode == key);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        initView();
    }

    private void initView() {
        contentTxt = findViewById(R.id.tv_content);
        titleTxt = findViewById(R.id.tv_title);
        submitBtn = findViewById(R.id.bt_ok);
        submitBtn.setOnClickListener(this);
        cancelTv = findViewById(R.id.tv_cancel);
        cancelTv.setOnClickListener(this);

        if (!TextUtils.isEmpty(content)) {
            contentTxt.setText(content);
        }

        if (spannableString != null && isContainLink) {
            contentTxt.append(spannableString);
            contentTxt.setMovementMethod(new LinkMovementMethod());
        }

        if (!TextUtils.isEmpty(positiveName)) {
            submitBtn.setText(positiveName);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }

        if (!TextUtils.isEmpty(cancel)) {
            cancelTv.setText(cancel);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_ok) {
            if (listener != null) {
                listener.onClick(this, true);
            }
            dismiss();
        } else if (v.getId() == R.id.tv_cancel) {
            if (listener != null) {
                listener.onClick(this, false);
            }
            dismiss();
        }
    }

    @Override
    public void show() {
        super.show();
        try {
            // 将对话框的大小按屏幕大小的百分比设置
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = (int) (display.getWidth() * 0.65); //设置宽度
            getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnCommitListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}
