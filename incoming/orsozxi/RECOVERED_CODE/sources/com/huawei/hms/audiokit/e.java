package com.huawei.hms.audiokit;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes2.dex */
class e {
    private final Bundle a;

    e(Bundle bundle) {
        this.a = bundle == null ? new Bundle() : bundle;
    }

    int a(String str) {
        try {
            return this.a.getInt(str, 0);
        } catch (Exception e) {
            Log.e("SafeBundle", "getInt exception: " + e.getMessage());
            return 0;
        }
    }

    <T extends Parcelable> T b(String str) {
        try {
            return (T) this.a.getParcelable(str);
        } catch (Exception e) {
            Log.e("SafeBundle", "getParcelable exception: " + e.getMessage());
            return null;
        }
    }
}
