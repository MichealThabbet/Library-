package com.huawei.hms.analytics.framework.c;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.framework.config.ICallback;
import java.util.List;

/* loaded from: classes2.dex */
public final class h implements Runnable {
    private String a;
    private String b;
    private ICallback c;
    private IStorageHandler d;

    public h(String str, String str2, ICallback iCallback) {
        this.a = str;
        this.b = str2;
        this.c = iCallback;
        this.d = com.huawei.hms.analytics.framework.a.a.a(str);
    }

    @Override // java.lang.Runnable
    public final void run() {
        HiLog.d("ReportMission", "report running, serviceTag: " + this.a);
        ICallback iCallback = this.c;
        if (iCallback != null && iCallback.isAAIDChanged(this.a, true)) {
            HiLog.i("ReportMission", "aaid changed");
            return;
        }
        if (this.d == null) {
            return;
        }
        List<Event> listA = c.a(this.a, this.b, false);
        if (listA != null && listA.size() > 0) {
            this.d.insertEx(listA);
        }
        com.huawei.hms.analytics.framework.c.a.a aVar = new com.huawei.hms.analytics.framework.c.a.a();
        aVar.a = this.a;
        aVar.b = this.b;
        aVar.c = com.huawei.hms.analytics.framework.b.b.a().a(this.a).getRegion();
        new g(aVar, this.c).d();
    }
}
