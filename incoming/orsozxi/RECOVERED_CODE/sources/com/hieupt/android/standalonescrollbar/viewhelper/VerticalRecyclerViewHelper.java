package com.hieupt.android.standalonescrollbar.viewhelper;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.hieupt.android.standalonescrollbar.ScrollableView;
import com.hieupt.android.standalonescrollbar.VerticalScrollableView;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VerticalRecyclerViewHelper.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J+\u0010\u001f\u001a\u00020 2!\u0010!\u001a\u001d\u0012\u0013\u0012\u00110#¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020 0\"H\u0016J+\u0010'\u001a\u00020 2!\u0010(\u001a\u001d\u0012\u0013\u0012\u00110#¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020 0\"H\u0016J\u0010\u0010)\u001a\u00020 2\u0006\u0010*\u001a\u00020\u0006H\u0016J\u0018\u0010+\u001a\u00020 2\u0006\u0010,\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0002R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\bR\u0014\u0010\u0011\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\bR\u0014\u0010\u0013\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\bR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u00188BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\bR\u0014\u0010\u001d\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\b¨\u0006-"}, d2 = {"Lcom/hieupt/android/standalonescrollbar/viewhelper/VerticalRecyclerViewHelper;", "Lcom/hieupt/android/standalonescrollbar/VerticalScrollableView;", Promotion.ACTION_VIEW, "Landroidx/recyclerview/widget/RecyclerView;", "(Landroidx/recyclerview/widget/RecyclerView;)V", "firstItemAdapterPosition", "", "getFirstItemAdapterPosition", "()I", "firstItemOffset", "getFirstItemOffset", "firstItemPosition", "getFirstItemPosition", "itemCount", "getItemCount", "itemHeight", "getItemHeight", "scrollOffset", "getScrollOffset", "scrollRange", "getScrollRange", "tempRect", "Landroid/graphics/Rect;", "verticalLinearLayoutManager", "Landroidx/recyclerview/widget/LinearLayoutManager;", "getVerticalLinearLayoutManager", "()Landroidx/recyclerview/widget/LinearLayoutManager;", "viewHeight", "getViewHeight", "viewWidth", "getViewWidth", "addOnDraw", "", "onDraw", "Lkotlin/Function1;", "Lcom/hieupt/android/standalonescrollbar/ScrollableView;", "Lkotlin/ParameterName;", "name", "caller", "addOnScrollChangedListener", "onScrollChanged", "scrollTo", "offset", "scrollToPositionWithOffset", "position", "android-standalone-scroll-bar_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes2.dex */
public final class VerticalRecyclerViewHelper implements VerticalScrollableView {
    private final Rect tempRect;
    private final RecyclerView view;

    public VerticalRecyclerViewHelper(RecyclerView view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.view = view;
        this.tempRect = new Rect();
    }

    @Override // com.hieupt.android.standalonescrollbar.VerticalScrollableView, com.hieupt.android.standalonescrollbar.ScrollableView
    public int getScrollOffsetRange() {
        return VerticalScrollableView.DefaultImpls.getScrollOffsetRange(this);
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public void addOnScrollChangedListener(final Function1<? super ScrollableView, Unit> onScrollChanged) {
        Intrinsics.checkNotNullParameter(onScrollChanged, "onScrollChanged");
        this.view.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.hieupt.android.standalonescrollbar.viewhelper.VerticalRecyclerViewHelper.addOnScrollChangedListener.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
                onScrollChanged.invoke(VerticalRecyclerViewHelper.this);
            }
        });
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public void addOnDraw(final Function1<? super ScrollableView, Unit> onDraw) {
        Intrinsics.checkNotNullParameter(onDraw, "onDraw");
        this.view.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.hieupt.android.standalonescrollbar.viewhelper.VerticalRecyclerViewHelper.addOnDraw.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                Intrinsics.checkNotNullParameter(c, "c");
                Intrinsics.checkNotNullParameter(parent, "parent");
                Intrinsics.checkNotNullParameter(state, "state");
                super.onDrawOver(c, parent, state);
                onDraw.invoke(VerticalRecyclerViewHelper.this);
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
        if (getItemCount() == 0 || getItemHeight() == 0) {
            return 0;
        }
        return this.view.getPaddingTop() + (getItemCount() * getItemHeight()) + this.view.getPaddingBottom();
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public int getScrollOffset() {
        int firstItemPosition = getFirstItemPosition();
        if (firstItemPosition == -1) {
            return 0;
        }
        int itemHeight = getItemHeight();
        return (this.view.getPaddingTop() + (firstItemPosition * itemHeight)) - getFirstItemOffset();
    }

    @Override // com.hieupt.android.standalonescrollbar.ScrollableView
    public void scrollTo(int offset) {
        this.view.stopScroll();
        int paddingTop = offset - this.view.getPaddingTop();
        int itemHeight = getItemHeight();
        int iMax = Math.max(0, paddingTop / itemHeight);
        scrollToPositionWithOffset(iMax, (itemHeight * iMax) - paddingTop);
    }

    private final int getItemCount() {
        int itemCount;
        LinearLayoutManager verticalLinearLayoutManager = getVerticalLinearLayoutManager();
        if (verticalLinearLayoutManager == null || (itemCount = verticalLinearLayoutManager.getItemCount()) == 0) {
            return 0;
        }
        return verticalLinearLayoutManager instanceof GridLayoutManager ? ((itemCount - 1) / ((GridLayoutManager) verticalLinearLayoutManager).getSpanCount()) + 1 : itemCount;
    }

    private final int getItemHeight() {
        if (this.view.getChildCount() == 0) {
            return 0;
        }
        this.view.getDecoratedBoundsWithMargins(this.view.getChildAt(0), this.tempRect);
        return this.tempRect.height();
    }

    private final int getFirstItemPosition() {
        int firstItemAdapterPosition = getFirstItemAdapterPosition();
        LinearLayoutManager verticalLinearLayoutManager = getVerticalLinearLayoutManager();
        if (verticalLinearLayoutManager != null) {
            return verticalLinearLayoutManager instanceof GridLayoutManager ? firstItemAdapterPosition / ((GridLayoutManager) verticalLinearLayoutManager).getSpanCount() : firstItemAdapterPosition;
        }
        return -1;
    }

    private final int getFirstItemAdapterPosition() {
        if (this.view.getChildCount() == 0) {
            return -1;
        }
        View childAt = this.view.getChildAt(0);
        LinearLayoutManager verticalLinearLayoutManager = getVerticalLinearLayoutManager();
        if (verticalLinearLayoutManager != null) {
            return verticalLinearLayoutManager.getPosition(childAt);
        }
        return -1;
    }

    private final int getFirstItemOffset() {
        if (this.view.getChildCount() == 0) {
            return -1;
        }
        this.view.getDecoratedBoundsWithMargins(this.view.getChildAt(0), this.tempRect);
        return this.tempRect.top;
    }

    private final void scrollToPositionWithOffset(int position, int offset) {
        LinearLayoutManager verticalLinearLayoutManager = getVerticalLinearLayoutManager();
        if (verticalLinearLayoutManager != null) {
            if (verticalLinearLayoutManager instanceof GridLayoutManager) {
                position *= ((GridLayoutManager) verticalLinearLayoutManager).getSpanCount();
            }
            verticalLinearLayoutManager.scrollToPositionWithOffset(position, offset - this.view.getPaddingTop());
        }
    }

    private final LinearLayoutManager getVerticalLinearLayoutManager() {
        RecyclerView.LayoutManager layoutManager = this.view.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            layoutManager = null;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        if (linearLayoutManager == null || linearLayoutManager.getOrientation() != 1) {
            return null;
        }
        return linearLayoutManager;
    }
}
