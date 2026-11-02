package com.huawei.hms.analytics.framework;

import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.framework.SyncManager;
import com.huawei.hms.analytics.framework.b.c;
import com.huawei.hms.analytics.framework.c.f;
import com.huawei.hms.analytics.framework.c.h;
import com.huawei.hms.analytics.framework.config.ICallback;
import com.huawei.hms.analytics.framework.e.b;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class a implements HAFrameworkInstance {
    private String a;

    public a(String str) {
        this.a = str;
    }

    private com.huawei.hms.analytics.framework.c.a.a a(String str, String str2) {
        com.huawei.hms.analytics.framework.c.a.a aVar = new com.huawei.hms.analytics.framework.c.a.a();
        aVar.b = str2;
        aVar.a = this.a;
        aVar.c = str;
        aVar.f = true;
        return aVar;
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void clearCacheData(String str) {
        com.huawei.hms.analytics.framework.d.a.a().b(this.a);
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onBackground(long j) {
        b bVarA = com.huawei.hms.analytics.framework.e.a.a().a(this.a);
        bVarA.c = true;
        bVarA.d = j;
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onEvent(String str, List<JSONObject> list, ICallback iCallback) {
        com.huawei.hms.analytics.framework.d.a.a().a(this.a, str, list, iCallback);
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onEvent(String str, List<JSONObject> list, ICallback iCallback, long j) {
        com.huawei.hms.analytics.framework.d.a.a().a(this.a, str, list, iCallback, j);
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onEvent(List<Event> list, ICallback iCallback) {
        com.huawei.hms.analytics.framework.d.a.a().a(list, iCallback);
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onEventRecord(String str, List<JSONObject> list, long j) {
        com.huawei.hms.analytics.framework.d.a.a();
        com.huawei.hms.analytics.framework.d.a.a(this.a, str, list, j);
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onForeground(long j) {
        b bVarA = com.huawei.hms.analytics.framework.e.a.a().a(this.a);
        if (bVarA.d != 0) {
            bVarA.c = j - bVarA.d > bVarA.b;
            bVarA.d = 0L;
        }
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onReport(String str, ICallback iCallback) {
        com.huawei.hms.analytics.framework.d.a.a();
        String str2 = this.a;
        if (com.huawei.hms.analytics.framework.d.a.a(str2)) {
            com.huawei.hms.analytics.framework.f.a.b().a(new h(str2, str, iCallback));
        }
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onReport(String str, String str2, ICallback iCallback) {
        com.huawei.hms.analytics.framework.d.a.a();
        com.huawei.hms.analytics.framework.c.a.a aVarA = a(str, str2);
        if (com.huawei.hms.analytics.framework.d.a.a(aVarA.a)) {
            SyncManager.getInstance().start(SyncManager.TaskNames.REGION_CHANGE);
            new f(aVarA, iCallback).run();
            return;
        }
        IStorageHandler iStorageHandlerA = com.huawei.hms.analytics.framework.a.a.a(aVarA.a);
        if (iStorageHandlerA != null) {
            iStorageHandlerA.deleteByTag(aVarA.a);
            com.huawei.hms.analytics.framework.b.b.a().a(aVarA.a).serviceListener(true, aVarA.c);
        }
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onReport(String str, String str2, List<JSONObject> list, ICallback iCallback) {
        com.huawei.hms.analytics.framework.d.a.a();
        com.huawei.hms.analytics.framework.c.a.a aVarA = a(str, str2);
        if (com.huawei.hms.analytics.framework.d.a.a(aVarA.a)) {
            com.huawei.hms.analytics.framework.f.a.b().a(new f(aVarA, list, iCallback));
        }
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onStreamEvent(String str, List<JSONObject> list, ICallback iCallback) {
        com.huawei.hms.analytics.framework.d.a.a();
        com.huawei.hms.analytics.framework.d.a.b(this.a, str, list, iCallback);
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void onStreamEvent(String str, List<JSONObject> list, ICallback iCallback, long j) {
        com.huawei.hms.analytics.framework.d.a.a();
        com.huawei.hms.analytics.framework.d.a.b(this.a, str, list, iCallback, j);
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void setMinSessionDuration(long j) {
        com.huawei.hms.analytics.framework.e.a.a().a(this.a).b = j;
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void setNeedRefreshSession(boolean z) {
        c cVarB = com.huawei.hms.analytics.framework.b.b.a().b(this.a);
        if (cVarB != null) {
            cVarB.a = z;
        }
    }

    @Override // com.huawei.hms.analytics.framework.HAFrameworkInstance
    public final void setSessionTimeoutDuration(long j) {
        com.huawei.hms.analytics.framework.e.a.a().a(this.a).a = j;
    }
}
