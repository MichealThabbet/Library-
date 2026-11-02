package com.huawei.hms.analytics.framework.e;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class a {
    private static a a;
    private final Map<String, b> b = new ConcurrentHashMap();

    private a() {
    }

    public static a a() {
        if (a == null) {
            b();
        }
        return a;
    }

    private static synchronized void b() {
        if (a == null) {
            a = new a();
        }
    }

    public final b a(String str) {
        if (this.b.containsKey(str)) {
            return this.b.get(str);
        }
        b bVar = new b();
        this.b.put(str, bVar);
        return bVar;
    }

    public final b a(String str, long j) {
        b bVarA = a(str);
        bVarA.a(str, j);
        return bVarA;
    }
}
