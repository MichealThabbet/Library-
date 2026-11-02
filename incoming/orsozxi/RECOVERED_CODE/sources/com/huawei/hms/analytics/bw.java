package com.huawei.hms.analytics;

import android.os.Bundle;
import com.huawei.hms.analytics.core.log.HiLog;

/* loaded from: classes2.dex */
public final class bw {
    private final Bundle lmn;

    public bw(Bundle bundle) {
        this.lmn = bundle != null ? (Bundle) bundle.clone() : new Bundle();
    }

    public final String hij(String str) {
        try {
            String string = this.lmn.getString(str);
            this.lmn.remove(str);
            return string;
        } catch (Throwable unused) {
            HiLog.e("SafeParams", "getString throwable");
            return "";
        }
    }

    public final Object ijk(String str) {
        try {
            return this.lmn.getSerializable(str);
        } catch (Throwable unused) {
            HiLog.e("SafeParams", "getSerializable throwable");
            return null;
        }
    }

    public final int ikl(String str) {
        try {
            return this.lmn.getInt(str);
        } catch (Throwable unused) {
            HiLog.e("SafeParams", "getString throwable");
            return 0;
        }
    }

    public final String klm(String str) {
        try {
            return this.lmn.getString(str);
        } catch (Throwable unused) {
            HiLog.e("SafeParams", "getString throwable");
            return "";
        }
    }

    public final Bundle lmn(String str) {
        try {
            Bundle bundle = this.lmn.getBundle(str);
            return bundle == null ? new Bundle() : bundle;
        } catch (Throwable unused) {
            HiLog.e("SafeParams", "getBundle throwable");
            return new Bundle();
        }
    }
}
