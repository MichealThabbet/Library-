package com.hieupt.android.standalonescrollbar;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: ScrollableView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J+\u0010\u000e\u001a\u00020\u000f2!\u0010\u0010\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u000f0\u0011H&J+\u0010\u0015\u001a\u00020\u000f2!\u0010\u0016\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u000f0\u0011H&J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0012\u0010\n\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0012\u0010\f\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005¨\u0006\u0019"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/ScrollableView;", "", "scrollOffset", "", "getScrollOffset", "()I", "scrollOffsetRange", "getScrollOffsetRange", "scrollRange", "getScrollRange", "viewHeight", "getViewHeight", "viewWidth", "getViewWidth", "addOnDraw", "", "onDraw", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "caller", "addOnScrollChangedListener", "onScrollChanged", "scrollTo", "offset", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public interface ScrollableView {
    void addOnDraw(Function1<? super ScrollableView, Unit> onDraw);

    void addOnScrollChangedListener(Function1<? super ScrollableView, Unit> onScrollChanged);

    int getScrollOffset();

    int getScrollOffsetRange();

    int getScrollRange();

    int getViewHeight();

    int getViewWidth();

    void scrollTo(int offset);
}
