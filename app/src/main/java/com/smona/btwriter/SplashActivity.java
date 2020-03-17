package com.smona.btwriter;


import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.data.AccountDataCenter;
import com.smona.btwriter.data.AccountInfo;
import com.smona.btwriter.data.LanuageDataCenter;
import com.smona.btwriter.language.BaseLanguageActivity;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.SPUtils;
import com.smona.http.config.GsonUtil;

@Route(path = ARouterPath.PATH_TO_SPLASH)
public class SplashActivity extends BaseLanguageActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gotoMain();
    }

    private void gotoMain() {
        mHandler.postDelayed(() -> {
            LanuageDataCenter.getInstance().loadLanuage();
            //读取持久化登录信息
            String loginInfo = (String) SPUtils.get(SPUtils.LOGIN_INFO, "");
            String path = ARouterPath.PATH_TO_LOGIN;
            if(!TextUtils.isEmpty(loginInfo)) {
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
}
