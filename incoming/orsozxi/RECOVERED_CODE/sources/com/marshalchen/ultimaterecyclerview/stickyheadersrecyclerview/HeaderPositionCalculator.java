package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.caching.HeaderProvider;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.calculation.DimensionCalculator;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.util.OrientationProvider;

/* loaded from: classes2.dex */
public class HeaderPositionCalculator {
    private final StickyRecyclerHeadersAdapter mAdapter;
    private final DimensionCalculator mDimensionCalculator;
    private final HeaderProvider mHeaderProvider;
    private final OrientationProvider mOrientationProvider;

    public HeaderPositionCalculator(StickyRecyclerHeadersAdapter stickyRecyclerHeadersAdapter, HeaderProvider headerProvider, OrientationProvider orientationProvider, DimensionCalculator dimensionCalculator) {
        this.mAdapter = stickyRecyclerHeadersAdapter;
        this.mHeaderProvider = headerProvider;
        this.mOrientationProvider = orientationProvider;
        this.mDimensionCalculator = dimensionCalculator;
    }

    public boolean hasNewHeader(int i) {
        if (indexOutOfBounds(i)) {
            return false;
        }
        long headerId = this.mAdapter.getHeaderId(i);
        if (headerId < 0) {
            return false;
        }
        return i == 0 || headerId != this.mAdapter.getHeaderId(i - 1);
    }

    private boolean indexOutOfBounds(int i) {
        return i < 0 || i >= this.mAdapter.getItemCount();
    }

    public Rect getHeaderBounds(RecyclerView recyclerView, View view, View view2, boolean z) {
        Rect defaultHeaderOffset = getDefaultHeaderOffset(recyclerView, view, view2, this.mOrientationProvider.getOrientation(recyclerView));
        if (z && isStickyHeaderBeingPushedOffscreen(recyclerView, view)) {
            View firstViewUnobscuredByHeader = getFirstViewUnobscuredByHeader(recyclerView, view);
            translateHeaderWithNextHeader(recyclerView, this.mOrientationProvider.getOrientation(recyclerView), defaultHeaderOffset, view, firstViewUnobscuredByHeader, this.mHeaderProvider.getHeader(recyclerView, recyclerView.getChildAdapterPosition(firstViewUnobscuredByHeader)));
        }
        return defaultHeaderOffset;
    }

    private Rect getDefaultHeaderOffset(RecyclerView recyclerView, View view, View view2, int i) {
        int iMax;
        int iMax2;
        Rect margins = this.mDimensionCalculator.getMargins(view);
        if (i == 1) {
            iMax = view2.getLeft() + margins.left;
            iMax2 = Math.max((view2.getTop() - view.getHeight()) - margins.bottom, getListTop(recyclerView) + margins.top);
        } else {
            int top = view2.getTop() + margins.top;
            iMax = Math.max((view2.getLeft() - view.getWidth()) - margins.right, getListLeft(recyclerView) + margins.left);
            iMax2 = top;
        }
        return new Rect(iMax, iMax2, view.getWidth() + iMax, view.getHeight() + iMax2);
    }

    private boolean isStickyHeaderBeingPushedOffscreen(RecyclerView recyclerView, View view) {
        View firstViewUnobscuredByHeader = getFirstViewUnobscuredByHeader(recyclerView, view);
        int childAdapterPosition = recyclerView.getChildAdapterPosition(firstViewUnobscuredByHeader);
        if (childAdapterPosition != -1 && childAdapterPosition > 0 && hasNewHeader(childAdapterPosition)) {
            View header = this.mHeaderProvider.getHeader(recyclerView, childAdapterPosition);
            Rect margins = this.mDimensionCalculator.getMargins(header);
            Rect margins2 = this.mDimensionCalculator.getMargins(view);
            if (this.mOrientationProvider.getOrientation(recyclerView) == 1) {
                if (((firstViewUnobscuredByHeader.getTop() - margins.bottom) - header.getHeight()) - margins.top < recyclerView.getPaddingTop() + view.getBottom() + margins2.top + margins2.bottom) {
                    return true;
                }
            } else if (((firstViewUnobscuredByHeader.getLeft() - margins.right) - header.getWidth()) - margins.left < recyclerView.getPaddingLeft() + view.getRight() + margins2.left + margins2.right) {
                return true;
            }
        }
        return false;
    }

    private void translateHeaderWithNextHeader(RecyclerView recyclerView, int i, Rect rect, View view, View view2, View view3) {
        Rect margins = this.mDimensionCalculator.getMargins(view3);
        Rect margins2 = this.mDimensionCalculator.getMargins(view);
        if (i == 1) {
            int listTop = getListTop(recyclerView) + margins2.top + margins2.bottom;
            int top = ((((view2.getTop() - view3.getHeight()) - margins.bottom) - margins.top) - view.getHeight()) - listTop;
            if (top < listTop) {
                rect.top += top;
                return;
            }
            return;
        }
        int listLeft = getListLeft(recyclerView) + margins2.left + margins2.right;
        int left = ((((view2.getLeft() - view3.getWidth()) - margins.right) - margins.left) - view.getWidth()) - listLeft;
        if (left < listLeft) {
            rect.left += left;
        }
    }

    private View getFirstViewUnobscuredByHeader(RecyclerView recyclerView, View view) {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View childAt = recyclerView.getChildAt(i);
            if (!itemIsObscuredByHeader(recyclerView, childAt, view, this.mOrientationProvider.getOrientation(recyclerView))) {
                return childAt;
            }
        }
        return null;
    }

    private boolean itemIsObscuredByHeader(RecyclerView recyclerView, View view, View view2, int i) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        Rect margins = this.mDimensionCalculator.getMargins(view2);
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition == -1 || this.mHeaderProvider.getHeader(recyclerView, childAdapterPosition) != view2) {
            return false;
        }
        if (i == 1) {
            if (view.getTop() - layoutParams.topMargin > view2.getBottom() + margins.bottom + margins.top) {
                return false;
            }
        } else if (view.getLeft() - layoutParams.leftMargin > view2.getRight() + margins.right + margins.left) {
            return false;
        }
        return true;
    }

    private int getListTop(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager().getClipToPadding()) {
            return recyclerView.getPaddingTop();
        }
        return 0;
    }

    private int getListLeft(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager().getClipToPadding()) {
            return recyclerView.getPaddingLeft();
        }
        return 0;
    }
}
