package com.smona.btwriter.webview;

import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.base.ui.activity.BaseUiActivity;
import com.smona.btwriter.R;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_WEBVIEW)
public class WebViewActivity extends BaseUiActivity {

    private TextView titleTv;
    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        String url = getIntent().getStringExtra(ARouterPath.PATH_TO_WEBVIEW);
        titleTv = findViewById(R.id.title);
        findViewById(R.id.back).setOnClickListener(v -> finish());
        webView = findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                getWebTitle();
            }
        });
        webView.loadUrl(url);
    }

    private void getWebTitle() {
        WebBackForwardList forwardList = webView.copyBackForwardList();
        WebHistoryItem item = forwardList.getCurrentItem();
        if (item != null) {
            titleTv.setText(item.getTitle());
        }
    }
}
