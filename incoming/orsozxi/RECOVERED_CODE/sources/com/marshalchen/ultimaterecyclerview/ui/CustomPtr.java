package com.marshalchen.ultimaterecyclerview.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import in.srain.cube.views.ptr.PtrFrameLayout;

/* loaded from: classes2.dex */
public class CustomPtr extends PtrFrameLayout {
    public CustomPtr(Context context) {
        super(context);
    }

    public CustomPtr(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomPtr(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // in.srain.cube.views.ptr.PtrFrameLayout, android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return dispatchTouchEventSupper(motionEvent);
    }
}
