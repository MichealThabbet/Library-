package com.marshalchen.ultimaterecyclerview.expanx.Util;

import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/* loaded from: classes2.dex */
public interface ChildVH<bindData extends ExpandableItemData> {
    void bindView(bindData binddata, int i);

    void onChildItemClick(String str, String str2);
}
