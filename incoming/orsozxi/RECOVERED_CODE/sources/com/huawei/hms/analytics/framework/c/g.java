package com.huawei.hms.analytics.framework.c;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.framework.config.ICallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class g extends a {
    public g(com.huawei.hms.analytics.framework.c.a.a aVar, ICallback iCallback) {
        super(null, aVar, iCallback);
    }

    private void e() {
        this.d.e = false;
        this.b.setStopEventReport(false);
        long jB = b(this.c.count(this.d.a));
        for (int i = 0; i < jB; i++) {
            List<Event> events = this.c.readEvents(this.d.a, this.d.b, 300);
            a(events, true);
            a(events);
        }
    }

    public final void d() {
        if (b()) {
            a(false, "");
            try {
                Event eventA = a();
                if (eventA != null) {
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(eventA);
                    this.d.e = true;
                    this.b.setStopEventReport(true);
                    a(arrayList);
                }
            } catch (IllegalArgumentException e) {
                HiLog.e("ReportAssignment", e.getMessage());
                a(System.currentTimeMillis());
            }
            try {
                if (this.c == null) {
                    return;
                }
                e();
            } catch (Exception unused) {
                HiLog.e("ReportAssignment", "upload data failed.");
            }
        }
    }
}
