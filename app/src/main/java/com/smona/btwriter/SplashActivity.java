package com.smona.btwriter;


import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.data.AccountDataCenter;
import com.smona.btwriter.data.AccountInfo;
import com.smona.btwriter.data.LanuageDataCenter;
import com.smona.btwriter.language.BaseLanguageActivity;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.SPUtils;
import com.smona.btwriter.widget.ClickSpan;
import com.smona.btwriter.widget.CommonDialog;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.config.GsonUtil;

@Route(path = ARouterPath.PATH_TO_SPLASH)
public class SplashActivity extends BaseLanguageActivity {

    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        executeProtocol();
    }

    private void executeProtocol() {
        String version = (String)SPUtils.get(SPUtils.PROTOCOL_INFO, "");
        if (TextUtils.isEmpty(version)) {
            showProtocolDialog();
        }else{
            gotoMain();
        }
    }

    private void gotoMain() {
        mHandler.postDelayed(() -> {
            LanuageDataCenter.getInstance().loadLanuage();
            //读取持久化登录信息
            String loginInfo = (String) SPUtils.get(SPUtils.LOGIN_INFO, "");
            String path = ARouterPath.PATH_TO_LOGIN;
            if (!TextUtils.isEmpty(loginInfo)) {
                AccountInfo accountInfo = GsonUtil.jsonToObj(loginInfo, AccountInfo.class);
                if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getToken())) {
                    AccountDataCenter.getInstance().setAccountInfo(accountInfo.getEmail(), accountInfo.getToken());
                    path = ARouterPath.PATH_TO_MAIN;
                }
            }
            ARouterManager.getInstance().gotoActivity(path);
            overridePendingTransition(0, 0);
            finish();
        }, 1000);
    }

    private void showProtocolDialog() {
        String contentStart = getString(R.string.get_info_content1);
        String contentEnd = getString(R.string.get_info_content2);

        String user_protocol = getString(R.string.user_protocol);
        String privacy_protocol = getString(R.string.privacy_protocol);

        int userStart = contentStart.length();
        int privacyStart = userStart + user_protocol.length();
        int end = privacyStart + privacy_protocol.length();

        String content = contentStart + user_protocol + privacy_protocol + contentEnd;
        SpannableString spannableString = new SpannableString(content);
        int highColor = getResources().getColor(R.color.color_main);
        ClickSpan userProtocolClicked = new ClickSpan(v -> startH5(BusinessHttpService.USER_PROTOCOL), highColor);
        ClickSpan privacyProtocolClicked = new ClickSpan(v -> startH5(BusinessHttpService.PRIVACY_PROTOCOL), highColor);

        spannableString.setSpan(userProtocolClicked, userStart, privacyStart, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(privacyProtocolClicked, privacyStart, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        CommonDialog commonDialog = new CommonDialog(this).setTitle(getString(R.string.get_info_title)).setPositiveButton(getString(R.string.agreen)).setCancel(getString(R.string.not_agreen))
                .setCommitListener((dialog, confirm) -> {
                    if (confirm) {
                        SPUtils.put(SPUtils.PROTOCOL_INFO, "protocol_version");
                        gotoMain();
                    } else {
                        finish();
                    }
                    dialog.dismiss();
                });
        commonDialog.setContainLink(true);
        commonDialog.setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
        commonDialog.setSpannable(spannableString);
        commonDialog.show();
    }

    private void startH5(String url) {
        ARouterManager.getInstance().gotoActivityWithString(ARouterPath.PATH_TO_WEBVIEW, ARouterPath.PATH_TO_WEBVIEW, url);
    }
}
