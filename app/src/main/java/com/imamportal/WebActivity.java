package com.imamportal;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.imamportal.utils.AppConstant;

public class WebActivity extends AppCompatActivity {

    Context context;
    WebView webView;
    SwipeRefreshLayout swipe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_page);

        context = this;
        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                WebAction();
            }
        });

        WebAction();

    }

    public void WebAction(){

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT < 8) {
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        }
        else {
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        }
        webView.loadUrl(AppConstant.webUrl);
        Log.e("webUrl",""+AppConstant.webUrl);
        //webView.loadUrl("http://imam.gov.bd/public/video/c098b53e3846c9b4258b8683f6f15771.mp4");
        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                //webView.loadUrl("file:///android_assets/error.html");

            }

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                swipe.setRefreshing(false);
            }

        });

    }


    @Override
    public void onBackPressed(){

        if (webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
    }
}
