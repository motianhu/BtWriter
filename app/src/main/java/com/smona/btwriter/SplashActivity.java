package com.smona.btwriter;


import android.os.Bundle;
import android.os.Handler;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.language.BaseLanguageActivity;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;

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
            ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_LOGIN);
            overridePendingTransition(0, 0);
            finish();
        }, (long) (1000));
    }

}
