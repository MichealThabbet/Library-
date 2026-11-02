package com.huawei.hms.framework.network.grs;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.network.grs.g.h;
import com.huawei.hms.framework.network.grs.local.model.CountryCodeBean;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class a {
    private static final String e = "a";
    private final GrsBaseInfo a;
    private com.huawei.hms.framework.network.grs.e.a b;
    private h c;
    private com.huawei.hms.framework.network.grs.e.c d;

    /* renamed from: com.huawei.hms.framework.network.grs.a$a, reason: collision with other inner class name */
    private static class C0056a implements com.huawei.hms.framework.network.grs.b {
        String a;
        Map<String, String> b;
        IQueryUrlsCallBack c;
        Context d;
        GrsBaseInfo e;
        com.huawei.hms.framework.network.grs.e.a f;

        C0056a(String str, Map<String, String> map, IQueryUrlsCallBack iQueryUrlsCallBack, Context context, GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.a aVar) {
            this.a = str;
            this.b = map;
            this.c = iQueryUrlsCallBack;
            this.d = context;
            this.e = grsBaseInfo;
            this.f = aVar;
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a() {
            Map<String, String> map = this.b;
            if (map != null && !map.isEmpty()) {
                Logger.i(a.e, "get expired cache localUrls");
                this.c.onCallBackSuccess(this.b);
            } else {
                if (this.b != null) {
                    this.c.onCallBackFail(-3);
                    return;
                }
                Logger.i(a.e, "access local config for return a domain.");
                this.c.onCallBackSuccess(com.huawei.hms.framework.network.grs.f.b.a(this.d.getPackageName(), this.e).a(this.d, this.f, this.e, this.a, true));
            }
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a(com.huawei.hms.framework.network.grs.g.d dVar) {
            Map<String, String> mapA = a.a(dVar.j(), this.a);
            if (mapA.isEmpty()) {
                Map<String, String> map = this.b;
                if (map != null && !map.isEmpty()) {
                    Logger.i(a.e, "get expired cache localUrls");
                    this.c.onCallBackSuccess(this.b);
                    return;
                } else if (this.b != null) {
                    this.c.onCallBackFail(-5);
                    return;
                } else {
                    Logger.i(a.e, "access local config for return a domain.");
                    mapA = com.huawei.hms.framework.network.grs.f.b.a(this.d.getPackageName(), this.e).a(this.d, this.f, this.e, this.a, true);
                }
            } else {
                Logger.i(a.e, "get url is from remote server");
            }
            this.c.onCallBackSuccess(mapA);
        }
    }

    private static class b implements com.huawei.hms.framework.network.grs.b {
        String a;
        String b;
        IQueryUrlCallBack c;
        String d;
        Context e;
        GrsBaseInfo f;
        com.huawei.hms.framework.network.grs.e.a g;

        b(String str, String str2, IQueryUrlCallBack iQueryUrlCallBack, String str3, Context context, GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.a aVar) {
            this.a = str;
            this.b = str2;
            this.c = iQueryUrlCallBack;
            this.d = str3;
            this.e = context;
            this.f = grsBaseInfo;
            this.g = aVar;
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a() {
            if (!TextUtils.isEmpty(this.d)) {
                Logger.i(a.e, "get expired cache localUrl");
                this.c.onCallBackSuccess(this.d);
            } else {
                if (!TextUtils.isEmpty(this.d)) {
                    this.c.onCallBackFail(-3);
                    return;
                }
                Logger.i(a.e, "access local config for return a domain.");
                this.c.onCallBackSuccess(com.huawei.hms.framework.network.grs.f.b.a(this.e.getPackageName(), this.f).a(this.e, this.g, this.f, this.a, this.b, true));
            }
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a(com.huawei.hms.framework.network.grs.g.d dVar) {
            String strA;
            IQueryUrlCallBack iQueryUrlCallBack;
            Map<String, String> mapA = a.a(dVar.j(), this.a);
            if (mapA.containsKey(this.b)) {
                Logger.i(a.e, "get url is from remote server");
                iQueryUrlCallBack = this.c;
                strA = mapA.get(this.b);
            } else if (!TextUtils.isEmpty(this.d)) {
                Logger.i(a.e, "get expired cache localUrl");
                this.c.onCallBackSuccess(this.d);
                return;
            } else if (!TextUtils.isEmpty(this.d)) {
                this.c.onCallBackFail(-5);
                return;
            } else {
                Logger.i(a.e, "access local config for return a domain.");
                strA = com.huawei.hms.framework.network.grs.f.b.a(this.e.getPackageName(), this.f).a(this.e, this.g, this.f, this.a, this.b, true);
                iQueryUrlCallBack = this.c;
            }
            iQueryUrlCallBack.onCallBackSuccess(strA);
        }
    }

    public a(GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.a aVar, h hVar, com.huawei.hms.framework.network.grs.e.c cVar) {
        this.a = grsBaseInfo;
        this.b = aVar;
        this.c = hVar;
        this.d = cVar;
    }

    public static CountryCodeBean a(Context context, boolean z) {
        return new CountryCodeBean(context, z);
    }

    public static Map<String, Map<String, String>> a(String str) throws JSONException {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(16);
        if (TextUtils.isEmpty(str)) {
            Logger.v(e, "isSpExpire jsonValue is null.");
            return concurrentHashMap;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                if (!TextUtils.isEmpty(next)) {
                    concurrentHashMap.put(next, a(jSONObject2));
                }
            }
            return concurrentHashMap;
        } catch (JSONException e2) {
            Logger.w(e, "getServicesUrlsMap occur a JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return concurrentHashMap;
        }
    }

    private Map<String, String> a(String str, com.huawei.hms.framework.network.grs.e.b bVar, Context context) {
        Map<String, String> mapA = this.b.a(this.a, str, bVar, context);
        if (mapA == null || mapA.isEmpty()) {
            Map<String, String> mapA2 = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName(), this.a).a(context, this.b, this.a, str, false);
            return mapA2 != null ? mapA2 : new HashMap();
        }
        com.huawei.hms.framework.network.grs.f.b.a(context, this.a);
        return mapA;
    }

    public static Map<String, String> a(String str, String str2) {
        HashMap map = new HashMap();
        if (TextUtils.isEmpty(str)) {
            Logger.w(e, "isSpExpire jsonValue from server is null.");
            return map;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.has(str2) ? jSONObject.getJSONObject(str2) : null;
            if (jSONObject2 == null) {
                Logger.w(e, "getServiceNameUrls: paser null from server json data by {%s}.", str2);
                return map;
            }
            Iterator<String> itKeys = jSONObject2.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject2.get(next).toString());
            }
            return map;
        } catch (JSONException e2) {
            Logger.w(e, "Method{getServiceNameUrls} query url from SP occur an JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return map;
        }
    }

    public static Map<String, String> a(JSONObject jSONObject) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(16);
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String string = jSONObject.get(next).toString();
                if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(string)) {
                    concurrentHashMap.put(next, string);
                }
            }
            return concurrentHashMap;
        } catch (JSONException e2) {
            Logger.w(e, "getServiceUrls occur a JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return concurrentHashMap;
        }
    }

    public String a(Context context, String str) {
        com.huawei.hms.framework.network.grs.g.d dVarA = this.c.a(new com.huawei.hms.framework.network.grs.g.k.c(this.a, context), str, this.d);
        return dVarA == null ? "" : dVarA.m() ? this.b.a().a(this.a.getGrsParasKey(true, true, context), "") : dVarA.j();
    }

    public String a(String str, String str2, Context context) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        String strA = a(str, bVar, context).get(str2);
        if (bVar.a() && !TextUtils.isEmpty(strA)) {
            Logger.i(e, "get unexpired cache localUrl: %s", StringUtils.anonymizeMessage(strA));
            com.huawei.hms.framework.network.grs.f.b.a(context, this.a);
            return strA;
        }
        String str3 = a(a(context, str), str).get(str2);
        if (!TextUtils.isEmpty(str3)) {
            Logger.i(e, "get url is from remote server");
            com.huawei.hms.framework.network.grs.f.b.a(context, this.a);
            return str3;
        }
        if (TextUtils.isEmpty(strA)) {
            Logger.i(e, "access local config for return a domain.");
            strA = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName(), this.a).a(context, this.b, this.a, str, str2, true);
        } else {
            Logger.i(e, "get expired cache localUrl");
        }
        Logger.i(e, "synGetGrsUrl: %s", StringUtils.anonymizeMessage(strA));
        return strA;
    }

    public Map<String, String> a(String str, Context context) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        Map<String, String> mapA = a(str, bVar, context);
        if (bVar.a() && !mapA.isEmpty()) {
            Logger.i(e, "get unexpired cache localUrls: %s", StringUtils.anonymizeMessage(new JSONObject(mapA).toString()));
            com.huawei.hms.framework.network.grs.f.b.a(context, this.a);
            return mapA;
        }
        Map<String, String> mapA2 = a(a(context, str), str);
        if (!mapA2.isEmpty()) {
            Logger.i(e, "get url is from remote server");
            com.huawei.hms.framework.network.grs.f.b.a(context, this.a);
            return mapA2;
        }
        if (mapA.isEmpty()) {
            Logger.i(e, "access local config for return a domain.");
            mapA = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName(), this.a).a(context, this.b, this.a, str, true);
        } else {
            Logger.i(e, "get expired cache localUrls");
        }
        String str2 = e;
        Object[] objArr = new Object[1];
        objArr[0] = StringUtils.anonymizeMessage(mapA != null ? new JSONObject(mapA).toString() : "");
        Logger.i(str2, "synGetGrsUrls: %s", objArr);
        return mapA;
    }

    public void a(String str, IQueryUrlsCallBack iQueryUrlsCallBack, Context context) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        Map<String, String> mapA = a(str, bVar, context);
        if (!bVar.a()) {
            this.c.a(new com.huawei.hms.framework.network.grs.g.k.c(this.a, context), new C0056a(str, mapA, iQueryUrlsCallBack, context, this.a, this.b), str, this.d);
            return;
        }
        Logger.i(e, "get unexpired cache localUrls");
        if (mapA.isEmpty()) {
            iQueryUrlsCallBack.onCallBackFail(-5);
            return;
        }
        com.huawei.hms.framework.network.grs.f.b.a(context, this.a);
        Logger.i(e, "ayncGetGrsUrls: %s", StringUtils.anonymizeMessage(new JSONObject(mapA).toString()));
        iQueryUrlsCallBack.onCallBackSuccess(mapA);
    }

    public void a(String str, String str2, IQueryUrlCallBack iQueryUrlCallBack, Context context) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        String str3 = a(str, bVar, context).get(str2);
        if (!bVar.a()) {
            this.c.a(new com.huawei.hms.framework.network.grs.g.k.c(this.a, context), new b(str, str2, iQueryUrlCallBack, str3, context, this.a, this.b), str, this.d);
            return;
        }
        Logger.i(e, "get unexpired cache localUrl");
        if (TextUtils.isEmpty(str3)) {
            iQueryUrlCallBack.onCallBackFail(-5);
            return;
        }
        com.huawei.hms.framework.network.grs.f.b.a(context, this.a);
        Logger.i(e, "ayncGetGrsUrl: %s", StringUtils.anonymizeMessage(str3));
        iQueryUrlCallBack.onCallBackSuccess(str3);
    }
}
