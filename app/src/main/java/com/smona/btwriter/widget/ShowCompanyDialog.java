package com.smona.btwriter.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.main.bean.CompanyBean;

public class ShowCompanyDialog extends Dialog {

    private TextView titleTxt;
    private TextView submitBtn;

    private String title;
    private CompanyBean companyBean;
    private ShowCompanyDialog.OnCommitListener listener;

    public ShowCompanyDialog(Context context) {
        super(context, R.style.commdialog);
        setCanceledOnTouchOutside(true);
    }

    public ShowCompanyDialog setCompanyBean(CompanyBean companyBean) {
        this.companyBean = companyBean;
        return this;
    }

    public ShowCompanyDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ShowCompanyDialog setOnCommitListener(ShowCompanyDialog.OnCommitListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_company_info_layout);
        initView();
    }

    private void initView() {
        titleTxt = findViewById(R.id.tv_title);
        submitBtn = findViewById(R.id.tv_ok);
        submitBtn.setOnClickListener(v -> clickOk());

        refreshTitle();

        TextView textView = findViewById(R.id.distributor);
        textView.setText(companyBean.getUsername());
        textView = findViewById(R.id.email);
        textView.setText(companyBean.getEmail());
        textView = findViewById(R.id.phone);
        textView.setText(companyBean.getMobile());
        textView = findViewById(R.id.contact);
        textView.setText(companyBean.getContact());
        textView = findViewById(R.id.company);
        textView.setText(companyBean.getCompany());
        textView = findViewById(R.id.account);
        textView.setText(companyBean.getBankAccount());
    }

    private void refreshTitle() {
        if (!TextUtils.isEmpty(title) && titleTxt != null) {
            titleTxt.setText(title);
        }
    }

    private void clickOk() {
        if (listener != null) {
            listener.onClick(this);
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
        void onClick(Dialog dialog);
    }
}