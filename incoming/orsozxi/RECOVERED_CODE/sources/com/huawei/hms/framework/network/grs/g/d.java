package com.huawei.hms.framework.network.grs.g;

import android.text.TextUtils;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpHeaders;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class d {
    private static final String o = "d";
    private Map<String, List<String>> a;
    private byte[] b;
    private int c;
    private long d;
    private long e;
    private long f;
    private String g;
    private int h;
    private int i;
    private String j;
    private long k;
    private String l;
    private Exception m;
    private String n;

    public d(int i, Map<String, List<String>> map, byte[] bArr, long j) throws NumberFormatException {
        this.c = 0;
        this.h = 2;
        this.i = ConnectionResult.RESOLUTION_REQUIRED;
        this.j = "";
        this.k = 0L;
        this.l = "";
        this.c = i;
        this.a = map;
        this.b = ByteBuffer.wrap(bArr).array();
        this.d = j;
        s();
    }

    public d(Exception exc, long j) {
        this.c = 0;
        this.h = 2;
        this.i = ConnectionResult.RESOLUTION_REQUIRED;
        this.j = "";
        this.k = 0L;
        this.l = "";
        this.m = exc;
        this.d = j;
    }

    private void a(Map<String, String> map) {
        String str;
        String str2;
        if (map.containsKey(HttpHeaders.ETAG)) {
            String str3 = map.get(HttpHeaders.ETAG);
            if (!TextUtils.isEmpty(str3)) {
                Logger.i(o, "success get Etag from server");
                a(str3);
                return;
            } else {
                str = o;
                str2 = "The Response Heads Etag is Empty";
            }
        } else {
            str = o;
            str2 = "Response Heads has not Etag";
        }
        Logger.i(str, str2);
    }

    private void b(int i) {
        this.i = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00bb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void b(java.util.Map<java.lang.String, java.lang.String> r12) throws java.lang.NumberFormatException {
        /*
            r11 = this;
            java.lang.String r0 = "Cache-Control"
            boolean r1 = r12.containsKey(r0)
            r2 = 1000(0x3e8, double:4.94E-321)
            r4 = 0
            r6 = 0
            r7 = 1
            if (r1 == 0) goto L4d
            java.lang.Object r12 = r12.get(r0)
            java.lang.String r12 = (java.lang.String) r12
            boolean r0 = android.text.TextUtils.isEmpty(r12)
            if (r0 != 0) goto Laf
            java.lang.String r0 = "max-age="
            boolean r1 = r12.contains(r0)
            if (r1 == 0) goto Laf
            int r0 = r12.indexOf(r0)     // Catch: java.lang.NumberFormatException -> L43
            int r0 = r0 + 8
            java.lang.String r12 = r12.substring(r0)     // Catch: java.lang.NumberFormatException -> L43
            long r0 = java.lang.Long.parseLong(r12)     // Catch: java.lang.NumberFormatException -> L43
            java.lang.String r12 = com.huawei.hms.framework.network.grs.g.d.o     // Catch: java.lang.NumberFormatException -> L41
            java.lang.String r8 = "Cache-Control value{%s}"
            java.lang.Object[] r9 = new java.lang.Object[r7]     // Catch: java.lang.NumberFormatException -> L41
            java.lang.Long r10 = java.lang.Long.valueOf(r0)     // Catch: java.lang.NumberFormatException -> L41
            r9[r6] = r10     // Catch: java.lang.NumberFormatException -> L41
            com.huawei.hms.framework.common.Logger.v(r12, r8, r9)     // Catch: java.lang.NumberFormatException -> L41
            goto Lb0
        L41:
            r12 = move-exception
            goto L45
        L43:
            r12 = move-exception
            r0 = r4
        L45:
            java.lang.String r8 = com.huawei.hms.framework.network.grs.g.d.o
            java.lang.String r9 = "getExpireTime addHeadersToResult NumberFormatException"
            com.huawei.hms.framework.common.Logger.w(r8, r9, r12)
            goto Lb0
        L4d:
            java.lang.String r0 = "Expires"
            boolean r1 = r12.containsKey(r0)
            if (r1 == 0) goto La8
            java.lang.Object r0 = r12.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r1 = com.huawei.hms.framework.network.grs.g.d.o
            java.lang.Object[] r8 = new java.lang.Object[r7]
            r8[r6] = r0
            java.lang.String r9 = "expires is{%s}"
            com.huawei.hms.framework.common.Logger.v(r1, r9, r8)
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat
            java.util.Locale r8 = java.util.Locale.ROOT
            java.lang.String r9 = "EEE, d MMM yyyy HH:mm:ss 'GMT'"
            r1.<init>(r9, r8)
            r8 = 0
            java.lang.String r9 = "Date"
            boolean r10 = r12.containsKey(r9)
            if (r10 == 0) goto L7f
            java.lang.Object r12 = r12.get(r9)
            r8 = r12
            java.lang.String r8 = (java.lang.String) r8
        L7f:
            java.util.Date r12 = r1.parse(r0)     // Catch: java.text.ParseException -> L9f
            boolean r0 = android.text.TextUtils.isEmpty(r8)     // Catch: java.text.ParseException -> L9f
            if (r0 == 0) goto L8f
            java.util.Date r0 = new java.util.Date     // Catch: java.text.ParseException -> L9f
            r0.<init>()     // Catch: java.text.ParseException -> L9f
            goto L93
        L8f:
            java.util.Date r0 = r1.parse(r8)     // Catch: java.text.ParseException -> L9f
        L93:
            long r8 = r12.getTime()     // Catch: java.text.ParseException -> L9f
            long r0 = r0.getTime()     // Catch: java.text.ParseException -> L9f
            long r8 = r8 - r0
            long r0 = r8 / r2
            goto Lb0
        L9f:
            r12 = move-exception
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.d.o
            java.lang.String r1 = "getExpireTime ParseException."
            com.huawei.hms.framework.common.Logger.w(r0, r1, r12)
            goto Laf
        La8:
            java.lang.String r12 = com.huawei.hms.framework.network.grs.g.d.o
            java.lang.String r0 = "response headers neither contains Cache-Control nor Expires."
            com.huawei.hms.framework.common.Logger.i(r12, r0)
        Laf:
            r0 = r4
        Lb0:
            int r12 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r12 <= 0) goto Lbb
            r4 = 2592000(0x278d00, double:1.280618E-317)
            int r12 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r12 <= 0) goto Lbe
        Lbb:
            r0 = 86400(0x15180, double:4.26873E-319)
        Lbe:
            long r0 = r0 * r2
            java.lang.String r12 = com.huawei.hms.framework.network.grs.g.d.o
            java.lang.Object[] r2 = new java.lang.Object[r7]
            java.lang.Long r3 = java.lang.Long.valueOf(r0)
            r2[r6] = r3
            java.lang.String r3 = "convert expireTime{%s}"
            com.huawei.hms.framework.common.Logger.i(r12, r3, r2)
            long r2 = java.lang.System.currentTimeMillis()
            long r0 = r0 + r2
            java.lang.String r12 = java.lang.String.valueOf(r0)
            r11.c(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.framework.network.grs.g.d.b(java.util.Map):void");
    }

    private void c(int i) {
        this.h = i;
    }

    private void c(long j) {
        this.k = j;
    }

    private void c(String str) {
        this.j = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void c(java.util.Map<java.lang.String, java.lang.String> r6) throws java.lang.NumberFormatException {
        /*
            r5 = this;
            java.lang.String r0 = "Retry-After"
            boolean r1 = r6.containsKey(r0)
            if (r1 == 0) goto L21
            java.lang.Object r6 = r6.get(r0)
            java.lang.String r6 = (java.lang.String) r6
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 != 0) goto L21
            long r0 = java.lang.Long.parseLong(r6)     // Catch: java.lang.NumberFormatException -> L19
            goto L23
        L19:
            r6 = move-exception
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.d.o
            java.lang.String r1 = "getRetryAfter addHeadersToResult NumberFormatException"
            com.huawei.hms.framework.common.Logger.w(r0, r1, r6)
        L21:
            r0 = 0
        L23:
            r2 = 1000(0x3e8, double:4.94E-321)
            long r0 = r0 * r2
            java.lang.String r6 = com.huawei.hms.framework.network.grs.g.d.o
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.Long r3 = java.lang.Long.valueOf(r0)
            r4 = 0
            r2[r4] = r3
            java.lang.String r3 = "convert retry-afterTime{%s}"
            com.huawei.hms.framework.common.Logger.v(r6, r3, r2)
            r5.c(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.framework.network.grs.g.d.c(java.util.Map):void");
    }

    private void d(String str) {
    }

    private void e(String str) {
    }

    private void f(String str) {
        this.g = str;
    }

    private void p() {
        if (m()) {
            Logger.i(o, "GRSSDK get httpcode{304} not any changed.");
            c(1);
            return;
        }
        if (!o()) {
            Logger.i(o, "GRSSDK parse server body all failed.");
            c(2);
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(StringUtils.byte2Str(this.b));
            int i = -1;
            if (jSONObject.has("isSuccess")) {
                if (jSONObject.getInt("isSuccess") == 1) {
                }
            } else if (jSONObject.has("resultCode")) {
                i = jSONObject.getInt("resultCode") == 0 ? 1 : 2;
            } else {
                Logger.e(o, "sth. wrong because server errorcode's key.");
            }
            if (i != 1 && jSONObject.has("services")) {
                i = 0;
            }
            c(i);
            if (i == 1 || i == 0) {
                f(jSONObject.has("services") ? jSONObject.getJSONObject("services").toString() : "");
                e(jSONObject.has("errorList") ? jSONObject.getJSONObject("errorList").toString() : "");
            } else {
                b(jSONObject.has("errorCode") ? jSONObject.getInt("errorCode") : ConnectionResult.RESOLUTION_REQUIRED);
                d(jSONObject.has("errorDesc") ? jSONObject.getString("errorDesc") : "");
            }
        } catch (JSONException e) {
            Logger.w(o, "GrsResponse GrsResponse(String result) JSONException: %s", StringUtils.anonymizeMessage(e.getMessage()));
            c(2);
        }
    }

    private void q() throws NumberFormatException {
        if (o() || n() || m()) {
            Map<String, String> mapR = r();
            if (mapR.size() <= 0) {
                Logger.w(o, "parseHeader {headers.size() <= 0}");
                return;
            }
            try {
                if (o() || m()) {
                    b(mapR);
                    a(mapR);
                }
                if (n()) {
                    c(mapR);
                }
            } catch (JSONException e) {
                Logger.w(o, "parseHeader catch JSONException: %s", StringUtils.anonymizeMessage(e.getMessage()));
            }
        }
    }

    private Map<String, String> r() {
        HashMap map = new HashMap(16);
        Map<String, List<String>> map2 = this.a;
        if (map2 == null || map2.size() <= 0) {
            Logger.v(o, "parseRespHeaders {respHeaders == null} or {respHeaders.size() <= 0}");
            return map;
        }
        for (Map.Entry<String, List<String>> entry : this.a.entrySet()) {
            String key = entry.getKey();
            Iterator<String> it2 = entry.getValue().iterator();
            while (it2.hasNext()) {
                map.put(key, it2.next());
            }
        }
        return map;
    }

    private void s() throws NumberFormatException {
        q();
        p();
    }

    public String a() {
        return this.j;
    }

    public void a(int i) {
    }

    public void a(long j) {
        this.f = j;
    }

    public void a(String str) {
        this.l = str;
    }

    public int b() {
        return this.c;
    }

    public void b(long j) {
        this.e = j;
    }

    public void b(String str) {
        this.n = str;
    }

    public int c() {
        return this.i;
    }

    public Exception d() {
        return this.m;
    }

    public String e() {
        return this.l;
    }

    public int f() {
        return this.h;
    }

    public long g() {
        return this.f;
    }

    public long h() {
        return this.e;
    }

    public long i() {
        return this.d;
    }

    public String j() {
        return this.g;
    }

    public long k() {
        return this.k;
    }

    public String l() {
        return this.n;
    }

    public boolean m() {
        return this.c == 304;
    }

    public boolean n() {
        return this.c == 503;
    }

    public boolean o() {
        return this.c == 200;
    }
}
