package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes2.dex */
public interface UndoAdapter {
    View getUndoClickView(View view);

    View getUndoView(int i, View view, ViewGroup viewGroup);
}
