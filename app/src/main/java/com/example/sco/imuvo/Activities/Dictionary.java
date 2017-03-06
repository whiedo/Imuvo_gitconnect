package com.example.sco.imuvo.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.sco.imuvo.R;

/**
 * Created by fte on 06.03.2017.
 */

public class Dictionary extends BaseActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_dictionary, frameLayout);

        webView = (WebView) findViewById(R.id.dictionaryWebview);
        webView.loadUrl(getString(R.string.dictionaryURL));

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                setTitle(getString(R.string.pageLoading));
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                setTitle(R.string.app_name);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(Dictionary.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
