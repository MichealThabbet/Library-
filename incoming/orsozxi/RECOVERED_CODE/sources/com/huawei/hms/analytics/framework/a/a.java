package com.huawei.hms.analytics.framework.a;

import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.framework.b.b;
import com.huawei.hms.analytics.framework.b.c;
import com.huawei.hms.analytics.framework.policy.IStoragePolicy;

/* loaded from: classes2.dex */
public final class a {
    public static IStorageHandler a(String str) {
        c cVarB = b.a().b(str);
        if (cVarB != null) {
            return cVarB.b;
        }
        return null;
    }

    public static IStoragePolicy b(String str) {
        com.huawei.hms.analytics.framework.b.a aVar = b.a().b.get(str);
        if (aVar != null) {
            return aVar.b;
        }
        return null;
    }
}
