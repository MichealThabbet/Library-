package com.marshalchen.ultimaterecyclerview.expanx.Util;

import android.view.View;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/* loaded from: classes2.dex */
public abstract class child<T extends ExpandableItemData> extends BaseViewHolder<T> implements ChildVH<T> {
    protected ChildClickListener listener;

    public child(View view) {
        super(view);
    }

    public void setChildListener(ChildClickListener childClickListener) {
        this.listener = childClickListener;
    }
}
