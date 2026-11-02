package com.app.piapostolos.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import com.app.piapostolos.R;

/* loaded from: classes.dex */
public class ScheduleFragment extends Fragment {
    private View rootView;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.fragment_schedule, viewGroup, false);
        Log.d("radio_hash", "onCreate");
        return this.rootView;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d("radio_hash", "onResume");
        initView();
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        Log.d("radio_hash", "isVisibleToUser");
        if (z) {
            Log.d("radio_hash", "isVisibleToUser true");
            initView();
        }
    }

    private void initView() {
        WebView webView = (WebView) this.rootView.findViewById(R.id.v_schedule);
        webView.setBackgroundColor(0);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("https://services.invalid/unconfigured");
        webView.setWebViewClient(new WebViewClient() { // from class: com.app.piapostolos.Fragments.ScheduleFragment.1
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str, Bitmap bitmap) {
                ScheduleFragment.this.rootView.findViewById(R.id.v_progress_schedule).setVisibility(0);
                super.onPageStarted(webView2, str, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                ScheduleFragment.this.rootView.findViewById(R.id.v_progress_schedule).setVisibility(8);
                super.onPageFinished(webView2, str);
            }
        });
    }
}
