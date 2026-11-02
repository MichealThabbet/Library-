package com.app.orsozoxi.Views.DropDown;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class DragNDropSimpleAdapter extends SimpleAdapter implements DragNDropAdapter {
    int mHandler;
    private int[] mPosition;

    @Override // com.app.orsozoxi.Views.DropDown.DragNDropListView.OnItemDragNDropListener
    public void onItemDrag(DragNDropListView dragNDropListView, View view, int i, long j) {
    }

    public int[] getmPosition() {
        return this.mPosition;
    }

    public void setmPosition(int[] iArr) {
        this.mPosition = iArr;
    }

    public DragNDropSimpleAdapter(Context context, List<? extends Map<String, ?>> list, int i, String[] strArr, int[] iArr, int i2) {
        super(context, list, i, strArr, iArr);
        this.mHandler = i2;
        setup(list.size());
    }

    private void setup(int i) {
        this.mPosition = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mPosition[i2] = i2;
        }
    }

    @Override // android.widget.SimpleAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return super.getDropDownView(this.mPosition[i], view, viewGroup);
    }

    @Override // android.widget.SimpleAdapter, android.widget.Adapter
    public Object getItem(int i) {
        return super.getItem(this.mPosition[i]);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        return super.getItemViewType(this.mPosition[i]);
    }

    @Override // android.widget.SimpleAdapter, android.widget.Adapter
    public long getItemId(int i) {
        return super.getItemId(this.mPosition[i]);
    }

    @Override // android.widget.SimpleAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return super.getView(this.mPosition[i], view, viewGroup);
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return super.isEnabled(this.mPosition[i]);
    }

    @Override // com.app.orsozoxi.Views.DropDown.DragNDropListView.OnItemDragNDropListener
    public void onItemDrop(DragNDropListView dragNDropListView, View view, int i, int i2, long j) {
        int i3 = this.mPosition[i];
        if (i < i2) {
            while (i < i2) {
                int[] iArr = this.mPosition;
                int i4 = i + 1;
                iArr[i] = iArr[i4];
                i = i4;
            }
        } else if (i2 < i) {
            while (i > i2) {
                int[] iArr2 = this.mPosition;
                iArr2[i] = iArr2[i - 1];
                i--;
            }
        }
        this.mPosition[i2] = i3;
    }

    @Override // com.app.orsozoxi.Views.DropDown.DragNDropAdapter
    public int getDragHandler() {
        return this.mHandler;
    }
}
