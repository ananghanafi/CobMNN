package com.masbi.cobmnn;

/**
 * Created by AnangHanafi on 07/03/2018.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DriveMcc extends AppCompatActivity {
    WebView mywebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drive_mcc);
        mywebview = (WebView) findViewById(R.id.webview);
//        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
//        mywebview.getSettings().setUserAgentString(newUA);
        mywebview.setFocusable(true);
        mywebview.setDuplicateParentStateEnabled(true);
        mywebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.loadUrl("http://drive.google.com/drive/mobile/folders/12qj40MNw9P0BQwps5oz4FChKZrPzbCfA");
        mywebview.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if (mywebview.canGoBack()) {
            mywebview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
