package com.app.piapostolos.Views.DropDown;

import android.widget.ListAdapter;
import com.app.piapostolos.Views.DropDown.DragNDropListView;

/* loaded from: classes.dex */
public interface DragNDropAdapter extends ListAdapter, DragNDropListView.OnItemDragNDropListener {
    int getDragHandler();
}
