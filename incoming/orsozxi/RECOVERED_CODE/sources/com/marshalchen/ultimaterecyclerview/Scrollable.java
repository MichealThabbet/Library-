package com.marshalchen.ultimaterecyclerview;

import android.view.ViewGroup;

/* loaded from: classes2.dex */
public interface Scrollable {
    int getCurrentScrollY();

    void scrollVerticallyTo(int i);

    void setScrollViewCallbacks(ObservableScrollViewCallbacks observableScrollViewCallbacks);

    void setTouchInterceptionViewGroup(ViewGroup viewGroup);
}
