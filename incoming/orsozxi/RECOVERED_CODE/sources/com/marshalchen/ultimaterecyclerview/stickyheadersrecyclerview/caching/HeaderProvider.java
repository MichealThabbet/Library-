package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.caching;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public interface HeaderProvider {
    View getHeader(RecyclerView recyclerView, int i);

    void invalidate();
}
