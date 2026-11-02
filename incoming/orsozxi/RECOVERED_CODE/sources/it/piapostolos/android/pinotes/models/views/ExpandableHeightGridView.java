package it.piapostolos.android.pinotes.models.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import androidx.core.view.ViewCompat;

/* loaded from: classes3.dex */
public class ExpandableHeightGridView extends GridView {
    private int itemHeight;

    public ExpandableHeightGridView(Context context) {
        super(context);
    }

    public ExpandableHeightGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ExpandableHeightGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(ViewCompat.MEASURED_SIZE_MASK, Integer.MIN_VALUE));
        getLayoutParams().height = getMeasuredHeight();
    }

    public void autoresize() {
        setNumColumns(getAdapter().getCount() != 1 ? 2 : 1);
    }

    public int getItemHeight() {
        return this.itemHeight;
    }
}
