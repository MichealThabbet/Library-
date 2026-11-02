package com.marshalchen.ultimaterecyclerview.quickAdapter.extBaseAdapter;

import android.content.Context;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class EnhancedQuickAdapter<T> extends QuickAdapter<T> {
    protected abstract void convert(BaseAdapterHelper baseAdapterHelper, T t, boolean z);

    public EnhancedQuickAdapter(Context context, int i) {
        super(context, i);
    }

    public EnhancedQuickAdapter(Context context, int i, List<T> list) {
        super(context, i, list);
    }

    @Override // com.marshalchen.ultimaterecyclerview.quickAdapter.extBaseAdapter.BaseQuickAdapter
    protected final void convert(BaseAdapterHelper baseAdapterHelper, T t) {
        boolean z = baseAdapterHelper.associatedObject == null || !baseAdapterHelper.associatedObject.equals(t);
        baseAdapterHelper.associatedObject = t;
        convert(baseAdapterHelper, t, z);
    }
}
