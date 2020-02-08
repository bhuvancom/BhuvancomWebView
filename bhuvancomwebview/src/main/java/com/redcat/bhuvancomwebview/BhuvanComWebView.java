package com.redcat.bhuvancomwebview;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;

public class BhuvanComWebView extends WebView {
    private WebView webView;
    private ActionBar actionBar;
    private String baseURL;
    private ProgressBar progressBar;
    private boolean enableJs = false;
    private String lastUrl;
    private boolean enableSaveFormData = false;
    private boolean enableLoadImageAuto = false;
    private boolean enableZoom = false;
    private boolean enableDomStorage = false;
    private boolean enableLoadWithOverViewMode = false;
    private boolean showMessageInSnackBar = false;
    private WebViewEdited webViewEdited;
    private Context context;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public BhuvanComWebView(Context context, WebView webView, ActionBar actionBar, ProgressBar progressBar) {
        super(context);
        this.webView = webView;
        this.actionBar = actionBar;
        this.progressBar = progressBar;
        this.context = context;

    }

    public void loadUrl(String URL) {
        baseURL = URL;
        setWebSettings();
        webViewEdited = new WebViewEdited(context, baseURL, listener);
        ChromeWebView chromeWebView = new ChromeWebView(listener);
        webView.setWebViewClient(webViewEdited);
        webView.setWebChromeClient(chromeWebView);
        webView.loadUrl(baseURL);
    }

    public interface Listener {
        /**
         * @param url this url is external to host name, open other activity or do whatever you want to do
         */
        void onExternalPageRequest(String url);

        void onPageError(int errorCode, String description, String failingUrl);

        void onPageProgress(int newProgress);
    }

    private void setWebSettings() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(isEnableLoadWithOverViewMode());
        webSettings.setDomStorageEnabled(isEnableDomStorage());
        webSettings.setSupportZoom(isEnableZoom());
        webSettings.setJavaScriptEnabled(isEnableJs());
        webSettings.setSaveFormData(isEnableSaveFormData());
        webSettings.setLoadsImagesAutomatically(isEnableLoadImageAuto());
    }

    public boolean isEnableJs() {
        return enableJs;
    }

    public void setEnableJs(boolean enableJs) {
        this.enableJs = enableJs;
    }

    public String getLastUrl() {
        return webViewEdited.getLastUrl();
    }

    public boolean isEnableSaveFormData() {
        return enableSaveFormData;
    }

    public void setEnableSaveFormData(boolean enableSaveFormData) {
        this.enableSaveFormData = enableSaveFormData;
    }

    public boolean isEnableLoadImageAuto() {
        return enableLoadImageAuto;
    }

    public void setEnableLoadImageAuto(boolean enableLoadImageAuto) {
        this.enableLoadImageAuto = enableLoadImageAuto;
    }

    public boolean isEnableZoom() {
        return enableZoom;
    }

    public void setEnableZoom(boolean enableZoom) {
        this.enableZoom = enableZoom;
    }

    public boolean isEnableDomStorage() {
        return enableDomStorage;
    }

    public void setEnableDomStorage(boolean enableDomStorage) {
        this.enableDomStorage = enableDomStorage;
    }

    public boolean isEnableLoadWithOverViewMode() {
        return enableLoadWithOverViewMode;
    }

    public void setEnableLoadWithOverViewMode(boolean enableLoadWithOverViewMode) {
        this.enableLoadWithOverViewMode = enableLoadWithOverViewMode;
    }

    public boolean isShowMessageInSnackBar() {
        return showMessageInSnackBar;
    }

    public void setShowMessageInSnackBar(boolean showMessageInSnackBar) {
        this.showMessageInSnackBar = showMessageInSnackBar;
    }
}
