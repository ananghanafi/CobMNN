package com.masbi.cobmnn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SendGmail extends AppCompatActivity {
    WebView mywebviewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gmail);
        mywebviewEmail = (WebView) findViewById(R.id.webviewEmail);
//        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
//        mywebview.getSettings().setUserAgentString(newUA);
        mywebviewEmail.setFocusable(true);
        mywebviewEmail.setDuplicateParentStateEnabled(true);
        mywebviewEmail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mywebviewEmail.getSettings().setJavaScriptEnabled(true);
        mywebviewEmail.loadUrl("https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
        mywebviewEmail.setWebViewClient(new WebViewClient());

    }
    @Override
    public void onBackPressed() {
        if (mywebviewEmail.canGoBack()) {
            mywebviewEmail.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

