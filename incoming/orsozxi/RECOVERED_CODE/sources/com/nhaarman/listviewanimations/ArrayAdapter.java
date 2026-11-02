package com.nhaarman.listviewanimations;

import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.util.Insertable;
import com.nhaarman.listviewanimations.util.Swappable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class ArrayAdapter<T> extends BaseAdapter implements Swappable, Insertable<T> {
    private BaseAdapter mDataSetChangedSlavedAdapter;
    private final List<T> mItems;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    protected ArrayAdapter() {
        this(null);
    }

    protected ArrayAdapter(List<T> list) {
        if (list != null) {
            this.mItems = list;
        } else {
            this.mItems = new ArrayList();
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mItems.size();
    }

    @Override // android.widget.Adapter
    public T getItem(int i) {
        return this.mItems.get(i);
    }

    public List<T> getItems() {
        return this.mItems;
    }

    public boolean add(T t) {
        boolean zAdd = this.mItems.add(t);
        notifyDataSetChanged();
        return zAdd;
    }

    @Override // com.nhaarman.listviewanimations.util.Insertable
    public void add(int i, T t) {
        this.mItems.add(i, t);
        notifyDataSetChanged();
    }

    public boolean addAll(Collection<? extends T> collection) {
        boolean zAddAll = this.mItems.addAll(collection);
        notifyDataSetChanged();
        return zAddAll;
    }

    public boolean contains(T t) {
        return this.mItems.contains(t);
    }

    public void clear() {
        this.mItems.clear();
        notifyDataSetChanged();
    }

    public boolean remove(Object obj) {
        boolean zRemove = this.mItems.remove(obj);
        notifyDataSetChanged();
        return zRemove;
    }

    public T remove(int i) {
        T tRemove = this.mItems.remove(i);
        notifyDataSetChanged();
        return tRemove;
    }

    @Override // com.nhaarman.listviewanimations.util.Swappable
    public void swapItems(int i, int i2) {
        T t = this.mItems.set(i, getItem(i2));
        notifyDataSetChanged();
        this.mItems.set(i2, t);
    }

    public void propagateNotifyDataSetChanged(BaseAdapter baseAdapter) {
        this.mDataSetChangedSlavedAdapter = baseAdapter;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        BaseAdapter baseAdapter = this.mDataSetChangedSlavedAdapter;
        if (baseAdapter != null) {
            baseAdapter.notifyDataSetChanged();
        }
    }
}
