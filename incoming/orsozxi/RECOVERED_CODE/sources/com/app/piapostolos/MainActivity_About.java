package com.app.piapostolos;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import com.app.piapostolos.Others.checksim;

/* loaded from: classes.dex */
public class MainActivity_About extends BaseActivity {
    private WebView myWebView;

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.activity_about);
        getWindow().addFlags(128);
        new checksim();
        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_About.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_About.this.finish();
            }
        });
        WebView webView = (WebView) findViewById(R.id.wv_about);
        this.myWebView = webView;
        webView.getSettings().setJavaScriptEnabled(true);
        this.myWebView.loadUrl("file:///android_asset/about.htm");
    }
}
