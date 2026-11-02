package com.marshalchen.ultimaterecyclerview.quickAdapter.extBaseAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.marshalchen.ultimaterecyclerview.quickAdapter.extUtimateRVA.MultiItemTypeSupport;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class QuickAdapter<T> extends BaseQuickAdapter<T, BaseAdapterHelper> {
    public QuickAdapter(Context context, int i) {
        super(context, i);
    }

    public QuickAdapter(Context context, int i, List<T> list) {
        super(context, i, list);
    }

    public QuickAdapter(Context context, ArrayList<T> arrayList, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, arrayList, multiItemTypeSupport);
    }

    @Override // com.marshalchen.ultimaterecyclerview.quickAdapter.extBaseAdapter.BaseQuickAdapter
    protected BaseAdapterHelper getAdapterHelper(int i, View view, ViewGroup viewGroup) {
        if (this.mMultiItemSupport != null) {
            return BaseAdapterHelper.get(this.context, view, viewGroup, this.mMultiItemSupport.getLayoutId(i, this.data.get(i)), i);
        }
        return BaseAdapterHelper.get(this.context, view, viewGroup, this.layoutResId, i);
    }
}
