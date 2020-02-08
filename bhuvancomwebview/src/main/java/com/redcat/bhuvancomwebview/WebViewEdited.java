package com.redcat.bhuvancomwebview;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;

import java.util.Objects;

public class WebViewEdited extends WebViewClient {
    private Context context;
    private String lastUrl = "";
    private String baseURL;
    private BhuvanComWebView.Listener listener;

    public String getLastUrl() {
        return lastUrl;
    }

    /**
     * @param context context in which webview will render
     * @param baseURL baseurl which is needed to render first page
     */
    WebViewEdited(Context context, String baseURL, BhuvanComWebView.Listener listener) {
        this.context = context;
        this.baseURL = baseURL;
        lastUrl = baseURL;
        this.listener = listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        lastUrl = url;
        Uri uri = Uri.parse(baseURL);
        Uri last = Uri.parse(lastUrl);
        String host = uri.getHost();
        if (!(last != null && Objects.requireNonNull(last.getHost()).equalsIgnoreCase(host)) || !lastUrl.contains("http") || lastUrl.contains("mailto")) {
            if (listener != null) {
                listener.onExternalPageRequest(url);
                return true;
            }
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (listener != null) {
            listener.onPageError(errorCode, description, failingUrl);
        }
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        String title = "SSL Certificate error.";
        StringBuilder message = new StringBuilder();
        switch (error.getPrimaryError()) {
            case SslError.SSL_UNTRUSTED:
                message.append("\nThe certificate authority is not trusted.");
                break;
            case SslError.SSL_EXPIRED:
                message.append("\nThe certificate has expired.");
                break;
            case SslError.SSL_IDMISMATCH:
                message.append("\nThe certificate Hostname mismatch.");
                break;
            case SslError.SSL_NOTYETVALID:
                message.append("\nThe certificate is not yet valid.");
                break;
            case SslError.SSL_DATE_INVALID:
                message.append("\nThe certificate date is invalid");
                break;
            case SslError.SSL_INVALID:
                message.append("\nThe certificate is invalid");
                break;
        }
        message.append("\nDo you want to continue anyway?");

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.proceed();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.cancel();
            }
        });

        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        super.onReceivedSslError(view, handler, error);
    }


}
