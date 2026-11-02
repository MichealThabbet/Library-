package com.marshalchen.ultimaterecyclerview.quickAdapter.extBaseAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.marshalchen.ultimaterecyclerview.quickAdapter.extBaseAdapter.BaseAdapterHelper;
import com.marshalchen.ultimaterecyclerview.quickAdapter.extUtimateRVA.MultiItemTypeSupport;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class BaseQuickAdapter<T, H extends BaseAdapterHelper> extends BaseAdapter {
    protected static final String TAG = BaseQuickAdapter.class.getSimpleName();
    protected final Context context;
    protected final List<T> data;
    protected boolean displayIndeterminateProgress;
    protected int layoutResId;
    protected MultiItemTypeSupport<T> mMultiItemSupport;

    protected abstract void convert(H h, T t);

    protected abstract H getAdapterHelper(int i, View view, ViewGroup viewGroup);

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public BaseQuickAdapter(Context context, int i) {
        this(context, i, (List) null);
    }

    public BaseQuickAdapter(Context context, int i, List<T> list) {
        this.displayIndeterminateProgress = false;
        this.data = list == null ? new ArrayList() : new ArrayList(list);
        this.context = context;
        this.layoutResId = i;
    }

    public BaseQuickAdapter(Context context, ArrayList<T> arrayList, MultiItemTypeSupport<T> multiItemTypeSupport) {
        this.displayIndeterminateProgress = false;
        this.mMultiItemSupport = multiItemTypeSupport;
        this.data = arrayList == null ? new ArrayList() : new ArrayList(arrayList);
        this.context = context;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.data.size() + (this.displayIndeterminateProgress ? 1 : 0);
    }

    @Override // android.widget.Adapter
    public T getItem(int i) {
        if (i >= this.data.size()) {
            return null;
        }
        return this.data.get(i);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        MultiItemTypeSupport<T> multiItemTypeSupport = this.mMultiItemSupport;
        if (multiItemTypeSupport != null) {
            return multiItemTypeSupport.getViewTypeCount() + 1;
        }
        return 2;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        if (this.displayIndeterminateProgress) {
            if (this.mMultiItemSupport != null) {
                if (i >= this.data.size()) {
                    return 0;
                }
                return this.mMultiItemSupport.getItemViewType(i, this.data.get(i));
            }
        } else {
            MultiItemTypeSupport<T> multiItemTypeSupport = this.mMultiItemSupport;
            if (multiItemTypeSupport != null) {
                return multiItemTypeSupport.getItemViewType(i, this.data.get(i));
            }
        }
        return i >= this.data.size() ? 0 : 1;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (getItemViewType(i) == 0) {
            return createIndeterminateProgressView(view, viewGroup);
        }
        BaseAdapterHelper adapterHelper = getAdapterHelper(i, view, viewGroup);
        T item = getItem(i);
        adapterHelper.setAssociatedObject(item);
        convert(adapterHelper, item);
        return adapterHelper.getView();
    }

    private View createIndeterminateProgressView(View view, ViewGroup viewGroup) {
        if (view != null) {
            return view;
        }
        FrameLayout frameLayout = new FrameLayout(this.context);
        frameLayout.setForegroundGravity(17);
        frameLayout.addView(new ProgressBar(this.context));
        return frameLayout;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return i < this.data.size();
    }

    public void add(T t) {
        this.data.add(t);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        this.data.addAll(list);
        notifyDataSetChanged();
    }

    public void set(T t, T t2) {
        set(this.data.indexOf(t), (int) t2);
    }

    public void set(int i, T t) {
        this.data.set(i, t);
        notifyDataSetChanged();
    }

    public void remove(T t) {
        this.data.remove(t);
        notifyDataSetChanged();
    }

    public void remove(int i) {
        this.data.remove(i);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> list) {
        this.data.clear();
        this.data.addAll(list);
        notifyDataSetChanged();
    }

    public boolean contains(T t) {
        return this.data.contains(t);
    }

    public void clear() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public void showIndeterminateProgress(boolean z) {
        if (z == this.displayIndeterminateProgress) {
            return;
        }
        this.displayIndeterminateProgress = z;
        notifyDataSetChanged();
    }
}
