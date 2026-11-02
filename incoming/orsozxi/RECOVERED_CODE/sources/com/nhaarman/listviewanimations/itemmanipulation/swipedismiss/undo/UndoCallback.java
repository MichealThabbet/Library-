package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.view.View;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

/* loaded from: classes2.dex */
public interface UndoCallback extends OnDismissCallback {
    View getPrimaryView(View view);

    View getUndoView(View view);

    void onDismiss(View view, int i);

    void onUndo(View view, int i);

    void onUndoShown(View view, int i);
}
