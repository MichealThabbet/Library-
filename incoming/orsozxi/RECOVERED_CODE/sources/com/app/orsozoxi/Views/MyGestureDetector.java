package com.app.orsozoxi.Views;

import android.view.GestureDetector;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private MenuDateView menuView;

    public MyGestureDetector(MenuDateView menuDateView) {
        this.menuView = menuDateView;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return super.onDoubleTap(motionEvent);
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        float y = motionEvent2.getY() - motionEvent.getY();
        float x = motionEvent2.getX() - motionEvent.getX();
        if (Math.abs(x) > Math.abs(y)) {
            if (x > 0.0f) {
                if (!this.menuView.getMenuView().isMenuOpen()) {
                    this.menuView.getMenuView().setMenuOpen(true);
                    this.menuView.getMenuButton().performClick();
                }
            } else if (this.menuView.getMenuView().isMenuOpen()) {
                this.menuView.getMenuView().setMenuOpen(false);
                this.menuView.getMenuButton().performClick();
            }
        }
        return false;
    }
}
