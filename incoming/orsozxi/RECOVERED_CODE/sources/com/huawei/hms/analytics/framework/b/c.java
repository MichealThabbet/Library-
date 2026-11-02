package com.huawei.hms.analytics.framework.b;

import com.huawei.hms.analytics.core.storage.IStorageHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class c {
    public IStorageHandler b;
    public boolean a = false;
    private Map<String, Long> c = new ConcurrentHashMap();

    public final long a(String str) {
        Long l = this.c.get(str);
        if (l != null) {
            return l.longValue();
        }
        return 0L;
    }

    public final void a(String str, long j) {
        this.c.put(str, Long.valueOf(j));
    }
}
