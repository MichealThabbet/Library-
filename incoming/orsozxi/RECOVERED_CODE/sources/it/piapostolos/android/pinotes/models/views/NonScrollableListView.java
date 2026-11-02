package it.piapostolos.android.pinotes.models.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/* loaded from: classes3.dex */
public class NonScrollableListView extends ListView {
    public NonScrollableListView(Context context) {
        super(context);
    }

    public NonScrollableListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NonScrollableListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void justifyListViewHeightBasedOnChildren() {
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return;
        }
        int measuredHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, this);
            view.measure(0, 0);
            measuredHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = measuredHeight + (getDividerHeight() * (adapter.getCount() - 1));
        setLayoutParams(layoutParams);
        requestLayout();
    }
}
