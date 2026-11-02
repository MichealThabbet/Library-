package com.huawei.hms.analytics;

import android.content.Context;

/* loaded from: classes2.dex */
public final class dr {
    public static void lmn(Context context, int i) {
        if (dl.klm(context, "global_v2", "sig_flg", 0) == i) {
            return;
        }
        dl.lmn(context, "global_v2", "sig_flg", i);
    }
}
