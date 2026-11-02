package com.app.piapostolos.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes.dex */
public class UrlImageView extends LinearLayout {
    private final Handler imageLoadedHandler;
    private Context mContext;
    private Drawable mDrawable;
    private ImageView mImage;
    private ProgressBar mSpinner;

    public UrlImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.imageLoadedHandler = new Handler(new Handler.Callback() { // from class: com.app.piapostolos.Views.UrlImageView.2
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what != -1) {
                    return true;
                }
                UrlImageView.this.mImage.setImageDrawable(UrlImageView.this.mDrawable);
                UrlImageView.this.mImage.setVisibility(0);
                UrlImageView.this.mSpinner.setVisibility(8);
                return true;
            }
        });
        init(context);
    }

    public UrlImageView(Context context) {
        super(context);
        this.imageLoadedHandler = new Handler(new Handler.Callback() { // from class: com.app.piapostolos.Views.UrlImageView.2
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what != -1) {
                    return true;
                }
                UrlImageView.this.mImage.setImageDrawable(UrlImageView.this.mDrawable);
                UrlImageView.this.mImage.setVisibility(0);
                UrlImageView.this.mSpinner.setVisibility(8);
                return true;
            }
        });
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        ImageView imageView = new ImageView(this.mContext);
        this.mImage = imageView;
        imageView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.mImage.setVisibility(8);
        ProgressBar progressBar = new ProgressBar(this.mContext);
        this.mSpinner = progressBar;
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.mSpinner.setIndeterminate(true);
        addView(this.mSpinner);
        addView(this.mImage);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.app.piapostolos.Views.UrlImageView$1] */
    public void setImageDrawable(final String str) {
        this.mDrawable = null;
        this.mSpinner.setVisibility(0);
        this.mImage.setVisibility(8);
        new Thread() { // from class: com.app.piapostolos.Views.UrlImageView.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    UrlImageView.this.mDrawable = UrlImageView.getDrawableFromUrl(str);
                    UrlImageView.this.imageLoadedHandler.sendEmptyMessage(-1);
                } catch (MalformedURLException unused) {
                    UrlImageView.this.imageLoadedHandler.sendEmptyMessage(0);
                } catch (IOException unused2) {
                    UrlImageView.this.imageLoadedHandler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Drawable getDrawableFromUrl(String str) throws IOException {
        return Drawable.createFromStream((InputStream) new URL(str).getContent(), "name");
    }
}
