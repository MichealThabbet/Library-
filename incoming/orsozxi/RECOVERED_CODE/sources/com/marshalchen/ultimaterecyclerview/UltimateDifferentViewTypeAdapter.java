package com.marshalchen.ultimaterecyclerview;

import android.view.ViewGroup;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.multiViewTypes.DataBinder;
import java.lang.Enum;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class UltimateDifferentViewTypeAdapter<E extends Enum<E>> extends UltimateViewAdapter<UltimateRecyclerviewViewHolder> {
    private Map<E, DataBinder> mBinderMap = new HashMap();

    public abstract E getEnumFromOrdinal(int i);

    public abstract E getEnumFromPosition(int i);

    protected class VIEW_TYPES extends UltimateViewAdapter.VIEW_TYPES {
        public static final int MULTI_VIEWS = 5;

        protected VIEW_TYPES() {
            super();
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return getDataBinder(i).newViewHolder(viewGroup);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int i) {
        getDataBinder(ultimateRecyclerviewViewHolder.getItemViewType()).bindViewHolder(ultimateRecyclerviewViewHolder, getBinderPosition(i));
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        Iterator<DataBinder> it2 = this.mBinderMap.values().iterator();
        int itemCount = 0;
        while (it2.hasNext()) {
            itemCount += it2.next().getItemCount();
        }
        return itemCount;
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return getEnumFromPosition(i).ordinal();
    }

    public <T extends DataBinder> T getDataBinder(int i) {
        return (T) getDataBinder((UltimateDifferentViewTypeAdapter<E>) getEnumFromOrdinal(i));
    }

    public int getPosition(DataBinder dataBinder, int i) {
        Enum enumFromBinder = getEnumFromBinder(dataBinder);
        for (int i2 = 0; i2 < getItemCount(); i2++) {
            if (enumFromBinder == getEnumFromPosition(i2) && i - 1 <= 0) {
                return i2;
            }
        }
        return getItemCount();
    }

    public int getBinderPosition(int i) {
        Enum enumFromPosition = getEnumFromPosition(i);
        int i2 = -1;
        for (int i3 = 0; i3 <= i; i3++) {
            if (enumFromPosition == getEnumFromPosition(i3)) {
                i2++;
            }
        }
        if (i2 != -1) {
            return i2;
        }
        throw new IllegalArgumentException("Invalid Argument");
    }

    public void notifyBinderItemRangeChanged(DataBinder dataBinder, int i, int i2) {
        while (i <= i2) {
            notifyItemChanged(getPosition(dataBinder, i));
            i++;
        }
    }

    public void notifyBinderItemRangeInserted(DataBinder dataBinder, int i, int i2) {
        while (i <= i2) {
            notifyItemInserted(getPosition(dataBinder, i));
            i++;
        }
    }

    public void notifyBinderItemRangeRemoved(DataBinder dataBinder, int i, int i2) {
        while (i <= i2) {
            notifyItemRemoved(getPosition(dataBinder, i));
            i++;
        }
    }

    public void notifyBinderItemChanged(DataBinder dataBinder, int i) {
        notifyItemChanged(getPosition(dataBinder, i));
    }

    public void notifyBinderItemInserted(DataBinder dataBinder, int i) {
        notifyItemInserted(getPosition(dataBinder, i));
    }

    public void notifyBinderItemMoved(DataBinder dataBinder, int i, int i2) {
        notifyItemMoved(getPosition(dataBinder, i), getPosition(dataBinder, i2));
    }

    public void notifyBinderItemRemoved(DataBinder dataBinder, int i) {
        notifyItemRemoved(getPosition(dataBinder, i));
    }

    public E getEnumFromBinder(DataBinder dataBinder) {
        for (Map.Entry<E, DataBinder> entry : this.mBinderMap.entrySet()) {
            if (entry.getValue().equals(dataBinder)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Invalid Data Binder");
    }

    public <T extends DataBinder> T getDataBinder(E e) {
        return (T) this.mBinderMap.get(e);
    }

    public Map<E, DataBinder> getBinderMap() {
        return this.mBinderMap;
    }

    public void putBinder(E e, DataBinder dataBinder) {
        this.mBinderMap.put(e, dataBinder);
    }

    public void removeBinder(E e) {
        this.mBinderMap.remove(e);
    }

    public void clearBinderMap() {
        this.mBinderMap.clear();
    }
}
