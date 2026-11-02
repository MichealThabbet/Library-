package com.huawei.hms.analytics.framework.d;

import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.framework.c.d;
import com.huawei.hms.analytics.framework.config.ICallback;
import com.huawei.hms.analytics.framework.policy.IStoragePolicy;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class a {
    private static a a;

    public static a a() {
        if (a == null) {
            b();
        }
        return a;
    }

    public static void a(String str, String str2, List<JSONObject> list, long j) {
        new d(str, str2, list, j).run();
    }

    public static boolean a(String str) {
        IStoragePolicy iStoragePolicyB = com.huawei.hms.analytics.framework.a.a.b(str);
        return iStoragePolicyB != null && iStoragePolicyB.decide(IStoragePolicy.PolicyType.NETWORK, "");
    }

    private static synchronized void b() {
        if (a == null) {
            a = new a();
        }
    }

    public static void b(String str, String str2, List<JSONObject> list, ICallback iCallback) {
        d dVar = new d(str, str2, list);
        dVar.a = true;
        dVar.b = iCallback;
        com.huawei.hms.analytics.framework.f.a.a().a(dVar);
    }

    public static void b(String str, String str2, List<JSONObject> list, ICallback iCallback, long j) {
        d dVar = new d(str, str2, list, j);
        dVar.a = true;
        dVar.b = iCallback;
        com.huawei.hms.analytics.framework.f.a.a().a(dVar);
    }

    public final synchronized void a(String str, String str2, List<JSONObject> list, ICallback iCallback) {
        d dVar = new d(str, str2, list);
        dVar.b = iCallback;
        com.huawei.hms.analytics.framework.f.a.a().a(dVar);
    }

    public final synchronized void a(String str, String str2, List<JSONObject> list, ICallback iCallback, long j) {
        d dVar = new d(str, str2, list, j);
        dVar.b = iCallback;
        com.huawei.hms.analytics.framework.f.a.a().a(dVar);
    }

    public final synchronized void a(List<Event> list, ICallback iCallback) {
        d dVar = new d(list);
        dVar.b = iCallback;
        com.huawei.hms.analytics.framework.f.a.a().a(dVar);
    }

    public final synchronized void b(String str) {
        IStorageHandler iStorageHandlerA = com.huawei.hms.analytics.framework.a.a.a(str);
        if (iStorageHandlerA != null) {
            iStorageHandlerA.deleteAll();
        }
    }
}
