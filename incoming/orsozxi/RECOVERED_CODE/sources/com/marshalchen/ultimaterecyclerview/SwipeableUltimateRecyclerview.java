package com.marshalchen.ultimaterecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListView;
import com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewListener;
import com.marshalchen.ultimaterecyclerview.ui.VerticalSwipeRefreshLayout;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton;

/* loaded from: classes2.dex */
public class SwipeableUltimateRecyclerview extends UltimateRecyclerView {
    public SwipeableUltimateRecyclerview(Context context) {
        super(context);
    }

    public SwipeableUltimateRecyclerview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ((SwipeListView) this.mRecyclerView).init(attributeSet);
    }

    public SwipeableUltimateRecyclerview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ((SwipeListView) this.mRecyclerView).init(attributeSet);
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
    protected void initViews() {
        View viewInflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.swipeable_ultimate_recycler_view_layout, this);
        this.mRecyclerView = (SwipeListView) viewInflate.findViewById(R.id.ultimate_list);
        this.mSwipeRefreshLayout = (VerticalSwipeRefreshLayout) viewInflate.findViewById(R.id.swipe_refresh_layout);
        setScrollbars();
        this.mSwipeRefreshLayout.setEnabled(false);
        if (this.mRecyclerView != null) {
            this.mRecyclerView.setClipToPadding(this.mClipToPadding);
            if (this.mPadding != -1.1f) {
                this.mRecyclerView.setPadding(this.mPadding, this.mPadding, this.mPadding, this.mPadding);
            } else {
                this.mRecyclerView.setPadding(this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom);
            }
        }
        this.defaultFloatingActionButton = (FloatingActionButton) viewInflate.findViewById(R.id.defaultFloatingActionButton);
        setDefaultScrollListener();
        this.mEmpty = (ViewStub) viewInflate.findViewById(R.id.emptyview);
        this.mFloatingButtonViewStub = (ViewStub) viewInflate.findViewById(R.id.floatingActionViewStub);
        this.mEmpty.setLayoutResource(this.mEmptyId);
        this.mFloatingButtonViewStub.setLayoutResource(this.mFloatingButtonId);
        if (this.mEmptyId != 0) {
            this.mEmptyView = this.mEmpty.inflate();
        }
        this.mEmpty.setVisibility(8);
        if (this.mFloatingButtonId != 0) {
            this.mFloatingButtonView = this.mFloatingButtonViewStub.inflate();
            this.mFloatingButtonView.setVisibility(0);
        }
    }

    public void setSwipeListViewListener(SwipeListViewListener swipeListViewListener) {
        ((SwipeListView) this.mRecyclerView).swipeListViewListener = swipeListViewListener;
    }
}
