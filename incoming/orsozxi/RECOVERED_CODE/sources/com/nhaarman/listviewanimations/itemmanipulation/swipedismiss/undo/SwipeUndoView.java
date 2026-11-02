package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

/* loaded from: classes2.dex */
class SwipeUndoView extends FrameLayout {
    private View mPrimaryView;
    private View mUndoView;

    SwipeUndoView(Context context) {
        super(context);
    }

    void setPrimaryView(View view) {
        View view2 = this.mPrimaryView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mPrimaryView = view;
        addView(view);
    }

    void setUndoView(View view) {
        View view2 = this.mUndoView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mUndoView = view;
        view.setVisibility(8);
        addView(this.mUndoView);
    }

    View getPrimaryView() {
        return this.mPrimaryView;
    }

    View getUndoView() {
        return this.mUndoView;
    }
}
