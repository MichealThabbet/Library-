package com.app.piapostolos.Alarm.Bubbles;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.FrameLayout;

/* loaded from: classes.dex */
public class BubbleBaseLayout extends FrameLayout {
    private BubblesLayoutCoordinator layoutCoordinator;
    private WindowManager.LayoutParams params;
    private WindowManager windowManager;

    public void setLayoutCoordinator(BubblesLayoutCoordinator bubblesLayoutCoordinator) {
        this.layoutCoordinator = bubblesLayoutCoordinator;
    }

    BubblesLayoutCoordinator getLayoutCoordinator() {
        return this.layoutCoordinator;
    }

    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    WindowManager getWindowManager() {
        return this.windowManager;
    }

    public void setViewParams(WindowManager.LayoutParams layoutParams) {
        this.params = layoutParams;
    }

    public WindowManager.LayoutParams getViewParams() {
        return this.params;
    }

    public BubbleBaseLayout(Context context) {
        super(context);
    }

    public BubbleBaseLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BubbleBaseLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
