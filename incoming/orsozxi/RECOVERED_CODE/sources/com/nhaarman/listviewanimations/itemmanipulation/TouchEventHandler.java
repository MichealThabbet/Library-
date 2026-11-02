package com.nhaarman.listviewanimations.itemmanipulation;

import android.view.MotionEvent;

/* loaded from: classes2.dex */
public interface TouchEventHandler {
    boolean isInteracting();

    boolean onTouchEvent(MotionEvent motionEvent);
}
