package com.app.orsozoxi.Views.DropDown;

import android.widget.ListAdapter;
import com.app.orsozoxi.Views.DropDown.DragNDropListView;

/* loaded from: classes.dex */
public interface DragNDropAdapter extends ListAdapter, DragNDropListView.OnItemDragNDropListener {
    int getDragHandler();
}
