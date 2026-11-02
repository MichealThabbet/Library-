package com.marshalchen.ultimaterecyclerview.expanx;

import android.content.Context;
import java.util.List;
import java.util.UUID;

/* loaded from: classes2.dex */
public class SmartItem extends ExpandableItemData {
    public SmartItem(int i, String str, String str2, int i2, List<SmartItem> list) {
        super(i, str, str2, UUID.randomUUID().toString(), i2, list);
    }

    public static SmartItem parent(String str, String str2, List<SmartItem> list) {
        return new SmartItem(1026, str, str2, 0, list);
    }

    public static SmartItem parent(Context context, int i, String str, List<SmartItem> list) {
        return new SmartItem(1026, context.getResources().getString(i), str, 0, list);
    }

    public static SmartItem child(String str, String str2) {
        return new SmartItem(1135, str, str2, 1, null);
    }

    public static SmartItem child(Context context, int i, String str) {
        return new SmartItem(1135, context.getResources().getString(i), str, 1, null);
    }
}
