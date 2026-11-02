package com.app.piapostolos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;
import com.navdrawer.SimpleSideDrawer;
import it.orsozoxi.android.orsonotes.R2;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: classes.dex */
public class zweilawebsite extends FragmentActivity {
    String act = null;
    private GestureDetector gestureDetector;
    WebView myWebView;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.zweilawebsite);
        TextView textView = (TextView) findViewById(R.id.tv_header_title);
        getWindow().addFlags(128);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        int i = 0;
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.zweilawebsite.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        this.myWebView = (WebView) findViewById(R.id.webView122);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("url");
        this.myWebView.setBackgroundColor(0);
        String stringExtra2 = intent.getStringExtra(ShareConstants.MEDIA_TYPE);
        if (stringExtra2.equals("agenda")) {
            findViewById(R.id.linearLayout2).setVisibility(0);
        }
        String stringExtra3 = intent.getStringExtra("preact");
        this.act = stringExtra3;
        setTitle(stringExtra3);
        textView.setText(this.act);
        if (stringExtra2.equals(AppSettingsData.STATUS_NEW)) {
            this.myWebView.getSettings().setJavaScriptEnabled(true);
            this.myWebView.setWebViewClient(new myWebClient());
            this.myWebView.getSettings().setLoadWithOverviewMode(true);
            this.myWebView.getSettings().setUseWideViewPort(true);
            this.myWebView.loadUrl(stringExtra);
            return;
        }
        if (stringExtra2.equals("agenda")) {
            this.myWebView.setFocusableInTouchMode(false);
            this.myWebView.setFocusable(false);
            WebSettings settings = this.myWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);
            this.myWebView.loadUrl(stringExtra);
            Spinner spinner = (Spinner) findViewById(R.id.spinner12);
            int i2 = Calendar.getInstance().get(1);
            final ArrayList arrayList = new ArrayList();
            int i3 = 0;
            for (int i4 = R2.dimen.preference_seekbar_padding_horizontal; i4 < 2026; i4++) {
                if (i2 == i4) {
                    i = i3;
                }
                i3++;
                arrayList.add(i4 + "");
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item_white, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter((SpinnerAdapter) arrayAdapter);
            spinner.setPrompt("الأعوام");
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.zweilawebsite.2
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i5, long j) {
                    zweilawebsite.this.myWebView.loadUrl("file:///android_asset/Agenda/" + ((String) arrayList.get(i5)) + ".htm");
                }
            });
            spinner.setSelection(i);
            return;
        }
        this.myWebView.getSettings().setJavaScriptEnabled(true);
        this.myWebView.getSettings().setUseWideViewPort(true);
        this.myWebView.loadUrl(stringExtra);
    }

    public class myWebClient extends WebViewClient {
        public myWebClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            zweilawebsite.this.findViewById(R.id.progressBar1).setVisibility(8);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        this.myWebView.stopLoading();
        this.myWebView.clearFormData();
        this.myWebView.clearHistory();
        super.onDestroy();
    }
}
