package com.app.piapostolos.Views.DropDown;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

/* loaded from: classes.dex */
public class DragNDropCursorAdapter extends SimpleCursorAdapter implements DragNDropAdapter {
    int mHandler;
    int[] mPosition;

    @Override // com.app.piapostolos.Views.DropDown.DragNDropListView.OnItemDragNDropListener
    public void onItemDrag(DragNDropListView dragNDropListView, View view, int i, long j) {
    }

    public DragNDropCursorAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr, int i2) {
        super(context, i, cursor, strArr, iArr, 0);
        this.mHandler = i2;
        setup();
    }

    @Override // androidx.cursoradapter.widget.SimpleCursorAdapter, androidx.cursoradapter.widget.CursorAdapter
    public Cursor swapCursor(Cursor cursor) {
        Cursor cursorSwapCursor = super.swapCursor(cursor);
        this.mPosition = null;
        setup();
        return cursorSwapCursor;
    }

    private void setup() {
        Cursor cursor = getCursor();
        if (cursor == null || !cursor.moveToFirst()) {
            return;
        }
        this.mPosition = new int[cursor.getCount()];
        int i = 0;
        while (true) {
            int[] iArr = this.mPosition;
            if (i >= iArr.length) {
                return;
            }
            iArr[i] = i;
            i++;
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return super.getDropDownView(this.mPosition[i], view, viewGroup);
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.Adapter
    public Object getItem(int i) {
        return super.getItem(this.mPosition[i]);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        return super.getItemViewType(this.mPosition[i]);
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.Adapter
    public long getItemId(int i) {
        return super.getItemId(this.mPosition[i]);
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return super.getView(this.mPosition[i], view, viewGroup);
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return super.isEnabled(this.mPosition[i]);
    }

    @Override // com.app.piapostolos.Views.DropDown.DragNDropListView.OnItemDragNDropListener
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

    @Override // com.app.piapostolos.Views.DropDown.DragNDropAdapter
    public int getDragHandler() {
        return this.mHandler;
    }
}
