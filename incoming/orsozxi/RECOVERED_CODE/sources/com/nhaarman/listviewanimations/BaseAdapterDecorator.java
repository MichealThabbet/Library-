package com.nhaarman.listviewanimations;

import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.SpinnerAdapter;
import com.nhaarman.listviewanimations.util.AbsListViewWrapper;
import com.nhaarman.listviewanimations.util.Insertable;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nhaarman.listviewanimations.util.ListViewWrapperSetter;
import com.nhaarman.listviewanimations.util.Swappable;

/* loaded from: classes2.dex */
public abstract class BaseAdapterDecorator extends BaseAdapter implements SectionIndexer, Swappable, Insertable, ListViewWrapperSetter {
    private final BaseAdapter mDecoratedBaseAdapter;
    private ListViewWrapper mListViewWrapper;

    protected BaseAdapterDecorator(BaseAdapter baseAdapter) {
        this.mDecoratedBaseAdapter = baseAdapter;
    }

    public BaseAdapter getDecoratedBaseAdapter() {
        return this.mDecoratedBaseAdapter;
    }

    protected BaseAdapter getRootAdapter() {
        BaseAdapter decoratedBaseAdapter = this.mDecoratedBaseAdapter;
        while (decoratedBaseAdapter instanceof BaseAdapterDecorator) {
            decoratedBaseAdapter = ((BaseAdapterDecorator) decoratedBaseAdapter).getDecoratedBaseAdapter();
        }
        return decoratedBaseAdapter;
    }

    public void setAbsListView(AbsListView absListView) {
        setListViewWrapper(new AbsListViewWrapper(absListView));
    }

    public ListViewWrapper getListViewWrapper() {
        return this.mListViewWrapper;
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapperSetter
    public void setListViewWrapper(ListViewWrapper listViewWrapper) {
        this.mListViewWrapper = listViewWrapper;
        SpinnerAdapter spinnerAdapter = this.mDecoratedBaseAdapter;
        if (spinnerAdapter instanceof ListViewWrapperSetter) {
            ((ListViewWrapperSetter) spinnerAdapter).setListViewWrapper(listViewWrapper);
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mDecoratedBaseAdapter.getCount();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.mDecoratedBaseAdapter.getItem(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return this.mDecoratedBaseAdapter.getItemId(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return this.mDecoratedBaseAdapter.getView(i, view, viewGroup);
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return this.mDecoratedBaseAdapter.areAllItemsEnabled();
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return this.mDecoratedBaseAdapter.getDropDownView(i, view, viewGroup);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        return this.mDecoratedBaseAdapter.getItemViewType(i);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return this.mDecoratedBaseAdapter.getViewTypeCount();
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return this.mDecoratedBaseAdapter.hasStableIds();
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean isEmpty() {
        return this.mDecoratedBaseAdapter.isEmpty();
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return this.mDecoratedBaseAdapter.isEnabled(i);
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        BaseAdapter baseAdapter = this.mDecoratedBaseAdapter;
        if (baseAdapter instanceof ArrayAdapter) {
            return;
        }
        baseAdapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged(boolean z) {
        if (z || !(this.mDecoratedBaseAdapter instanceof ArrayAdapter)) {
            this.mDecoratedBaseAdapter.notifyDataSetChanged();
        }
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetInvalidated() {
        this.mDecoratedBaseAdapter.notifyDataSetInvalidated();
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDecoratedBaseAdapter.registerDataSetObserver(dataSetObserver);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDecoratedBaseAdapter.unregisterDataSetObserver(dataSetObserver);
    }

    @Override // android.widget.SectionIndexer
    public int getPositionForSection(int i) {
        SpinnerAdapter spinnerAdapter = this.mDecoratedBaseAdapter;
        if (spinnerAdapter instanceof SectionIndexer) {
            return ((SectionIndexer) spinnerAdapter).getPositionForSection(i);
        }
        return 0;
    }

    @Override // android.widget.SectionIndexer
    public int getSectionForPosition(int i) {
        SpinnerAdapter spinnerAdapter = this.mDecoratedBaseAdapter;
        if (spinnerAdapter instanceof SectionIndexer) {
            return ((SectionIndexer) spinnerAdapter).getSectionForPosition(i);
        }
        return 0;
    }

    @Override // android.widget.SectionIndexer
    public Object[] getSections() {
        Object[] objArr = new Object[0];
        SpinnerAdapter spinnerAdapter = this.mDecoratedBaseAdapter;
        return spinnerAdapter instanceof SectionIndexer ? ((SectionIndexer) spinnerAdapter).getSections() : objArr;
    }

    @Override // com.nhaarman.listviewanimations.util.Swappable
    public void swapItems(int i, int i2) {
        SpinnerAdapter spinnerAdapter = this.mDecoratedBaseAdapter;
        if (spinnerAdapter instanceof Swappable) {
            ((Swappable) spinnerAdapter).swapItems(i, i2);
        } else {
            Log.w("ListViewAnimations", "Warning: swapItems called on an adapter that does not implement Swappable!");
        }
    }

    @Override // com.nhaarman.listviewanimations.util.Insertable
    public void add(int i, Object obj) {
        SpinnerAdapter spinnerAdapter = this.mDecoratedBaseAdapter;
        if (spinnerAdapter instanceof Insertable) {
            ((Insertable) spinnerAdapter).add(i, obj);
        } else {
            Log.w("ListViewAnimations", "Warning: add called on an adapter that does not implement Insertable!");
        }
    }
}
