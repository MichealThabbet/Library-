package com.marshalchen.ultimaterecyclerview;

/* loaded from: classes2.dex */
public interface ObservableScrollViewCallbacks {
    void onDownMotionEvent();

    void onScrollChanged(int i, boolean z, boolean z2);

    void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState);
}
