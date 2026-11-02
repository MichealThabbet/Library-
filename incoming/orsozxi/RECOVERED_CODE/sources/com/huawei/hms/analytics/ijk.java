package com.huawei.hms.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.av;
import com.huawei.hms.analytics.bc;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.framework.HAFrameworkInstance;
import com.huawei.hms.analytics.framework.config.ICollectorConfig;
import com.huawei.hms.analytics.framework.config.RomAttributeCollector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.fortuna.ical4j.model.Parameter;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class ijk implements bh {
    protected final ICollectorConfig ghi;
    protected String hij;
    protected Context ijk;
    bj ikl;
    protected HAFrameworkInstance.Builder klm;
    protected HAFrameworkInstance lmn;

    public class lmn {
        public lmn() {
        }

        public final void lmn() {
            ijk.this.lmn.clearCacheData("oper");
            ijk.this.ijk();
            cy.lmn().ikl(ijk.this.ijk);
            try {
                cy.lmn();
                cy.ijk();
            } catch (Exception unused) {
                HiLog.e("createInstance", "bindService error");
            }
            ar.lmn().ikl().ghi();
            ar.lmn().klm().ghi();
            at atVar = ar.lmn().lmn;
            atVar.bcd = "";
            atVar.e = "";
            atVar.d = "";
            atVar.cde = "";
            atVar.g = "";
            atVar.t = "";
            atVar.s = "";
            atVar.r = "";
            atVar.o = "";
            atVar.p = false;
            atVar.u = "";
            atVar.v = 0L;
            dl.lmn(ijk.this.ijk, "global_v2", "push_token", "ab_info", "w_app_id", "api_channel", "camp_info", "is_new_user", "sig_flg", "is_cs_refr_run");
            ijk.this.lmn.setNeedRefreshSession(true);
            bcd bcdVarLmn = bcd.lmn();
            bcdVarLmn.lmn.clear();
            bcdVarLmn.ikl.lmn();
            if (bcdVarLmn.ijk != null) {
                bcdVarLmn.ijk.lmn();
            }
            bcdVarLmn.ghi = 0L;
            bcdVarLmn.hij = null;
            bcdVarLmn.cde.clear();
            bcdVarLmn.bcd.clear();
            ijk.this.ghi("clearCachedData");
        }
    }

    public ijk(Context context, String str) {
        this.ijk = context;
        ar.lmn().lmn.ghi = context;
        this.hij = str;
        this.ikl = bj.lmn(context);
        this.ghi = new bq(str);
        HAFrameworkInstance.Builder builder = new HAFrameworkInstance.Builder();
        this.klm = builder;
        builder.setStorageHandler(bd.lmn(context)).setStoragePolicy(new bu(str)).setCollectorConfig(this.ghi).setMandatoryParameters(new bt());
    }

    private av efg(String str) {
        av avVarLmn = ar.lmn().lmn.q;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (avVarLmn == null) {
            avVarLmn = ca.lmn(this.ijk);
            if (avVarLmn == null) {
                ca.klm(this.ijk, str, jCurrentTimeMillis);
                return null;
            }
            ar.lmn().lmn.q = avVarLmn;
        }
        if (!avVarLmn.lmn(str)) {
            return null;
        }
        ca.klm(this.ijk, str, jCurrentTimeMillis);
        return avVarLmn;
    }

    private boolean fgh(String str) {
        String strLmn = ar.lmn().lmn.n.lmn(this.hij);
        if (("_openness_config_tag".equals(this.hij) || "AGC_TAG".equals(this.hij)) && Parameter.CN.equals(strLmn)) {
            return lmn(str, strLmn);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ghi(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        dl.lmn(this.ijk, "global_v2", "firstRunTime", String.valueOf(jCurrentTimeMillis));
        Bundle bundle = new Bundle();
        bundle.putString("$StartType", "");
        bundle.putString("$OccurredType", str);
        ikl("$LaunchApp", new dj("$LaunchApp", bundle), System.currentTimeMillis());
        bundle.clear();
        bundle.putString("$StartType", "");
        bundle.putString("$OccurredType", str);
        bundle.putString("$InstallChannel", "");
        ikl("$AppFirstOpen", new dj("$AppFirstOpen", bundle), jCurrentTimeMillis);
    }

    private static boolean hij(String str) {
        if (ba.lmn(str)) {
            return true;
        }
        HiLog.e("createInstance", "Stop report! Instance init failed: ".concat(String.valueOf(str)));
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ijk() {
        if (dl.lmn(this.ijk, "global_v2", "is_log")) {
            try {
                be.lmn().ikl();
            } catch (bc.lmn unused) {
                HiLog.e("createInstance", "log db init failed");
            }
        }
    }

    private static boolean ijk(String str) {
        String str2;
        String str3;
        if (str.length() > 256) {
            str2 = HiLog.ErrorCode.PE002;
            str3 = " Length of eventId exceeds the limit. Max Length is 256.";
        } else {
            if (dc.lmn("eventId", str, am.klm)) {
                return true;
            }
            str2 = HiLog.ErrorCode.PE006;
            str3 = " eventId pattern error";
        }
        HiLog.w("instanceimpl", str2, str3);
        return false;
    }

    private au ikl() {
        au auVarLmn = ar.lmn().lmn(this.hij);
        if (auVarLmn != null) {
            return auVarLmn;
        }
        au auVar = new au();
        ar.lmn().lmn(this.hij, auVar);
        return auVar;
    }

    private void ikl(String str, dj djVar, long j) {
        lmn(str, djVar.lmn, j);
        if (dg.ghi(this.ijk)) {
            this.lmn.onStreamEvent("oper", djVar.ikl, new cl(), j);
        } else {
            if (!fgh(str)) {
                this.lmn.onEventRecord("oper", djVar.ikl, j);
                return;
            }
            cl clVar = new cl();
            clVar.lmn = true;
            this.lmn.onStreamEvent("oper", djVar.ikl, clVar, j);
        }
    }

    private void ikl(List<JSONObject> list) {
        this.lmn.onEvent("oper", list, new cl());
    }

    private boolean klm(String str, dj djVar) {
        String str2;
        String str3;
        String str4;
        if (!"_openness_config_tag".equals(this.hij)) {
            if (str.length() > 256) {
                str2 = "instanceimpl";
                str3 = HiLog.ErrorCode.PE002;
                str4 = " Length of eventId exceeds the limit. Max Length is 256.";
            }
            HiLog.w(str2, str3, str4);
            return true;
        }
        if (!ijk(str)) {
            return true;
        }
        if (djVar.klm(djVar.klm)) {
            return false;
        }
        str2 = "createInstance";
        str3 = HiLog.ErrorCode.PE006;
        str4 = "bundle params is invalid.";
        HiLog.w(str2, str3, str4);
        return true;
    }

    private static boolean lmn(long j, List<av.ikl> list) {
        if (list == null) {
            return false;
        }
        for (av.ikl iklVar : list) {
            if (iklVar != null && j > iklVar.lmn.longValue() && j < iklVar.klm.longValue()) {
                return true;
            }
        }
        return false;
    }

    private boolean lmn(String str, String str2) {
        av avVarEfg = efg(str2);
        if (avVarEfg == null || !avVarEfg.ghi) {
            return false;
        }
        List<av.klm> list = avVarEfg.klm;
        if (list == null) {
            HiLog.d("createInstance", "camp stream evts is empty");
            return false;
        }
        for (av.klm klmVar : list) {
            if (str.equals(klmVar.lmn)) {
                if (lmn(System.currentTimeMillis(), klmVar.klm)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.huawei.hms.analytics.bh
    public final void ikl(String str) {
        if (ar.lmn().lmn.fgh) {
            this.lmn.onReport(str, "oper", new cl());
            return;
        }
        HiLog.w("createInstance", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
        try {
            bd.lmn(this.ijk).deleteByTag(this.hij);
        } catch (bc.lmn unused) {
            HiLog.e("createInstance", "delete db exception");
        }
        if (ar.lmn().lmn.a != null) {
            ar.lmn().lmn.a.lmn();
        }
    }

    public final void ikl(boolean z) {
        ar.lmn().lmn.v = 0L;
        this.lmn.clearCacheData("oper");
        ijk();
        dl.lmn(this.ijk, "global_v2", "is_new_user");
        this.lmn.setNeedRefreshSession(true);
        if (z) {
            ghi("aaidReset");
        }
        try {
            cy.lmn();
            cy.ijk();
        } catch (Exception unused) {
            HiLog.e("createInstance", "bindService error");
        }
    }

    @Override // com.huawei.hms.analytics.bh
    public final void klm() {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("createInstance", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
        } else if (hij(this.hij) && dl.lmn(this.ijk, "global_v2", "is_log")) {
            cx.ijk().lmn(new cp(ar.lmn().lmn.n.ghi, false));
        }
    }

    @Override // com.huawei.hms.analytics.bh
    public final void klm(long j) {
        this.lmn.onBackground(j);
    }

    @Override // com.huawei.hms.analytics.bh
    public final void klm(String str) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("createInstance", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
        } else if (hij(this.hij) && dl.lmn(this.ijk, "global_v2", "is_log")) {
            cx.ijk().lmn(new cp(str, true));
        }
    }

    public final void klm(String str, dj djVar, long j) {
        bj.klm().lmn(this.hij, "oper", str, djVar.lmn);
        bj.lmn().lmn(str, djVar.lmn);
        cl clVar = new cl();
        clVar.lmn = true;
        this.lmn.onStreamEvent("oper", djVar.ikl, clVar, j);
    }

    @Override // com.huawei.hms.analytics.bh
    public final void klm(List<Event> list) {
        this.lmn.onEvent(list, new cl());
    }

    public final void klm(boolean z) {
        ikl().lmn = z;
    }

    public Map<String, String> lmn(boolean z) {
        Map<String, String> map = new HashMap<>();
        try {
            if (z) {
                RomAttributeCollector romAttribute = this.ghi.getRomAttribute("oper");
                if (romAttribute != null) {
                    map = dd.lmn(romAttribute.doCollector());
                }
            } else {
                map = dd.klm(ar.lmn().klm().klm());
            }
        } catch (Throwable th) {
            HiLog.w("createInstance", "get rom attribute exception");
            di.lmn(th);
        }
        return map;
    }

    @Override // com.huawei.hms.analytics.bh
    public final void lmn() {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("createInstance", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
        } else if (hij(this.hij)) {
            this.lmn.onReport("oper", new cl());
        }
    }

    @Override // com.huawei.hms.analytics.bh
    public final void lmn(long j) {
        this.lmn.onForeground(j);
    }

    public final void lmn(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        HashMap map = new HashMap();
        map.put("ha-sdk-service", str);
        ikl().klm = map;
    }

    @Override // com.huawei.hms.analytics.bh
    public final void lmn(String str, Bundle bundle, long j) {
        if (dg.ghi(this.ijk)) {
            bj.klm().lmn(this.hij, "oper", str, bundle, j);
        } else {
            bj.klm().lmn(this.hij, "oper", str, bundle);
        }
        bj.lmn().lmn(str, bundle);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0055, code lost:
    
        com.huawei.hms.analytics.core.log.HiLog.w("ABTskIdHolder", "Duplicate AB attributes exist in the cache.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x005a, code lost:
    
        r8 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void lmn(java.lang.String r13, com.huawei.hms.analytics.dj r14) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 363
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.ijk.lmn(java.lang.String, com.huawei.hms.analytics.dj):void");
    }

    @Override // com.huawei.hms.analytics.bh
    public final void lmn(String str, dj djVar, long j) {
        if (dg.ghi(this.ijk)) {
            bj.klm().lmn(this.hij, "oper", str, djVar.lmn, j);
            bj.lmn().lmn(str, djVar.lmn);
            this.lmn.onStreamEvent("oper", djVar.ikl, new cl(), j);
            return;
        }
        bj.klm().lmn(this.hij, "oper", str, djVar.lmn);
        bj.lmn().lmn(str, djVar.lmn);
        if (!fgh(str)) {
            this.lmn.onEvent("oper", djVar.ikl, new cl(), j);
            return;
        }
        cl clVar = new cl();
        clVar.lmn = true;
        this.lmn.onStreamEvent("oper", djVar.ikl, clVar, j);
    }

    @Override // com.huawei.hms.analytics.bh
    public final void lmn(String str, List<JSONObject> list) {
        if (fgh(str)) {
            lmn(list);
        } else {
            ikl(list);
        }
    }

    @Override // com.huawei.hms.analytics.bh
    public final void lmn(String str, Map<String, dj> map) {
        Set<Map.Entry<String, dj>> setEntrySet = map.entrySet();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, dj> entry : setEntrySet) {
            String key = entry.getKey();
            dj value = entry.getValue();
            bj.klm().lmn(this.hij, "oper", key, value.lmn);
            bj.lmn().lmn(key, value.lmn);
            arrayList.addAll(value.ikl);
        }
        this.lmn.onReport(str, "oper", arrayList, new cl());
    }

    @Override // com.huawei.hms.analytics.bh
    public final void lmn(List<JSONObject> list) {
        this.lmn.onStreamEvent("oper", list, new cl());
    }
}
