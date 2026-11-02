package com.hieupt.android.standalonescrollbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.core.widget.NestedScrollView;
import com.facebook.places.model.PlaceFields;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NestedScrollView2.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bJ\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\rJ\b\u0010\u0013\u001a\u00020\u0007H\u0007J\b\u0010\u0014\u001a\u00020\u0007H\u0007J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0014J(\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0007H\u0014R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/view/NestedScrollView2;", "Landroidx/core/widget/NestedScrollView;", PlaceFields.CONTEXT, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "drawListeners", "", "Lcom/hieupt/android/standalonescrollbar/view/OnDrawListener;", "scrollChangeListeners", "Lcom/hieupt/android/standalonescrollbar/view/OnScrollChangedListener;", "addOnDrawListener", "", "onDrawListener", "addOnScrollListener", "onScrollChangeListener", "calculateVerticalScrollOffset", "calculateVerticalScrollRange", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onScrollChanged", "l", "t", "oldl", "oldt", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public final class NestedScrollView2 extends NestedScrollView {
    private final Set<OnDrawListener> drawListeners;
    private final Set<OnScrollChangedListener> scrollChangeListeners;

    public NestedScrollView2(Context context) {
        this(context, null, 0, 6, null);
    }

    public NestedScrollView2(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ NestedScrollView2(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? (AttributeSet) null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedScrollView2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.drawListeners = new LinkedHashSet();
        this.scrollChangeListeners = new LinkedHashSet();
    }

    public final void addOnDrawListener(OnDrawListener onDrawListener) {
        Intrinsics.checkNotNullParameter(onDrawListener, "onDrawListener");
        this.drawListeners.add(onDrawListener);
    }

    public final void addOnScrollListener(OnScrollChangedListener onScrollChangeListener) {
        Intrinsics.checkNotNullParameter(onScrollChangeListener, "onScrollChangeListener");
        this.scrollChangeListeners.add(onScrollChangeListener);
    }

    public final int calculateVerticalScrollRange() {
        return computeVerticalScrollRange();
    }

    public final int calculateVerticalScrollOffset() {
        return computeVerticalScrollOffset();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        Iterator<T> it2 = this.drawListeners.iterator();
        while (it2.hasNext()) {
            ((OnDrawListener) it2.next()).onDraw(canvas);
        }
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.View
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Iterator<T> it2 = this.scrollChangeListeners.iterator();
        while (it2.hasNext()) {
            ((OnScrollChangedListener) it2.next()).onScrollChanged(l, t, oldl, oldt);
        }
    }
}
