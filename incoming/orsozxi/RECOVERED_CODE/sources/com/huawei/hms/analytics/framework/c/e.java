package com.huawei.hms.analytics.framework.c;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.framework.config.ICallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class e extends a {
    public e(List<Event> list, com.huawei.hms.analytics.framework.c.a.a aVar, ICallback iCallback) {
        super(list, aVar, iCallback);
    }

    private void e() {
        long jB = b(this.a.size());
        this.d.e = false;
        this.b.setStopEventReport(false);
        for (int i = 0; i < jB; i++) {
            try {
                a(a(i * 300));
            } catch (IllegalArgumentException e) {
                HiLog.w("regionProcess", e.getMessage());
            }
        }
    }

    public final void d() {
        if (b()) {
            a(true, this.d.c);
            try {
                Event eventA = a();
                if (eventA != null) {
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(eventA);
                    this.d.e = true;
                    this.b.setStopEventReport(true);
                    a(arrayList);
                }
                e();
            } catch (IllegalArgumentException e) {
                HiLog.e("regionProcess", e.getMessage());
            }
        }
    }
}
