package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.caching;

import android.view.View;
import android.view.ViewGroup;
import androidx.collection.LongSparseArray;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.util.OrientationProvider;

/* loaded from: classes2.dex */
public class HeaderViewCache implements HeaderProvider {
    private final StickyRecyclerHeadersAdapter mAdapter;
    private final LongSparseArray<View> mHeaderViews = new LongSparseArray<>();
    private final OrientationProvider mOrientationProvider;

    public HeaderViewCache(StickyRecyclerHeadersAdapter stickyRecyclerHeadersAdapter, OrientationProvider orientationProvider) {
        this.mAdapter = stickyRecyclerHeadersAdapter;
        this.mOrientationProvider = orientationProvider;
    }

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.caching.HeaderProvider
    public View getHeader(RecyclerView recyclerView, int i) {
        int iMakeMeasureSpec;
        int iMakeMeasureSpec2;
        long headerId = this.mAdapter.getHeaderId(i);
        View view = this.mHeaderViews.get(headerId);
        if (view == null) {
            RecyclerView.ViewHolder viewHolderOnCreateHeaderViewHolder = this.mAdapter.onCreateHeaderViewHolder(recyclerView);
            this.mAdapter.onBindHeaderViewHolder(viewHolderOnCreateHeaderViewHolder, i);
            view = viewHolderOnCreateHeaderViewHolder.itemView;
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            if (this.mOrientationProvider.getOrientation(recyclerView) == 1) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), BasicMeasure.EXACTLY);
                iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 0);
            } else {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 0);
                iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), BasicMeasure.EXACTLY);
            }
            view.measure(ViewGroup.getChildMeasureSpec(iMakeMeasureSpec, recyclerView.getPaddingLeft() + recyclerView.getPaddingRight(), view.getLayoutParams().width), ViewGroup.getChildMeasureSpec(iMakeMeasureSpec2, recyclerView.getPaddingTop() + recyclerView.getPaddingBottom(), view.getLayoutParams().height));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            this.mHeaderViews.put(headerId, view);
        }
        return view;
    }

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.caching.HeaderProvider
    public void invalidate() {
        this.mHeaderViews.clear();
    }
}
