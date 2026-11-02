package com.huawei.hms.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.analytics.bc;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.database.APIEvent;
import com.huawei.hms.analytics.framework.SyncManager;
import com.huawei.hms.analytics.type.HALogConfig;
import com.huawei.hms.analytics.type.ReportPolicy;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.support.hianalytics.HiAnalyticsUtil;
import com.huawei.hms.utils.HMSBIInitializer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class HiAnalyticsInstance {
    private static boolean ikl;
    private static bi klm;
    private static HiAnalyticsInstance lmn;

    private HiAnalyticsInstance() {
    }

    private static hij ikl(Context context, String str) {
        return new hij(context, new klm(!GrsBaseInfo.CountryCodeSource.UNKNOWN.equals(str), str), "_openness_config_tag", str, SyncManager.TaskNames.INIT_TASK_OPENNESS);
    }

    private static bi klm(Context context, String str) {
        Context applicationContext = context.getApplicationContext() != null ? context.getApplicationContext() : context;
        cc ccVarLmn = cd.lmn("HiAnalyticsInstance#getInstance(Context)");
        try {
            try {
                ghi ghiVar = (ghi) fgh.lmn().lmn("_openness_config_tag");
                if (ghiVar == null) {
                    fgh.lmn();
                    fgh.klm(context);
                    ghiVar = new ghi(applicationContext);
                    fgh.lmn().lmn("_openness_config_tag", ghiVar);
                    lmn(context, str, ghiVar);
                    fgh.lmn();
                    fgh.lmn(applicationContext, ghiVar);
                    fgh.lmn();
                    fgh.lmn(applicationContext);
                }
                ccVarLmn.lmn = "0";
                ikl = true;
                return ghiVar;
            } finally {
                cd.lmn(ccVarLmn);
            }
        } catch (bc.lmn e) {
            HiLog.e("HiAnalyticsCreation", HiLog.ErrorCode.SE001, "DBException");
            ccVarLmn.klm = "SE-001:002";
            di.lmn(e);
            try {
                HMSBIInitializer.getInstance(applicationContext).initBI();
                APIEvent aPIEventKlm = cd.klm(ccVarLmn);
                HiAnalyticsUtil.getInstance().onNewEvent(applicationContext, aPIEventKlm.getCode(), aPIEventKlm.toMap());
            } catch (Exception | NoClassDefFoundError unused) {
                HiLog.w("APIEvtRecordHolder", "init hms BISDK failed");
            }
            return null;
        } catch (Throwable th) {
            ccVarLmn.klm = "SE-001:003";
            di.lmn(th);
            HiLog.e("HiAnalyticsCreation", HiLog.ErrorCode.SE001, "Exception " + th.getMessage());
            return null;
        }
    }

    static synchronized HiAnalyticsInstance lmn() {
        klm = null;
        ikl = false;
        ba.lmn("_openness_config_tag", Boolean.FALSE);
        if (lmn != null) {
            return lmn;
        }
        HiAnalyticsInstance hiAnalyticsInstance = new HiAnalyticsInstance();
        lmn = hiAnalyticsInstance;
        return hiAnalyticsInstance;
    }

    static synchronized HiAnalyticsInstance lmn(Context context) {
        return lmn(context, GrsBaseInfo.CountryCodeSource.UNKNOWN);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static synchronized com.huawei.hms.analytics.HiAnalyticsInstance lmn(android.content.Context r4, java.lang.String r5) {
        /*
            java.lang.Class<com.huawei.hms.analytics.HiAnalyticsInstance> r0 = com.huawei.hms.analytics.HiAnalyticsInstance.class
            monitor-enter(r0)
            com.huawei.hms.analytics.dq r1 = com.huawei.hms.analytics.dq.lmn()     // Catch: java.lang.Throwable -> L98
            boolean r1 = r1.lmn(r4)     // Catch: java.lang.Throwable -> L98
            if (r1 == 0) goto L87
            com.huawei.hms.analytics.HiAnalyticsInstance r1 = com.huawei.hms.analytics.HiAnalyticsInstance.lmn     // Catch: java.lang.Throwable -> L98
            if (r1 != 0) goto L30
            com.huawei.hms.analytics.HiAnalyticsInstance r1 = new com.huawei.hms.analytics.HiAnalyticsInstance     // Catch: java.lang.Throwable -> L98
            r1.<init>()     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.HiAnalyticsInstance.lmn = r1     // Catch: java.lang.Throwable -> L98
            android.os.Looper r1 = android.os.Looper.getMainLooper()     // Catch: java.lang.Throwable -> L98
            android.os.Looper r2 = android.os.Looper.myLooper()     // Catch: java.lang.Throwable -> L98
            if (r1 == r2) goto L2d
            java.lang.String r1 = "HiAnalyticsCreation"
            java.lang.String r2 = "IE-001"
            java.lang.String r3 = "init must be called in the main thread"
            com.huawei.hms.analytics.core.log.HiLog.e(r1, r2, r3)     // Catch: java.lang.Throwable -> L98
            r1 = 0
            goto L2e
        L2d:
            r1 = 1
        L2e:
            if (r1 == 0) goto L87
        L30:
            if (r4 != 0) goto L43
            java.lang.String r4 = "HiAnalyticsCreation"
            java.lang.String r5 = "PE-001"
            java.lang.String r1 = "context is null, failed to initialize the Analytics Kit."
            com.huawei.hms.analytics.core.log.HiLog.e(r4, r5, r1)     // Catch: java.lang.Throwable -> L98
            java.lang.String r4 = "_openness_config_tag"
            java.lang.Boolean r5 = java.lang.Boolean.FALSE     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.ba.lmn(r4, r5)     // Catch: java.lang.Throwable -> L98
            goto L87
        L43:
            boolean r1 = com.huawei.hms.analytics.HiAnalyticsInstance.ikl     // Catch: java.lang.Throwable -> L98
            if (r1 != 0) goto L4e
            com.huawei.hms.analytics.bi r4 = klm(r4, r5)     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.HiAnalyticsInstance.klm = r4     // Catch: java.lang.Throwable -> L98
            goto L87
        L4e:
            boolean r4 = com.huawei.hms.analytics.ba.lmn()     // Catch: java.lang.Throwable -> L98
            if (r4 != 0) goto L64
            com.huawei.hms.analytics.ar r4 = com.huawei.hms.analytics.ar.lmn()     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.at r4 = r4.lmn     // Catch: java.lang.Throwable -> L98
            android.content.Context r4 = r4.ghi     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.bi r1 = com.huawei.hms.analytics.HiAnalyticsInstance.klm     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.ghi r1 = (com.huawei.hms.analytics.ghi) r1     // Catch: java.lang.Throwable -> L98
            lmn(r4, r5, r1)     // Catch: java.lang.Throwable -> L98
            goto L87
        L64:
            boolean r4 = com.huawei.hms.analytics.ba.lmn()     // Catch: java.lang.Throwable -> L98
            if (r4 == 0) goto L87
            com.huawei.hms.analytics.fgh.lmn()     // Catch: java.lang.Throwable -> L98
            java.lang.String r4 = "_openness_config_tag"
            boolean r4 = com.huawei.hms.analytics.fgh.lmn(r5, r4)     // Catch: java.lang.Throwable -> L98
            if (r4 == 0) goto L87
            com.huawei.hms.analytics.ar r4 = com.huawei.hms.analytics.ar.lmn()     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.at r4 = r4.lmn     // Catch: java.lang.Throwable -> L98
            android.content.Context r4 = r4.ghi     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.hij r4 = ikl(r4, r5)     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.fgh.lmn()     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.fgh.lmn(r4)     // Catch: java.lang.Throwable -> L98
        L87:
            com.huawei.hms.analytics.HiAnalyticsInstance r4 = com.huawei.hms.analytics.HiAnalyticsInstance.lmn     // Catch: java.lang.Throwable -> L98
            if (r4 != 0) goto L94
            com.huawei.hms.analytics.HiAnalyticsInstance r4 = new com.huawei.hms.analytics.HiAnalyticsInstance     // Catch: java.lang.Throwable -> L98
            r4.<init>()     // Catch: java.lang.Throwable -> L98
            com.huawei.hms.analytics.HiAnalyticsInstance.lmn = r4     // Catch: java.lang.Throwable -> L98
            monitor-exit(r0)
            return r4
        L94:
            com.huawei.hms.analytics.HiAnalyticsInstance r4 = com.huawei.hms.analytics.HiAnalyticsInstance.lmn     // Catch: java.lang.Throwable -> L98
            monitor-exit(r0)
            return r4
        L98:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.HiAnalyticsInstance.lmn(android.content.Context, java.lang.String):com.huawei.hms.analytics.HiAnalyticsInstance");
    }

    private static void lmn(Context context, String str, ghi ghiVar) {
        hij hijVarIkl = ikl(context, str);
        hijVarIkl.lmn = new ikl(ghiVar);
        hijVarIkl.klm = cd.lmn("HiAnalyticsInstance#initTask");
        fgh.lmn();
        fgh.lmn(hijVarIkl);
    }

    public void addDefaultEventParams(Bundle bundle) {
        if (klm != null) {
            final Bundle bundleLmn = dd.lmn(bundle);
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.22
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.lmn(bundleLmn);
                    }
                }
            });
        }
    }

    public void clearCachedData() {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.12
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.ghi();
                    }
                }
            });
        }
    }

    public Task<String> getAAID() {
        if (klm == null) {
            return null;
        }
        if (ba.lmn()) {
            return klm.fgh();
        }
        try {
            SyncManager.getInstance().await(SyncManager.TaskNames.INIT_TASK_OPENNESS, 150L);
            return klm.fgh();
        } catch (InterruptedException unused) {
            HiLog.w("HiAnalyticsCreation", "getAAID Interrupted Exception");
            return null;
        }
    }

    public Map<String, String> getUserProfiles(boolean z) {
        if (klm != null) {
            if (ba.lmn()) {
                return klm.lmn(z);
            }
            try {
                SyncManager.getInstance().await(SyncManager.TaskNames.INIT_TASK_OPENNESS, 150L);
                return klm.lmn(z);
            } catch (InterruptedException unused) {
                HiLog.w("HiAnalyticsCreation", "getAAID Interrupted Exception");
            }
        }
        return new HashMap();
    }

    public boolean isRestrictionEnabled() {
        if (klm != null) {
            return ba.lmn() ? klm.ijk() : ba.ijk();
        }
        return false;
    }

    public boolean isRestrictionShared() {
        if (klm == null) {
            return true;
        }
        if (ba.lmn()) {
            return klm.hij();
        }
        try {
            SyncManager.getInstance().await(SyncManager.TaskNames.INIT_TASK_OPENNESS, 150L);
            return klm.hij();
        } catch (InterruptedException unused) {
            HiLog.w("HiAnalyticsCreation", "isRestrictionShared Interrupted Exception");
            return true;
        }
    }

    public void onEvent(final String str, Bundle bundle) {
        if (klm != null) {
            final Bundle bundleLmn = dd.lmn(bundle);
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.6
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.lmn(str, bundleLmn);
                    }
                }
            });
        }
    }

    public void onNewIntent(final Intent intent) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.18
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.lmn(intent);
                    }
                }
            });
        }
    }

    public void pageEnd(final String str) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.25
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.fgh(str);
                    }
                }
            });
        }
    }

    public void pageStart(final String str, final String str2) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.24
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.klm(str, str2);
                    }
                }
            });
        }
    }

    @Deprecated
    public void regHmsSvcEvent() {
    }

    public void setAnalyticsEnabled(final boolean z) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.19
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.fgh(z);
                    }
                }
            });
        }
    }

    @Deprecated
    public void setAutoCollectionEnabled(boolean z) {
    }

    public void setChannel(final String str) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.15
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.cde(str);
                    }
                }
            });
        }
    }

    public void setCollectAdsIdEnabled(final boolean z) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.5
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.ijk(z);
                    }
                }
            });
        }
    }

    @Deprecated
    public void setCurrentActivity(final Activity activity, final String str, final String str2) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.14
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.lmn(activity, str, str2);
                    }
                }
            });
        }
    }

    public void setCustomReferrer(final String str) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.1
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.bcd(str);
                    }
                }
            });
        }
    }

    public void setMinActivitySessions(final long j) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.20
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.ikl(j);
                    }
                }
            });
        }
    }

    public void setPropertyCollection(final String str, final boolean z) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.17
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.lmn(str, z);
                    }
                }
            });
        }
    }

    public void setPushToken(final String str) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.3
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.def(str);
                    }
                }
            });
        }
    }

    public void setPushTokenCollectionEnabled(final boolean z) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.16
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.efg(z);
                    }
                }
            });
        }
    }

    public void setReportPolicies(Set<ReportPolicy> set) {
        if (klm != null) {
            final HashSet hashSet = new HashSet(set);
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.8
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.lmn(hashSet);
                    }
                }
            });
        }
    }

    public void setRestrictionEnabled(final boolean z) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.4
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.ghi(z);
                    }
                }
            });
        }
    }

    public void setRestrictionShared(final boolean z) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.9
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.hij(z);
                    }
                }
            });
        }
    }

    public void setSessionDuration(final long j) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.21
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.ijk(j);
                    }
                }
            });
        }
    }

    public void setUserId(final String str) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.2
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.efg(str);
                    }
                }
            });
        }
    }

    public void setUserProfile(final String str, final String str2) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.23
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.lmn(str, str2);
                    }
                }
            });
        }
    }

    public void setWXAppId(final String str) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.13
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.ghi(str);
                    }
                }
            });
        }
    }

    public void setWXOpenId(final String str) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.11
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.hij(str);
                    }
                }
            });
        }
    }

    public void setWXUnionId(final String str) {
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.10
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.ijk(str);
                    }
                }
            });
        }
    }

    @Deprecated
    public void unRegHmsSvcEvent() {
    }

    public void writeLog(HALogConfig hALogConfig, final String str) {
        if (hALogConfig == null) {
            HiLog.w("HiAnalyticsCreation", "log config is null");
            return;
        }
        final HALogConfig hALogConfig2 = new HALogConfig(hALogConfig);
        if (klm != null) {
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.HiAnalyticsInstance.7
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.lmn()) {
                        HiAnalyticsInstance.klm.lmn(hALogConfig2, str);
                    }
                }
            });
        }
    }
}
