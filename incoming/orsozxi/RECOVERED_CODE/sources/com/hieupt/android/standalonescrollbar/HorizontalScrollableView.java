package com.hieupt.android.standalonescrollbar;

import kotlin.Metadata;

/* compiled from: HorizontalScrollableView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/HorizontalScrollableView;", "Lcom/hieupt/android/standalonescrollbar/ScrollableView;", "scrollOffsetRange", "", "getScrollOffsetRange", "()I", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public interface HorizontalScrollableView extends ScrollableView {
    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    int getScrollOffsetRange();

    /* compiled from: HorizontalScrollableView.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 1})
    public static final class DefaultImpls {
        public static int getScrollOffsetRange(HorizontalScrollableView horizontalScrollableView) {
            return horizontalScrollableView.getScrollRange() - horizontalScrollableView.getViewWidth();
        }
    }
}
