package com.marshalchen.ultimaterecyclerview.expanx.Util;

import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/* loaded from: classes2.dex */
public interface ItemDataClickListener<T extends ExpandableItemData> {
    void onExpandChildren(T t);

    void onHideChildren(T t);
}
