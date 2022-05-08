package edu.skku.cs.mysimplecalendar.activity.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.activity.BaseActivity;
import edu.skku.cs.mysimplecalendar.databinding.ActivityMainBinding;
import edu.skku.cs.mysimplecalendar.databinding.ActivityWebBinding;

public class WebActivity extends BaseActivity {

    ActivityWebBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = bind(R.layout.activity_web);

        bind.backBtn.setOnClickListener(v->{onBackPressed();});
        getExtra();
    }


    private void getExtra(){
        String title = getIntent().getStringExtra(CODE_WEBVIEW_TITLE);
        String url = getIntent().getStringExtra(CODE_WEBVIEW_URL);
        bind.setTitle(title);

        setWebView(bind.webView,url);

    }

    private void setWebView(WebView view, String url)
    {
        setWebviewSetting(view.getSettings());

        view.setWebViewClient(webViewClient);

        view.loadUrl(url);
    }

    private void setWebviewSetting(WebSettings setting)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setting.setSafeBrowsingEnabled(true);
        }

        setting.setBuiltInZoomControls(false);
        setting.setSupportZoom(true);
        setting.setUseWideViewPort(true);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        setting.setLoadWithOverviewMode(true);
        setting.setDomStorageEnabled(false);
        setting.setJavaScriptEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    WebViewClient webViewClient = new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.setVisibility(View.INVISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bind.pgWebview.setVisibility(View.GONE);
            if (true //Internet OK
            ) {
                view.setVisibility(View.VISIBLE);
            } else {
                bind.imgNetworkNull.setVisibility (View.VISIBLE);
            }
        }

        @SuppressLint("WebViewClientOnReceivedSslError")
        @Override
        public void onReceivedSslError(
               WebView view, SslErrorHandler handler, SslError error) {
            Log.d("Webview","Ssl Error");

        }
    };



    public static final String CODE_WEBVIEW_TITLE = "code_webview_title";
    public static final String CODE_WEBVIEW_URL = "code_webview_url";
}