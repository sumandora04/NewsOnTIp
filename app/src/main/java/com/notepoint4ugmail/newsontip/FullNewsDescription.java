package com.notepoint4ugmail.newsontip;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class FullNewsDescription extends AppCompatActivity {

    WebView webView;
    ProgressBar loader;
    FrameLayout frameLayout;
    String url = "";
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news_description);

        mToolbar = findViewById(R.id.navigation_action);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        loader = findViewById(R.id.loader);
        loader.setMax(100);
        webView = findViewById(R.id.webView);
        frameLayout = findViewById(R.id.frame_layout);


        webView.setWebViewClient(new HelpClient());

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                frameLayout.setVisibility(View.VISIBLE);
                loader.setProgress(progress);
                if (progress == 100) {
                    frameLayout.setVisibility(View.GONE);
                    loader.setVisibility(View.GONE);
                } else {
                    loader.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view,progress);
            }
        });

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        loader.setProgress(0);
        //Setting the progressbar color.
        loader.getProgressDrawable().setColorFilter(
               getResources().getColor(R.color.Orange), PorterDuff.Mode.MULTIPLY);

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(FullNewsDescription.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private class HelpClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
