package it.feio.android.simplegallery.util;

import android.app.Activity;
import android.view.View;

/* loaded from: classes2.dex */
public class SystemUiHiderBase extends SystemUiHider {
    private boolean mVisible;

    protected SystemUiHiderBase(Activity activity, View view, int i) {
        super(activity, view, i);
        this.mVisible = true;
    }

    @Override // it.feio.android.simplegallery.util.SystemUiHider
    public void setup() {
        if ((this.mFlags & 1) == 0) {
            this.mActivity.getWindow().setFlags(768, 768);
        }
    }

    @Override // it.feio.android.simplegallery.util.SystemUiHider
    public boolean isVisible() {
        return this.mVisible;
    }

    @Override // it.feio.android.simplegallery.util.SystemUiHider
    public void hide() {
        if ((this.mFlags & 2) != 0) {
            this.mActivity.getWindow().setFlags(1024, 1024);
        }
        this.mOnVisibilityChangeListener.onVisibilityChange(false);
        this.mVisible = false;
    }

    @Override // it.feio.android.simplegallery.util.SystemUiHider
    public void show() {
        if ((this.mFlags & 2) != 0) {
            this.mActivity.getWindow().setFlags(0, 1024);
        }
        this.mOnVisibilityChangeListener.onVisibilityChange(true);
        this.mVisible = true;
    }
}
