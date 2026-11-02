package com.hieupt.android.standalonescrollbar.viewhelper;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.hieupt.android.standalonescrollbar.HorizontalScrollableView;
import com.hieupt.android.standalonescrollbar.ScrollableView;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: HorizontalRecyclerViewHelper.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J+\u0010\"\u001a\u00020#2!\u0010$\u001a\u001d\u0012\u0013\u0012\u00110&¢\u0006\f\b'\u0012\b\b(\u0012\u0004\b\b()\u0012\u0004\u0012\u00020#0%H\u0016J+\u0010*\u001a\u00020#2!\u0010+\u001a\u001d\u0012\u0013\u0012\u00110&¢\u0006\f\b'\u0012\b\b(\u0012\u0004\b\b()\u0012\u0004\u0012\u00020#0%H\u0016J\u0010\u0010,\u001a\u00020#2\u0006\u0010-\u001a\u00020\u0006H\u0016J\u0018\u0010.\u001a\u00020#2\u0006\u0010/\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u0006H\u0002R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\bR\u0014\u0010\u0016\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\bR\u0014\u0010\u0018\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\bR\u0014\u0010\u001a\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\bR\u0014\u0010 \u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\b¨\u00060"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/viewhelper/HorizontalRecyclerViewHelper;", "Lcom/hieupt/android/standalonescrollbar/HorizontalScrollableView;", Promotion.ACTION_VIEW, "Landroidx/recyclerview/widget/RecyclerView;", "(Landroidx/recyclerview/widget/RecyclerView;)V", "firstItemAdapterPosition", "", "getFirstItemAdapterPosition", "()I", "firstItemOffset", "getFirstItemOffset", "firstItemPosition", "getFirstItemPosition", "horizontalLinearLayoutManager", "Landroidx/recyclerview/widget/LinearLayoutManager;", "getHorizontalLinearLayoutManager", "()Landroidx/recyclerview/widget/LinearLayoutManager;", "isLayoutRtl", "", "()Z", "itemCount", "getItemCount", "itemWidth", "getItemWidth", "scrollOffset", "getScrollOffset", "scrollRange", "getScrollRange", "tempRect", "Landroid/graphics/Rect;", "viewHeight", "getViewHeight", "viewWidth", "getViewWidth", "addOnDraw", "", "onDraw", "Lkotlin/Function1;", "Lcom/hieupt/android/standalonescrollbar/ScrollableView;", "Lkotlin/ParameterName;", "name", "caller", "addOnScrollChangedListener", "onScrollChanged", "scrollTo", "offset", "scrollToPositionWithOffset", "position", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public final class HorizontalRecyclerViewHelper implements HorizontalScrollableView {
    private final Rect tempRect;
    private final RecyclerView view;

    public HorizontalRecyclerViewHelper(RecyclerView view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.view = view;
        this.tempRect = new Rect();
    }

    @Override // com.hieupt.android.standalonescrollbar.HorizontalScrollableView, com.hieupt.android.standalonescrollbar.ScrollableView
    public int getScrollOffsetRange() {
        return HorizontalScrollableView.DefaultImpls.getScrollOffsetRange(this);
    }

    private final boolean isLayoutRtl() {
        return this.view.getLayoutDirection() == 1;
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public void addOnScrollChangedListener(final Function1<? super ScrollableView, Unit> onScrollChanged) {
        Intrinsics.checkNotNullParameter(onScrollChanged, "onScrollChanged");
        this.view.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.hieupt.android.standalonescrollbar.viewhelper.HorizontalRecyclerViewHelper.addOnScrollChangedListener.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
                onScrollChanged.invoke(HorizontalRecyclerViewHelper.this);
            }
        });
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public void addOnDraw(final Function1<? super ScrollableView, Unit> onDraw) {
        Intrinsics.checkNotNullParameter(onDraw, "onDraw");
        this.view.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.hieupt.android.standalonescrollbar.viewhelper.HorizontalRecyclerViewHelper.addOnDraw.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                Intrinsics.checkNotNullParameter(c, "c");
                Intrinsics.checkNotNullParameter(parent, "parent");
                Intrinsics.checkNotNullParameter(state, "state");
                super.onDrawOver(c, parent, state);
                onDraw.invoke(HorizontalRecyclerViewHelper.this);
            }
        });
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public int getViewWidth() {
        return this.view.getWidth();
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public int getViewHeight() {
        return this.view.getHeight();
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public int getScrollRange() {
        if (getItemCount() == 0 || getItemWidth() == 0) {
            return 0;
        }
        return this.view.getPaddingStart() + (getItemCount() * getItemWidth()) + this.view.getPaddingEnd();
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public int getScrollOffset() {
        int firstItemPosition = getFirstItemPosition();
        if (firstItemPosition == -1) {
            return 0;
        }
        int itemWidth = getItemWidth();
        int firstItemOffset = getFirstItemOffset();
        if (isLayoutRtl()) {
            return ((this.view.getPaddingStart() + (firstItemPosition * itemWidth)) + firstItemOffset) - this.view.getWidth();
        }
        return (this.view.getPaddingStart() + (firstItemPosition * itemWidth)) - firstItemOffset;
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public void scrollTo(int offset) {
        this.view.stopScroll();
        int paddingStart = offset - this.view.getPaddingStart();
        int itemWidth = getItemWidth();
        int iMax = Math.max(0, paddingStart / itemWidth);
        scrollToPositionWithOffset(iMax, (itemWidth * iMax) - paddingStart);
    }

    private final int getItemCount() {
        int itemCount;
        LinearLayoutManager horizontalLinearLayoutManager = getHorizontalLinearLayoutManager();
        if (horizontalLinearLayoutManager == null || (itemCount = horizontalLinearLayoutManager.getItemCount()) == 0) {
            return 0;
        }
        return horizontalLinearLayoutManager instanceof GridLayoutManager ? ((itemCount - 1) / ((GridLayoutManager) horizontalLinearLayoutManager).getSpanCount()) + 1 : itemCount;
    }

    private final int getItemWidth() {
        if (this.view.getChildCount() == 0) {
            return 0;
        }
        this.view.getDecoratedBoundsWithMargins(this.view.getChildAt(0), this.tempRect);
        return this.tempRect.width();
    }

    private final int getFirstItemPosition() {
        int firstItemAdapterPosition = getFirstItemAdapterPosition();
        LinearLayoutManager horizontalLinearLayoutManager = getHorizontalLinearLayoutManager();
        if (horizontalLinearLayoutManager != null) {
            return horizontalLinearLayoutManager instanceof GridLayoutManager ? firstItemAdapterPosition / ((GridLayoutManager) horizontalLinearLayoutManager).getSpanCount() : firstItemAdapterPosition;
        }
        return -1;
    }

    private final int getFirstItemAdapterPosition() {
        if (this.view.getChildCount() == 0) {
            return -1;
        }
        View childAt = this.view.getChildAt(0);
        LinearLayoutManager horizontalLinearLayoutManager = getHorizontalLinearLayoutManager();
        if (horizontalLinearLayoutManager != null) {
            return horizontalLinearLayoutManager.getPosition(childAt);
        }
        return -1;
    }

    private final int getFirstItemOffset() {
        if (this.view.getChildCount() == 0) {
            return -1;
        }
        this.view.getDecoratedBoundsWithMargins(this.view.getChildAt(0), this.tempRect);
        if (isLayoutRtl()) {
            return this.tempRect.right;
        }
        return this.tempRect.left;
    }

    private final void scrollToPositionWithOffset(int position, int offset) {
        LinearLayoutManager horizontalLinearLayoutManager = getHorizontalLinearLayoutManager();
        if (horizontalLinearLayoutManager != null) {
            if (horizontalLinearLayoutManager instanceof GridLayoutManager) {
                position *= ((GridLayoutManager) horizontalLinearLayoutManager).getSpanCount();
            }
            horizontalLinearLayoutManager.scrollToPositionWithOffset(position, offset - this.view.getPaddingStart());
        }
    }

    private final LinearLayoutManager getHorizontalLinearLayoutManager() {
        RecyclerView.LayoutManager layoutManager = this.view.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            layoutManager = null;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        if (linearLayoutManager == null || linearLayoutManager.getOrientation() != 0) {
            return null;
        }
        return linearLayoutManager;
    }
}
