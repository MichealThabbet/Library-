package com.nhaarman.listviewanimations.util;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/* loaded from: classes2.dex */
public class AdapterViewUtil {
    private AdapterViewUtil() {
    }

    public static int getPositionForView(ListViewWrapper listViewWrapper, View view) {
        return listViewWrapper.getPositionForView(view) - listViewWrapper.getHeaderViewsCount();
    }

    public static int getPositionForView(AbsListView absListView, View view) {
        int positionForView = absListView.getPositionForView(view);
        return absListView instanceof ListView ? positionForView - ((ListView) absListView).getHeaderViewsCount() : positionForView;
    }

    public static View getViewForPosition(ListViewWrapper listViewWrapper, int i) {
        int childCount = listViewWrapper.getChildCount();
        View view = null;
        for (int i2 = 0; i2 < childCount && view == null; i2++) {
            View childAt = listViewWrapper.getChildAt(i2);
            if (childAt != null && getPositionForView(listViewWrapper, childAt) == i) {
                view = childAt;
            }
        }
        return view;
    }

    public static View getViewForPosition(AbsListView absListView, int i) {
        int childCount = absListView.getChildCount();
        View view = null;
        for (int i2 = 0; i2 < childCount && view == null; i2++) {
            View childAt = absListView.getChildAt(i2);
            if (childAt != null && getPositionForView(absListView, childAt) == i) {
                view = childAt;
            }
        }
        return view;
    }
}
