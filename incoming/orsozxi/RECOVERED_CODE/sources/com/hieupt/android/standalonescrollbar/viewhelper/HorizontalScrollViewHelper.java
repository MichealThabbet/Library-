package com.hieupt.android.standalonescrollbar.viewhelper;

import android.graphics.Canvas;
import com.hieupt.android.standalonescrollbar.HorizontalScrollableView;
import com.hieupt.android.standalonescrollbar.ScrollableView;
import com.hieupt.android.standalonescrollbar.view.HorizontalScrollView2;
import com.hieupt.android.standalonescrollbar.view.OnDrawListener;
import com.hieupt.android.standalonescrollbar.view.OnScrollChangedListener;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: HorizontalScrollViewHelper.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J+\u0010\u000f\u001a\u00020\u00102!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00100\u0012H\u0016J+\u0010\u0017\u001a\u00020\u00102!\u0010\u0018\u001a\u001d\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00100\u0012H\u0016J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\b¨\u0006\u001b"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/viewhelper/HorizontalScrollViewHelper;", "Lcom/hieupt/android/standalonescrollbar/HorizontalScrollableView;", "scrollView", "Lcom/hieupt/android/standalonescrollbar/view/HorizontalScrollView2;", "(Lcom/hieupt/android/standalonescrollbar/view/HorizontalScrollView2;)V", "scrollOffset", "", "getScrollOffset", "()I", "scrollRange", "getScrollRange", "viewHeight", "getViewHeight", "viewWidth", "getViewWidth", "addOnDraw", "", "onDraw", "Lkotlin/Function1;", "Lcom/hieupt/android/standalonescrollbar/ScrollableView;", "Lkotlin/ParameterName;", "name", "caller", "addOnScrollChangedListener", "onScrollChanged", "scrollTo", "offset", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public final class HorizontalScrollViewHelper implements HorizontalScrollableView {
    private final HorizontalScrollView2 scrollView;

    public HorizontalScrollViewHelper(HorizontalScrollView2 scrollView) {
        Intrinsics.checkNotNullParameter(scrollView, "scrollView");
        this.scrollView = scrollView;
    }

    @Override // com.hieupt.android.standalonescrollbar.HorizontalScrollableView, com.hieupt.android.standalonescrollbar.ScrollableView
    public int getScrollOffsetRange() {
        return HorizontalScrollableView.DefaultImpls.getScrollOffsetRange(this);
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public int getViewWidth() {
        return this.scrollView.getWidth();
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public int getViewHeight() {
        return this.scrollView.getHeight();
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public int getScrollRange() {
        return this.scrollView.calculateHorizontalScrollRange() + this.scrollView.getPaddingStart() + this.scrollView.getPaddingEnd();
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public int getScrollOffset() {
        return this.scrollView.calculateHorizontalScrollOffset();
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public void addOnScrollChangedListener(final Function1<? super ScrollableView, Unit> onScrollChanged) {
        Intrinsics.checkNotNullParameter(onScrollChanged, "onScrollChanged");
        this.scrollView.addOnScrollListener(new OnScrollChangedListener() { // from class: com.hieupt.android.standalonescrollbar.viewhelper.HorizontalScrollViewHelper.addOnScrollChangedListener.1
            @Override // com.hieupt.android.standalonescrollbar.view.OnScrollChangedListener
            public final void onScrollChanged(int i, int i2, int i3, int i4) {
                onScrollChanged.invoke(HorizontalScrollViewHelper.this);
            }
        });
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public void addOnDraw(final Function1<? super ScrollableView, Unit> onDraw) {
        Intrinsics.checkNotNullParameter(onDraw, "onDraw");
        this.scrollView.addOnDrawListener(new OnDrawListener() { // from class: com.hieupt.android.standalonescrollbar.viewhelper.HorizontalScrollViewHelper.addOnDraw.1
            @Override // com.hieupt.android.standalonescrollbar.view.OnDrawListener
            public final void onDraw(Canvas it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                onDraw.invoke(HorizontalScrollViewHelper.this);
            }
        });
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public void scrollTo(int offset) {
        HorizontalScrollView2 horizontalScrollView2 = this.scrollView;
        horizontalScrollView2.scrollTo(offset, horizontalScrollView2.getScrollY());
    }
}
