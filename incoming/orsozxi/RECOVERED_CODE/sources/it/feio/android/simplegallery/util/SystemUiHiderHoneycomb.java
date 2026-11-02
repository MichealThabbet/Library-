package it.feio.android.simplegallery.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import it.orsozoxi.android.orsonotes.R2;

/* loaded from: classes2.dex */
public class SystemUiHiderHoneycomb extends SystemUiHiderBase {
    private int mHideFlags;
    private int mShowFlags;
    private View.OnSystemUiVisibilityChangeListener mSystemUiVisibilityChangeListener;
    private int mTestFlags;
    private boolean mVisible;

    protected SystemUiHiderHoneycomb(Activity activity, View view, int i) {
        super(activity, view, i);
        this.mVisible = true;
        this.mSystemUiVisibilityChangeListener = new View.OnSystemUiVisibilityChangeListener() { // from class: it.feio.android.simplegallery.util.SystemUiHiderHoneycomb.1
            @Override // android.view.View.OnSystemUiVisibilityChangeListener
            public void onSystemUiVisibilityChange(int i2) {
                if ((i2 & SystemUiHiderHoneycomb.this.mTestFlags) == 0) {
                    SystemUiHiderHoneycomb.this.mAnchorView.setSystemUiVisibility(SystemUiHiderHoneycomb.this.mShowFlags);
                    if (Build.VERSION.SDK_INT < 16) {
                        SystemUiHiderHoneycomb.this.mActivity.getActionBar().show();
                        SystemUiHiderHoneycomb.this.mActivity.getWindow().setFlags(0, 1024);
                    }
                    SystemUiHiderHoneycomb.this.mOnVisibilityChangeListener.onVisibilityChange(true);
                    SystemUiHiderHoneycomb.this.mVisible = true;
                    return;
                }
                if (Build.VERSION.SDK_INT < 16) {
                    SystemUiHiderHoneycomb.this.mActivity.getActionBar().hide();
                    SystemUiHiderHoneycomb.this.mActivity.getWindow().setFlags(1024, 1024);
                }
                SystemUiHiderHoneycomb.this.mOnVisibilityChangeListener.onVisibilityChange(false);
                SystemUiHiderHoneycomb.this.mVisible = false;
            }
        };
        this.mShowFlags = 0;
        this.mHideFlags = 1;
        this.mTestFlags = 1;
        if ((this.mFlags & 2) != 0) {
            this.mShowFlags |= 1024;
            this.mHideFlags |= R2.attr.subMenuArrow;
        }
        if ((this.mFlags & 6) != 0) {
            this.mShowFlags |= 512;
            this.mHideFlags |= 514;
            this.mTestFlags |= 2;
        }
    }

    @Override // it.feio.android.simplegallery.util.SystemUiHiderBase, it.feio.android.simplegallery.util.SystemUiHider
    public void setup() {
        this.mAnchorView.setOnSystemUiVisibilityChangeListener(this.mSystemUiVisibilityChangeListener);
    }

    @Override // it.feio.android.simplegallery.util.SystemUiHiderBase, it.feio.android.simplegallery.util.SystemUiHider
    public void hide() {
        this.mAnchorView.setSystemUiVisibility(this.mHideFlags);
    }

    @Override // it.feio.android.simplegallery.util.SystemUiHiderBase, it.feio.android.simplegallery.util.SystemUiHider
    public void show() {
        this.mAnchorView.setSystemUiVisibility(this.mShowFlags);
    }

    @Override // it.feio.android.simplegallery.util.SystemUiHiderBase, it.feio.android.simplegallery.util.SystemUiHider
    public boolean isVisible() {
        return this.mVisible;
    }
}
