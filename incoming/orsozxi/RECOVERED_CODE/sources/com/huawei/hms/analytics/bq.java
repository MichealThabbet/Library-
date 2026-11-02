package com.huawei.hms.analytics;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.internal.ServerProtocol;
import com.huawei.hms.analytics.az;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.framework.config.DeviceAttributeCollector;
import com.huawei.hms.analytics.framework.config.EvtHeaderAttributeCollector;
import com.huawei.hms.analytics.framework.config.ICollectorConfig;
import com.huawei.hms.analytics.framework.config.RomAttributeCollector;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fortuna.ical4j.model.Parameter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class bq implements ICollectorConfig {
    private String ijk;
    private at ikl = ar.lmn().lmn;
    private bv klm;
    private String lmn;

    public bq(String str) {
        this.lmn = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean ikl() {
        /*
            r5 = this;
            com.huawei.hms.analytics.at r0 = r5.ikl
            boolean r0 = r0.def
            r1 = 1
            if (r0 == 0) goto L8
            return r1
        L8:
            r0 = 0
            com.huawei.hms.analytics.at r2 = r5.ikl
            com.huawei.hms.analytics.def r2 = r2.b
            java.lang.String r3 = "OpennessCollectSettings"
            if (r2 == 0) goto L2c
            java.lang.String r2 = r2.lmn()     // Catch: java.lang.Exception -> L28
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Exception -> L28
            if (r4 != 0) goto L25
            java.lang.String r4 = "true"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Exception -> L28
            if (r2 != 0) goto L25
            goto L26
        L25:
            r1 = 0
        L26:
            r0 = r1
            goto L31
        L28:
            java.lang.String r1 = "syncDataSharingStatus error"
            goto L2e
        L2c:
            java.lang.String r1 = "IHiAnalyticsService is null"
        L2e:
            com.huawei.hms.analytics.core.log.HiLog.w(r3, r1)
        L31:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.bq.ikl():boolean");
    }

    private List<JSONObject> klm() throws JSONException {
        JSONObject jSONObject;
        String strKlm = dl.klm(this.ikl.ghi, "global_v2", "camp_evt", "");
        if (TextUtils.isEmpty(strKlm)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(strKlm);
            int length = jSONArray.length();
            if (length == 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                if (!dc.klm(jSONObject2.remove("t"))) {
                    ArrayList<JSONObject> arrayList2 = new dj("$CampaignPushClick", jSONObject2).ikl;
                    jSONObject = arrayList2.size() != 1 ? null : arrayList2.get(0);
                }
                if (jSONObject != null) {
                    arrayList.add(jSONObject);
                }
            }
            return arrayList;
        } catch (JSONException unused) {
            HiLog.w("OpennessCollectSettings", "Camp event is error from sp");
            return null;
        }
    }

    private JSONArray klm(String str) throws JSONException {
        try {
            JSONArray jSONArray = new JSONArray();
            JSONArray jSONArray2 = new JSONArray(str);
            int length = jSONArray2.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray2.getJSONObject(i);
                if (dc.klm(jSONObject.getString("t"))) {
                    HiLog.i("OpennessCollectSettings", "CampInfo has expired");
                } else {
                    jSONArray.put(jSONObject);
                }
            }
            if (jSONArray.length() < length) {
                dl.lmn(this.ikl.ghi, "global_v2", "camp_info", jSONArray.toString());
            }
            return jSONArray;
        } catch (JSONException unused) {
            dl.lmn(this.ikl.ghi, "global_v2", "camp_info");
            return null;
        }
    }

    private int lmn(String str) {
        int iKlm = dl.klm(this.ikl.ghi, "global_v2", "is_new_user", -1);
        if (iKlm == 0) {
            return iKlm;
        }
        try {
            if (!dc.lmn(Long.parseLong(str), System.currentTimeMillis())) {
                return 1;
            }
            dl.lmn(this.ikl.ghi, "global_v2", "is_new_user", 0);
            return 0;
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    private String lmn() {
        JSONArray jSONArrayKlm;
        String strKlm = dl.klm(this.ikl.ghi, "global_v2", "camp_info", "");
        if (TextUtils.isEmpty(strKlm) || (jSONArrayKlm = klm(strKlm)) == null) {
            return "";
        }
        int length = jSONArrayKlm.length();
        for (int i = 0; i < length; i++) {
            try {
                jSONArrayKlm.getJSONObject(i).remove("t");
            } catch (JSONException unused) {
                return "";
            }
        }
        return jSONArrayKlm.toString();
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final String getAppId() {
        return this.ikl.n.klm;
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final String[] getCollectUrls(String str, String str2) {
        ay ayVar = this.ikl.n;
        if (TextUtils.isEmpty(str2)) {
            str2 = this.ikl.n.ghi;
        }
        return (String[]) ayVar.lmn.get(str2).clone();
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final List<JSONObject> getCustomEventJson(long j) throws IllegalAccessException, JSONException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Bundle bundle;
        dj djVar;
        ArrayList<JSONObject> arrayList = null;
        if (!"_openness_config_tag".equals(this.lmn)) {
            return null;
        }
        if (Parameter.CN.equals(this.ikl.n.lmn(this.lmn)) && !dg.ghi(this.ikl.ghi)) {
            if (!ar.lmn().lmn.p) {
                if (this.ikl.w) {
                    cj cjVar = new cj(this.lmn);
                    Context context = ar.lmn().lmn.ghi;
                    String strKlm = (z.lmn() || z.klm()) ? new az.lmn(context).klm() : "";
                    if (TextUtils.isEmpty(strKlm)) {
                        strKlm = ar.lmn().lmn.o;
                    }
                    Object objLmn = new az.ijk(context).lmn();
                    String str = objLmn instanceof String ? (String) objLmn : "";
                    Object objLmn2 = new az.ikl().lmn();
                    String str2 = objLmn2 instanceof String ? (String) objLmn2 : "";
                    Object objLmn3 = new az.klm(context).lmn();
                    String str3 = objLmn3 instanceof String ? (String) objLmn3 : "";
                    if (TextUtils.isEmpty(strKlm) && TextUtils.isEmpty(str) && TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
                        bundle = null;
                    } else {
                        bundle = new Bundle();
                        bundle.putString("$HuaweiMedium", strKlm);
                        bundle.putString("$XiaomiMedium", str3);
                        bundle.putString("$VivoMedium", str);
                        bundle.putString("$OppoMedium", str2);
                    }
                    if (bundle == null) {
                        HiLog.i("pshTken", "all push token is empty");
                        djVar = null;
                    } else {
                        djVar = new dj("$AppPushToken", bundle);
                    }
                    if (djVar != null) {
                        ar.lmn().lmn.p = true;
                        if (cjVar.lmn != null) {
                            cjVar.lmn.lmn("$AppPushToken", djVar.lmn, j);
                        }
                        arrayList = djVar.ikl;
                    }
                } else {
                    HiLog.i("OpennessCollectSettings", "it is not allowed to collect push token");
                }
            }
            List<JSONObject> listKlm = klm();
            if (listKlm == null) {
                return arrayList;
            }
            if (arrayList == null) {
                return listKlm;
            }
            arrayList.addAll(listKlm);
        }
        return arrayList;
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final DeviceAttributeCollector getDeviceAttribute(String str) {
        br brVar = new br();
        brVar.lmn = cy.lmn().klm();
        brVar.klm = this.ikl.h;
        brVar.hij = this.ikl.i;
        brVar.ikl = this.ikl.bcd;
        if (("_openness_config_tag".equals(this.lmn) || "AGC_TAG".equals(this.lmn)) && Parameter.CN.equals(this.ijk)) {
            brVar.ghi = this.ikl.r;
            brVar.fgh = this.ikl.s;
        }
        brVar.ijk = ar.lmn().klm().ikl();
        return brVar;
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final EvtHeaderAttributeCollector getEvtCustomHeader(String str, JSONObject jSONObject) {
        return new bs(jSONObject, this.ijk, this.lmn);
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final Map<String, String> getHttpHeader(String str) {
        Map<String, String> map;
        HashMap map2 = new HashMap();
        if (dg.ghi(this.ikl.ghi)) {
            map2.put("x-hasdk-realtime", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
        }
        map2.put("x-hasdk-productid", this.ikl.n.ikl);
        map2.put("x-hasdk-resourceid", this.ikl.n.ijk);
        map2.put("x-hasdk-token", this.ikl.hij);
        map2.put("x-hasdk-clientid", this.ikl.n.fgh);
        au auVarLmn = ar.lmn().lmn(this.lmn);
        if (auVarLmn != null && (map = auVarLmn.klm) != null && map.size() > 0) {
            map2.putAll(map);
        }
        return map2;
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final String getRegion() {
        return TextUtils.isEmpty(this.ikl.n.lmn(this.lmn)) ? this.ikl.n.ghi : this.ikl.n.lmn(this.lmn);
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final RomAttributeCollector getRomAttribute(String str) {
        if (this.klm == null) {
            this.klm = new bv();
            at atVar = this.ikl;
            String strKlm = atVar.lmn;
            if (TextUtils.isEmpty(strKlm)) {
                strKlm = dg.klm();
                atVar.lmn = strKlm;
            }
            String str2 = Build.MANUFACTURER == null ? "unknown" : Build.MANUFACTURER;
            Point pointDef = dg.def(atVar.ghi);
            int i = pointDef.y;
            int i2 = pointDef.x;
            this.klm.b = str2;
            this.klm.c = i;
            this.klm.d = i2;
            this.klm.lmn = dg.ikl();
            this.klm.hij = atVar.klm;
            this.klm.klm = strKlm;
            this.klm.def = "hianalytics";
            this.klm.cde = "Android";
            this.klm.ghi = "6.6.0.300";
            this.klm.ikl = Build.MODEL != null ? Build.MODEL : "unknown";
            this.klm.bcd = dg.fgh();
            this.klm.abc = dg.ijk();
            this.klm.ijk = atVar.ghi.getPackageName();
            this.klm.e = ("HUAWEI".equals(str2) || "HONOR".equals(str2)) ? dg.lmn(atVar.ghi) : dg.klm(atVar.ghi);
        }
        String strEfg = dg.efg(this.ikl.ghi);
        String strKlm2 = dg.klm(this.ikl.ghi, this.ijk);
        this.klm.f = this.ikl.efg;
        this.klm.a = strEfg;
        this.klm.fgh = strKlm2;
        this.klm.h = this.ikl.g;
        this.klm.i = this.ikl.j;
        this.klm.s = dg.c(this.ikl.ghi);
        if (Parameter.CN.equals(this.ijk)) {
            this.klm.g = Boolean.valueOf(ikl());
        } else {
            this.klm.g = null;
        }
        if (this.ikl.ghi != null) {
            this.klm.j = dg.a(this.ikl.ghi);
            this.klm.k = dg.hij();
            this.klm.l = dg.ghi();
            ActivityManager activityManager = (ActivityManager) this.ikl.ghi.getSystemService("activity");
            if (activityManager != null) {
                ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                activityManager.getMemoryInfo(memoryInfo);
                this.klm.m = dg.lmn(memoryInfo.totalMem);
                this.klm.n = dg.lmn(memoryInfo.availMem);
            }
            this.klm.o = dg.bcd(this.ikl.ghi);
            this.klm.p = dg.abc(this.ikl.ghi);
            this.klm.q = dg.b(this.ikl.ghi);
        }
        String strKlm3 = dl.klm(this.ikl.ghi, "global_v2", "firstRunTime", "");
        this.klm.r = strKlm3;
        this.klm.B = dl.klm(this.ikl.ghi, "global_v2", "sig_flg", 0);
        this.klm.z = lmn(strKlm3);
        this.klm.A = dg.fgh(this.ikl.ghi);
        this.klm.t = dg.d(this.ikl.ghi);
        String str3 = this.ikl.ijk;
        if (TextUtils.isEmpty(str3)) {
            this.klm.efg = this.ikl.lmn();
        } else {
            this.klm.efg = str3;
        }
        if (Parameter.CN.equals(this.ijk)) {
            this.klm.u = Integer.valueOf(dg.e(this.ikl.ghi));
            this.klm.x = Boolean.valueOf(dg.h(this.ikl.ghi));
            this.klm.w = Boolean.valueOf(dg.g(this.ikl.ghi));
            this.klm.v = Integer.valueOf(dg.f(this.ikl.ghi));
            this.klm.y = lmn();
        } else {
            this.klm.u = null;
            this.klm.x = null;
            this.klm.w = null;
            this.klm.v = null;
            this.klm.y = null;
        }
        return this.klm;
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final Event getSpecialEvent(String str) {
        if (!"_openness_config_tag".equals(this.lmn)) {
            return null;
        }
        String strKlm = dl.klm(this.ikl.ghi, "stop_v2_1", "stop_event", "");
        if (TextUtils.isEmpty(strKlm)) {
            return null;
        }
        try {
            dl.lmn(this.ikl.ghi, "stop_v2_1", new String[0]);
            JSONObject jSONObject = new JSONObject(strKlm);
            Event event = new Event();
            event.formJson(jSONObject);
            return event;
        } catch (JSONException unused) {
            HiLog.w("OpennessCollectSettings", "event form json exception");
            return null;
        }
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final void handlerThrowable(Throwable th) {
        di.lmn(th);
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final boolean isDebugModel() {
        return dg.ghi(this.ikl.ghi);
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final boolean isEnableSession(String str) {
        return true;
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final void serviceListener(boolean z, String str) throws InterruptedException {
        if ("_openness_config_tag".equals(this.lmn)) {
            k kVar = ar.lmn().lmn.a;
            if (kVar == null) {
                k kVar2 = new k(fgh.lmn().lmn(this.lmn));
                ar.lmn().lmn.a = kVar2;
                kVar = kVar2;
            }
            if (z) {
                kVar.lmn();
            }
            kVar.lmn(str);
        }
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final void setRegion(String str) {
        this.ijk = str;
    }

    @Override // com.huawei.hms.analytics.framework.config.ICollectorConfig
    public final void syncOaid() {
        af.lmn(this.ikl.ghi, this.ijk);
    }
}
