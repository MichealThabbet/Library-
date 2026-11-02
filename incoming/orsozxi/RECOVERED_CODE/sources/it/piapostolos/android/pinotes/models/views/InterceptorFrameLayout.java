package it.piapostolos.android.pinotes.models.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import it.piapostolos.android.pinotes.models.listeners.OnViewTouchedListener;

/* loaded from: classes3.dex */
public class InterceptorFrameLayout extends FrameLayout {
    private OnViewTouchedListener mOnViewTouchedListener;

    public InterceptorFrameLayout(Context context) {
        super(context);
    }

    public InterceptorFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        OnViewTouchedListener onViewTouchedListener = this.mOnViewTouchedListener;
        if (onViewTouchedListener != null) {
            onViewTouchedListener.onViewTouchOccurred(motionEvent);
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void setOnViewTouchedListener(OnViewTouchedListener onViewTouchedListener) {
        this.mOnViewTouchedListener = onViewTouchedListener;
    }
}
