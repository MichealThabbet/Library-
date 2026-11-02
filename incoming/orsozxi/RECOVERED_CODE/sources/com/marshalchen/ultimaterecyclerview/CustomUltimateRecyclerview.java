package com.marshalchen.ultimaterecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton;
import in.srain.cube.views.ptr.PtrFrameLayout;

/* loaded from: classes2.dex */
public class CustomUltimateRecyclerview extends UltimateRecyclerView {
    public PtrFrameLayout mPtrFrameLayout;

    public CustomUltimateRecyclerview(Context context) {
        super(context);
    }

    public CustomUltimateRecyclerview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomUltimateRecyclerview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
    protected void initViews() {
        View viewInflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.custom_recycler_view_layout, this);
        this.mRecyclerView = (RecyclerView) viewInflate.findViewById(R.id.ultimate_list);
        this.mSwipeRefreshLayout = null;
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

    public void setCustomSwipeToRefresh() {
        PtrFrameLayout ptrFrameLayout = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        this.mPtrFrameLayout = ptrFrameLayout;
        ptrFrameLayout.setResistance(1.7f);
        this.mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        this.mPtrFrameLayout.setDurationToClose(200);
        this.mPtrFrameLayout.setDurationToCloseHeader(1000);
        this.mPtrFrameLayout.setPullToRefresh(false);
        this.mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
    }
}
