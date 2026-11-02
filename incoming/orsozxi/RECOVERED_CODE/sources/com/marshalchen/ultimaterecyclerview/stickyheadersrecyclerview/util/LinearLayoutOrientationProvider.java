package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.util;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class LinearLayoutOrientationProvider implements OrientationProvider {
    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.util.OrientationProvider
    public int getOrientation(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation();
        }
        throw new IllegalStateException("StickyListHeadersDecoration can only be used with a LinearLayoutManager.");
    }
}
