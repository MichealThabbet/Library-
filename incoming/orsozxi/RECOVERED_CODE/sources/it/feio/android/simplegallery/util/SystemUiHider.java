package it.feio.android.simplegallery.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/* loaded from: classes2.dex */
public abstract class SystemUiHider {
    public static final int FLAG_FULLSCREEN = 2;
    public static final int FLAG_HIDE_NAVIGATION = 6;
    public static final int FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES = 1;
    private static OnVisibilityChangeListener sDummyListener = new OnVisibilityChangeListener() { // from class: it.feio.android.simplegallery.util.SystemUiHider.1
        @Override // it.feio.android.simplegallery.util.SystemUiHider.OnVisibilityChangeListener
        public void onVisibilityChange(boolean z) {
        }
    };
    protected Activity mActivity;
    protected View mAnchorView;
    protected int mFlags;
    protected OnVisibilityChangeListener mOnVisibilityChangeListener = sDummyListener;

    public interface OnVisibilityChangeListener {
        void onVisibilityChange(boolean z);
    }

    public abstract void hide();

    public abstract boolean isVisible();

    public abstract void setup();

    public abstract void show();

    public static SystemUiHider getInstance(Activity activity, View view, int i) {
        if (Build.VERSION.SDK_INT >= 11) {
            return new SystemUiHiderHoneycomb(activity, view, i);
        }
        return new SystemUiHiderBase(activity, view, i);
    }

    protected SystemUiHider(Activity activity, View view, int i) {
        this.mActivity = activity;
        this.mAnchorView = view;
        this.mFlags = i;
    }

    public void toggle() {
        if (isVisible()) {
            hide();
        } else {
            show();
        }
    }

    public void setOnVisibilityChangeListener(OnVisibilityChangeListener onVisibilityChangeListener) {
        if (onVisibilityChangeListener == null) {
            onVisibilityChangeListener = sDummyListener;
        }
        this.mOnVisibilityChangeListener = onVisibilityChangeListener;
    }
}
