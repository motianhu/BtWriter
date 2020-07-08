package com.smona.btwriter.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.smona.btwriter.R;

public class EditCommonDialog extends Dialog {

    private TextView titleTxt;
    private EditText contentEt;
    private TextView submitBtn;

    private String title;
    private String content;
    private String hint;
    private String positiveName;
    private EditCommonDialog.OnCommitListener listener;

    public EditCommonDialog(Context context) {
        super(context, R.style.commdialog);
        setCanceledOnTouchOutside(true);
    }

    public EditCommonDialog setTitle(String title) {
        this.title = title;
        refreshTitle();
        return this;
    }

    public EditCommonDialog setHint(String hint) {
        this.hint = hint;
        refreshHint();
        return this;
    }

    public EditCommonDialog setContent(String content) {
        this.content = content;
        refreshContent();
        return this;
    }

    public EditCommonDialog setOkName(String name) {
        this.positiveName = name;
        refreshBtnName();
        return this;
    }

    public EditCommonDialog setOnCommitListener(EditCommonDialog.OnCommitListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_layout);
        initView();
    }

    private void initView() {
        titleTxt = findViewById(R.id.tv_title);
        contentEt = findViewById(R.id.tv_content);
        submitBtn = findViewById(R.id.tv_ok);
        submitBtn.setOnClickListener(v -> clickOk());

        refreshTitle();
        refreshHint();
        refreshContent();
        refreshBtnName();
    }

    private void refreshHint() {
        if (!TextUtils.isEmpty(hint) && contentEt != null) {
            contentEt.setHint(hint);
        }
    }

    private void refreshTitle() {
        if (!TextUtils.isEmpty(title) && titleTxt != null) {
            titleTxt.setText(title);
        }
    }

    private void refreshBtnName() {
        if (!TextUtils.isEmpty(positiveName) && submitBtn != null) {
            submitBtn.setText(positiveName);
        }
    }

    private void refreshContent() {
        if (contentEt != null) {
            contentEt.setText(content);
        }
    }

    private void clickOk() {
        if (listener != null) {
            String inputContent = contentEt.getText().toString();
            listener.onClick(this, inputContent);
        }
    }

    @Override
    public void show() {
        super.show();
        try {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_240dp);
            getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnCommitListener {
        void onClick(Dialog dialog, String content);
    }
}