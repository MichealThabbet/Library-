package com.huawei.hms.analytics.framework.c;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.framework.config.ICallback;
import com.huawei.hms.analytics.framework.policy.IStoragePolicy;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class d implements Runnable {
    public boolean a;
    public ICallback b;
    private String c;
    private String d;
    private long e;
    private List<JSONObject> f;
    private String g;
    private boolean h;
    private List<Event> i;

    public d(String str, String str2, List<JSONObject> list) {
        this(str, str2, list, 0L);
    }

    public d(String str, String str2, List<JSONObject> list, long j) {
        this.c = str;
        this.e = j == 0 ? System.currentTimeMillis() : j;
        if (list != null) {
            this.f = new ArrayList(list);
        }
        this.d = str2;
        if ("oper".equals(str2) && com.huawei.hms.analytics.framework.b.b.a().a(str).isEnableSession("oper")) {
            com.huawei.hms.analytics.framework.e.b bVarA = com.huawei.hms.analytics.framework.e.a.a().a(str, this.e);
            this.g = bVarA.a();
            this.h = bVarA.b();
        }
    }

    public d(List<Event> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList(list);
        this.i = arrayList;
        this.c = ((Event) arrayList.get(0)).getServiceTag();
        this.d = this.i.get(0).getEvttype();
    }

    private void a() {
        com.huawei.hms.analytics.framework.b.c cVarB = com.huawei.hms.analytics.framework.b.b.a().b(this.c);
        if (cVarB == null) {
            HiLog.w("RecordMission", "get framework config info error");
            return;
        }
        long jA = cVarB.a(this.d);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - jA <= 30000) {
            HiLog.w("RecordMission", "autoReport timeout. interval < 30s ");
            return;
        }
        HiLog.d("RecordMission", "begin to auto report!");
        cVarB.a(this.d, jCurrentTimeMillis);
        com.huawei.hms.analytics.framework.f.a.b().a(new h(this.c, this.d, this.b));
    }

    private void a(List<Event> list) {
        IStorageHandler iStorageHandlerA = com.huawei.hms.analytics.framework.a.a.a(this.c);
        IStoragePolicy iStoragePolicyB = com.huawei.hms.analytics.framework.a.a.b(this.c);
        if (iStorageHandlerA == null || iStoragePolicyB == null) {
            HiLog.e("RecordMission", "storageHandler is null!");
            return;
        }
        if (iStoragePolicyB.decide(IStoragePolicy.PolicyType.STORAGELENGTH, "userEvent.db")) {
            HiLog.e("RecordMission", "db file reach max limited length,clear db file");
            iStorageHandlerA.deleteAll();
            iStorageHandlerA.insertEx(list);
            return;
        }
        long jCount = iStorageHandlerA.count(this.c);
        if (jCount == 0) {
            iStorageHandlerA.insertEx(list);
            HiLog.d("RecordMission", "record event size: 1");
            return;
        }
        if (jCount > 5000) {
            HiLog.e("RecordMission", "db file reach max limited size,clear db file");
            iStorageHandlerA.deleteByTag(this.c);
            iStorageHandlerA.insertEx(list);
            return;
        }
        iStorageHandlerA.insertEx(list);
        long jCount2 = iStorageHandlerA.count(this.c);
        HiLog.i("RecordMission", "record evt size : ".concat(String.valueOf(jCount2)));
        if (iStoragePolicyB.decide(IStoragePolicy.PolicyType.STORAGESIZE, jCount2) && iStoragePolicyB.decide(IStoragePolicy.PolicyType.NETWORK, "")) {
            HiLog.i("RecordMission", "ready to auto report!");
            a();
        }
    }

    public final void a(List<Event> list, JSONObject jSONObject) {
        String str = (String) jSONObject.remove("^eventId");
        String str2 = (String) jSONObject.remove("^id");
        String str3 = (String) jSONObject.remove("^pid");
        Object objRemove = jSONObject.remove("f");
        Event event = new Event();
        event.setServiceTag(this.c);
        event.setEvttype(this.d);
        event.setEvtid(str);
        event.setEvttime(String.valueOf(this.e));
        event.setSessionid(String.valueOf(this.h));
        event.setSessionName(this.g);
        event.setAssociationid(str2);
        event.setPid(str3);
        if (objRemove != null) {
            event.setPushEvtFlag((String) objRemove);
        }
        String string = jSONObject.toString();
        if (!this.a) {
            string = b.a(string);
        }
        event.setContent(string);
        list.add(event);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0083  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            r10 = this;
            java.lang.String r0 = "RecordMission"
            com.huawei.hms.analytics.framework.SyncManager r1 = com.huawei.hms.analytics.framework.SyncManager.getInstance()     // Catch: java.lang.InterruptedException -> L17
            java.lang.String r2 = "region_change"
            r3 = 2000(0x7d0, double:9.88E-321)
            r1.await(r2, r3)     // Catch: java.lang.InterruptedException -> L17
            com.huawei.hms.analytics.framework.SyncManager r1 = com.huawei.hms.analytics.framework.SyncManager.getInstance()     // Catch: java.lang.InterruptedException -> L17
            java.lang.String r2 = "aaid_task"
            r1.await(r2, r3)     // Catch: java.lang.InterruptedException -> L17
            goto L1d
        L17:
            java.lang.String r1 = "wait sync error"
            com.huawei.hms.analytics.core.log.HiLog.e(r0, r1)
        L1d:
            com.huawei.hms.analytics.framework.config.ICallback r1 = r10.b
            if (r1 == 0) goto L88
            java.lang.String r2 = r10.c
            java.util.List<com.huawei.hms.analytics.core.storage.Event> r3 = r10.i
            java.lang.String r4 = "$LaunchApp"
            r5 = 0
            java.lang.String r6 = "$AppFirstOpen"
            if (r3 == 0) goto L51
            java.util.Iterator r3 = r3.iterator()
        L30:
            boolean r7 = r3.hasNext()
            if (r7 == 0) goto L51
            java.lang.Object r7 = r3.next()
            com.huawei.hms.analytics.core.storage.Event r7 = (com.huawei.hms.analytics.core.storage.Event) r7
            java.lang.String r8 = r7.getEvtid()
            boolean r8 = r8.equals(r6)
            if (r8 != 0) goto L7d
            java.lang.String r7 = r7.getEvtid()
            boolean r7 = r7.equals(r4)
            if (r7 == 0) goto L30
            goto L7d
        L51:
            java.util.List<org.json.JSONObject> r3 = r10.f
            if (r3 == 0) goto L7c
            java.util.Iterator r3 = r3.iterator()
        L59:
            boolean r7 = r3.hasNext()
            if (r7 == 0) goto L7c
            java.lang.Object r7 = r3.next()
            org.json.JSONObject r7 = (org.json.JSONObject) r7
            java.lang.String r8 = "^eventId"
            java.lang.String r9 = r7.optString(r8)
            boolean r9 = r6.equals(r9)
            if (r9 != 0) goto L7d
            java.lang.String r7 = r7.optString(r8)
            boolean r7 = r4.equals(r7)
            if (r7 == 0) goto L59
            goto L7d
        L7c:
            r5 = 1
        L7d:
            boolean r1 = r1.isAAIDChanged(r2, r5)
            if (r1 == 0) goto L88
            java.lang.String r1 = "aaid changed"
            com.huawei.hms.analytics.core.log.HiLog.i(r0, r1)
        L88:
            java.util.List<com.huawei.hms.analytics.core.storage.Event> r0 = r10.i
            if (r0 == 0) goto Lac
            java.util.Iterator r1 = r0.iterator()
        L90:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto La8
            java.lang.Object r2 = r1.next()
            com.huawei.hms.analytics.core.storage.Event r2 = (com.huawei.hms.analytics.core.storage.Event) r2
            java.lang.String r3 = r2.getContent()
            java.lang.String r3 = com.huawei.hms.analytics.framework.c.b.a(r3)
            r2.setContent(r3)
            goto L90
        La8:
            r10.a(r0)
            return
        Lac:
            java.util.List<org.json.JSONObject> r0 = r10.f
            if (r0 != 0) goto Lb1
            return
        Lb1:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List<org.json.JSONObject> r1 = r10.f
            java.util.Iterator r1 = r1.iterator()
        Lbc:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto Lcc
            java.lang.Object r2 = r1.next()
            org.json.JSONObject r2 = (org.json.JSONObject) r2
            r10.a(r0, r2)
            goto Lbc
        Lcc:
            int r1 = r0.size()
            if (r1 != 0) goto Ld3
            return
        Ld3:
            boolean r1 = r10.a
            if (r1 == 0) goto Lea
            com.huawei.hms.analytics.framework.f.a r1 = com.huawei.hms.analytics.framework.f.a.b()
            com.huawei.hms.analytics.framework.c.k r2 = new com.huawei.hms.analytics.framework.c.k
            java.lang.String r3 = r10.c
            java.lang.String r4 = r10.d
            com.huawei.hms.analytics.framework.config.ICallback r5 = r10.b
            r2.<init>(r3, r4, r0, r5)
            r1.a(r2)
            return
        Lea:
            r10.a(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.framework.c.d.run():void");
    }
}
