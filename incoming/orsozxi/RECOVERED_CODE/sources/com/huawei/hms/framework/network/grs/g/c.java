package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.framework.network.grs.h.d;
import com.kobakei.ratethisapp.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.http.HttpHeaders;
import org.json.JSONArray;

/* loaded from: classes2.dex */
public class c {
    private static final String n = "c";
    private final GrsBaseInfo a;
    private final Context b;
    private final com.huawei.hms.framework.network.grs.e.a c;
    private d d;
    private final com.huawei.hms.framework.network.grs.g.k.c j;
    private com.huawei.hms.framework.network.grs.g.k.d k;
    private final Map<String, Future<d>> e = new ConcurrentHashMap(16);
    private final List<d> f = new CopyOnWriteArrayList();
    private final JSONArray g = new JSONArray();
    private final List<String> h = new CopyOnWriteArrayList();
    private final List<String> i = new CopyOnWriteArrayList();
    private String l = "";
    private long m = 1;

    class a implements Callable<d> {
        final /* synthetic */ ExecutorService a;
        final /* synthetic */ String b;
        final /* synthetic */ com.huawei.hms.framework.network.grs.e.c c;

        a(ExecutorService executorService, String str, com.huawei.hms.framework.network.grs.e.c cVar) {
            this.a = executorService;
            this.b = str;
            this.c = cVar;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public d call() {
            return c.this.b(this.a, this.b, this.c);
        }
    }

    public c(com.huawei.hms.framework.network.grs.g.k.c cVar, com.huawei.hms.framework.network.grs.e.a aVar) {
        this.j = cVar;
        this.a = cVar.b();
        this.b = cVar.a();
        this.c = aVar;
        c();
        d();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x009d A[LOOP:0: B:3:0x0006->B:33:0x009d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0095 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.huawei.hms.framework.network.grs.g.d a(java.util.concurrent.ExecutorService r17, java.util.List<java.lang.String> r18, java.lang.String r19, com.huawei.hms.framework.network.grs.e.c r20) throws java.util.concurrent.ExecutionException, java.lang.InterruptedException, java.util.concurrent.TimeoutException {
        /*
            r16 = this;
            r9 = r16
            r10 = 0
            r0 = 0
            r11 = r0
            r12 = 0
        L6:
            int r0 = r18.size()
            if (r12 >= r0) goto La1
            r13 = r18
            java.lang.Object r0 = r13.get(r12)
            java.lang.String r0 = (java.lang.String) r0
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r14 = 1
            if (r1 != 0) goto L90
            com.huawei.hms.framework.network.grs.g.a r15 = new com.huawei.hms.framework.network.grs.g.a
            android.content.Context r5 = r9.b
            com.huawei.hms.framework.network.grs.GrsBaseInfo r7 = r9.a
            r1 = r15
            r2 = r0
            r3 = r12
            r4 = r16
            r6 = r19
            r8 = r20
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            java.util.concurrent.Callable r1 = r15.g()
            r2 = r17
            java.util.concurrent.Future r1 = r2.submit(r1)
            java.util.Map<java.lang.String, java.util.concurrent.Future<com.huawei.hms.framework.network.grs.g.d>> r3 = r9.e
            r3.put(r0, r1)
            long r3 = r9.m     // Catch: java.util.concurrent.TimeoutException -> L6a java.lang.InterruptedException -> L73 java.util.concurrent.ExecutionException -> L7d java.util.concurrent.CancellationException -> L87
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.util.concurrent.TimeoutException -> L6a java.lang.InterruptedException -> L73 java.util.concurrent.ExecutionException -> L7d java.util.concurrent.CancellationException -> L87
            java.lang.Object r0 = r1.get(r3, r0)     // Catch: java.util.concurrent.TimeoutException -> L6a java.lang.InterruptedException -> L73 java.util.concurrent.ExecutionException -> L7d java.util.concurrent.CancellationException -> L87
            r1 = r0
            com.huawei.hms.framework.network.grs.g.d r1 = (com.huawei.hms.framework.network.grs.g.d) r1     // Catch: java.util.concurrent.TimeoutException -> L6a java.lang.InterruptedException -> L73 java.util.concurrent.ExecutionException -> L7d java.util.concurrent.CancellationException -> L87
            if (r1 == 0) goto L68
            boolean r0 = r1.o()     // Catch: java.util.concurrent.TimeoutException -> L5e java.lang.InterruptedException -> L60 java.util.concurrent.ExecutionException -> L63 java.util.concurrent.CancellationException -> L66
            if (r0 != 0) goto L55
            boolean r0 = r1.m()     // Catch: java.util.concurrent.TimeoutException -> L5e java.lang.InterruptedException -> L60 java.util.concurrent.ExecutionException -> L63 java.util.concurrent.CancellationException -> L66
            if (r0 == 0) goto L68
        L55:
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.n     // Catch: java.util.concurrent.TimeoutException -> L5e java.lang.InterruptedException -> L60 java.util.concurrent.ExecutionException -> L63 java.util.concurrent.CancellationException -> L66
            java.lang.String r3 = "grs request return body is not null and is OK."
            com.huawei.hms.framework.common.Logger.i(r0, r3)     // Catch: java.util.concurrent.TimeoutException -> L5e java.lang.InterruptedException -> L60 java.util.concurrent.ExecutionException -> L63 java.util.concurrent.CancellationException -> L66
            r11 = r1
            goto L93
        L5e:
            r11 = r1
            goto L6a
        L60:
            r0 = move-exception
            r11 = r1
            goto L74
        L63:
            r0 = move-exception
            r11 = r1
            goto L7e
        L66:
            r11 = r1
            goto L87
        L68:
            r11 = r1
            goto L92
        L6a:
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.n
            java.lang.String r1 = "the wait timed out"
            com.huawei.hms.framework.common.Logger.w(r0, r1)
            goto L92
        L73:
            r0 = move-exception
        L74:
            java.lang.String r1 = com.huawei.hms.framework.network.grs.g.c.n
            java.lang.String r3 = "the current thread was interrupted while waiting"
            com.huawei.hms.framework.common.Logger.w(r1, r3, r0)
            goto L93
        L7d:
            r0 = move-exception
        L7e:
            java.lang.String r1 = com.huawei.hms.framework.network.grs.g.c.n
            java.lang.String r3 = "the computation threw an ExecutionException"
            com.huawei.hms.framework.common.Logger.w(r1, r3, r0)
            goto L92
        L87:
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.n
            java.lang.String r1 = "{requestServer} the computation was cancelled"
            com.huawei.hms.framework.common.Logger.i(r0, r1)
            goto L93
        L90:
            r2 = r17
        L92:
            r14 = 0
        L93:
            if (r14 == 0) goto L9d
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.n
            java.lang.String r1 = "needBreak is true so need break current circulation"
            com.huawei.hms.framework.common.Logger.v(r0, r1)
            goto La1
        L9d:
            int r12 = r12 + 1
            goto L6
        La1:
            com.huawei.hms.framework.network.grs.g.d r0 = r9.b(r11)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.framework.network.grs.g.c.a(java.util.concurrent.ExecutorService, java.util.List, java.lang.String, com.huawei.hms.framework.network.grs.e.c):com.huawei.hms.framework.network.grs.g.d");
    }

    private void a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(str);
        String grsReqParamJoint = this.a.getGrsReqParamJoint(false, false, e(), this.b);
        if (!TextUtils.isEmpty(grsReqParamJoint)) {
            sb.append("?");
            sb.append(grsReqParamJoint);
        }
        this.i.add(sb.toString());
    }

    private d b(d dVar) throws ExecutionException, InterruptedException, TimeoutException {
        String str;
        String str2;
        for (Map.Entry<String, Future<d>> entry : this.e.entrySet()) {
            if (dVar != null && (dVar.o() || dVar.m())) {
                break;
            }
            try {
                dVar = entry.getValue().get(40000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e = e;
                str = n;
                str2 = "{checkResponse} when check result, find InterruptedException, check others";
                Logger.w(str, str2, e);
            } catch (CancellationException unused) {
                Logger.i(n, "{checkResponse} when check result, find CancellationException, check others");
            } catch (ExecutionException e2) {
                e = e2;
                str = n;
                str2 = "{checkResponse} when check result, find ExecutionException, check others";
                Logger.w(str, str2, e);
            } catch (TimeoutException unused2) {
                Logger.w(n, "{checkResponse} when check result, find TimeoutException, cancel current request task");
                if (!entry.getValue().isCancelled()) {
                    entry.getValue().cancel(true);
                }
            }
        }
        return dVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public d b(ExecutorService executorService, String str, com.huawei.hms.framework.network.grs.e.c cVar) throws ExecutionException, InterruptedException, TimeoutException {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        d dVarA = a(executorService, this.i, str, cVar);
        int iB = dVarA == null ? 0 : dVarA.b();
        Logger.v(n, "use 2.0 interface return http's code is：{%s}", Integer.valueOf(iB));
        if (iB == 404 || iB == 401) {
            if (TextUtils.isEmpty(e()) && TextUtils.isEmpty(this.a.getAppName())) {
                Logger.i(n, "request grs server use 1.0 API must set appName,please check.");
                return null;
            }
            this.e.clear();
            Logger.i(n, "this env has not deploy new interface,so use old interface.");
            dVarA = a(executorService, this.h, str, cVar);
        }
        e.a(new ArrayList(this.f), SystemClock.elapsedRealtime() - jElapsedRealtime, this.g, this.b);
        this.f.clear();
        return dVarA;
    }

    private void b(String str, String str2) {
        if (TextUtils.isEmpty(this.a.getAppName()) && TextUtils.isEmpty(e())) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[1];
        objArr[0] = TextUtils.isEmpty(e()) ? this.a.getAppName() : e();
        sb.append(String.format(locale, str, objArr));
        String grsReqParamJoint = this.a.getGrsReqParamJoint(false, false, BuildConfig.VERSION_NAME, this.b);
        if (!TextUtils.isEmpty(grsReqParamJoint)) {
            sb.append("?");
            sb.append(grsReqParamJoint);
        }
        this.h.add(sb.toString());
    }

    private void c() {
        com.huawei.hms.framework.network.grs.g.k.d dVarA = com.huawei.hms.framework.network.grs.g.j.a.a(this.b);
        if (dVarA == null) {
            Logger.w(n, "g*s***_se****er_conf*** maybe has a big error");
            return;
        }
        a(dVarA);
        List<String> listA = dVarA.a();
        if (listA == null || listA.size() <= 0) {
            Logger.v(n, "maybe grs_base_url config with [],please check.");
            return;
        }
        if (listA.size() > 10) {
            throw new IllegalArgumentException("grs_base_url's count is larger than MAX value 10");
        }
        String strC = dVarA.c();
        String strB = dVarA.b();
        if (listA.size() > 0) {
            for (String str : listA) {
                if (str.startsWith("https://")) {
                    b(strC, str);
                    a(strB, str);
                } else {
                    Logger.w(n, "grs server just support https scheme url,please check.");
                }
            }
        }
        Logger.v(n, "request to GRS server url is{%s} and {%s}", this.h, this.i);
    }

    private void d() {
        String grsParasKey = this.a.getGrsParasKey(true, true, this.b);
        this.l = this.c.a().a(grsParasKey + HttpHeaders.ETAG, "");
    }

    private String e() {
        com.huawei.hms.framework.network.grs.f.b bVarA = com.huawei.hms.framework.network.grs.f.b.a(this.b.getPackageName(), this.a);
        com.huawei.hms.framework.network.grs.local.model.a aVarA = bVarA != null ? bVarA.a() : null;
        if (aVarA == null) {
            return "";
        }
        String strB = aVarA.b();
        Logger.v(n, "get appName from local assets is{%s}", strB);
        return strB;
    }

    public d a(ExecutorService executorService, String str, com.huawei.hms.framework.network.grs.e.c cVar) {
        String str2;
        String str3;
        if (this.h.isEmpty() && this.i.isEmpty()) {
            return null;
        }
        try {
            return (d) executorService.submit(new a(executorService, str, cVar)).get(b() != null ? r0.d() : 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e = e;
            str2 = n;
            str3 = "{submitExcutorTaskWithTimeout} the current thread was interrupted while waiting";
            Logger.w(str2, str3, e);
            return null;
        } catch (CancellationException unused) {
            Logger.i(n, "{submitExcutorTaskWithTimeout} the computation was cancelled");
            return null;
        } catch (ExecutionException e2) {
            e = e2;
            str2 = n;
            str3 = "{submitExcutorTaskWithTimeout} the computation threw an ExecutionException";
            Logger.w(str2, str3, e);
            return null;
        } catch (TimeoutException unused2) {
            Logger.w(n, "{submitExcutorTaskWithTimeout} the wait timed out");
            return null;
        } catch (Exception e3) {
            e = e3;
            str2 = n;
            str3 = "{submitExcutorTaskWithTimeout} catch Exception";
            Logger.w(str2, str3, e);
            return null;
        }
    }

    public String a() {
        return this.l;
    }

    public synchronized void a(d dVar) {
        this.f.add(dVar);
        d dVar2 = this.d;
        if (dVar2 != null && (dVar2.o() || this.d.m())) {
            Logger.v(n, "grsResponseResult is ok");
            return;
        }
        if (dVar.n()) {
            Logger.i(n, "GRS server open 503 limiting strategy.");
            com.huawei.hms.framework.network.grs.h.d.a(this.a.getGrsParasKey(true, true, this.b), new d.a(dVar.k(), SystemClock.elapsedRealtime()));
            return;
        }
        if (dVar.m()) {
            Logger.i(n, "GRS server open 304 Not Modified.");
        }
        if (!dVar.o() && !dVar.m()) {
            Logger.v(n, "grsResponseResult has exception so need return");
            return;
        }
        this.d = dVar;
        this.c.a(this.a, dVar, this.b, this.j);
        for (Map.Entry<String, Future<d>> entry : this.e.entrySet()) {
            if (!entry.getKey().equals(dVar.l()) && !entry.getValue().isCancelled()) {
                Logger.i(n, "future cancel");
                entry.getValue().cancel(true);
            }
        }
    }

    public void a(com.huawei.hms.framework.network.grs.g.k.d dVar) {
        this.k = dVar;
    }

    public com.huawei.hms.framework.network.grs.g.k.d b() {
        return this.k;
    }
}
