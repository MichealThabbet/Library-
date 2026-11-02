package com.app.orsozoxi;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import java.io.File;

/* loaded from: classes.dex */
public class PdfViewerActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_pdf);
        displayFromAsset(getIntent().getStringExtra("filepath"));
    }

    private void displayFromAsset(String str) {
        ((PDFView) findViewById(R.id.pdfView)).fromFile(new File(str)).defaultPage(0).onPageChange(this).enableAnnotationRendering(true).onLoad(this).scrollHandle(new DefaultScrollHandle(this)).spacing(10).onPageError(this).fitEachPage(true).load();
    }

    @Override // com.github.barteksc.pdfviewer.listener.OnPageChangeListener
    public void onPageChanged(int i, int i2) {
        Log.d("TAG", "onPageChanged " + i);
    }

    @Override // com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
    public void loadComplete(int i) {
        Log.d("TAG", "load page " + i);
    }

    @Override // com.github.barteksc.pdfviewer.listener.OnPageErrorListener
    public void onPageError(int i, Throwable th) {
        Log.d("TAG", "onPageError " + i);
    }
}
