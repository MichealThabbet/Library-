package com.huawei.hms.audiokit;

import android.os.Bundle;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class d {
    public static int a(Bundle bundle, String str) {
        if (bundle == null) {
            return 0;
        }
        return new e(bundle).a(str);
    }

    public static <T extends Parcelable> T b(Bundle bundle, String str) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        return (T) new e(bundle).b(str);
    }
}
