package com.app.piapostolos.Views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class WrappingLinearLayoutManager extends LinearLayoutManager {
    private int[] mMeasuredDimension;

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return false;
    }

    public WrappingLinearLayoutManager(Context context) {
        super(context);
        this.mMeasuredDimension = new int[2];
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < getItemCount(); i5++) {
            if (getOrientation() == 0) {
                measureScrapChild(recycler, i5, View.MeasureSpec.makeMeasureSpec(0, 0), i2, this.mMeasuredDimension);
                int[] iArr = this.mMeasuredDimension;
                i4 += iArr[0];
                if (i5 == 0) {
                    i3 = iArr[1];
                }
            } else {
                measureScrapChild(recycler, i5, i, View.MeasureSpec.makeMeasureSpec(0, 0), this.mMeasuredDimension);
                int[] iArr2 = this.mMeasuredDimension;
                i3 += iArr2[1];
                if (i5 == 0) {
                    i4 = iArr2[0];
                }
            }
        }
        if (mode != 1073741824) {
            size = i4;
        }
        if (mode2 != 1073741824) {
            size2 = i3;
        }
        setMeasuredDimension(size, size2);
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int i, int i2, int i3, int[] iArr) {
        View viewForPosition = recycler.getViewForPosition(i);
        if (viewForPosition.getVisibility() == 8) {
            iArr[0] = 0;
            iArr[1] = 0;
            return;
        }
        super.measureChildWithMargins(viewForPosition, 0, 0);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewForPosition.getLayoutParams();
        viewForPosition.measure(ViewGroup.getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight() + getDecoratedLeft(viewForPosition) + getDecoratedRight(viewForPosition), layoutParams.width), ViewGroup.getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + getDecoratedTop(viewForPosition) + getDecoratedBottom(viewForPosition), layoutParams.height));
        iArr[0] = getDecoratedMeasuredWidth(viewForPosition) + layoutParams.leftMargin + layoutParams.rightMargin;
        iArr[1] = getDecoratedMeasuredHeight(viewForPosition) + layoutParams.bottomMargin + layoutParams.topMargin;
        recycler.recycleView(viewForPosition);
    }
}
