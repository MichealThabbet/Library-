package com.marshalchen.ultimaterecyclerview.dragsortadapter;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class NoForegroundShadowBuilder extends DragSortShadowBuilder {
    private final WeakReference<View> viewRef;

    public NoForegroundShadowBuilder(View view, Point point) {
        super(view, point);
        this.viewRef = new WeakReference<>(view);
    }

    @Override // com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortShadowBuilder, android.view.View.DragShadowBuilder
    public void onDrawShadow(Canvas canvas) {
        View view = this.viewRef.get();
        if (view != null) {
            Drawable drawable = null;
            if (view instanceof FrameLayout) {
                FrameLayout frameLayout = (FrameLayout) view;
                if (frameLayout.getForeground() != null) {
                    Drawable foreground = frameLayout.getForeground();
                    frameLayout.setForeground(null);
                    drawable = foreground;
                }
            }
            view.draw(canvas);
            if (drawable != null) {
                ((FrameLayout) view).setForeground(drawable);
                return;
            }
            return;
        }
        Log.e(TAG, "Asked to draw drag shadow but no view");
    }
}
