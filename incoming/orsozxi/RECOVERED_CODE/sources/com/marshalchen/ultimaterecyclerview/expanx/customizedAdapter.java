package com.marshalchen.ultimaterecyclerview.expanx;

import android.content.Context;
import com.marshalchen.ultimaterecyclerview.expanx.Util.child;
import com.marshalchen.ultimaterecyclerview.expanx.Util.parent;

/* loaded from: classes2.dex */
public abstract class customizedAdapter<G extends parent<SmartItem>, T extends child<SmartItem>> extends LinearExpanxURVAdapter<SmartItem, G, T> {
    public customizedAdapter(Context context) {
        super(context, 1, true);
    }
}
