package com.huawei.hms.analytics.framework.b;

import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.framework.config.ICollectorConfig;
import com.huawei.hms.analytics.framework.policy.IStoragePolicy;

/* loaded from: classes2.dex */
public final class a {
    ICollectorConfig a;
    public IStoragePolicy b;
    c c;

    public a(ICollectorConfig iCollectorConfig, IStorageHandler iStorageHandler, IStoragePolicy iStoragePolicy) {
        this.a = iCollectorConfig;
        c cVar = new c();
        this.c = cVar;
        cVar.b = iStorageHandler;
        this.b = iStoragePolicy;
    }
}
