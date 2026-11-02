package com.marshalchen.ultimaterecyclerview.expanx.Util;

import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/* loaded from: classes2.dex */
public interface ParentVH<bindData extends ExpandableItemData> {
    void bindView(bindData binddata, int i, ItemDataClickListener itemDataClickListener);

    int closeDegree();

    void onParentItemClick(String str);

    int openDegree();

    void rotationExpandIcon(float f, float f2);
}
