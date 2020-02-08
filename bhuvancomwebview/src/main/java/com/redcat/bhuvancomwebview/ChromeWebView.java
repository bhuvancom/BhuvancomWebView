package com.redcat.bhuvancomwebview;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class ChromeWebView extends WebChromeClient {

    BhuvanComWebView.Listener listener;
    protected ChromeWebView(BhuvanComWebView.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (listener!=null){
            listener.onPageProgress(newProgress);
        }

        super.onProgressChanged(view, newProgress);
    }
}
