package com.huawei.hms.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.analytics.bcd;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.g;
import com.huawei.hms.analytics.ijk;
import com.huawei.hms.analytics.type.HALogConfig;
import com.huawei.hms.analytics.type.HAParamType;
import com.huawei.hms.analytics.type.ReportPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.util.Dates;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ghi extends ijk implements bi {
    private g fgh;

    public ghi(Context context) {
        super(context, "_openness_config_tag");
        this.lmn = this.klm.build(this.hij);
    }

    private void def() {
        dl.lmn(this.ijk, "stop_v2_1", "stop_event");
        dj djVar = new dj("$StopAnalyticsCollection", new Bundle());
        cl clVar = new cl();
        clVar.setStopEventReport(true);
        this.lmn.onStreamEvent("oper", djVar.ikl, clVar);
    }

    private void def(boolean z) {
        if (Parameter.CN.equals(ar.lmn().lmn.n.lmn(this.hij))) {
            ar.lmn().lmn.def = z;
            dl.lmn(this.ijk, "global_v2", "is_restriction_shared", z);
        }
    }

    private void efg() {
        if (this.fgh == null) {
            this.fgh = new g(this.ijk);
        }
        try {
            this.fgh.lmn.lmn().addOnFailureListener(new g.lmn()).addOnSuccessListener(new g.klm());
        } catch (Exception unused) {
            HiLog.w("instanceimpl", "observableManager Exception");
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void bcd(String str) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setCustomReferrer");
        if (!dc.lmn(str, 256)) {
            HiLog.w("instanceimpl", "Custom referrer check failed");
            cd.lmn(ccVarLmn);
        } else {
            new cs(this).lmn(new cr(str));
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void cde(String str) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setChannel(String))");
        at atVar = ar.lmn().lmn;
        if (!TextUtils.isEmpty(atVar.ijk)) {
            HiLog.w("instanceimpl", "channel already exists in manifest file");
            cd.lmn(ccVarLmn);
        } else {
            if (!TextUtils.isEmpty(atVar.lmn())) {
                HiLog.w("instanceimpl", "duplicate set,channel already exists");
                cd.lmn(ccVarLmn);
                return;
            }
            String strLmn = dg.lmn(str);
            if (!TextUtils.isEmpty(strLmn)) {
                dl.lmn(this.ijk, "global_v2", "api_channel", strLmn);
                atVar.u = strLmn;
                ccVarLmn.lmn = "0";
            }
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void def(String str) {
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setPushToken(String)");
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        if (!ar.lmn().lmn.w) {
            HiLog.w("instanceimpl", "push token switch is disabled");
            cd.lmn(ccVarLmn);
            return;
        }
        if (!dc.lmn(str, 256)) {
            HiLog.w("instanceimpl", "push token check failed");
            cd.lmn(ccVarLmn);
            return;
        }
        String str2 = ar.lmn().lmn.o;
        if (!TextUtils.isEmpty(str2) && !str2.equals(str)) {
            ar.lmn().lmn.p = false;
        }
        ar.lmn().lmn.o = str;
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
    }

    @Override // com.huawei.hms.analytics.bi
    public final void efg(String str) {
        String str2;
        String str3;
        String str4;
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setUserId(String)");
        Context context = this.ijk;
        if (str == null) {
            dr.lmn(context, 0);
        } else {
            if (str.isEmpty()) {
                str2 = HiLog.ErrorCode.PE001;
                str3 = "UserId is empty";
            } else {
                if (str.length() <= 256) {
                    dr.lmn(context, 1);
                    str4 = ar.lmn().lmn.bcd;
                    if (!TextUtils.isEmpty(str4) && !str4.equals(str)) {
                        this.lmn.setNeedRefreshSession(true);
                    }
                    ar.lmn().lmn.bcd = str;
                    ccVarLmn.lmn = "0";
                    cd.lmn(ccVarLmn);
                }
                str2 = HiLog.ErrorCode.PE002;
                str3 = "Length of userId exceeds the limit";
            }
            HiLog.w("UserIdHandler", str2, str3);
        }
        str = "";
        str4 = ar.lmn().lmn.bcd;
        if (!TextUtils.isEmpty(str4)) {
            this.lmn.setNeedRefreshSession(true);
        }
        ar.lmn().lmn.bcd = str;
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
    }

    @Override // com.huawei.hms.analytics.bi
    public final void efg(boolean z) {
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setPushTokenCollectionEnabled(boolean))");
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        at atVar = ar.lmn().lmn;
        if (!Parameter.CN.equals(atVar.n.lmn(this.hij))) {
            HiLog.w("instanceimpl", "only available in China region");
            cd.lmn(ccVarLmn);
        } else {
            dl.lmn(this.ijk, "global_v2", "push_token_collection_enable", z);
            atVar.w = z;
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final Task<String> fgh() {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return null;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#getAAID()");
        try {
            Task<String> taskIkl = cy.lmn().ikl();
            ccVarLmn.lmn = "0";
            return taskIkl;
        } finally {
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void fgh(String str) {
        String str2;
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#pageEnd(String)");
        if (!dc.lmn(str, 256)) {
            HiLog.w("instanceimpl", "pageEnd: pageName check failed");
            cd.lmn(ccVarLmn);
            return;
        }
        bcd bcdVarLmn = bcd.lmn();
        long jCurrentTimeMillis = System.currentTimeMillis();
        HiLog.d("ActivityStatCommander", "onPageExit " + str + " with time: " + jCurrentTimeMillis);
        bcd.lmn lmnVar = bcdVarLmn.lmn.get(str);
        if (lmnVar == null) {
            str2 = "onPageEnter should be invoked first.";
        } else {
            long j = lmnVar.ijk;
            long j2 = 0;
            if (j != 0) {
                bcdVarLmn.hij = lmnVar;
                bcdVarLmn.ghi = jCurrentTimeMillis;
                Bundle bundle = new Bundle();
                bundle.putString("$CurActivityName", lmnVar.lmn);
                bundle.putString("$CurActivityClass", lmnVar.klm);
                bundle.putString("$CurActivityId", lmnVar.ikl);
                bundle.putString("$PrePageId", bcdVarLmn.bcd.getString("$PrevActivityClass", ""));
                bundle.putString("$CurrPageId", lmnVar.klm);
                HiLog.d("ActivityStatCommander", "onPageExit duration cal: 1. " + jCurrentTimeMillis + " 2. " + j);
                bundle.putString(HAParamType.DURATION, String.valueOf(Math.min(jCurrentTimeMillis - j, Dates.MILLIS_PER_WEEK)));
                HiLog.i("ActivityStatCommander", "onPageExit: send ScreenExit event...");
                bcdVarLmn.def.lmn("$ExitScreen", new dj("$ExitScreen", bundle), jCurrentTimeMillis);
                if (bcdVarLmn.ijk.lmn.equals(str)) {
                    if (bcdVarLmn.lmn.size() > 1) {
                        for (Map.Entry<String, bcd.lmn> entry : bcdVarLmn.lmn.entrySet()) {
                            String key = entry.getKey();
                            bcd.lmn lmnVar2 = bcdVarLmn.lmn.get(key);
                            if (!str.equals(key) && lmnVar2 != null && lmnVar2.ijk > j2) {
                                long j3 = lmnVar2.ijk;
                                bcdVarLmn.ijk = bcdVarLmn.lmn.get(entry.getKey());
                                j2 = j3;
                            }
                        }
                    } else {
                        bcdVarLmn.ijk = null;
                    }
                }
                bcdVarLmn.lmn.remove(str);
                ccVarLmn.lmn = "0";
                cd.lmn(ccVarLmn);
            }
            str2 = "timestamp is 0.";
        }
        HiLog.w("ActivityStatCommander", str2);
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
    }

    @Override // com.huawei.hms.analytics.bi
    public final void fgh(boolean z) {
        if (ar.lmn().lmn.fgh == z) {
            HiLog.w("instanceimpl", "The collection status is the same. isEnableAnalytics: ".concat(String.valueOf(z)));
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setAnalyticsEnabled(boolean)");
        ar.lmn().lmn.fgh = z;
        dl.lmn(this.ijk, "global_v2", "is_analytics_enabled", z);
        if (!dl.lmn(this.ijk, "global_v2", "is_restriction_enabled")) {
            ar.lmn().lmn.efg = !z;
            if (!z) {
                def();
                def(true);
            }
        } else if (!z && !ar.lmn().lmn.efg) {
            ar.lmn().lmn.efg = true;
            dl.lmn(this.ijk, "global_v2", "is_restriction_enabled", true);
            def();
            def(true);
        } else if (!z && ar.lmn().lmn.efg) {
            HiLog.w("instanceimpl", "The stop event has been reported.");
        }
        efg();
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
        HiLog.i("instanceimpl", "The collection status is the differ. isEnableAnalytics: ".concat(String.valueOf(z)));
        HashMap map = new HashMap();
        map.put("analyticsEnabled", Boolean.valueOf(z));
        bj.lmn().lmn(map);
    }

    @Override // com.huawei.hms.analytics.bi
    public final void ghi() {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#clearCachedData()");
        try {
            new ijk.lmn().lmn();
            ccVarLmn.lmn = "0";
        } catch (Exception e) {
            HiLog.w("instanceimpl", "reset analytics exception: " + e.getMessage());
        }
        cd.lmn(ccVarLmn);
    }

    @Override // com.huawei.hms.analytics.bi
    public final void ghi(String str) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        if (!Parameter.CN.equals(ar.lmn().lmn.n.lmn(this.hij))) {
            HiLog.w("instanceimpl", "Region is not CN. This api does not take effect");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setWXAppId");
        if (!dc.lmn(str, 256)) {
            ar.lmn().lmn.t = "";
            dl.lmn(this.ijk, "global_v2", "w_app_id", "");
            cd.lmn(ccVarLmn);
        } else {
            ar.lmn().lmn.t = str;
            dl.lmn(this.ijk, "global_v2", "w_app_id", str);
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void ghi(boolean z) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        boolean z2 = ar.lmn().lmn.efg;
        ar.lmn().lmn.efg = z;
        dl.lmn(this.ijk, "global_v2", "is_restriction_enabled", z);
        if (z == z2) {
            HiLog.w("instanceimpl", "The enabled status is the same. isRestriction: ".concat(String.valueOf(z)));
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setRestrictionEnabled(boolean)");
        if (z) {
            def();
            def(z);
        }
        efg();
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
    }

    @Override // com.huawei.hms.analytics.bi
    public final void hij(String str) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        if (!Parameter.CN.equals(ar.lmn().lmn.n.lmn(this.hij))) {
            HiLog.w("instanceimpl", "Region is not CN. This api does not take effect");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setWXOpenId");
        if (!dc.lmn(str, 256)) {
            ar.lmn().lmn.s = "";
            cd.lmn(ccVarLmn);
        } else {
            ar.lmn().lmn.s = str;
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void hij(boolean z) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setRestrictionShared");
        try {
            at atVar = ar.lmn().lmn;
            if (!Parameter.CN.equals(atVar.n.lmn(this.hij))) {
                HiLog.w("instanceimpl", "Region is not CN. This api does not take effect");
                return;
            }
            if (atVar.efg) {
                HiLog.w("instanceimpl", "Restriction enabled, setRestrictionShared failed");
            } else {
                if (z == atVar.def) {
                    HiLog.w("instanceimpl", "The enabled status is the same.");
                    return;
                }
                atVar.def = z;
                efg();
                dl.lmn(this.ijk, "global_v2", "is_restriction_shared", z);
            }
        } finally {
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final boolean hij() {
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#isRestrictionShared");
        try {
            at atVar = ar.lmn().lmn;
            if (Parameter.CN.equals(atVar.n.lmn(this.hij))) {
                return atVar.def;
            }
            HiLog.w("instanceimpl", "Region is not CN. This api does not take effect");
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
            return true;
        } finally {
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void ijk(long j) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setSessionDuration(long)");
        this.lmn.setSessionTimeoutDuration(dc.lmn("sessionDuration", j));
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
    }

    @Override // com.huawei.hms.analytics.bi
    public final void ijk(String str) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        if (!Parameter.CN.equals(ar.lmn().lmn.n.lmn(this.hij))) {
            HiLog.w("instanceimpl", "Region is not CN. This api does not take effect");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setWXUnionId");
        if (!dc.lmn(str, 256)) {
            ar.lmn().lmn.r = "";
            cd.lmn(ccVarLmn);
        } else {
            ar.lmn().lmn.r = str;
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void ijk(boolean z) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setCollectAdsIdEnabled");
        if (ar.lmn().lmn.k != z) {
            ar.lmn().lmn.k = z;
            dl.lmn(this.ijk, "global_v2", "is_enabled_adsid", z);
        }
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
    }

    @Override // com.huawei.hms.analytics.bi
    public final boolean ijk() {
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#isRestrictionEnabled");
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
        return ar.lmn().lmn.efg;
    }

    public final void ikl() {
        if (dg.ghi(this.ijk) || !dl.klm(this.ijk, "global_v2", "isFirstRun", false)) {
            HiLog.i("instanceimpl", "start to report referrer");
            cx.ikl().lmn(new ck(this.ijk, this));
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void ikl(long j) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setMinActivitySessions(long)");
        this.lmn.setMinSessionDuration(dc.lmn("minActivitySessions", j));
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
    }

    @Override // com.huawei.hms.analytics.bi
    public final void klm(String str, String str2) {
        bcd.lmn lmnVar;
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#pageStart(String,String)");
        if (!dc.lmn(str, 256) || !dc.lmn(str2, 256)) {
            HiLog.w("instanceimpl", "pageStart: pageName or pageClassOverride check failed");
            cd.lmn(ccVarLmn);
            return;
        }
        if (bcd.lmn().ikl.lmn.equals(str)) {
            HiLog.w("instanceimpl", "pageStart: pageName cannot be equals to previous pageName");
            cd.lmn(ccVarLmn);
            return;
        }
        bcd bcdVarLmn = bcd.lmn();
        Bundle bundle = new Bundle();
        long jCurrentTimeMillis = System.currentTimeMillis();
        HiLog.d("ActivityStatCommander", "onScreenEnter with time: ".concat(String.valueOf(jCurrentTimeMillis)));
        bcd.lmn lmnVar2 = new bcd.lmn(str, str2, "", jCurrentTimeMillis);
        bcdVarLmn.lmn.put(str, lmnVar2);
        HiLog.d("ActivityStatCommander", "onPageEnterSend");
        if (bcdVarLmn.ijk == null) {
            bundle.putString("$PrevActivityName", bcdVarLmn.ikl.lmn);
            bundle.putString("$PrevActivityClass", bcdVarLmn.ikl.klm);
            bundle.putString("$PrevActivityId", bcdVarLmn.ikl.ikl);
            bcdVarLmn.bcd.putString("$PrevActivityClass", bcdVarLmn.ikl.klm);
            lmnVar = bcdVarLmn.ikl;
        } else {
            bundle.putString("$PrevActivityName", bcdVarLmn.ijk.lmn);
            bundle.putString("$PrevActivityClass", bcdVarLmn.ijk.klm);
            bundle.putString("$PrevActivityId", bcdVarLmn.ijk.ikl);
            bcdVarLmn.bcd.putString("$PrevActivityClass", bcdVarLmn.ijk.klm);
            lmnVar = bcdVarLmn.ijk;
        }
        bundle.putString("$PrePageId", lmnVar.klm);
        bundle.putString("$CurActivityName", lmnVar2.lmn);
        bundle.putString("$CurActivityClass", lmnVar2.klm);
        bundle.putString("$CurActivityId", lmnVar2.ikl);
        bcdVarLmn.bcd.putString("$CurActivityClass", lmnVar2.klm);
        bundle.putString("$CurrPageId", lmnVar2.klm);
        bcdVarLmn.ijk = lmnVar2;
        bcdVarLmn.def.lmn("$EnterScreen", new dj("$EnterScreen", bundle), jCurrentTimeMillis);
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
    }

    @Override // com.huawei.hms.analytics.ijk, com.huawei.hms.analytics.bi
    public final Map<String, String> lmn(boolean z) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return new HashMap();
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#getUserProfiles(boolean)");
        Map<String, String> mapLmn = super.lmn(z);
        try {
            if (mapLmn == null) {
                return new HashMap();
            }
            ccVarLmn.lmn = "0";
            if ("_openness_config_tag".equals(this.hij)) {
                cd.lmn(ccVarLmn);
            }
            return mapLmn;
        } finally {
            if ("_openness_config_tag".equals(this.hij)) {
                cd.lmn(ccVarLmn);
            }
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void lmn(Activity activity, String str, String str2) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setCurrentActivity(Activity,String,String))");
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (activity != null && !TextUtils.isEmpty(str)) {
                if (TextUtils.isEmpty(str2)) {
                    str2 = activity.getClass().getCanonicalName();
                }
                String str3 = str2;
                if (dc.lmn(str, 256)) {
                    if (dc.lmn(str3, 256)) {
                        bcd.lmn().lmn(jCurrentTimeMillis);
                        Bundle bundle = new Bundle();
                        bcd bcdVarLmn = bcd.lmn();
                        HiLog.d("ActivityStatCommander", "onScreenEnter with time: ".concat(String.valueOf(jCurrentTimeMillis)));
                        bcdVarLmn.lmn(new bcd.lmn(str, str3, String.valueOf(activity.getTaskId()), jCurrentTimeMillis), bundle, jCurrentTimeMillis);
                        ccVarLmn.lmn = "0";
                        return;
                    }
                    return;
                }
                return;
            }
            HiLog.w("instanceimpl", HiLog.ErrorCode.PE001, " activity is null,or screenName is null");
        } finally {
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void lmn(Intent intent) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#onNewIntent");
        if (intent == null) {
            HiLog.w("instanceimpl", "new intent is null");
            cd.lmn(ccVarLmn);
        } else {
            f.lmn(this.ijk, new bw(intent.getExtras()));
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void lmn(Bundle bundle) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#addDefaultEventParameters(Bundle)");
        try {
            ar.lmn().ikl().lmn(bundle);
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        } catch (Exception unused) {
            cd.lmn(ccVarLmn);
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void lmn(HALogConfig hALogConfig, String str) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#writeLog");
        String region = hALogConfig.getRegion();
        String projectId = hALogConfig.getProjectId();
        String logGroupId = hALogConfig.getLogGroupId();
        String logStreamId = hALogConfig.getLogStreamId();
        if (!dc.lmn(region, 128) || !dc.lmn(projectId, 128) || !dc.lmn(logGroupId, 128) || !dc.lmn(logStreamId, 128)) {
            HiLog.e("instanceimpl", "The logConfigure parameter is invalid.");
            cd.lmn(ccVarLmn);
            return;
        }
        if (!dc.lmn(str, 4096)) {
            str = str.substring(0, 4096);
        }
        hALogConfig.setTags(dc.lmn(hALogConfig.getTags()));
        cx.ijk().lmn(new co(hALogConfig, "$WriteLog", str));
        Context context = this.ijk;
        if (!dl.lmn(context, "global_v2", "is_log")) {
            dl.lmn(context, "global_v2", "is_log", true);
        }
        ccVarLmn.lmn = "0";
        cd.lmn(ccVarLmn);
    }

    @Override // com.huawei.hms.analytics.bi
    public final void lmn(String str, Bundle bundle) {
        boolean zEquals;
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#onEvent(String,Bundle)");
        try {
            if (TextUtils.isEmpty(str)) {
                HiLog.w("instanceimpl", HiLog.ErrorCode.PE001, " eventId is empty");
                if ("_openness_config_tag".equals(this.hij)) {
                    ccVarLmn.ijk = "";
                    cd.lmn(ccVarLmn);
                    return;
                }
                return;
            }
            if (dc.lmn(str, an.lmn)) {
                HiLog.e("instanceimpl", HiLog.ErrorCode.PE004, str + " is reserved.");
                if (zEquals) {
                    return;
                } else {
                    return;
                }
            }
            if (dc.lmn(str, bundle)) {
                if ("_openness_config_tag".equals(this.hij)) {
                    ccVarLmn.ijk = str;
                    cd.lmn(ccVarLmn);
                    return;
                }
                return;
            }
            dj djVar = new dj(str, true);
            String strIkl = ar.lmn().ikl().ikl();
            if (!TextUtils.isEmpty(strIkl)) {
                try {
                    djVar.ijk = new JSONObject(strIkl);
                } catch (JSONException unused) {
                    HiLog.w("P_CEvtHandler", "defEvtJson exception");
                }
            }
            if (bundle == null) {
                HiLog.w("instanceimpl", "bundle is null");
                bundle = new Bundle();
            }
            djVar.ikl(bundle);
            lmn(str, djVar);
            ccVarLmn.lmn = "0";
            if ("_openness_config_tag".equals(this.hij)) {
                ccVarLmn.ijk = str;
                cd.lmn(ccVarLmn);
            }
        } finally {
            if ("_openness_config_tag".equals(this.hij)) {
                ccVarLmn.ijk = str;
                cd.lmn(ccVarLmn);
            }
        }
    }

    @Override // com.huawei.hms.analytics.bi
    public final void lmn(String str, String str2) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("instanceimpl", HiLog.ErrorCode.IE006, "The Analytics Kit is disabled");
            return;
        }
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setUserProfile(String,String)");
        if (!dc.lmn(str, str2)) {
            HiLog.w("instanceimpl", "userProperties check failed");
            cd.lmn(ccVarLmn);
        } else {
            try {
                ar.lmn().klm().lmn(str, (Object) str2);
            } catch (JSONException unused) {
                HiLog.e("instanceimpl", "add userProper json exception");
            }
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0057 A[PHI: r1
      0x0057: PHI (r1v6 java.util.List) = (r1v1 java.util.List), (r1v7 java.util.List) binds: [B:22:0x0065, B:17:0x0055] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.huawei.hms.analytics.bi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void lmn(java.lang.String r5, boolean r6) {
        /*
            r4 = this;
            com.huawei.hms.analytics.ar r0 = com.huawei.hms.analytics.ar.lmn()
            com.huawei.hms.analytics.at r0 = r0.lmn
            boolean r0 = r0.fgh
            java.lang.String r1 = "instanceimpl"
            if (r0 != 0) goto L14
            java.lang.String r5 = "IE-006"
            java.lang.String r6 = "The Analytics Kit is disabled"
            com.huawei.hms.analytics.core.log.HiLog.w(r1, r5, r6)
            return
        L14:
            java.lang.String r0 = "HiAnalyticsInstance#setPropertyCollection"
            com.huawei.hms.analytics.cc r0 = com.huawei.hms.analytics.cd.lmn(r0)
            boolean r2 = android.text.TextUtils.isEmpty(r5)
            if (r2 == 0) goto L29
            java.lang.String r5 = "property is empty"
            com.huawei.hms.analytics.core.log.HiLog.w(r1, r5)
            com.huawei.hms.analytics.cd.lmn(r0)
            return
        L29:
            java.util.Locale r2 = java.util.Locale.ENGLISH
            java.lang.String r2 = r5.toUpperCase(r2)
            java.util.List<java.lang.String> r3 = com.huawei.hms.analytics.an.ikl
            boolean r3 = r3.contains(r2)
            if (r3 != 0) goto L48
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r6 = "set property collection failed: "
            java.lang.String r5 = r6.concat(r5)
            com.huawei.hms.analytics.core.log.HiLog.w(r1, r5)
            com.huawei.hms.analytics.cd.lmn(r0)
            return
        L48:
            android.content.Context r5 = r4.ijk
            java.util.List r1 = com.huawei.hms.analytics.dh.lmn(r5)
            if (r1 != 0) goto L5b
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            if (r6 != 0) goto L68
        L57:
            r1.add(r2)
            goto L68
        L5b:
            if (r6 == 0) goto L61
            r1.remove(r2)
            goto L68
        L61:
            boolean r6 = r1.contains(r2)
            if (r6 != 0) goto L68
            goto L57
        L68:
            int r6 = r1.size()
            if (r6 != 0) goto L74
            java.lang.String r6 = ""
        L70:
            com.huawei.hms.analytics.dh.lmn(r5, r6)
            goto La2
        L74:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.util.Iterator r1 = r1.iterator()
        L7d:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L93
            java.lang.Object r2 = r1.next()
            java.lang.String r2 = (java.lang.String) r2
            r6.append(r2)
            java.lang.String r2 = "|"
            r6.append(r2)
            goto L7d
        L93:
            int r1 = r6.length()
            int r1 = r1 + (-1)
            java.lang.StringBuilder r6 = r6.deleteCharAt(r1)
            java.lang.String r6 = r6.toString()
            goto L70
        La2:
            java.lang.String r5 = "0"
            r0.lmn = r5
            com.huawei.hms.analytics.cd.lmn(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.ghi.lmn(java.lang.String, boolean):void");
    }

    @Override // com.huawei.hms.analytics.bi
    public final void lmn(Set<ReportPolicy> set) {
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#setReportPolicies(Set<ReportPolicy>)");
        try {
            bp.lmn().lmn(set);
            ccVarLmn.lmn = "0";
        } catch (Exception unused) {
            HiLog.e("instanceimpl", "decide policy exception");
        } finally {
            cd.lmn(ccVarLmn);
        }
    }
}
