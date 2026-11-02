package com.huawei.hms.analytics.framework.b;

import com.huawei.hms.analytics.framework.config.ICollectorConfig;
import com.huawei.hms.analytics.framework.config.IMandatoryParameters;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class b {
    private static final b c = new b();
    public IMandatoryParameters a;
    public final Map<String, a> b = new ConcurrentHashMap();

    public static b a() {
        return c;
    }

    public final ICollectorConfig a(String str) {
        a aVar = this.b.get(str);
        if (aVar != null) {
            return aVar.a;
        }
        return null;
    }

    public final synchronized void a(String str, IMandatoryParameters iMandatoryParameters, a aVar) {
        this.b.put(str, aVar);
        if (this.a == null && iMandatoryParameters != null) {
            this.a = iMandatoryParameters;
        }
    }

    public final c b(String str) {
        a aVar = this.b.get(str);
        if (aVar != null) {
            return aVar.c;
        }
        return null;
    }
}
