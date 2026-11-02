package com.huawei.hms.analytics.framework.c;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.framework.SyncManager;
import com.huawei.hms.analytics.framework.config.ICallback;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class f implements Runnable {
    private final com.huawei.hms.analytics.framework.c.a.a a;
    private final ICallback b;
    private List<JSONObject> c;

    public f(com.huawei.hms.analytics.framework.c.a.a aVar, ICallback iCallback) {
        this.a = aVar;
        this.b = iCallback;
    }

    public f(com.huawei.hms.analytics.framework.c.a.a aVar, List<JSONObject> list, ICallback iCallback) {
        this.a = aVar;
        this.c = list;
        this.b = iCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        List<Event> events;
        List<JSONObject> list = this.c;
        if (list == null || list.size() <= 0) {
            String str = this.a.a;
            IStorageHandler iStorageHandlerA = com.huawei.hms.analytics.framework.a.a.a(str);
            if (iStorageHandlerA == null) {
                events = null;
            } else {
                events = iStorageHandlerA.readEvents(str);
                if (events == null || events.size() == 0) {
                    HiLog.i("regionReport", "no have last region data. tag: " + this.a.a);
                    events = null;
                } else {
                    iStorageHandlerA.deleteByTag(str);
                }
            }
        } else {
            long jCurrentTimeMillis = System.currentTimeMillis();
            com.huawei.hms.analytics.framework.e.b bVarA = com.huawei.hms.analytics.framework.e.a.a().a(this.a.a, jCurrentTimeMillis);
            String strA = bVarA.a();
            boolean zB = bVarA.b();
            events = new ArrayList<>();
            for (JSONObject jSONObject : this.c) {
                String str2 = (String) jSONObject.remove("^eventId");
                String str3 = (String) jSONObject.remove("^id");
                String str4 = (String) jSONObject.remove("^pid");
                Event event = new Event();
                event.setServiceTag(this.a.a);
                event.setEvttype(this.a.b);
                event.setEvtid(str2);
                event.setEvttime(String.valueOf(jCurrentTimeMillis));
                event.setSessionid(String.valueOf(zB));
                event.setSessionName(strA);
                event.setAssociationid(str3);
                event.setPid(str4);
                event.setContent(jSONObject.toString());
                events.add(event);
            }
        }
        SyncManager.getInstance().countDown(SyncManager.TaskNames.REGION_CHANGE);
        if (events != null) {
            new e(events, this.a, this.b).d();
            return;
        }
        com.huawei.hms.analytics.framework.b.b.a().a(this.a.a).serviceListener(true, this.a.c);
    }
}
