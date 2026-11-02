package com.marshalchen.ultimaterecyclerview.quickAdapter.extUtimateRVA;

/* loaded from: classes2.dex */
public interface MultiItemTypeSupport<T> {
    int getItemViewType(int i, T t);

    int getLayoutId(int i, T t);

    int getViewTypeCount();
}
