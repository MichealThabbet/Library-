package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.rendering;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.calculation.DimensionCalculator;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.util.OrientationProvider;

/* loaded from: classes2.dex */
public class HeaderRenderer {
    private final DimensionCalculator mDimensionCalculator;
    private final OrientationProvider mOrientationProvider;

    public HeaderRenderer(OrientationProvider orientationProvider) {
        this(orientationProvider, new DimensionCalculator());
    }

    private HeaderRenderer(OrientationProvider orientationProvider, DimensionCalculator dimensionCalculator) {
        this.mOrientationProvider = orientationProvider;
        this.mDimensionCalculator = dimensionCalculator;
    }

    public void drawHeader(RecyclerView recyclerView, Canvas canvas, View view, Rect rect) {
        canvas.save();
        if (recyclerView.getLayoutManager().getClipToPadding()) {
            canvas.clipRect(getClipRectForHeader(recyclerView, view));
        }
        canvas.translate(rect.left, rect.top);
        view.draw(canvas);
        canvas.restore();
    }

    private Rect getClipRectForHeader(RecyclerView recyclerView, View view) {
        Rect margins = this.mDimensionCalculator.getMargins(view);
        if (this.mOrientationProvider.getOrientation(recyclerView) == 1) {
            return new Rect(recyclerView.getPaddingLeft(), recyclerView.getPaddingTop(), (recyclerView.getWidth() - recyclerView.getPaddingRight()) - margins.right, recyclerView.getHeight() - recyclerView.getPaddingBottom());
        }
        return new Rect(recyclerView.getPaddingLeft(), recyclerView.getPaddingTop(), recyclerView.getWidth() - recyclerView.getPaddingRight(), (recyclerView.getHeight() - recyclerView.getPaddingBottom()) - margins.bottom);
    }
}
